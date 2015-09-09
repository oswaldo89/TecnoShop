package com.ogomez.tecnoshop.app.Activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ogomez.tecnoshop.R;
import com.ogomez.tecnoshop.app.Adapters.AdapterItems;
import com.ogomez.tecnoshop.app.RestClient.Items;
import com.ogomez.tecnoshop.app.RestClient.ItemsMore;
import com.quentindommerc.superlistview.OnMoreListener;
import com.quentindommerc.superlistview.SuperListview;

public class SearchScreen extends AppCompatActivity {
    private Bundle extras;
    private SuperListview listview;
    String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_search_screen);
        extras = getIntent().getExtras();
        searchTerm = extras.getString("searchTerm");


        if (searchTerm.contains(" ")) {
            searchTerm = searchTerm.substring(0, searchTerm.indexOf(" "));
            bar.setSubtitle(searchTerm);
        }


        //Comienza a superlista
        // Initialize recycler view
        listview = (SuperListview) findViewById(R.id.recycler_view);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AdapterItems adpt = (AdapterItems) listview.getAdapter();
                int _id = adpt.getItem(i).getId();
                String _nombre = adpt.getItem(i).getNombre();
                String _categoria = adpt.getItem(i).getCategoria();
                String _descripcion = adpt.getItem(i).getDescripcion();
                String _path = adpt.getItem(i).getPath();
                String _telefono = adpt.getItem(i).getTelefono();
                double _precio = adpt.getItem(i).getPrecio();
                int _local = adpt.getItem(i).getLocal();


                Intent intent = new Intent(SearchScreen.this, DetailScreen.class);
                intent.putExtra("_id", _id);
                intent.putExtra("_nombre", _nombre);
                intent.putExtra("_categoria", _categoria);
                intent.putExtra("_descripcion", _descripcion);
                intent.putExtra("_path", _path);
                intent.putExtra("_precio", _precio);
                intent.putExtra("_local", _local);
                intent.putExtra("_telefono", _telefono);
                startActivity(intent);

            }
        });
        //carga mas items
        listview.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {
                listview.showMoreProgress();
                ItemsMore.getMorebySearch(SearchScreen.this, listview, numberOfItems, 20, searchTerm);

            }
        }, 1);

        //refresca la lista
        listview.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Items.getBySearch(SearchScreen.this, listview, searchTerm);
            }
        });


        Items.getBySearch(SearchScreen.this, listview, searchTerm);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
