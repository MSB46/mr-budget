package com.testproduct.testpart3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Add extends AppCompatActivity {

    private Toolbar mToolBar;
    double addAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);



        //String currentString = getIntent().getStringExtra("PromptValue");
        //int currentAmount = Integer.parseInt(currentString);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //EditText addAmount = (EditText) findViewById(R.id.editAdd);
                //int addInt = Integer.parseInt(addAmount.toString());

                //String currentString = getIntent().getStringExtra("PromptValue");
                //int currentAmount = Integer.parseInt(currentString);

                //currentAmount = currentAmount + addInt;



                Toast.makeText(getApplicationContext(),
                        "Actual feature to be added soon. Hopefully",
                        Toast.LENGTH_SHORT).show();


            }


        });
    }
}

