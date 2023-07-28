package com.akistd.moneybucket.ui.history;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.akistd.moneybucket.R;
import com.akistd.moneybucket.data.MongoDB;
import com.akistd.moneybucket.data.Transaction;
import com.akistd.moneybucket.ui.baocaochithu.QLtheoTuan_Fragment;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ImageButton backButton;
    ArrayList<HistoryClass> historyArrLs;
    HistoryAdapter historyAdapter;
    private MongoDB mongoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        addControls();
        addEvents();
        loadHistory();
    }

    private void addControls() {

        backButton = (ImageButton) findViewById(R.id.imgBtnOut);

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
    private void loadHistory() {
        // Khởi tạo MongoDB instance
        MongoDB mongoDB = MongoDB.getInstance();
        // Gọi phương thức để lấy dữ liệu
        ArrayList<Transaction> transactions = mongoDB.getAllSortedTransaction();
        // TODO: Sử dụng 'transactions' ở đây, ví dụ: hiển thị chúng trong một RecyclerView
        // Tạo một adapter từ ArrayList<Transaction>
        HistoryAdapter historyAdapter = new HistoryAdapter(this, transactions);

        // Tìm RecyclerView trong layout
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        // Đặt adapter cho RecyclerView
        recyclerView.setAdapter(historyAdapter);

        // Đóng kết nối với MongoDB
        mongoDB.close();
    }
    public ArrayList<Transaction> getAllTransaction() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (transactions document : transactions) {
            Transaction transaction = new Transaction();
            transaction.setName(document.getString("name"));
            transaction.setAmount(document.getDouble("lostmoney"));
            transaction.setDescription(document.getString("minuteago"));
            transactions.add(transaction);
        }
        // Đóng kết nối với MongoDB
        mongoDB.close();
        return transactions;
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
}