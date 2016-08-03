package com.tremorvideo.sdk.tremorvideodemo;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
 * This Activity demonstrates the "Show Immediately" ad flow. The application can make an ad request,
 * wait for the ad to be ready and show the ad as soon as an ad is ready.
 *  - The application makes an ad request.
 *  - The application shows a loading screen while waiting for ad response
 *  - As soon as an ad is ready, the application receives the adReady callback.
 *  - If an ad is ready, the application calls showAd to display the ad
 *
 *  For more details about SDK API and Integration, please visit
 *  https://github.com/TremorVideoMobile/TremorVideo-Android-SDK
 */
public class ShowImmediatelyActivity extends AppCompatActivity implements TremorAdStateListener {

    private static final int PERMISSION_REQUEST_CODE = 0x1111;
    private Activity mActivity;
    private Context mContext;
    private TextView mTextView;
    private ScrollView mScrollView;
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_immediately);
        Intent intent = getIntent();
        String title = intent.getStringExtra(TremorListActivity.TITLE);
        setTitle(title);
        mActivity = this;
        mContext = getApplicationContext();

        mTextView = (TextView) findViewById(R.id.logMessageTextView);
        String siteID = intent.getStringExtra(TremorListActivity.SITE_ID);

        /** initialize TremorVideo */
        TremorVideo.initialize(this, siteID);

        // Set information about our content and user
        Settings settings = new Settings();
        settings.userAge = 27;
        settings.userEducation = Settings.Education.CollegeBachelor;
        settings.userZip = "10001";
        settings.maxAdTimeSeconds = 60;
        settings.userGender = Settings.Gender.Male;
        TremorVideo.updateSettings(settings);

        /** Set the AdStateListener so that this class can listen for TremorVideo events */
        TremorVideo.setAdStateListener(this);

        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        final Button loadAndShowAdButton = (Button) findViewById(R.id.loadAndShowAd);
        if(loadAndShowAdButton != null) {
            loadAndShowAdButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        if (TremorVideo.isInitialized()) {
                            showLoadingScreen();

                            /** To request an ad, call loadAd, then wait for adReady callback */
                            TremorVideo.loadAd();
                        }
                    } catch (Exception e) {
                        /** This exception should be always be handled
                         * In cases where an error happens during a loadAd request then adReady will not be made
                         */
                        hideLoadingScreen(); // reset the app state
                        mTextView.append("Exception loading Ad: " + e.getMessage() );
                        e.printStackTrace();
                    }
                }
            });
        }

        /** request runtime permission for API 23
         * If the TargetSDK and device api level is 23 or greater then required permissions need to be requested at runtime.
         * To avoid dialogs being spawned at an arbitrary times these permissions should be made before any ad requests.
         * Run time permissons can be requested with the following code
         * For more information see: http://developer.android.com/training/permissions/requesting.html
         */
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

    public void showLoadingScreen() {
        if( mLoadingDialog == null) {
            mLoadingDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
            mLoadingDialog.setContentView(R.layout.loading_screen);
        }
        if(!mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }
    public void hideLoadingScreen(){
        if(mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.hide();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
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
     * After the ad has started, this TremorAdStateListener method is called
     */
    @Override
    public void adStart() {
        mTextView.append("Ad Started\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
    }

    /**
     * This callback is made after TremorVideo SDK has finished with a showAd request.
     * It can be used to resume publisher content
     * See ShowBAckToBackActivity for an example of how it can be used to show another ad right away
     */
    @Override
    public void adComplete(boolean b, int i) {
        mTextView.append("Ad Completed\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
        hideLoadingScreen();
    }

    /**
     * This callback is made after the TremorVideo SDK has finished with a loadAd request
     * In this case we are using it to show an Ad as soon as its ready
     * If the ad fails to download, b will equal false
     */
    @Override
    public void adReady(boolean b) {
        mTextView.append("Ad Ready: " + b + "\n");
        mScrollView.fullScroll(View.FOCUS_DOWN);
        if(b) {
            try {
                TremorVideo.showAd(this, RESULT_OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hideLoadingScreen();
        }
    }

    /**
     * If the user skips an ad, this TremorAdStateListener method is called
     */
    @Override
    public void adSkipped() {
        mTextView.append("Ad Skipped\n");
        hideLoadingScreen();
    }

    @Override
    public void adClickThru() {

    }

    @Override
    public void adImpression() {

    }

    @Override
    public void adVideoStart(int i) {
        hideLoadingScreen();
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
}
