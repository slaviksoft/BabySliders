package com.slaviksoft.games.babysliders.android;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.slaviksoft.games.babysliders.BabySlidersGame;

import custom.IAdRequestHandler;

public class AndroidLauncher extends AndroidApplication implements IAdRequestHandler{

	private static final String AD_UNIT_ID = "ca-app-pub-1789345590331746/4925156116";

    private final int SHOW_ADS = 1;
    private final int HIDE_ADS = 0;

	protected AdView adView;
	protected View gameView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		try {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
		} catch (Exception ex) {
			log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
		}
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) config.useImmersiveMode = true;
		config.useWakelock = true;

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

        gameView = createGameView(config);
        layout.addView(gameView);

        adView = createAdView();
        layout.addView(adView);

		setContentView(layout);

		//startAdvertising(adView);
	}

	private AdView createAdView() {
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		adView.setId(View.NO_ID); // this is an arbitrary id, allows for relative positioning in createGameView()

        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);


		adView.setLayoutParams(adParams);
		adView.setBackgroundColor(Color.parseColor("#003f9368"));
		return adView;
	}

	private View createGameView(AndroidApplicationConfiguration cfg) {
		gameView = initializeForView(new BabySlidersGame(this), cfg);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        gameView.setLayoutParams(params);
		return gameView;
	}

	private void startAdvertising(AdView adView) {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.build();
		adView.loadAd(adRequest);
	}

    protected Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case SHOW_ADS:
                {
                    adView.setVisibility(View.VISIBLE);
                    adView.resume();
					startAdvertising(adView);
                    break;
                }
                case HIDE_ADS:
                {
                    adView.setVisibility(View.GONE);
                    adView.pause();
                    break;
                }
            }
        }
    };

    @Override
    public void showAds(boolean show) {
        handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
    }
}
