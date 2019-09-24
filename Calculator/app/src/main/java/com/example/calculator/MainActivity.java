package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.math.*;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private CalculatorModel calculator;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BigDecimal x = new BigDecimal("1.3");
        System.out.println(x);

        Log.wtf("asdas", "123123");

        int[] numbersIds = new int[] {
                R.id.zero,
                R.id.one,
                R.id.two,
                R.id.three,
                R.id.four,
                R.id.five,
                R.id.six,
                R.id.seven,
                R.id.eight,
                R.id.nine
        };

        int[] actionsId = new int[] {
                R.id.plus,
                R.id.minus,
                R.id.multiply,
                R.id.division,
                R.id.equals
        };

        text = findViewById(R.id.text);

        calculator = new CalculatorModel();

        View.OnClickListener numberButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onNumPressed(view.getId());
                text.setText(calculator.getText());
            }
        };

        View.OnClickListener actionButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculator.onActionPressed(view.getId());
            }
        };

        for(int i = 0; i < numbersIds.length; i++) {
            findViewById(numbersIds[i]).setOnClickListener(numberButtonClickListener);
        }

        for(int i = 0; i < actionsId.length; i++) {
            findViewById(actionsId[i]).setOnClickListener(actionButtonClickListener);
        }

    }
}
