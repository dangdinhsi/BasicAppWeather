package com.dangdinhsi.asmt1902e.network;

import com.dangdinhsi.asmt1902e.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIManager {
    String DOMAIN ="http://dataservice.accuweather.com";
    @GET("/forecasts/v1/hourly/12hour/353412?apikey=a4FXshjpyJ13FW63JgtAAOXIqf1MMOC8&language=vi-vn&metric=true")
    Call<List<Item>> getListData();
}