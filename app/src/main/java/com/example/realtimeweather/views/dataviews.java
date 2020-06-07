package com.example.realtimeweather.views;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimeweather.R;

public class dataviews extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView datetxt, generaltxt, windtxt, pressuretxt, humiditydailytxt;
    public ItemClickListener listener;

    public dataviews(@NonNull View itemView) {
        super(itemView);


        humiditydailytxt = itemView.findViewById(R.id.humiditydailytxt);
        datetxt = itemView.findViewById(R.id.datetxt);
        generaltxt = itemView.findViewById(R.id.generaltxt);
        windtxt = itemView.findViewById(R.id.windtxt);
        pressuretxt = itemView.findViewById(R.id.pressuretxt);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
