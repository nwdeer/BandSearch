package search.band.vilner.dmitry.bandsearch.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import search.band.vilner.dmitry.bandsearch.R;

public class BandDetailsActivity extends AppCompatActivity {

    public static final String BAND_ID = "band_id";
    private static final String TAG = BandDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.band_details_activity);
        String bandId = getIntent().getStringExtra(BAND_ID);
        Log.i(TAG, "onCreate: started with bandId " + bandId);
    }
}
