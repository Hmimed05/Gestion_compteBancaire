package com.miniProjet.Hamza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        // Obtient la liste des transactions de l'activité principale.
        TransactionList transactions = getIntent().getParcelableExtra(MainActivity.TRANSACTION_LIST);

        final EditText montant = findViewById(R.id.txt_amount);;
        final TextView errorMessage = findViewById(R.id.lbl_amount_check);;
        final Button payButton = findViewById(R.id.btn_pay);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

        // Désactive le bouton de paiement.
        payButton.setEnabled(false);



        // Surveille le champ "montant" pour les changements.
        montant.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            // Fonction pour quand le montant est changé.
            @Override
            public void afterTextChanged(Editable s) {
                // Vérifie si le champ "montant" n'est pas vide.
                if (montant.getText().toString().trim().length() > 0) {
                    String value = montant.getText().toString();
                    float finalValue = Float.valueOf(value);

                    // Affiche un message d'erreur et un bouton de désactivation si le montant choisi est en dehors des obligations.
                    if (finalValue > TransactionList.getSolde()) {
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("Error: entrés un montant suffisant");
                        payButton.setEnabled(false);

                    } else {
                        // Si le transfert est légal, supprimez l'erreur et activez le bouton.
                        errorMessage.setVisibility(View.INVISIBLE);
                        payButton.setEnabled(true);

                        // Listener pour le boutton ""pay""
                        payButton.setOnClickListener(v -> {
                            // Si vous appuyez sur le bouton, mettez à jour le solde de l'argent des utilisateurs.
                            TransactionList.updateSolde(finalValue);
                            montant.setText("");

                            // Crée un nouvel objet de transaction, le remplit et l'ajoute à la liste.
                            transactions.addTransaction( dateFormat.format(new Date()),
                                    finalValue,
                                    TransactionList.getSolde());

                            // Renvoie l'objet à MainActivity.
                            Intent intent = new Intent();
                            intent.putExtra(MainActivity.TRANSACTION_LIST, transactions);
                            setResult(RESULT_OK, intent);
                            finish();
                        });
                    }

                    // Si le montant est 0, affiche un message d'erreur
                    if (finalValue == 0) {
                        errorMessage.setVisibility(View.VISIBLE);
                        errorMessage.setText("Error: Entrer Un nombre différent à zéro");
                        payButton.setEnabled(false);
                    }
                } else {
                    // Si aucun montant n'est choisi (vide), ne pas afficher d'erreur et désactiver le bouton.
                    errorMessage.setVisibility(View.INVISIBLE);
                    payButton.setEnabled(false);
                }
            }
        }) ;
    }
}
