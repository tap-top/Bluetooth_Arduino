package com.boe.tww.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.boe.tww.builders.SciChartBuilder;

import java.util.ArrayList;

import static com.boe.tww.bluetooth.fragment1.cleanPoint;

public class showOscillogram extends Activity implements View.OnClickListener {
    public static ArrayList<Integer> ocgData = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SciChartBuilder.init(this);
        setContentView(R.layout.show_oscillogram);

        findViewById(R.id.returnButton1).
                setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.returnButton1:
                returnMain();
        }
    }
    private void returnMain(){
        cleanPoint();
        onDestroy();
        Intent intent = new Intent(showOscillogram.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
