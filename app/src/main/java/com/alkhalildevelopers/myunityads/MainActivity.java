package com.alkhalildevelopers.myunityads;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.IUnityBannerListener;
import com.unity3d.services.banners.UnityBanners;

public class MainActivity extends AppCompatActivity {
    private String unityGameID = "3414885";
    private Boolean testMode = true;
    private String PlacementID_intersitial = "TestComplete";
    private String PlacementID_banner = "banner";
    private IUnityAdsListener unityAdsListener;
    private Button showInterstitialAdsBtn, showBannerAdsBtn;
    private View bannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInterstitialAdsBtn = findViewById(R.id.showAdsBtn);
        showBannerAdsBtn = findViewById(R.id.showBannerAdsBtn);

        IUnityAdsListener mUnityAdListener = new IUnityAdsListener() {
            @Override
            public void onUnityAdsReady(String s) {
                Toast.makeText(MainActivity.this, "Interstitial Ad Ready to Display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsStart(String s) {
                Toast.makeText(MainActivity.this, "Ad started plaing " , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {
                Toast.makeText(MainActivity.this, "interstitial Ad finished", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {
                Toast.makeText(MainActivity.this, unityAdsError.toString(), Toast.LENGTH_SHORT).show();
            }
        };



        IUnityBannerListener bannerListener = new IUnityBannerListener() {
            @Override
            public void onUnityBannerLoaded(String s, View view) {
                bannerView = view;
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).removeView(view);
                ((ViewGroup) findViewById(R.id.bannerAdLayout)).addView(view);
                UnityBanners.loadBanner(MainActivity.this,PlacementID_banner);
            }

            @Override
            public void onUnityBannerUnloaded(String s) {
                bannerView = null;
            }

            @Override
            public void onUnityBannerShow(String s) {

            }

            @Override
            public void onUnityBannerClick(String s) {

            }

            @Override
            public void onUnityBannerHide(String s) {

            }

            @Override
            public void onUnityBannerError(String s) {

            }
        };
        UnityBanners.setBannerListener(bannerListener);


        UnityAds.initialize(MainActivity.this, unityGameID, testMode);
        UnityAds.setListener(mUnityAdListener);

        showBannerAdsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    UnityBanners.loadBanner(MainActivity.this, "banner");

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        showInterstitialAdsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UnityAds.load(PlacementID_intersitial);
                DisplayInterstitialAd();
            }
        });


    }

    private void DisplayInterstitialAd() {
        if (UnityAds.isReady(PlacementID_intersitial)) {
            UnityAds.show(MainActivity.this, PlacementID_intersitial);
        } else {
            Toast.makeText(MainActivity.this, "Interstitial ad not loaded", Toast.LENGTH_SHORT).show();
        }
    }

}




