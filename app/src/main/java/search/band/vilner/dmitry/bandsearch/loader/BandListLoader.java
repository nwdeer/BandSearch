package search.band.vilner.dmitry.bandsearch.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import search.band.vilner.dmitry.bandsearch.network.BandService;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

public class BandListLoader extends AsyncTaskLoader <LoaderResult<List<BandShortInfo>>> {
    private String TAG = BandListLoader.class.getSimpleName();
    private String searchTerm;
    private LoaderResult<List<BandShortInfo>> result;

    public BandListLoader(Context context, String searchTerm) {
        super(context);
        this.searchTerm = searchTerm;
    }

    @Override
    public LoaderResult<List<BandShortInfo>> loadInBackground() {
        try {
            ResponseData<SearchData> searchDataResponseData = BandService.getInstance().searchByBandName(searchTerm);
            if (searchDataResponseData.code == HttpURLConnection.HTTP_OK) {
                result = new LoaderResult<>(searchDataResponseData.data.search_results);
            } else {
                result = new LoaderResult<>(searchDataResponseData.code);
            }
        } catch (IOException e) {
            Log.e(TAG, "loadInBackground: finished with exception ", e);
            result = new LoaderResult<>(e);
        }
        return result;
    }

    @Override
    protected LoaderResult<List<BandShortInfo>> onLoadInBackground() {
        return super.onLoadInBackground();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (result != null && result.isSuccessful) {
            deliverResult(result);
        } else {
            forceLoad();
        }
    }
}
