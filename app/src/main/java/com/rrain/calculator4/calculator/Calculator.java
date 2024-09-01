package com.rrain.calculator4.calculator;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import com.annimon.stream.Stream;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.rrain.calculator4.App;
import com.rrain.calculator4.CalculationUtil.Expression;
import com.rrain.calculator4.CalculationUtil.Expression.*;
import com.rrain.calculator4.Clipboard;
import com.rrain.calculator4.DisplayManager;
import com.rrain.calculator4.GetBrackets;
import com.rrain.calculator4.HelpActivity;
import com.rrain.calculator4.HistoryManager;
import com.rrain.calculator4.R;
import com.rrain.calculator4.SettingsActivity;
import com.rrain.calculator4.SettingsManager;
import com.rrain.calculator4.ThemeManager;
import com.rrain.calculator4.ViewUtil;
import com.rrain.calculator4.databinding.Calculator1DrawerLayoutBinding;
import com.rrain.calculator4.db.HistoryEntry;
import com.rrain.calculator4.dialog.ConfirmDialog;
import com.rrain.calculator4.dialog.SelectDialog;
import com.rrain.calculator4.views.GestureSelectionView;

import static com.rrain.calculator4.CalculationUtil.divideDigits;
import static com.rrain.calculator4.CalculationUtil.decToOther;
import static com.rrain.calculator4.CalculationUtil.exponentFormat;
import static com.rrain.calculator4.CalculationUtil.redesignateInfinities;
import static com.rrain.calculator4.CalculationUtil.removeBitDividers;
import static com.rrain.calculator4.CalculationUtil.roundUp;
import static com.rrain.calculator4.CalculationUtil.stripTrailingZeros;
import static com.rrain.calculator4.CalculationUtil.toDec;
import static com.rrain.calculator4.CalculationUtil.toPlainString;
import static com.rrain.calculator4.ViewUtil.toastLong;
import static com.rrain.calculator4.ViewUtil.toastShort;

import org.jetbrains.annotations.Contract;


