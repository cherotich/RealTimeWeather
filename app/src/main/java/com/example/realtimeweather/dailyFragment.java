package com.example.realtimeweather;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.realtimeweather.adapter.TabAdapter;
import com.example.realtimeweather.models.wetherdata;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class dailyFragment extends Fragment {
    DatePicker pickdate;
    String todaychosen;
    DatePickerDialog.OnDateSetListener date;
    DatePickerDialog.OnClickListener listen;
    Calendar myorigcalender = Calendar.getInstance();
    EditText inputdate;
    TextView generalvalue, generaWeather, tempvalue, rainfallvalue, humidityvalue, windspeedvalue, barometervalue;
    //    Calendar calendar;
//    String saveCurrentTime, monthno, day, year, monthstring, today, exacttoday;
    DatabaseReference dataref1, dataref2, dataref3, dataref4, dataref5, dataref6;
    ImageView updateweatherdata;
    Query dataquery;
    Handler mHandler;
    Runnable myrun;
    //date range
    TextView rangedate;
    //today
    TextView todaydatestring, todaydateno, hightemptoday, lowtemptoday, humiditytoday;
    ImageView todayweatherimage;
    Button todaybtn;
    Calendar calendar;
    String saveCurrentTime, monthno, day, todayno, year, monthstring, today, exacttoday;
    //Yesterday
    TextView datestring2, dateno2, hightemp2, lowtemp2, humidityday2;
    ImageView weatherimage2;
    Button day2btn;
    String monthno2, day2, dayno2, year2, monthstring2, today2;
    //day3
    TextView datestring3, dateno3, hightemp3, lowtemp3, humidityday3;
    ImageView weatherimage3;
    Button day3btn;
    String monthno3, day3, dayno3, year3, monthstring3, today3;
    //day4
    TextView datestring4, dateno4, hightemp4, lowtemp4, humidityday4;
    ImageView weatherimage4;
    Button day4btn;
    String monthno4, day4, dayno4, year4, monthstring4, today4;
    //day5
    TextView datestring5, dateno5, hightemp5, lowtemp5, humidityday5;
    ImageView weatherimage5;
    Button day5btn;
    String monthno5, day5, dayno5, year5, monthstring5, today5;
    //day6
    TextView datestring6, dateno6, hightemp6, lowtemp6, humidityday6;
    ImageView weatherimage6;
    Button day6btn;
    String monthno6, day6, dayno6, year6, monthstring6, today6;
    private TabAdapter adapter;
    // private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public dailyFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.daily_fragment, container, false);

        rangedate = view.findViewById(R.id.rangedate);

        //datareference
        dataref1 = FirebaseDatabase.getInstance().getReference("Weather_Data");
        dataref2 = FirebaseDatabase.getInstance().getReference("Weather_Data");
        dataref3 = FirebaseDatabase.getInstance().getReference("Weather_Data");
        dataref4 = FirebaseDatabase.getInstance().getReference("Weather_Data");
        dataref5 = FirebaseDatabase.getInstance().getReference("Weather_Data");
        dataref6 = FirebaseDatabase.getInstance().getReference("Weather_Data");


        //today
        todaydatestring = view.findViewById(R.id.todaydatestring);
        todaydateno = view.findViewById(R.id.todaydateno);
        todayweatherimage = view.findViewById(R.id.todayweatherimage);
        hightemptoday = view.findViewById(R.id.hightemptoday);
        lowtemptoday = view.findViewById(R.id.lowtemptoday);
        humiditytoday = view.findViewById(R.id.humiditytoday);
        todaybtn = view.findViewById(R.id.todaybtn);

        //yesterday
        datestring2 = view.findViewById(R.id.datestring2);
        dateno2 = view.findViewById(R.id.dateno2);
        hightemp2 = view.findViewById(R.id.hightemp2);
        lowtemp2 = view.findViewById(R.id.lowtemp2);
        humidityday2 = view.findViewById(R.id.humidityday2);
        weatherimage2 = view.findViewById(R.id.day2weatherimage);
        day2btn = view.findViewById(R.id.day2btn);


        //day3
        datestring3 = view.findViewById(R.id.datestring3);
        dateno3 = view.findViewById(R.id.dateno3);
        hightemp3 = view.findViewById(R.id.hightemp3);
        lowtemp3 = view.findViewById(R.id.lowtemp3);
        humidityday3 = view.findViewById(R.id.humidityday3);
        weatherimage3 = view.findViewById(R.id.day3weatherimage);
        day3btn = view.findViewById(R.id.day3btn);

        //day4
        datestring4 = view.findViewById(R.id.datestring4);
        dateno4 = view.findViewById(R.id.dateno4);
        hightemp4 = view.findViewById(R.id.hightemp4);
        lowtemp4 = view.findViewById(R.id.lowtemp4);
        humidityday4 = view.findViewById(R.id.humidityday4);
        weatherimage4 = view.findViewById(R.id.day4weatherimage);
        day4btn = view.findViewById(R.id.day4btn);


        //day5
        datestring5 = view.findViewById(R.id.datestring5);
        dateno5 = view.findViewById(R.id.dateno5);
        hightemp5 = view.findViewById(R.id.hightemp5);
        lowtemp5 = view.findViewById(R.id.lowtemp5);
        humidityday5 = view.findViewById(R.id.humidityday5);
        weatherimage5 = view.findViewById(R.id.day5weatherimage);
        day5btn = view.findViewById(R.id.day5btn);

        //day6
        datestring6 = view.findViewById(R.id.datestring6);
        dateno6 = view.findViewById(R.id.dateno6);
        hightemp6 = view.findViewById(R.id.hightemp6);
        lowtemp6 = view.findViewById(R.id.lowtemp6);
        humidityday6 = view.findViewById(R.id.humidityday6);
        weatherimage6 = view.findViewById(R.id.day6weatherimage);
        day6btn = view.findViewById(R.id.day6btn);

        //getdates
        //today
        calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        day = (String) DateFormat.format("dd", calendar.getTime());
        year = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno = (String) DateFormat.format("MM", calendar.getTime());
        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
        today = year + "-" + monthno + "-" + day;
        todayno = (String) DateFormat.format("EEEE", calendar.getTime());
        todaydatestring.setText(todayno.substring(0, 3));
        todaydateno.setText(monthno + "/" + day);


        //yesterday
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        day2 = (String) DateFormat.format("EEEE", calendar.getTime());
        year2 = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno2 = (String) DateFormat.format("MM", calendar.getTime());
        monthstring2 = (String) DateFormat.format("MMM", calendar.getTime());
        dayno2 = (String) DateFormat.format("dd", calendar.getTime());

        today2 = year2 + "-" + monthno2 + "-" + dayno2;


        datestring2.setText(day2.substring(0, 3));
//        datestring2.setText("");
        dateno2.setText(monthno2 + "/" + dayno2);

        //day3
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        day3 = (String) DateFormat.format("EEEE", calendar.getTime());
        year3 = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno3 = (String) DateFormat.format("MM", calendar.getTime());
        monthstring3 = (String) DateFormat.format("MMM", calendar.getTime());
        dayno3 = (String) DateFormat.format("dd", calendar.getTime());

        today3 = year3 + "-" + monthno3 + "-" + dayno3;

        datestring3.setText(day3.substring(0, 3));
//        datestring3.setText("");
        dateno3.setText(monthno3 + "/" + dayno3);

        //day4
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -3);
        day4 = (String) DateFormat.format("EEEE", calendar.getTime());
        year4 = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno4 = (String) DateFormat.format("MM", calendar.getTime());
        monthstring4 = (String) DateFormat.format("MMM", calendar.getTime());
        dayno4 = (String) DateFormat.format("dd", calendar.getTime());

        today4 = year4 + "-" + monthno4 + "-" + dayno4;

