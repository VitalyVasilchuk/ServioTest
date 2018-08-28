package apps.basilisk.serviotest.api;

import java.util.concurrent.TimeUnit;

import apps.basilisk.serviotest.BuildConfig;
import apps.basilisk.serviotest.model.Places;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public interface ServioService {
    String TAG = "ServioService";
    String ENDPOINT = "http://sms.servio.support:32892/";

    @POST("GetPlaces")
    Call<Places> getPlaces();

    class Factory {
        private static ServioService create() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor).build();
            }

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ServioService.ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            return retrofit.create(ServioService.class);
        }

        public static Call<Places> getPlaces() {
            return create().getPlaces();
        }
    }

}
