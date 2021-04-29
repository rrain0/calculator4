package com.rrain.calculator4;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //ViewPager viewPager = findViewById(R.id.view_pager);
        //viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        //tabs.setupWithViewPager(viewPager);
        //FloatingActionButton fab = findViewById(R.id.fab);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/




        BottomSheet bs = new BottomSheet(this);
        bs.show(getSupportFragmentManager(), "bottom sheet fragment");



















        /*LinearLayout ll = findViewById(R.id.bottom_sheet);
        //BottomSheetGestureListener bottomSheetGestureListener = ;
        GestureDetector gdt = new GestureDetector(this, new BottomSheetGestureListener(ll));
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //Log.e("onTouch: ", "onTouch: ");
                gdt.onTouchEvent(motionEvent);
                return true;
            }
        });*/


        /*BottomSheetBehavior<LinearLayout> bottomSheet = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        bottomSheet.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });*/


        //new BottomSheetGestureListener(BottomSheetBehavior.from(bs));



    }
















    public static class BottomSheet extends BottomSheetDialogFragment {
        private Context context;

        public BottomSheet(Context context) {
            this.context = context;
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void setupDialog(@NonNull Dialog dialog, int style) {
            super.setupDialog(dialog, style);

            View contentView = View.inflate(getContext(), R.layout.layout_bottom_sheet_dialog_fragment, null);
            context = contentView.getContext();
            //ButterKnife.bind(this, contentView);
            dialog.setContentView(contentView);
            //tv_title.setText(getString(R.string.app_name)); R.style.AppBottomSheetDialogTheme


            /*DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            int maxHeight = (int) (height*0.44); //custom height of bottom sheet

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = params.getBehavior();
            ((BottomSheetBehavior) behavior).setPeekHeight(maxHeight);  //changed default peek height of bottom sheet

            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState)
                    {
                        String state = "";
                        switch (newState)
                        {
                            case BottomSheetBehavior.STATE_DRAGGING: {
                                //imgBtnClose.setVisibility(View.INVISIBLE);
                                iv_closeDialog.setVisibility(View.GONE);
                                state = "DRAGGING";
                                break;
                            }
                            case BottomSheetBehavior.STATE_SETTLING: {
                                // imgBtnClose.setVisibility(View.INVISIBLE);
                                iv_closeDialog.setVisibility(View.GONE);
                                state = "SETTLING";
                                break;
                            }
                            case BottomSheetBehavior.STATE_EXPANDED: {
                                // imgBtnClose.setVisibility(View.VISIBLE);
                                iv_closeDialog.setVisibility(View.VISIBLE);
                                state = "EXPANDED";
                                break;
                            }
                            case BottomSheetBehavior.STATE_COLLAPSED: {
                                //imgBtnClose.setVisibility(View.INVISIBLE);
                                iv_closeDialog.setVisibility(View.GONE);
                                state = "COLLAPSED";
                                break;
                            }
                            case BottomSheetBehavior.STATE_HIDDEN: {
                                // imgBtnClose.setVisibility(View.INVISIBLE);
                                iv_closeDialog.setVisibility(View.GONE);
                                dismiss();
                                state = "HIDDEN";
                                break;
                            }
                        }
                        Log.i("BottomSheetFrag", "onStateChanged: "+ state);
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    }
                });
            }


            //close dialog
            iv_closeDialog.setOnClickListener(view -> dismiss());*/

        }






        @Override
        public void onResume() {
            super.onResume();
            addGlobalLayoutListener(getView());
        }

        private void addGlobalLayoutListener(final View v){
            v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    setPeekHeight(v.getMeasuredHeight());
                    v.removeOnLayoutChangeListener(this);
                }
            });
        }

        public void setPeekHeight(int peekHeight){
            BottomSheetBehavior behavior = getBottomSheetBehavior();
            if (behavior == null) {
                return;
            }
            behavior.setPeekHeight(peekHeight);
        }

        private BottomSheetBehavior getBottomSheetBehavior(){
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) ((View) getView().getParent()).getLayoutParams();
            CoordinatorLayout.Behavior behavior = layoutParams.getBehavior();
            if (behavior != null && behavior instanceof BottomSheetBehavior) {
                ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
                return (BottomSheetBehavior) behavior;
            }
            return null;
        }

        private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }
            }

            /*
             * Offset increases as this bottom sheet is moving upward.
             * From 0 to 1 the sheet is between collapsed and expanded states
             * and from -1 to 0 it is between hidden and collapsed states.
             * */
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset < 0) {
                    dismiss();
                }
            }
        };
    }















    /*private static final float SWIPE_THRESHOLD_VELOCITY = 200f;
    private class BottomSheetGestureListener extends GestureDetector.SimpleOnGestureListener{
        private LinearLayout ll;
        private BottomSheetBehavior<LinearLayout> bottomSheet;

        public BottomSheetGestureListener(LinearLayout ll) {
            this.ll = ll;
            this.bottomSheet = BottomSheetBehavior.from(ll);
            Log.e("onTouch", "BottomSheetGestureListener: ");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e("onTouch", "onScroll: ");

            if (distanceY > 0){
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)ll.getLayoutParams();
                if (lp.height+distanceY > DisplayManager.getHeight(Calculator.this)*3f/5f){
                    bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                    //lp.height = (int)(DisplayManager.getHeight(Calculator.this)*3f/5f);
                } else {
                    bottomSheet.setState(BottomSheetBehavior.STATE_DRAGGING);
                    lp.height += distanceY;
                }
                ll.setLayoutParams(lp);
            } else if (distanceY < 0){
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)ll.getLayoutParams();
                if (lp.height+distanceY < 0){
                    bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    bottomSheet.setState(BottomSheetBehavior.STATE_DRAGGING);
                    lp.height += distanceY;
                }
                ll.setLayoutParams(lp);
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("onTouch: ", "onFling: ");
            float deltaY = e1.getY() - e2.getY();
            velocityY = Math.abs(velocityY);

            if (velocityY > SWIPE_THRESHOLD_VELOCITY){
                if (deltaY>0){ //открыть лист на полную
                   bottomSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else if (deltaY<0) { //закрыть лист
                    bottomSheet.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            } else {
                if (deltaY>0){
                    //открыть лист на полную
                } else if (deltaY<0) {
                    //закрыть лист
                }
            }

            return false;
        }
    }*/


}