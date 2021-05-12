package com.dangdinhsi.asmt1902e.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dangdinhsi.asmt1902e.R;
import com.dangdinhsi.asmt1902e.model.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter {
    final String LINK_ICON ="https://developer.accuweather.com/sites/default/files/";
    final String TAIL ="-s.png";
    private Activity activity;
    private List<Item> list;
    public ItemAdapter(Activity activity, List<Item> list) {
        this.activity = activity;
        this.list = list;
    }
    public void reloadData(List<Item> list){
        this.list = list;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = activity.getLayoutInflater().inflate(R.layout.weather_layout,parent,false);
        ItemHolder holder = new ItemHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String urlIcon ="";
        ItemHolder vh = (ItemHolder) holder;
        Item model = list.get(position);
        float iTem = model.getTemperature().getValue();
        vh.wDate.setText(convertTime(model.getDateTime(),"ha"));
        vh.wTemp.setText(""+iTem);
        vh.wStatus.setText(model.getIconPhrase());
        if(model.getWeatherIcon()<10){
            urlIcon += LINK_ICON+ "0"+model.getWeatherIcon()+TAIL;
        }
            urlIcon += LINK_ICON+model.getWeatherIcon()+TAIL;
            Glide.with(activity).load(urlIcon).into(vh.wIcon);

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{

        TextView wStatus,wDate,wTemp;
        ImageView  wIcon;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            wStatus = itemView.findViewById(R.id.wStatus);
            wDate = itemView.findViewById(R.id.wDate);
            wTemp = itemView.findViewById(R.id.wTemp);
            wIcon = itemView.findViewById(R.id.wIcon);
        }
    }

    public static String convertTime(String inputTime,String pattern) {
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat(pattern);
        String goal = outFormat.format(date);
        return goal;
    }
}
