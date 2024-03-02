package co.smallcademy.livetvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.smallcademy.livetvapp.adapters.CategoryYTAdapter;
import co.smallcademy.livetvapp.models.CatYoutube;
import co.smallcademy.livetvapp.models.Category;
import co.smallcademy.livetvapp.services.ChannelDataService;


public class CategoryYT extends AppCompatActivity {
    public static final String TAG = "TAG";
    RecyclerView categoryLists;
    CategoryYTAdapter categoryAdapter;
    List<CatYoutube> categoryList;
    ChannelDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        getSupportActionBar().setTitle("Live Youtube");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataService = new ChannelDataService(this);
        categoryList = new ArrayList<CatYoutube>();
        categoryLists = findViewById(R.id.category_lists);
        categoryLists.setLayoutManager(new GridLayoutManager(this,2));
        categoryAdapter = new CategoryYTAdapter(categoryList);
        categoryLists.setAdapter(categoryAdapter);

        dataService.getChannelData("https://ailoktv.000webhostapp.com/api.php?key=1A4mgi2rBHCJdqggsYVx&id=1&cat=Youtube", new ChannelDataService.OnDataResponse() {
            @Override
            public void onResponse(JSONObject response) {
                for(int i = 0; i<response.length();i++){
                    try {
                        JSONObject categoryData = response.getJSONObject(String.valueOf(i));

                        CatYoutube category = new CatYoutube(categoryData.getInt("id"),categoryData.getString("name"),categoryData.getString("thumbnail"), categoryData.getString("live_url"));
                        categoryList.add(category);
                        categoryAdapter.notifyDataSetChanged();


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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

}
