package search.band.vilner.dmitry.bandsearch.ui;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import search.band.vilner.dmitry.bandsearch.R;
import search.band.vilner.dmitry.bandsearch.loader.BandListLoader;
import search.band.vilner.dmitry.bandsearch.loader.LoaderResult;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

public class BandSearchActivity extends AppCompatActivity {

    private static final String TAG = BandSearchActivity.class.getSimpleName();
    public static final String SEARCH_TERM = "SearchTerm";
    private static final String LAST_SEARCH_EXTRA = "last_search";
    private BandsListAdapter bandsListAdapter;
    private static final int BAND_SEARCH_LOADER_ID = 1;
    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SearchView searchView = findViewById(R.id.search_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.i(TAG, "onQueryTextSubmit: " + s);
                bandsListAdapter.clear();
                bandsListAdapter.notifyDataSetChanged();
                BandSearchActivity.this.searchTerm = s;
                getLoaderManager().restartLoader(BAND_SEARCH_LOADER_ID, null, callbacks);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BandShortInfo bandShortInfo = bandsListAdapter.getItem(position);
                Intent intent = new Intent(BandSearchActivity.this, BandDetailsActivity.class);
                intent.putExtra(BandDetailsActivity.BAND_ID, bandShortInfo.id);
                intent.putExtra(BandDetailsActivity.BAND_NAME, bandShortInfo.name);
                startActivity(intent);
            }
        });

        if (savedInstanceState != null) {
            String lastSearch = savedInstanceState.getString(LAST_SEARCH_EXTRA);
            searchTerm = lastSearch;
            if (!TextUtils.isEmpty(lastSearch)) {
                getLoaderManager().restartLoader(BAND_SEARCH_LOADER_ID, null, callbacks);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(LAST_SEARCH_EXTRA, searchTerm);
        super.onSaveInstanceState(outState);
    }

    private LoaderManager.LoaderCallbacks<LoaderResult<SearchData>> callbacks = new LoaderManager.LoaderCallbacks<LoaderResult<SearchData>>() {
        @Override
        public Loader<LoaderResult<SearchData>> onCreateLoader(int i, Bundle bundle) {
            return new BandListLoader(BandSearchActivity.this, searchTerm);
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<SearchData>> loader, LoaderResult<SearchData> result) {
            bandsListAdapter.clear();
            if (result.isSuccessful) {
                bandsListAdapter.addAll(result.data.search_results);
                bandsListAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(BandSearchActivity.this, "Could not load data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<SearchData>> loader) {
        }
    };
}
