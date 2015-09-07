package com.ogomez.tecnoshop.app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.ogomez.tecnoshop.R;
import com.ogomez.tecnoshop.app.Activities.DetailScreen;
import com.ogomez.tecnoshop.app.Activities.HomeTabs;
import com.ogomez.tecnoshop.app.Activities.UploadItem;
import com.ogomez.tecnoshop.app.Adapters.AdapterItems;
import com.ogomez.tecnoshop.app.RestClient.Items;
import com.ogomez.tecnoshop.app.RestClient.ItemsMore;
import com.ogomez.tecnoshop.app.Utils.Utils;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;

public class UltimosFragment extends android.support.v4.app.Fragment {

    private SuperListview listview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ultimos, container, false);


        // Initialize recycler view
        listview = (SuperListview) root.findViewById(R.id.recycler_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AdapterItems adpt = (AdapterItems) listview.getAdapter();
                int _id = adpt.getItem(i).getId();
                String _nombre = adpt.getItem(i).getNombre();
                String _categoria = adpt.getItem(i).getCategoria();
                String _descripcion = adpt.getItem(i).getDescripcion();
                String _path = adpt.getItem(i).getPath();
                double _precio = adpt.getItem(i).getPrecio();
                int _local = adpt.getItem(i).getLocal();


                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtra("_id", _id);
                intent.putExtra("_nombre", _nombre);
                intent.putExtra("_categoria", _categoria);
                intent.putExtra("_descripcion", _descripcion);
                intent.putExtra("_path", _path);
                intent.putExtra("_precio", _precio);
                intent.putExtra("_local", _local);
                startActivity(intent);

            }
        });


        //carga mas items
        listview.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                listview.showMoreProgress();
                ItemsMore.getAll(getActivity(), listview);
            }
        }, 1);

        //refresca la lista
        listview.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Items.getAll(getActivity(), listview);
            }
        });

        //cuando se toca la lista
        listview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((HomeTabs) getActivity()).hideMenu();
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UploadItem.class);
                startActivity(i);
            }
        });

        Items.getAll(getActivity(), listview);


        return root;
    }

    @Override
    public void onResume() {
        if (Utils.refresh == 1) {
            Items.getAll(getActivity(), listview);
        } else {
            Utils.refresh = 0;
        }
        super.onResume();
    }
}
