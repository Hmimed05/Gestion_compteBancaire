package com.miniProjet.Hamza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int TRANSFER_REQUEST_CODE = 0;
    private TransactionList transactions = new TransactionList();              // Liste de Transactions
    public static final String TRANSACTION_LIST = "TRANSACTION_LIST";          //Nom de la liste de transactions à utiliser dans l'intention.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Button transferButton;
        final Button transactionButton;
        Random rand = new Random();  //pour avoir un Solde aléatoire
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss"); // pour la date exacte du transaction faite

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Obtient la Solde en tant qu'objet.
        TextView balance = findViewById(R.id.lbl_balance);

        // Créez un nombre aléatoire entre 2000et 100000et mettez à jour la classe «Transaction».
        TransactionList.setSolde((float) rand.nextInt((10000-2000) + 1) + 2000);

        // Affiche le solde actuel.
        balance.setText(String.valueOf(TransactionList.getSolde()));

        // Crée un nouvel objet de transaction. Et remplissez la transaction initiale.
        transactions.addTransaction(dateFormat.format(new Date()), TransactionList.getSolde(), TransactionList.getSolde());

        // Transfer le button listener.
        transferButton = findViewById(R.id.btn_pay);
        transferButton.setOnClickListener(v -> openTransferActivity());

        // Transaction du button listener.
        transactionButton = findViewById(R.id.btn_transactions);
        transactionButton.setOnClickListener(v -> openTransactionsActivity());
    }

    // Lorsque l'activité reprend , mettez à jour le solde.
    @Override
    public void onResume() {
        super.onResume();
        TextView balance = findViewById(R.id.lbl_balance);
        balance.setText(String.valueOf(TransactionList.getSolde()));
    }

    // Une fois l'activité de transfert terminée, mettez à jour la liste des transactions si elle a été modifiée.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Vérifiez qu'il s'agit bien de TransferActivity avec un résultat OK.
        if (requestCode == TRANSFER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Mettre à jour la liste des transactions à partir de la classe parcellable.
                transactions = data.getParcelableExtra(TRANSACTION_LIST);
            }
        }
    }

    // Méthode pour ouvrir l'activité de transfert.
    public void openTransferActivity() {
        Intent intent = new Intent(this, TransferActivity.class);
        intent.putExtra(TRANSACTION_LIST , transactions);                 // Passe la liste des transactions.
        startActivityForResult(intent, TRANSFER_REQUEST_CODE);
    }

    // Méthode pour ouvrir l'activité de transaction.
    public void openTransactionsActivity() {
        Intent intent = new Intent(this, TransactionsActivity.class);
        intent.putExtra(TRANSACTION_LIST , transactions);
        startActivity(intent);
    }
}
