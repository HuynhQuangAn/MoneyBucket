package com.akistd.moneybucket.ui.history;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akistd.moneybucket.R;
import com.akistd.moneybucket.data.MongoDB;
import com.akistd.moneybucket.data.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import io.realm.Realm;
import io.realm.Sort;

public class HistoryActivity extends AppCompatActivity {
    ImageButton backButton;
    ArrayList<HistoryClass> historyArrLs;
    Realm realm = Realm.getDefaultInstance();
    HistoryAdapter adapter;
    ArrayList<Transaction> transactions = MongoDB.getInstance().getThisMonthSortedOutcomeTransaction();
    private MongoDB mongoDB;
    private AlertDialog.Builder recyclerView;
    private Calendar time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // Initialize Realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add
        addControls();
        addEvents();

        // Load data
        loadDataForThisMonth();
    }

    private void addControls() {

        backButton = (ImageButton) findViewById(R.id.imgBtnOut);

    }

    private void loadDataForThisMonth() {
        ArrayList<Transaction> transactions = getThisMonthSortedOutcomeTransaction();
        adapter = new HistoryAdapter(historyArrLs);
        recyclerView.setAdapter(adapter, null);
    }
    private void loadDataForThisWeek() {
        ArrayList<Transaction> transactions = getWeekSortedOutcomeTransaction();
        adapter = new HistoryAdapter(historyArrLs);
        recyclerView.setAdapter(adapter, null);
    }

    private ArrayList<Transaction> getWeekSortedOutcomeTransaction() {
        ArrayList<Transaction> dataList = new ArrayList<>();
        realm.executeTransaction(r->{
            try {

                time.setTimeZone(TimeZone.getTimeZone("UTC"));

                Calendar calendarStart = time;
                calendarStart.set(Calendar.DAY_OF_WEEK, 1);
                calendarStart.set(Calendar.HOUR_OF_DAY, 0);
                calendarStart.set(Calendar.MINUTE,0);
                calendarStart.set(Calendar.SECOND,0);
                Date jan1 = new Date(calendarStart.getTimeInMillis());
                Log.v("getWeekSortedOutcomeTransaction", "Start!! "+String.valueOf(calendarStart.getTime()));

                Calendar calendarEnd = time;
                calendarEnd.add(Calendar.DATE, 6);
                calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
                calendarEnd.set(Calendar.MINUTE,59);
                calendarEnd.set(Calendar.SECOND,59);
                Log.v("getWeekSortedOutcomeTransaction", "END!! "+ String.valueOf(calendarEnd.getTime()));
                Date jan2 = new Date(calendarEnd.getTimeInMillis());
                Log.v("getWeekSortedOutcomeTransaction", "BETWWEN!! "+ String.valueOf(jan1 + " " +jan2));



                Transaction[] trans = realm.where(Transaction.class)
                        .lessThan("trans_amount",0)
                        .lessThanOrEqualTo("create_at", jan2)
                        .greaterThanOrEqualTo("create_at", jan1)
                        .findAll().toArray(new Transaction[0]);


                dataList.addAll(Arrays.asList(trans));

                for (Transaction tr: trans) {
                    Log.v("DATA IN WEEK LOG", tr.getCreateAt().toString());

                    /*if (tr.getCreateAt().after(jan1)){
                        Log.v("DATA IN WEEK LOG ADDED",tr.getCreateAt() + ">" + calendarStart.getTime());
                        dataList.add(tr);
                    }*/

                }
            }catch (Exception e){
                Log.v("AKI EXCEPTION", e.getMessage().toString());
            }
        });

        return dataList;
    }


    private ArrayList<Transaction> getThisMonthSortedOutcomeTransaction() {
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.DAY_OF_MONTH, 1);
        calendarStart.set(Calendar.HOUR, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);

        //Log.v("AKKI LOG", "Start!! "+String.valueOf(calendarStart.getTime()));
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.set(Calendar.DAY_OF_MONTH,calendarEnd.getActualMaximum(Calendar.DATE));
        calendarEnd.set(Calendar.HOUR, 12);
        calendarEnd.set(Calendar.MINUTE, 0);
        calendarEnd.set(Calendar.SECOND, 0);

        //Log.v("AKKI LOG", "END!! "+String.valueOf(calendarEnd.getTime()));
        ArrayList<Transaction> dataList = new ArrayList<>();
        realm.executeTransaction(r->{
            try {
                Transaction[] trans = realm.where(Transaction.class).greaterThanOrEqualTo("create_at", calendarStart.getTime())
                        .lessThan("create_at",calendarEnd.getTime()).sort("create_at", Sort.DESCENDING).lessThan("trans_amount",0).findAll().toArray(new Transaction[0]);
                dataList.addAll(Arrays.asList(trans));

                /*for (Transaction tr: dataList) {
                    Log.v("DATA IN MONTH LOG", tr.getTransAmount().toString());
                }*/
            }catch (Exception e){
                Log.v("AKI EXCEPTION", e.getMessage().toString());
            }
        });

        return dataList;
    }
    

    private void addEvents(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new HistoryFragment());
                finish();
            }

        });


    }
    public void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
// create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction ft = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        ft.replace(R.id.myRecyclerView, fragment);
        ft.commit(); // save the changes
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}