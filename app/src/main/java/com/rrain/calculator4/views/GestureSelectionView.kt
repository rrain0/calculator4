package com.rrain.calculator4.views

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.rrain.calculator4.R
import kotlin.math.min

class GestureSelectionView(private val activity: Activity) {
  private var root: LinearLayout? = null
  fun getRoot() = root
  private var onCard = 0
  private var circleIndicatorColor = 0
  private var onCircleIndicatorColor = 0
  
  private var selectedIdx = -1
  fun getSelectedIdx() = selectedIdx
  private var cnt = 0
  private var posX: FloatArray? = null
  
  fun create(touchX: Float, x: Float, parentW: Float, vararg variants: String?) {
    val inflater = activity.getLayoutInflater()
    root = inflater.inflate(R.layout.gesture_selection_layout, null) as LinearLayout
    root!!.setVisibility(View.INVISIBLE)
    
    val ta = activity.getTheme().obtainStyledAttributes(
      intArrayOf(
        R.attr.onCard,
        R.attr.circleIndicatorColor,
        R.attr.onCircleIndicatorColor
      )
    )
    onCard = ta.getColor(0, activity.resources.getColor(R.color.black))
    circleIndicatorColor = ta.getColor(1, activity.resources.getColor(R.color.black))
    onCircleIndicatorColor = ta.getColor(2, activity.resources.getColor(R.color.black))
    
    cnt = variants.size
    
    for (i in 0..<cnt) {
      val tv = inflater.inflate(R.layout.rounded_text_view, null) as TextView
      tv.setText(variants[i])
      root!!.addView(tv, i)
      unselect(i)
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
    root!!.getViewTreeObserver().addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
      override fun onGlobalLayout() {
        val w = root!!.getWidth().toFloat()
        val newX = min(parentW - w - 50, x)
        val layoutParams = (root!!.getLayoutParams() as ConstraintLayout.LayoutParams)
        layoutParams.leftMargin = newX.toInt()
        root!!.setLayoutParams(layoutParams)
        
        root!!.getViewTreeObserver().removeOnGlobalLayoutListener(this)
        
        root!!.getViewTreeObserver().addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
          override fun onGlobalLayout() {
            posX = FloatArray(cnt)
            for (i in 0..<cnt) {
              posX!![i] = root!!.getX() + root!!.getChildAt(i).getX()
            }
            setSelected(touchX)
            root!!.setVisibility(View.VISIBLE)
            root!!.getViewTreeObserver().removeOnGlobalLayoutListener(this)
          }
        })
      }
    })
    
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
    // root.getViewTreeObserver().add
    
    // select(selectedIdx);
    // setSelected(touchX);
  }
  
  
  fun setSelected(idx: Int) {
    if (selectedIdx >= 0) unselect(selectedIdx)
    selectedIdx = idx
    select(selectedIdx)
  }
  
  fun setSelected(realX: Float) {
    // Log.e("TAG", "setSelected: " + realX);
    
    // Log.e("TAG", "setSelected: " + posX[0]+" "+posX[1]);
    // Log.e("TAG", "setSelected2: "+root.getMeasuredWidth());
    /*if (posX[0]==0f){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setSelected(realX);
            return;
        }*/
    
    if (posX != null) for (i in cnt - 1 downTo 0) {
      if (realX >= (if (i != 0) posX!![i] else 0f)) {
        setSelected(i)
        break
      }
    }
    // Log.e("TAG", "root X: "+root.getX());
    // Log.e("TAG", "first X: "+root.getX());
    // Log.e("TAG", "first width: "+root.getChildAt(0).getWidth());
    // Log.e("TAG", "second X: "+root.getChildAt(1).getX());
  }
  
  fun unselect(idx: Int) {
    val tv = root!!.getChildAt(idx) as TextView
    tv.setTextColor(onCard)
    (tv.getBackground() as GradientDrawable).setColor(Color.TRANSPARENT)
  }
  
  fun select(idx: Int) {
    val tv = root!!.getChildAt(idx) as TextView
    (tv.getBackground() as GradientDrawable).setColor(circleIndicatorColor)
    tv.setTextColor(onCircleIndicatorColor)
  }
}
