package com.pooja.mediplusapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pooja.mediplusapp.R;

/**
 * Created by Pooja on 3/10/2017.
 */

public class SimpleShoppingAdapter extends RecyclerView.Adapter<SimpleShoppingAdapter.Shop_ViewHolder> {
    Context ctx;
    String[] arraystring;

    public SimpleShoppingAdapter(Context ctx, String[] arraystring) {
        this.ctx = ctx;
        this.arraystring=arraystring;
    }

    @Override
    public Shop_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_url,parent,false);
        return new Shop_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Shop_ViewHolder holder, int position) {
        holder.url_text.setText(arraystring[position]);

    }

    @Override
    public int getItemCount() {
        return arraystring.length;
    }

    public class Shop_ViewHolder extends RecyclerView.ViewHolder {
        TextView url_text;
        public Shop_ViewHolder(View itemView) {
            super(itemView);
            url_text=(TextView)itemView.findViewById(R.id.textview_url);
        }
    }
}
