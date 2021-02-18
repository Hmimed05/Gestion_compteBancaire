package com.miniProjet.Hamza;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class TransactionList implements Parcelable {
    public static int Solde;         // Champ statique contenant le solde actuel.
    private ArrayList<Transaction> transactions;     // Champ statique contenant le solde actuel.

    TransactionList() {                                                                     // Constructor. Creates a new empty ArrayList.
        this.transactions = new ArrayList<>();
    }
    // Ajoute une nouvelle Transaction à la liste
    void addTransaction(String date, float montant, float montantLeft) {
        this.transactions.add(new Transaction(date, montant, montantLeft));
    }

    int length() {                                                                          // Returns the length of the ArrayList.
        return this.transactions.size();
    }

    Transaction get(int index) {
        return this.transactions.get(index);
    }

    static float getSolde() {                                                        // Returns the money as a float.
        return (float)TransactionList.Solde / 100;
    }

    static void setSolde(float money) {                                              // Sets the new money, * 100 to make it work with float.
        TransactionList.Solde = ((int)money) * 100;
    }

    static void updateSolde(float transferred) {                                     // Updates the current money.
        TransactionList.Solde -= (transferred * 100);
    }


    protected TransactionList(Parcel in) {
        transactions = in.createTypedArrayList(Transaction.CREATOR);
    }

    public static final Creator<TransactionList> CREATOR = new Creator<TransactionList>() {
        @Override
        public TransactionList createFromParcel(Parcel in) {
            return new TransactionList(in);
        }

        @Override
        public TransactionList[] newArray(int size) {
            return new TransactionList[size];
        }
    };

    @Override
    public int describeContents() {                                                         // Parcel method.
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {                                     // Parcel method.
        dest.writeTypedList(transactions);
    }


    // ------------------------------------------------------------------------------------------------//
                  // Creation d'une classe Transaction
    static class Transaction implements Parcelable  {
        private String date;
        private float montant;
        private float montantLeft;

        Transaction(String date, float montant, float montantLeft) {  // Constructeur. Crée un nouvel objet Transaction.
            this.date = date;  // est appelé à partir de la fonction 'TransactionList' 'add'.
            this.montant = montant;
            this.montantLeft = montantLeft;
        }

        String transactionToString() { // Crée une représentation sous forme de chaîne des champs de données.
            String stringObj;
            stringObj = this.date + " | ";
            stringObj += Float.toString(this.montant) + " | ";
            stringObj += String.valueOf(this.montantLeft);

            return stringObj;
        }

        protected Transaction(Parcel in) {
            date = in.readString();
            montant = in.readFloat();
            montantLeft = in.readFloat();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeFloat(montant);
            dest.writeFloat(montantLeft);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
            @Override
            public Transaction createFromParcel(Parcel in) {
                return new Transaction(in);
            }

            @Override
            public Transaction[] newArray(int size) {
                return new Transaction[size];
            }
        };
    }
}