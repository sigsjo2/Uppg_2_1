package com.example.s.uppg_2_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String PRIME = "primeKey";
    /** start value, this must be odd */
    public static final long lowestPrime = 3;
    /** this must be even */
    public static final int step = 2;
    public static final String TAG = "CalcPrime";
    TextView textView;
    String textViewString;
    String numberAsString;
    long prime;

    /** shared preferences is used as storage */
    SharedPreferences sharedPref;


    /** initialize variables etc at startup */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String viewStr;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        textViewString = textView.getText().toString();

        /** get stored prime or if first time get lowest prime */
        sharedPref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        prime = sharedPref.getLong(PRIME, lowestPrime);

        /** prime must be odd */
        if ((prime % 2) == 0) {
            prime += 1;
        }

        numberAsString = Long.toString(prime);
        viewStr = textViewString + " " + numberAsString;
        textView.setText(viewStr);
    }

    /** Called when user taps the close button */
    public void closeActivity(View view) {

        finish();
    }


    /**  find next prime , which should be higher than last one */
    /**  Called when the user taps the  get next button */
    public void findNextPrime(View view) {

        boolean find = false;
        SharedPreferences.Editor editor = sharedPref.edit();
        String viewStr;

        while (!find) {

            prime += step;
            find = isPrime(prime);
        }

        editor.putLong(PRIME, prime);
        editor.apply();

        numberAsString = Long.toString(prime);

        viewStr = textViewString + " " + numberAsString;
        textView.setText(viewStr);

    }

    /** check if number is a prime */
    private boolean isPrime(long candidate) {

        long sqrt = (long)Math.sqrt(candidate);
        String tmp;

        for(long i = lowestPrime; i <= sqrt; i += step) {

            if((candidate % i) == 0) {
                tmp = Long.toString(candidate);
                Log.d(TAG, tmp);
                return false;
            }
        }


        tmp = Long.toString(candidate);
        Log.d(TAG, tmp);

        return true;
    }
}
