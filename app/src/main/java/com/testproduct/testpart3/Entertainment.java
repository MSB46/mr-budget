package com.testproduct.testpart3;

import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Entertainment extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolBar;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);

        //editTextPrompt EditText = (EditText) findViewById(R.layout.editTextPrompt);
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        final EditText editTextEnt = (EditText) findViewById(R.id.editTextEnt);
        Button btnEnt = (Button) findViewById(R.id.btnEnt);
        btnEnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MainActivity maEnt = new MainActivity();
                maEnt.prepareListData();
                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();

                String newValueEnt = editTextEnt.getText().toString();

                listDataHeader.add("Investment 1");
                List<String> INVESTMENT_1  = new ArrayList<>();
                //INVESTMENT_1.add(newValueEnt);


                listAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),
                        "Added.",
                        Toast.LENGTH_SHORT).show();

                Intent entToHome = new Intent(Entertainment.this, MainActivity.class);
                startActivity(entToHome);

            }



        });

    }}
