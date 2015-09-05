package com.ogomez.tecnoshop.app.Adapters;

/**
 * Created by Oswaldo Gomez on 25/07/2015.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ogomez.tecnoshop.R;

import java.util.List;
public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.CustomViewHolder> {
    private final int[] icon_category;
    private List<String> feedItemList;
    private Context mContext;

    public AdapterCategories(Context context, List<String> feedItemList, int[] icon_category) {
        this.feedItemList = feedItemList;
        this.icon_category = icon_category;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_row, null);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {

        //crea cada una de las categorias
        Typeface face = Typeface.createFromAsset(mContext.getAssets(), "fonts/robotoBold.ttf");
        customViewHolder.textView.setText(feedItemList.get(i));
        customViewHolder.textView.setTypeface(face);

        //crea la imagen
        customViewHolder.imageView.setImageResource(icon_category[i]);
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
        }
    }
}
