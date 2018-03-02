package com.testproduct.testpart3;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView txtViewPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        String val = getIntent().getStringExtra("PromptValue");

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);

        txtViewPrompt = (TextView) findViewById(R.id.txtViewPrompt);

        txtViewPrompt.setText(val);

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        EditText editTextEnt = (EditText) findViewById(R.id.editText);
        Button btnEnt = (Button) findViewById(R.id.btnEnt);
        btnEnt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(),
                        "Added.",
                        Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("activity","first");
                intent.putExtra("PromptValue",txtViewPrompt.getText().toString());
                startActivity(intent);

            }



        });

    }}
