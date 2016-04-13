package co.swisapp.dev.swis2.model;

/**
 * Created by sns on 4/12/16.
 */
public class GPSLocation {
    long x,y;

    public GPSLocation(long xy[]){
        this.x = xy[0];
        this.y = xy[1];
    }

}
