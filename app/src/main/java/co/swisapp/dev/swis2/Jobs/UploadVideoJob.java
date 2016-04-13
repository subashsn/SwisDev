package co.swisapp.dev.swis2.Jobs;

import com.birbit.android.jobqueue.Job;
import com.birbit.android.jobqueue.Params;
import com.birbit.android.jobqueue.RetryConstraint;

import org.json.JSONObject;

import java.net.HttpURLConnection;

import co.swisapp.dev.swis2.Helpers.UploadStage1;
import co.swisapp.dev.swis2.model.VideoItem;
import io.realm.Realm;

public class UploadVideoJob extends Job {
    public static final int PRIORITY =5;
    VideoItem vid;
    Realm realm = Realm.getDefaultInstance();

    public UploadVideoJob(VideoItem vid){
        super(new Params(PRIORITY).requireNetwork().persist());
        this.vid=vid;
    }

    @Override
    public void onAdded() {
        realm.beginTransaction();
        vid.status="UP-QUEUE";
        realm.commitTransaction();

    }

    @Override
    public void onRun() throws Throwable {
        if(vid.status=="UP-QUEUE" || vid.status=="UP-STAGE1"){
            if(vid.status=="UP-QUEUE"){
                realm.beginTransaction();
                vid.status="UP-STAGE1";
                realm.commitTransaction();
            }
            String response= new UploadStage1().getResponse(vid);
            if (response=="success"){
                
            }
        }
    }

    @Override
    protected void onCancel(int cancelReason) {
        realm.beginTransaction();
        vid.status="UP-CANCEL";
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