public class Calculator
        extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    public static final int REQUEST_CODE_SETTINGS = 1;
    public static final String INTENT_CODE_CHANGE_BUTTONS_SIZE = "changeButtonsSize";
    public static final String INTENT_CODE_RESTART = "restart";


    public static boolean enableAutocalculation;
    public static boolean enableRounding;
    public static int roundingPrecision;
    public static int numberFormat; // 0 обычный  1 научный  3 инженерный
    public static String digitSeparator;
    public static boolean enableAutodeleteOfHistory;
    public static int historyLimit;
    public static boolean enableConversionToast;
    public static boolean enableSecondField;
    public static boolean enableSavingByEquals;
    public static String theme;
    public static int angleUnit; // 0_rad  1_deg
    public static int radix; // 2_bin  8_oct  10_dec  16_hex
    static ArrayList<Integer> bracketColors1 = new ArrayList<>();

    //static boolean doRestart = false;


    private ViewModel viewModel;

    private EditText getExpressionEditText1() { return findViewById(R.id.expression_edit_text_1); }
    private boolean alreadyChanged1 = false;
    public static UndoManager undoManager1 = new UndoManager(100);
    private TextView getResultTextView1() { return findViewById(R.id.result_text_view_1); }
    private TextView getInfoTextView1() { return findViewById(R.id.info_text_view_1); }


    private EditText getExpressionEditText2() { return findViewById(R.id.expression_edit_text_2); }
    private boolean alreadyChanged2 = false;
    public static UndoManager undoManager2 = new UndoManager(100);
    private TextView getResultTextView2() { return findViewById(R.id.result_text_view_2); }
    private TextView getInfoTextView2() { return findViewById(R.id.info_text_view_2); }


    CoveringWithBrackets coveringWithBrackets = new CoveringWithBrackets();
    ChangingSing changingSign = new ChangingSing();


    SettingsManager settings;
    private HistoryManager historyManager;
    private ThemeManager themeManager;
    private Clipboard clipboard;
    Calculator1DrawerLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Date start = new Date();*/

        settings = App.getApp().getSettingsManager();
        historyManager = App.getApp().getHistoryManager();
        themeManager = App.getApp().getThemeManager();
        clipboard = App.getApp().getClipboard();
        LiveData<List<HistoryEntry>> historyLive = historyManager.getHistoryLive();
        ThemeManager themeManager = App.getApp().getThemeManager();

        theme = settings.getTheme();
        setTheme(themeManager.getStyleIdByName(theme));

        super.onCreate(savedInstanceState);
        getOnBackPressedDispatcher().addCallback(this, onBackPressedCallback);

        binding = DataBindingUtil.setContentView(this, R.layout.calculator_1_drawer_layout);

        Toolbar toolbar = findViewById(R.id.calculator_toolbar);
        // использовать тулбар как экшон бар, где располагаются кнопки меню и др. системные элементы
        setSupportActionBar(toolbar);


        viewModel = new ViewModel(this);

        bracketColors1.clear();
        TypedArray ta = getTheme().obtainStyledAttributes(new int[]{
                R.attr.bracket0color0,
                R.attr.bracket0color1,
                R.attr.bracket0color2,
                R.attr.bracket0color3,
                R.attr.bracket0color4,
                R.attr.bracket0color5
        });
        for (int i = 0; i < ta.length(); i++) bracketColors1.add(ta.getColor(i, Color.BLACK));


        //// История
        RecyclerView historyRecyclerView = (RecyclerView)findViewById(R.id.history_recycler_view); // находим RecyclerView
        binding.setBottomSheetHeight(DisplayManager.getHeight(this) * 3f/5f);
        HistoryRecyclerAdapter historyAdapter = new HistoryRecyclerAdapter(Collections.EMPTY_LIST); // создаем адаптер
        historyRecyclerView.setAdapter(historyAdapter); // устанавливаем для списка адаптер
        historyLive.observe(this, historyAdapter::updateContainer);


        // Полезный код
        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/


        Button angleUnitButton = findViewById(R.id.btn_angle_unit);
        Button radixBtn = findViewById(R.id.btn_radix);

        ViewUtil.showKeyboardOnFocus(false, getExpressionEditText1());
        ViewUtil.showKeyboardOnFocus(false, getExpressionEditText2());


        getExpressionEditText1().addTextChangedListener(new TextWatcher() {

            /**
             * This method is called to notify you that, within <code>s</code>,
             * the <code>count</code> characters beginning at <code>start</code>
             * are about to be replaced by new text with length <code>after</code>.
             * It is an error to attempt to make changes to <code>s</code> from
             * this callback.
             *
             * public void beforeTextChanged(CharSequence s, int start, int count, int after);
             */
            @Override
            public void beforeTextChanged(
                    CharSequence charSequence, int posWhereCharsToBeAdded, int countCharsToBeReplaced, int i2) {
                if (!alreadyChanged1 && undoManager1.getLast()!=null
                        && getExpressionEditText1().getSelectionStart()!=undoManager1.getLast().pos
                        && !undoManager1.alreadyChanged){
                    undoManager1.add(getExpressionEditText1());
                    undoManager1.addSelectionToLastEntry(getExpressionEditText1().getSelectionStart());
                }
            }


            /**
             * This method is called to notify you that, within <code>s</code>,
             * the <code>count</code> characters beginning at <code>start</code>
             * have just replaced old text that had length <code>before</code>.
             * It is an error to attempt to make changes to <code>s</code> from
             * this callback.
             *
             * public void onTextChanged(CharSequence s, int start, int before, int count);
             */
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (alreadyChanged1)return;

                alreadyChanged1 = true;

                viewModel.updateResultTV1TextColor(false);
                getInfoTextView1().setText("");

                try { stylizeExpression(getExpressionEditText1()); }
                catch (Exception e) { e.printStackTrace(); }

                undoManager1.add(getExpressionEditText1());

                alreadyChanged1 = false;

                if (enableAutocalculation)
                    calculateResultPreEntrance(getExpressionEditText1(), getResultTextView1(), getInfoTextView1(), !enableSavingByEquals);
            }
        });

        getExpressionEditText2().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!alreadyChanged2 && undoManager2.getLast()!=null
                        && getExpressionEditText2().getSelectionStart()!=undoManager2.getLast().pos
                        && !undoManager2.alreadyChanged){

                        undoManager2.add(getExpressionEditText2());
                        undoManager2.addSelectionToLastEntry(getExpressionEditText2().getSelectionStart());
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (alreadyChanged2) return;

                alreadyChanged2 = true;

                viewModel.updateResultTV2TextColor(false);
                getInfoTextView2().setText("");

                try {
                    stylizeExpression(getExpressionEditText2()); }
                catch (Exception e) { e.printStackTrace(); }

                undoManager2.add(getExpressionEditText2());

                alreadyChanged2 = false;

                if (enableAutocalculation)
                    calculateResultPreEntrance(getExpressionEditText2(), getResultTextView2(), getInfoTextView2(), !enableSavingByEquals);
            }
        });


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            GridLayout gridLayout = findViewById(R.id.btn_container1);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                gridLayout.getChildAt(i).setOnClickListener(this);
            }
            gridLayout = findViewById(R.id.btn_container2);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                gridLayout.getChildAt(i).setOnClickListener(this);
            }
        }else{
            GridLayout gridLayout = findViewById(R.id.btn_container);
            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                gridLayout.getChildAt(i).setOnClickListener(this);
            }
        }
        
        
        angleUnitButton.setOnClickListener(this);
        radixBtn.setOnClickListener(this);
        getResultTextView1().setOnClickListener(this);
        findViewById(R.id.btn_delete_from_1).setOnClickListener(this);
        findViewById(R.id.btn_equals_from_1).setOnClickListener(this);
        getResultTextView2().setOnClickListener(this);
        findViewById(R.id.btn_delete_from_2).setOnClickListener(this);
        findViewById(R.id.btn_equals_from_2).setOnClickListener(this);


        getResultTextView1().setOnLongClickListener(this);
        getResultTextView2().setOnLongClickListener(this);
        findViewById(R.id.btn_delete_from_1).setOnLongClickListener(this);
        findViewById(R.id.btn_delete_from_2).setOnLongClickListener(this);
        radixBtn.setOnLongClickListener(this);
        findViewById(R.id.btn_clear).setOnLongClickListener(this);
        findViewById(R.id.btn_left_parenthesis).setOnLongClickListener(this);
        findViewById(R.id.btn_right_parenthesis).setOnLongClickListener(this);
        findViewById(R.id.btn_parentheses).setOnLongClickListener(this);
        //findViewById(R.id.btn_square).setOnLongClickListener(this);
        //findViewById(R.id.btn_sqrt).setOnLongClickListener(this);
        findViewById(R.id.btn_zero).setOnLongClickListener(this);
        findViewById(R.id.btn_sin).setOnLongClickListener(this);
        findViewById(R.id.btn_cos).setOnLongClickListener(this);
        findViewById(R.id.btn_pi).setOnLongClickListener(this);
        findViewById(R.id.btn_abs).setOnLongClickListener(this);
        //findViewById(R.id.btn_tg).setOnLongClickListener(this);
        //findViewById(R.id.btn_ctg).setOnLongClickListener(this);
        findViewById(R.id.btn_sh).setOnLongClickListener(this);
        findViewById(R.id.btn_ch).setOnLongClickListener(this);
        //findViewById(R.id.btn_th).setOnLongClickListener(this);
        //findViewById(R.id.btn_cth).setOnLongClickListener(this);
        //findViewById(R.id.btn_log).setOnLongClickListener(this);
        //findViewById(R.id.btn_ln).setOnLongClickListener(this);
        findViewById(R.id.btn_coverWithBrackets).setOnLongClickListener(this);


        TouchListener touchListener = new TouchListener();
        findViewById(R.id.btn_tg).setOnTouchListener(touchListener);
        findViewById(R.id.btn_ln).setOnTouchListener(touchListener);
        findViewById(R.id.btn_square).setOnTouchListener(touchListener);
        findViewById(R.id.btn_sqrt).setOnTouchListener(touchListener);
        findViewById(R.id.btn_th).setOnTouchListener(touchListener);


        //Настройки

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            BtnSizePort.load(this).set(this);
        } else {
            BtnSizeLand.load(this).set(this);
        }


        enableAutocalculation = settings.isAutocalculationEnabled();
        enableSecondField = settings.isSecondFieldEnabled();
        enableRounding = settings.isRoundingEnabled();
        roundingPrecision = settings.getRoundingAccuracy();
        angleUnit = settings.getAngleUnit();
        radix = settings.getRadix();
        numberFormat = settings.getNumberFormat();
        digitSeparator = settings.getDigitSeparator();

        enableAutodeleteOfHistory = settings.isOldHistoryDeletingEnabled();
        historyLimit = settings.getHistoryLimit();
        enableSavingByEquals = settings.isSavingByEqualsEnabled();

        enableConversionToast = settings.isConversionToastEnabled();

        getExpressionEditText1().setText(settings.getExpression1());
        getExpressionEditText1().setSelection(settings.getExpression1SelStart(), settings.getExpression1SelEnd());
        getResultTextView1().setText(settings.getResult1());
        viewModel.updateResultTV1TextColor(settings.isResult1ColorPrimary());

        getExpressionEditText2().setText(settings.getExpression2());
        getExpressionEditText2().setSelection(settings.getExpression2SelStart(), settings.getExpression2SelEnd());
        getResultTextView2().setText(settings.getResult2());
        viewModel.updateResultTV2TextColor(settings.isResult2ColorPrimary());

        if (settings.getFocusedElem() == 1) getExpressionEditText2().requestFocus();
        //// Настройки

        updateVisibilityOfSecondFields();
        viewModel.updateAngleUnit(angleUnit);
        viewModel.updateRadix(radix);


        // реклама
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) { }
        });


        // TODO: 20.08.2018 text from html
        //((Button)findViewById(R.id.test)).setText(Html.fromHtml(("( <sub>(</sub>")));


        /*Date end = new Date();
        toastLong(end.getTime()-start.getTime()+"");*/
    }











    @Override
    public void onPause() { 

        SharedPreferences.Editor editor = settings.getEditor();

        settings.enableAutocalculation(editor, enableAutocalculation);
        settings.enableSecondField(editor, enableSecondField);
        settings.setAngleUnit(editor, angleUnit);
        settings.setRadix(editor, radix);
        settings.setNumberFormat(editor, numberFormat);
        settings.setDigitSeparator(editor, digitSeparator);
        settings.setFocusedElem(editor, getCurrentFocus()==getExpressionEditText1() ? 0 : 1);
        settings.setExpression1(editor, getExpressionEditText1().getText().toString());
        settings.setExpression1SelStart(editor, getExpressionEditText1().getSelectionStart());
        settings.setExpression1SelEnd(editor, getExpressionEditText1().getSelectionEnd());
        settings.setResult1(editor, getResultTextView1().getText().toString());
        settings.setResult1ColorPrimary(editor, viewModel.isResultTV1TextColorActual());
        settings.setExpression2(editor, getExpressionEditText2().getText().toString());
        settings.setExpression2SelStart(editor, getExpressionEditText2().getSelectionStart());
        settings.setExpression2SelEnd(editor, getExpressionEditText2().getSelectionEnd());
        settings.setResult2(editor, getResultTextView2().getText().toString());
        settings.setResult2ColorPrimary(editor, viewModel.isResultTV2TextColorActual());
        settings.setTheme(editor, theme);

        settings.apply(editor);

        super.onPause();
    }

    @Override
    public void onResume() { 
        super.onResume();

        /*if (doRestart){
            restartActivity();
            doRestart = false;
        } else{
            if( (getResultTextView2().getVisibility() == View.VISIBLE) != enableSecondField) updateVisibilityOfSecondFields();
            //if(changeButtonsSizeDialogEnabled) {changeButtonsSize(); changeButtonsSizeDialogEnabled = false; }
        }*/
        if( (getResultTextView2().getVisibility() == View.VISIBLE) != enableSecondField ) updateVisibilityOfSecondFields();
    }

    private final OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
        @Override public void handleOnBackPressed() {
            onBack();
        }
    };

    private void onBack() {
        //  Navigation View
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SETTINGS://результат приходит, когда возвращаемся в активити
                    //Log.e("CODEDE", "onActivityResult: "+requestCode);
                    if (data!=null) {
                        if (data.getBooleanExtra(INTENT_CODE_RESTART, false)) restartActivity();
                        if (data.getBooleanExtra(INTENT_CODE_CHANGE_BUTTONS_SIZE, false)) changeButtonsSize();
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_calc_on_the_go).setChecked(enableAutocalculation);
        menu.findItem(R.id.menu_second_field).setChecked(enableSecondField);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.calculator_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //Log.d("MENU", "onOptionsItemSelected: "+item.getMenuInfo());

        int itemId = item.getItemId();
        if (itemId == R.id.menu_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        } else if (itemId == R.id.menu_calc_on_the_go) {
            changeAutocalculationMode();
        } else if (itemId == R.id.menu_second_field) {
            showSecondField();
        } else if (itemId == R.id.menu_btn_dimens) {
            changeButtonsSize();
        } else if (itemId == R.id.menu_clear_history) {
            showClearHistoryAlertDialog();
        } else if (itemId == R.id.menu_help) {
            Intent intent1 = new Intent(this, HelpActivity.class);
            startActivity(intent1);
        } else if (itemId == R.id.theme_menu) {
            String[] elems = Stream.of(themeManager.getThemes()).map(e -> e.getValue().getDisplayedName()).toArray(String[]::new);
            SelectDialog.show(this, null, elems, pos -> {
                theme = Stream.of(themeManager.getThemes()).skip(pos).findFirst().get().getKey();
                restartActivity();
            });
        }

        return super.onOptionsItemSelected(item);
    }


    private void restartActivity() { 
        Intent i = new Intent( this , this.getClass() );
        this.finish();
        this.startActivity(i);
    }



