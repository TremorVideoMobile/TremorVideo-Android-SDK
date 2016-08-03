package com.tremorvideo.sdk.tremorvideodemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 *   The Tremor Video SDK shows several different ad formats at random.
 *   This demo allows a developer to test each format individually.
 *
 *   Additionally, there are two strategies for showing an ad.
 *   A developer can show the ad immediately after it's been downloaded,
 *   or some time in the near future. This is demonstrated in the Ad Flows
 *   section. Be advised that it's possible for an ad to expire before it's
 *   been shown. So don't request an ad too far in advance.
 */


public class TremorListActivity extends AppCompatActivity {

    ListView mAdFlowListView;
    TextView mAdFlowTextView;
    final static String TITLE = "title";
    final static String SITE_ID = "siteID";
    enum FlowType{ SHOW_BACKTOBACK, SHOW_IMMEDIATELY, PRELOAD };
    static class Ad {
        final String name;
        final String siteID;
        final FlowType flowType;
        Ad(String n, String id, FlowType f){
            name = n;
            siteID = id;
            flowType = f;
        }
    }

    static final String HEADER = "Ad Flow";
    static final Ad[] AD_FLOWS = {
            new Ad("Preload in Background", "test", FlowType.PRELOAD),
            new Ad("Show Immediately", "test", FlowType.SHOW_IMMEDIATELY),
            new Ad("Show Back To Back", "test", FlowType.SHOW_BACKTOBACK)
    };

    class AdArrayAdapter<String> extends ArrayAdapter {
        Ad[] ads;
        AdArrayAdapter(Context context, int resource, Ad[] objects){
            super(context, resource);
            ads = objects;
            for(Ad element : objects ){
                this.add(element.name);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tremor_list);
        mAdFlowListView = (ListView) findViewById(R.id.listView1);
        mAdFlowTextView = (TextView) findViewById(R.id.title1);
        mAdFlowTextView.setText(HEADER);
        mAdFlowListView.setAdapter(new AdArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AD_FLOWS));
        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                AdArrayAdapter associatedAdapter = (AdArrayAdapter) parent.getAdapter();
                Ad[] section = associatedAdapter.ads;

                if(FlowType.SHOW_IMMEDIATELY == section[position].flowType){
                    intent = new Intent(getApplicationContext(),ShowImmediatelyActivity.class);
                } else if(FlowType.SHOW_BACKTOBACK == section[position].flowType){
                    intent = new Intent(getApplicationContext(),ShowBackToBackActivity.class);
                } else {
                    intent = new Intent(getApplicationContext(),PreloadActivity.class);
                }
                intent.putExtra(TITLE,(String) section[position].name);
                intent.putExtra(SITE_ID,(String) section[position].siteID);
                startActivity(intent);
            }
        };
        mAdFlowListView.setOnItemClickListener(clickListener);
    }
}


