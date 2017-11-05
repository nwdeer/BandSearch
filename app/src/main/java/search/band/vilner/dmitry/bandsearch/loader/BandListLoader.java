package search.band.vilner.dmitry.bandsearch.loader;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import search.band.vilner.dmitry.bandsearch.network.BandService;
import search.band.vilner.dmitry.bandsearch.network.model.BandShortInfo;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

public class BandListLoader extends BandLoaderBase<SearchData> {
    private String TAG = BandListLoader.class.getSimpleName();
    private String searchTerm;
    private LoaderResult<List<BandShortInfo>> result;

    public BandListLoader(Context context, String searchTerm) {
        super(context);
        this.searchTerm = searchTerm;
    }

    @Override
    ResponseData<SearchData> loadData() throws IOException {
        return BandService.getInstance().searchByBandName(searchTerm);
    }
}
