package co.swisapp.dev.swis2.Helpers;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import co.swisapp.dev.swis2.model.VideoItem;
import io.realm.Realm;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadStage1 {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String jsonMaker(VideoItem vid){
        return "{'winCondition':" + vid.timeRecorded + ","
                + "'duration':"+ vid.duration + ","
                + "'gps':[" + vid.gpsX + "," + vid.gpsY + "]}";
    }

    public String getResponse(VideoItem vid) throws IOException, JSONException {
        String json = jsonMaker(vid);
        JSONObject response = new JSONObject(this.post("https://swis-vuln.c9users.io/",json));
        if (response.getString("status")=="success") {
            Realm realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            vid.status="UP-STAGE2";
            vid.test_key = response.getString("key");
            realm.commitTransaction();
        }
        return response.getString("status");
    }
}