package com.example.formularze;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText etWeight, etHeight;
    private Button btnCalculate;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        btnCalculate.setOnClickListener(v -> calculateBmi());
    }

    private void calculateBmi() {
        String weightStr = etWeight.getText().toString().trim();
        if (weightStr.isEmpty()) {
            etWeight.setError(getString(R.string.error_weight));
            etWeight.requestFocus();
            return;
        }

        String heightStr = etHeight.getText().toString().trim();
        if (heightStr.isEmpty()) {
            etHeight.setError(getString(R.string.error_height));
            etHeight.requestFocus();
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double heightCm = Double.parseDouble(heightStr);

        if (weight <= 0 || heightCm <= 0) {
            return;
        }

        double heightM = heightCm / 100.0;
        double bmi = weight / (heightM * heightM);

        String category = getBmiCategory(bmi);

        String resultText = String.format(Locale.US, getString(R.string.bmi_result), bmi, category);
        tvResult.setText(resultText);

        Snackbar.make(findViewById(R.id.main), R.string.bmi_calculated, Snackbar.LENGTH_LONG).show();
    }

    private String getBmiCategory(double bmi) {
        if (bmi < 18.5) {
            return getString(R.string.bmi_underweight);
        } else if (bmi < 25.0) {
            return getString(R.string.bmi_normal);
        } else if (bmi < 30.0) {
            return getString(R.string.bmi_overweight);
        } else {
            return getString(R.string.bmi_obese);
        }
    }
}