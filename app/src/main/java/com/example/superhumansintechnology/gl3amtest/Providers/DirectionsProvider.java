package com.example.superhumansintechnology.gl3amtest.Providers;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import retrofit.http.Query;
import com.example.superhumansintechnology.gl3amtest.Models.SearchResultModel;


/**
 * Created by Zach on 11/5/2015.
 */
public interface DirectionsProvider {
    @GET("/directions/JSON?")
    Observable<SearchResultModel> getDirections(@Query("origin") String origin)
}
