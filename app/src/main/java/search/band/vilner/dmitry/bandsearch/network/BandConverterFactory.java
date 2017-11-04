package search.band.vilner.dmitry.bandsearch.network;

import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import search.band.vilner.dmitry.bandsearch.network.model.ResponseData;

public class BandConverterFactory extends Converter.Factory {
    private static final MediaType REQUEST_MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    private static final Gson gson =  new GsonBuilder()
            .registerTypeAdapter(ResponseData.class, new GsonTypeAdapter<ResponseData>()).create();

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, Object>() {
            @Override
            public Object convert(ResponseBody value) throws IOException {
                JsonReader jsonReader = new JsonReader(value.charStream());
                try {
                    return gson.getAdapter(TypeToken.get(type)).read(jsonReader);
                } finally {
                    jsonReader.close();
                    value.close();
                }
            }
        };
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new RequestBodyConverter<>(gson.getAdapter(TypeToken.get(type)));
    }

    /**
     * Helper class to allow the wildcard be captured through type inference.
     *
     * See https://docs.oracle.com/javase/tutorial/java/generics/capture.html
     *
     * @param <T> Type to infer from the wildcard
     */
    private static class RequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final TypeAdapter<T> adapter;

        RequestBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(REQUEST_MEDIA_TYPE, adapter.toJson(value));
        }
    }
}
