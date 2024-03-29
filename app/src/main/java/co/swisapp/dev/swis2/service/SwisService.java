package co.swisapp.dev.swis2.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import co.swisapp.dev.swis2.Helpers.UploadStage1;
import co.swisapp.dev.swis2.Jobs.UploadVideoJob;
import co.swisapp.dev.swis2.events.TestMessageEvent;
import co.swisapp.dev.swis2.model.VideoItem;

public class SwisService extends Service {
    private final String TAG = "SWIS_TEST:";
    JobManager jobManager;
    public SwisService() {
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        jobManager = new JobManager(new Configuration.Builder(this).build());
        EventBus.getDefault().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStart");
        return START_STICKY;
    }

    @Subscribe
    public void onVideoUpload(VideoItem vid){
        Log.i(TAG,"Video Uploading");
        jobManager.addJobInBackground(new UploadVideoJob("yolo"));
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Log.i(TAG,"onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
