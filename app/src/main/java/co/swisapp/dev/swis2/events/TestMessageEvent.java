package co.swisapp.dev.swis2.events;

/**
 * Created by sns on 4/12/16.
 */
public class TestMessageEvent {
    public final String message;

    public TestMessageEvent(String msg){
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }
}
