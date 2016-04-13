package co.swisapp.dev.swis2.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import co.swisapp.dev.swis2.R;
import co.swisapp.dev.swis2.events.TestMessageEvent;
import co.swisapp.dev.swis2.model.VideoItem;
import co.swisapp.dev.swis2.service.SwisService;
import io.realm.Realm;

public class UploadPageFragment extends android.app.Fragment implements View.OnClickListener{
    String TAG="SWIS_TEST:";

    private Button buttonUpload;
    private Button buttonStartService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return inflater.inflate(R.layout.fragment_upload,container,false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);
        buttonStartService.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        Log.i(TAG,"listenersSet");
    }

    private void initialize(View view) {
        buttonUpload = (Button) view.findViewById(R.id.buttonUpload);
        buttonStartService = (Button) view.findViewById(R.id.buttonStartService);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonStartService:
                onButtonStartService(v);
                break;
            case R.id.buttonUpload:
                onButtonUpload(v);
                break;
        }
    }

    public void onButtonUpload(View view) {
        Log.i(TAG,"UploadButton");
        double gps[] = {1,2.00};
        VideoItem vid = new VideoItem("/root",gps,10,false,"");
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(vid);
        realm.commitTransaction();
        EventBus.getDefault().post(vid);
    }

    @Subscribe
    public void onMessageEvent(TestMessageEvent tme){
        Log.i(TAG,tme.getMessage());
        Toast.makeText(getActivity(),tme.message,Toast.LENGTH_SHORT).show();
    }

    public void onButtonStartService(View view){
        view.getContext().startService(new Intent(view.getContext(), SwisService.class));
    }
}
