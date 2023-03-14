package com.example.systempos.Card;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Customer.View_Customer;
import com.example.systempos.Payment.PaymentData;
import com.example.systempos.Payment.Show_PaymentActivity;
import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityCardBinding;

import java.text.DecimalFormat;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    ActivityCardBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;

    List<CustomerData> customerDataList;
    List<PaymentData> paymentDataList;
    RecyclerView recyclerView;

    TextView txSubtotal, txtotalAmount, txSubtotal_real;
    EditText discount_p, discount_D;
    double discount;
    double discount_Bydollar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recyclerView = findViewById(R.id.recycler_CardList);
        txSubtotal = findViewById(R.id.SubTotal);
        txtotalAmount = findViewById(R.id.totalAmount);
        discount_p = findViewById(R.id.Discount_p);
        discount_D = findViewById(R.id.Discount_D);
        txSubtotal_real = findViewById(R.id.Subtotal_Real);


        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = userDatabase.userDAO();


        List<CardData> cardData = userDAO.getAllCard();
        Card_Adapter cardAdapter = new Card_Adapter(cardData, getApplicationContext(), txSubtotal, txtotalAmount, txSubtotal_real, discount_p);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cardAdapter);


        Spinner spinnercustomer = findViewById(R.id.Spinner_ShowCato);
        Spinner spinnerpayment = findViewById(R.id.Spinner_showPaymet);


        userDAO = UserDatabase.getUserDatabase(getApplicationContext()).userDAO();
        customerDataList = userDAO.getAllCustomer();
        select_Spinner_cust adptercust = new select_Spinner_cust(customerDataList, getApplicationContext());
        spinnercustomer.setAdapter(adptercust);


        paymentDataList = userDAO.getAllPayment();
        select_Spinner_payment adaperpaymnet = new select_Spinner_payment(paymentDataList, getApplicationContext());
        spinnerpayment.setAdapter(adaperpaymnet);


        binding.cardListAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardActivity.this, View_Customer.class);
                startActivity(intent);
            }
        });

        binding.cardListAddPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardActivity.this, Show_PaymentActivity.class);
                startActivity(intent);
            }
        });

        // binding.SubTotal.setText();

//        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sum = 0;
//                for (int i = 0; i<cardData.size(); i++){
//                    sum+= (cardData.get(i).getPro_cardQty() * cardData.get(i).getPro_cardQty());
//                }
//
//
//            }
//        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double sum = 0.0;
                int i;
                for (i = 0; i < cardData.size(); i++) {
                    sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
                }
                discount = 0;

                if (discount_p.getText().toString().isEmpty()) {
                    txSubtotal.setText("$" + numberFormat(String.valueOf(sum)));
                } else {
                    discount = Double.parseDouble(discount_p.getText().toString());
                    double discount_price = discount / 100;
                    double discountTotal = sum - (sum * discount_price);

                    txSubtotal.setText("$" + numberFormat(String.valueOf(discountTotal)));
                    txSubtotal_real.setText("R" + numberFormat(String.valueOf(discountTotal * 4100)));

                }

//                discount_Bydollar = 0;
//                if(discount_D.getText().toString().isEmpty()){
//                    txSubtotal.setText("$" + numberFormat(String.valueOf(sum)));
//                }else {
//                    discount_Bydollar = Double.parseDouble(discount_D.getText().toString());
//                    double discount_dollar = sum - discount_Bydollar;
//                    txSubtotal.setText("$" + numberFormat(String.valueOf(discount_dollar)));
//
//                }

            }
        });


        int i;
        double sum = 0.0;
        for (i = 0; i < cardData.size(); i++) {
            sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
        }

        txSubtotal.setText("$" + numberFormat(String.valueOf(sum)));
        txSubtotal_real.setText("R" + numberFormat(String.valueOf(sum * 4100)));
        txtotalAmount.setText("$" + numberFormat(String.valueOf(sum)));

    }


    public static String numberFormat(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        return decimalFormat.format(Double.parseDouble(number));
    }


}