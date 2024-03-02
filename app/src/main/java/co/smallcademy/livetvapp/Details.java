package co.smallcademy.livetvapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.LocalMediaDrmCallback;
import com.google.android.exoplayer2.drm.MediaDrmCallback;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.smallcademy.livetvapp.models.Channel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Details extends AppCompatActivity {
    PlayerView playerView;
    ImageView fbLink,twtLink,ytLink,webLink;
    TextView description;
    ImageView fullScreen;
    ImageView track, setResolusi;

    boolean isFullScreen = false;
    SimpleExoPlayer player;
    ProgressBar progressBar;
    boolean isShowingTrackSelectionDialog;
    DefaultTrackSelector trackSelector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Channel channel = (Channel) getIntent().getSerializableExtra("channel");
        getSupportActionBar().setTitle(channel.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        playerView = findViewById(R.id.playerView);
        fullScreen = playerView.findViewById(R.id.exo_fullscreen_icon);
        progressBar = findViewById(R.id.progressBar);
        track = playerView.findViewById(R.id.exo_track_selection_view);
        setResolusi = playerView.findViewById(R.id.exo_set);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);




        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] resolutions = {"Enxe Layar 16:9", "Enxe Layar leten", "Enxe Tomak"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Details.this);
                builder.setTitle("Hili Mode Video");
                builder.setItems(resolutions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // ubah resolusi
                        switch (i) {
                            case 0:
                                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                                break;
                            case 1:
                                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
                                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                                break;
                            case 2:
                                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();

            }
        });

        setResolusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowingTrackSelectionDialog
                        && TrackSelectionDialog.willHaveContent(trackSelector)) {
                    isShowingTrackSelectionDialog = true;
                    TrackSelectionDialog trackSelectionDialog =
                            TrackSelectionDialog.createForTrackSelector(
                                    trackSelector,
                                    /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                    trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);

                }


            }
        });



        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullScreen){

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().show();
                        playerView.setKeepScreenOn(true);
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = (int) ( 200 * getApplicationContext().getResources().getDisplayMetrics().density);
                    playerView.setLayoutParams(params);

//                    Toast.makeText(Details.this, "Ita agora iha Mode Full Screen.", Toast.LENGTH_SHORT).show();
                    isFullScreen = false;
                }else {

                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

                    if(getSupportActionBar() != null){
                        getSupportActionBar().hide();
                    }

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) playerView.getLayoutParams();
                    params.width = params.MATCH_PARENT;
                    params.height = params.MATCH_PARENT;
                    playerView.setLayoutParams(params);

                    Toast.makeText(Details.this, "Tama ba Mode Full Screen", Toast.LENGTH_SHORT).show();
                    isFullScreen = true;
                }
            }
        });


        fbLink = findViewById(R.id.facebookLink);
        twtLink = findViewById(R.id.twitterLink);
        ytLink = findViewById(R.id.youtubeLink);
        webLink = findViewById(R.id.websiteLink);

        description = findViewById(R.id.channelDesc);
        description.setText(channel.getDescription());

        fbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openLink(channel.getFacebook());
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(Details.this, "link facebook la eziste", Toast.LENGTH_SHORT).show();
                }
            }
        });


        twtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    openLink(channel.getTwitter());
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Details.this, "link twitter la eziste", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ytLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    openLink(channel.getYoutube());
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Details.this, "link youtube la eziste", Toast.LENGTH_SHORT).show();
                }
            }
        });

        webLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    openLink(channel.getWebsite());
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Details.this, "link website la eziste", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playChannel(channel.getLive_url());

    }

    public void openLink(String url){
        Intent open = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(open);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void playChannel(String live_url) {
        trackSelector = new DefaultTrackSelector(this);
        player = new SimpleExoPlayer.Builder(this).setTrackSelector(trackSelector).build();
        playerView = findViewById(R.id.playerView);
        playerView.setPlayer(player);

        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        MediaSource mediaSource;

        if (live_url.contains(".mpd") && !live_url.contains("transvision") && !live_url.contains("dazn")) {

            String license_url = "https://mrpw.ptmnc01.verspective.net/?deviceId=OTFmNDAwZWEtZjI5OC0zNTAzLWE0NzktZWI2NGIxMjRmMGFm";
            HttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSourceFactory();
            HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(license_url, httpDataSourceFactory);

            mediaSource = new DashMediaSource.Factory(dataSourceFactory)
                    .setDrmSessionManagerProvider(mediaItem -> {
                        return new DefaultDrmSessionManager.Builder()
                                .setPlayClearSamplesWithoutKeys(true)
                                .setMultiSession(false)
                                .setKeyRequestParameters(new HashMap<String, String>())
                                .setUuidAndExoMediaDrmProvider(C.WIDEVINE_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
                                .build(drmCallback);
                    })
                    .createMediaSource(MediaItem.fromUri(live_url));
        }

        else if (live_url.contains(".mpd") && live_url.contains("transvision")){

            String license_url = "https://cubmu.combipart1.workers.dev/";
            HttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSourceFactory();
            HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(license_url, httpDataSourceFactory);

            mediaSource = new DashMediaSource.Factory(dataSourceFactory)
                    .setDrmSessionManagerProvider(mediaItem -> {
                        return new DefaultDrmSessionManager.Builder()
                                .setPlayClearSamplesWithoutKeys(true)
                                .setMultiSession(false)
                                .setKeyRequestParameters(new HashMap<String, String>())
                                .setUuidAndExoMediaDrmProvider(C.WIDEVINE_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
                                .build(drmCallback);
                    })
                    .createMediaSource(MediaItem.fromUri(live_url));

        }


        else if (live_url.contains(".mpd") && live_url.contains("dazn")) {

            Uri videoURI = Uri.parse(live_url);
            String keyValue = "GxRPc+b+/pHNBfhQ4rWJ0A";
            String keyId = "gvjioX2sRMChj2YEeTScWQ";

            String keyString = "{\"keys\":[{\"kty\":\"oct\",\"k\":\"" + keyValue + "\",\"kid\":\"" + keyId + "\"}],\"type\":\"temporary\"}";
            MediaDrmCallback callback = new LocalMediaDrmCallback(keyString.getBytes());

            mediaSource = new DashMediaSource.Factory(dataSourceFactory)
                    .setDrmSessionManagerProvider(mediaItem -> {
                        return new DefaultDrmSessionManager.Builder()
                                .setPlayClearSamplesWithoutKeys(true)
                                .setMultiSession(false)
                                .setKeyRequestParameters(new HashMap<String, String>())
                                .setUuidAndExoMediaDrmProvider(C.CLEARKEY_UUID, FrameworkMediaDrm.DEFAULT_PROVIDER)
                                .build(callback);
                    })
                    .createMediaSource(MediaItem.fromUri(videoURI));
        } else {
            mediaSource = new HlsMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(MediaItem.fromUri(live_url));
        }

        player.setMediaSource(mediaSource);
        player.prepare();
        player.setPlayWhenReady(true);

        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int state) {
                if(state == Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                    playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                    player.setPlayWhenReady(true);
                }else if(state == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                    playerView.setKeepScreenOn(true);
                }else {
                    progressBar.setVisibility(View.GONE);
                    playerView.setKeepScreenOn(true);
                    player.setPlayWhenReady(true);
                }
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        player.seekToDefaultPosition();
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onPause() {
        player.setPlayWhenReady(false);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        player.release();
        super.onDestroy();
    }
}