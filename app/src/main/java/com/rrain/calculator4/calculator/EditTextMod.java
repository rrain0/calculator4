package com.rrain.calculator4.calculator;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.rrain.calculator4.R;

public class EditTextMod extends AppCompatEditText {

    public EditTextMod(Context context) {
        super(context);
    }

    public EditTextMod(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditTextMod(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * This method is called when the selection has changed, in case any
     * subclasses would like to know.
     *
     * @param selStart The new selection start location.
     * @param selEnd The new selection end location.
     */
    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        /*Log.w("#editText", Integer.toString(selStart));*/
        super.onSelectionChanged(selStart, selEnd);
        if (this == findViewById(R.id.expression_edit_text_1)) {
            Calculator.undoManager1.addSelectionToLastEntry(selStart);
        } else if (this == findViewById(R.id.expression_edit_text_2)) {
            Calculator.undoManager2.addSelectionToLastEntry(selStart);
        }
    }
}
