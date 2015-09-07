package com.ogomez.tecnoshop.app.Activities;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogomez.tecnoshop.R;
import com.ogomez.tecnoshop.app.Utils.Utils;
import com.squareup.picasso.Picasso;

public class DetailScreen extends AppCompatActivity {
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail_screen);
        extras = getIntent().getExtras();

        //inicializa variables
        ImageView imgThumb = (ImageView) findViewById(R.id.imgThumb);
        TextView _titulo = (TextView) findViewById(R.id._titulo);
        TextView _precio = (TextView) findViewById(R.id._precio);
        TextView _catego = (TextView) findViewById(R.id._catego);
        TextView _local = (TextView) findViewById(R.id._local);
        TextView _desc = (TextView) findViewById(R.id._desc);


        //Trae parametros de la lista anterior
        String mPath = extras.getString("_path");
        String mTitulo = extras.getString("_nombre");
        double mPrecio = extras.getDouble("_precio");


        //asignacion de valores
        Picasso.with(this).load(Utils.URL_IMG + mPath).into(imgThumb);
        _titulo.setText(mTitulo);
        _precio.setText(" $" + String.valueOf(mPrecio));
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
