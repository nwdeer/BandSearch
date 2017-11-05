package search.band.vilner.dmitry.bandsearch.ui;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import search.band.vilner.dmitry.bandsearch.R;
import search.band.vilner.dmitry.bandsearch.loader.BandDetailsLoader;
import search.band.vilner.dmitry.bandsearch.loader.LoaderResult;
import search.band.vilner.dmitry.bandsearch.network.model.BandDetailsData;

public class BandDetailsActivity extends AppCompatActivity {

    public static final String BAND_ID = "band_id";
    public static final String BAND_NAME = "band_name";
    private static final int BAND_DETAILS_LOADER_ID = 1;
    private static final String TAG = BandDetailsActivity.class.getSimpleName();
    private String bandName;
    private TextView bandNameView;
    private TextView bandYearsView;
    private TextView bandGenreView;
    private TextView bandCountryView;
    private View progressBar;
    private AlbumListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_details_activity);
        String bandId = getIntent().getStringExtra(BAND_ID);
        bandName = getIntent().getStringExtra(BAND_NAME);
        Bundle bundle = new Bundle();
        bundle.putString(BAND_ID, bandId);
        getLoaderManager().restartLoader(BAND_DETAILS_LOADER_ID, bundle, callbacks);
        initViews();
    }

    private void initViews() {
        bandNameView = findViewById(R.id.band_name);
        bandYearsView = findViewById(R.id.years);
        bandGenreView = findViewById(R.id.genre);
        bandCountryView = findViewById(R.id.country);
        ListView albumsListView = findViewById(R.id.albums);
        adapter = new AlbumListAdapter(this, R.layout.album_list_item);
        albumsListView.setAdapter(adapter);
        progressBar = findViewById(R.id.progress_bar);
    }

    LoaderManager.LoaderCallbacks<LoaderResult<BandDetailsData>> callbacks = new LoaderManager.LoaderCallbacks<LoaderResult<BandDetailsData>>() {
        @Override
        public Loader<LoaderResult<BandDetailsData>> onCreateLoader(int id, Bundle args) {
            return new BandDetailsLoader(BandDetailsActivity.this, args.getString(BAND_ID));
        }

        @Override
        public void onLoadFinished(Loader<LoaderResult<BandDetailsData>> loader, LoaderResult<BandDetailsData> data) {
            if (data.isSuccessful) {
                bindBandData(data.data);
            } else {
                Toast.makeText(BandDetailsActivity.this, "Couldn't load data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<LoaderResult<BandDetailsData>> loader) {

        }
    };

    private void bindBandData(BandDetailsData data) {
        bandNameView.setText(bandName);
        bandCountryView.setText(data.details.countryOfOrigin);
        bandGenreView.setText(data.details.genre);
        bandYearsView.setText(data.details.yearsActive);
        adapter.clear();
        adapter.addAll(data.discography);
        progressBar.setVisibility(View.GONE);
    }
}
