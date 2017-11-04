package search.band.vilner.dmitry.bandsearch.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import search.band.vilner.dmitry.bandsearch.network.model.BandDetailsData;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

public interface BandServiceApi {
    @GET("/search/{search_type}/{keyword}")
    Call<ResponseData<SearchData>> search(@Path("search_type") String searchType, @Path("keyword") String keyword);

    @GET("/band/{band_id}")
    Call<ResponseData<BandDetailsData>> getById(@Path("band_id") String bandId);

}
