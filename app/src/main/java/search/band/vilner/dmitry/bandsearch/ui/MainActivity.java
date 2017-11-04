package search.band.vilner.dmitry.bandsearch.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import search.band.vilner.dmitry.bandsearch.R;
import search.band.vilner.dmitry.bandsearch.loader.BandListLoader;
import search.band.vilner.dmitry.bandsearch.loader.LoaderResult;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SEARCH_TERM = "SearchTerm";
    private BandsListAdapter bandsListAdapter;
    private static final int BAND_SEARCH_LOADER_ID = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "onQueryTextSubmit: " + s);
                Bundle bundle = new Bundle();
                bundle.putString(SEARCH_TERM, s);
                bandsListAdapter.clear();
                bandsListAdapter.notifyDataSetChanged();
                getLoaderManager().restartLoader(BAND_SEARCH_LOADER_ID, bundle, callbacks);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        ListView listView = findViewById(R.id.band_list_view);
        bandsListAdapter = new BandsListAdapter(this, R.layout.band_list_item);
        listView.setAdapter(bandsListAdapter);
    }

    private LoaderManager.LoaderCallbacks<LoaderResult<List<BandShortInfo>>> callbacks = new LoaderManager.LoaderCallbacks<LoaderResult<List<BandShortInfo>>>() {
        @Override
        public Loader<LoaderResult<List<BandShortInfo>>> onCreateLoader(int i, Bundle bundle) {
            String searchTerm = bundle.getString(SEARCH_TERM);
            return new BandListLoader(MainActivity.this, searchTerm);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<List<BandShortInfo>>> loader, LoaderResult<List<BandShortInfo>> result) {
            bandsListAdapter.clear();
            if (result.isSuccessful) {
                bandsListAdapter.addAll(result.data);
                bandsListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(MainActivity.this, "Could not load data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<List<BandShortInfo>>> loader) {
        }
    };
}
