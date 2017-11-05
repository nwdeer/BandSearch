package search.band.vilner.dmitry.bandsearch.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;

import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;

abstract class BandLoaderBase<T> extends AsyncTaskLoader<LoaderResult<T>> {
    private static final String TAG = BandLoaderBase.class.getSimpleName();
    private LoaderResult<T> result;

    public BandLoaderBase(Context context) {
        super(context);
    }

    @Override
    public LoaderResult<T> loadInBackground() {
        try {
            ResponseData<T> responseData = loadData();
            if (responseData.code == HttpURLConnection.HTTP_OK) {
                result = new LoaderResult<>(responseData.data);
            } else {
                result = new LoaderResult<>(responseData.code);
            }
        } catch (IOException e) {
            Log.e(TAG, "loadInBackground: finished with exception ", e);
            result = new LoaderResult<>(e);
        }
        return result;
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

    abstract ResponseData<T> loadData() throws IOException;
}
