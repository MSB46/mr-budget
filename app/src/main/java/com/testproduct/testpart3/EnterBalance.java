package com.testproduct.testpart3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EnterBalance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_balance);

        class Balance {
            String amount;

            void setBalance (String a) {
                amount = a;
            }

            String getAmount() {
                return amount;
            }
        }


        class ClockTestDrive {
            public void main (String [] args) {
                Balance b = new Balance();

                b.setBalance("12345");
                String val = b.getAmount();

            }
        }
    }
}
