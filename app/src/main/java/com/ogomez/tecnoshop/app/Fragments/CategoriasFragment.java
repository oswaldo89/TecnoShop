package com.ogomez.tecnoshop.app.Fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ogomez.tecnoshop.app.Adapters.AdapterCategories;
import com.ogomez.tecnoshop.app.HomeTabs;
import com.ogomez.tecnoshop.app.R;
import com.ogomez.tecnoshop.app.RestClient.Items;

import java.util.ArrayList;
import java.util.List;

public class CategoriasFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "RecyclerViewExample";
    private RecyclerView mRecyclerView;
    private AdapterCategories adapter;
    private ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_categorias, container, false);

        // Initialize recycler view
        mRecyclerView = (RecyclerView)root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                ((HomeTabs) getActivity()).hideMenu();
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        progressBar = (ProgressBar)root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);



        List<String> lista = new ArrayList<>();
        lista.add("Computacion");
        lista.add("Celulares y Telefonia");
        lista.add("Camaras y accesorios");
        lista.add("Consolas y videojuegos");
        lista.add("Electronica, audio y video");
        lista.add("Musica , peliculas y series");
        lista.add("Servicios");

        int[] icon_category = new int[]{
                R.drawable.ic_computacion,
                R.drawable.ic_celulares,
                R.drawable.ic_camaras,
                R.drawable.ic_juegos,
                R.drawable.ic_electronica,
                R.drawable.ic_audio,
                R.drawable.ic_servicios
        };


        adapter = new AdapterCategories(getActivity(), lista,icon_category);
        mRecyclerView.setAdapter(adapter);


        return root;
    }


}
