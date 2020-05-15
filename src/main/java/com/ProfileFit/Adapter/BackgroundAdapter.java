package com.software.ProfileFit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.software.ProfileFit.R;
import com.software.ProfileFit.Utils.BitmapUtils;
import java.util.ArrayList;
import java.util.List;


public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.BackgroundViewHolder> {

    private Context context;
    List<Integer> backgroundList;
    List<Integer> backgroundList2;
    BackgroundAdapterListener onlistener;

    private int selectedIndex = 0;

    private List<String> namesList;

    int row_selected = -1;

    public BackgroundAdapter(Context context,  BackgroundAdapterListener listener) {
        this.context = context;
        this.backgroundList = getBackgroundList();
        this.backgroundList2 = getBackgroundList2();
        this.onlistener = listener;
        this.namesList = getNamesList();
    }

    private List<Integer> getBackgroundList() {
        List<Integer> result = new ArrayList<>();

        result.add(R.drawable.grey3);
        result.add(R.drawable.white1);
        result.add(R.drawable.negro_conbor);
        result.add(R.drawable.cover_twitter3);
        result.add(R.drawable.cover_whatsapp2);
        result.add(R.drawable.cover_instagram2);
        result.add(R.drawable.cover_main2);
        result.add(R.drawable.green3);
        result.add(R.drawable.green4);
        result.add(R.drawable.green);
        result.add(R.drawable.blue2);
        result.add(R.drawable.blue3);
        result.add(R.drawable.blue1);
        result.add(R.drawable.red2);
        result.add(R.drawable.red3);
        result.add(R.drawable.red);
        result.add(R.drawable.orange2);
        result.add(R.drawable.orange3);
        result.add(R.drawable.orange);
        result.add(R.drawable.pink);
        result.add(R.drawable.purple3);
        result.add(R.drawable.pink2);
        result.add(R.drawable.pink_light);
        result.add(R.drawable.purple5);
        result.add(R.drawable.purple);
        result.add(R.drawable.grey_light);
        result.add(R.drawable.grey_normal);
        result.add(R.drawable.black_light);
        result.add(R.drawable.brown2);
        result.add(R.drawable.brown);
        result.add(R.drawable.brown3);
        result.add(R.drawable.yellow2);
        result.add(R.drawable.yellow3);
        result.add(R.drawable.yellow_light);
        result.add(R.drawable.aqualight);
        result.add(R.drawable.aquamarine);
        result.add(R.drawable.green_light);
        result.add(R.drawable.blue_sky);
        result.add(R.drawable.lightazul2);
        result.add(R.drawable.blue_light);
        result.add(R.drawable.lime2);
        result.add(R.drawable.lime3);
        result.add(R.drawable.limedark);

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
        names.add("PF4");
        names.add("AQ1");
        names.add("GR1");
        names.add("BL2");
        names.add("BL3");
        names.add("RD1");
        names.add("OR1");
        names.add("GR1");
        names.add("BL4");
        names.add("PK2");
        names.add("PL2");
        names.add("GR2");
        names.add("WH2");
        names.add("BR1");
        names.add("LB1");
        names.add("PL3");
        names.add("GR1");
        names.add("YL2");
        names.add("YL1");
        names.add("PK1");
        names.add("HH6");
        names.add("LM8");
        names.add("LM9");
        names.add("HB3");
        names.add("HB3");
        names.add("HB3");
        names.add("HB5");
        names.add("PU9");
        names.add("PI9");
        names.add("PI9");
        names.add("PI9");
        names.add("JK6");
        names.add("YL5");
        names.add("YL5");
        names.add("YL5");
        names.add("YL5");
        names.add("YL5");

        return names;
    }

    private List<Integer> getBackgroundList2(){
        List<Integer> result = new ArrayList<>();

        result.add(R.drawable.pic_1);
        result.add(R.drawable.pic_2);
        result.add(R.drawable.pic_3);
        result.add(R.drawable.pic_4);
        return result;
    }

    @NonNull
    @Override
    public BackgroundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.back_thumbnail,parent,false);
        return new BackgroundViewHolder(itemView, onlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final BackgroundViewHolder holder, final int position) {

        //thumbnail name
        holder.back_name.setVisibility(View.INVISIBLE);
        holder.back_name.setText(namesList.get(position));


        holder.thumbnail1.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(context.getResources(),backgroundList.get(position),100,100));

        //holder.boder.setImageResource(R.drawable.invissible_line);
        if(selectedIndex == position){
            holder.boder.setImageResource(R.drawable.round_outline);
        }
        else{
            holder.boder.setImageDrawable(null);
        }

        holder.thumbnail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onlistener.onBackgroundSelected(backgroundList.get(position));
                selectedIndex = position;
                row_selected = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return backgroundList.size();
    }


    public class BackgroundViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView thumbnail1, boder;
        ImageView thumbanil2, boder2;
        TextView back_name;
        TextView back_name2;
        LinearLayout background_id;
        BackgroundAdapterListener backgroundAdapterListener;

        public BackgroundViewHolder(@NonNull View itemView, BackgroundAdapterListener backgroundAdapterListener) {
            super(itemView);
            back_name = (TextView)itemView.findViewById(R.id.back_name);
            thumbnail1 = (ImageView)itemView.findViewById(R.id.thumbnail1);
            boder = (ImageView)itemView.findViewById(R.id.boder);
            back_name2 = (TextView)itemView.findViewById(R.id.back_name_in2);
            thumbanil2 = (ImageView)itemView.findViewById(R.id.thumbnail_in2);
            boder2= (ImageView)itemView.findViewById(R.id.boder_in2);
            background_id = (LinearLayout) itemView.findViewById(R.id.background_id);
            this.backgroundAdapterListener = backgroundAdapterListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            backgroundAdapterListener.onBackgroundSelected(getAdapterPosition());
        }
    }

    public interface BackgroundAdapterListener{
        void onBackgroundSelected(int background);

    }
}
