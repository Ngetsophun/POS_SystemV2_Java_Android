package com.example.systempos.Card;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.systempos.Customer.CustomerData;
import com.example.systempos.Customer.View_Customer;
import com.example.systempos.Invoice.Invoiceitems;
import com.example.systempos.Payment.PaymentData;
import com.example.systempos.Payment.Show_PaymentActivity;
import com.example.systempos.Product.ProductData;
import com.example.systempos.R;
import com.example.systempos.dao.UserDAO;
import com.example.systempos.database.UserDatabase;
import com.example.systempos.databinding.ActivityCardBinding;
import com.example.systempos.model.CatogoryData;

import java.text.DecimalFormat;
import java.util.List;

public class CardActivity extends AppCompatActivity {

    ActivityCardBinding binding;
    UserDatabase userDatabase;
    UserDAO userDAO;

    List<ProductData> productData;

    List<CustomerData> customerDataList;
    List<PaymentData> paymentDataList;
    RecyclerView recyclerView;

    TextView txSubtotal, txtotalAmount, txSubtotal_real,txDiscount_R,txDiscount_D,txCardTax;

    EditText discount_p;
    double discount;


    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        recyclerView = findViewById(R.id.recycler_CardList);
        txSubtotal = findViewById(R.id.SubTotal);

        discount_p = findViewById(R.id.Discount_p);

        txSubtotal_real = findViewById(R.id.Subtotal_Real);

        txDiscount_D = findViewById(R.id.Card_Discount_D);
        txDiscount_R = findViewById(R.id.Card_Discount_R);
        txCardTax = findViewById(R.id.card_tax_D);




        //dialog to print

        dialog = new Dialog(CardActivity.this);
        dialog.setContentView(R.layout.costom_dialog_save);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.back_ground_dialog_save));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);


        Button btncash  = dialog.findViewById(R.id.btncash);
        Button btncancel = dialog.findViewById(R.id.btncancel);


        TextView dialog_real = dialog.findViewById(R.id.dialog_Subtotal_Real);
        TextView dialog_dollar = dialog.findViewById(R.id.dialog_Subtotal_Dollar);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CardActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        btncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(CardActivity.this,Invoiceitems.class);
               startActivity(intent);


            }
        });




        //

        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "bluedb")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        userDAO = userDatabase.userDAO();


        List<ProductData> productData1 = userDAO.getAllProduct();

        List<CardData> cardData = userDAO.getAllCard();
        Card_Adapter cardAdapter = new Card_Adapter(cardData,productData1,getApplicationContext(), txSubtotal, txtotalAmount, txSubtotal_real,txCardTax, discount_p);
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
                    txDiscount_D.setText("$" + numberFormat(String.valueOf(sum)));

                } else {
                    discount = Double.parseDouble(discount_p.getText().toString());
                    double discount_price = discount / 100;
                    double discountTotal = sum - (sum * discount_price);

                    txDiscount_D.setText("$" + numberFormat(String.valueOf(discountTotal)));
                    txDiscount_R.setText("R" + numberFormat(String.valueOf(discountTotal * 4100)));

                    dialog_real.setText("R" + numberFormat(String.valueOf(discountTotal)));
                    dialog_dollar.setText("R" + numberFormat(String.valueOf(discountTotal * 4100)));



                }
                dialog.show();
            }

        });

//        int i,k;
//        double sum = 0.0;
//        double sumTax = 0.0;
//        for (i = 0; i < cardData.size(); i++) {
//            sum = (sum + (cardData.get(i).getPro_cardPrice() * cardData.get(i).getPro_cardQty()));
//            sumTax = (sumTax + (productData1.get(i).getProtax()));
//        }

//
//        txSubtotal.setText("$" + numberFormat(String.valueOf(sum)));
//        txSubtotal_real.setText("R" + numberFormat(String.valueOf(sum * 4100)));
//
//        txCardTax.setText("$" + numberFormat(String.valueOf(sumTax)));
//
//        txDiscount_D.setText("$" + numberFormat(String.valueOf(sum)));
//        txDiscount_R.setText("R" + numberFormat(String.valueOf(sum * 4100)));
//
//        dialog_real.setText("R" + numberFormat(String.valueOf(sum)));
//        dialog_dollar.setText("R" + numberFormat(String.valueOf(sum * 4100)));



    }

//    public  void CheckQty(){
//       List<ProductData> productData1;
//
//        double sub;
//        for(int i = 0; i< productData.size(); i++){
//            sub  = productData.get(i).getPro_cardQty() - productData.get(i).getProqty();
//        }
//    }


    public static String numberFormat(String number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        return decimalFormat.format(Double.parseDouble(number));
    }


}