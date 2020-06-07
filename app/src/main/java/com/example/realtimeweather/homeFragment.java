package com.example.realtimeweather;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realtimeweather.models.wetherdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class homeFragment extends Fragment {
    public homeFragment() {
        // Required empty public constructor
    }

    Handler mHandler;
    Runnable myrun;
TextView generalvalue,generaWeather,tempvalue,rainfallvalue,humidityvalue,windspeedvalue,barometervalue;

    String saveCurrentTime, monthno, day, year, monthstring,today;
    Calendar calendar;
     ImageView generalImgae;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//Images
        generalImgae = view.findViewById(R.id.generalimage);

        //TextViews
        generalvalue=view.findViewById(R.id.generalvalue);
        generaWeather=view.findViewById(R.id.generaWeather);
        tempvalue= view.findViewById(R.id.tempvalue);
        rainfallvalue = view.findViewById(R.id.rainfallvalue);
        humidityvalue = view.findViewById(R.id.humidityvalue);
        windspeedvalue = view.findViewById(R.id.windspeedvalue);
        barometervalue = view.findViewById(R.id.barometervalue);


        calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        day = (String) DateFormat.format("dd", calendar.getTime());
        year = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno = (String) DateFormat.format("MM", calendar.getTime());
        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
        today = year+"-"+monthno+"-"+day;

        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
        ValueEventListener eventListener;

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int tot = 0;
                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (("2020-06-06").equalsIgnoreCase(data.getDate())) {

                            if (data.getTemperature() == null) {
                                tempvalue.setText("0");
                            } else {
                                generalvalue.setText(String.valueOf(data.getTemperature()) + " \u2103");
                                tempvalue.setText(String.valueOf(data.getTemperature()) + " \u2103");
                                rainfallvalue.setText(String.valueOf(data.getRainfall_1hr()) + " mm");
                                humidityvalue.setText(String.valueOf(data.getHumidity()) + " %");
                                windspeedvalue.setText(String.valueOf(data.getA_Windsepeed()) + " m/s");
                                barometervalue.setText(String.valueOf(data.getBarometric_Pressure()) + " Pa");
                                Long temp = data.getTemperature();
                                int tempint = temp.intValue();
                                if ((tempint) >= 18 && (tempint) <= 24) {
                                    if ((tempint) < 0.4) {
                                        generaWeather.setText("The weather is comfortable ");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.smilly_sun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((data.getRainfall_1hr().intValue()) > 0.5 && (data.getRainfall_1hr().intValue()) < 4) {

                                        generaWeather.setText("The weather is comfortable with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 4 && (data.getRainfall_1hr().intValue()) < 8) {

                                        generaWeather.setText("The weather is comfortable with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 8) {

                                        generaWeather.setText("The weather is comfortable with very heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((data.getTemperature().intValue()) >= 25 && (data.getTemperature().intValue()) <= 29) {

                                    if ((data.getRainfall_1hr().intValue()) < 0.4) {
                                        generaWeather.setText("The weather is warm");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.goodsun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((data.getRainfall_1hr().intValue()) > 0.5 && (data.getRainfall_1hr().intValue()) < 4) {

                                        generaWeather.setText("The weather is warm with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 4 && (data.getRainfall_1hr().intValue()) < 8) {

                                        generaWeather.setText("The weather is warm with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 8) {

                                        generaWeather.setText("The weather is warm with very heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((data.getTemperature().intValue()) >= 30 && (data.getTemperature().intValue()) <= 35) {


                                    if ((data.getRainfall_1hr().intValue()) < 0.4) {
                                        generaWeather.setText("The weather is hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.sunny);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((data.getRainfall_1hr().intValue()) > 0.5 && (data.getRainfall_1hr().intValue()) < 4) {

                                        generaWeather.setText("The weather is hot with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 4 && (data.getRainfall_1hr().intValue()) < 8) {

                                        generaWeather.setText("The weather is hot with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((data.getRainfall_1hr().intValue()) > 8) {

                                        generaWeather.setText("The weather is hot with very heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                generalImgae.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }
                                }

                            }
//                            tot = tot + (data.getHumidity().intValue());
                            //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();


                        }



                    }

                } else {
                    //Toast.makeText(getContext(), "Data does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();



    }
}
