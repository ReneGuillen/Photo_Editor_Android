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

import com.software.ProfileFit.Interface.BorderFragmentListerner;
import com.software.ProfileFit.R;
import com.software.ProfileFit.Utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;


public class BorderAdapter extends RecyclerView.Adapter<BorderAdapter.BorderViewHolder> {

    Context context;
    List<Integer> borderList;
    BorderAdapterListener onlistener;

    private int selectedIndex = 0;

    private List<String> namesList;

    int row_selected = -1;

    public BorderAdapter(Context context, BorderAdapterListener listener) {
        this.context = context;
        this.borderList = getBorderList();
        this.onlistener = listener;
        this.namesList = getNamesList();
    }

    private List<Integer> getBorderList() {
        List<Integer> result = new ArrayList<>();

        result.add(R.drawable.whitex_trans);
        result.add(R.drawable.whie_line);
        result.add(R.drawable.lime_line);
        result.add(R.drawable.aqua_line);
        result.add(R.drawable.red_line);
        result.add(R.drawable.pink_line);
        result.add(R.drawable.yellow_line);
        result.add(R.drawable.blue_line);
        result.add(R.drawable.olive_line);
        result.add(R.drawable.grey_line);
        result.add(R.drawable.anaranja_line);
        result.add(R.drawable.purple_line);
        result.add(R.drawable.brown_line);

        return result;
    }

    private List<String> getNamesList() {
        List<String> names = new ArrayList<>();

        names.add("Blur");
        names.add("WH1");
        names.add("BL1");
        names.add("PF1");
        names.add("PF2");
        names.add("PF3");
        names.add("PW3");
        names.add("PJ3");
        names.add("HE4");
        names.add("HO9");
        names.add("PU6");
        names.add("AN6");
        names.add("BR5");

        return names;
    }


    @NonNull
    @Override
    public BorderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.border_thumbnail,parent,false);
        return new BorderViewHolder(itemView, onlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final BorderViewHolder holder, final int position) {

        //thumbnail name
        holder.border_name.setVisibility(View.INVISIBLE);
        holder.border_name.setText(namesList.get(position));

        //holder.border_thumbnail1.setImageResource(borderList.get(position));
        holder.border_thumbnail1.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(context.getResources(),borderList.get(position),100,100));

        //holder.boder.setImageResource(R.drawable.invissible_line);


        if(selectedIndex == position){
            //holder.border_of_border.setImageResource(R.drawable.aquax_trans);
            holder.border_of_border.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(context.getResources(),R.drawable.aquax_trans,100,100));
        }
        else{
            holder.border_of_border.setImageDrawable(null);
        }

        holder.border_thumbnail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistener.onBorderSelected(borderList.get(position));
                selectedIndex = position;
                row_selected = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return borderList.size();
    }



    public class BorderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView border_thumbnail1, border_of_border;
        TextView border_name;
        BorderAdapterListener BorderAdapterListener;

        public BorderViewHolder(@NonNull View itemView, BorderAdapterListener borderAdapterListener) {
            super(itemView);
            border_name = (TextView)itemView.findViewById(R.id.border_name);
            border_thumbnail1 = (ImageView)itemView.findViewById(R.id.border_thumbnail);
            border_of_border = (ImageView)itemView.findViewById(R.id.boder_border);
            this.BorderAdapterListener = borderAdapterListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            BorderAdapterListener.onBorderSelected(getAdapterPosition());
        }
    }

    public interface BorderAdapterListener{
        void onBorderSelected(int border);

    }
}
