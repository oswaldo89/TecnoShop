package com.ogomez.tecnoshop.app.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


        //Trae parametros de la lista anterior
        String mPath = extras.getString("_path");
        String mTitulo = extras.getString("_nombre");
        double mPrecio = extras.getDouble("_precio");
        String mcatego = extras.getString("_categoria");
        int mLocal = extras.getInt("_local");
        String mDesc = extras.getString("_descripcion");


        //asignacion de valores
        Picasso.with(this).load(Utils.URL_IMG + mPath).into(imgThumb);
        _titulo.setText(mTitulo);
        _precio.setText(" $" + String.valueOf(mPrecio));
        _catego.setText(mcatego);
        _local.setText(String.valueOf(mLocal));
        _desc.setText(mDesc);

        btnWpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsappContact("8120392634");
            }
        });
    }

    void openWhatsappContact(String number) {
        /*
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
        */

        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.putExtra("sms_body", "Que tal, este mensaje es enviado desde la aplicacion TecnoShop");
        i.setPackage("com.whatsapp");
        startActivity(i);


        /*
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("content://com.android.contacts/data/" + uri));
        i.setType("text/plain");
        i.setPackage("com.whatsapp");           // so that only Whatsapp reacts and not the chooser
        i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(Intent.EXTRA_TEXT, "I'm the body.");
        startActivity(i);
        */
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
