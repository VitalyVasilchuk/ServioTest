package apps.basilisk.serviotest;

import android.util.Log;
import android.util.Pair;

import java.io.IOException;
import java.util.Observable;

import apps.basilisk.serviotest.api.ServioService;
import apps.basilisk.serviotest.model.Places;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataLoader extends Observable {
    private static final String TAG = "DataLoader";
    private static DataLoader instance;

    // объявление приватного конструктора запрещает создание экземпляра класса из вне
    private DataLoader() {
    }

    public static DataLoader getInstance() {
        if (instance == null) instance = new DataLoader();
        return instance;
    }

    private void pushResult(String keyName, Object object) {
        setChanged();
        notifyObservers(new Pair<>("RESULT_" + keyName, object));
    }

    private void pushError(String keyName, Response response) {
        String message = "error #" + response.code() + ": " + response.message(); // HTTP error
        try {
            String errorString = response.errorBody().string();
            if (errorString != null && !errorString.isEmpty()) {
                message += "\n(" + errorString + "))"; // API error
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers(new Pair<>("ERROR_" + keyName, message));
    }

    private void pushFailure(String keyName, String message) {
        setChanged();
        notifyObservers(new Pair<>("ERROR_" + keyName, message));
    }

    public void loadPlaces() {
        final String keyName = "PLACES";

        Call<Places> call = ServioService.Factory.getPlaces();
        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                if (response.isSuccessful()) {
                    pushResult(keyName, response.body());
                } else {
                    String message = "error #" + response.code() + ": " + response.message(); // HTTP error
                    pushError(keyName, response);
                }
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                pushFailure(keyName, "loadPlaces().onFailure: " + t.getMessage());
                if (BuildConfig.DEBUG) Log.d(TAG, "loadPlaces().onFailure: " + t.getMessage());
            }
        });
    }

}
