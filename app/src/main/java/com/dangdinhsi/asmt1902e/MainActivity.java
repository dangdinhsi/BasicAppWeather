package com.dangdinhsi.asmt1902e;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.dangdinhsi.asmt1902e.adapter.ItemAdapter;
import com.dangdinhsi.asmt1902e.model.Item;
import com.dangdinhsi.asmt1902e.network.APIManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    List<Item> listData;
    ItemAdapter itemAdapter;
    TextView wStatusCurrent,currentTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wStatusCurrent = findViewById(R.id.wStatus);
        currentTemp = findViewById(R.id.currentTemp);

        //B1: Data source
        CallApi();
        //B2: Adapter
        listData= new ArrayList<>();
        itemAdapter = new ItemAdapter(MainActivity.this,listData);
        //B3: Layout Manager.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        //B4: RecyclerView
        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(itemAdapter);
    }
    public void CallApi(){
        Retrofit retrofit = new  Retrofit.Builder().baseUrl(APIManager.DOMAIN).addConverterFactory(GsonConverterFactory.create()).build();
        APIManager service = retrofit.create(APIManager.class);
        service.getListData().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if(response.body() !=null){
                   listData  = response.body();
                   itemAdapter.reloadData(listData);
                }
                Item item = listData.get(0);
                wStatusCurrent.setText(item.getIconPhrase());
                currentTemp.setText(item.getTemperature().getValue()+"°");
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"FAIL"+t,Toast.LENGTH_LONG).show();
                Log.e("Error", "Message: "+t);
            }
        });
    }
}