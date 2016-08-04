package com.androidcamp.jobbies;


import android.icu.util.Currency;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class AddNewJobFragment extends Fragment {

    private static final int DEFAULT_PAYMENT = 1;

    private JobDescription jobDescription;
    private LinearLayout paymentView;
    private EditText amountEditText;
    private int providedAmount;


    public AddNewJobFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_new_job, container, false);

        jobDescription = new JobDescription();

        populateCategorySpinner(v);
        initializePaymentView(v);

        return v;
    }

    private void initializePaymentView(View v) {
        populateCurrencySpinner(v);

        paymentView = (LinearLayout) v.findViewById(R.id.paymentDetailsView);
        amountEditText = (EditText) paymentView.findViewById(R.id.amountEditText);
        amountEditText.setText(Integer.toString(DEFAULT_PAYMENT));

        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.paymentRadioGroup);
        radioGroup.check(R.id.paidJobRadioButton);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.paidJobRadioButton) {
                    enablePaymentDetailsView(true);
                }
                else {
                    enablePaymentDetailsView(false);
                }
            }
        });
    }

    private void enablePaymentDetailsView(boolean enable) {
        for (int c = 0; c < paymentView.getChildCount(); c++) {
            View child = paymentView.getChildAt(c);
            child.setEnabled(enable);
        }

        if (!jobDescription.isVoluntary()) {
            providedAmount = Integer.parseInt(amountEditText.getText().toString());
        }

        jobDescription.setVoluntary(!enable);

        amountEditText.setText(Integer.toString(enable ? providedAmount : 0));
    }

    private void populateCategorySpinner(View v) {
        Spinner categorySpinner = (Spinner) v.findViewById(R.id.category_spinner);

        ArrayAdapter<String> adapter= new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, JobCategory.getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void populateCurrencySpinner(View v) {
        Spinner currencySpinner = (Spinner) v.findViewById(R.id.currency_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, Payment.getAvailableCurrencies());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }
}
