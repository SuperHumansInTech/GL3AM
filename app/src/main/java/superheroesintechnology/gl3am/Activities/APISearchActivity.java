package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;


import android.text.TextUtils;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Subscriber;
import superheroesintechnology.gl3am.Adapters.DirectionListAdapter;
import superheroesintechnology.gl3am.Models.LatLngModel;
import superheroesintechnology.gl3am.Models.LegModel;
import superheroesintechnology.gl3am.Models.RouteModel;
import superheroesintechnology.gl3am.Models.SearchResultModel;
import superheroesintechnology.gl3am.Models.TextValModel;
import superheroesintechnology.gl3am.R;
import superheroesintechnology.gl3am.Services.APIClient;

/**
 * Created by student on 11/12/15.
 */
public class APISearchActivity extends Activity {
    private ListView searchResultsList;
    private Button searchButton;
    private EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apisearch);

        searchResultsList = (ListView)findViewById(R.id.searchResultsList);
        searchButton = (Button) findViewById(R.id.searchButton);
        searchInput = (EditText) findViewById(R.id.searchInput);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SearchResultModel model = setTestData();
               //searchResultsList.setAdapter(new DirectionListAdapter(APISearchActivity.this, model.getSearchResults()));
                if(searchInput.getText() == null) {
                    return;
                }

                APIClient.getDirectionsProvider()
                        .getDirections("Santa+Rosa+CA", TextUtils.htmlEncode(searchInput.getText().toString()))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<SearchResultModel>() {

                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {int i = 0; }

                            @Override
                            public void onNext(SearchResultModel searchResultModel) {
                                searchResultsList.setAdapter(new DirectionListAdapter(APISearchActivity.this, searchResultModel.getSearchResults()));
                            }
                        });

            }
        });


    }

    private SearchResultModel setTestData() {
        SearchResultModel results = new SearchResultModel();
        results.setSearchResults(new ArrayList<RouteModel>());
        RouteModel route1 = new RouteModel();
        RouteModel route2 = new RouteModel();
        RouteModel route3 = new RouteModel();

        LegModel  leg1 = new LegModel();
        LegModel  leg2 = new LegModel();
        LegModel  leg3 = new LegModel();
        TextValModel duration1 = new TextValModel();
        TextValModel duration2 = new TextValModel();
        TextValModel duration3 = new TextValModel();

        duration1.setText("1 hour");
        duration1.setValue(60);
        leg1.setDuration(duration1);

        duration2.setText("2 hours");
        duration2.setValue(120);
        leg2.setDuration(duration2);

        duration3.setText("3 hours");
        duration3.setValue(180);
        leg3.setDuration(duration3);


        leg1.setEnd_address("123 Street St.");
        leg2.setEnd_address("123 Street St.");
        leg3.setEnd_address("123 Street St.");

        leg1.setEnd_address("123 Street St.");
        leg2.setEnd_address("123 Street St.");
        leg3.setEnd_address("123 Street St.");

        LatLngModel end_coords = new LatLngModel();
        end_coords.setCoords(12.55, 12.55);

        leg1.setEnd_location(end_coords);
        leg2.setEnd_location(end_coords);
        leg3.setEnd_location(end_coords);

        LatLngModel start_coords = new LatLngModel();
        start_coords.setCoords(20.20, 20.20);

        leg1.setStart_location(start_coords);
        leg2.setStart_location(start_coords);
        leg3.setStart_location(start_coords);

        ArrayList<LegModel> legList1 = new ArrayList<LegModel>();
        ArrayList<LegModel> legList2 = new ArrayList<LegModel>();
        ArrayList<LegModel> legList3 = new ArrayList<LegModel>();

        legList1.add(leg1);
        legList2.add(leg2);
        legList3.add(leg3);

        route1.setSummary("Best one");
        route1.setLegsArray(legList1);

        route2.setSummary("Second best");
        route2.setLegsArray(legList2);

        route3.setSummary("Third best");
        route3.setLegsArray(legList3);

        results.getSearchResults().add(route1);
        results.getSearchResults().add(route2);
        results.getSearchResults().add(route3);
        //LegModel leg1 = new LegModel();
       // LegModel leg2 = new LegModel();
       // LegModel leg3 = new LegModel();


        return results;


    }


}
