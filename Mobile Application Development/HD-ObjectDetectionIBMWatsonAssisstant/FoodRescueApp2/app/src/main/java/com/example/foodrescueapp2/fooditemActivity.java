package com.example.foodrescueapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrescueapp2.data.DatabaseHelper;
import com.example.foodrescueapp2.model.Cart;
import com.example.foodrescueapp2.model.Food;
import com.example.foodrescueapp2.util.PaymentsUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class fooditemActivity extends AppCompatActivity {
    List<Food> foodList= new ArrayList<>();
    Food food;

    ImageButton googlePayButton;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private PaymentsClient paymentsClient;
    double totalPrice;
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem);
        totalPrice=0;

        TextView title,description,date,time,quantity,location, price;
        ImageView imageView;


        title=findViewById(R.id.titleview);
        description=findViewById(R.id.descriptionview);
        date=findViewById(R.id.dateview);
        time=findViewById(R.id.timeview);
        quantity=findViewById(R.id.quantityview);
        location=findViewById(R.id.locationview);
        price=findViewById(R.id.priceView);

        imageView=findViewById(R.id.imageview);

        Intent intent=getIntent();
        int foodid=intent.getIntExtra("foodid", 1);
        DatabaseHelper db = new DatabaseHelper(fooditemActivity.this);
        Log.i("Food id",String.valueOf(foodid));
        foodList=db.fetchAllFoods();
        for(int i=0;i<foodList.size();i++)
        {
            Log.i(String.valueOf(i),String.valueOf(foodList.get(i).getFood_id()));
            if(foodList.get(i).getFood_id()==foodid)
            {
                Log.i("Comparison","Working");
                food=foodList.get(i);
            }
        }

        if(food!=null)
        {

            if(isNumeric(food.getPrice()))
            {
                totalPrice=Double.parseDouble(food.getPrice());
            }
            title.setText("Title: "+food.getTitle());
            description.setText("Description :"+food.getDescription());
            date.setText("Date: "+food.getDate());
            time.setText("Pick Up Time: "+food.getPickuptime());
            quantity.setText("Quantity: "+food.getQuantity());
            location.setText("Location: "+food.getLocation());
            price.setText("Price(AUD): "+food.getPrice());
            if(food.getImage()!=null)
            {
                String encodedImageString = Base64.encodeToString(food.getImage(), Base64.DEFAULT);
                byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
                Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                        bytarray.length);
                imageView.setImageBitmap(bmimage);

            }
        }
        Button addtocart=findViewById(R.id.addtocartbutton);
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(fooditemActivity.this);
                Log.i("Add to Cart Button check","Working");
                long result=db.insertCart(new Cart(food.getFood_id()));
                if (result > 0)
                {
                    Toast.makeText(fooditemActivity.this, "Added to Cart successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(fooditemActivity.this, "Add to Cart Unsuccessful!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        googlePayButton=findViewById(R.id.googlePayButton);
        //Step 8: Register event handler for user gesture

        googlePayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPayment(view);
                    }
                });

        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        possiblyShowGooglePayButton();
    }
    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        double foodPrice = totalPrice;
        long foodPriceCents = Math.round(foodPrice * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
        long priceCents = foodPriceCents;

        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

        // Since loadPaymentData may show the UI asking the user to select a payment method, we use
        // AutoResolveHelper to wait for the user interacting with it. Once completed,
        // onActivityResult will be called with the result.
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    }
    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String tokenizationType = tokenizationData.getString("type");
            final String token = tokenizationData.getString("token");

            if ("PAYMENT_GATEWAY".equals(tokenizationType) && "examplePaymentMethodToken".equals(token)) {
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage(getString(R.string.gateway_replace_name_example))
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.setClickable(true);
        }
    }
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Google Pay button is avilable", Toast.LENGTH_LONG).show();
        }
    }
    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }
}