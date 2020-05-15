package com.software.ProfileFit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.software.ProfileFit.Interface.FirltersListFragmentListener;
import com.software.ProfileFit.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.MyViewHolder> {

    private List<ThumbnailItem> thumbnailItems;
    private FirltersListFragmentListener listener;
    private Context context;
    private int selectedIndex = 0;

    private List<String> namesList;

    public ThumbnailAdapter(List<ThumbnailItem> thumbnailItems, FirltersListFragmentListener listener, Context context) {
        this.thumbnailItems = thumbnailItems;
        this.listener = listener;
        this.context = context;
        this.namesList = getNamesList();
    }

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();

        names.add("Normal");
        names.add("Wind");
        names.add("Sand");
        names.add("Leaf");
        names.add("Lime");
        names.add("Sky");
        names.add("Night");
        names.add("Light");
        names.add("Beach");
        names.add("Dice");
        names.add("Waves");
        names.add("Rain");
        names.add("Snow");
        names.add("Cloud");
        names.add("Tree");
        names.add("Winter");
        names.add("Road");

        return names;
    }


    //String[] names =  { "One"};


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.thumbnail_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final ThumbnailItem thumbnailItem = thumbnailItems.get(position);


        holder.thumbnail.setImageBitmap(thumbnailItem.image);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFrilterSelected(thumbnailItem.filter);
                selectedIndex = position;
                notifyDataSetChanged();
            }
        });
        //namesList.get(position)
        holder.filter_nmae.setText(namesList.get(position));


        if(selectedIndex == position)
            holder.filter_nmae.setTextColor(ContextCompat.getColor(context, R.color.selected_item));
        else
            holder.filter_nmae.setTextColor(ContextCompat.getColor(context, R.color.browser_actions_bg_grey));

        if(selectedIndex == position){
            holder.boder.setImageResource(R.drawable.circulo_select);
        }
        else{
            holder.boder.setImageDrawable(null);
        }
    }

    @Override
    public int getItemCount() {
        return thumbnailItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail, boder;
        TextView filter_nmae;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            filter_nmae = (TextView) itemView.findViewById(R.id.filter_name);
            boder = (ImageView) itemView.findViewById(R.id.boder1);
        }
    }
}