//     int days=   calendar.getActualMaximum(calendar.DAY_OF_MONTH);
//     Log.d("TAG",String.valueOf(days));

        datestring4.setText(day4.substring(0, 3));
//        datestring4.setText("");
        dateno4.setText(monthno4 + "/" + dayno4);

        //day5
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -4);
        day5 = (String) DateFormat.format("EEEE", calendar.getTime());
        year5 = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno5 = (String) DateFormat.format("MM", calendar.getTime());
        monthstring5 = (String) DateFormat.format("MMM", calendar.getTime());
        dayno5 = (String) DateFormat.format("dd", calendar.getTime());

        today5 = year5 + "-" + monthno5 + "-" + dayno5;

        datestring5.setText(day5.substring(0, 3));
        //datestring5.setText("");
        dateno5.setText(monthno5 + "/" + dayno5);

        //day6
        calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -5);
        day6 = (String) DateFormat.format("EEEE", calendar.getTime());
        year6 = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno6 = (String) DateFormat.format("MM", calendar.getTime());
        monthstring6 = (String) DateFormat.format("MMM", calendar.getTime());
        dayno6 = (String) DateFormat.format("dd", calendar.getTime());

        today6 = year6 + "-" + monthno6 + "-" + dayno6;

        datestring6.setText(day6.substring(0, 3));

        dateno6.setText(monthno6 + "/" + dayno6);


        rangedate.setText(monthstring + " " + day + "-" + monthstring6 + " " + dayno6);


