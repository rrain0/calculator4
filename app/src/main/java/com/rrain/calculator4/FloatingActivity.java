package com.rrain.calculator4;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FloatingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setLayout(400, 500);

        /*int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                this.getWindow().setLayout(900, 755);
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                this.getWindow().setLayout(1080, 1000); //width x height
                break;
        }*/
    }

}
