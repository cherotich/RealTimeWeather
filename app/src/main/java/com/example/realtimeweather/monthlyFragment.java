package com.example.realtimeweather;

import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realtimeweather.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.realtimeweather.models.wetherdata;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.EventListener;
import java.util.List;

public class monthlyFragment extends Fragment {
    //    LineChart templinechart;
//    LineDataSet templinedataset = new LineDataSet(null, null);
//    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
//    LineData lineData;


    // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
//    LineGraphSeries series;
//    GraphView graphView;

    public monthlyFragment() {
        // Required empty public constructor
    }

    //week 1
    Query week1, week2, week2evequery, wk2evening;
    DatabaseReference week2reference;
    DatabaseReference dataref;
    LinearLayout contentlayout;
    ImageView todaybtn;
    Spinner choosemonth;
    TextView chooseyear, week1range, hightempweek1, lowtempweek1;
    String[] result;
    String mystring;
    ImageView week1image;
    CardView week1card;
    String weeklyrange;

    ArrayAdapter<CharSequence> arrayAdapter;
    Calendar calendar;
    String saveCurrentTime, monthno, day, todayno, year, monthstring, today, exacttoday;
    String month, truncmonth;
    String monthspinnernumber = "";
    Handler mHandler;
    Runnable myrun;

    //current month
    int indexofmonth;


    //refresh
    Button refresh;

    //week2
    ImageView week2btn;
    CardView week2card;
    LinearLayout contentlayoutwk2;
    TextView lowtempweek2, hightempweek2;

    //week3
    CardView week3card;
    ImageView week3image;
    LinearLayout contentlayoutwk3;

    //week4
    CardView week4card;
    ImageView week4btn;
    LinearLayout contentlayoutwk4;

    //range
    TextView week2range, week3range, week4range;

    //week1 morning content
    TextView mngavrainfallweek1, mngavtempweek1, mngavpressureweek1, mngavhumidityweek1;

    //week1 afternoon content
    TextView aftavrainfallweek1, aftavtempweek1, aftavpressureweek1, aftavhumidityweek1;

    //week2 morning content
    TextView mngavhumiditywk2, mngavpressurewk2, mngavtempwk2, mngavrainfallwk2;
    //week2 afternoon content
    TextView aftavhumiditywk2, aftavpressurewk2, aftavtempwk2, aftavrainfallwk2;

    //week2 evening content
    TextView evnavhumidwk2, evnavpressurewk2, evnavtempwk2, evnavrainfallwk2;

    //current time
    Query currentweek1, currentweek2, currentweek3, currentweek4, currentmonth;


    //week6
    Query week1June, week2June, week3June, week4June;

    //weekdata
    Query week1data, week2data, week3data, week4data;

    //general weekly weather
    TextView generalweatherweek1, weatherwk2, weatherwk3, weatherwk4;
    ImageView week2image, week3weatherimage, week4weatherimage;
    TextView hightempweek3, lowtempweek3, hightempweek4, lowtempweek4;

///general monthly data

    TextView monthlytemptxt, monthlyrainfalltxt, monthlyhumiditytxt, monthlypressuretxt, monthlywindspeedtxt;

    //days of the month
    Query day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15, day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28, day29, day30, day31;

    //specific date contents
    TextView moningcontent, date2, date3, date4, date5, date6, date7, date8, date9, date10, date11, date12, date13, date14, date15, date16, date17, date18, date19, date20, date21;
    TextView day3temperature, day3rainfall, day3pressure, day3humidity, day4temperature, day4rainfall, day4pressure, day4humidity;
    TextView day5temperature, day5rainfall, day5pressure, day5humidity, day6temperature, day6rainfall, day6pressure, day6humidity;
    TextView day7temperature, day7rainfall, day7pressure, day7humidity, day11temperature, day11rainfall, day11pressure, day11humidity;
    TextView day12temperature, day12rainfall, day12pressure, day12humidity, day13temperature, day13rainfall, day13pressure, day13humidity;
    TextView day14temperature, day14rainfall, day14pressure, day14humidity, day15temperature, day15rainfall, day15pressure, day15humidity;
    TextView day16temperature, day16rainfall, day16pressure, day16humidity, day17temperature, day17rainfall, day17pressure, day17humidity;
    TextView day18temperature, day18rainfall, day18pressure, day18humidity, day19temperature, day19rainfall, day19pressure, day19humidity;
    TextView day20temperature, day20rainfall, day20pressure, day20humidity, day21temperature, day21rainfall, day21pressure, day21humidity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.monthly_fragment, container, false);


        //specific date content
        //week1
        moningcontent = view.findViewById(R.id.date1);
        date2 = view.findViewById(R.id.date2);
        date3 = view.findViewById(R.id.date3);
        day3temperature = view.findViewById(R.id.day3temperature);
        day3rainfall = view.findViewById(R.id.day3rainfall);
        day3pressure = view.findViewById(R.id.day3pressure);
        day3humidity = view.findViewById(R.id.day3humidity);

        date4 = view.findViewById(R.id.date4);
        day4temperature = view.findViewById(R.id.day4temperature);
        day4rainfall = view.findViewById(R.id.day4rainfall);
        day4pressure = view.findViewById(R.id.day4pressure);
        day4humidity = view.findViewById(R.id.day4humidity);

        date5 = view.findViewById(R.id.date5);
        day5temperature = view.findViewById(R.id.day5temperature);
        day5rainfall = view.findViewById(R.id.day5rainfall);
        day5pressure = view.findViewById(R.id.day5pressure);
        day5humidity = view.findViewById(R.id.day5humidity);

        date6 = view.findViewById(R.id.date6);
        day6temperature = view.findViewById(R.id.day6temperature);
        day6rainfall = view.findViewById(R.id.day6rainfall);
        day6pressure = view.findViewById(R.id.day6pressure);
        day6humidity = view.findViewById(R.id.day6humidity);

        date7 = view.findViewById(R.id.date7);
        day7temperature = view.findViewById(R.id.day7temperature);
        day7rainfall = view.findViewById(R.id.day7rainfall);
        day7pressure = view.findViewById(R.id.day7pressure);
        day7humidity = view.findViewById(R.id.day7humidity);

        //week2
        //day8
        date8 = view.findViewById(R.id.date8);
        //day9
        date9 = view.findViewById(R.id.date9);
        //day10
        date10 = view.findViewById(R.id.date10);

        //day11
        date11 = view.findViewById(R.id.date11);
        day11temperature = view.findViewById(R.id.day11temperature);
        day11rainfall = view.findViewById(R.id.day11rainfall);
        day11pressure = view.findViewById(R.id.day11pressure);
        day11humidity = view.findViewById(R.id.day11humidity);

        //day12
        date12 = view.findViewById(R.id.date12);
        day12temperature = view.findViewById(R.id.day12temperature);
        day12rainfall = view.findViewById(R.id.day12rainfall);
        day12pressure = view.findViewById(R.id.day12pressure);
        day12humidity = view.findViewById(R.id.day12humidity);

        //day13
        date13 = view.findViewById(R.id.date13);
        day13temperature = view.findViewById(R.id.day13temperature);
        day13rainfall = view.findViewById(R.id.day13rainfall);
        day13pressure = view.findViewById(R.id.day13pressure);
        day13humidity = view.findViewById(R.id.day13humidity);

        //day14
        date14 = view.findViewById(R.id.date14);
        day14temperature = view.findViewById(R.id.day14temperature);
        day14rainfall = view.findViewById(R.id.day14rainfall);
        day14pressure = view.findViewById(R.id.day14pressure);
        day14humidity = view.findViewById(R.id.day14humidity);

        //week3

        //day15
        date15 = view.findViewById(R.id.date15);
        day15temperature = view.findViewById(R.id.day15temperature);
        day15rainfall = view.findViewById(R.id.day15rainfall);
        day15pressure = view.findViewById(R.id.day15pressure);
        day15humidity = view.findViewById(R.id.day15humidity);

        //day16
        date16 = view.findViewById(R.id.date16);
        day16temperature = view.findViewById(R.id.day16temperature);
        day16rainfall = view.findViewById(R.id.day16rainfall);
        day16pressure = view.findViewById(R.id.day16pressure);
        day16humidity = view.findViewById(R.id.day16humidity);


        //day17
        date17 = view.findViewById(R.id.date17);
        day17temperature = view.findViewById(R.id.day17temperature);
        day17rainfall = view.findViewById(R.id.day17rainfall);
        day17pressure = view.findViewById(R.id.day17pressure);
        day17humidity = view.findViewById(R.id.day17humidity);

        //day18
        date18 = view.findViewById(R.id.date18);
        day18temperature = view.findViewById(R.id.day18temperature);
        day18rainfall = view.findViewById(R.id.day18rainfall);
        day18pressure = view.findViewById(R.id.day18pressure);
        day18humidity = view.findViewById(R.id.day18humidity);

        //day19
        date19 = view.findViewById(R.id.date19);
        day19temperature = view.findViewById(R.id.day19temperature);
        day19rainfall = view.findViewById(R.id.day19rainfall);
        day19pressure = view.findViewById(R.id.day19pressure);
        day19humidity = view.findViewById(R.id.day19humidity);

        //day20
        date20 = view.findViewById(R.id.date20);
        day20temperature = view.findViewById(R.id.day20temperature);
        day20rainfall = view.findViewById(R.id.day20rainfall);
        day20pressure = view.findViewById(R.id.day20pressure);
        day20humidity = view.findViewById(R.id.day20humidity);

        //day21
        date21 = view.findViewById(R.id.date21);
        day21temperature = view.findViewById(R.id.day21temperature);
        day21rainfall = view.findViewById(R.id.day21rainfall);
        day21pressure = view.findViewById(R.id.day21pressure);
        day21humidity = view.findViewById(R.id.day21humidity);


        //general weekly data
        generalweatherweek1 = view.findViewById(R.id.generalweatherweek1);

        //week 2 general data
        hightempweek2 = view.findViewById(R.id.hightempweek2);
        lowtempweek2 = view.findViewById(R.id.lowtempweek2);
        weatherwk2 = view.findViewById(R.id.weatherwk2);
        week2image = view.findViewById(R.id.week2image);

        //week 3 general data
        week3weatherimage = view.findViewById(R.id.week3image);
        weatherwk3 = view.findViewById(R.id.weatherwk3);
        lowtempweek3 = view.findViewById(R.id.lowtempweek3);
        hightempweek3 = view.findViewById(R.id.hightempweek3);

        //week 4 general data
        week4weatherimage = view.findViewById(R.id.week4weatherimage);
        lowtempweek4 = view.findViewById(R.id.lowtempweek4);
        hightempweek4 = view.findViewById(R.id.hightempweek4);
        weatherwk4 = view.findViewById(R.id.weatherwk4);

        //general monthly data
        monthlywindspeedtxt = view.findViewById(R.id.monthlywindspeedtxt);
        monthlypressuretxt = view.findViewById(R.id.monthlypressuretxt);
        monthlyhumiditytxt = view.findViewById(R.id.monthlyhumiditytxt);
        monthlyrainfalltxt = view.findViewById(R.id.monthlyrainfalltxt);
        monthlytemptxt = view.findViewById(R.id.monthlytemptxt);


        calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        day = (String) DateFormat.format("dd", calendar.getTime());
        year = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno = (String) DateFormat.format("MM", calendar.getTime());
        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
        today = year + "-" + monthno + "-" + day;
        todayno = (String) DateFormat.format("EEEE", calendar.getTime());

        dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
        week2 = dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-14");
        wk2evening = dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00");

        //refresh
        refresh = view.findViewById(R.id.refresh);

        //current time
        indexofmonth = Calendar.getInstance().get(Calendar.MONTH);

        //currentmonth = dataref.orderByChild("Date").startAt(year + "-"+indexofmonth+"-01").endAt(year + "-"+indexofmonth+"-28");

        choosemonth = view.findViewById(R.id.choosemonth);
        chooseyear = view.findViewById(R.id.choseyear);

        contentlayout = view.findViewById(R.id.contentlayout);
        todaybtn = view.findViewById(R.id.todaybtn);

        week1range = view.findViewById(R.id.week1range);
        lowtempweek1 = view.findViewById(R.id.lowtempweek1);
        hightempweek1 = view.findViewById(R.id.hightempweek1);
        week1image = view.findViewById(R.id.week1image);
        week1card = view.findViewById(R.id.week1card);
