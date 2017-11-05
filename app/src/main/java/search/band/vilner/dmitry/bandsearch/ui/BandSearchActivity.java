package search.band.vilner.dmitry.bandsearch.ui;

import android.app.LoaderManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
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
import search.band.vilner.dmitry.bandsearch.provider.RecentSearchContentProvider;

public class BandSearchActivity extends AppCompatActivity {

    private static final String TAG = BandSearchActivity.class.getSimpleName();
    public static final String SEARCH_TERM = "SearchTerm";
    private static final String LAST_SEARCH_EXTRA = "last_search";
    private BandsListAdapter bandsListAdapter;
    private static final int BAND_SEARCH_LOADER_ID = 1;
    private String searchTerm;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchView.setQuery(query, false);
            doSearch(query);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    RecentSearchContentProvider.AUTHORITY, RecentSearchContentProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(LAST_SEARCH_EXTRA, searchTerm);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.band_search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;

    }

    private void doSearch(String searchTerm) {
        bandsListAdapter.clear();
        bandsListAdapter.notifyDataSetChanged();
        BandSearchActivity.this.searchTerm = searchTerm;
        getLoaderManager().restartLoader(BAND_SEARCH_LOADER_ID, null, callbacks);
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
