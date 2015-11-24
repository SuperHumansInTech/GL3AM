package superheroesintechnology.gl3am.Services;


import com.google.gson.Gson;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import superheroesintechnology.gl3am.AppDefines;
import superheroesintechnology.gl3am.Providers.DirectionsProvider;

/**
 * Created by Zach on 11/11/2015.
 */
public class APIClient {
    private static DirectionsProvider directionsProvider;

    public static DirectionsProvider getDirectionsProvider(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppDefines.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        directionsProvider = retrofit.create(DirectionsProvider.class);

        return directionsProvider;
    }

}