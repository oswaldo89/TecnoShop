package com.ogomez.tecnoshop.app.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogomez.tecnoshop.app.Objects.ItemP;
import com.ogomez.tecnoshop.app.Objects.ItemProduct;
import com.ogomez.tecnoshop.R;
import com.ogomez.tecnoshop.app.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oswaldo Gomez on 25/07/2015.
 */

/**
 * Created by Oswaldo Gomez on 17/08/2015.
 */
public class AdapterItems extends ArrayAdapter<ItemP> {

    private final Activity context;
    List<ItemP> cat;


    public AdapterItems(Activity context, List<ItemP> cat) {
        super(context, R.layout.item_row, cat);
        this.context = context;
        this.cat = cat;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = inflater.inflate(R.layout.item_row, null);

        ImageView _imagenItem = (ImageView) rowView.findViewById(R.id.thumbnail);
        TextView _nombreItem = (TextView) rowView.findViewById(R.id.title);
        TextView _categoryItem = (TextView) rowView.findViewById(R.id.titleCat);
        TextView _localNumber = (TextView) rowView.findViewById(R.id.txtLocal);

        Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/robotoBold.ttf");
        //asignamos el estilo de letra
        _nombreItem.setTypeface(face);
        _categoryItem.setTypeface(face);
        _localNumber.setTypeface(face);

        //categoria ala que pertenece
        _nombreItem.setText(cat.get(i).getNombre());
        _categoryItem.setText(cat.get(i).getCategoria());
        _localNumber.setText("Local :: " + cat.get(i).getLocal());

        //crea la imagen
        Picasso.with(context).load(Utils.URL_IMG + cat.get(i).getPath()).into(_imagenItem);

        return rowView;
    }


}
