package superheroesintechnology.gl3am.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Subscriber;
import superheroesintechnology.gl3am.Adapters.DirectionListAdapter;
import superheroesintechnology.gl3am.Models.SearchResultModel;
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
                APIClient.getDirectionsProvider()
                        .getDirections("Santa Rosa, CA", searchInput.getText().toString(), "DRIVING", "")
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


}
