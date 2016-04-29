package co.swisapp.dev.swis2.Jobs;

import android.util.Log;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import co.swisapp.dev.swis2.Helpers.UploadStage1;
import co.swisapp.dev.swis2.model.VideoItem;
import io.realm.Realm;

public class UploadVideoJob extends Job {
    String TAG = "SWIS_TEST";
    public static final int PRIORITY =1;
    String  vid;
    Realm realm;

    public UploadVideoJob(String vid){
        super(new Params(PRIORITY).persist());
        this.vid=vid;
    }

    @Override
    public void onAdded() {
        realm = Realm.getDefaultInstance();
        Log.d(TAG,"onAdded");
        realm.beginTransaction();
       /* vid.status="UP-QUEUE";*/
        realm.commitTransaction();
    }

    @Override
    public void onRun() throws Throwable {
        realm = Realm.getDefaultInstance();
        Log.d(TAG,"onRun");
/*        if(vid.status=="UP-QUEUE" || vid.status=="UP-STAGE1"){
            if(vid.status=="UP-QUEUE"){
                realm.beginTransaction();
                vid.status="UP-STAGE1";
                realm.commitTransaction();
            }
            String response= new UploadStage1().getResponse(vid);
            if (response=="success"){

            }
        }*/
    }

    @Override
    protected void onCancel(int cancelReason) {
        realm.beginTransaction();
        /*vid.status="UP-CANCEL";*/
        realm.commitTransaction();
    }

    @Override
    protected int getRetryLimit() {
        return 5;
    }

    @Override
    protected RetryConstraint shouldReRunOnThrowable(Throwable throwable, int runCount, int maxRunCount) {
        return RetryConstraint.createExponentialBackoff(runCount, 1000);
    }
}