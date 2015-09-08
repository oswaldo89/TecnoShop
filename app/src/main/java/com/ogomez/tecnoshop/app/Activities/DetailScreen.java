package com.ogomez.tecnoshop.app.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
        Button btnWpp = (Button) findViewById(R.id.btnWhatsap);
        Button btn_llamar = (Button) findViewById(R.id.btn_llamar);


        //Trae parametros de la lista anterior
        String mPath = extras.getString("_path");
        String mTitulo = extras.getString("_nombre");
        double mPrecio = extras.getDouble("_precio");
        String mcatego = extras.getString("_categoria");
        int mLocal = extras.getInt("_local");
        String mDesc = extras.getString("_descripcion");
        final String mTel = extras.getString("_telefono");


        //asignacion de valores
        Picasso.with(this).load(Utils.URL_IMG + mPath).into(imgThumb);
        _titulo.setText(mTitulo);
        _precio.setText(" $" + String.valueOf(mPrecio));
        _catego.setText(mcatego);

        if(mLocal == 0){
            _local.setText("Vendedor independiente");
        }else{
            _local.setText(String.valueOf(mLocal));
        }

        _desc.setText(mDesc);

        btnWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTel != null && !mTel.isEmpty()) {
                    openWhatsappContact(mTel);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Este producto no tiene telefono agregado", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btn_llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTel != null && !mTel.isEmpty()) {
                    openTehelphoneService(mTel);
                } else {
                    Snackbar.make(findViewById(android.R.id.content), "Este producto no tiene telefono agregado", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    void openWhatsappContact(String number) {

        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", "Aplicacion TecnoShop");
        i.setPackage("com.whatsapp");
        startActivity(i);
    }

    void openTehelphoneService(String number) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:" + number));
        startActivity(callIntent);
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
