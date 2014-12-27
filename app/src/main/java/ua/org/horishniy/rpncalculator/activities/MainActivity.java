package ua.org.horishniy.rpncalculator.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ua.org.horishniy.rpncalculator.R;
import ua.org.horishniy.rpncalculator.utils.FiloStack;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static final String LOG = "RageLog: ";

    private static final int PLUS = 0;
    private static final int MINUS = 1;
    private static final int MULTIPLY = 2;
    private static final int DIVISION = 3;
    private static final int REVERCE = 4;
    private static final int SIGN_CHANGE = 5;
    private static final int ENTER = 6;
    private static final int CLEAR = 7;

    private FiloStack<String> stack;
    private StringBuilder buffer;
    private boolean coma;
    private TextView viewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpn_calculator);

        stack = new FiloStack<>();
        buffer = new StringBuilder();
        coma = false;

        viewDisplay = (TextView) findViewById(R.id.text_view_display);

        //Add buttons
        Button button0 = (Button) findViewById(R.id.button_0);
        Button button1 = (Button) findViewById(R.id.button_1);
        Button button2 = (Button) findViewById(R.id.button_2);
        Button button3 = (Button) findViewById(R.id.button_3);
        Button button4 = (Button) findViewById(R.id.button_4);
        Button button5 = (Button) findViewById(R.id.button_5);
        Button button6 = (Button) findViewById(R.id.button_6);
        Button button7 = (Button) findViewById(R.id.button_7);
        Button button8 = (Button) findViewById(R.id.button_8);
        Button button9 = (Button) findViewById(R.id.button_9);
        Button buttonComa = (Button) findViewById(R.id.button_coma);
        Button buttonPlus = (Button) findViewById(R.id.button_plus);
        Button buttonMinus = (Button) findViewById(R.id.button_minus);
        Button buttonMultiply = (Button) findViewById(R.id.button_multiply);
        Button buttonDivision = (Button) findViewById(R.id.button_division);
        Button buttonReverse = (Button) findViewById(R.id.button_reverse);
        Button buttonSignChange = (Button) findViewById(R.id.button_sign_change);
        Button buttonEnter = (Button) findViewById(R.id.button_enter);
        Button buttonClear = (Button) findViewById(R.id.button_clear);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        buttonComa.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonReverse.setOnClickListener(this);
        buttonSignChange.setOnClickListener(this);
        buttonEnter.setOnClickListener(this);
        buttonClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    int id = v.getId();
        switch (id) {
            case R.id.button_0:
                addDigit("0");
                break;
            case R.id.button_1:
                addDigit("1");
                break;
            case R.id.button_2:
                addDigit("2");
                break;
            case R.id.button_3:
                addDigit("3");
                break;
            case R.id.button_4:
                addDigit("4");
                break;
            case R.id.button_5:
                addDigit("5");
                break;
            case R.id.button_6:
                addDigit("6");
                break;
            case R.id.button_7:
                addDigit("7");
                break;
            case R.id.button_8:
                addDigit("8");
                break;
            case R.id.button_9:
                addDigit("9");
                break;
            case R.id.button_coma:
                addDigit(".");
                break;
            case R.id.button_plus:
                math(PLUS);
                break;
            case R.id.button_minus:
                math(MINUS);
                break;
            case R.id.button_multiply:
                math(MULTIPLY);
                break;
            case R.id.button_division:
                math(DIVISION);
                break;
            case R.id.button_reverse:
                function(REVERCE);
                break;
            case R.id.button_sign_change:
                function(SIGN_CHANGE);
                break;
            case R.id.button_enter:
                function(ENTER);
                break;
            case R.id.button_clear:
                function(CLEAR);
                break;
        }

        Log.d(LOG, "Buffer: " + buffer);
    }

    private void addDigit(String d) {
        switch (d) {
            case "0":
                if (!buffer.toString().equals("0")) {
                    buffer.append("0");
                }
                break;
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                if (!buffer.toString().equals("0")) {
                    buffer.append(d);
                } else {
                    buffer.setLength(0);
                    buffer.append(d);
                }
                break;
            case ".":
                if (!coma) {
                    coma = true;
                    if (buffer.toString().equals("")) {
                        buffer.append("0");
                    }
                    buffer.append(".");
                }
                break;
        }
        display(buffer.toString());
    }

    private void function(int id) {
        switch (id) {
            case ENTER:
                if (!buffer.toString().equals("")) {
                    stack.push(buffer.toString());
                }
                clear();
                break;
            case CLEAR:
                clear();
                stack.clear();
                display(getString(R.string.display_default));
                break;
        }
    }

    private void math(int id) {
        if (!buffer.toString().equals("")) {
            stack.push(buffer.toString());
            coma = false;
        }
        if (stack.size() >= 2) {
            double b = Double.parseDouble(stack.peek());
            stack.pop();
            double a = Double.parseDouble(stack.peek());
            stack.pop();
            boolean error = false;
            switch (id) {
                case PLUS:
                    a = a + b;
                    break;
                case MINUS:
                    a = a - b;
                    break;
                case MULTIPLY:
                    a = a * b;
                    break;
                case DIVISION:
                    if (b != 0) {
                        a = a / b;
                    } else {
                        error = true;
                    }
                    break;
            }
            if (!error) {
                Log.d(LOG, "rez = " + a);
                stack.push("" + a);
                display("" + a);
            } else {
                display(getString(R.string.display_error));
            }
        } else {
            display(getString(R.string.display_error));
        }
        clear();
    }

    private void clear(){
        buffer.setLength(0);
        coma = false;
    }

    private void display(String massage) {
        viewDisplay.setText(massage);
    }
}
