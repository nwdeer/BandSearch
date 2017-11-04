package search.band.vilner.dmitry.bandsearch.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import search.band.vilner.dmitry.bandsearch.BuildConfig;
import search.band.vilner.dmitry.bandsearch.network.model.BandDetailsData;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;
import search.band.vilner.dmitry.bandsearch.network.model.SearchData;

public class BandService {
    private static final String API_KEY_VALUE = "61a82fc3-9e19-477a-b6d6-23471afd0e35";
    private static final String API_KEY_PARAMETER = "api_key";
    //hardcoded for now
    private static final String BAND_NAME_PARAMETER = "band_name";
    private BandServiceApi serviceApi;

    //Lazy Singleton implementation
    private static class LazyHolder {
        private static final BandService INSTANCE = new BandService();
    }

    public static BandService getInstance() {
        return LazyHolder.INSTANCE;
    }

    private BandService() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        //It is useful to see whole information while debugging.
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(loggingInterceptor);
        }
        //Add api key via an interceptor
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl url = request.url().newBuilder().addQueryParameter(API_KEY_PARAMETER, API_KEY_VALUE).build();
                request = request.newBuilder().url(url).build();
                return chain.proceed(request);
            }
        });

        serviceApi = new Retrofit.Builder()
                .client(clientBuilder.build())
                .baseUrl("http://em.wemakesites.net/")
                .addConverterFactory(new BandConverterFactory())
                .build()
                .create(BandServiceApi.class);
    }

    public ResponseData<SearchData> searchByBandName(String searchTerm) throws IOException {
        Call<ResponseData<SearchData>> search = serviceApi.search(BAND_NAME_PARAMETER, searchTerm);
        retrofit2.Response<ResponseData<SearchData>> execute = search.execute();
        return execute.body();
    }

    public ResponseData<BandDetailsData> getBandById(String id) throws IOException {
        Call<ResponseData<BandDetailsData>> getById = serviceApi.getById(id);
        retrofit2.Response<ResponseData<BandDetailsData>> execute = getById.execute();
        return execute.body();
     }

}
