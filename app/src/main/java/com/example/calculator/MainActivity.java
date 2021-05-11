package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resOut, draftOut;
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btnDel, btnAC, btnPercent, btnPlus, btnMinus, btnDiv, btnMul, btnEqual, btnDot;

    double firstPara = 0, secondPara = 0;

    boolean errorDivide = false;

    public enum STATE {
        FIRST_OP, RESET_SECOND_OP, SECOND_OP
    }

    STATE state = STATE.FIRST_OP;

    public enum Operator {
        NONE,
        ADD,
        SUB,
        MUL,
        DIV,
        EQUAL,
        PERCENT,
    }

    Operator operator = Operator.NONE;

    public void enableButton(boolean setEnable)
    {
        btnPlus.setEnabled(setEnable);
        btnMinus.setEnabled(setEnable);
        btnMul.setEnabled(setEnable);
        btnDiv.setEnabled(setEnable);
        btnDot.setEnabled(setEnable);
        btnPercent.setEnabled(setEnable);
        btnEqual.setEnabled(setEnable);
        btnDel.setEnabled(setEnable);

        if(!setEnable)
        {
            btnPlus.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnMinus.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnMul.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnEqual.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnDiv.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnDot.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnPercent.setBackgroundColor(Color.parseColor("#ffcc66"));
            btnDot.setBackgroundColor(Color.parseColor("#ffcc66"));
        }
        else
        {
            btnPlus.setBackgroundColor(Color.parseColor("#ff6600"));
            btnMinus.setBackgroundColor(Color.parseColor("#ff6600"));
            btnMul.setBackgroundColor(Color.parseColor("#ff6600"));
            btnEqual.setBackgroundColor(Color.parseColor("#ff6600"));
            btnDiv.setBackgroundColor(Color.parseColor("#ff6600"));
            btnDot.setBackgroundColor(Color.parseColor("#ff6600"));
            btnPercent.setBackgroundColor(Color.parseColor("#ff6600"));
            btnDot.setBackgroundColor(Color.parseColor("#ff6600"));
        }
    }


    @Override
    public void onClick(View v) {
        switch (state)
        {
            case FIRST_OP:

                if(errorDivide)
                {
                    enableButton(true);
                    errorDivide = false;
                }

                /* Get number */
                if( v.getId() == R.id.btn0 || v.getId() == R.id.btn1 || v.getId() == R.id.btn2 ||
                    v.getId() == R.id.btn3 || v.getId() == R.id.btn4 || v.getId() == R.id.btn5 ||
                    v.getId() == R.id.btn6 || v.getId() == R.id.btn7 || v.getId() == R.id.btn8 ||
                    v.getId() == R.id.btn9)
                {
                    String displayNumber = ((Button) v).getText().toString();
                    if(resOut.getText().toString().equals("0") == true || operator == Operator.EQUAL)
                    {
                        resOut.setText(displayNumber);
                        operator = Operator.NONE;
                    }
                    else
                    {
                        resOut.setText(resOut.getText() + displayNumber);
                    }
                }

                /* Get dot */
                else if( v.getId() == R.id.btnDot)
                {
                    if(resOut.getText().toString().contains(".") == false)
                    {
                        resOut.setText(resOut.getText() + ".");
                    }
                }

                /* Delete element */
                else if( v.getId() == R.id.btnDel && operator != Operator.EQUAL)
                {
                    if(resOut.getText().toString().length() > 1)
                    {
                        String newRes = resOut.getText().toString();
                        resOut.setText(newRes.substring(0, newRes.length() - 1));
                    }
                    else
                    {
                        resOut.setText("0");
                    }
                }

                /* AC button */
                else if( v.getId() == R.id.btnAC)
                {
                    resOut.setText("0");
                    draftOut.setText("");
                }

                /* equal button */
                else if( v.getId() == R.id.btnEqual)
                {
                    operator = Operator.EQUAL;
                    resOut.setText(Double.parseDouble(resOut.getText().toString()) + "");
                    draftOut.setText(Double.parseDouble(resOut.getText().toString()) + " = ");
                }

                /* Get operator */
                else if( v.getId() != R.id.btnPercent && v.getId() != R.id.btnDel)
                {
                    firstPara = Double.parseDouble(resOut.getText().toString());
                    String opOut = "";
                    switch ( v.getId())
                    {
                        case R.id.btnPlus:
                            operator = Operator.ADD;
                            opOut = " + ";
                            break;

                        case R.id.btnMinus:
                            operator = Operator.SUB;
                            opOut = " - ";
                            break;

                        case R.id.btnMul:
                            operator = Operator.MUL;
                            opOut = " * ";
                            break;

                        case R.id.btnDiv:
                            operator = Operator.DIV;
                            opOut = " / ";
                            break;

                    }
                    draftOut.setText(firstPara + opOut);
                    state = STATE.RESET_SECOND_OP;
                }
                break;

            case RESET_SECOND_OP:
                /* Get number */
                if(     v.getId() == R.id.btn0 || v.getId() == R.id.btn1 || v.getId() == R.id.btn2 ||
                        v.getId() == R.id.btn3 || v.getId() == R.id.btn4 || v.getId() == R.id.btn5 ||
                        v.getId() == R.id.btn6 || v.getId() == R.id.btn7 || v.getId() == R.id.btn8 ||
                        v.getId() == R.id.btn9)
                {
                    String displayNumber = ((Button) v).getText().toString();
                    resOut.setText(displayNumber);
                    state = STATE.SECOND_OP;
                }
                else if( v.getId() == R.id.btnAC)
                {
                    firstPara = 0;
                    secondPara = 0;
                    resOut.setText("0");
                    draftOut.setText("");
                    state = STATE.FIRST_OP;
                }
                else if( v.getId() != R.id.btnDot && v.getId() != R.id.btnPercent && v.getId() != R.id.btnDel)
                {
                    String opOut = "";
                    switch ( v.getId())
                    {
                        case R.id.btnPlus:
                            operator = Operator.ADD;
                            opOut = " + ";
                            break;

                        case R.id.btnMinus:
                            operator = Operator.SUB;
                            opOut = " - ";
                            break;

                        case R.id.btnMul:
                            operator = Operator.MUL;
                            opOut = " * ";
                            break;

                        case R.id.btnDiv:
                            operator = Operator.DIV;
                            opOut = " / ";
                            break;

                    }
                    draftOut.setText(firstPara + opOut);
                }
                break;

            case SECOND_OP:
                double perTemp = 0;
                /* Get number */
                if(     v.getId() == R.id.btn0 || v.getId() == R.id.btn1 || v.getId() == R.id.btn2 ||
                        v.getId() == R.id.btn3 || v.getId() == R.id.btn4 || v.getId() == R.id.btn5 ||
                        v.getId() == R.id.btn6 || v.getId() == R.id.btn7 || v.getId() == R.id.btn8 ||
                        v.getId() == R.id.btn9)
                {
                    String displayNumber = ((Button) v).getText().toString();
                    resOut.setText(resOut.getText() + displayNumber);
                }

                /* Get dot */
                else if( v.getId() == R.id.btnDot)
                {
                    if(resOut.getText().toString().contains(".") == false)
                    {
                        resOut.setText(resOut.getText() + ".");
                    }
                }

                /* Delete element */
                else if( v.getId() == R.id.btnDel)
                {
                    if(resOut.getText().toString().length() > 1)
                    {
                        String newRes = resOut.getText().toString();
                        resOut.setText(newRes.substring(0, newRes.length() - 1));
                    }
                    else
                    {
                        resOut.setText("0");
                    }
                }

                /* AC button */
                else if(v.getId() == R.id.btnAC)
                {
                    firstPara = 0;
                    resOut.setText("0");
                    draftOut.setText("");
                    state = STATE.FIRST_OP;
                }

                else
                {
                    secondPara = Double.parseDouble(resOut.getText().toString());
                    if (v.getId() == R.id.btnPercent)
                    {
                        perTemp = firstPara/100;
                    }
                    if(perTemp != 0)
                    {
                        secondPara *= perTemp;
                    }
                    switch (operator)
                    {
                        case ADD:
                            firstPara += secondPara;
                            operator = Operator.NONE;
                            break;

                        case SUB:
                            firstPara -= secondPara;
                            operator = Operator.NONE;
                            break;

                        case MUL:
                            firstPara *= secondPara;
                            operator = Operator.NONE;
                            break;

                        case DIV:
                            if(secondPara != 0)
                            {
                                firstPara /= secondPara;
                            }
                            else
                            {
                                errorDivide = true;
                                firstPara = 0;
                            }
                            operator = Operator.NONE;
                            break;
                    }

                    String opOut = "";

                    switch (v.getId())
                    {
                        case R.id.btnPlus:
                            operator = Operator.ADD;
                            state = STATE.RESET_SECOND_OP;
                            opOut = " + ";
                            break;

                        case R.id.btnMinus:
                            operator = Operator.SUB;
                            state = STATE.RESET_SECOND_OP;
                            opOut = " - ";
                            break;

                        case R.id.btnMul:
                            operator = Operator.MUL;
                            state = STATE.RESET_SECOND_OP;
                            opOut = " * ";
                            break;

                        case R.id.btnDiv:
                            operator = Operator.DIV;
                            state = STATE.RESET_SECOND_OP;
                            opOut = " / ";
                            break;

                        case R.id.btnPercent:
                        case R.id.btnEqual:
                            operator = Operator.EQUAL;
                            state = STATE.FIRST_OP;
                            opOut = secondPara + " =";
                            break;
                    }
                    if(operator != Operator.EQUAL)
                    {
                        draftOut.setText(firstPara + opOut);
                    }
                    else
                    {
                        draftOut.setText(draftOut.getText() + opOut);
                    }
                    if(errorDivide)
                    {
                        enableButton(false);
                        resOut.setText("CAN NOT DIVIDE BY ZERO");
                    }
                    else
                    {
                        String output = String.format("%.14f", firstPara);
                        if (output.endsWith("00"))
                        {
                            resOut.setText((firstPara + ""));
                        }
                        else
                        {
                            resOut.setText(output);
                        }
                    }

                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resOut = (TextView) findViewById(R.id.result);
        draftOut = (TextView) findViewById(R.id.draft);
        btn0 = (Button) findViewById(R.id.btn0); btn0.setOnClickListener(this);
        btn1 = (Button) findViewById(R.id.btn1); btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2); btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3); btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4); btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5); btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6); btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7); btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8); btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9); btn9.setOnClickListener(this);
        btnPlus = (Button) findViewById(R.id.btnPlus); btnPlus.setOnClickListener(this);
        btnMinus = (Button) findViewById(R.id.btnMinus); btnMinus.setOnClickListener(this);
        btnMul = (Button) findViewById(R.id.btnMul); btnMul.setOnClickListener(this);
        btnDiv = (Button) findViewById(R.id.btnDiv); btnDiv.setOnClickListener(this);
        btnAC = (Button) findViewById(R.id.btnAC); btnAC.setOnClickListener(this);
        btnDel = (Button) findViewById(R.id.btnDel); btnDel.setOnClickListener(this);
        btnPercent = (Button) findViewById(R.id.btnPercent); btnPercent.setOnClickListener((this));
        btnEqual = (Button) findViewById(R.id.btnEqual); btnEqual.setOnClickListener(this);
        btnDot = (Button) findViewById(R.id.btnDot); btnDot.setOnClickListener(this);


    }
}