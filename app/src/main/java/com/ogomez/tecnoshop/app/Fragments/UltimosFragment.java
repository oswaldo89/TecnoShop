package com.ogomez.tecnoshop.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ogomez.tecnoshop.app.Adapters.AdapterItems;
import com.ogomez.tecnoshop.app.HomeTabs;
import com.ogomez.tecnoshop.app.R;
import com.ogomez.tecnoshop.app.RestClient.Items;
import com.ogomez.tecnoshop.app.UploadItem;
import com.ogomez.tecnoshop.app.Utils.Utils;

import java.util.ArrayList;

public class UltimosFragment extends android.support.v4.app.Fragment {


    private static final String TAG = "RecyclerViewExample";
    private RecyclerView mRecyclerView;
    private AdapterItems adapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout = null;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ultimos, container, false);


        // Initialize recycler view
        mRecyclerView = (RecyclerView)root.findViewById(R.id.recycler_view);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                ((HomeTabs)getActivity()).hideMenu();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);



        FloatingActionButton fab = (FloatingActionButton)root.findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),UploadItem.class);
                startActivity(i);
            }
        });

        //Initialize swipe to refresh view
        mSwipeRefreshLayout = (SwipeRefreshLayout)root.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Items.getAll(getActivity(), mRecyclerView);
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });

        Items.getAll(getActivity(), mRecyclerView);



        return root;
    }

    @Override
    public void onResume() {
        if(Utils.refresh == 1){
            Items.getAll(getActivity(), mRecyclerView);
        }else{
            Utils.refresh = 0;
        }
        super.onResume();
    }
}
