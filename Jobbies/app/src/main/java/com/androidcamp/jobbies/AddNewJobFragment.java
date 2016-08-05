package com.androidcamp.jobbies;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

/**
 * Created by Karolina Pawlikowska on 8/4/16.
 */
public class AddNewJobFragment extends Fragment {

    private static final String SUCCESS = "SUCCESS";
    private static final int DEFAULT_PAYMENT = 1;

    private int providedAmount;
    private int year, month, day, hours, minutes;

    private Spinner categorySpinner;
    private Spinner currencySpinner;
    private RadioGroup paymentOptionsGroup;
    private EditText amountEditText;
    private EditText titleEditText;
    private EditText locationEditText;
    private EditText descriptionEditText;
    private Date date;


    public AddNewJobFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_new_job, container, false);

        populateCategorySpinner(v);
        initializePaymentView(v);
        initializeDatePicker(v);
        setAddJobButtonclickedListener(v);

        titleEditText = (EditText) v.findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText) v.findViewById(R.id.description_edit_text);
        locationEditText = (EditText) v.findViewById(R.id.location_edit_text);

        return v;
    }

    private void setAddJobButtonclickedListener(View v) {
        FloatingActionButton addJobButton = (FloatingActionButton)
                v.findViewById(R.id.add_job_button);

        addJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String validationResult = null;
                if ((validationResult = validate()).equals(SUCCESS)) {
                    DatabaseProvider databaseProvider = new DatabaseProvider();
                    databaseProvider.addOffer(getJobDescription());
                    // go to detail view of your offer
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(validationResult)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // do nothing
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    private String validate() {

        if (titleEditText.getText().toString().isEmpty()) {
            return getString(R.string.validation_error_title);
        }
        if (locationEditText.getText().toString().isEmpty()) {
            return getString(R.string.validation_error_location);
        }
        if (descriptionEditText.getText().toString().isEmpty()) {
            return getString(R.string.validation_error_description);
        }
        if (categorySpinner.getSelectedItemPosition() == 0) {
            return getString(R.string.validation_error_category);
        }
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.before(new Date())) {
            return getString(R.string.validation_error_date);
        }

        return SUCCESS;
    }

    private JobDescription getJobDescription() {
        JobDescription jobDescription = new JobDescription();
        jobDescription.setTitle(titleEditText.getText().toString());
        jobDescription.setAddress_str(locationEditText.getText().toString());
        jobDescription.setDescription(descriptionEditText.getText().toString());
        jobDescription.setCategory(categorySpinner.getSelectedItem().toString());
        jobDescription.setDate(date);
        jobDescription.setIsVoluntary(paymentOptionsGroup.getCheckedRadioButtonId() ==
                R.id.voluntary_job_radio_button);
        jobDescription.setPayment(jobDescription.getIsVoluntary() ? null :
                new Payment(Integer.parseInt(amountEditText.getText().toString()),
                        Currency.getInstance(currencySpinner.getSelectedItem().toString())));

        return jobDescription;
    }

    private void initializeDatePicker(View v) {

        final Calendar calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR) + 1;
        minutes = 0;

        calendar.set(Calendar.HOUR, hours);
        calendar.set(Calendar.MINUTE, minutes);

        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm a");

        final String fullDate = dateFormat.format(calendar.getTime());

        final TextView dateTextView = (TextView) v.findViewById(R.id.datetime_text_view);
        dateTextView.setText(fullDate);

        final TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hours = i;
                        minutes = i1;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, day);
                        calendar.set(Calendar.HOUR, hours);
                        calendar.set(Calendar.MINUTE, minutes);
                        dateTextView.setText(dateFormat.format(calendar.getTime()));
                        date = calendar.getTime();
                    }
                }, hours, minutes, false);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
                timePickerDialog.show();
            }
        }, year, month, day);

        Button changeDateAndTimeButton = (Button) v.findViewById(R.id.datetime_changer_button);
        changeDateAndTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

    }

    private void initializePaymentView(View v) {
        populateCurrencySpinner(v);

        final LinearLayout paymentView = (LinearLayout) v.findViewById(R.id.payment_details_view);
        amountEditText = (EditText) paymentView.findViewById(R.id.amount_edit_text);
        amountEditText.setText(Integer.toString(DEFAULT_PAYMENT));

        paymentOptionsGroup = (RadioGroup) v.findViewById(R.id.payment_radio_group);
        paymentOptionsGroup.check(R.id.paid_job_radio_button);
        paymentOptionsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.paid_job_radio_button) {
                    enablePaymentDetailsView(paymentView, amountEditText, true);
                }
                else {
                    enablePaymentDetailsView(paymentView, amountEditText, false);
                }
            }
        });
    }

    private void enablePaymentDetailsView(LinearLayout paymentView,
                                          EditText amountEditText, boolean enable) {
        for (int c = 0; c < paymentView.getChildCount(); c++) {
            View child = paymentView.getChildAt(c);
            child.setEnabled(enable);
        }

        if (enable) {
            providedAmount = Integer.parseInt(amountEditText.getText().toString());
        }

        amountEditText.setText(Integer.toString(enable ? providedAmount : 0));
    }

    private void populateCategorySpinner(View v) {
        categorySpinner = (Spinner) v.findViewById(R.id.category_spinner);

        ArrayAdapter<String> adapter= new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, JobCategory.getCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
    }

    private void populateCurrencySpinner(View v) {
        currencySpinner = (Spinner) v.findViewById(R.id.currency_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, Payment.getAvailableCurrencies());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);
    }
}
