package com.example.formularze;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    private EditText etName, etEmail;
    private Button btnBirthDate, btnSubmit;
    private Spinner spCourse;
    private RadioGroup rgMode;
    private SeekBar sbLevel;
    private TextView tvLevelValue, tvSummary;
    private MaterialSwitch swNewsletter;
    private CheckBox cbTerms;

    private String birthDate = "2000-01-01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        btnBirthDate = findViewById(R.id.btnBirthDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        spCourse = findViewById(R.id.spCourse);
        rgMode = findViewById(R.id.rgMode);
        sbLevel = findViewById(R.id.sbLevel);
        tvLevelValue = findViewById(R.id.tvLevelValue);
        tvSummary = findViewById(R.id.tvSummary);
        swNewsletter = findViewById(R.id.swNewsletter);
        cbTerms = findViewById(R.id.cbTerms);

        setUpListeners();
        tvLevelValue.setText(String.valueOf(sbLevel.getProgress()));
    }

    private void setUpListeners() {
        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) ->
                btnSubmit.setEnabled(isChecked));

        sbLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLevelValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private void submitForm() {
        tvSummary.setText(buildSummary());
    }

    private String buildSummary() {
        String mode = rgMode.getCheckedRadioButtonId() == R.id.rbOnsite
                ? getString(R.string.mode_onsite)
                : getString(R.string.mode_remote);

        String newsletter = swNewsletter.isChecked()
                ? getString(R.string.yes_short)
                : getString(R.string.no_short);

        String courseText = spCourse.getSelectedItem() != null
                ? spCourse.getSelectedItem().toString()
                : "";

        return getString(R.string.summary_format,
                etName.getText().toString().trim(),
                etEmail.getText().toString().trim(),
                birthDate,
                courseText,
                mode,
                sbLevel.getProgress(),
                newsletter);
    }
}