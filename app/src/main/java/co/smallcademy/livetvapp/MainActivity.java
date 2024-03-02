package co.smallcademy.livetvapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.smallcademy.livetvapp.adapters.ChannelAdapter;
import co.smallcademy.livetvapp.models.Category;
import co.smallcademy.livetvapp.models.Channel;
import co.smallcademy.livetvapp.services.ChannelDataService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String TAG = "TAG";
    RecyclerView bigSliderList,newsChannelList,sportsChannelList,enterChannelList, scienceChannelList, cartoonChannelList, filmChannelList;
    ChannelAdapter bigSliderAdapter,newsChannelAdapter,sportsChannelAdapter,enterChannelAdapter, scienceChannelAdapter, cartoonChannelAdapter, filmChannelAdapter ;
    List<Channel> channelList,newsChannels,sportsChannel,enterChannel, scienceChannel, cartoonChannel, filmChannel;
    ChannelDataService service;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ImageView btn_refresh;
    ShimmerFrameLayout shimmerView;
    TextView testu1, testu2, testu3, testu4, testu5, testu6;

    private String sliderUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&channels=all";
    private String newsUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Notisia";
    private String sportsUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Desportu";
    private String enterUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Kanal Mundial";
    private String ScienceUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Siensia";
    private String CartoonUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Kartun";
    private String FilmUrl = "https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Filme";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        testu1 = (TextView) findViewById(R.id.textView4);
        testu2 = (TextView) findViewById(R.id.textView6);
        testu3 = (TextView) findViewById(R.id.textView8);
        testu4 = (TextView) findViewById(R.id.textView10);
        testu5 = (TextView) findViewById(R.id.textView12);
        testu6 = (TextView) findViewById(R.id.textView14);
        btn_refresh = (ImageView) findViewById(R.id.btn_refresh);

        // Inisialisasi shimmerView
        shimmerView = findViewById(R.id.shimmer_view);
        shimmerView.startShimmer();

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()) {
                    case R.id.textView4:
                        // code untuk textView4
                        Intent intent = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value = "Notisia";
                        intent.putExtra("key", value);
                        startActivity(intent);
                        break;

                    case R.id.textView6:
                        // code untuk textView6
                        Intent intent1 = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value1 = "Desportu";
                        intent1.putExtra("key", value1);
                        startActivity(intent1);
                        break;
                    case R.id.textView8:
                        // code untuk textView7
                        Intent intent2 = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value2 = "Kanal Mundial";
                        intent2.putExtra("key", value2);
                        startActivity(intent2);
                        break;

                    case R.id.textView10:
                        // code untuk textView7
                        Intent intent3 = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value3 = "Siensia";
                        intent3.putExtra("key", value3);
                        startActivity(intent3);
                        break;

                    case R.id.textView12:
                        // code untuk textView7
                        Intent intent4 = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value4= "Kartun";
                        intent4.putExtra("key", value4);
                        startActivity(intent4);
                        break;

                    case R.id.textView14:
                        // code untuk textView7
                        Intent intent5 = new Intent(MainActivity.this, CategoryDetailsHome.class);
                        String value5 = "Filme";
                        intent5.putExtra("key", value5);
                        startActivity(intent5);
                        break;
                }
            }
        };

        testu1.setOnClickListener(myOnClickListener);
        testu2.setOnClickListener(myOnClickListener);
        testu3.setOnClickListener(myOnClickListener);
        testu4.setOnClickListener(myOnClickListener);
        testu5.setOnClickListener(myOnClickListener);
        testu6.setOnClickListener(myOnClickListener);


        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Panggil method yang melakukan refresh data
                refreshData();

            }
        });


        channelList = new ArrayList<>();
        service = new ChannelDataService(this);

        bigSliderList = findViewById(R.id.big_slider_list);
        bigSliderList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        bigSliderAdapter = new ChannelAdapter(channelList,"slider");
        bigSliderList.setAdapter(bigSliderAdapter);

        getSliderData(sliderUrl);
        getNewsChannels(newsUrl);
        getSportsChannel(sportsUrl);
        getEnterChannel(enterUrl);
        getScienceChannel(ScienceUrl);
        getCartoonChannel(CartoonUrl);
        getFilmChannel(FilmUrl);

    }


    private void refreshData() {
        channelList = new ArrayList<>();
        service = new ChannelDataService(this);

        bigSliderList = findViewById(R.id.big_slider_list);
        bigSliderList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        bigSliderAdapter = new ChannelAdapter(channelList,"slider");
        bigSliderList.setAdapter(bigSliderAdapter);

        getSliderData(sliderUrl);
        getNewsChannels(newsUrl);
        getSportsChannel(sportsUrl);
        getEnterChannel(enterUrl);
        getScienceChannel(ScienceUrl);
        getCartoonChannel(CartoonUrl);
        getFilmChannel(FilmUrl);
    }


    public void getSliderData(String url){
        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        String category = channelData.getString("category");
                        if(category.equals("Youtube")) {
                            continue;
                        }else{
                            Channel c = new Channel();
                            c.setId(channelData.getInt("id"));
                            c.setName(channelData.getString("name"));
                            c.setDescription(channelData.getString("description"));
                            c.setThumbnail(channelData.getString("thumbnail"));
                            c.setLive_url(channelData.getString("live_url"));
                            c.setFacebook(channelData.getString("facebook"));
                            c.setTwitter(channelData.getString("twitter"));
                            c.setYoutube(channelData.getString("youtube"));
                            c.setWebsite(channelData.getString("website"));
                            c.setCategory(channelData.getString("category"));
                            shimmerView.stopShimmer();
                            shimmerView.setVisibility(View.GONE);
                            channelList.add(c);
                            bigSliderAdapter.notifyDataSetChanged();
                        }


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {

                Intent intent = new Intent(MainActivity.this, Jaringan_Araska.class);
                startActivity(intent);
                finish();

                Log.d(TAG, "onErrorResponse: " + error);
            }
        });
    }

    public void getNewsChannels(String url){
        newsChannelList = findViewById(R.id.news_channel_list);
        newsChannels = new ArrayList<>();
        newsChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        newsChannelAdapter = new ChannelAdapter(newsChannels,"category");
        newsChannelList.setAdapter(newsChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        newsChannels.add(c);
                        newsChannelAdapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });


    }

    public void getSportsChannel(String url){
        sportsChannelList = findViewById(R.id.sports_channel_list);
        sportsChannel = new ArrayList<>();
        sportsChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        sportsChannelAdapter = new ChannelAdapter(sportsChannel,"category");
        sportsChannelList.setAdapter(sportsChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
//                Log.d(TAG, "onResponse: sports" + response.toString());
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));
                        Log.d(TAG, "onResponse: " + c.toString());
                        sportsChannel.add(c);
                        sportsChannelAdapter.notifyDataSetChanged();


                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });


    }

    public void getEnterChannel(String url){

        enterChannelList = findViewById(R.id.enter_channel_list);
        enterChannel = new ArrayList<>();
        enterChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        enterChannelAdapter = new ChannelAdapter(enterChannel,"category");
        enterChannelList.setAdapter(enterChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        enterChannel.add(c);
                        enterChannelAdapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });

    }

    public void getScienceChannel(String url){

        scienceChannelList = findViewById(R.id.science_channel_list);
        scienceChannel = new ArrayList<>();
        scienceChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        scienceChannelAdapter = new ChannelAdapter(scienceChannel,"category");
        scienceChannelList.setAdapter(scienceChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        scienceChannel.add(c);
                        scienceChannelAdapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });

    }

    public void getCartoonChannel(String url){

        cartoonChannelList = findViewById(R.id.cartoon_channel_list);
        cartoonChannel = new ArrayList<>();
        cartoonChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        cartoonChannelAdapter = new ChannelAdapter(cartoonChannel,"category");
        cartoonChannelList.setAdapter(cartoonChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        cartoonChannel.add(c);
                        cartoonChannelAdapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });

    }

    public void getFilmChannel(String url){

        filmChannelList = findViewById(R.id.film_channel_list);
        filmChannel = new ArrayList<>();
        filmChannelList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        filmChannelAdapter = new ChannelAdapter(filmChannel,"category");
        filmChannelList.setAdapter(filmChannelAdapter);

        service.getChannelData(url, new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject channelData = response.getJSONObject(String.valueOf(i));
                        Channel c = new Channel();
                        c.setId(channelData.getInt("id"));
                        c.setName(channelData.getString("name"));
                        c.setDescription(channelData.getString("description"));
                        c.setThumbnail(channelData.getString("thumbnail"));
                        c.setLive_url(channelData.getString("live_url"));
                        c.setFacebook(channelData.getString("facebook"));
                        c.setTwitter(channelData.getString("twitter"));
                        c.setYoutube(channelData.getString("youtube"));
                        c.setWebsite(channelData.getString("website"));
                        c.setCategory(channelData.getString("category"));

                        filmChannel.add(c);
                        filmChannelAdapter.notifyDataSetChanged();


//                        Log.d(TAG, "onResponse: " + c.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(String error) {
                Log.d(TAG, "onError: " + error);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.home){

        }

        if(item.getItemId() == R.id.Youtube){
            startActivity(new Intent(this,CategoryYT.class));
        }

        if(item.getItemId() == R.id.categories){
            // start category activity
            startActivity(new Intent(this,Categories.class));
        }

        if(item.getItemId() == R.id.feedback){
            startActivity(new Intent(this,Feedback.class));
        }
        return false;

    }


}