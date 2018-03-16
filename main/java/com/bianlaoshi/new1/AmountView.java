package com.bianlaoshi.new1;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by zhuangyuan on 2017/12/9.
 */

public class AmountView extends LinearLayout implements View.OnClickListener,TextWatcher{
    private static final String TAG = "AmountView";
    private int amount = 1; //排名
    private int studentamount= 6; //小组人数

    private OnAmountChangeListener mListener;

    private EditText etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    public AmountView(Context context) {
        super(context);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.amountview, this);
        etAmount = (EditText) findViewById(R.id.etAmount);
        String m="1";
        etAmount.setText(m);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);

        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
        etAmount.addTextChangedListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, LayoutParams.WRAP_CONTENT);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, LayoutParams.MATCH_PARENT);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    public void setGoods_storage(int goods_storage) {
        this.studentamount = goods_storage;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > 1) {
                amount--;
                Log.d(TAG, "onClick:===== "+amount);
                etAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < studentamount) {
                amount++;
                etAmount.setText(amount + "");
            }
        }
        //清除焦点
        etAmount.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount >studentamount) {
            etAmount.setText(studentamount + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }
}
