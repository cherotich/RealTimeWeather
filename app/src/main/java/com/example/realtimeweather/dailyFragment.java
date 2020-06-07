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
import java.util.Calendar;

public class dailyFragment extends Fragment {
    DatePicker pickdate;

    public dailyFragment() {
        // Required empty public constructor
    }

    String todaychosen;
    private TabAdapter adapter;
    // private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    DatePickerDialog.OnDateSetListener date;
    Calendar myorigcalender = Calendar.getInstance();
    EditText inputdate;
    TextView generalvalue, generaWeather, tempvalue, rainfallvalue, humidityvalue, windspeedvalue, barometervalue;
    Calendar calendar;
    String saveCurrentTime, monthno, day, year, monthstring, today, exacttoday;
    DatabaseReference dataref;
    ImageView updateweatherdata;
    Query dataquery;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.daily_fragment, container, false);
        //database reference
        dataref = FirebaseDatabase.getInstance().getReference("Weather_Data");
//        dataquery=dataref.orderByChild("Date").equalTo(todaychosen);

        ValueEventListener eventListener;

        //update data btn
        updateweatherdata = view.findViewById(R.id.updateweatherdata);
        updateweatherdata.setVisibility(View.INVISIBLE);


        //viewpager
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
        exacttoday = year + "-" + monthno + "-" + day;
        inputdate.setText(exacttoday);
        SimpleDateFormat yesterday = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DATE, -1);
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

    @Override
    public void onStart() {
        super.onStart();

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


                            tempvalue.setText(String.valueOf(data.getTemperature()) + " \u2103");
                            rainfallvalue.setText(String.valueOf(data.getRainfall_1hr()) + " mm");
                            humidityvalue.setText(String.valueOf(data.getHumidity()) + " %");
                            windspeedvalue.setText(String.valueOf(data.getA_Windsepeed()) + " m/s");
                            barometervalue.setText(String.valueOf(data.getBarometric_Pressure()) + " Pa");


                        } else {
                            tempvalue.setText(0 + " \u2103");
                            rainfallvalue.setText(0 + " mm");
                            humidityvalue.setText(0 + " %");
                            windspeedvalue.setText(0 + " m/s");
                            barometervalue.setText(0 + " Pa");
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
