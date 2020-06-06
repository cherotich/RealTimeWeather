package com.example.realtimeweather;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.realtimeweather.adapter.TabAdapter;
import com.example.realtimeweather.models.wetherdata;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dailyFragment extends Fragment {
    DatePicker pickdate;

    public dailyFragment() {
        // Required empty public constructor
    }

    private TabAdapter adapter;
    // private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    Calendar myorigcalender = Calendar.getInstance();
    EditText inputdate;
    TextView generalvalue, generaWeather, tempvalue, rainfallvalue, humidityvalue, windspeedvalue, barometervalue;
    Calendar calendar;
    String saveCurrentTime, monthno, day, year, monthstring, today, exacttoday;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.daily_fragment, container, false);
        viewPager = view.findViewById(
                R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab_layout);

        //tablayout

        adapter = new TabAdapter(getChildFragmentManager());
        adapter.addFragment(new todayFragment(), "Today");
        adapter.addFragment(new yesterdayFragment(), "Yesterday");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab_layout);


        //textviews

        tempvalue = view.findViewById(R.id.dailytemptxt);
        rainfallvalue = view.findViewById(R.id.dailyrainfalltxt);
        humidityvalue = view.findViewById(R.id.dailyhumiditytxt);
        windspeedvalue = view.findViewById(R.id.dailywindspeedtxt);
        barometervalue = view.findViewById(R.id.dailypressuretxt);


        inputdate= view.findViewById(R.id.mycalender);

        calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        day = (String) DateFormat.format("dd", calendar.getTime());
        year = (String) DateFormat.format("yyyy", calendar.getTime());
        monthno = (String) DateFormat.format("MM", calendar.getTime());
        monthstring = (String) DateFormat.format("MMM", calendar.getTime());
        exacttoday = year+"-"+monthno+"-"+day;
        inputdate.setText(exacttoday);







//        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
//        ValueEventListener eventListener;
//
//        dataref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                   // int tot = 0;
//                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
//                    for (DataSnapshot dataSnapshoti : childrensnapshot) {
//
//                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);
//
//                        if ((today).equalsIgnoreCase(data.getDate())){
//
//                            //tot = tot + (data.getHumidity().intValue());
//                            //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();
//
//
//                            tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
//                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
//                            humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
//                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
//                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");
//
//
//                        }
//
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










        final DatePickerDialog.OnDateSetListener date= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myorigcalender.set(Calendar.YEAR,year);
                myorigcalender.set(Calendar.MONTH,month);
                myorigcalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel();
            }
        };
        inputdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(),date,myorigcalender
                .get(Calendar.YEAR), myorigcalender.get(Calendar.MONTH),
                        myorigcalender.get(Calendar.DAY_OF_MONTH)).show();






            }
        });


        return view;
    }


    private void updateLabel() {
//        String myFormat ="yy-MM-dd";
        //SimpleDateFormat simpleDateFormat =new SimpleDateFormat(myFormat);

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(myorigcalender.getTime());
        day = (String) DateFormat.format("dd", myorigcalender.getTime());
        year = (String) DateFormat.format("yyyy", myorigcalender.getTime());
        monthno = (String) DateFormat.format("MM", myorigcalender.getTime());
        monthstring = (String) DateFormat.format("MMM", myorigcalender.getTime());
        today = year+"-"+monthno+"-"+day;
        inputdate.setText(today);

        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
        ValueEventListener eventListener;

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // int tot = 0;
                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);

                        if ((today).equalsIgnoreCase(data.getDate())){

                            //tot = tot + (data.getHumidity().intValue());
                            //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();


                            tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
                            humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");


                        }
                        else {
                            tempvalue.setText(0 +" \u2103");
                            rainfallvalue.setText(0+" mm");
                            humidityvalue.setText(0+" %");
                            windspeedvalue.setText(0+" m/s");
                            barometervalue.setText(0+" Pa");
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



    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
        ValueEventListener eventListener;

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // int tot = 0;
                    Iterable<DataSnapshot> childrensnapshot = dataSnapshot.getChildren();
                    for (DataSnapshot dataSnapshoti : childrensnapshot) {

                        wetherdata data = dataSnapshoti.getValue(wetherdata.class);

                        if ((exacttoday).equalsIgnoreCase(data.getDate())){

                            //tot = tot + (data.getHumidity().intValue());
                            //Toast.makeText(getContext(), "Data  exist" +tot, Toast.LENGTH_LONG).show();


                            tempvalue.setText(String.valueOf(data.getTemperature())+" \u2103");
                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr())+" mm");
                            humidityvalue.setText(String.valueOf(data.getHumidity())+" %");
                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed())+" m/s");
                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure())+" Pa");


                        }
                        else {
                            tempvalue.setText(0 +" \u2103");
                            rainfallvalue.setText(0+" mm");
                            humidityvalue.setText(0+" %");
                            windspeedvalue.setText(0+" m/s");
                            barometervalue.setText(0+" Pa");
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



    }
}
