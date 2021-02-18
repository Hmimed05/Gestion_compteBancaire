package com.miniProjet.Hamza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TransactionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        ListView list = findViewById(R.id.transactionList);

        // Obtient la liste des transactions de l'activité principale.
        TransactionList transactions = getIntent().getParcelableExtra(MainActivity.TRANSACTION_LIST);

        // Crée un nouveau arrayList pour stocker la représentation sous forme de chaîne des transactions.
        ArrayList<String> transactionString = new ArrayList<>();
        for (int i = 0; i < transactions.length(); i++) {
            transactionString.add(transactions.get(i).transactionToString());
        }

        // Crée l'adaptateur de tableau et le connecte à ListView.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TransactionsActivity.this,
                android.R.layout.simple_list_item_1, transactionString);
        list.setAdapter(adapter);

        // Message Toast lorsqu'un élément est cliqué dans la liste.
        list.setOnItemLongClickListener((parent, view, position, id) -> {
            Toast.makeText(TransactionsActivity.this,
                    list.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show();
            return true;
        });
    }
}
