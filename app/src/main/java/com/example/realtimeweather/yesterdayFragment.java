package com.example.realtimeweather;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realtimeweather.models.wetherdata;
import com.example.realtimeweather.views.dataviews;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class yesterdayFragment extends Fragment {
    ListView list;

    public yesterdayFragment() {
    }

    RecyclerView todayweatherlist;
    String saveCurrentTime, monthno, day, year, monthstring, today, yes;
    Calendar calendar;
    Query todaylistquery;
    DatabaseReference dataref;
    LinearLayoutManager todaylistlayoutmanager;
    SimpleDateFormat yesterday;

    Handler mHandler;
    Runnable myrun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.today, container, false);
        todayweatherlist = view.findViewById(R.id.todaylist);

        todaylistlayoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        todayweatherlist.setLayoutManager(todaylistlayoutmanager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(todayweatherlist.getContext(),
                todaylistlayoutmanager.getOrientation());
        todayweatherlist.addItemDecoration(dividerItemDecoration);

//        calendar = Calendar.getInstance();
//        yesterday = new SimpleDateFormat("yyyy-MM-dd");
//        calendar.add(Calendar.DATE, -1);
//        yes = yesterday.format(calendar.getTime());

        calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        day = (String) DateFormat.format("dd", calendar.getTime());
        year = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno = (String) DateFormat.format("MM", calendar.getTime());
        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
        today = year + "-" + monthno + "-" + day;
        FirebaseRecyclerOptions<wetherdata> options = new FirebaseRecyclerOptions.Builder<wetherdata>()
                .setQuery(todaylistquery.orderByChild("Date").equalTo(today), wetherdata.class)
                .build();
        FirebaseRecyclerAdapter<wetherdata, dataviews> adapter = new FirebaseRecyclerAdapter<wetherdata, dataviews>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final dataviews dataviews, int i, @NonNull wetherdata wetherdata) {


                if (wetherdata.getTemperature() == null) {
                    dataviews.temptext.setText("0");
                } else {
                    dataviews.datetxt.setText(wetherdata.getDate() + ">" + wetherdata.getTime());
                    dataviews.humiditydailytxt.setText(String.valueOf(wetherdata.getHumidity()) + " %");
                    dataviews.generaltxt.setText("Hot");
                    dataviews.windtxt.setText(String.valueOf(wetherdata.getM_Windspeed()) + " m/s");
                    dataviews.pressuretxt.setText(String.valueOf(wetherdata.getBarometric_Pressure()) + " Pa");
                    dataviews.temptext.setText(String.valueOf(wetherdata.getTemperature()) + "\u2103");
                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);


                    Long temp = wetherdata.getTemperature();


                    int tempint = temp.intValue();
                    if ((tempint) < -9) {
                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.frigidweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.icy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.frigid_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.frigid_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.frigid_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((tempint) >= (-9) && (tempint) < 0) {
                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.freezingweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.iceberg);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.freezing_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.hails);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.freezing_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.hails);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.freezing_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((tempint) >= 0 && (tempint) < 7) {
                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.verycoldweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.snow);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.verycold_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.verycold_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.verycold_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((tempint) >= 7 && (tempint) < 12) {

                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.coldweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.snowstorm);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.cold_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.cold_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.cold_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((tempint) >= 12 && (tempint) < 18) {

                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.coolweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.partly_cloudy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.cool_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.partlyrainy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.cool_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.cool_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((tempint) >= 18 && (tempint) < 24) {
                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.comfortableweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.comfortable_weather);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.comfortable_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.partlyrainy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.comfortable_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.comfortable_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }


                    } else if ((wetherdata.getTemperature().intValue()) > 24 && (wetherdata.getTemperature().intValue()) < 29) {

                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.warmweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.smilly_sun);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.warm_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.warm_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.warm_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }

                    } else if ((wetherdata.getTemperature().intValue()) >= 29 && (wetherdata.getTemperature().intValue()) < 35) {


                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.hotweather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.sunny);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.hot_moderaterain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.partlyrainy);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.heavy_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.hot_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }
                    } else if ((wetherdata.getTemperature().intValue()) > 35) {


                        if ((wetherdata.getRainfall_1hr().intValue()) < 0.4) {
                            dataviews.generaltxt.setText(R.string.sweltering_weather);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.sunny_day);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);
                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 0.5 && (wetherdata.getRainfall_1hr().intValue()) < 4) {

                            dataviews.generaltxt.setText(R.string.moderate_rain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.light_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 4 && (wetherdata.getRainfall_1hr().intValue()) < 8) {

                            dataviews.generaltxt.setText(R.string.sweltering_veryheavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.rainfall1);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        } else if ((wetherdata.getRainfall_1hr().intValue()) > 8) {

                            dataviews.generaltxt.setText(R.string.sweltering_heavyrain);
                            mHandler = new Handler();
                            myrun = new Runnable() {
                                @Override
                                public void run() {
                                    dataviews.dailyweatherimage.setImageResource(R.drawable.heavy_rain);
                                }
                            };

                            mHandler.postDelayed(myrun, 00L);

                        }
                    }
                }


            }

            @NonNull
            @Override
            public dataviews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weathercontent, parent, false);
                dataviews holder = new dataviews(view);
                return holder;
            }
        };
        todayweatherlist.setAdapter(adapter);
        adapter.startListening();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
        todaylistquery = dataref;


    }

}


