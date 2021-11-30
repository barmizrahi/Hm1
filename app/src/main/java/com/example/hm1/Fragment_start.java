package com.example.hm1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

public class Fragment_start extends Fragment {
    private MaterialTextView frame1_LBL_title;
    private MaterialButton frame1_BTN_buttonMode;
    private MaterialButton frame1_BTN_sensorMode;
    private MaterialButton frame1_BTN_topTen;
    private AppCompatActivity activity;
    private CallBack_Start callBack_Start;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setCallBackStart(CallBack_Start callBack_Start) {
        this.callBack_Start = callBack_Start;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_button_or_senssor, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        frame1_BTN_buttonMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack_Start!=null){
                    callBack_Start.gameWithButton();
                }
            }
        });

        frame1_BTN_sensorMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack_Start!=null){
                    callBack_Start.gameWithSensor();
                }
            }
        });

        frame1_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callBack_Start!=null){
                    callBack_Start.showTopTen();
                }
            }
        });
    }

    private void findViews(View view) {
        frame1_BTN_buttonMode = view.findViewById(R.id.frame1_BTN_buttonMode);
        frame1_BTN_sensorMode = view.findViewById(R.id.frame1_BTN_sensorMode);
        frame1_BTN_topTen = view.findViewById(R.id.frame1_BTN_topTen);
    }
}
