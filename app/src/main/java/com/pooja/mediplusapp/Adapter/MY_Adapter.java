package com.pooja.mediplusapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pooja.mediplusapp.Model.MediData;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

/**
 * Created by Pooja on 3/3/2017.
 */

public class MY_Adapter extends RecyclerView.Adapter<MY_Adapter.MyView_Holder> {

    Context context;
    ArrayList<MediData> myarraylist;
    private ClickInterface myclickinterface;


    public void deletedrug(int position)
    {
        myarraylist.remove(position);
        notifyItemRemoved(position);
    }
    public interface ClickInterface
    {
        public void onsingleClick(int position);
        public void onLongclick(int position);
    }

    public void setClickInterface(final ClickInterface clickinterface)
    {
        this.myclickinterface=clickinterface;
    }

    public MY_Adapter(Context context, ArrayList<MediData> myarraylist) {
        this.context = context;
        this.myarraylist = myarraylist;
    }

    @Override
    public MyView_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_mainpage,parent,false);
        return new MyView_Holder(view,context);
    }

    @Override
    public void onBindViewHolder(MyView_Holder holder, int position) {
        MediData mymedidata=myarraylist.get(position);
        holder.drugname.setText(mymedidata.getName());
        holder.drug_id.setText(String.valueOf(mymedidata.getId()));
    }

    @Override
    public int getItemCount() {
        return myarraylist.size();
    }

    public class MyView_Holder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        Context ctc;
        TextView drugname,drug_id;

        public MyView_Holder(View itemView,Context con) {
            super(itemView);
            this.ctc=con;
            drugname=(TextView)itemView.findViewById(R.id.textView_drug_name_main_page);
            drug_id=(TextView)itemView.findViewById(R.id.id_drug_name_main_page);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
         myclickinterface.onsingleClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            myclickinterface.onLongclick(getAdapterPosition());
            deletedrug(getAdapterPosition());
            return true;
        }
    }
}
