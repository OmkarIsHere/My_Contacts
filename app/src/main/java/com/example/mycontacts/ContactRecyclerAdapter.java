package com.example.mycontacts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>{
    private static final String TAG = "adapter";
    Context context;
    ArrayList<AllContact> arrList;
    ContactRecyclerAdapter(Context context , ArrayList<AllContact> arrList){
        this.context = context;
        this.arrList = arrList;
        Log.d(TAG, "ContactRecyclerAdapter: ");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.contact_card_layout, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
        Log.d(TAG, "onCreateViewHolder: ");
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String id = String.valueOf(arrList.get(position).cId);
        holder.contactId.setText(id);

        String s = (arrList.get(position).cName);
        if(s.length() > 2 && s.contains(" ")){
            holder.contactImg.setText(s.replaceAll("^\\s*([a-zA-Z]).*\\s+([a-zA-Z])\\S+$", "$1$2"));
        }else if(s.length() > 2 && !s.contains(" ")){
            holder.contactImg.setText(s.substring(0,1));
        }else{
            holder.contactImg.setText(s);
        }

        s = s.replaceFirst("^ *", "");
        if(s.length()>=29){
          String firstHalf = s.substring(0,28);
          String fullString = firstHalf + "...";
          holder.contactName.setText(fullString);
        }else{
        holder.contactName.setText(s);
        }
        holder.numType.setText(arrList.get(position).numType);
        Log.d(TAG, "onBindViewHolder: " + arrList.get(position).cId + s );
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView contactId, contactName, numType, contactImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactId = (TextView)itemView.findViewById(R.id.contactId);
            contactName = (TextView)itemView.findViewById(R.id.contactName);
            contactImg = (TextView)itemView.findViewById(R.id.contactImg);
            numType = (TextView)itemView.findViewById(R.id.numType);
            Log.d(TAG, "ViewHolder: ");
        }
    }
}
