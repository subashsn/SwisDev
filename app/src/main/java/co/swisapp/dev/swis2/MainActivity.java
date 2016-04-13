package co.swisapp.dev.swis2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;

import co.swisapp.dev.swis2.events.TestMessageEvent;
import co.swisapp.dev.swis2.fragment.ProfileFragment;
import co.swisapp.dev.swis2.fragment.UploadPageFragment;
import co.swisapp.dev.swis2.service.SwisService;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{
    private String TAG="SWIS_TEST";
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

        viewPager.addOnPageChangeListener(this);

        FragmentPagerAdapter fragAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public android.app.Fragment getItem(int position) {
                switch (position){
                    case 0:
                        return new UploadPageFragment();
                    case 1:
                        return new ProfileFragment();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };

        viewPager.setAdapter(fragAdapter);
    }

    private void initialize(){
        viewPager = (ViewPager) findViewById(R.id.mainPager);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).name("swistest.realm").build();
        Realm.setDefaultConfiguration(realmConfig);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {

    }
}