//week 2 mrng content
        mngavhumiditywk2 = view.findViewById(R.id.mngavhumiditywk2);
        mngavpressurewk2 = view.findViewById(R.id.mngavpressurewk2);
        mngavtempwk2 = view.findViewById(R.id.mngavtempwk2);
        mngavrainfallwk2 = view.findViewById(R.id.mngavrainfallwk2);
//week 2 afternoon content
        aftavhumiditywk2 = view.findViewById(R.id.aftavhumiditywk2);
        aftavpressurewk2 = view.findViewById(R.id.aftavpressurewk2);
        aftavtempwk2 = view.findViewById(R.id.aftavtempwk2);
        aftavrainfallwk2 = view.findViewById(R.id.aftavrainfallwk2);
        //week 2 evening content
        evnavhumidwk2 = view.findViewById(R.id.evnavhumidwk2);
        evnavpressurewk2 = view.findViewById(R.id.evnavpressurewk2);
        evnavtempwk2 = view.findViewById(R.id.evnavtempwk2);
        evnavrainfallwk2 = view.findViewById(R.id.evnavrainfallwk2);

        //week1 morning content
        mngavhumidityweek1 = view.findViewById(R.id.mngavhumidityweek1);
        mngavpressureweek1 = view.findViewById(R.id.mngavpressureweek1);
        mngavtempweek1 = view.findViewById(R.id.mngavtempweek1);
        mngavrainfallweek1 = view.findViewById(R.id.mngavrainfallweek1);

        //week1 afternoon content
        aftavhumidityweek1 = view.findViewById(R.id.aftavhumidityweek1);
        aftavpressureweek1 = view.findViewById(R.id.aftavpressureweek1);
        aftavtempweek1 = view.findViewById(R.id.aftavtempweek1);
        aftavrainfallweek1 = view.findViewById(R.id.aftavrainfallweek1);


        //range
        week4range = view.findViewById(R.id.week4range);
        week3range = view.findViewById(R.id.week3range);
        week2range = view.findViewById(R.id.week2range);


        //week1
        contentlayout.setVisibility(View.GONE);


        week1card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contentlayout.setVisibility(View.VISIBLE);
                contentlayoutwk2.setVisibility(View.GONE);
                contentlayoutwk4.setVisibility(View.GONE);
                contentlayoutwk3.setVisibility(View.GONE);
                contentlayoutwk4.setVisibility(View.GONE);
                week4btn.setImageResource(R.drawable.expandup);
                week2btn.setImageResource(R.drawable.expandup);
                week3image.setImageResource(R.drawable.expandup);
                if (contentlayout.getVisibility() == View.VISIBLE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            todaybtn.setImageResource(R.drawable.expandup);


                        }
                    };
                    mHandler.postDelayed(myrun, 00L);
                    contentlayout.setVisibility(View.GONE);

                } else if (contentlayout.getVisibility() == View.GONE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            todaybtn.setImageResource(R.drawable.expanddown);


                        }
                    };

                    mHandler.postDelayed(myrun, 00L);
                    contentlayout.setVisibility(View.VISIBLE);
                }

            }
        });


        //week2
        week2btn = view.findViewById(R.id.week2btn);
        week2card = view.findViewById(R.id.week2card);
        contentlayoutwk2 = view.findViewById(R.id.contentlayoutwk2);
        contentlayoutwk2.setVisibility(View.GONE);


        week2card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contentlayout.setVisibility(View.VISIBLE);
                contentlayout.setVisibility(View.GONE);
                contentlayoutwk4.setVisibility(View.GONE);
                contentlayoutwk3.setVisibility(View.GONE);
                contentlayoutwk4.setVisibility(View.GONE);
                todaybtn.setImageResource(R.drawable.expandup);
                week4btn.setImageResource(R.drawable.expandup);
                week3image.setImageResource(R.drawable.expandup);
                if (contentlayoutwk2.getVisibility() == View.VISIBLE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week2btn.setImageResource(R.drawable.expandup);


                        }
                    };
                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk2.setVisibility(View.GONE);

                } else if (contentlayoutwk2.getVisibility() == View.GONE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week2btn.setImageResource(R.drawable.expanddown);


                        }
                    };

                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk2.setVisibility(View.VISIBLE);
                }

            }
        });


        //week3
        week3card = view.findViewById(R.id.week3card);
        week3image = view.findViewById(R.id.week3btn);
        contentlayoutwk3 = view.findViewById(R.id.contentlayoutwk3);
        contentlayoutwk3.setVisibility(View.GONE);


        week3card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contentlayout.setVisibility(View.VISIBLE);
                contentlayout.setVisibility(View.GONE);
                contentlayoutwk2.setVisibility(View.GONE);
                contentlayoutwk4.setVisibility(View.GONE);
//                contentlayoutwk4.setVisibility(View.GONE);
                todaybtn.setImageResource(R.drawable.expandup);
                week2btn.setImageResource(R.drawable.expandup);
                week4btn.setImageResource(R.drawable.expandup);
                if (contentlayoutwk3.getVisibility() == View.VISIBLE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week3image.setImageResource(R.drawable.expandup);


                        }
                    };
                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk3.setVisibility(View.GONE);

                } else if (contentlayoutwk3.getVisibility() == View.GONE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week3image.setImageResource(R.drawable.expanddown);


                        }
                    };

                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk3.setVisibility(View.VISIBLE);
                }

            }
        });


        //week4
        week4card = view.findViewById(R.id.week4card);
        contentlayoutwk4 = view.findViewById(R.id.contentlayoutwk4);
        week4btn = view.findViewById(R.id.week4btn);
        contentlayoutwk4.setVisibility(View.GONE);

        week4card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                contentlayout.setVisibility(View.VISIBLE);
                contentlayout.setVisibility(View.GONE);
                contentlayoutwk2.setVisibility(View.GONE);
                contentlayoutwk3.setVisibility(View.GONE);
