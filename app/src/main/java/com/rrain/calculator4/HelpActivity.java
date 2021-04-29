package com.rrain.calculator4;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.rrain.calculator4.calculator.Calculator.theme;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ThemeManager themeManager = App.getApp().getThemeManager();
        setTheme(themeManager.getStyleIdByName(theme));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_1_app_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.help_screen_toolbar);
        setSupportActionBar(toolbar);

        ViewUtil.showBackBtn(this, true);
        toolbar.setNavigationOnClickListener(view->onBackPressed());

        /*findViewById(R.id.scrolllll).setOnClickListener(this);
        findViewById(R.id.texttttttt).setOnClickListener(this);
        findViewById(R.id.scrolllll2).setOnClickListener(this);
        findViewById(R.id.texttttttt2).setOnClickListener(this);*/

    }


    @Override
    public void onClick(View view) {
        /*Log.e("onClick", "onClick: ");
        switch (view.getId()){
            case R.id.scrolllll:
                Log.e("onClick", "onClick: scrool");
                break;
            case R.id.texttttttt:
                Log.e("onClick", "onClick: text");
                break;
            case R.id.scrolllll2:
                Log.e("onClick", "onClick: scrool2");
                break;
            case R.id.texttttttt2:
                Log.e("onClick", "onClick: text2");
                break;
        }*/
    }

}
