package com.rrain.calculator4.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.rrain.calculator4.DisplayManager;
import com.rrain.calculator4.R;
import com.rrain.calculator4.Util;
import com.rrain.calculator4.dialog.InputDialog;

import static com.rrain.calculator4.ViewUtil.toastShort;

public class ButtonsSizeLandDialog extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Calculator calc;

    private final int max;
    private final BtnSizeLand oldSize;

    private BtnSizeLand size;

    private TextView textView;
    private SeekBar seekBar;

    public ButtonsSizeLandDialog(Calculator calc) {
        this.calc = calc;
        this.max = DisplayManager.getWidth(calc)/6;// TODO: 29.08.2020 вычислять
        size = oldSize = BtnSizeLand.get(calc);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LinearLayout root = (LinearLayout) calc.getLayoutInflater().inflate(R.layout.dialog_change_buttons_size_land, null);

        final Dialog dialog = new Dialog(calc);
        dialog.setContentView(root);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setDimAmount(0f);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView = root.findViewById(R.id.button_ratio_tv);
        seekBar = root.findViewById(R.id.button_width_seekbar);
        seekBar.setMax(max);
        textView.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        root.findViewById(R.id.btn_select_dialog_prev).setOnClickListener(this);
        root.findViewById(R.id.btn_select_dialog_default).setOnClickListener(this);
        root.findViewById(R.id.btn_select_dialog_ok).setOnClickListener(this);

        updateTextView();
        updateSeekBar();

        return dialog;

        //setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialog); //стиль диалога

            /*
            //установить позицию диалога относительно левого верхнего угла
            // set "origin" to top left corner, so to speak
            window.setGravity(Gravity.TOP|Gravity.LEFT);

            // after that, setting values for x and y works "naturally"
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 300;
            params.y = 100;
            window.setAttributes(params);
        */
    }

    private void updateSeekBar(){ seekBar.setProgress((int)size.lw); }
    private void updateTextView(){ textView.setText(Util.round3String(size.lrw)+":"+Util.round3String(size.lrh)); }
    private void setButtonsSize(){ size.set(calc); }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        size.save(calc);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        size.save(calc);
    }

    @Override
    public void onResume() {
        calc.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);//зафиксировать ориентацию экрана
        super.onResume();
    }

    @Override
    public void onDestroy() {
        calc.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);//разрешить поворот экрана
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ratio_tv:
                InputDialog.show(
                        calc,
                        getString(R.string.button_side_ratio),
                        InputDialog.INPUT_TIME,
                        txt -> {
                            try {
                                String[] arr = txt.split("[^\\d\\.]+");
                                size = size.buildNew(Float.parseFloat(arr[0]), Float.parseFloat(arr[1]));
                                //size = new ButtonSizeLandscape(size.lw, Float.parseFloat(arr[0]), Float.parseFloat(arr[1]));
                                updateTextView();
                                setButtonsSize();
                            } catch (NumberFormatException e) {
                                toastShort(getString(R.string.incorrect_expression));
                            }
                        },
                        "1:1", "16:9",   "16:10", "16:11", "16:12", "16:13",   "16:14"
                );
                break;
            case R.id.btn_select_dialog_prev:
                size = oldSize;
                updateTextView();
                updateSeekBar();
                setButtonsSize();
                break;
            case R.id.btn_select_dialog_default:
                size = new BtnSizeLand(calc);
                updateTextView();
                updateSeekBar();
                setButtonsSize();
                break;
            case R.id.btn_select_dialog_ok:
                this.dismiss();
                break;
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            size = size.buildNew(progress);
            //size = calc.new ButtonSizeLandscape(progress, size.lrw, size.lrh);
            setButtonsSize();
        }
    }
    @Override public void onStartTrackingTouch(SeekBar seekBar) { }
    @Override public void onStopTrackingTouch(SeekBar seekBar) { }
}

