package com.ogomez.tecnoshop.app.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogomez.tecnoshop.app.Objects.ItemP;
import com.ogomez.tecnoshop.app.Objects.ItemProduct;
import com.ogomez.tecnoshop.app.R;
import com.ogomez.tecnoshop.app.Utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oswaldo Gomez on 25/07/2015.
 */

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.CustomViewHolder> {
    private List<ItemP> items;
    private Context mContext;

    public AdapterItems(Context context, List<ItemP> p) {

        this.items = p;
        //Log.v("TAG","Tamaño :: "+this.items.size());
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, null);
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {


        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/robotoBold.ttf");
        //asignamos el estilo de letra
        customViewHolder._nombreItem.setTypeface(face);
        customViewHolder._categoryItem.setTypeface(face);
        customViewHolder._localNumber.setTypeface(face);

        //categoria ala que pertenece
        customViewHolder._nombreItem.setText(items.get(i).getNombre());
        customViewHolder._categoryItem.setText(items.get(i).getCategoria());
        customViewHolder._localNumber.setText("Local :: " + items.get(i).getLocal());

        //crea la imagen
        Picasso.with(mContext).load(Utils.URL_IMG+items.get(i).getPath()).into(customViewHolder._imagenItem);
        //Log.v("REST",""+Utils.URL_IMG+items.get(i).getPath());
    }

    @Override
    public int getItemCount() {

        return (null != items ? items.size() : 0);
    }


    public void clear(){
        items.clear();
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView _imagenItem;
        protected TextView _nombreItem;
        protected TextView _categoryItem;
        protected TextView _localNumber;


        public CustomViewHolder(View view) {
            super(view);
            this._imagenItem = (ImageView) view.findViewById(R.id.thumbnail);
            this._nombreItem = (TextView) view.findViewById(R.id.title);
            this._categoryItem = (TextView) view.findViewById(R.id.titleCat);
            this._localNumber = (TextView) view.findViewById(R.id.txtLocal);
        }
    }
}