//day1 data manipulation
        dataref1.orderByChild("Date").equalTo(today).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();


                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (data.getTemperature() == null || data.getRainfall_1hr() == null) {
                            hightemptoday.setText("");
                            lowtemptoday.setText("");

                        } else if (data.getHumidity() == null) {
                            humiditytoday.setText("");
                        } else {
                            humiditytoday.setText(data.getHumidity() + "%");
                            //rain
                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                            day1rainlist.add(day1rainref);

                            //temp
                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                            list.add(alltemps);


                            Query recent1 = dataref1.orderByChild("Humidity").limitToLast(1);

                            recent1.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                        // Toast.makeText(getContext(), ""+childrensnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                        wetherdata humiditydata1 = childrensnapshot.getValue(wetherdata.class);
                                        Long temp = humiditydata1.getHumidity();

//                                            humiditytoday.setText(data.getHumidity().toString() + "%");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }


                    }
                    //temp
                    Double high1 = Collections.max(list);
                    Double low1 = Collections.min(list);
                    Double avaragetemp1 = (high1 + low1) / 2;

                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1) / 2;


                    lowtemptoday.setText("/" + low1.toString().substring(0, 2) + "\u2103");
                    hightemptoday.setText(high1.toString().substring(0, 2) + "\u2103");


                    if ((avaragetemp1) < -9) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if (avaragetemp1 >= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    todayweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low1.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//day2 data manipulation
        dataref2.orderByChild("Date").equalTo(today2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();


                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (data.getTemperature() == null) {
                            hightemp2.setText("");
                            lowtemp2.setText("");

                        } else if (data.getHumidity() == null) {
                            humidityday2.setText("");
                        } else {
                            humidityday2.setText(data.getHumidity() + "%");
                            //rain
                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                            day1rainlist.add(day1rainref);

                            //Temp
                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                            list.add(alltemps);


                            Query recent2 = dataref2.orderByChild("Humidity").limitToLast(1);

                            recent2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                        // Toast.makeText(getContext(), ""+childrensnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                        wetherdata humiditydata2 = childrensnapshot.getValue(wetherdata.class);
                                        Long temp = humiditydata2.getHumidity();

//                                            humidityday2.setText(temp.toString() + "%");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                        //   }
                    }
                    //temp
                    Double high2 = Collections.max(list);
                    Double low2 = Collections.min(list);
                    Double avaragetemp = (high2 + low2) / 2;


                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1);

                    lowtemp2.setText("/" + low2.toString().substring(0, 2) + "\u2103");
                    hightemp2.setText(high2.toString().substring(0, 2) + "\u2103");


                    if ((avaragetemp) < -9) {

                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp) >= (-9) && (avaragetemp) < 0) {

                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp) >= 0 && (avaragetemp) < 7) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp) >= 7 && (avaragetemp) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp) >= 12 && (avaragetemp) < 18) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp) >= 18 && (avaragetemp) < 24) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp) >= 24 && (avaragetemp) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp) >= 29 && (avaragetemp) < 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if (avaragetemp >= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage2.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low2.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//day3 data manipulation
        dataref3.orderByChild("Date").equalTo(today3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();


                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (data.getTemperature() == null) {
                            lowtemp3.setText("");
                            hightemp3.setText("");

                        } else if (data.getHumidity() == null) {
                            humidityday3.setText("");
                        } else {
                            humidityday3.setText(data.getHumidity() + "%");
                            //rain
                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                            day1rainlist.add(day1rainref);

                            //Temp
                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                            list.add(alltemps);


                            Query recent3 = dataref3.orderByChild("Humidity").limitToLast(1);

                            recent3.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                        // Toast.makeText(getContext(), ""+childrensnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                        wetherdata humiditydata2 = childrensnapshot.getValue(wetherdata.class);
                                        Long temp = humiditydata2.getHumidity();

//                                            humidityday3.setText(temp.toString() + "%");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }


                    }

                    //temp
                    Double high3 = Collections.max(list);
                    Double low3 = Collections.min(list);
                    Double avaragetemp3 = (high3 + low3) / 2;

                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1);

                    lowtemp3.setText("/" + low3.toString().substring(0, 2) + "\u2103");
                    hightemp3.setText(high3.toString().substring(0, 2) + "\u2103");
                    if ((avaragetemp3) < -9) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp3) >= (-9) && (avaragetemp3) < 0) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp3) >= 0 && (avaragetemp3) < 7) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp3) >= 7 && (avaragetemp3) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp3) >= 12 && (avaragetemp3) < 18) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp3) >= 18 && (avaragetemp3) < 24) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp3) >= 24 && (avaragetemp3) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp3) >= 29 && (avaragetemp3) < 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if (avaragetemp3 >= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage3.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low3.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//day4 data manipulation
        dataref4.orderByChild("Date").equalTo(today4).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();


                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (data.equals(null)) {
                            hightemp4.setText("");
                            lowtemp4.setText("");
                        } else if (data.getHumidity() == null) {
                            humidityday4.setText("");
                        } else {

                            if (data.getTemperature() == null) {
                                hightemp4.setText("");
                                lowtemp4.setText("");

                            } else {
                                humidityday4.setText(data.getHumidity() + "%");


                                //rain
                                Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                day1rainlist.add(day1rainref);

                                //Temp
                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                                list.add(alltemps);


                                Query recent4 = dataref4.orderByChild("Humidity").limitToLast(1);

                                recent4.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                            wetherdata humiditydata2 = childrensnapshot.getValue(wetherdata.class);
                                            Long temp = humiditydata2.getHumidity();

//                                                humidityday4.setText(temp.toString() + "%");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                        }


                    }
                    //temp
                    Double high4 = Collections.max(list);
                    Double low4 = Collections.min(list);
                    Double avaragetemp4 = (high4 + low4) / 2;

                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1);

                    lowtemp4.setText("/" + low4.toString().substring(0, 2) + "\u2103");
                    hightemp4.setText(high4.toString().substring(0, 2) + "\u2103");
                    if ((avaragetemp4) < -9) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp4) >= (-9) && (avaragetemp4) < 0) {


                        if ((maxrainfall1) <= 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp4) >= 0 && (avaragetemp4) < 7) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp4) >= 7 && (avaragetemp4) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp4) >= 12 && (avaragetemp4) < 18) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp4) >= 18 && (avaragetemp4) < 24) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp4) >= 24 && (avaragetemp4) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp4) >= 29 && (avaragetemp4) < 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if (avaragetemp4 >= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low4.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //day5 data manipulation
        dataref5.orderByChild("Date").equalTo(today5).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();

                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        //   if ((today4).equalsIgnoreCase(data.getDate())) {

                        if (data.equals(null)) {
                            hightemp5.setText("");
                            lowtemp5.setText("");
                        } else if (data.getHumidity() == null) {
                            humidityday5.setText("");
                        } else {

                            if (data.getTemperature() == null) {
                                hightemp5.setText("");
                                lowtemp5.setText("");

                            } else {

                                humidityday5.setText(data.getHumidity() + "%");

                                //rain
                                Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                day1rainlist.add(day1rainref);

                                //Temp
                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                                list.add(alltemps);


                                Query recent5 = dataref5.orderByChild("Humidity").limitToLast(1);

                                recent5.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                            wetherdata humiditydata2 = childrensnapshot.getValue(wetherdata.class);
                                            Long temp = humiditydata2.getHumidity();

                                            humidityday5.setText(temp.toString() + "%");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                        }

                        //  }
                    }

                    //temp
                    Double high5 = Collections.max(list);
                    Double low5 = Collections.min(list);
                    Double avaragetemp5 = (high5 + low5) / 2;

                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1);

                    lowtemp5.setText("/" + low5.toString().substring(0, 2) + "\u2103");
                    hightemp5.setText(high5.toString().substring(0, 2) + "\u2103");
                    if ((avaragetemp5) < -9) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= (-9) && (avaragetemp5) < 0) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 0 && (avaragetemp5) < 7) {

                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 7 && (avaragetemp5) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 12 && (avaragetemp5) < 18) {

                        if ((maxrainfall1) < 0.4) {

                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 18 && (avaragetemp5) < 24) {


                        if ((maxrainfall1) < 0.4) {

                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 24 && (avaragetemp5) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((avaragetemp5) >= 29 && (avaragetemp5) <= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if (avaragetemp5 > 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage4.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage5.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low5.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //day6 data manipulation
        dataref6.orderByChild("Date").equalTo(today6).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    List<Double> list = new ArrayList<>();
                    List<Double> day1rainlist = new ArrayList<>();


                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                        if (data.equals(null)) {
                            hightemp6.setText("");
                            lowtemp6.setText("");
                        } else {

                            if (data.getTemperature() == null) {
                                hightemp6.setText("");
                                lowtemp6.setText("");

                            } else if (data.getHumidity() == null) {
                                humidityday6.setText("");
                            } else {
                                humidityday6.setText(data.getHumidity() + "%");
                                //temp
                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
                                list.add(alltemps);
                                //rain
                                Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                day1rainlist.add(day1rainref);


                                Query recent6 = dataref6.orderByChild("Humidity").limitToLast(1);

                                recent6.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childrensnapshot : dataSnapshot.getChildren()) {
                                            // Toast.makeText(getContext(), ""+childrensnapshot.getValue(), Toast.LENGTH_SHORT).show();
                                            wetherdata humiditydata2 = childrensnapshot.getValue(wetherdata.class);
                                            Long temp = humiditydata2.getHumidity();

//                                            humidityday6.setText(temp.toString() + "%");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                        }


                    }

                    //temp
                    Double high6 = Collections.max(list);
                    Double low6 = Collections.min(list);
                    Double avaragetemp5 = (high6 + low6) / 2;

                    //rainfall
                    Double maxrainfall1 = Collections.max(day1rainlist);
                    Double minrainfall1 = Collections.min(day1rainlist);
                    Double avaragerainfall1 = (minrainfall1 + maxrainfall1);

                    lowtemp6.setText("/" + low6.toString().substring(0, 2) + "\u2103");
                    hightemp6.setText(high6.toString().substring(0, 2) + "\u2103");
                    if ((avaragetemp5) < -9) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= (-9) && (avaragetemp5) < 0) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 0 && (avaragetemp5) < 7) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 7 && (avaragetemp5) < 12) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 12 && (avaragetemp5) < 18) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 18 && (avaragetemp5) < 24) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 24 && (avaragetemp5) < 29) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((avaragetemp5) >= 29 && (avaragetemp5) < 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if (avaragetemp5 >= 35) {


                        if ((maxrainfall1) < 0.4) {
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);


                        } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((maxrainfall1) >= 8) {


                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    weatherimage6.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    }
                    Log.d("TAG", String.valueOf(list));

                    Log.d("TAG", low6.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //database reference
//        dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
//        dataquery=dataref.orderByChild("Date").equalTo(todaychosen);

        // ValueEventListener eventListener;

        //update data btn
//        updateweatherdata = view.findViewById(R.id.updateweatherdata);
//        updateweatherdata.setVisibility(View.INVISIBLE);


        //viewpager
//        viewPager = view.findViewById(
//                R.id.viewpager);
//        tabLayout = view.findViewById(R.id.tab_layout);

        //tablayout

//        adapter = new TabAdapter(getChildFragmentManager());
//        adapter.addFragment(new todayFragment(), "Today");
//        adapter.addFragment(new yesterdayFragment(), "Yesterday");
//
//        viewPager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(viewPager);

//
//        viewPager = view.findViewById(R.id.viewpager);
//        tabLayout = view.findViewById(R.id.tab_layout);


        //textviews
//
//        tempvalue = view.findViewById(R.id.dailytemptxt);
//        rainfallvalue = view.findViewById(R.id.dailyrainfalltxt);
//        humidityvalue = view.findViewById(R.id.dailyhumiditytxt);
//        windspeedvalue = view.findViewById(R.id.dailywindspeedtxt);
//        barometervalue = view.findViewById(R.id.dailypressuretxt);
//
//
//        inputdate= view.findViewById(R.id.mycalender);
//
//        calendar = Calendar.getInstance();
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(calendar.getTime());
//        day = (String) DateFormat.format("dd", calendar.getTime());
//        year = (String) DateFormat.format("yyyy", calendar.getTime());
//        monthno = (String) DateFormat.format("MM", calendar.getTime());
//        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
//        exacttoday = year + "-" + monthno + "-" + day;
//        inputdate.setText(exacttoday);
//        SimpleDateFormat yesterday = new SimpleDateFormat("yyyy-MM-dd");
//
//        calendar.add(Calendar.DATE, -1);
        // Toast.makeText(getContext(), ""+yesterday.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
//currentTime.format(calendar.getTime());


//       date= new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myorigcalender.set(Calendar.YEAR,year);
//                myorigcalender.set(Calendar.MONTH,month);
//                myorigcalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//                updateLabel();
////                updatedata();
//            }

//
//        };


        //show calender dialog
//        inputdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(getContext(),date,myorigcalender
//                .get(Calendar.YEAR), myorigcalender.get(Calendar.MONTH),
//                        myorigcalender.get(Calendar.DAY_OF_MONTH)).show();
//
//
//
//
//
//
//            }
//        });
//                updateweatherdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataquery=dataref.orderByChild("Date").equalTo(todaychosen);
//                Toast.makeText(getContext(), ""+todaychosen, Toast.LENGTH_SHORT).show();
//                dataquery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            Toast.makeText(getContext(), "exixruuhb", Toast.LENGTH_SHORT).show();
//                            wetherdata data = dataSnapshot.getValue(wetherdata.class);
//                            Toast.makeText(getContext(), "jh"+data.getTemperature(), Toast.LENGTH_SHORT).show();
//                            tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
//                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
//                            humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
//                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
//                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
//                        }
//                        else {
//                            rainfallvalue.setText(0+" mm");
//                            humidityvalue.setText(0+" %");
//                            windspeedvalue.setText(0+" m/s");
//                            barometervalue.setText(0+" Pa");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


        //  }});
//
//
//        updateweatherdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                dataquery.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            wetherdata data = dataSnapshot.getValue(wetherdata.class);
//
//                                                                tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
//                                    rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
//                                    humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
//                                    windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
//                                    barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
//                        }
////                        else {
////                            rainfallvalue.setText(0+" mm");
////                            humidityvalue.setText(0+" %");
////                            windspeedvalue.setText(0+" m/s");
////                            barometervalue.setText(0+" Pa");
////                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });
//
//        updateweatherdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dataref.orderByChild("Date").equalTo(todaychosen)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()){
//                                    Toast.makeText(getContext(), "it does exist at long last", Toast.LENGTH_SHORT).show();
//                                    DatabaseReference legit = FirebaseDatabase.getInstance().getReference();
//                                    legit.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
//                                                if (dataSnapshoti.child(todaychosen).exists()){
//
//                                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//                                                    tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
//                                                    rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
//                                                    humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
//                                                    windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
//                                                    barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
//                                                }
//
//
//                                        }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
////                                else {
////                                    Toast.makeText(getContext(), "it does not exist", Toast.LENGTH_SHORT).show();
////                                    inputdate.setError("Data does not exist");
////                                    tempvalue.setText("");
////                                    rainfallvalue.setText("");
////                                    humidityvalue.setText("");
////                                    windspeedvalue.setText("");
////                                    barometervalue.setText("");
////                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//            }
//        });
//        updateweatherdata.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String finaldate= inputdate.getText().toString();
//              //  Toast.makeText(getContext(), "Chosen is:"+todaychosen, Toast.LENGTH_SHORT).show();
//                dataref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            // int tot = 0;
//                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
//                                if (dataSnapshoti.child(finaldate).exists()){
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//                                    tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
//                                    rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
//                                    humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
//                                    windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
//                                    barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
//                                }
//
//                              else {
//                                                                        inputdate.setError("Data does not exist");
//                                    tempvalue.setText("");
//                                    rainfallvalue.setText("");
//                                    humidityvalue.setText("");
//                                    windspeedvalue.setText("");
//                                    barometervalue.setText("");
//                                }
//
////                                if (!((finaldate).equalsIgnoreCase(data.getDate()))){
////
////                                    //tot = tot + (data.getHumidity().intValue());
////                                    //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();
////
////
////                                    inputdate.setError("Data does not exist");
////                                    tempvalue.setText("");
////                                    rainfallvalue.setText("");
////                                    humidityvalue.setText("");
////                                    windspeedvalue.setText("");
////                                    barometervalue.setText("");
////
////
////                                }
////                                else{
////
////                                    tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
////                                    rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
////                                    humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
////                                    windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
////                                    barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
////
////                                }
////                                 if (!((todaychosen).equalsIgnoreCase(data.getDate()))){
////
////                                    tempvalue.setVisibility(View.INVISIBLE);
////                                    rainfallvalue.setVisibility(View.INVISIBLE);
////                                    humidityvalue.setVisibility(View.INVISIBLE);
////                                    barometervalue.setVisibility(View.INVISIBLE);
////                                    barometervalue.setVisibility(View.INVISIBLE);
////
//////                                    rainfallvalue.setText(0+" mm");
//////                                    humidityvalue.setText(0+" %");
//////                                    windspeedvalue.setText(0+" m/s");
//////                                    barometervalue.setText(0+" Pa");
////                                }
////                                else {
////                                    Toast.makeText(getContext(), "Date is not  correct", Toast.LENGTH_LONG).show();
////                                    tempvalue.setText(0 +" \u2103");
////                                    rainfallvalue.setText(0+" mm");
////                                    humidityvalue.setText(0+" %");
////                                    windspeedvalue.setText(0+" m/s");
////                                    barometervalue.setText(0+" Pa");
////                                }
//
//
//
//                            }
//
//                        } else {
//                            Toast.makeText(getContext(), "Data does not exist", Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//
//            }
//        });


        return view;
    }

//    private void updatedata() {
//
//
//
//    }


//    private void updateLabel() {
////        String myFormat ="yy-MM-dd";
//        //SimpleDateFormat simpleDateFormat =new SimpleDateFormat(myFormat);
//
//        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
//        saveCurrentTime = currentTime.format(myorigcalender.getTime());
//        day = (String) DateFormat.format("dd", myorigcalender.getTime());
//        year = (String) DateFormat.format("yyyy", myorigcalender.getTime());
//        monthno = (String) DateFormat.format("MM", myorigcalender.getTime());
//        monthstring = (String) DateFormat.format("MMM", myorigcalender.getTime());
//        todaychosen = year+"-"+monthno+"-"+day;
//        inputdate.setText(todaychosen);
//        updateweatherdata.setVisibility(View.VISIBLE);
//
//
//
//
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
//        ValueEventListener eventListener;
//
//        dataref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // int tot = 0;
//                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                    for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//                        if ((exacttoday).equalsIgnoreCase(data.getDate())){
//
//                            //tot = tot + (data.getHumidity().intValue());
//                            //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();
//
//
//                            tempvalue.setText(String.valueOf(data.getTemperature()) + " \u2103");
//                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr()) + " mm");
//                            humidityvalue.setText(String.valueOf(data.getHumidity()) + " %");
//                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed()) + " m/s");
//                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure()) + " Pa");
//
//
//                        } else {
//                            tempvalue.setText(0 + " \u2103");
//                            rainfallvalue.setText(0 + " mm");
//                            humidityvalue.setText(0 + " %");
//                            windspeedvalue.setText(0 + " m/s");
//                            barometervalue.setText(0 + " Pa");
//                        }
//
//
//                    }
//
//                } else {
//                    //Toast.makeText(getContext(), "Data does not exist", Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }


}
