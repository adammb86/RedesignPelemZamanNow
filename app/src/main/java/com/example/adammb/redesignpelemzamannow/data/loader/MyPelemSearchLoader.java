package com.example.adammb.redesignpelemzamannow.data.loader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.adammb.redesignpelemzamannow.BuildConfig;
import com.example.adammb.redesignpelemzamannow.R;
import com.example.adammb.redesignpelemzamannow.data.model.Pelem;
import com.example.adammb.redesignpelemzamannow.screen.main.MainActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import cz.msebera.android.httpclient.Header;

public class MyPelemSearchLoader extends AsyncTaskLoader<LinkedList<Pelem>> {
    private LinkedList<Pelem> listPelem;
    private boolean hasResult = false;
    private Pelem pelem;
    private static final String TAG = MainActivity.class.getSimpleName();
    private String originalTitle;

    public MyPelemSearchLoader(@NonNull Context context, String originalTitle) {
        super(context);

        onContentChanged();
        this.originalTitle = originalTitle;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(listPelem);
    }

    @Override
    public void deliverResult(@Nullable LinkedList<Pelem> data) {
        listPelem = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            listPelem = null;
            hasResult = false;
        }
    }

    @Nullable
    @Override
    public LinkedList<Pelem> loadInBackground() {
        final LinkedList<Pelem> listPelem = new LinkedList<>();

        SyncHttpClient client = new SyncHttpClient();

        if ((originalTitle == null) || (originalTitle.equals(""))) {
            String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.TMDB_KEY + "" +
                    "&language=" + getContext().getString(R.string.language) + "en-US&page=2";

            Log.e(TAG, "getFilm " + url);
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();

                    setUseSynchronousMode(true);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String hasil = new String(responseBody);
                    Log.d(TAG, hasil);

                    try {
                        JSONObject responseObject = new JSONObject(hasil);
                        JSONArray responseResult = responseObject.getJSONArray("results");
                        for (int i = 0; i < responseResult.length(); i++) {
                            JSONObject objectResult = responseResult.getJSONObject(i);
                            pelem = new Pelem();
                            pelem.setId(objectResult.getInt("id"));
                            pelem.setOriginal_title(objectResult.getString("original_title"));
                            pelem.setOverview(objectResult.getString("overview"));
                            pelem.setRelease_date(objectResult.getString("release_date"));
                            pelem.setPoster_path("http://image.tmdb.org/t/p/w185" + objectResult.getString("poster_path"));
                            pelem.setVote_average(objectResult.getDouble("vote_average"));
                            pelem.setVote_count(objectResult.getInt("vote_count"));
                            listPelem.add(pelem);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, "on Failure");
                }
            });
        } else {
            String url = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.TMDB_KEY + "" +
                    "&language=" + getContext().getString(R.string.language) + "&query=" + originalTitle;

            Log.e(TAG, "getFilm " + url);
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();

                    setUseSynchronousMode(true);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String hasil = new String(responseBody);
                    Log.d(TAG, hasil);

                    try {
                        JSONObject responseObject = new JSONObject(hasil);
                        JSONArray responseResult = responseObject.getJSONArray("results");
                        for (int i = 0; i < responseResult.length(); i++) {
                            JSONObject objectResult = responseResult.getJSONObject(i);
                            pelem = new Pelem();
                            pelem.setId(objectResult.getInt("id"));
                            pelem.setOriginal_title(objectResult.getString("original_title"));
                            pelem.setOverview(objectResult.getString("overview"));
                            pelem.setRelease_date(objectResult.getString("release_date"));
                            pelem.setPoster_path("http://image.tmdb.org/t/p/w185" + objectResult.getString("poster_path"));
                            pelem.setVote_average(objectResult.getDouble("vote_average"));
                            pelem.setVote_count(objectResult.getInt("vote_count"));
                            listPelem.add(pelem);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e(TAG, "on Failure");
                }
            });
        }

        return listPelem;
    }
}
