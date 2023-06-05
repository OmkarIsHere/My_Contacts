package com.example.mycontacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>{
    private static final String TAG = "adapter";
    String[] colors ={"#8B1874", "#B71375","#FC4F00","#F79540","#3C486B", "#1B9C85", "#FFB84C"};
    Context context;
    ArrayList<AllContact> arrList;ContactRecyclerAdapter(Context context , ArrayList<AllContact> arrList){
        this.context = context;
        this.arrList = arrList;
        Log.d(TAG, "ContactRecyclerAdapter: ");
//        getcolor();
    }

    void getcolor(){
        int count=0;
        for(int i=0; i<arrList.size(); i++){
                if (count > colors.length) {
                    count = 0;
                }
            Log.d(TAG, "colors : " +colors[count]);
            count++;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.contact_card_layout, parent, false);
       ViewHolder viewHolder = new ViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Random generator = new Random();
        int randomIndex = generator.nextInt(colors.length);
        String color = (colors[randomIndex]);

        String id = String.valueOf(arrList.get(position).cId);
        holder.contactId.setText(id);
        holder.hexColor.setText(color);

        try{
            holder.contactImg.setBackgroundColor(Color.parseColor(color));
        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }

        String s = (arrList.get(position).cName).replaceFirst("^ *", "");
        if(s.length() > 2 && s.contains(" ")){
            holder.contactImg.setText(s.replaceAll("^\\s*([a-zA-Z]).*\\s+([a-zA-Z])\\S+$", "$1$2"));
            Log.d(TAG, "first if: "+ s.length() + " "+s);
        }else if(s.length() > 2 && !s.contains(" ")){
            holder.contactImg.setText(s.substring(0,1));
        }else{
            holder.contactImg.setText(s);
        }


        if(s.length()>=29){
          String firstHalf = s.substring(0,28);
          String fullString = firstHalf + "...";
          holder.contactName.setText(fullString);
        }else{
            holder.contactName.setText(s);
        }
        String numType =(arrList.get(position).numType);
        String cNumber = (arrList.get(position).cNumber);
        String joined =  numType+ ": " + cNumber;
        holder.numType.setText(joined);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hexColor = holder.hexColor.getText().toString();
                String temp = holder.contactImg.getText().toString();

                Intent i = new Intent(context.getApplicationContext(), ContactDetailsActivity.class);
                i.putExtra("id",id);
                i.putExtra("img",temp);
                i.putExtra("hexColor",hexColor);
                i.putExtra("name", s);
                i.putExtra("number",cNumber);
                i.putExtra("numType",numType);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.getApplicationContext().startActivity(i);
                Log.d(TAG, "id "+ id + " "+ temp +" "+ s + " " +cNumber +" "+ numType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView contactId, contactName, numType, contactImg, hexColor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            contactId = (TextView)itemView.findViewById(R.id.contactId);
            contactName = (TextView)itemView.findViewById(R.id.contactName);
            contactImg = (TextView)itemView.findViewById(R.id.contactImg);
            numType = (TextView)itemView.findViewById(R.id.numType);
            hexColor = (TextView)itemView.findViewById(R.id.hexColor);
        }
    }
}