//                contentlayoutwk4.setVisibility(View.GONE);
                todaybtn.setImageResource(R.drawable.expandup);
                week2btn.setImageResource(R.drawable.expandup);
                week3image.setImageResource(R.drawable.expandup);
                if (contentlayoutwk4.getVisibility() == View.VISIBLE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week4btn.setImageResource(R.drawable.expandup);


                        }
                    };
                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk4.setVisibility(View.GONE);

                } else if (contentlayoutwk4.getVisibility() == View.GONE) {
                    mHandler = new Handler();
                    myrun = new Runnable() {
                        @Override
                        public void run() {
                            week4btn.setImageResource(R.drawable.expanddown);


                        }
                    };

                    mHandler.postDelayed(myrun, 00L);
                    contentlayoutwk4.setVisibility(View.VISIBLE);
                }

            }
        });


        chooseyear.setText(year);
        arrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.months, android.R.layout.simple_spinner_dropdown_item);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choosemonth.setAdapter(arrayAdapter);
        choosemonth.setSelection(indexofmonth);


        choosemonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (id == 0) {
//                    month = parent.getItemAtPosition(position).toString();
//                    truncmonth = month.substring(0, 3);

                    monthspinnernumber = "01";
//                    week1range.setText("Jan 01 to Jan 07");
//                    week2range.setText("Jan 08 to Jan 14");
//                    week3range.setText("Jan 15 to Jan 21");
//                    week4range.setText("Jan 22 to Jan 30");
                }
                if (id == 1) {

                    monthspinnernumber = "02";
                    //range
//                    week1range.setText("Feb 01 to Feb 07");
//                    week2range.setText("Feb 08 to Feb 14");
//                    week3range.setText("Feb 15 to Feb 21");
//                    week4range.setText("Feb 22 to Feb 28");
                }
                if (id == 2) {

                    monthspinnernumber = "03";
                    //range
//                    week1range.setText("Mar 01 to Mar 07");
//                    week2range.setText("Mar 08 to Mar 14");
//                    week3range.setText("Mar 15 to Mar 21");
//                    week4range.setText("Mar 22 to Mar 30");
                }
                if (id == 3) {

                    monthspinnernumber = "04";
                    //range
//            week1range.setText("Apr 01 to Apr 07");
//            week2range.setText("Apr 08 to Apr 14");
//            week3range.setText("Apr 15 to Apr 21");
//            week4range.setText("Apr 22 to Apr 30");
                }
                if (id == 4) {

                    monthspinnernumber = "05";
                    //range
//            week1range.setText("May 01 to May 07");
//            week2range.setText("May 08 to May 14");
//            week3range.setText("May 15 to May 21");
//            week4range.setText("May 22 to May 30");
                }
                if (id == 5) {

                    monthspinnernumber = "06";
                    //range
//            week1range.setText("Jun 01 to Jun 07");
//            week2range.setText("Jun 08 to Jun 14");
//            week3range.setText("Jun 15 to Jun 21");
//            week4range.setText("Jun 22 to Jun 30");
                }
                if (id == 6) {

                    monthspinnernumber = "07";
                    //range
//            week1range.setText("Jul 01 to Jul 07");
//            week2range.setText("Jul 08 to Jul 14");
//            week3range.setText("Jul 15 to Jul 21");
//            week4range.setText("Jul 22 to Jul 30");
                }
                if (id == 7) {

                    monthspinnernumber = "08";
                    //range
//            week1range.setText("Aug 01 to Aug 07");
//            week2range.setText("Aug 08 to Aug 14");
//            week3range.setText("Aug 15 to Aug 21");
//            week4range.setText("Aug 22 to Aug 30");
                }
                if (id == 8) {

                    monthspinnernumber = "09";
                    //range
//            week1range.setText("Sept 01 to Sept 07");
//            week2range.setText("Sept 08 to Sept 14");
//            week3range.setText("Sept 15 to Sept 21");
//            week4range.setText("Sept 22 to Sept 30");
                }
                if (id == 9) {

                    monthspinnernumber = "10";
                    //range
//            week1range.setText("Oct 01 to Oct 07");
//            week2range.setText("Oct 08 to Oct 14");
//            week3range.setText("Oct 15 to Oct 21");
//            week4range.setText("Oct 22 to Oct 30");
                }
                if (id == 10) {

                    monthspinnernumber = "11";
                    //range
//            week1range.setText("Nov 01 to Nov 07");
//            week2range.setText("Nov 08 to Nov 14");
//            week3range.setText("Nov 15 to Nov 21");
//            week4range.setText("Nov 22 to Nov 30");
                }
                if (id == 11) {

                    monthspinnernumber = "12";
                    //range
//            week1range.setText("Dec 01 to Dec 07");
//            week2range.setText("Dec 08 to Dec 14");
//            week3range.setText("Dec 15 to Dec 21");
//            week4range.setText("Dec 22 to Dec 30");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//refresh
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (monthspinnernumber == "01") {

                    week1range.setText("Jan 01 to Jan 07");
                    week2range.setText("Jan 08 to Jan 14");
                    week3range.setText("Jan 15 to Jan 21");
                    week4range.setText("Jan 22 to Jan 30");
                }

                if (monthspinnernumber == "02") {


                    //range
                    week1range.setText("Feb 01 to Feb 07");
                    week2range.setText("Feb 08 to Feb 14");
                    week3range.setText("Feb 15 to Feb 21");
                    week4range.setText("Feb 22 to Feb 30");
                }
                if (monthspinnernumber == "03") {


                    //range
                    week1range.setText("Mar 01 to Mar 07");
                    week2range.setText("Mar 08 to Mar 14");
                    week3range.setText("Mar 15 to Mar 21");
                    week4range.setText("Mar 22 to Mar 30");
                }

                if (monthspinnernumber == "04") {


                    //range
                    week1range.setText("Apr 01 to Apr 07");
                    week2range.setText("Apr 08 to Apr 14");
                    week3range.setText("Apr 15 to Apr 21");
                    week4range.setText("Apr 22 to Apr 30");
                }

                if (monthspinnernumber == "05") {


                    //range
                    week1range.setText("May 01 to May 07");
                    week2range.setText("May 08 to May 14");
                    week3range.setText("May 15 to May 21");
                    week4range.setText("May 22 to May 30");
                }

                if (monthspinnernumber == "06") {


                    //range
                    week1range.setText("Jun 01 to Jun 07");
                    week2range.setText("Jun 08 to Jun 14");
                    week3range.setText("Jun 15 to Jun 21");
                    week4range.setText("Jun 22 to Jun 30");


                    // general june weather
                }

                if (monthspinnernumber == "07") {


                    //range
                    week1range.setText("Jul 01 to Jul 07");
                    week2range.setText("Jul 08 to Jul 14");
                    week3range.setText("Jul 15 to Jul 21");
                    week4range.setText("Jul 22 to Jul 30");
                }

                if (monthspinnernumber == "08") {


                    //range
                    week1range.setText("Aug 01 to Aug 07");
                    week2range.setText("Aug 08 to Aug 14");
                    week3range.setText("Aug 15 to Aug 21");
                    week4range.setText("Aug 22 to Aug 30");
                }

                if (monthspinnernumber == "09") {

                    //range
                    week1range.setText("Sept 01 to Sept 07");
                    week2range.setText("Sept 08 to Sept 14");
                    week3range.setText("Sept 15 to Sept 21");
                    week4range.setText("Sept 22 to Sept 30");
                }

                if (monthspinnernumber == "10") {

                    //range
                    week1range.setText("Oct 01 to Oct 07");
                    week2range.setText("Oct 08 to Oct 14");
                    week3range.setText("Oct 15 to Oct 21");
                    week4range.setText("Oct 22 to Oct 30");
                }

                if (monthspinnernumber == "11") {


                    //range
                    week1range.setText("Nov 01 to Nov 07");
                    week2range.setText("Nov 08 to Nov 14");
                    week3range.setText("Nov 15 to Nov 21");
                    week4range.setText("Nov 22 to Nov 30");
                }

                if (monthspinnernumber == "12") {


                    //range
                    week1range.setText("Dec 01 to Dec 07");
                    week2range.setText("Dec 08 to Dec 14");
                    week3range.setText("Dec 15 to Dec 21");
                    week4range.setText("Dec 22 to Dec 30");
                }


//general

                dataref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> windspeedlist = new ArrayList<>();
                            final List<Double> pressurelist = new ArrayList<>();
                            final List<Double> humiditylist = new ArrayList<>();
                            final List<Double> rainlist = new ArrayList<>();
                            final List<Double> junetemplist = new ArrayList<>();
                            for (DataSnapshot dataSnapshoti : childrensnapshot) {


                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                                mystring = data.getDate();

                                if (mystring == null) {

                                } else if (data.getTemperature() == null) {
                                    monthlytemptxt.setText("");

                                } else if (data.getHumidity() == null) {

                                    monthlyhumiditytxt.setText("");

                                } else if (data.getBarometric_Pressure() == null) {

                                    monthlypressuretxt.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    monthlyrainfalltxt.setText(0 + " mm");

                                } else if (data.getM_Windspeed() == null) {
                                    monthlywindspeedtxt.setText(0 + " m/s");
                                } else {

                                    if (((mystring.substring(5, 7)).equals(monthspinnernumber)) && ((mystring.substring(0, 4)).equals(year))) {


                                        //temperature
                                        Double junetemp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                        junetemplist.add(junetemp);

                                        //rainfall
                                        Double rainlistvalue = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                        rainlist.add(rainlistvalue);

                                        //humidity
                                        Double humiditylistvalue = dataSnapshoti.child("Humidity").getValue(Double.class);
                                        humiditylist.add(humiditylistvalue);

                                        //pressure
                                        Double presssurelistvalue = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                        pressurelist.add(presssurelistvalue);

                                        //windspeed
                                        Double windspeedlistvalue = dataSnapshoti.child("M_Windspeed").getValue(Double.class);
                                        windspeedlist.add(windspeedlistvalue);


                                    }


                                }
                            }

                            if (junetemplist.isEmpty() || rainlist.isEmpty() || humiditylist.isEmpty() || pressurelist.isEmpty() || windspeedlist.isEmpty()) {

                                monthlytemptxt.setText(0 + "\u2103");
                                monthlyrainfalltxt.setText(0 + " mm");
                                monthlyhumiditytxt.setText(0 + " %");
                                monthlypressuretxt.setText(0 + " Pa");
                                monthlywindspeedtxt.setText(0 + " m/s");
                            } else {
                                //temperature
                                Double highjunetemp = Collections.max(junetemplist);
                                Double lowjunetemp = Collections.min(junetemplist);
                                Double avjunetemp = (highjunetemp + lowjunetemp) / 2;
                                monthlytemptxt.setText(avjunetemp.toString().substring(0, 2) + "\u2103");

                                //rain
                                Double highrain = Collections.max(rainlist);
                                Double lowrain = Collections.min(rainlist);
                                Double avrain = (highrain + lowrain) / 2;
                                monthlyrainfalltxt.setText(avrain.toString().substring(0, 4) + " mm");

                                //humidity
                                Double highhumidity = Collections.max(humiditylist);
                                Double lowhumidity = Collections.min(humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                monthlyhumiditytxt.setText(avhumidity.toString().substring(0, 4) + " %");

                                //pressure
                                Double highpressure = Collections.max(pressurelist);
                                Double lowpressure = Collections.min(pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                monthlypressuretxt.setText(avpressure.toString().substring(0, 4) + " Pa");

                                //windspeed
                                Double highwindspeed = Collections.max(humiditylist);
                                Double lowwindspeed = Collections.min(humiditylist);
                                Double avwindspeed = (highwindspeed + lowwindspeed) / 2;
                                monthlywindspeedtxt.setText(avwindspeed.toString().substring(0, 2) + " m/s");

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //weekly
                week1data = dataref.orderByChild("Date").startAt(year + "-" + monthspinnernumber + "-01").endAt(year + "-" + monthspinnernumber + "-07");
                week2data = dataref.orderByChild("Date").startAt(year + "-" + monthspinnernumber + "-08").endAt(year + "-" + monthspinnernumber + "-14");
                week3data = dataref.orderByChild("Date").startAt(year + "-" + monthspinnernumber + "-15").endAt(year + "-" + monthspinnernumber + "-21");
                week4data = dataref.orderByChild("Date").startAt(year + "-" + monthspinnernumber + "-22").endAt(year + "-" + monthspinnernumber + "-30");

//week1
                week1data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> week1templist = new ArrayList<>();
                            final List<Double> week1rainlist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {


                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                                mystring = data.getDate();

                                if (mystring == null) {

                                } else if (data.getTemperature() == null) {
                                    hightempweek1.setText("");
                                    lowtempweek1.setText("");
                                } else {

                                    //temperature
                                    Double week1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    week1templist.add(week1temp);

                                    //rainfall
                                    Double week1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    week1rainlist.add(week1rain);


                                }

                            }
                            if (week1templist.isEmpty() || week1rainlist.isEmpty()) {

                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(week1templist);
                                Double lowweek1temp = Collections.min(week1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                hightempweek1.setText(highweek1temp.toString().substring(0, 2) + "\u2103");
                                lowtempweek1.setText(lowweek1temp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(week1rainlist);
                                Double lowrain = Collections.min(week1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;


                                if ((avaragetemp) < -9) {

                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is freezing");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.icy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is freezing with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is freezing with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is freezing with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= (-9) && (avaragetemp) < 0) {
                                    generalweatherweek1.setText("The weather is a little bit freezing ");
                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.iceberg);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is a little bit freezing  with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is a little bit freezing  with moderate rain");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is a little bit freezing  with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= 0 && (avaragetemp) < 7) {
                                    generalweatherweek1.setText("The weather is very cold");


                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.snow);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is very cold with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 7 && (avaragetemp) < 12) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is very cold with some snow");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.snowstorm);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        generalweatherweek1.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is very cold with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 12 && (avaragetemp) < 18) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is a bit comfortable");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.partly_cloudy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        generalweatherweek1.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is a bit comfortable with moderate rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        generalweatherweek1.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 18 && (avaragetemp) < 24) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is  comfortable");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.comfortable_weather);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        generalweatherweek1.setText("The weather is a bit comfortable with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 24 && (avaragetemp) < 29) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is a bit sunny");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.smilly_sun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        generalweatherweek1.setText("The weather is a bit sunny with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        generalweatherweek1.setText("The weather is a bit sunny with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        generalweatherweek1.setText("The weather is a bit sunny with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 29 && (avaragetemp) < 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is  hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.sunny);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is  hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is  hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        generalweatherweek1.setText("The weather is  hot with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if (avaragetemp >= 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        generalweatherweek1.setText("The weather is  very hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.sunny_day);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        generalweatherweek1.setText("The weather is  very hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        generalweatherweek1.setText("The weather is  very hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        generalweatherweek1.setText("The weather is  very hot with very heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week1image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                }


                            }


                        } else {
                            hightempweek1.setText(0 + "\u2103");
                            lowtempweek1.setText(0 + "\u2103");
                            generalweatherweek1.setText("Weather");
                            week1image.setImageResource(R.drawable.wind);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //week2

                week2data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> week1templist = new ArrayList<>();
                            final List<Double> week1rainlist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {


                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                                mystring = data.getDate();

                                if (mystring == null) {

                                } else if (data.getTemperature() == null) {
                                    hightempweek2.setText("");
                                    lowtempweek2.setText("");
                                } else {

                                    //temperature
                                    Double week1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    week1templist.add(week1temp);

                                    //rainfall
                                    Double week1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    week1rainlist.add(week1rain);


                                }

                            }
                            if (week1templist.isEmpty() || week1rainlist.isEmpty()) {

                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(week1templist);
                                Double lowweek1temp = Collections.min(week1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                hightempweek2.setText(highweek1temp.toString().substring(0, 2) + "\u2103");
                                lowtempweek2.setText(lowweek1temp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(week1rainlist);
                                Double lowrain = Collections.min(week1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;


                                if ((avaragetemp) < -9) {

                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is freezing");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.icy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is freezing with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is freezing with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is freezing with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= (-9) && (avaragetemp) < 0) {
                                    weatherwk2.setText("The weather is a little bit freezing ");
                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.iceberg);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is a little bit freezing  with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is a little bit freezing  with moderate rain");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is a little bit freezing  with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= 0 && (avaragetemp) < 7) {
                                    weatherwk2.setText("The weather is very cold");


                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.snow);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is very cold with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 7 && (avaragetemp) < 12) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is very cold with some snow");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.snowstorm);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk2.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is very cold with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 12 && (avaragetemp) < 18) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is a bit comfortable");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.partly_cloudy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk2.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is a bit comfortable with moderate rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk2.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 18 && (avaragetemp) < 24) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is  comfortable");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.comfortable_weather);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk2.setText("The weather is a bit comfortable with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 24 && (avaragetemp) < 29) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is a bit sunny");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.smilly_sun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk2.setText("The weather is a bit sunny with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk2.setText("The weather is a bit sunny with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk2.setText("The weather is a bit sunny with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 29 && (avaragetemp) < 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is  hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.sunny);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is  hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is  hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk2.setText("The weather is  hot with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if (avaragetemp >= 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk2.setText("The weather is  very hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.sunny_day);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk2.setText("The weather is  very hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk2.setText("The weather is  very hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk2.setText("The weather is  very hot with very heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week2image.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                }


                            }


                        } else {
                            hightempweek2.setText(0 + "\u2103");
                            lowtempweek2.setText(0 + "\u2103");
                            weatherwk2.setText("Weather");
                            week2image.setImageResource(R.drawable.wind);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //week3

                week3data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> week1templist = new ArrayList<>();
                            final List<Double> week1rainlist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {


                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                                mystring = data.getDate();

                                if (mystring == null) {

                                } else if (data.getTemperature() == null) {
                                    hightempweek3.setText("");
                                    lowtempweek3.setText("");
                                } else {

                                    //temperature
                                    Double week1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    week1templist.add(week1temp);

                                    //rainfall
                                    Double week1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    week1rainlist.add(week1rain);


                                }

                            }
                            if (week1templist.isEmpty() || week1rainlist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(week1templist);
                                Double lowweek1temp = Collections.min(week1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                hightempweek3.setText(highweek1temp.toString().substring(0, 2) + "\u2103");
                                lowtempweek3.setText(lowweek1temp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(week1rainlist);
                                Double lowrain = Collections.min(week1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;


                                if ((avaragetemp) < -9) {

                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is freezing");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.icy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is freezing with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is freezing with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is freezing with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= (-9) && (avaragetemp) < 0) {
                                    weatherwk3.setText("The weather is a little bit freezing ");
                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.iceberg);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is a little bit freezing  with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is a little bit freezing  with moderate rain");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is a little bit freezing  with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= 0 && (avaragetemp) < 7) {
                                    weatherwk3.setText("The weather is very cold");


                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.snow);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is very cold with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 7 && (avaragetemp) < 12) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is very cold with some snow");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.snowstorm);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk3.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is very cold with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 12 && (avaragetemp) < 18) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is a bit comfortable");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.partly_cloudy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk3.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is a bit comfortable with moderate rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk3.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 18 && (avaragetemp) < 24) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is  comfortable");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.comfortable_weather);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk3.setText("The weather is a bit comfortable with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 24 && (avaragetemp) < 29) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is a bit sunny");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.smilly_sun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk3.setText("The weather is a bit sunny with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk3.setText("The weather is a bit sunny with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk3.setText("The weather is a bit sunny with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 29 && (avaragetemp) < 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is  hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.sunny);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is  hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is  hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk3.setText("The weather is  hot with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if (avaragetemp >= 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk3.setText("The weather is  very hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.sunny_day);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk3.setText("The weather is  very hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk3.setText("The weather is  very hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk3.setText("The weather is  very hot with very heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week3weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                }


                            }


                        } else {
                            hightempweek3.setText(0 + "\u2103");
                            lowtempweek3.setText(0 + "\u2103");
                            weatherwk3.setText("Weather");
                            week3weatherimage.setImageResource(R.drawable.wind);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //week4

                week4data.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> week1templist = new ArrayList<>();
                            final List<Double> week1rainlist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {


                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);


                                mystring = data.getDate();

                                if (mystring == null) {

                                } else if (data.getTemperature() == null) {
                                    hightempweek4.setText("");
                                    lowtempweek4.setText("");
                                } else {

                                    //temperature
                                    Double week1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    week1templist.add(week1temp);

                                    //rainfall
                                    Double week1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    week1rainlist.add(week1rain);


                                }

                            }
                            if (week1templist.isEmpty() || week1rainlist.isEmpty()) {

                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(week1templist);
                                Double lowweek1temp = Collections.min(week1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                hightempweek4.setText(highweek1temp.toString().substring(0, 2) + "\u2103");
                                lowtempweek4.setText(lowweek1temp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(week1rainlist);
                                Double lowrain = Collections.min(week1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;


                                if ((avaragetemp) < -9) {

                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is freezing");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.icy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is freezing with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is freezing with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is freezing with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= (-9) && (avaragetemp) < 0) {
                                    weatherwk4.setText("The weather is a little bit freezing ");
                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.iceberg);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.5 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is a little bit freezing  with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is a little bit freezing  with moderate rain");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is a little bit freezing  with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }

                                } else if ((avaragetemp) >= 0 && (avaragetemp) < 7) {
                                    weatherwk4.setText("The weather is very cold");


                                    if ((maxrainfall1) < 0.4) {
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.snow);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is very cold with heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 7 && (avaragetemp) < 12) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is very cold with some snow");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.snowstorm);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk4.setText("The weather is very cold with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is very cold with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is very cold with heavy rainfall");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 12 && (avaragetemp) < 18) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is a bit comfortable");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.partly_cloudy);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk4.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is a bit comfortable with moderate rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk4.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 18 && (avaragetemp) < 24) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is  comfortable");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.comfortable_weather);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is a bit comfortable with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk4.setText("The weather is a bit comfortable with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is a bit comfortable with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 24 && (avaragetemp) < 29) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is a bit sunny");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.smilly_sun);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {
                                        weatherwk4.setText("The weather is a bit sunny with light showers");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {

                                        weatherwk4.setText("The weather is a bit sunny with moderate rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {

                                        weatherwk4.setText("The weather is a bit sunny with heavy rain");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if ((avaragetemp) >= 29 && (avaragetemp) < 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is  hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.sunny);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is  hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is  hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk4.setText("The weather is  hot with heavy rainfall");


                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                } else if (avaragetemp >= 35) {


                                    if ((maxrainfall1) < 0.4) {
                                        weatherwk4.setText("The weather is  very hot");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.sunny_day);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);
                                    } else if ((maxrainfall1) >= 0.4 && (maxrainfall1) < 4) {

                                        weatherwk4.setText("The weather is  very hot with light showers");
                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.light_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 4 && (maxrainfall1) < 8) {
                                        weatherwk4.setText("The weather is  very hot with moderate rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.rainfall1);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    } else if ((maxrainfall1) >= 8) {
                                        weatherwk4.setText("The weather is  very hot with very heavy rain");

                                        mHandler = new Handler();
                                        myrun = new Runnable() {
                                            @Override
                                            public void run() {
                                                week4weatherimage.setImageResource(R.drawable.heavy_rain);
                                            }
                                        };

                                        mHandler.postDelayed(myrun, 00L);

                                    }


                                }


                            }


                        } else {
                            hightempweek4.setText(0 + "\u2103");
                            lowtempweek4.setText(0 + "\u2103");
                            weatherwk4.setText("Weather");
                            week4weatherimage.setImageResource(R.drawable.wind);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //1st week days
                day1 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-01");
                day2 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-02");
                day3 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-03");
                day4 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-04");
                day5 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-05");
                day6 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-06");
                day7 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-07");
                day8 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-08");
                day9 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-09");
                day10 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-10");
                day11 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-11");
                day12 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-12");
                day13 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-13");
                day14 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-14");
                day15 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-15");
                day16 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-16");
                day17 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-17");
                day18 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-18");
                day19 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-19");
                day20 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-20");
                day21 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-21");
                day22 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-22");
                day23 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-23");
                day24 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-24");
                day25 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-25");
                day26 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-26");
                day27 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-27");
                day28 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-28");
                day29 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-29");
                day30 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-30");
                day31 = dataref.orderByChild("Date").equalTo(year + "-" + monthspinnernumber + "-31");


//setting dates
                moningcontent.setText(year + "-" + monthspinnernumber + "-01");
                date2.setText(year + "-" + monthspinnernumber + "-02");
                date3.setText(year + "-" + monthspinnernumber + "-03");
                date4.setText(year + "-" + monthspinnernumber + "-04");
                date5.setText(year + "-" + monthspinnernumber + "-05");
                date6.setText(year + "-" + monthspinnernumber + "-06");
                date7.setText(year + "-" + monthspinnernumber + "-07");
                date8.setText(year + "-" + monthspinnernumber + "-08");
                date9.setText(year + "-" + monthspinnernumber + "-09");
                date10.setText(year + "-" + monthspinnernumber + "-10");
                date11.setText(year + "-" + monthspinnernumber + "-11");
                date12.setText(year + "-" + monthspinnernumber + "-12");
                date13.setText(year + "-" + monthspinnernumber + "-13");
                date14.setText(year + "-" + monthspinnernumber + "-14");
                date15.setText(year + "-" + monthspinnernumber + "-15");
                date16.setText(year + "-" + monthspinnernumber + "-16");
                date17.setText(year + "-" + monthspinnernumber + "-17");
                date18.setText(year + "-" + monthspinnernumber + "-18");
                date19.setText(year + "-" + monthspinnernumber + "-19");
                date20.setText(year + "-" + monthspinnernumber + "-20");
                date21.setText(year + "-" + monthspinnernumber + "-21");


//week1
                //day1
                day1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    mngavtempweek1.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    mngavrainfallweek1.setText("");
                                } else if (data.getHumidity() == null) {
                                    mngavhumidityweek1.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    mngavpressureweek1.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                mngavtempweek1.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                mngavrainfallweek1.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                mngavpressureweek1.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                mngavhumidityweek1.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            mngavtempweek1.setText("Av temperature:" + 0 + "\u2103");
                            mngavrainfallweek1.setText("Av. rainfall:" + 0 + " mm");
                            mngavpressureweek1.setText("Av. pressure:" + 0 + " Pa");
                            mngavhumidityweek1.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//day2
                day2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    aftavtempweek1.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    aftavrainfallweek1.setText("");

                                } else if (data.getHumidity() == null) {
                                    aftavhumidityweek1.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    aftavpressureweek1.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                aftavtempweek1.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                aftavrainfallweek1.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                aftavpressureweek1.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                aftavhumidityweek1.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            aftavtempweek1.setText("Av temperature:" + 0 + "\u2103");
                            aftavrainfallweek1.setText("Av. rainfall:" + 0 + " mm");
                            aftavpressureweek1.setText("Av. pressure:" + 0 + " Pa");
                            aftavhumidityweek1.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


//day3
                day3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day3temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day3rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day3humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day3pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day3temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day3rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day3pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day3humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day3temperature.setText("Av temperature:" + 0 + "\u2103");
                            day3rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day3pressure.setText("Av. pressure:" + 0 + " Pa");
                            day3humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day4
                day4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day4temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day4rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day4humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day4pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day4temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day4rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day4pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day4humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day4temperature.setText("Av temperature:" + 0 + "\u2103");
                            day4rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day4pressure.setText("Av. pressure:" + 0 + " Pa");
                            day4humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day5
                day5.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day5temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day5rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day5humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day5pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day5temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day5rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day5pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day5humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day5temperature.setText("Av temperature:" + 0 + "\u2103");
                            day5rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day5pressure.setText("Av. pressure:" + 0 + " Pa");
                            day5humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day6
                day6.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day6temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day6rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day6humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day6pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day6temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day6rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day6pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day6humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day6temperature.setText("Av temperature:" + 0 + "\u2103");
                            day6rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day6pressure.setText("Av. pressure:" + 0 + " Pa");
                            day6humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //day7
                day7.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day7temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day7rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day7humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day7pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day7temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day7rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day7pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day7humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day7temperature.setText("Av temperature:" + 0 + "\u2103");
                            day7rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day7pressure.setText("Av. pressure:" + 0 + " Pa");
                            day7humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //week2


                //day8
                day8.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    mngavtempwk2.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    mngavrainfallwk2.setText("");
                                } else if (data.getHumidity() == null) {
                                    mngavhumiditywk2.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    mngavpressurewk2.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                mngavtempwk2.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                mngavrainfallwk2.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                mngavpressurewk2.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                mngavhumiditywk2.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            mngavtempwk2.setText("Av temperature:" + 0 + "\u2103");
                            mngavrainfallwk2.setText("Av. rainfall:" + 0 + " mm");
                            mngavpressurewk2.setText("Av. pressure:" + 0 + " Pa");
                            mngavhumiditywk2.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day9
                day9.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    aftavtempwk2.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    aftavrainfallwk2.setText("");

                                } else if (data.getHumidity() == null) {
                                    aftavhumiditywk2.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    aftavpressurewk2.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                aftavtempwk2.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                aftavrainfallwk2.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                aftavpressurewk2.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                aftavhumiditywk2.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            aftavtempwk2.setText("Av temperature:" + 0 + "\u2103");
                            aftavrainfallwk2.setText("Av. rainfall:" + 0 + " mm");
                            aftavpressurewk2.setText("Av. pressure:" + 0 + " Pa");
                            aftavhumiditywk2.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day10
                day10.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    evnavtempwk2.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    evnavrainfallwk2.setText("");

                                } else if (data.getHumidity() == null) {
                                    evnavhumidwk2.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    evnavpressurewk2.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                evnavtempwk2.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                evnavrainfallwk2.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                evnavpressurewk2.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                aftavhumiditywk2.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            evnavtempwk2.setText("Av temperature:" + 0 + "\u2103");
                            evnavrainfallwk2.setText("Av. rainfall:" + 0 + " mm");
                            evnavpressurewk2.setText("Av. pressure:" + 0 + " Pa");
                            evnavhumidwk2.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day11
                day11.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day11temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day11rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day11humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day11pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day11temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day11rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day11pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day11humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day11temperature.setText("Av temperature:" + 0 + "\u2103");
                            day11rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day11pressure.setText("Av. pressure:" + 0 + " Pa");
                            day11humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day12
                day12.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day12temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day12rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day12humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day12pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day12temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day12rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day12pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day12humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day12temperature.setText("Av temperature:" + 0 + "\u2103");
                            day12rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day12pressure.setText("Av. pressure:" + 0 + " Pa");
                            day12humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day13
                day13.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day13temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day13rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day13humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day13pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day13temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day13rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day13pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day13humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day13temperature.setText("Av temperature:" + 0 + "\u2103");
                            day13rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day13pressure.setText("Av. pressure:" + 0 + " Pa");
                            day13humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day14
                day14.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day14temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day14rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day14humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day14pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day14temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day14rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day14pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day14humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day14temperature.setText("Av temperature:" + 0 + "\u2103");
                            day14rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day14pressure.setText("Av. pressure:" + 0 + " Pa");
                            day14humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day15
                day15.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day15temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day15rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day15humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day15pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day15temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day15rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day15pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day15humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day15temperature.setText("Av temperature:" + 0 + "\u2103");
                            day15rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day15pressure.setText("Av. pressure:" + 0 + " Pa");
                            day15humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day16
                day16.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day16temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day16rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day16humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day16pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day16temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day16rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                int avaragepressure = avpressure.intValue();
                                day16pressure.setText("Av. pressure:" + String.valueOf(avaragepressure) + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day16humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day16temperature.setText("Av temperature:" + 0 + "\u2103");
                            day16rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day16pressure.setText("Av. pressure:" + 0 + " Pa");
                            day16humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day17
                day17.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day17temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day17rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day17humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day17pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day17temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day17rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day17pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day17humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day17temperature.setText("Av temperature:" + 0 + "\u2103");
                            day17rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day17pressure.setText("Av. pressure:" + 0 + " Pa");
                            day17humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day18
                day18.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day18temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day18rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day18humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day18pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day18temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day18rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day18pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day18humidity.setText("Av. humidity:" + avhumidity.toString().substring(0, 4) + " %");


                            }


                        } else {
                            day18temperature.setText("Av temperature:" + 0 + "\u2103");
                            day18rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day18pressure.setText("Av. pressure:" + 0 + " Pa");
                            day18humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day19
                day19.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day19temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day19rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day19humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day19pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day19temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day19rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day19pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day19humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day19temperature.setText("Av temperature:" + 0 + "\u2103");
                            day19rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day19pressure.setText("Av. pressure:" + 0 + " Pa");
                            day19humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day20
                day20.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day20temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day20rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day20humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day20pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                                day20temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day20rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                day20pressure.setText("Av. pressure:" + avpressure.toString() + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day20humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day20temperature.setText("Av temperature:" + 0 + "\u2103");
                            day20rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day20pressure.setText("Av. pressure:" + 0 + " Pa");
                            day20humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //day21
                day21.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();

                            final List<Double> day1templist = new ArrayList<>();
                            final List<Double> day1rainlist = new ArrayList<>();
                            final List<Double> day1humiditylist = new ArrayList<>();
                            final List<Double> day1pressurelist = new ArrayList<>();

                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
                                if (data.getTemperature() == null) {
                                    day21temperature.setText("");
                                } else if (data.getRainfall_1hr() == null) {
                                    day21rainfall.setText("");

                                } else if (data.getHumidity() == null) {
                                    day21humidity.setText("");
                                } else if (data.getBarometric_Pressure() == null) {
                                    day21pressure.setText("");

                                } else {
                                    //temperature
                                    Double day1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
                                    day1templist.add(day1temp);

                                    //rainfall
                                    Double day1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
                                    day1rainlist.add(day1rain);

                                    //humidity
                                    Double day1humidity = dataSnapshoti.child("Humidity").getValue(Double.class);
                                    day1humiditylist.add(day1humidity);

                                    //pressure
                                    Double day1pressure = dataSnapshoti.child("Barometric_Pressure").getValue(Double.class);
                                    day1pressurelist.add(day1pressure);
                                }


                            }
                            if (day1rainlist.isEmpty() || day1templist.isEmpty() || day1pressurelist.isEmpty() || day1humiditylist.isEmpty()) {


                            } else {

                                //temperature
                                Double highweek1temp = Collections.max(day1templist);
                                Double lowweek1temp = Collections.min(day1templist);
                                Double avaragetemp = (highweek1temp + lowweek1temp) / 2;
                            
                                day21temperature.setText("Av temperature:" + avaragetemp.toString().substring(0, 2) + "\u2103");


                                //rain
                                Double highrain = Collections.max(day1rainlist);
                                Double lowrain = Collections.min(day1rainlist);
                                Double maxrainfall1 = (highrain + lowrain) / 2;
                                day21rainfall.setText("Av. rainfall:" + maxrainfall1.toString() + " mm");

                                //pressure
                                Double highpressure = Collections.max(day1pressurelist);
                                Double lowpressure = Collections.min(day1pressurelist);
                                Double avpressure = (highpressure + lowpressure) / 2;
                                int avaragepressure = avpressure.intValue();
                                day16pressure.setText("Av. pressure:" + String.valueOf(avaragepressure) + " Pa");

                                //humidity
                                Double highhumidity = Collections.max(day1humiditylist);
                                Double lowhumidity = Collections.min(day1humiditylist);
                                Double avhumidity = (highhumidity + lowhumidity) / 2;
                                day21humidity.setText("Av. humidity:" + avhumidity.toString() + " %");


                            }


                        } else {
                            day21temperature.setText("Av temperature:" + 0 + "\u2103");
                            day21rainfall.setText("Av. rainfall:" + 0 + " mm");
                            day21pressure.setText("Av. pressure:" + 0 + " Pa");
                            day21humidity.setText("Av. humidity:" + 0 + " %");


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


//general data


//        choosemonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (id == 0) {
////                    month = parent.getItemAtPosition(position).toString();
////                    truncmonth = month.substring(0, 3);
//
//                    monthspinnernumber = "01";
//                    //range
//                    week1range.setText("Jan 01 to Jan 07");
//                    week2range.setText("Jan 08 to Jan 14");
//                    week3range.setText("Jan 15 to Jan 21");
//                    week4range.setText("Jan 22 to Jan 30");
//
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-01-01").endAt(year + "-01-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1+ " %");
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1+ " Pa");
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1+ " mm");
//                                                                                    mngavtempweek1.setText(mrntempweek1+" \u2103");
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1+ " %");
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1+ " Pa");
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1+ " mm");
//                                                                                    mngavtempweek1.setText(mrntempweek1+" \u2103");
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1+ " %");
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1+ " Pa");
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1+ " mm");
//                                                                                    mngavtempweek1.setText(mrntempweek1+" \u2103");
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1+ " %");
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1+ " Pa");
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1+ " mm");
//                                                                                    mngavtempweek1.setText(mrntempweek1+" \u2103");
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                            //avarage weekly
//                                            dataref.orderByChild("Date").startAt(year + "-01-01").endAt(year + "-01-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 1) {
//
//                    monthspinnernumber = "02";
//                    //range
//                    week1range.setText("Feb 01 to Feb 07");
//                    week2range.setText("Feb 08 to Feb 14");
//                    week3range.setText("Feb 15 to Feb 21");
//                    week4range.setText("Feb 22 to Feb 30");
//
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-02-01").endAt(year + "-02-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 2) {
//
//                    monthspinnernumber = "03";
//                    //range
//                    week1range.setText("Mar 01 to Mar 07");
//                    week2range.setText("Mar 08 to Mar 14");
//                    week3range.setText("Mar 15 to Mar 21");
//                    week4range.setText("Mar 22 to Mar 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-03-01").endAt(year + "-03-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 3) {
//
//                    monthspinnernumber = "04";
//                    //range
//                    week1range.setText("Apr 01 to Apr 07");
//                    week2range.setText("Apr 08 to Apr 14");
//                    week3range.setText("Apr 15 to Apr 21");
//                    week4range.setText("Apr 22 to Apr 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-04-01").endAt(year + "-04-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 4) {
//
//                    monthspinnernumber = "05";
//                    //range
//                    week1range.setText("May 01 to May 07");
//                    week2range.setText("May 08 to May 14");
//                    week3range.setText("May 15 to May 21");
//                    week4range.setText("May 22 to May 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-05-01").endAt(year + "-05-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 5) {
//
//                    monthspinnernumber = "06";
//                    //range
//                    week1range.setText("Jun 01 to Jun 07");
//                    week2range.setText("Jun 08 to Jun 14");
//                    week3range.setText("Jun 15 to Jun 21");
//                    week4range.setText("Jun 22 to Jun 30");
////                    june();
//
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                             week1=  dataref.orderByChild("Date").startAt(year + "-06-01").endAt(year + "-06-07");
//                                         week1.addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                week1.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                                            List<Double> list = new ArrayList<>();
//                                                                            List<Double> day1rainlist = new ArrayList<>();
//
//
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    list.add(alltemps);
//
////                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
////                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
////                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
////                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
////                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
////                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
////                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
////                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
////                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//                                                                                }
//
//                                                                            }
//                                                                            Double high2 = Collections.max(list);
//                                                                            Double low2 = Collections.min(list);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
//                                                                            mngavtempweek1.setText(String.valueOf(avaragetemp));
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                                            List<Double> list = new ArrayList<>();
//                                                                            List<Double> day1rainlist = new ArrayList<>();
//
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    list.add(alltemps);
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//                                                                                }
//
//                                                                            }
//                                                                            Double high2 = Collections.max(list);
//                                                                            Double low2 = Collections.min(list);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
//                                                                            aftavtempweek1.setText(String.valueOf(avaragetemp));
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                                            List<Double> list = new ArrayList<>();
//                                                                            List<Double> day1rainlist = new ArrayList<>();
//
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    list.add(alltemps);
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//                                                                                }
//
//                                                                            }
//
//                                                                            Double high2 = Collections.max(list);
//                                                                            Double low2 = Collections.min(list);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
////                                                                            .setText(String.valueOf(avaragetemp));
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                       //     week2
//
//                                            week2.addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        final List<Double> evlist = new ArrayList<>();
//                                                        final List<Double> day1rainlist = new ArrayList<>();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            List<Double> list = new ArrayList<>();
//                                                                            List<Double> day1rainlist = new ArrayList<>();
//
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                }
//                                                                                else {
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    list.add(alltemps);
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//
//
//
//
//                                                                                }
//
//                                                                            }
//                                                                            Double high2 = Collections.max(list);
//                                                                            Double low2 = Collections.min(list);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
//                                                                            mngavtempwk2.setText(String.valueOf(avaragetemp));
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            List<Double> list = new ArrayList<>();
//                                                                            List<Double> day1rainlist = new ArrayList<>();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    evlist.add(alltemps);
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//                                                                                }
//
//                                                                            }
//                                                                            Double high2 = Collections.max(list);
//                                                                            Double low2 = Collections.min(list);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
//                                                                            aftavtempwk2.setText(String.valueOf(avaragetemp));
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                wk2evening.addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    //rain
//                                                                                    Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                                                    day1rainlist.add(day1rainref);
//
//                                                                                    //Temp
//                                                                                    Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                                                    list.add(alltemps);
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////                                                                                    aftavhumidityweek1.setText(String.valueOf(mrnhumidweek1));
////                                                                                    aftavpressureweek1.setText(String.valueOf(mrnpressureweek1));
////                                                                                    aftavrainfallweek1.setText(String.valueOf(mrnrainweek1));
////                                                                                    aftavtempweek1.setText(String.valueOf(mrntempweek1));
//                                                                                }
//
//                                                                            }
//                                                                            Double high2 = Collections.max(evlist);
//                                                                            Double low2 = Collections.min(evlist);
//                                                                            Double avaragetemp = (high2 + low2) / 2;
//                                                                            evnavtempwk2.setText(String.valueOf(avaragetemp));
//
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                            //week1 avarage
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                }
//                if (id == 6) {
//
//                    monthspinnernumber = "07";
//                    //range
//                    week1range.setText("Jul 01 to Jul 07");
//                    week2range.setText("Jul 08 to Jul 14");
//                    week3range.setText("Jul 15 to Jul 21");
//                    week4range.setText("Jul 22 to Jul 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-07-01").endAt(year + "-07-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                if (id == 7) {
//
//                    monthspinnernumber = "08";
//                    //range
//                    week1range.setText("Aug 01 to Aug 07");
//                    week2range.setText("Aug 08 to Aug 14");
//                    week3range.setText("Aug 15 to Aug 21");
//                    week4range.setText("Aug 22 to Aug 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-08-01").endAt(year + "-08-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 8) {
//
//                    monthspinnernumber = "09";
//                    //range
//                    week1range.setText("Sept 01 to Sept 07");
//                    week2range.setText("Sept 08 to Sept 14");
//                    week3range.setText("Sept 15 to Sept 21");
//                    week4range.setText("Sept 22 to Sept 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-09-01").endAt(year + "-09-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 9) {
//
//                    monthspinnernumber = "10";
//                    //range
//                    week1range.setText("Oct 01 to Oct 07");
//                    week2range.setText("Oct 08 to Oct 14");
//                    week3range.setText("Oct 15 to Oct 21");
//                    week4range.setText("Oct 22 to Oct 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-10-01").endAt(year + "-10-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
////                    dataref.addValueEventListener(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            if (dataSnapshot.exists()) {
////                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
////                                List<String> daysstrings = new ArrayList<>();
//////                    String chosenmonthspinner=monthspinnernumber;
////                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
////
////                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
//////                        daysstrings.add(daysstringdata);
//////                        assert data != null;
////
////                                    mystring = data.getDate();
////                                    if (mystring == null) {
////
////                                    } else {
////                                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                                        Toast.makeText(getContext(), "" + (mystring.substring(5, 7)), Toast.LENGTH_SHORT).show();
////                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////
////                                            Toast.makeText(getContext(), "" + data.getDate(), Toast.LENGTH_SHORT).show();
//////                                 Log.d("TAG",data.getDate());
////                                        } else {
////                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
////                                        }
////                                    }
////
////
//////                   String date=   result.substring(3,);
////
//////                        result = mystring.split("-");
////                                    Log.d("TAG", String.valueOf(result));
////
////                                }
////
//////                    Collections.sort(daysstrings);
////
////                            }
////
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                        }
////                    });
//
//                }
//                if (id == 10) {
//
//                    monthspinnernumber = "11";
//                    //range
//                    week1range.setText("Nov 01 to Nov 07");
//                    week2range.setText("Nov 08 to Nov 14");
//                    week3range.setText("Nov 15 to Nov 21");
//                    week4range.setText("Nov 22 to Nov 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-11-01").endAt(year + "-11-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                if (id == 11) {
//
//                    monthspinnernumber = "12";
//                    //range
//                    week1range.setText("Dec 01 to Dec 07");
//                    week2range.setText("Dec 08 to Dec 14");
//                    week3range.setText("Dec 15 to Dec 21");
//                    week4range.setText("Dec 22 to Dec 30");
//                    dataref.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//                                Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                                List<String> daysstrings = new ArrayList<>();
//                                final List<Double> list = new ArrayList<>();
//                                for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                    wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                    mystring = data.getDate();
//                                    if (mystring == null) {
//
//                                    } else {
//
//                                        if (((mystring.substring(5, 7)).equals(monthspinnernumber))) {
////                                            wetherdata permonthdata = dataSnapshoti.getValue(wetherdata.class);
//
//                                            //week1 data
//                                            dataref.orderByChild("Date").startAt(year + "-12-01").endAt(year + "-12-07").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    if (dataSnapshot.exists()) {
//                                                        Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                        for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                            wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                            if (weeklydata.getRainfall_1hr() == null) {
//
//                                                            } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                                            } else if (weeklydata.getHumidity() == null) {
//
//                                                            } else if (weeklydata.getTemperature() == null) {
//
//                                                            } else {
//                                                                //morning
//                                                                dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //afternoon
//                                                                dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
//                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
//                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
//                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
//                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //evening
//                                                                dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//                                                                //night
//                                                                dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if (dataSnapshot.exists()) {
//                                                                            Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                                            for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                                                wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                                                if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                                                } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                                                } else if (mornidataweek1.getHumidity() == null) {
//
//                                                                                } else if (mornidataweek1.getTemperature() == null) {
//
//                                                                                } else {
//
//                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
//                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
//                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                                                }
//
//                                                                            }
//                                                                        }
//
//                                                                    }
//
//                                                                    @Override
//                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                    }
//                                                                });
//
//                                                            }
//
//
//                                                        }
//                                                    } else {
//
//                                                    }
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//                                            dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-15").addValueEventListener(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                        wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//
//                                                    }
//                                                    Double high1 = Collections.max(list);
//                                                    Double low1 = Collections.min(list);
//                                                    Double avaragetemp1 = (high1 + low1) / 2;
//                                                    if ((avaragetemp1) < -9) {
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.icy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.iceberg);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snow);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.snowstorm);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.partly_cloudy);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.comfortable_weather);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.smilly_sun);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    } else if (avaragetemp1 >= 35) {
//
//
//                                                        mHandler = new Handler();
//                                                        myrun = new Runnable() {
//                                                            @Override
//                                                            public void run() {
//                                                                week1image.setImageResource(R.drawable.sunny_day);
//                                                            }
//                                                        };
//
//                                                        mHandler.postDelayed(myrun, 00L);
//                                                    }
//                                                    lowtempweek1.setText("/" + low1.toString().substring(0, 2) + "\u2103");
//                                                    hightempweek1.setText(high1.toString().substring(0, 2) + "\u2103");
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
////                                            if (permonthdata.getDate().equals(year+"-06-12")){
////
////                                                Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
////                                                list.add(alltemps);
////
////                                               // Toast.makeText(getContext(), ""+permonthdata.getBarometric_Pressure(), Toast.LENGTH_SHORT).show();
////                                            }
//
//
//                                            //Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                                        } else {
//                                            Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                                    Log.d("TAG", String.valueOf(result));
//
//                                }
//
//
////                    Collections.sort(daysstrings);
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        choosemonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(id==0){
//                    monthstring = (String) DateFormat.format("MMM", calendar.getTime());
//                }
//            }
//        });


//
//        graphView = view.findViewById(R.id.tempgraph);
//        series = new LineGraphSeries();
//        graphView.addSeries(series);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
//                dataref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()){
//                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                            List<String> daysstrings = new ArrayList<>();
//
//                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//
//                                mystring = data.getDate();
//                                if (mystring==null){
//
//                                }
//                                else
//                                {
//
//                                    if (((mystring.substring(5,7)).equals(monthspinnernumber))){
//
//                                        Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
//
//                                    }
//                                    else{
//                                        Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//
//
//
//
//
//                                Log.d("TAG", String.valueOf(result));
//
//                            }
//
////                    Collections.sort(daysstrings);
//
//                        }
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });
//        dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
//        dataref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                    List<String> daysstrings = new ArrayList<>();
////                    String chosenmonthspinner=monthspinnernumber;
//                    for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);
////                        String daysstringdata = dataSnapshoti.child("Day").getValue(String.class);
////                        daysstrings.add(daysstringdata);
////                        assert data != null;
//
//                         mystring = data.getDate();
//                         if (mystring==null){
//
//                         }
//                         else
//                         {
//                        //Toast.makeText(getContext(), "This"+chosenmonthspinner, Toast.LENGTH_SHORT).show();
////                             Toast.makeText(getContext(), ""+(mystring.substring(5,7)), Toast.LENGTH_SHORT).show();
//                             if (((mystring.substring(5,7)).equals(monthspinnernumber))){
//
//                                 Toast.makeText(getContext(), ""+data.getDate(), Toast.LENGTH_SHORT).show();
////                                 Log.d("TAG",data.getDate());
//                             }
//                             else{
//                                 Toast.makeText(getContext(), "Empty/....", Toast.LENGTH_SHORT).show();
//                             }
//                         }
//
//
//
//
////                   String date=   result.substring(3,);
//
////                        result = mystring.split("-");
//                        Log.d("TAG", String.valueOf(result));
//
//                    }
//
////                    Collections.sort(daysstrings);
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//        graphView.getGridLabelRenderer().setNumHorizontalLabels(50);
//
//        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
//        {
//
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX)
//                {
//                    return  simpleDateFormat.format(new Date((long) value));
//                }
//                else {
//                    return super.formatLabel(value, isValueX);}
//            }
//        });
//
//
//        templinechart = view.findViewById(R.id.templinechart);