//  Navigation View
    /*@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/


    private void changeAutocalculationMode() { 
        enableAutocalculation = !enableAutocalculation;
    }



    private EditText lastEditText = null;
    private EditText getCurrentEditText() {
        var view = getCurrentFocus();
        if (view instanceof EditText et) lastEditText = et;
        if (lastEditText == null) lastEditText = getExpressionEditText1();
        //lastEditText.requestFocus()
        return lastEditText;
    }

    
    
    // добавление символов в edit text
    void addSymbols(String symbols, int offset){
        var editText = getCurrentEditText();
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        StringBuilder stringBuilder = new StringBuilder(editText.getText().toString());
        stringBuilder.delete(start, end);
        stringBuilder.insert(start, symbols);
        editText.setText(stringBuilder);
        editText.setSelection(start + offset);
    }
    private void deleteSymbols(EditText editText){
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(editText.getText());
        if (start==end)
            if(start>0) {stringBuilder.delete(start-1, end); start--; }
            else return;
        else stringBuilder.delete(start, end);
        editText.setText(stringBuilder);
        editText.setSelection(start);
    }

    // TODO: 20.08.2018 Delete Not Only One Symbol
    /*public void deleteSymbols(int quantity, EditText editText){
        int index = editText.getSelectionStart();
        StringBuilder stringBuilder= new StringBuilder(editText.getText());
        if(quantity>index) quantity = index;
        stringBuilder.delete(index-quantity, index);
        editText.setText(stringBuilder);
        editText.setSelection(index-quantity);
    }*/

    private void add_0() { addSymbols("0", 1); }
    private void add_three_zeros() { addSymbols("000", 3); }
    private void add_point() { addSymbols(".", 1); }
    private void add_1() { addSymbols("1", 1); }
    private void add_2() { addSymbols("2", 1); }
    private void add_3() { addSymbols("3", 1); }
    private void add_4() { addSymbols("4", 1); }
    private void add_5() { addSymbols("5", 1); }
    private void add_6() { addSymbols("6", 1); }
    private void add_7() { addSymbols("7", 1); }
    private void add_8() { addSymbols("8", 1); }
    private void add_9() { addSymbols("9", 1); }
    private void add_E() { addSymbols("E", 1); }

    private void add_pi() { addSymbols("π", 1); }
    private void add_e() { addSymbols("e", 1); }
    private void add_infinity() { addSymbols("∞", 1); }

    private void add_left_parenthesis() { addSymbols("(", 1); }
    private void add_right_parenthesis() { addSymbols(")", 1); }
    private void add_parentheses() { addSymbols("()", 1); }
    private void add_left_square_bracket() { addSymbols("[", 1); }
    private void add_right_square_bracket() { addSymbols("]", 1); }
    private void add_square_brackets() { addSymbols("[]", 1); }

    private void add_plus() { addSymbols("+", 1); }
    private void add_minus() { addSymbols("−", 1); }
    private void add_mult() { addSymbols("×", 1); }
    private void add_div() { addSymbols("/", 1); }
    private void add_pow() { addSymbols("^", 1); }
    private void add_square() { addSymbols("²", 1); }
    private void add_cube() { addSymbols("³", 1); }
    private void add_quad() { addSymbols("⁴", 1); }
    private void add_sqrt() { addSymbols("√", 1); }
    private void add_cbrt() { addSymbols(getString(R.string.cubeRoot), getString(R.string.cubeRoot).length()); }
    private void add_quad_root() { addSymbols(getString(R.string.quadRoot), getString(R.string.quadRoot).length()); }

    private void add_abs() { addSymbols("abs()", 4); }
    private void add_inv() { addSymbols("inv", 3); }
    private void add_sgn() { addSymbols("sgn()", 4); }

    private void add_sin() { addSymbols("sin", 3); }
    private void add_cos() { addSymbols("cos", 3); }
    private void add_tg() { addSymbols("tg", 2); }
    private void add_ctg() { addSymbols("ctg", 3); }
    private void add_arcsin() { addSymbols("arcsin", 6); }
    private void add_arccos() { addSymbols("arccos", 6); }
    private void add_arctg() { addSymbols("arctg", 5); }
    private void add_arcctg() { addSymbols("arcctg", 6); }

    private void add_sh() { addSymbols("sh", 2); }
    private void add_ch() { addSymbols("ch", 2); }
    private void add_th() { addSymbols("th", 2); }
    private void add_cth() { addSymbols("cth", 3); }
    private void add_arsh() { addSymbols("arsh", 4); }
    private void add_arch() { addSymbols("arch", 4); }
    private void add_arth() { addSymbols("arth", 4); }
    private void add_arcth() { addSymbols("arcth", 5); }

    private void add_log() { addSymbols("log[][]", 4); }
    private void add_ln() { addSymbols("ln", 2); }
    private void add_lg() { addSymbols("lg", 2); }
    private void add_lb() { addSymbols("lb", 2); }

    private void add_factorial() { addSymbols("!", 1); }
    private void add_angle_degree() { addSymbols("°", 1); }
    private void add_percent() { addSymbols("%", 1); }

    private void add_bin() { addSymbols("bin", 3); }
    private void add_oct() { addSymbols("oct", 3); }
    private void add_dec() { addSymbols("dec", 3); }
    private void add_hex() { addSymbols("hex", 3); }
    private void add_A() { addSymbols("A", 1); }
    private void add_B() { addSymbols("B", 1); }
    private void add_C() { addSymbols("C", 1); }
    private void add_D() { addSymbols("D", 1); }
    private void add_F() { addSymbols("F", 1); }

    private void coverWithBracketsBeforeCursor() { 
        var editText = getCurrentEditText();
        int start = editText.getSelectionStart();
        if(start == 0 || start != editText.getSelectionEnd()) return;

        StringBuilder stringBuilder = new StringBuilder(editText.getText().toString());
        stringBuilder.insert(0, "(");
        stringBuilder.insert(start+1, ")");
        editText.setText(stringBuilder);
        editText.setSelection(start + 2);
    }



    private void cursorToLeft() { 
        var editText = getCurrentEditText();
        int index = editText.getSelectionStart();
        if (index != 0) editText.setSelection(index - 1);
    }
    private void cursorToRight() { 
        var editText = getCurrentEditText();
        int index = editText.getSelectionStart();
        if (index!= editText.length()) editText.setSelection(index + 1);
    }
    private void cursorToStart() { 
        var editText = getCurrentEditText();
        editText.setSelection(0);
    }
    private void cursorToEnd() { 
        var editText = getCurrentEditText();
        editText.setSelection(editText.length());
    }

    private void deleteSymbolFrom1() { deleteSymbols(getExpressionEditText1()); }
    private void deleteSymbolFrom2() { deleteSymbols(getExpressionEditText2()); }

    private void clear() { 
        var editText = getCurrentEditText();
        editText.setText("");
    }
    private void clearAll() { 
        getExpressionEditText1().setText("");
        getExpressionEditText2().setText("");
        getResultTextView1().setText("");
        getResultTextView2().setText("");
    }

    private void copyExpression() { 
        var editText = getCurrentEditText();
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start==end){
            try {
                clipboard.add(editText.getText().toString());
                Toast.makeText(getApplicationContext(), getString(R.string.expression_was_copied), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {/*e.printStackTrace();*/}
        }
        else {
            try {
                clipboard.add(editText.getText().toString().substring(start, end));
                Toast.makeText(getApplicationContext(), getString(R.string.alloted_was_copied), Toast.LENGTH_SHORT).show();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    private void pasteExpression() { 
        try {
            String string = clipboard.getLast();
            addSymbols(string, string.length());
        } catch (Exception e) {e.printStackTrace(); }
    }
















    public void calculateResultByButtonFrom1() {
        calculateResultPreEntrance(getExpressionEditText1(), getResultTextView1(), getInfoTextView1(), true);
    }
    public void calculateResultByButtonFrom2() {
        calculateResultPreEntrance(getExpressionEditText2(), getResultTextView2(), getInfoTextView2(), true);
    }

    void calculateResultPreEntrance (EditText editText, TextView resultTextView, TextView infoTextView, boolean doSaveToHistory) {
        String expression = editText.getText().toString();
        Expression expr = new Expression(expression, angleUnit, radix, enableConversionToast);
        String result = "";
        try {
            result = expr.calculateToDec();
                        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
        } catch (EmptyExpressionException e) {/*e.printStackTrace();*/
            return;
        } catch (UnknownFunctionException e) {/*e.printStackTrace();*/
            infoTextView.setText(getString(R.string.incorrect_symbols));return;
        } catch (WrongBracketsException e) {/*e.printStackTrace();*/
            infoTextView.setText(getString(R.string.wrong_brackets));return;
        } catch (IncorrectExpressionException e) {/*e.printStackTrace();*/
            infoTextView.setText(getString(R.string.incorrect_expression));return;
        } catch (Exception e) {/*e.printStackTrace();*/
            Toast.makeText(this, R.string.went_wrong, Toast.LENGTH_SHORT).show();return;
        } finally {
            if (enableConversionToast && expr.convertedExpression!=null)
                Toast.makeText(this, expr.convertedExpression, Toast.LENGTH_LONG).show();
        }

        try {

            if(enableRounding) result = roundUp(result, roundingPrecision);
                        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
            result = toPlainString(result);
            result = stripTrailingZeros(result);
            result = redesignateInfinities(result);
                        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
            if (radix!=10) result = decToOther(result, radix);
            else result = exponentFormat(result, numberFormat);
                        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
            result = divideDigits(result, digitSeparator, radix);
                        /*Toast.makeText(this, result, Toast.LENGTH_SHORT).show();*/
        } catch (Exception e) {/*e.printStackTrace();*/
            Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

        int id = resultTextView.getId();
        if (id == R.id.result_text_view_1) {
            viewModel.updateResultTV1TextColor(true);
        } else if (id == R.id.result_text_view_2) {
            viewModel.updateResultTV2TextColor(true);
        }
        resultTextView.setText(result);

        if (doSaveToHistory) {
            try {
                saveToHistory(expression, result);
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, getString(R.string.error_history), Toast.LENGTH_SHORT).show();
            }
        }

    }






    void showResultAlertDialog(String[] data) {
        final View resultLayout = getLayoutInflater().inflate(R.layout.alert_dialog_result, null);

        AlertDialog.Builder resultDialog = new AlertDialog.Builder(Calculator.this);
        resultDialog
            .setView(resultLayout)
            .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });


        View.OnClickListener clickListener = new View.OnClickListener() { 
            @Override
            public void onClick(View view) {
                int id = view.getId();
                if (id == R.id.result0) {
                    clipboard.add((String) ((TextView) resultLayout.findViewById(R.id.result0)).getText());
                    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                } else if (id == R.id.result1) {
                    clipboard.add((String) ((TextView) resultLayout.findViewById(R.id.result1)).getText());
                    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                } else if (id == R.id.result2) {
                    clipboard.add((String) ((TextView) resultLayout.findViewById(R.id.result2)).getText());
                    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                } else if (id == R.id.result3) {
                    clipboard.add((String) ((TextView) resultLayout.findViewById(R.id.result3)).getText());
                    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                } else if (id == R.id.result4) {
                    clipboard.add((String) ((TextView) resultLayout.findViewById(R.id.result4)).getText());
                    Toast.makeText(getApplicationContext(), R.string.copied, Toast.LENGTH_SHORT).show();
                }
            }
        };


        resultLayout.findViewById(R.id.result0).setOnClickListener(clickListener);
        ((TextView)resultLayout.findViewById(R.id.radix0)).setText(data[0]);
        ((TextView)resultLayout.findViewById(R.id.result0)).setText(data[1]);


        resultLayout.findViewById(R.id.result1).setOnClickListener(clickListener);
        ((TextView)resultLayout.findViewById(R.id.radix1)).setText(data[2]);
        ((TextView)resultLayout.findViewById(R.id.result1)).setText(data[3]);


        resultLayout.findViewById(R.id.result2).setOnClickListener(clickListener);
        ((TextView)resultLayout.findViewById(R.id.radix2)).setText(data[4]);
        ((TextView)resultLayout.findViewById(R.id.result2)).setText(data[5]);


        resultLayout.findViewById(R.id.result3).setOnClickListener(clickListener);
        ((TextView)resultLayout.findViewById(R.id.radix3)).setText(data[6]);
        ((TextView)resultLayout.findViewById(R.id.result3)).setText(data[7]);


        resultLayout.findViewById(R.id.result4).setOnClickListener(clickListener);
        ((TextView)resultLayout.findViewById(R.id.radix4)).setText(data[8]);
        ((TextView)resultLayout.findViewById(R.id.result4)).setText(data[9]);


        resultDialog.create().show();
    }



    private void showClearHistoryAlertDialog() { 
        ConfirmDialog.show(this, getString(R.string.clear_history)+"?", this::clearHistory);
    }


    public void changeAngleUnit() { 
        angleUnit^=1;
        viewModel.updateAngleUnit(angleUnit);
        autocalculateIfNecessary();
    }



    public void changeRadix() {
        if (radix == 2) radix = 8;
        else if (radix == 8) radix = 10;
        else if (radix == 10) radix = 16;
        else if (radix == 16) radix = 2;
        else radix = 10;
        viewModel.updateRadix(radix);
        autocalculateIfNecessary();
    }
    public void setRadix () { 
        try {
            int radixx = Integer.parseInt(getCurrentEditText().getText().toString());
            if (radixx < 0 || radixx > 16) { throw new NumberFormatException(); }

            radix = radixx;
            viewModel.updateRadix(radix);
            toastShort("radix = " + radix);

            autocalculateIfNecessary();
        } catch (Exception e) {
            toastShort(getString(R.string.incorrect_expression));
        }
    }


    void autocalculateIfNecessary() { 
        if (enableAutocalculation) {
            getInfoTextView1().setText(""); getInfoTextView2().setText("");
            calculateResultPreEntrance(getExpressionEditText1(), getResultTextView1(), getInfoTextView1(), !enableSavingByEquals);
            calculateResultPreEntrance(getExpressionEditText2(), getResultTextView2(), getInfoTextView2(), !enableSavingByEquals);
        }
    }




    public void toClipboardFromResultTextView1() { 
            clipboard.add(getResultTextView1().getText().toString());
            Toast.makeText(getApplicationContext(), getString(R.string.result_was_copied), Toast.LENGTH_SHORT).show();
    }
    public void toClipboardFromResultTextView2() { 
            clipboard.add(getResultTextView2().getText().toString());
            Toast.makeText(getApplicationContext(), getString(R.string.result_was_copied), Toast.LENGTH_SHORT).show();
    }


    


    void saveToHistory(String expression, String result) {
        historyManager.add(new HistoryEntry(expression, result));
        //boolean saved = historyManager.add(new HistoryEntry(expression, result));
        //if (saved) adapter.notifyDataSetChanged();
    }

    private void clearHistory() { 
        historyManager.clear();
        //adapter.notifyDataSetChanged();
    }






    void stylizeExpression(EditText editText) {
        Spannable expression = new SpannableString(editText.getText().toString());

        ArrayList<Integer> bracketsStack = new ArrayList<>();

        for (int i = 0; i < expression.length(); i++) {
            switch (expression.charAt(i)){
                case '[': case '(':
                    bracketsStack.add(i+1);
                    expression.setSpan(new StyleSpan(Typeface.BOLD), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//сделать текст жирным
                    break;
                case ']': case ')':
                    bracketsStack.add(-(i+1));
                    expression.setSpan(new StyleSpan(Typeface.BOLD), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
            }
        }
        bracketsStack.add(-expression.length()); //мнимая закрывающая скобка в конце выражения

        /*List<Integer> colors = (editText == getExpressionEditText1()) ? bracketColors1 : bracketColors2;*/
        List<Integer> colors = bracketColors1;

        int level = -1; int startIndex = 0;

        for (int i = 0; i < bracketsStack.size(); i++) {
            if (bracketsStack.get(i)>0){
                if (level>=0) if (level<colors.size())
                    expression.setSpan(new ForegroundColorSpan(colors.get(level)), startIndex, bracketsStack.get(i)-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                else expression.setSpan(new ForegroundColorSpan(colors.get(colors.size()-1)), startIndex, bracketsStack.get(i)-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex=bracketsStack.get(i)-1;
                level++;
            } else{
                if (level>=0) if (level<colors.size())
                    expression.setSpan(new ForegroundColorSpan(colors.get(level)), startIndex, -bracketsStack.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                else expression.setSpan(new ForegroundColorSpan(colors.get(colors.size()-1)), startIndex, -bracketsStack.get(i), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                startIndex= -bracketsStack.get(i);
                level--;
            }
        }
        /*if (level>=0) if (level<colors.size())
            expression.setSpan(new ForegroundColorSpan(colors.get(level)), startIndex, expression.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        else expression.setSpan(new ForegroundColorSpan(colors.get(colors.size()-1)), startIndex, expression.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);*/

        editText.setTextKeepState(expression);
    }

    static class CoveringWithBrackets {
        int selection = -1;
        int origSelection = -1;
        String origText = "";
        String currentText = "";
        int currentLevelCount = 0;

        void coverWithBrackets(EditText editText) {
            int selStart = editText.getSelectionStart();
            if (selStart != editText.getSelectionEnd()) return;

            StringBuilder sb = new StringBuilder(editText.getText().toString());

            int levelUp = 0;

            if (selection == selStart && sb.toString().equals(currentText)) {
                levelUp = currentLevelCount;
                sb = new StringBuilder(origText);
                selStart = origSelection;
                currentText="";
            }
            else {
                currentLevelCount =0;
                origText=sb.toString();
                currentText="";
                origSelection=selStart;
            }

            GetBrackets expr = new GetBrackets(sb.toString(), radix);

            try {
                List<GetBrackets.Bracket> list = expr.getBrackets();

                /*{
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("----------\n");
                    for (int i = 0; i < list.size(); i++) {
                        sb2.append(list.get(i).toString()+"\n");
                    }
                    sb2.append("----------\n");
                    Log.w("coverWithBrackets", sb2.toString());
                }*/


                for (int i = list.size()-1; i >=0 ; i--) {
                    if(list.get(i).pair==null || list.get(i).isReal) list.remove(i);
                }



                /*{
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("----------\n");
                    for (int i = 0; i < list.size(); i++) {
                        sb2.append(list.get(i).toString()+"\n");
                    }
                    sb2.append("----------\n");
                    Log.w("coverWithBrackets", sb2.toString());
                }*/


                for (int i = 0; i < list.size()-1; i++) {
                    if (list.get(i).isOpen && list.get(i+1).isOpen
                            && list.get(i).pos == list.get(i+1).pos
                            /*&& list.get(i).pair!=null && list.get(i+1).pair!=null*/
                            && list.get(i).pair.pos == list.get(i+1).pair.pos){
                        list.remove(list.get(i + 1).pair);
                        list.remove(i+1);
                    }
                }



                int start = -1;
                int end = list.size();
                int level = Integer.MIN_VALUE;

                for (int i = start + 1; i < end; i++) {
                    if (list.get(i).isOpen && list.get(i).depth > level
                            /*&& list.get(i).pair!=null*/
                            && selStart >= list.get(i).pos && selStart <= list.get(i).pair.pos) {

                        start = i;
                        end = list.indexOf(list.get(start).pair);
                        level = list.get(i).depth;
                    } else i = list.indexOf(list.get(i).pair);
                }


                while (levelUp > 0 && start > 0) {
                    for (int i = start - 1; i >= 0; i--) {
                        if (list.get(i).isOpen && list.get(i).depth < level
                                /*&& list.get(i).pair!=null*/
                                && list.indexOf(list.get(i).pair)>end) {

                            start = i;
                            level = list.get(i).depth;
                            levelUp--;
                            end = list.indexOf(list.get(i).pair);
                            break;
                        }
                        /*if (i==0) break loop;*/
                    }
                }


                if (levelUp > 0 && list.get(start).depth <= 0) {
                    editText.setText(sb.toString());
                    editText.setSelection(origSelection);
                    currentLevelCount = 0;
                    selection = origSelection;
                    currentText = sb.toString();
                    return;
                }

                sb.insert(list.get(end).pos, ")");
                sb.insert(list.get(start).pos, "(");
                editText.setText(sb.toString());
                editText.setSelection(list.get(end).pos + 1);

                currentLevelCount++;
                selection=list.get(end).pos+1;
                currentText=sb.toString();
            } catch (Exception e) {/*e.printStackTrace();*/}
        }

    }




    static class ChangingSing {

        void changeSign(EditText editText) {
            int selStart = editText.getSelectionStart();
            if (selStart != editText.getSelectionEnd()) return;

            StringBuilder sb = new StringBuilder(editText.getText().toString());

            GetBrackets expr = new GetBrackets(sb.toString(), radix);

            try {
                List<GetBrackets.Bracket> list = expr.getBrackets();

                /*{
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("----------\n");
                    for (int i = 0; i < list.size(); i++) {
                        sb2.append(list.get(i).toString()+"\n");
                    }
                    sb2.append("----------\n");
                    Log.w("coverWithBrackets", sb2.toString());
                }*/



                int start = -1;
                int end = list.size();
                int level = Integer.MIN_VALUE;

                /*Log.w("coverWithBrackets", "1");*/

                for (int i = start + 1; i < end; i++) {
                    if (list.get(i).isOpen && list.get(i).depth > level
                            && list.get(i).pair!=null
                            && selStart >= list.get(i).pos && selStart <= list.get(i).pair.pos) {
                        start = i;
                        end = list.indexOf(list.get(i).pair);
                        level = list.get(i).depth;

                    } else if (list.get(i).pair!=null) i = list.indexOf(list.get(i).pair);
                }



                if (list.get(start).pos>0 && sb.charAt(list.get(start).pos-1) == '+') {
                    sb.replace(list.get(start).pos-1, list.get(start).pos, "−");
                    editText.setText(sb.toString());
                    editText.setSelection(selStart);
                    return;
                }



                if (list.get(start).pos > 0 && (sb.charAt(list.get(start).pos - 1) == '−' || sb.charAt(list.get(start).pos - 1) == '-')) {

                    if (list.get(start - 1).isOpen
                            && (
                            (list.get(start - 1).isReal && list.get(start - 1).pos + 2 == list.get(start).pos)
                                    || (!list.get(start - 1).isReal && list.get(start - 1).pos + 1 == list.get(start).pos)
                    )
                            ) {
                        sb.replace(list.get(start).pos - 1,list.get(start).pos, "");
                        editText.setText(sb.toString());
                        editText.setSelection(selStart - 1);
                        return;
                    }

                    sb.replace(list.get(start).pos - 1,list.get(start).pos, "+");
                    editText.setText(sb.toString());
                    editText.setSelection(selStart);
                    return;
                }


                {
                    /*{
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("----------start="+start+"\n");
                        for (int i = 0; i < list.size(); i++) {
                            sb2.append(list.get(i).toString()+"\n");
                        }
                        sb2.append("----------\n");
                        Log.w("coverWithBrackets", sb2.toString());
                    }*/

                    if (list.get(start - 1).isOpen
                            && (
                                    (list.get(start - 1).isReal && list.get(start - 1).pos + 1 == list.get(start).pos)
                                    || (!list.get(start - 1).isReal && list.get(start - 1).pos == list.get(start).pos)
                               )
                            ) {
                            sb.insert(list.get(start - 1).isReal ? list.get(start - 1).pos + 1 : list.get(start - 1).pos, "−");
                            editText.setText(sb.toString());
                            editText.setSelection(selStart + 1);
                            return;
                    }




                    sb.insert(list.get(start).pair.pos, ")");
                    sb.insert(list.get(start).pos, "(−");
                    editText.setText(sb.toString());
                    editText.setSelection(selStart + 2);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }



    // TODO: 20.08.2018 digit separation
    /*SpannableStringBuilder digitSeparation(SpannableStringBuilder str){
        int start=-1;
        int end=-1;
        for (int i = str.length()-1; i >= 0; i--) {
            if (start==-1)
                if (str.subSequence(i, i+1).toString().matches("[\\dABCDEF]")) start=i;
        }
        return str;
    }*/




    private void showSecondField() { 
        enableSecondField = !enableSecondField;
        updateVisibilityOfSecondFields();
    }
    void updateVisibilityOfSecondFields() { 
        getInfoTextView2()                       .setVisibility(enableSecondField ? View.VISIBLE : View.GONE);
        getExpressionEditText2()                 .setVisibility(enableSecondField ? View.VISIBLE : View.GONE);
        getResultTextView2()                     .setVisibility(enableSecondField ? View.VISIBLE : View.GONE);
        findViewById(R.id.btn_delete_from_2).setVisibility(enableSecondField ? View.VISIBLE : View.GONE);
        findViewById(R.id.btn_equals_from_2).setVisibility(enableSecondField ? View.VISIBLE : View.GONE);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            findViewById(R.id.landSecondFieldsSeparator).setVisibility(enableSecondField ? View.VISIBLE : View.GONE);

    }






    @NonNull
    @Contract(" -> new")
    private int[] extractWidthHeight() throws Exception{
        var editText = getCurrentEditText();
        String[] dimens = editText.getText().toString().split("[\\*×]");
        if (dimens.length == 1) dimens = new String[]{ dimens[0], "" };
        return new int[] {
                dimens[0].isEmpty() ? -1 : Integer.parseInt(dimens[0]), //-1 если параметр не введён
                dimens[1].isEmpty() ? -1 : Integer.parseInt(dimens[1])
        };
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(@NonNull View v, float w){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.width = (int)w;
        v.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setLayoutHeight(@NonNull View v, float h){
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        layoutParams.height = (int)h;
        v.setLayoutParams(layoutParams);
    }

    /*private void changeTopRowDimensPortrait(int[] dimens, boolean doSave){
        if (dimens[0]>=0) binding.setTopRowWidth(dimens[0]);
        if (dimens[1]>=0) binding.setTopRowHeight(dimens[1]);

        if(dimens[1]>=0) {
            binding.setTextSizeTop1(dimens[1] * 0.28f);
            binding.setTextSizeTop2(dimens[1] * 0.45f);
            binding.setTextSizeTop3(dimens[1] * 0.2f);
        }

        if (doSave){
            SharedPreferences.Editor editor = settings.getEditor();
            if (dimens[1]>=0) settings.setTopRowHeightPortrait(editor, dimens[1]);
            if (dimens[0]>=0) settings.setTopRowWidthPortrait(editor, dimens[0]);
            editor.apply();
        }
    }*/

    /*private void changeTopRowDimensPortrait(int[] dimens, boolean doSave){
        GridLayout grid = (GridLayout) findViewById(R.id.btn_container1);
        int rowSize = grid.getColumnCount();

        ViewGroup.LayoutParams params;
        for (int i = 0; i < grid.getChildCount(); i++) {
            params = grid.getChildAt(i).getLayoutParams();

            if (dimens[1]>=0 && i%rowSize == 0) params.height = dimens[1];
            if (dimens[0]>=0)                   params.width = dimens[0];

            grid.getChildAt(i).setLayoutParams(params);
        }

        if(dimens[1]>=0) {
            binding.setTextSizeTop1(dimens[1] * 0.28f);
            binding.setTextSizeTop2(dimens[1] * 0.45f);
            binding.setTextSizeTop3(dimens[1] * 0.2f);
        }

        if (doSave){
            SharedPreferences.Editor editor = settings.getEditor();
            if (dimens[1]>=0) settings.setTopRowHeightPortrait(editor, dimens[1]);
            if (dimens[0]>=0) settings.setTopRowWidthPortrait(editor, dimens[0]);
            editor.apply();
        }
    }*/
    /*private void doDefaultTopRowDimensPortrait() { 
        changeTopRowDimensPortrait(
                new int[]{getResources().getDimensionPixelSize(R.dimen.number_buttons_side),
                getResources().getDimensionPixelSize(R.dimen.number_buttons0_side)}, true);
    }*/

    /*private void changeMainRowsDimensPortrait(int[] dimens, boolean doSave){

        if (dimens[0]>=0) binding.setMainRowWidth(dimens[0]);
        if (dimens[1]>=0) binding.setMainRowHeight(dimens[1]);

        if(dimens[1]>=0) {
            binding.setTextSize1(dimens[1] * 0.45f);
            binding.setTextSize2(dimens[1] * 0.35f);
            binding.setTextSize3(dimens[1] * 0.25f);
        }

        *//*float mult = ((double)dimens[0]/dimens[1] > 2d) ? 2f : (float) ((double)dimens[0]/dimens[1]);
        *//**//*((Button)findViewById(R.id.BtnRow2Pos9)).setTextSize(TypedValue.COMPLEX_UNIT_PX, dimens[1]*0.4f*mult);*//**//*
        TypedValue tv = new TypedValue();
        tv.type = TypedValue.COMPLEX_UNIT_PX;
        tv.data = (int)(dimens[1]*0.4f*mult);
        getTheme().resolveAttribute(R.attr.btnMainTextSize, tv, true);*//*

        if (doSave){
            SharedPreferences.Editor editor = settings.getEditor();
            if (dimens[1]>=0) settings.setMainRowsHeightPortrait(editor, dimens[1]);
            if (dimens[0]>=0) settings.setMainRowsWidthPortrait(editor, dimens[0]);
            editor.apply();
        }
    }
    private void doDefaultMainRowsDimensPortrait() { 
        changeMainRowsDimensPortrait(
                new int[]{getResources().getDimensionPixelSize(R.dimen.number_buttons_side),
                        getResources().getDimensionPixelSize(R.dimen.number_buttons_side)}, true);
    }*/


    private void changeButtonsSize() { 
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            ButtonsSizePortDialog dialog = new ButtonsSizePortDialog(this);
            dialog.show(getSupportFragmentManager(), "");
        } else {
            ButtonsSizeLandDialog dialog = new ButtonsSizeLandDialog(this);
            dialog.show(getSupportFragmentManager(), "");
        }
    }




    public static class UndoManager {
        private final List<Entry> stack = new LinkedList<>();
        int targetSize;
        private int position = -1;
        boolean alreadyChanged = false;
        boolean readyToGetSelection = false;

        public UndoManager() { }

        public UndoManager(int targetSize) {
            this.targetSize = targetSize;
        }

        private void add(EditText editText) {
            if(alreadyChanged) return;

            readyToGetSelection = true;
            String text = editText.getText().toString();
            /*if(position>=0 && position<stack.size() && text.equals(stack.get(position).text)) return;*/
            for (int i = stack.size()-2; i >= position; i--) stack.add(stack.get(i));
            stack.add(new Entry(text, text.length()));
            while (stack.size()> targetSize) stack.remove(0);
            position = stack.size()-1;

            /*Log.w("undoManager1", "add: text="+text);*/
        }

        private void undo(EditText editText) {
            if (!stack.isEmpty() && position > 0) {
                alreadyChanged = true;
                position--;
                try {
                    editText.setText(stack.get(position).text);
                    editText.setSelection(stack.get(position).pos);
                } catch (Exception e) {
                    /*e.printStackTrace();*/
                }
                alreadyChanged = false;
            }
        }

        private void redo(EditText editText){
            if (position < stack.size() - 1){
                alreadyChanged = true;
                position++;
                try {
                    editText.setText(stack.get(position).text);
                    editText.setSelection(stack.get(position).pos);
                } catch (Exception e) {
                    /*e.printStackTrace();*/
                }
                alreadyChanged = false;
            }
        }


        void addSelectionToLastEntry(int pos) {
            if (readyToGetSelection) {
                stack.get(stack.size() - 1).pos = pos;
                /*Log.w("undoManager1", "addSel: text="+stack.get(stack.size()-1).text + " pos=" + stack.get(stack.size()-1).pos);*/
            }
            readyToGetSelection = false;
        }


        Entry getLast() { 
            if (!stack.isEmpty() && position >= 0) {
                return stack.get(position);
            }else return null;
        }


        public static class Entry{
            String text;
            int pos;

            public Entry(String text) {
                this.text = text;
            }

            public Entry(String text, int pos) {
                this.text = text;
                this.pos = pos;
            }
        }
    }






    @Override
    public void onClick(@NonNull View v) {

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
        v.startAnimation(animAlpha);

        var editText = getCurrentEditText();

        int id = v.getId();
        if (id == R.id.btn_cursor_to_start) {
            cursorToStart();
        } else if (id == R.id.btn_cursor_to_left) {
            cursorToLeft();
        } else if (id == R.id.btn_cursor_to_right) {
            cursorToRight();
        } else if (id == R.id.btn_cursor_to_end) {
            cursorToEnd();
        } else if (id == R.id.btn_clear) {
            clear();
        } else if (id == R.id.btn_copy) {
            copyExpression();
        } else if (id == R.id.btn_paste) {
            pasteExpression();
        } else if (id == R.id.btn_undo) {
            if (getCurrentFocus() == getExpressionEditText1()) {
                undoManager1.undo(getExpressionEditText1());
            } else if (getCurrentFocus() == getExpressionEditText2()) {
                undoManager2.undo(getExpressionEditText2());
            }
        } else if (id == R.id.btn_redo) {
            if (getCurrentFocus() == getExpressionEditText1()) {
                undoManager1.redo(getExpressionEditText1());
            } else if (getCurrentFocus() == getExpressionEditText2()) {
                undoManager2.redo(getExpressionEditText2());
            }
        } else if (id == R.id.btn_kbd) {
            ViewUtil.showHideKbd(this);
        } else if (id == R.id.btn_coverWithBrackets) {
            coveringWithBrackets.coverWithBrackets(editText);
        } else if (id == R.id.btn_changeSign) {
            changingSign.changeSign(editText);
        } else if (id == R.id.btn_A_hex) {
            add_A();
        } else if (id == R.id.btn_left_parenthesis) {
            add_left_parenthesis();
        } else if (id == R.id.btn_right_parenthesis) {
            add_right_parenthesis();
        } else if (id == R.id.btn_parentheses) {
            add_parentheses();
        } else if (id == R.id.btn_power) {
            add_pow();
        } else if (id == R.id.btn_square) {
            add_square();
        } else if (id == R.id.btn_sin) {
            add_sin();
            //case R.id.btn_log: add_log(); break;
            //case R.id.btn_quad: add_quad(); break;
            /*case R.id.BtnRow2Pos9: (); break;*/
        } else if (id == R.id.btn_sh) {
            add_sh();
        } else if (id == R.id.btn_bin) {
            add_bin();
        } else if (id == R.id.btn_B_hex) {
            add_B();
        } else if (id == R.id.btn_1) {
            add_1();
        } else if (id == R.id.btn_2) {
            add_2();
        } else if (id == R.id.btn_3) {
            add_3();
        } else if (id == R.id.btn_plus) {
            add_plus();
            //case R.id.btn_cube: add_cube(); break;
        } else if (id == R.id.btn_cos) {
            add_cos();
        } else if (id == R.id.btn_ln) {
            add_ln();
            //case R.id.btn_quad_root: add_quad_root(); break;
            /*case R.id.BtnRow3Pos9: (); break;*/
        } else if (id == R.id.btn_ch) {
            add_ch();
        } else if (id == R.id.btn_oct) {
            add_oct();
        } else if (id == R.id.btn_C_hex) {
            add_C();
        } else if (id == R.id.btn_4) {
            add_4();
        } else if (id == R.id.btn_5) {
            add_5();
        } else if (id == R.id.btn_6) {
            add_6();
        } else if (id == R.id.btn_minus) {
            add_minus();
        } else if (id == R.id.btn_sqrt) {
            add_sqrt();
        } else if (id == R.id.btn_tg) {
            add_tg();
            /*case R.id.btn_lg: add_lg(); break;*/
        } else if (id == R.id.btn_angle_degree) {
            add_angle_degree();
            /*case R.id.BtnRow4Pos9: (); break;*/
        } else if (id == R.id.btn_th) {
            add_th();
        } else if (id == R.id.btn_dec) {
            add_dec();
        } else if (id == R.id.btn_D_hex) {
            add_D();
        } else if (id == R.id.btn_7) {
            add_7();
        } else if (id == R.id.btn_8) {
            add_8();
        } else if (id == R.id.btn_9) {
            add_9();
        } else if (id == R.id.btn_mult) {
            add_mult();
            //case R.id.btn_cbrt: add_cbrt(); break;
            //case R.id.btn_ctg: add_ctg(); break;
            /*case R.id.btn_lb: add_lb(); break;*/
        } else if (id == R.id.btn_percent) {
            add_percent();
        } else if (id == R.id.btn_inv) {
            add_inv();
            //case R.id.btn_cth: add_cth(); break;
        } else if (id == R.id.btn_hex) {
            add_hex();
        } else if (id == R.id.btn_Exp) {
            add_E();
        } else if (id == R.id.btn_E_hex) {
            add_E();
        } else if (id == R.id.btn_zero) {
            add_0();
        } else if (id == R.id.btn_point) {
            add_point();
        } else if (id == R.id.btn_div) {
            add_div();
        } else if (id == R.id.btn_factorial) {
            add_factorial();
        } else if (id == R.id.btn_pi) {
            add_pi();
        } else if (id == R.id.btn_e) {
            add_e();
            //case R.id.btn_infinity: add_infinity(); break;
        } else if (id == R.id.btn_abs) {
            add_abs();
            //case R.id.btn_signum: add_sgn(); break;
            /*case R.id.btn_set_radix: setRadix(); break;*/
        } else if (id == R.id.btn_F_hex) {
            add_F();
        } else if (id == R.id.btn_angle_unit) {
            changeAngleUnit();
        } else if (id == R.id.btn_radix) {
            changeRadix();
        } else if (id == R.id.result_text_view_1) {
            toClipboardFromResultTextView1();
        } else if (id == R.id.btn_delete_from_1) {
            deleteSymbolFrom1();
        } else if (id == R.id.btn_equals_from_1) {
            calculateResultByButtonFrom1();
        } else if (id == R.id.result_text_view_2) {
            toClipboardFromResultTextView2();
        } else if (id == R.id.btn_delete_from_2) {
            deleteSymbolFrom2();
        } else if (id == R.id.btn_equals_from_2) {
            calculateResultByButtonFrom2();
        } else if (id == R.id.test_btn) {
            test();
        }
    }



    @Override
    public boolean onLongClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.result_text_view_1) {
            try {
                prepareAndShowResultAlertDialog(getResultTextView1().getText().toString());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.incorrect_expression, Toast.LENGTH_SHORT).show();
                /*e.printStackTrace();*/
            }
        } else if (id == R.id.result_text_view_2) {
            try {
                prepareAndShowResultAlertDialog(getResultTextView2().getText().toString());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), R.string.incorrect_expression, Toast.LENGTH_SHORT).show();
                /*e.printStackTrace();*/
            }
        } else if (id == R.id.btn_delete_from_1 || id == R.id.btn_delete_from_2) {
            new Thread(deleteWhenHoldBtn).start();
        } else if (id == R.id.btn_clear) {
            clearAll();
        } else if (id == R.id.btn_left_parenthesis) {
            add_left_square_bracket();
        } else if (id == R.id.btn_right_parenthesis) {
            add_right_square_bracket();
        } else if (id == R.id.btn_parentheses) {
            add_square_brackets();
            //case R.id.btn_square: add_quad(); break;
            //case R.id.btn_sqrt: add_quad_root(); break;
        } else if (id == R.id.btn_zero) {
            add_three_zeros();
        } else if (id == R.id.btn_sin) {
            add_arcsin();
        } else if (id == R.id.btn_cos) {
            add_arccos();
        } else if (id == R.id.btn_tg) {
            add_arctg();
            //case R.id.btn_ctg: add_arcctg(); break;
        } else if (id == R.id.btn_sh) {
            add_arsh();
        } else if (id == R.id.btn_ch) {
            add_arch();
        } else if (id == R.id.btn_th) {
            add_arth();
        } else if (id == R.id.btn_pi) {
            add_infinity();
        } else if (id == R.id.btn_abs) {
            add_sgn();
            //case R.id.btn_cth: add_arcth(); break;
            //case R.id.btn_log: add_lb(); break;
        } else if (id == R.id.btn_ln) {
            add_lg();
        } else if (id == R.id.btn_coverWithBrackets) {
            coverWithBracketsBeforeCursor();
        } else if (id == R.id.btn_radix) {
            setRadix();
        }
        return true;
    }

    void prepareAndShowResultAlertDialog(String number) throws Exception{
        String origNumber = number;
        number = removeBitDividers(number);
        if (radix == 10) number = toPlainString(number);
        number = stripTrailingZeros(number);
        number = toDec(number, radix);
        number = redesignateInfinities(number);

        String[] data = {Integer.toString(radix), origNumber,
                "bin", divideDigits(decToOther(number, 2), digitSeparator, 2),
                "oct", divideDigits(decToOther(number, 8), digitSeparator, 8),
                "dec", divideDigits(decToOther(number, 10), digitSeparator, 10),
                "hex", divideDigits(decToOther(number, 16), digitSeparator, 16)};
        showResultAlertDialog(data);
    }


    // удаление по зажатой кнопке
    Runnable deleteWhenHoldBtn = () -> {
        View v;
        EditText editText;
        if (getCurrentFocus()==getExpressionEditText1()){
            v = findViewById(R.id.btn_delete_from_1);
            editText = getExpressionEditText1();
        } else if (getCurrentFocus()==getExpressionEditText2()){
            v = findViewById(R.id.btn_delete_from_2);
            editText = getExpressionEditText2();
        } else return;

        alreadyChanged1 = true;
        alreadyChanged2 = true;

        try {
            for (int i = 0; i < 5; i++) {
                editText.post(()->deleteSymbols(editText));
                if(!v.isPressed()) return;
                Thread.sleep(150);
            }
            while (true){
                editText.post(()->deleteSymbols(editText));
                if(!v.isPressed()) return;
                Thread.sleep(60);
            }
        }
        catch (Exception e) { }
        finally {
            alreadyChanged1 = false;
            alreadyChanged2 = false;
        }

    };


    private class HistoryRecyclerAdapter extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder> {
        private List<HistoryEntry> entries;

        HistoryRecyclerAdapter(List<HistoryEntry> entries) {
            this.entries = entries;
        }

        public void updateContainer(List<HistoryEntry> list){
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() { return entries.size(); }

                @Override
                public int getNewListSize() { return list.size(); }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return entries.get(oldItemPosition).getId()==list.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    HistoryEntry old = entries.get(oldItemPosition);
                    HistoryEntry neww = list.get(newItemPosition);
                    return  old.getExpression().equals(neww.getExpression())
                            && old.getResult().equals(neww.getResult());
                }
            }, false);//detectMoves=true потом позволит вызвать анимацию если строка просто была перемещена
            entries = list;
            diffResult.dispatchUpdatesTo(this);
        }

        class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            final TextView expr, result;
            final Button delBtn, copyAllBtn, copyResBtn, copyExprBtn;

            ViewHolder(View view){
                super(view);
                expr = (TextView)view.findViewById(R.id.expr);
                result = (TextView) view.findViewById(R.id.result);
                delBtn = (Button) view.findViewById(R.id.delete_btn);
                copyAllBtn = (Button) view.findViewById(R.id.copy_all);
                copyResBtn = (Button) view.findViewById(R.id.copy_r);
                copyExprBtn = (Button) view.findViewById(R.id.copy_e);
            }

            @Override
            public void onClick(View v) {
                int id = v.getId();
                if (id == R.id.expr) {
                    addSymbols(
                            entries.get(this.getAdapterPosition()).getExpression(),
                            entries.get(this.getAdapterPosition()).getExpression().length()
                    );
                } else if (id == R.id.result) {
                    addSymbols(
                            entries.get(this.getAdapterPosition()).getResult(),
                            entries.get(this.getAdapterPosition()).getResult().length()
                    );
                } else if (id == R.id.delete_btn) {
                    historyManager.delete(entries.get(this.getAdapterPosition()));
                } else if (id == R.id.copy_all) {
                    String s = entries.get(this.getAdapterPosition()).getExpression() +
                            " = " +
                            entries.get(this.getAdapterPosition()).getResult();
                    clipboard.add(s);
                    toastShort(getString(R.string.copied) + ": \"" + s + "\"");
                } else if (id == R.id.copy_r) {
                    String s;
                    s = entries.get(this.getAdapterPosition()).getResult();
                    clipboard.add(s);
                    toastShort(getString(R.string.result_was_copied) + ": \"" + s + "\"");
                } else if (id == R.id.copy_e) {
                    String s;
                    s = entries.get(this.getAdapterPosition()).getExpression();
                    clipboard.add(s);
                    toastShort(getString(R.string.expression_was_copied) + ": \"" + s + "\"");
                }
            }
        }

        @NonNull
        @Override
        public HistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_row, parent, false);
            return new HistoryRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(HistoryRecyclerAdapter.ViewHolder holder, final int position) {
            holder.expr.setText(entries.get(position).getExpression());
            holder.expr.setOnClickListener(holder);

            holder.result.setText(entries.get(position).getResult());
            holder.result.setOnClickListener(holder);

            holder.delBtn.setOnClickListener(holder);
            holder.copyAllBtn.setOnClickListener(holder);
            holder.copyResBtn.setOnClickListener(holder);
            holder.copyExprBtn.setOnClickListener(holder);
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

    }





    public static final int HALF_LONG_CLICK_THRESHOLD = 300;
    public static final int MOVE_THRESHOLD = 30;
    private class TouchListener implements View.OnTouchListener {

        //private float startX;
        private Thread t;
        private GestureSelectionView gestureSelectionView;
        private boolean isGestureSelectionViewShowing = false;
        private boolean moved = false;
        private float downX;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, @NonNull MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN: // нажатие
                    //GridLayout grid = findViewById(R.id.grid);
                    //grid.
                    //Log.e("TAG", "onTouch: "+grid.getX()+" "+grid.getColumnCount());
                            /*Log.e("TAG", "onTouch: "+findViewById(R.id.btn_main).getX()
                                    +" "+findViewById(R.id.btn_main).getTranslationX()
                                    +" "+findViewById(R.id.btn_main).getWidth()
                                    +" "+findViewById(R.id.btn_main).getMeasuredWidth()
                                    +" "+findViewById(R.id.btn_main).getMinimumWidth());*/
                    isGestureSelectionViewShowing = false;
                    moved = false;
                    int scrollX0 = ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).getScrollX();
                    float touchX0 = view.getX() - scrollX0 + x;
                    downX = touchX0;

                    t = new Thread(() -> {
                        try { Thread.sleep(HALF_LONG_CLICK_THRESHOLD); } catch (InterruptedException e) { return; }
                        runOnUiThread(() -> {
                            ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).requestDisallowInterceptTouchEvent(true);
                            gestureSelectionView = new GestureSelectionView(Calculator.this);
                            int scrollX = ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).getScrollX();
                            float viewX = view.getX()-scrollX;
                            float touchX = viewX+x;
                            ConstraintLayout constraintLayout = findViewById(R.id.calculator_content);
                            String[] variants;
                            int id = view.getId();
                            if (id == R.id.btn_tg) {
                                variants = new String[]{ "arctg", "ctg", "arcctg" };
                            } else if (id == R.id.btn_ln) {
                                variants = new String[]{ "lg", "log", "lb" };
                            } else if (id == R.id.btn_square) {
                                variants = new String[]{ "3", "4" };
                            } else if (id == R.id.btn_th) {
                                variants = new String[]{ "arth", "cth", "arcth" };
                            } else if (id == R.id.btn_sqrt) {
                                variants = new String[]{ getString(R.string.cubeRoot), getString(R.string.quadRoot) };
                            } else {
                                variants = new String[0];
                            }
                            gestureSelectionView.create(touchX, viewX, constraintLayout.getWidth(), variants);
                            LinearLayout testLayout = gestureSelectionView.getRoot();
                            constraintLayout.addView(testLayout);
                            Log.e("gestureSelectionView", "onTouch: "+gestureSelectionView.getRoot().getWidth());
                            ConstraintSet constraintSet = new ConstraintSet();
                            constraintSet.clone(constraintLayout);
                            constraintSet.connect(R.id.gesture_selection_layout, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT,0);
                            //constraintSet.connect(R.id.gesture_selection_layout, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP,0);
                            //constraintSet.connect(R.id.gesture_selection_layout, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT,0);
                            constraintSet.connect(R.id.gesture_selection_layout, ConstraintSet.BOTTOM, R.id.kbd_scroll_view, ConstraintSet.TOP,10);
                            constraintSet.applyTo(constraintLayout);
                            isGestureSelectionViewShowing = true;
                            //Log.e("TAG", "onTouch: " + (view.getX()+x));
                            //Log.e("TAG", "onTouch: " + testLayout.getX()+" "+testLayout.getWidth());
                            //Log.e("TAG", "onTouch: " + testLayout.getChildAt(0).getX()+" "+testLayout.getChildAt(0).getWidth());
                            //Log.e("TAG", "onTouch: " + testLayout.getChildAt(1).getX()+" "+testLayout.getChildAt(1).getWidth());
                            //Log.e("TAG", "onTouch: " + testLayout.getChildAt(2).getX()+" "+testLayout.getChildAt(2).getWidth());
                            //Log.e("TAG", "onTouch: " + testLayout.getChildAt(3).getX()+" "+testLayout.getChildAt(3).getWidth());
                        });
                    });
                    t.start();
                    break;
                case MotionEvent.ACTION_MOVE: // движение
                    int scrollX = ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).getScrollX();
                    float touchX = view.getX() - scrollX + x;
                    if (Math.abs(touchX - downX) >= MOVE_THRESHOLD) moved = true;
                    if (isGestureSelectionViewShowing){
                        gestureSelectionView.setSelected(touchX);
                    } else if (Math.abs(touchX - downX) >= MOVE_THRESHOLD)
                        ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).requestDisallowInterceptTouchEvent(false);

                    //Log.e("TAG", "onTouch: " + (x));
                    //Log.e("TAG", "view.getX(): " + (view.getX()));
                    //Log.e("TAG", "scrollX: " + (scrollX));
                    //Log.e("TAG", "onTouch: " + (view.getX()-scrollX+x));
                    //Log.e("TAG", "onTouch: " + testLayout.getX()+" "+testLayout.getWidth());
                    //Log.e("TAG", "onTouch: " + testLayout.getChildAt(0).getX()+" "+testLayout.getChildAt(0).getWidth());
                    //Log.e("TAG", "onTouch: " + testLayout.getChildAt(1).getX()+" "+testLayout.getChildAt(1).getWidth());
                    //Log.e("TAG", "onTouch: " + testLayout.getChildAt(2).getX()+" "+testLayout.getChildAt(2).getWidth());
                    //Log.e("TAG", "onTouch: " + testLayout.getChildAt(3).getX()+" "+testLayout.getChildAt(3).getWidth());
                    break;

                case MotionEvent.ACTION_CANCEL: // отмена, например, когда возвращаем скролу способность перехватывать движения
                    moved = true;
                case MotionEvent.ACTION_UP: // отпускание
                    ((HorizontalScrollView)findViewById(R.id.kbd_scroll_view)).requestDisallowInterceptTouchEvent(false);
                    //Log.e("TAG", "onTouch: "+isGestureSelectionViewShowing+" "+moved);
                    if (!isGestureSelectionViewShowing && !moved) onClick(view);
                    isGestureSelectionViewShowing = false;
                    if (t != null) t.interrupt();
                    if (gestureSelectionView != null) {
                        ConstraintLayout root = findViewById(R.id.calculator_content);
                        int id = view.getId();
                        if (id == R.id.btn_tg) {
                            switch (gestureSelectionView.getSelectedIdx()) {
                                case 0:
                                    add_arctg();
                                    break;
                                case 1:
                                    add_ctg();
                                    break;
                                case 2:
                                    add_arcctg();
                                    break;
                            }
                        } else if (id == R.id.btn_ln) {
                            switch (gestureSelectionView.getSelectedIdx()) {
                                case 0:
                                    add_lg();
                                    break;
                                case 1:
                                    add_log();
                                    break;
                                case 2:
                                    add_lb();
                                    break;
                            }
                        } else if (id == R.id.btn_square) {
                            switch (gestureSelectionView.getSelectedIdx()) {
                                case 0:
                                    add_cube();
                                    break;
                                case 1:
                                    add_quad();
                                    break;
                            }
                        } else if (id == R.id.btn_sqrt) {
                            switch (gestureSelectionView.getSelectedIdx()) {
                                case 0:
                                    add_cbrt();
                                    break;
                                case 1:
                                    add_quad_root();
                                    break;
                            }
                        } else if (id == R.id.btn_th) {
                            switch (gestureSelectionView.getSelectedIdx()) {
                                case 0:
                                    add_arth();
                                    break;
                                case 1:
                                    add_cth();
                                    break;
                                case 2:
                                    add_arcth();
                                    break;
                            }
                        }

                        root.removeView(findViewById(R.id.gesture_selection_layout));
                        gestureSelectionView = null;
                    }
                    break;
            }


            return true;
        }
    }








    public void test() { 
        /*Intent i = new Intent(this, TestActivity.class);
        startActivity(i);*/
        //toastShort("test");
    }

}









