package com.skysearch.itm.skysearch.Server;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroService {
    @GET("schd/")
    Call<JsonArray> getSchedule(@Query("CH_id") int CH_id);
}