//        templinechart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                return null;
//            }
//        });
//        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
//            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");
//
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//
//                long millis = (long) value;
//                return mFormat.format(new Date(millis));
//            }
//        });


//        dataref = FirebaseDatabase.getInstance().getReference().child("Weather_Data");
//
//
//        dataref.child("").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ArrayList<Entry> datavalues = new ArrayList<Entry>();
//                if (dataSnapshot.exists()) {
//                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                    for (DataSnapshot dataSnapshoti : childrensnapshot) {
//                        final wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//                        if (("2020-06-06").equalsIgnoreCase(data.getDate())) {
//                            if (data.getTemperature() == null) {
//                                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
//                            } else {
//                                Long temp = data.getTemperature();
//                                int tempint = temp.intValue();
//                                Long humid = data.getHumidity();
//                                int humidint = humid.intValue();
//
//
//                                datavalues.add(new Entry(tempint, humidint));
//                            }
//                            // showChart(datavalues);
//                        }
//
//
//                    }
//
//                    showChart(datavalues);
//
//                } else {
//                    templinechart.clear();
//                    templinechart.invalidate();
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return view;
    }
//public void june(){
//    dataref.orderByChild("Date").startAt(year + "-06-08").endAt(year + "-06-14").addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            if (dataSnapshot.exists()) {
//                Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                    wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                    if (weeklydata.getRainfall_1hr() == null) {
//
//                    } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                    } else if (weeklydata.getHumidity() == null) {
//
//                    } else if (weeklydata.getTemperature() == null) {
//
//                    } else {
//                        //morning
//                        dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                    List<Double> list = new ArrayList<>();
//                                    List<Double> day1rainlist = new ArrayList<>();
//
//                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                        wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                        if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                        } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                        } else if (mornidataweek1.getHumidity() == null) {
//
//                                        } else if (mornidataweek1.getTemperature() == null) {
//
//                                        }
//                                        else {
//                                            //rain
//                                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                            day1rainlist.add(day1rainref);
//
//                                            //Temp
//                                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                            list.add(alltemps);
//
//                                            String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                            int wk1rain = Integer.parseInt(morningrainweek1);
//                                            int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                            String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                            int wk1temp = Integer.parseInt(morningtempweek1);
//                                            int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                            String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                            int wk1humid = Integer.parseInt(morninghumidweek1);
//                                            int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                            String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                            int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                            int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//
//
//
//
//                                        }
//
//                                    }
//                                    Double high2 = Collections.max(list);
//                                    Double low2 = Collections.min(list);
//                                    Double avaragetemp = (high2 + low2) / 2;
//                                    mngavtempwk2.setText(String.valueOf(avaragetemp));
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        //afternoon
//                        dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                    List<Double> list = new ArrayList<>();
//                                    List<Double> day1rainlist = new ArrayList<>();
//                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                        wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                        if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                        } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                        } else if (mornidataweek1.getHumidity() == null) {
//
//                                        } else if (mornidataweek1.getTemperature() == null) {
//
//                                        } else {
//                                            //rain
//                                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                            day1rainlist.add(day1rainref);
//
//                                            //Temp
//                                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                            list.add(alltemps);
//
//                                            String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                            int wk1rain = Integer.parseInt(morningrainweek1);
//                                            int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                            String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                            int wk1temp = Integer.parseInt(morningtempweek1);
//                                            int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                            String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                            int wk1humid = Integer.parseInt(morninghumidweek1);
//                                            int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                            String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                            int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                            int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//                                        }
//
//                                    }
//                                    Double high2 = Collections.max(list);
//                                    Double low2 = Collections.min(list);
//                                    Double avaragetemp = (high2 + low2) / 2;
//                                    aftavtempwk2.setText(String.valueOf(avaragetemp));
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        //evening
//                        dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                    List<Double> list = new ArrayList<>();
//                                    List<Double> day1rainlist = new ArrayList<>();
//                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                        wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                        if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                        } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                        } else if (mornidataweek1.getHumidity() == null) {
//
//                                        } else if (mornidataweek1.getTemperature() == null) {
//
//                                        } else {
//                                            //rain
//                                            Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                            day1rainlist.add(day1rainref);
//
//                                            //Temp
//                                            Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                            list.add(alltemps);
//
//                                            String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                            int wk1rain = Integer.parseInt(morningrainweek1);
//                                            int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                            String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                            int wk1temp = Integer.parseInt(morningtempweek1);
//                                            int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                            String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                            int wk1humid = Integer.parseInt(morninghumidweek1);
//                                            int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                            String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                            int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                            int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////                                                                                    aftavhumidityweek1.setText(String.valueOf(mrnhumidweek1));
////                                                                                    aftavpressureweek1.setText(String.valueOf(mrnpressureweek1));
////                                                                                    aftavrainfallweek1.setText(String.valueOf(mrnrainweek1));
////                                                                                    aftavtempweek1.setText(String.valueOf(mrntempweek1));
//                                        }
//
//                                    }
//                                    Double high2 = Collections.max(list);
//                                    Double low2 = Collections.min(list);
//                                    Double avaragetemp = (high2 + low2) / 2;
//                                    evnavtempwk2.setText(String.valueOf(avaragetemp));
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        //night
//                        dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                    for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                        wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                        if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                        } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                        } else if (mornidataweek1.getHumidity() == null) {
//
//                                        } else if (mornidataweek1.getTemperature() == null) {
//
//                                        } else {
//
//                                            String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                            int wk1rain = Integer.parseInt(morningrainweek1);
//                                            int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                            String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                            int wk1temp = Integer.parseInt(morningtempweek1);
//                                            int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                            String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                            int wk1humid = Integer.parseInt(morninghumidweek1);
//                                            int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                            String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                            int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                            int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    aftavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    aftavpressureweek1.setText(mrnpressureweek1);
////                                                                                    aftavrainfallweek1.setText(mrnrainweek1);
////                                                                                    aftavtempweek1.setText(mrntempweek1);
//                                        }
//
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//
//
//                }
//            } else {
//
//            }
//
//
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
//
//}

    @Override
    public void onStart() {
        super.onStart();
//June
//        june = dataref.orderByChild("Date").startAt(year+"-"+"06"+"-01").endAt(year+"-"+"06"+"-30");


        if (monthno.equals("01") || monthno.equals("03") || monthno.equals("05") || monthno.equals("07") || monthno.equals("08") || monthno.equals("10") || monthno.equals("12")) {
            week1range.setText(monthstring + " 01 to" + monthstring + " 07");

            week2range.setText(monthstring + " 08 to" + monthstring + " 14");

            week3range.setText(monthstring + " 15 to" + monthstring + " 21");

            week4range.setText(monthstring + " 22 to" + monthstring + " 31");

        }

        if (monthno.equals("04") || monthno.equals("06") || monthno.equals("09") || monthno.equals("11")) {
            week1range.setText(monthstring + " 01 to" + monthstring + " 07");

            week2range.setText(monthstring + " 08 to" + monthstring + " 14");

            week3range.setText(monthstring + " 15 to" + monthstring + " 21");

            week4range.setText(monthstring + " 22 to" + monthstring + " 30");
        }
        if (monthno.equals("02")) {


            week1range.setText(monthstring + " 01 to" + monthstring + " 07");

            week2range.setText(monthstring + " 08 to" + monthstring + " 14");

            week3range.setText(monthstring + " 15 to" + monthstring + " 21");

            week4range.setText(monthstring + " 22 to" + monthstring + " 28");
        }

//
//        if (indexofmonth==01||monthspinnernumber=="03"||monthspinnernumber=="05"||monthspinnernumber=="07"||monthspinnernumber=="08"||monthspinnernumber=="10"||monthspinnernumber=="12"){
//
//            currentmonth = dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-01").endAt(year + "-"+indexofmonth+"-31");
//
//        }
//        if (monthspinnernumber=="04"||indexofmonth==06||monthspinnernumber=="09"||monthspinnernumber=="11"){
//
//            currentmonth = dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-01").endAt(year+"-"+indexofmonth+"-30");
//
//            if (indexofmonth==06){
//                currentweek1 =dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-01").endAt(year+"-"+indexofmonth+"-07");
//                currentweek2 =dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-08").endAt(year+"-"+indexofmonth+"-14");
//                currentweek3 =dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-15").endAt(year+"-"+indexofmonth+"-21");
//                currentweek4 =dataref.orderByChild("Date").startAt(year+"-"+indexofmonth+"-22").endAt(year+"-"+indexofmonth+"-30");
//
//                currentweek3.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if (dataSnapshot.exists()) {
//                            Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                            List<Double> week1templist = new ArrayList<>();
//                            List<Double> week1rainlist = new ArrayList<>();
//                            for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                                wetherdata weeklydata = dataSnapshoti.getValue(wetherdata.class);
//                                if (weeklydata.getRainfall_1hr() == null) {
//
//                                } else if (weeklydata.getBarometric_Pressure() == null) {
//
//                                } else if (weeklydata.getHumidity() == null) {
//
//                                } else if (weeklydata.getTemperature() == null) {
//
//                                } else {
//
//                                    //rain
//                                    Double week1rain = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                    week1templist.add(week1rain);
//
//                                    //Temp
//                                    Double week1temp = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                    week1rainlist.add(week1temp);
//
//                                    //morning
//                                    dataref.orderByChild("Time").startAt("04:00:01").endAt("11:00:00").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                List<Double> list = new ArrayList<>();
//                                                List<Double> day1rainlist = new ArrayList<>();
//
//
//                                                for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                    wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                    if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                    } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                    } else if (mornidataweek1.getHumidity() == null) {
//
//                                                    } else if (mornidataweek1.getTemperature() == null) {
//
//                                                    } else {
//
//                                                        //rain
//                                                        Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                        day1rainlist.add(day1rainref);
//
//                                                        //Temp
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
////                                                                                    String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
////                                                                                    int wk1rain = Integer.parseInt(morningrainweek1);
////                                                                                    int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morningtempweek1 = mornidataweek1.getTemperature().toString();
////                                                                                    int wk1temp = Integer.parseInt(morningtempweek1);
////                                                                                    int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morninghumidweek1 = mornidataweek1.getHumidity().toString();
////                                                                                    int wk1humid = Integer.parseInt(morninghumidweek1);
////                                                                                    int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
////
////                                                                                    String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
////                                                                                    int wk1pressure = Integer.parseInt(morningpressureweek1);
////                                                                                    int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
////
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//
//                                                    }
//
//                                                }
//                                                Double high2 = Collections.max(list);
//                                                Double low2 = Collections.min(list);
//                                                Double avaragetemp = (high2 + low2) / 2;
//                                                mngavtempweek1.setText(String.valueOf(avaragetemp));
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                    //afternoon
//                                    dataref.orderByChild("Time").startAt("11:00:01").endAt("15:00:00").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                List<Double> list = new ArrayList<>();
//                                                List<Double> day1rainlist = new ArrayList<>();
//
//                                                for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                    wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                    if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                    } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                    } else if (mornidataweek1.getHumidity() == null) {
//
//                                                    } else if (mornidataweek1.getTemperature() == null) {
//
//                                                    } else {
//                                                        //rain
//                                                        Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                        day1rainlist.add(day1rainref);
//
//                                                        //Temp
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//                                                        String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                        int wk1rain = Integer.parseInt(morningrainweek1);
//                                                        int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                        int wk1temp = Integer.parseInt(morningtempweek1);
//                                                        int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                        String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                        int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                        int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                        int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                        int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//                                                    }
//
//                                                }
//                                                Double high2 = Collections.max(list);
//                                                Double low2 = Collections.min(list);
//                                                Double avaragetemp = (high2 + low2) / 2;
//                                                aftavtempweek1.setText(String.valueOf(avaragetemp));
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                    //evening
//                                    dataref.orderByChild("Time").startAt("15:00:01").endAt("19:00:00").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//
//                                                List<Double> list = new ArrayList<>();
//                                                List<Double> day1rainlist = new ArrayList<>();
//
//                                                for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                    wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                    if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                    } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                    } else if (mornidataweek1.getHumidity() == null) {
//
//                                                    } else if (mornidataweek1.getTemperature() == null) {
//
//                                                    } else {
//
//                                                        //rain
//                                                        Double day1rainref = dataSnapshoti.child("Rainfall_1hr").getValue(Double.class);
//                                                        day1rainlist.add(day1rainref);
//
//                                                        //Temp
//                                                        Double alltemps = dataSnapshoti.child("Temperature").getValue(Double.class);
//                                                        list.add(alltemps);
//
//                                                        String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                        int wk1rain = Integer.parseInt(morningrainweek1);
//                                                        int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                        int wk1temp = Integer.parseInt(morningtempweek1);
//                                                        int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                        String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                        int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                        int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                        int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                        int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//
////                                                                                    mngavhumidityweek1.setText(mrnhumidweek1);
////                                                                                    mngavpressureweek1.setText(mrnpressureweek1);
////                                                                                    mngavrainfallweek1.setText(mrnrainweek1);
////                                                                                    mngavtempweek1.setText(mrntempweek1);
//                                                    }
//
//                                                }
//
//                                                Double high2 = Collections.max(list);
//                                                Double low2 = Collections.min(list);
//                                                Double avaragetemp = (high2 + low2) / 2;
////                                                                            .setText(String.valueOf(avaragetemp));
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                    //night
//                                    dataref.orderByChild("Time").startAt("19:00:01").endAt("04:00:00").addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Iterable<DataSnapshot> weeklysnapshot = dataSnapshot.getChildren();
//                                                for (DataSnapshot dataSnapshoti : weeklysnapshot) {
//
//                                                    wetherdata mornidataweek1 = dataSnapshoti.getValue(wetherdata.class);
//
//                                                    if (mornidataweek1.getRainfall_1hr() == null) {
//
//
//                                                    } else if (mornidataweek1.getBarometric_Pressure() == null) {
//
//                                                    } else if (mornidataweek1.getHumidity() == null) {
//
//                                                    } else if (mornidataweek1.getTemperature() == null) {
//
//                                                    } else {
//
//                                                        String morningrainweek1 = mornidataweek1.getRainfall_1hr().toString();
//                                                        int wk1rain = Integer.parseInt(morningrainweek1);
//                                                        int mrnrainweek1 = (int) (wk1rain / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningtempweek1 = mornidataweek1.getTemperature().toString();
//                                                        int wk1temp = Integer.parseInt(morningtempweek1);
//                                                        int mrntempweek1 = (int) (wk1temp / (dataSnapshot.getChildrenCount()));
//
//                                                        String morninghumidweek1 = mornidataweek1.getHumidity().toString();
//                                                        int wk1humid = Integer.parseInt(morninghumidweek1);
//                                                        int mrnhumidweek1 = (int) (wk1humid / (dataSnapshot.getChildrenCount()));
//
//                                                        String morningpressureweek1 = mornidataweek1.getBarometric_Pressure().toString();
//                                                        int wk1pressure = Integer.parseInt(morningpressureweek1);
//                                                        int mrnpressureweek1 = (int) (wk1pressure / (dataSnapshot.getChildrenCount()));
//                                                        mngavhumidityweek1.setText(mrnhumidweek1);
//                                                        mngavpressureweek1.setText(mrnpressureweek1);
//                                                        mngavrainfallweek1.setText(mrnrainweek1);
//                                                        mngavtempweek1.setText(mrntempweek1);
//                                                    }
//
//                                                }
//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//
//                                }
//                            }
//                            Double high2 = Collections.max(week1templist);
//                            Double low2 = Collections.min(week1templist);
//                            Double avaragetemp1 = (high2 + low2) / 2;
////                            mngavtempweek1.setText(String.valueOf(avaragetemp1));
//
//
//                            if ((avaragetemp1) < -9) {
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.icy);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= (-9) && (avaragetemp1) < 0) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.iceberg);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 0 && (avaragetemp1) < 7) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.snow);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 7 && (avaragetemp1) < 12) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.snowstorm);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 12 && (avaragetemp1) < 18) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.partly_cloudy);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 18 && (avaragetemp1) < 24) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.comfortable_weather);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 24 && (avaragetemp1) < 29) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.smilly_sun);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if ((avaragetemp1) >= 29 && (avaragetemp1) < 35) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.sunny);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            } else if (avaragetemp1 >= 35) {
//
//
//                                mHandler = new Handler();
//                                myrun = new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        week1image.setImageResource(R.drawable.sunny_day);
//                                    }
//                                };
//
//                                mHandler.postDelayed(myrun, 00L);
//                            }
//                            lowtempweek1.setText("/" + low2.toString().substring(0, 2) + "\u2103");
//                            hightempweek1.setText(high2.toString().substring(0, 2) + "\u2103");
//                        }
//
//
//                    }
//
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        }
//
//
//        //february
//        if (monthspinnernumber=="02"){
//            currentmonth = dataref.orderByChild("Date").startAt(year + "-"+indexofmonth+"-01").endAt(year + "-"+indexofmonth+"-28");
//
//        }


    }
