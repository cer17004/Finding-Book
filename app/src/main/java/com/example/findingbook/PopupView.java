package com.example.findingbook;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;


public class PopupView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));
    }

    public void onEnterAddress(View enterAddress){
        Intent prev = new Intent();
        setResult(RESULT_FIRST_USER, prev);
        finish();

    }

    public void onDeleteMarker(View deleteMarkerBtn){
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        this.finish();
    }

}
