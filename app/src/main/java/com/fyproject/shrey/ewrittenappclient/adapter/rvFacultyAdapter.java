package com.fyproject.shrey.ewrittenappclient.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fyproject.shrey.ewrittenappclient.R;
import com.fyproject.shrey.ewrittenappclient.model.WAppBase;

import java.util.List;

/**
 * Created by shrey on 23/03/17.
 */

public class rvFacultyAdapter extends RecyclerView.Adapter<rvFacultyAdapter.MyViewHolder> {
    //WAppBase
    private List<WAppBase> listData;
    private rvFacultyAdapter.OnItemClickListener listener;

    /***** Creating OnItemClickListener *****/

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, WAppBase rowData, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(rvFacultyAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    /***** ***************************** *****/

    // Provide a reference to the views(in rv_row.xml) for each data item
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView toName,appType,date,status;

        public MyViewHolder(final View itemView){
            super(itemView);
            toName= (TextView) itemView.findViewById(R.id.tvToName);
            appType=(TextView) itemView.findViewById(R.id.tvAppType);
            date=(TextView) itemView.findViewById(R.id.tvDate);
            status=(TextView) itemView.findViewById(R.id.tvStatus);

            // Setup the click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(itemView,listData.get(position), position);
                        }
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public rvFacultyAdapter(List<WAppBase> listing){
        this.listData=listing;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_student_row,parent,false);
        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view(a row), bind data (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // get data from List<rvStudentRow> declared in this class
        WAppBase data=listData.get(position);
        holder.toName.setText(data.fromName);
        holder.appType.setText(data.getType());
        holder.date.setText(data.getDate_submitted());
        holder.status.setText(data.getStatus());  //Can check status here


    }

//    private Context getContext() {
//        return ;
//    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


}