//        dataref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//
//                DataPoint[] dt = new DataPoint[(int) dataSnapshot.getChildrenCount()];
//                int index=0;
//
//                for (DataSnapshot mydatasnapshot : dataSnapshot.getChildren())
//
//                {
//                    wetherdata pointvalu = mydatasnapshot.getValue(wetherdata.class);
//                    String time=pointvalu.getTime();
//                    String splitted1 = time.split(":")[0];
//                    String splitted2 = time.split(":")[1];
//                    String splitted3 = time.split(":")[2];
//                    String combined= splitted1+splitted2+splitted3;
//                    long x=Long.parseLong(combined);
//                    if (("2020-06-12").equalsIgnoreCase(pointvalu.getDate())) {
//                      dt[index] = new DataPoint(Double.parseDouble(combined),(pointvalu.getTemperature().intValue()));
//                       index++;
//                        Toast.makeText(getContext(), "trrddr"+x, Toast.LENGTH_SHORT).show();
//                    }
//
//
//
//                }
//
//                series.resetData(dt);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//    }
}
//    private void showChart(ArrayList<Entry> datavalues) {
//        templinedataset.setValues(datavalues);
//        templinedataset.setLabel("Temperature values");
//        iLineDataSets.clear();
//        iLineDataSets.add(templinedataset);
//        lineData = new LineData(iLineDataSets);
//        templinechart.clear();
//        templinechart.setData(lineData);
//        templinechart.invalidate();
//    }

