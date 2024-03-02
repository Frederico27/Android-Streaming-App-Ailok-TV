package co.smallcademy.livetvapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import co.smallcademy.livetvapp.adapters.CategoryYTAdapter;
import co.smallcademy.livetvapp.models.CatYoutube;
import co.smallcademy.livetvapp.models.Channel;

public class Youtube_Web extends AppCompatActivity {

    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yt_webview);

        CatYoutube link = (CatYoutube) getIntent().getSerializableExtra("catYT");
        String videoUrl = link.getLive_url();

        //Orentasi secara default

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        // inisialisasi WebView
        webView = findViewById(R.id.webview);

        // atur pengaturan WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);


        // atur klien WebView
        webView.setWebViewClient(new WebViewClient() {

            // memblokir navigasi ke halaman lain (seperti halaman web lain)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });

        // muat URL video YouTube ke dalam WebView
        webView.loadUrl("https://www.youtube.com/embed/" + getVideoIdFromUrl(videoUrl));
    }

    // fungsi untuk mendapatkan ID video dari URL video YouTube
    private String getVideoIdFromUrl(String url) {
        String videoId = null;

        if (url != null && url.trim().length() > 0 && url.startsWith("https://www.youtube.com/watch")) {
            String[] urlParts = url.split("v=");
            if (urlParts.length > 1) {
                videoId = urlParts[1];
                int ampersandIndex = videoId.indexOf("&");
                if (ampersandIndex != -1) {
                    videoId = videoId.substring(0, ampersandIndex);
                }
            }
        }

        return videoId;
    }

    // saat tombol kembali ditekan, kembali ke aktivitas sebelumnya
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
