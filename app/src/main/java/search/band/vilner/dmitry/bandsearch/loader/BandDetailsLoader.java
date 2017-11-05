package search.band.vilner.dmitry.bandsearch.loader;

import android.content.Context;

import java.io.IOException;

import search.band.vilner.dmitry.bandsearch.network.BandService;
import search.band.vilner.dmitry.bandsearch.network.model.BandDetailsData;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;

public class BandDetailsLoader extends BandLoaderBase<BandDetailsData> {
    private String TAG = BandDetailsLoader.class.getSimpleName();
    private String bandId;
    private LoaderResult<BandDetailsData> result;

    public BandDetailsLoader(Context context, String bandId) {
        super(context);
        this.bandId = bandId;
    }

    @Override
    ResponseData<BandDetailsData> loadData() throws IOException {
        return BandService.getInstance().getBandById(bandId);
    }
}
