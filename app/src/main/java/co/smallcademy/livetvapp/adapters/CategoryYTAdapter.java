package co.smallcademy.livetvapp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import co.smallcademy.livetvapp.CategoryDetails;
import co.smallcademy.livetvapp.R;
import co.smallcademy.livetvapp.Youtube_Web;
import co.smallcademy.livetvapp.models.CatYoutube;
import co.smallcademy.livetvapp.models.Category;
import co.smallcademy.livetvapp.models.Channel;

public class CategoryYTAdapter extends RecyclerView.Adapter<CategoryYTAdapter.ViewHolder> {
    List<CatYoutube> categoryList;


    public CategoryYTAdapter( List<CatYoutube> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryYTAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.catTitle.setText(categoryList.get(position).getName());
        Picasso.get().load(categoryList.get(position).getThumbnail()).into(holder.cateImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Youtube_Web.class);
                i.putExtra("catYT",categoryList.get(position));
                v.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cateImage;
        TextView catTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cateImage = itemView.findViewById(R.id.catImage);
            catTitle = itemView.findViewById(R.id.catTitle);
        }
    }
}
