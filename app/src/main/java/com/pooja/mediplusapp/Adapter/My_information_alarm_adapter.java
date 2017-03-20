package com.pooja.mediplusapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pooja.mediplusapp.Model.Alarm_Information;
import com.pooja.mediplusapp.R;

import java.util.ArrayList;

/**
 * Created by Pooja on 3/14/2017.
 */

public class My_information_alarm_adapter extends RecyclerView.Adapter<My_information_alarm_adapter.MY_INFO_ViewHolder> {
    Context context;
    ArrayList<Alarm_Information> infoarraylist;
    LongClickInteface longclickinterface;

    public interface LongClickInteface
    {
        public void onlongclicked(int position);
    }

    public void setLongClickInteface(LongClickInteface longclickinterface)
    {
        this.longclickinterface=longclickinterface;
    }

    public My_information_alarm_adapter(Context context, ArrayList<Alarm_Information> infoarraylist) {
        this.context = context;
        this.infoarraylist = infoarraylist;
    }

    @Override
    public My_information_alarm_adapter.MY_INFO_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.each_row_alarm_information,parent,false);
        return new MY_INFO_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(My_information_alarm_adapter.MY_INFO_ViewHolder holder, int position) {
        Alarm_Information alarm_information=infoarraylist.get(position);
        holder.al_name.setText(alarm_information.getAl_name());
        holder.al_msg.setText(alarm_information.getAl_message());
        holder.al_date.setText(alarm_information.getAl_date());
        holder.al_time.setText(alarm_information.getAl_time());
        holder.al_pi_no.setText(alarm_information.getAl_pending_no());
        holder.al_id.setText(String.valueOf(alarm_information.getAl_id()));
    }

    @Override
    public int getItemCount() {
        return infoarraylist.size();
    }

    public class MY_INFO_ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        TextView al_name,al_msg,al_date,al_time,al_pi_no,al_id;
        public MY_INFO_ViewHolder(View itemView) {
            super(itemView);
            al_name=(TextView)itemView.findViewById(R.id.info_alarm_disp_alarm_name);
            al_msg=(TextView)itemView.findViewById(R.id.info_display_alarm_message);
            al_date=(TextView)itemView.findViewById(R.id.info_alarm_disp_alarm_date);
            al_time=(TextView)itemView.findViewById(R.id.info_alarm_disp_alarm_time);
            al_pi_no=(TextView)itemView.findViewById(R.id.info_pending_intent);
            al_id=(TextView)itemView.findViewById(R.id.info_alarm_id);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            longclickinterface.onlongclicked(getAdapterPosition());
            Toast.makeText(context, "Long clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
