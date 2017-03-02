package com.tremorvideo.sdk.tremorvideodemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tremorvideo.sdk.android.videoad.Settings;
import com.tremorvideo.sdk.android.videoad.TremorAdStateListener;
import com.tremorvideo.sdk.android.videoad.TremorVideo;

/**
 * This Activity demonstrates the "Preload in Background" ad flow. Preload ad allows the application
 * to pre-fetech an ad in the background and the ad will be instantly played when showAd is called.
 *  - The application makes an ad request by calling loadAd.
 *  - When the application is ready to show an Ad, check if an ad is ready by using isAdReady method
 *  - If an ad is ready, the application can call showAd to show the ad
 *
 *  For more details about SDK API and Integration, please visit
 *  https://github.com/TremorVideoMobile/TremorVideo-Android-SDK
 */
public class PreloadActivity extends AppCompatActivity implements TremorAdStateListener {

    private static final int PERMISSION_REQUEST_CODE = 0x1111;
    private Activity mActivity;
    private Context mContext;
    private TextView mTextView;
    private Button mShowAdButton;
    private ScrollView mScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);

        Intent intent = getIntent();
        String title = intent.getStringExtra(TremorListActivity.TITLE);
        setTitle(title);
        mActivity = this;
        mContext = getApplicationContext();

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mTextView = (TextView) findViewById(R.id.logMessageTextView);
        String siteID = intent.getStringExtra(TremorListActivity.SITE_ID);
        TremorVideo.initialize(this, siteID);

        // Set information about our content and user
        Settings settings = new Settings();
        settings.userAge = 27;
        settings.userEducation = Settings.Education.CollegeBachelor;
        settings.userZip = "10001";
        settings.maxAdTimeSeconds = 60;
        settings.userGender = Settings.Gender.Male;
        TremorVideo.updateSettings(settings);

        TremorVideo.setAdStateListener(this);

        final Button loadAdButton = (Button) findViewById(R.id.loadAd);
        if(loadAdButton != null) {
            loadAdButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        if (TremorVideo.isInitialized()) {
                            /** To request an ad, call loadAd */
                            TremorVideo.loadAd();
                            mTextView.append("Loading\n");
                            mScrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        mShowAdButton = (Button) findViewById(R.id.showAd);
        if(mShowAdButton != null) {
            mShowAdButton.setEnabled(false);
            mShowAdButton.setBackgroundColor(Color.GRAY);
            mShowAdButton.setTextColor(Color.parseColor("#666666"));


            mShowAdButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                if (TremorVideo.isAdReady()) {
                    try {
                        /** Call showAd any time after adReady(true) has been called */
                        TremorVideo.showAd(PreloadActivity.this, RESULT_OK);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                }
            });
        }

        /** request runtime permission for API 23
         * If the TargetSDK and device api level is 23 or greater then required permissions need to be requested at runtime.
         * To avoid dialogs being spawned at an arbitrary times these permissions should be made before any ad requests.
         * Run time permissons can be requested with the following code
         * For more information see: http://developer.android.com/training/permissions/requesting.html
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // End the current user session while shutting down the static instance of TremorVideo
        TremorVideo.destroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(){
        String[] _Permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        for(String permission : _Permissions){
            if(mContext.checkCallingOrSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                mActivity.requestPermissions(new String[]{permission}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void sdkInitialized() {

    }

    /**
     * This call back is made when an ad is fully loaded and about to start rendering to screen
     */
    @Override
    public void adStart() {
        mTextView.append("Ad Started\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    /**
     * This callback is made after the TremorVideo SDK has finished with a showAd request.
     * It can be used to resume publisher content
     * See ShowBAckToBackActivity for an example of how it can be used to show another ad right away
     */
   @Override
    public void adComplete(boolean b, int i) {
        mTextView.append("Ad Completed\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }


    /**
     * This callback is made after the TremorVideo SDK has finished with a loadAd request
     * See the ShowImmediatelyActivity example for how this can be used to show an Ad as soon as its ready.
     * If the ad fails to download, b will equal false
     */
     @Override
    public void adReady(boolean b) {
        mShowAdButton.setEnabled(true);
        mShowAdButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mShowAdButton.setTextColor(Color.BLACK);
        mTextView.append("Ad Ready: " + b + "\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    /**
     * If the user skips an ad, this TremorAdStateListener method is called
     */
    @Override
    public void adSkipped() {

        mTextView.append("Ad Skipped\n");
    }

    @Override
    public void adClickThru() {

    }

    @Override
    public void adImpression() {

    }

    @Override
    public void adVideoStart(int i) {

    }

    @Override
    public void adVideoFirstQuartile(int i) {

    }

    @Override
    public void adVideoMidPoint(int i) {

    }

    @Override
    public void adVideoThirdQuartile(int i) {

    }

    @Override
    public void adVideoComplete(int i) {

    }

    @Override
    public void leftApp() {

    }

    @Override
    public void sdkDestroyed() {

    }
}
