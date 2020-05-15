package com.software.ProfileFit.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.ProfileFit.R;
import com.software.ProfileFit.Utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class RatioAdapter extends RecyclerView.Adapter<RatioAdapter.RatioViewHolder>{

    Context context;
    List<Integer> ratioList;
    RatioAdapterListener onlistener;
    private int selectedIndex = 0;
    int row_selected = -1;

    Bitmap originalbitmap;

    public RatioAdapter(Context context,  RatioAdapterListener listener) {
        this.context = context;
        this.ratioList = getRatioList();
        this.onlistener = listener;
    }

    private List<Integer> getRatioList() {
        List<Integer> result = new ArrayList<>();

        result.add(R.drawable.trans_onebyone);
        result.add(R.drawable.trans_fourbythree);
        result.add(R.drawable.trans_fourbyfive);
        result.add(R.drawable.trans_sixbynine);
        result.add(R.drawable.trans_threebyfour);
        result.add(R.drawable.trans_ninebysix);


        return result;
    }


    @NonNull
    @Override
    public RatioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.ratio_thumbnail,parent,false);
        return new RatioViewHolder(itemView, onlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull RatioViewHolder holder, final int position) {

        holder.ratio_name.setVisibility(View.INVISIBLE);
        holder.ratio_name.setText("Colorsprofi");

        //holder.thumbnail2.setImageResource(ratioList.get(position));
        holder.thumbnail2.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(context.getResources(),ratioList.get(position),100,100));

        if(selectedIndex == position){
            holder.boder2.setImageResource(R.drawable.round_outline);
        }
        else{
            holder.boder2.setImageDrawable(null);
        }

        holder.thumbnail2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistener.onRatioSelected(ratioList.get(position));
                selectedIndex = position;
                row_selected = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ratioList.size();
    }



    public class RatioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail2, boder2;
        TextView ratio_name;
        RatioAdapterListener ratioAdapterListener;

        public RatioViewHolder(@NonNull View itemView, RatioAdapterListener ratioAdapterListener) {
            super(itemView);
            ratio_name = (TextView)itemView.findViewById(R.id.ratio_name);
            thumbnail2 = (ImageView)itemView.findViewById(R.id.thumbnail2);
            boder2 = (ImageView)itemView.findViewById(R.id.boder2);
            this.ratioAdapterListener = ratioAdapterListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ratioAdapterListener.onRatioSelected(getAdapterPosition());
        }
    }

    public interface RatioAdapterListener{
        void onRatioSelected(int ratio);

    }

}
