package com.pooja.mediplusapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pooja.mediplusapp.Model.Pharma_model;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

/**
 * Created by Pooja on 3/16/2017.
 */

public class My_Pharma_Adapter extends RecyclerView.Adapter<My_Pharma_Adapter.My_Pharma_ViewHolder> {
    Context con;
    ArrayList<Pharma_model> mypharmaarraylist;
    ClickInterfacePharma clickinterfacephrma;

    public interface ClickInterfacePharma
    {

        public void onsingleclicknumber(int position);
        public void onLongclick(int position);
    }

    public void delete(int position)
    {
        mypharmaarraylist.remove(position);
        notifyItemRemoved(position);
    }
    public void deleteallphar()
    {
        mypharmaarraylist.clear();
        notifyDataSetChanged();
    }
    public void setClickintefacepharma(ClickInterfacePharma clickinterfacepharma)
    {
        this.clickinterfacephrma=clickinterfacepharma;
    }
    public My_Pharma_Adapter(Context con, ArrayList<Pharma_model> mypharmaarraylist) {
        this.con = con;
        this.mypharmaarraylist = mypharmaarraylist;
    }

    @Override
    public My_Pharma_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_pharmacy_det,parent,false);
        return new My_Pharma_ViewHolder(view,con);
    }

    @Override
    public void onBindViewHolder(My_Pharma_ViewHolder holder, int position) {

        holder.pharma_name.setText(mypharmaarraylist.get(position).getPharma_name());
        holder.pharma_number.setText(mypharmaarraylist.get(position).getPharma_number());
        holder.pharma_address.setText(mypharmaarraylist.get(position).getPharma_address());
        holder.pharma_id.setText(String.valueOf(mypharmaarraylist.get(position).getPharma_id()));
    }

    @Override
    public int getItemCount() {
        return mypharmaarraylist.size();
    }

    public class My_Pharma_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView pharma_name,pharma_number,pharma_address,pharma_id;
        Context context;
        public My_Pharma_ViewHolder(View itemView,Context c) {
            super(itemView);
            this.context=c;
            pharma_name=(TextView)itemView.findViewById(R.id.pharma_name);
            pharma_number=(TextView)itemView.findViewById(R.id.pharma_num);
            pharma_address=(TextView)itemView.findViewById(R.id.pharma_address);
            pharma_id=(TextView)itemView.findViewById(R.id.pharma_id);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            clickinterfacephrma.onLongclick(getAdapterPosition());
            delete(getAdapterPosition());
            return true;
        }


        @Override
        public void onClick(View v) {
            clickinterfacephrma.onsingleclicknumber(getAdapterPosition());

        }
    }
}
