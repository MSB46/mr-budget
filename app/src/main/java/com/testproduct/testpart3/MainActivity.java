package com.testproduct.testpart3;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity  {

    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    private FirebaseAuth firebaseAuth;
    private TextView textViewUserEmail;


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigation;
    private Toolbar mToolBar;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    TextView txtViewPrompt;

    private static int x = 0;

    FirebaseDatabase database;
    private DatabaseReference databaseReference;

    DatabaseReference myBudget;

    private static String category;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.simpleSpinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.categories,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // i represents position
                // adapterView is the parent

                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+" chosen",Toast.LENGTH_LONG).show();
                category = adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        txtViewPrompt = (TextView)findViewById(R.id.txtViewPrompt);

        final EditText editTemp = (EditText) findViewById(R.id.editTemp);

        String val = getIntent().getStringExtra("PromptValue");





        txtViewPrompt.setText("Your current budget is $"+val);

        firebaseAuth = FirebaseAuth.getInstance();
        if( firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = firebaseAuth.getCurrentUser();



        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
       prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });

     // Preparing the list data
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);


        final String blockCharacterSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*().,-";

        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };


        editTemp.setFilters(new InputFilter[] { filter });


        final Button btnTemp = (Button) findViewById(R.id.btnTemp);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MMM/dd/yyyy");
        final String formattedDate = df.format(c);


        final AlertDialog.Builder builder = new AlertDialog.Builder(this);







        btnTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTemp.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter a value", Toast.LENGTH_SHORT).show();
                    //prevents further execution
                    return;
                }




                builder
                        .setMessage("$"+editTemp.getText().toString()+" Is this your intended input?")
                        .setPositiveButton("Confirm",  new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Yes-code
                                String val = getIntent().getStringExtra("PromptValue");
                                double valBudget = Double.parseDouble(val);


                                listDataHeader.add("$"+editTemp.getText().toString());
                                double deduct = Double.parseDouble(editTemp.getText().toString());
                                double currentAmount = valBudget - deduct;

                                int xplus = x+1;

                                List<String> ArrayNew = new ArrayList<>();
                                ArrayNew.add(category);
                                ArrayNew.add(formattedDate.toString());
                                ArrayNew.add("Expense #"+xplus);


                                listAdapter.notifyDataSetChanged();
                                listDataChild.put(listDataHeader.get(x), ArrayNew);

                                if (currentAmount > 0) {
                                    if (x == 0) {

                                        txtViewPrompt.setText("Your current budget is $" + String.valueOf(currentAmount));



                                    }

                                    if (x != 0) {

                                        double newValue = currentAmount - deduct;

                                        txtViewPrompt.setText("Your current budget is $" + String.valueOf(newValue));


                                    }
                                }

                                if (currentAmount < 0 || currentAmount == 0){

                                    txtViewPrompt.setText("You outspent your current budget!");
                                    btnTemp.setEnabled(false);

                                }


                                Toast.makeText(getApplicationContext(),
                                        "Added.",
                                        Toast.LENGTH_SHORT).show();



                                x++;

                                saveUserInfo();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .show();




            }
        });


        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolBar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.food:
                        Intent fo = new Intent(MainActivity.this, Food.class);
                        fo.putExtra("PromptValue",txtViewPrompt.getText());
                        startActivity(fo);
                        break;

                    case R.id.entertainment:
                        Intent en = new Intent(MainActivity.this, Entertainment.class);
                        en.putExtra("PromptValue",txtViewPrompt.getText());
                        startActivity(en);
                        break;

                    case R.id.utilities:
                        Intent ut = new Intent(MainActivity.this, Utilities.class);
                        ut.putExtra("PromptValue",txtViewPrompt.getText());
                        startActivity(ut);
                        break;

                    case R.id.logout:
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));

                }
                return false;
            }});}


    private void saveUserInfo(){
        String val = getIntent().getStringExtra("PromptValue");
        EditText editTemp = (EditText) findViewById(R.id.editTemp);

        String expenses = editTemp.getText().toString().trim();
        String budget = val.trim();

        UserInfo userInfo = new UserInfo(budget,expenses);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).push().setValue(userInfo);

        //databaseReference.child(user.getUid()).push().setValue(editTemp.getText().toString());


    }

    public boolean onOptionsItemSelected(MenuItem item) {


        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    public void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        Entertainment ent = new Entertainment();

        //listDataHeader.add("$5.00");
        //listDataHeader.add("B");
        //listDataHeader.add("C");
        //listDataHeader.add("D");


        //List<String> ArrayA = new ArrayList<>();
        //ArrayA.add("Food");
        //ArrayA.add("Feb/25/2018");
        //ArrayA.add("Test");

       /* List<String> B = new ArrayList<>();
        B.add("Test");
        B.add("Test");
        B.add("Test");

        List<String> C = new ArrayList<>();
        C.add("Test");
        C.add("Test");
        C.add("Test");

        List<String> D = new ArrayList<>();
        D.add("Test");
        D.add("Test");
        D.add("Test");
        */


        //listDataChild.put(listDataHeader.get(0), ArrayA);
        //listDataChild.put(listDataHeader.get(1), B);
        //listDataChild.put(listDataHeader.get(2), C);
        //listDataChild.put(listDataHeader.get(3), D);

    }


        }