// TODO: 20.08.2018 Полезный код Spanы
        /*Spannable parentheses = new SpannableString("( )[ ]");
        parentheses.setSpan(new SubscriptSpan(), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//подстрочный
        parentheses.setSpan(new ForegroundColorSpan(bgcBtnTextColor), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//изменить цвет
        parentheses.setSpan(new RelativeSizeSpan(0.5f), 3, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);//изменить размер шрифта  //s.setSpan(new TypefaceSpan(this, "Dolores.otf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); - изменить шрифт
        Button parenthesesBtn = (Button)findViewById(R.id.btn_parentheses);
        parenthesesBtn.setText(parentheses);*/



// TODO: 20.08.2018 Полезный код - Изменить цвет Drawable
        /*GradientDrawable bgc1 = (GradientDrawable)btn4.getBackground();
        Drawable bgc = btn4.getBackground();
        if (bgc instanceof ShapeDrawable) {
            ((ShapeDrawable)bgc).getPaint().setColor(Color.BLACK);
        } else if (bgc instanceof GradientDrawable) {
            ((GradientDrawable)bgc).setColor(Color.BLACK);
        } else if (bgc instanceof ColorDrawable) {
            ((ColorDrawable)bgc).setColor(Color.BLACK);
        }*/




// TODO: 20.08.2018 Полезный код DividerItemDecorator для RecyclerView
/*package com.rrain.calculator4;

*//**
 * Created by Ermac on 27.01.2018.
 *//*

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.drawable.Drawable;
        import android.support.v7.widget.RecyclerView;
        import android.view.View;

public class DividerItemDecorator extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    DividerItemDecorator(Context context) {
        mDivider = context.getResources().getDrawable(R.drawable.separator_1dp_color_accent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}*/

