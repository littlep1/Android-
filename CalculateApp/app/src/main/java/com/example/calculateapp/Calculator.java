package com.example.calculateapp;

import android.widget.Toast;
import android.content.Context;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Calculator {
    public String expression, result, str;
    public Stack<String> Operator = new Stack<String>();
    public Stack<Double> Number = new Stack<Double>();
    public Vector<String> s_beh, s_mid;
    public Context mContext;

    Calculator(Context context) {
        expression = "";
        result = "";
        str = "";
        Operator = new Stack<String>();
        Number = new Stack<Double>();
        s_beh = new Vector<String>();
        s_mid = new Vector<String>();
        mContext = context;
    }

    Calculator() {
        expression = "";
        result = "";
        str = "";
        Operator = new Stack<String>();
        Number = new Stack<Double>();
        s_beh = new Vector<String>();
        s_mid = new Vector<String>();
        mContext = null;
    }

    int priority(String a) {  //优先级判断
        if (a.equals(")") || a.equals("("))
            return 0;
        if (a.equals("+") || a.equals("-"))
            return 1;
        if (a.equals("*") || a.equals("/"))
            return 2;
        if (a.equals("^"))
            return 3;
        return -1;
    }

    boolean isoperator(String a) {    //判断当前字符是否为操作符
        if (a.equals(")") || a.equals("(") || a.equals("+") || a.equals("-") || a.equals("*") || a.equals("/") || a.equals("^"))
            return true;
        return false;
    }

    boolean islegal_infix(String str) {
        str = str.replaceAll(" ", ""); //去除空格
        Set<Character> operate_set = new HashSet<>();
        operate_set.add('-');
        operate_set.add('+');
        operate_set.add('*');
        operate_set.add('/');
        operate_set.add('^');

        //拆分字符串
        char[] arr = str.toCharArray();
        int len = arr.length;

        //括号计数
        int temp = 0;

        //数字集合
        List<Character> digit_list = new ArrayList<>();

        for (int i = 0; i < len; i++) {
            if (Character.isDigit(arr[i]) || arr[i] == '.' || arr[i] == 'π') {
                digit_list.add(arr[i]);
            } else {
                //非数字和小数点
                //如果集合中有值，取出来判断这个数字整体是否合法
                if (digit_list.size() > 0) {
                    //判断字符串是否合法
                    boolean isnum = getNumberStringFromList(digit_list);
                    if (isnum) {
                        digit_list.clear();
                    } else {
                        Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                if (arr[i] == '+' || arr[i] == '*' || arr[i] == '/' || arr[i] == '^') {
                    //判断规则(1.不能位于首位 2.不能位于末尾 3.后面不能有其他运算符 4.后面不能有后括号)
                    if (i == 0 || i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
                        Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                        System.out.println("error type : '+' or '*' or '/' or '^' ->" + arr[i]);
                        return false;
                    }
                } else if (arr[i] == '-') {
                    //减号判断规则(1.不能位于末尾 2.后面不能有其他运算符 3.后面不能有后括号)
                    if (i == (len - 1) || operate_set.contains(arr[i + 1]) || arr[i + 1] == ')') {
                        Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                        System.out.println("error type : '-' ->" + arr[i]);
                        return false;
                    }

                } else if (arr[i] == '(') {
                    temp++;
                    //判断规则(1.不能位于末尾 2.后面不能有+，*，/运算符和后括号 3.前面不能为数字)
                    if (i == (len - 1) || arr[i + 1] == '^' || arr[i + 1] == '+' || arr[i + 1] == '*' || arr[i + 1] == '/' || arr[i + 1] == ')' || (i != 0 && Character.isDigit(arr[i - 1]))) {
                        Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                        System.out.println("error type : '(' ->" + arr[i]);
                        return false;
                    }
                } else if (arr[i] == ')') {
                    temp--;
                    //判定规则(1.不能位于首位 2.后面不能是前括号 3.括号计数不能小于0，小于0说明前面少了前括号)
                    if (i == 0 || (i < (len - 1) && arr[i + 1] == '(') || temp < 0) {
                        Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                        System.out.println("error type : ')' ->" + arr[i]);
                        return false;
                    }
                } else {
                    //非数字和运算符
                    Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                    System.out.println("error type : not number and operator ->" + arr[i]);
                    return false;
                }
            }
        }

        //如果集合中有值，取出来判断这个数字整体是否合法
        if (digit_list.size() > 0) {
            //判断字符串是否合法
            boolean result = getNumberStringFromList(digit_list);
            if (result) {
                //如果合法，清空集合，为了判断下一个
                digit_list.clear();
            } else {
                //不合法，返回false
                Toast.makeText(mContext, "输入表达式有误！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        //不为0,说明括号不对等，可能多前括号
        if (temp != 0) {
            Toast.makeText(mContext, "括号不对等！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    boolean getNumberStringFromList(List<Character> list) {
        //如果集合中有值，取出来判断这个数字整体是否合法
        if (list != null) {
            StringBuffer stringBuffer = new StringBuffer();
            for (Character character : list) {
                stringBuffer.append(character);
            }
            //正则判断数字是否合法
            boolean isnum = isNumber(stringBuffer.toString());

            if (!isnum) {
                Toast.makeText(mContext, "输入数字不合法！", Toast.LENGTH_SHORT).show();
                System.out.println("error type: digit ->" + stringBuffer.toString());
            }
            return isnum;
        }
        return false;
    }

    boolean isNumber(String str) {  //正则判断
        if (str.equals("π"))
            return true;
        Pattern pattern = Pattern.compile("^-?([1-9]\\d*\\.\\d+|0\\.\\d*[1-9]\\d*|[1-9]\\d*|0)$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }


    void cal() {
        str = expression;
        System.out.println(str);
        while (!Operator.empty())
            Operator.pop();
        while (!Number.empty())
            Number.pop();
        s_mid.clear();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '-') { //解决负数
                if (i == 0) {
                    StringBuilder tmp = new StringBuilder(str);
                    tmp.insert(0, '0');
                    str = tmp.toString();
                    System.out.println(str);
                } else if (str.charAt(i - 1) == '(') {
                    StringBuilder tmp = new StringBuilder(str);
                    tmp.insert(i, '0');
                    str = tmp.toString();
                    System.out.println(str);
                }

            }
        }
        String inse1 = new String("");
        for (int i = 0; i < str.length(); i++) {    //小数点转化
            if (str.charAt(i) >= '0' && str.charAt(i) <= '9' || str.charAt(i) == '.') {
                inse1 = inse1.concat(String.valueOf(str.charAt(i)));
            } else if (str.charAt(i) == 'π') {
                inse1 = inse1.concat("p");
            } else {
                if (!inse1.isEmpty()) {
                    s_mid.add(inse1);
                    inse1 = new String("");
                }
                String inse2 =new String("");
                inse2 = inse2.concat(String.valueOf(str.charAt(i)));
                s_mid.add(inse2);
            }
        }
        if (!inse1.isEmpty()) {
            s_mid.add(inse1);
        }

        s_beh.clear();
        for (int i = 0; i < s_mid.size(); i++) {    //中缀到后缀的转化
            String tmp = new String(s_mid.elementAt(i));
            if (isoperator(tmp)) {
                if (tmp.equals(")")) {
                    while (!Operator.peek().equals("(")) {
                        s_beh.add(Operator.peek());
                        Operator.pop();
                    }
                    Operator.pop();
                } else if (Operator.empty() || tmp.equals("(") || priority(tmp) > priority(Operator.peek())) {
                    Operator.push(tmp);
                } else if (priority(tmp) <= priority(Operator.peek())) {
                    while (priority(tmp) <= priority(Operator.peek()) && (!Operator.empty())) {
                        s_beh.add(Operator.peek());
                        Operator.pop();
                        if (Operator.empty()) break;
                    }
                    Operator.push(tmp);
                }
            } else {
                s_beh.add(tmp);
            }
            if (i == s_mid.size() - 1) {
                while (!Operator.isEmpty()) {
                    s_beh.add(Operator.peek());
                    Operator.pop();
                }
                break;
            }
        }
        for(int i=0;i<s_beh.size();i++){
            System.out.println(s_beh.elementAt(i));
        }
        double x, y;
        for (int i = 0; i < s_beh.size(); i++) {    //后缀表达式计算求值
            String tmp = new String(s_beh.elementAt(i));
            if (tmp.equals("p")) {
                Number.push(Math.PI);
            } else if (!isoperator(s_beh.elementAt(i))) {
                Number.push(Double.valueOf(s_beh.elementAt(i)));
//                System.out.println(Number.peek());
            } else if (tmp.equals("+")) {
                x = Number.peek();
                Number.pop();
                y = Number.peek();
                Number.pop();
                Number.push(x + y);
            } else if (tmp.equals("-")) {
                x = Number.peek();
                Number.pop();
                y = Number.peek();
                Number.pop();
                Number.push(y - x);
//                System.out.println(y-x);
            } else if (tmp.equals("*")) {
                System.out.println(Number.peek());
                x = Number.peek();
                Number.pop();
                System.out.println(Number.peek());
                y = Number.peek();
                Number.pop();
                Number.push(y * x);
                System.out.println(x*y);
            } else if (tmp.equals("/")) {
                x = Number.peek();
                if (x == 0) {
                    Toast.makeText(mContext, "计算过程中出现除数为0", Toast.LENGTH_LONG).show();   //除0错误
                    result = "";
                    break;
                }
                Number.pop();
                y = Number.peek();
                Number.pop();
                Number.push(y / x);
                System.out.println(y/x);
            } else if (tmp.equals("^")) {
                x = Number.peek();
                Number.pop();
                y = Number.peek();
                Number.pop();
                Number.push(Math.pow(y, x));
            }
        }
        if (Number.peek().intValue() == Number.peek()) {
            int tmp = Number.peek().intValue();
            result = String.format("%.0f",Number.peek());
        } else result = String.format("%.8f",Number.peek());
    }
}
