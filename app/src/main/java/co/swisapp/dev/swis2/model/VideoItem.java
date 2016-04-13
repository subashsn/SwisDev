package co.swisapp.dev.swis2.model;

import java.util.Date;

import io.realm.RealmObject;

public class VideoItem extends RealmObject {
    public String video_id;
    public String status;
    public boolean savedLocal;
    public String internalLocation;
    public String localLocation;
    public int duration;
    public long views;
    public Date timeUploaded;
    public Date timeRecorded;
    public double gpsX,gpsY;
    public String s3_policy;
    public String s3_sign;
    public String s3_cred;
    public String s3_key;
    public String s3_date;
    public String test_key;

    public VideoItem(){

    }

    public VideoItem(String intLoc,double gpsLoc[],int dura,boolean isLocal,String locLoc){
        timeRecorded =new Date();
        internalLocation = intLoc;
        gpsX = gpsLoc[0];
        gpsY = gpsLoc[1];
        duration = dura;
        status = "UP-WAIT";
        savedLocal = isLocal;
        if (savedLocal){
            localLocation =locLoc;
        }
    }
}
