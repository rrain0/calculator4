package com.rrain.calculator4.calculator;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
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

public class ButtonsSizePortDialog extends DialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Calculator calc;

    private final int max;
    private final BtnSizePort oldSize;

    private BtnSizePort size;
    private boolean together;

    private TextView textView1;
    private SeekBar seekBar1;
    private TextView textView2;
    private SeekBar seekBar2;
    private CheckBox checkBox;

    public ButtonsSizePortDialog(Calculator calc) {
        this.calc = calc;
        size = oldSize = BtnSizePort.get(calc);
        max = DisplayManager.getWidth(calc)/3;// TODO: 29.08.2020 вычислять
        together = size.isSameWidth();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final LinearLayout root = (LinearLayout) calc.getLayoutInflater().inflate(R.layout.dialog_change_buttons_size_port, null);

        final Dialog dialog = new Dialog(calc);
        dialog.setContentView(root);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.setDimAmount(0f);
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView1 = root.findViewById(R.id.button_ratio_tv);
        seekBar1 = root.findViewById(R.id.button_width_seekbar);
        seekBar1.setMax(max);
        textView1.setOnClickListener(this);
        seekBar1.setOnSeekBarChangeListener(this);
        textView2 = root.findViewById(R.id.button_ratio_tv_2);
        seekBar2 = root.findViewById(R.id.button_width_seekbar_2);
        seekBar2.setMax(max);
        textView2.setOnClickListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        checkBox = root.findViewById(R.id.change_together);
        updateCheckBox();
        checkBox.setOnClickListener(this);
        root.findViewById(R.id.btn_select_dialog_prev).setOnClickListener(this);
        root.findViewById(R.id.btn_select_dialog_default).setOnClickListener(this);
        root.findViewById(R.id.btn_select_dialog_ok).setOnClickListener(this);

        updateTextView1();
        updateSeekBar1();
        updateTextView2();
        updateSeekBar2();

        return dialog;
    }

    private void updateTextView1(){ textView1.setText(Util.round3String(size.trw)+":"+Util.round3String(size.trh)); }
    private void updateSeekBar1(){ seekBar1.setProgress((int)size.tw); }
    private void setButtonsSize1(){ size.set(calc); }
    private void updateTextView2(){ textView2.setText(Util.round3String(size.mrw)+":"+Util.round3String(size.mrh)); }
    private void updateSeekBar2(){ seekBar2.setProgress((int)size.mw); }
    private void setButtonsSize2(){ size.set(calc); }
    private void updateCheckBox(){ checkBox.setChecked(together); }

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
        calc.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);//зафиксировать ориентацию экрана
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
                        InputDialog.INPUT_TIME_DECIMAL,
                        txt -> {
                            try {
                                String[] arr = txt.split("[^\\d\\.]+");
                                size = size.buildNew(true, Float.parseFloat(arr[0]), Float.parseFloat(arr[1]));
                                //size = calc.new ButtonSizePortrait(size.tw, Float.parseFloat(arr[0]), Float.parseFloat(arr[1]), size.mw, size.mrw, size.mrh);
                                updateTextView1();
                                setButtonsSize1();
                            } catch (NumberFormatException e) {
                                toastShort(getString(R.string.incorrect_expression));
                            }
                        },
                        "1:1", "16:9", "16:10", "16:11", "16:12", "16:13", "16:14"
                );
                break;
            case R.id.button_ratio_tv_2:
                InputDialog.show(
                        calc,
                        getString(R.string.button_side_ratio),
                        InputDialog.INPUT_TIME,
                        txt -> {
                            try {
                                String[] arr = txt.split("[^\\d\\.]+");
                                size = size.buildNew(false, Float.parseFloat(arr[0]), Float.parseFloat(arr[1]));
                                //size = calc.new ButtonSizePortrait(size.tw, size.trw, size.trh, size.mw, Float.parseFloat(arr[0]), Float.parseFloat(arr[1]));
                                updateTextView2();
                                setButtonsSize2();
                            } catch (NumberFormatException e) {
                                toastShort(getString(R.string.incorrect_expression));
                            }
                        },
                        "1:1", "16:9", "16:10", "16:11", "16:12", "16:13", "16:14"
                );
                break;
            case R.id.change_together:
                together = checkBox.isChecked();
                if (together) size = size.buildNew(size.mw); //size = calc.new ButtonSizePortrait(size.mw, size.trw, size.trh, size.mw, size.mrw, size.mrh);
                updateSeekBar1();
                setButtonsSize1();
                break;
            case R.id.btn_select_dialog_prev:
                size = oldSize;
                together = size.isSameWidth();
                updateCheckBox();
                updateTextView1();
                updateSeekBar1();
                updateTextView2();
                updateSeekBar2();
                setButtonsSize1();
                setButtonsSize2();
                break;
            case R.id.btn_select_dialog_default:
                size = new BtnSizePort(calc);
                together = size.isSameWidth();
                updateCheckBox();
                updateTextView1();
                updateSeekBar1();
                updateTextView2();
                updateSeekBar2();
                setButtonsSize1();
                setButtonsSize2();
                break;
            case R.id.btn_select_dialog_ok:
                this.dismiss();
                break;
        }
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            switch (seekBar.getId()){
                case R.id.button_width_seekbar:
                    if (together){
                        size = size.buildNew(progress);
                        //size = calc.new ButtonSizePortrait(progress, size.trw, size.trh, progress, size.mrw, size.mrh);
                        seekBar2.setProgress(progress);
                        setButtonsSize1();
                        setButtonsSize2();
                    } else {
                        size = size.buildNew(true, progress);
                        //size = calc.new ButtonSizePortrait(progress, size.trw, size.trh, size.mw, size.mrw, size.mrh);
                        setButtonsSize1();
                    }
                    break;
                case R.id.button_width_seekbar_2:
                    if (together){
                        size = size.buildNew(progress);
                        //size = calc.new ButtonSizePortrait(progress, size.trw, size.trh, progress, size.mrw, size.mrh);
                        seekBar1.setProgress(progress);
                        setButtonsSize1();
                        setButtonsSize2();
                    } else {
                        size = size.buildNew(false, progress);
                        //size = calc.new ButtonSizePortrait(size.tw, size.trw, size.trh, progress, size.mrw, size.mrh);
                        setButtonsSize2();
                    }
                    break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
}
