package search.band.vilner.dmitry.bandsearch.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface BandServiceApi {
    @GET("/search/{search_type}/{keyword}")
    Call<SearchResult> search(@Path("search_type") String searchType, @Path("keyword") String keyword);

}
