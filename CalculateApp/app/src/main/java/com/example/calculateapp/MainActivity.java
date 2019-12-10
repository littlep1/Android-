package com.example.calculateapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    EditText edit;
    EditText under_edit;
    Calculator cal = new Calculator(this);
    Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, plus, sub, mul, div, pow, dot, equal, sqrt, left_bracket, right_bracket, clear, sin, cos, tan, back, pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {    //隐藏标题栏
            actionBar.hide();
        }
        init();
    }

    void init() {
        edit = (EditText) findViewById(R.id.res);
        under_edit = (EditText) findViewById(R.id.under_res);
        edit.setInputType(InputType.TYPE_NULL); //禁止弹出软键盘
        under_edit.setInputType(InputType.TYPE_NULL);
        num0 = (Button) findViewById(R.id.num0);
        num1 = (Button) findViewById(R.id.num1);
        num2 = (Button) findViewById(R.id.num2);
        num3 = (Button) findViewById(R.id.num3);
        num4 = (Button) findViewById(R.id.num4);
        num5 = (Button) findViewById(R.id.num5);
        num6 = (Button) findViewById(R.id.num6);
        num7 = (Button) findViewById(R.id.num7);
        num8 = (Button) findViewById(R.id.num8);
        num9 = (Button) findViewById(R.id.num9);
        plus = (Button) findViewById(R.id.plus);
        sub = (Button) findViewById(R.id.sub);
        mul = (Button) findViewById(R.id.mul);
        div = (Button) findViewById(R.id.div);
        pow = (Button) findViewById(R.id.pow);
        dot = (Button) findViewById(R.id.dot);
        equal = (Button) findViewById(R.id.equal);
        sqrt = (Button) findViewById(R.id.sqrt);
        left_bracket = (Button) findViewById(R.id.left_bracket);
        right_bracket = (Button) findViewById(R.id.right_bracket);
        clear = (Button) findViewById(R.id.clear);
        back = (Button) findViewById(R.id.back);
        sin = (Button) findViewById(R.id.sin);
        cos = (Button) findViewById(R.id.cos);
        tan = (Button) findViewById(R.id.tan);
        pi = (Button) findViewById(R.id.pi);
        num0.setOnClickListener(listener);
        num1.setOnClickListener(listener);
        num2.setOnClickListener(listener);
        num3.setOnClickListener(listener);
        num4.setOnClickListener(listener);
        num5.setOnClickListener(listener);
        num6.setOnClickListener(listener);
        num7.setOnClickListener(listener);
        num8.setOnClickListener(listener);
        num9.setOnClickListener(listener);
        plus.setOnClickListener(listener);
        sub.setOnClickListener(listener);
        mul.setOnClickListener(listener);
        div.setOnClickListener(listener);
        pow.setOnClickListener(listener);
        dot.setOnClickListener(listener);
        equal.setOnClickListener(listener);
        sqrt.setOnClickListener(listener);
        left_bracket.setOnClickListener(listener);
        right_bracket.setOnClickListener(listener);
        clear.setOnClickListener(listener);
        back.setOnClickListener(listener);
        sin.setOnClickListener(listener);
        cos.setOnClickListener(listener);
        tan.setOnClickListener(listener);
        pi.setOnClickListener(listener);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.num0:
                    cal.expression += '0';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num1:
                    cal.expression += '1';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num2:
                    cal.expression += '2';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num3:
                    cal.expression += '3';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num4:
                    cal.expression += '4';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num5:
                    cal.expression += '5';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num6:
                    cal.expression += '6';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num7:
                    cal.expression += '7';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num8:
                    cal.expression += '8';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.num9:
                    cal.expression += '9';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.dot:
                    cal.expression += '.';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.div:
                    cal.expression += '/';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.sub:
                    cal.expression += '-';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.plus:
                    cal.expression += '+';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.mul:
                    cal.expression += '*';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.left_bracket:
                    cal.expression += '(';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.right_bracket:
                    cal.expression += ')';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.pow:
                    cal.expression += '^';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.back:
                    int new_length = cal.expression.length() - 1;
                    if (new_length < 0) new_length = 0;
                    cal.expression = cal.expression.substring(0, new_length);
                    under_edit.setText(cal.expression);
                    break;
                case R.id.clear:
                    cal.expression = "";
                    cal.result = "";
                    cal.str = "";
                    while (!cal.Operator.empty())
                        cal.Operator.pop();
                    while (!cal.Number.empty())
                        cal.Number.pop();
                    edit.setText("");
                    under_edit.setText("0");
                    break;
                case R.id.pi:
                    cal.expression += 'π';
                    under_edit.setText(cal.expression);
                    break;
                case R.id.equal:
                    cal.str = cal.expression;
                    if (!cal.str.isEmpty() && cal.islegal_infix(cal.str)) {
                        cal.cal();
                        edit.setText(cal.expression);
                        under_edit.setText(cal.result);
                        cal.expression = cal.result;
                    }
                    break;
                case R.id.sqrt:
                    if(!cal.result.isEmpty()) {
                        cal.expression=cal.result;
                        Double tmp=Math.sqrt(Double.valueOf(cal.result));
                        if(tmp.intValue() == tmp){
                            cal.result = String.format("%.0f",tmp);
                        }
                        else cal.result = String.format("%.8f",tmp);
                        edit.setText('√'+cal.expression);
                        under_edit.setText(cal.result);
                        cal.expression = cal.result;
                    }
                    break;
                case R.id.sin:
                    if(!cal.result.isEmpty()) {
                        cal.expression=cal.result;
                        Double tmp=Math.sin(Double.valueOf(cal.result));
                        System.out.println(tmp);
                        if(tmp.intValue() == tmp){
                            cal.result = String.format("%.0f",tmp);
                        }
                        else cal.result = String.format("%.8f",tmp);
                        edit.setText("sin("+cal.expression+")");
                        under_edit.setText(cal.result);
                        cal.expression = cal.result;
                    }
                    break;
                case R.id.cos:
                    if(!cal.result.isEmpty()) {
                        cal.expression=cal.result;
                        Double tmp=Math.cos(Double.valueOf(cal.result));
                        if(tmp.intValue() == tmp){
                            cal.result = String.format("%.0f",tmp);
                        }
                        else cal.result = String.format("%.8f",tmp);
                        edit.setText("cos("+cal.expression+")");
                        under_edit.setText(cal.result);
                        cal.expression = cal.result;
                    }
                    break;
                case R.id.tan:
                    if(!cal.result.isEmpty()) {
                        cal.expression=cal.result;
                        Double tmp=Math.tan(Double.valueOf(cal.result));
                        if(tmp.intValue() == tmp){
                            cal.result = String.format("%.0f",tmp);
                        }
                        else cal.result = String.format("%.8f",tmp);
                        edit.setText("tan("+cal.expression+")");
                        under_edit.setText(cal.result);
                        cal.expression = cal.result;
                    }
                    break;
            }
        }

    };
}
