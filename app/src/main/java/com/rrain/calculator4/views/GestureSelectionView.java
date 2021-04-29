package com.rrain.calculator4.views;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.rrain.calculator4.R;

import lombok.Getter;

public class GestureSelectionView {
    private Activity activity;
    @Getter private LinearLayout root;
    private int onCard;
    private int circleIndicatorColor;
    private int onCircleIndicatorColor;
    @Getter private int selectedIdx = -1;
    private int cnt;
    private float[] posX;

    public GestureSelectionView(Activity activity) {
        this.activity = activity;
    }

    public void create(float touchX, float x, float parentW, String... variants){
        LayoutInflater inflater = activity.getLayoutInflater();
        root = (LinearLayout)inflater.inflate(R.layout.gesture_selection_layout, null);
        root.setVisibility(View.INVISIBLE);

        TypedArray ta = activity.getTheme().obtainStyledAttributes(new int[]{
                R.attr.onCard,
                R.attr.circleIndicatorColor,
                R.attr.onCircleIndicatorColor
        });
        onCard = ta.getColor(0, activity.getResources().getColor(R.color.black));
        circleIndicatorColor = ta.getColor(1, activity.getResources().getColor(R.color.black));
        onCircleIndicatorColor = ta.getColor(2, activity.getResources().getColor(R.color.black));

        cnt = variants.length;

        for (int i = 0; i < cnt; i++) {
            TextView tv = (TextView)inflater.inflate(R.layout.rounded_text_view, null);
            tv.setText(variants[i]);
            root.addView(tv, i);
            unselect(i);
        }

        /*root.getChildAt(cnt-1).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e("TAG", "onLayoutChange: "+left+" "+right+" "+bottom+" "+top);
                posX = new float[cnt];
                for (int i = 0; i < cnt; i++) {
                    posX[i] = root.getX() + root.getChildAt(i).getX();
                }
                v.removeOnLayoutChangeListener(this);
                setSelected(touchX);
            }
        });*/
        /*root.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                posX = new float[cnt];
                for (int i = 0; i < cnt; i++) {
                    posX[i] = root.getX() + root.getChildAt(i).getX();
                }
                root.removeOnAttachStateChangeListener(this);
                setSelected(touchX);
            }

            @Override public void onViewDetachedFromWindow(View v) { }
        });*/

        /*root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                // viewToMeasure is now measured and laid out, and displayed dimensions are known.
                //logComputedViewDimensions(viewToMeasure.getWidth(), viewToMeasure.getHeight());

                float w = root.getWidth();
                float newX = Math.min(parentW-w-100, x);
                ConstraintLayout.LayoutParams layoutParams = ((ConstraintLayout.LayoutParams)root.getLayoutParams());
                layoutParams.leftMargin = (int)newX;
                root.setLayoutParams(layoutParams);

                posX = new float[cnt];
                for (int i = 0; i < cnt; i++) {
                    posX[i] = root.getX() + root.getChildAt(i).getX();
                }
                //Log.e("TAG", "onPreDraw: "+ posX[0]+" "+posX[1]);

                // Remove this listener, as we have now successfully calculated the desired dimensions.
                root.getViewTreeObserver().removeOnPreDrawListener(this);
                setSelected(touchX);

                // Always return true to continue drawing.
                return true;
            }
        });*/
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float w = root.getWidth();
                float newX = Math.min(parentW-w-50, x);
                ConstraintLayout.LayoutParams layoutParams = ((ConstraintLayout.LayoutParams)root.getLayoutParams());
                layoutParams.leftMargin = (int)newX;
                root.setLayoutParams(layoutParams);

                root.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        posX = new float[cnt];
                        for (int i = 0; i < cnt; i++) {
                            posX[i] = root.getX() + root.getChildAt(i).getX();
                        }
                        setSelected(touchX);
                        root.setVisibility(View.VISIBLE);
                        root.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        });
        /*root.getChildAt(cnt-1).getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                posX = new float[cnt];
                for (int i = 0; i < cnt; i++) {
                    posX[i] = root.getX() + root.getChildAt(i).getX();
                }
                setSelected(touchX);
                root.getChildAt(cnt-1).getViewTreeObserver().removeOnDrawListener(this);
            }
        });*/
        //root.getViewTreeObserver().add

        //select(selectedIdx);
        //setSelected(touchX);
    }


    public void setSelected(int idx){
        if (selectedIdx>=0) unselect(selectedIdx);
        selectedIdx = idx;
        select(selectedIdx);
    }
    public void setSelected(float realX){
        //Log.e("TAG", "setSelected: " + realX);

        //Log.e("TAG", "setSelected: " + posX[0]+" "+posX[1]);
        //Log.e("TAG", "setSelected2: "+root.getMeasuredWidth());
        /*if (posX[0]==0f){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setSelected(realX);
            return;
        }*/
        if (posX!=null) for (int i = cnt-1; i >= 0 ; i--) {
            if (realX >= (i!=0?posX[i]:0)){
                setSelected(i);
                break;
            }
        }
        //Log.e("TAG", "root X: "+root.getX());
        //Log.e("TAG", "first X: "+root.getX());
        //Log.e("TAG", "first width: "+root.getChildAt(0).getWidth());
        //Log.e("TAG", "second X: "+root.getChildAt(1).getX());
    }

    public void unselect(int idx){
        TextView tv = (TextView)root.getChildAt(idx);
        tv.setTextColor(onCard);
        ((GradientDrawable)tv.getBackground()).setColor(Color.TRANSPARENT);
    }

    public void select(int idx){
        TextView tv = (TextView)root.getChildAt(idx);
        ((GradientDrawable)tv.getBackground()).setColor(circleIndicatorColor);
        tv.setTextColor(onCircleIndicatorColor);
    }
}
