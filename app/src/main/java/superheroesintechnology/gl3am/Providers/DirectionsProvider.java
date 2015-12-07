package superheroesintechnology.gl3am.Providers;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import superheroesintechnology.gl3am.Models.SearchResultModel;

/**
 * Created by Zach on 11/11/2015.
 */
public interface DirectionsProvider {
    @GET("directions/json")
    Observable<SearchResultModel> getDirections(@Query("origin") String origin, @Query("destination") String destination);

}
