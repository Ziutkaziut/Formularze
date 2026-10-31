package com.example.formularze;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Spinner spService;
    private Button btnDate, btnBook, btnClear;
    private EditText etTime, etContact;
    private CheckBox cbSms, cbTerms;
    private TextView tvSummary;

    private String selectedDate = "";

    private String previousService = "";
    private String previousDate = "";
    private String previousTime = "";
    private String previousContact = "";
    private boolean previousSms = false;
    private boolean previousTerms = false;
    private String previousSummary = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spService = findViewById(R.id.spService);
        btnDate = findViewById(R.id.btnDate);
        btnBook = findViewById(R.id.btnBook);
        btnClear = findViewById(R.id.btnClear);
        etTime = findViewById(R.id.etTime);
        etContact = findViewById(R.id.etContact);
        cbSms = findViewById(R.id.cbSms);
        cbTerms = findViewById(R.id.cbTerms);
        tvSummary = findViewById(R.id.tvSummary);

        setupServiceSpinner();
        setupListeners();
    }

    private void setupServiceSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.services, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spService.setAdapter(adapter);
    }

    private void setupListeners() {
        cbTerms.setOnCheckedChangeListener((buttonView, isChecked) ->
                btnBook.setEnabled(isChecked));

        btnDate.setOnClickListener(v -> showDatePicker());

        btnBook.setOnClickListener(v -> {
            if (validateForm()) {
                showConfirmationDialog();
            }
        });

        btnClear.setOnClickListener(v -> confirmClearForm());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    btnDate.setText(selectedDate);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private boolean validateForm() {
        if (spService.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.error_service, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, R.string.error_service, Toast.LENGTH_SHORT).show();
            return false;
        }

        String time = etTime.getText().toString().trim();
        if (time.isEmpty()) {
            etTime.setError(getString(R.string.error_time));
            etTime.requestFocus();
            return false;
        }

        String contact = etContact.getText().toString().trim();
        if (contact.isEmpty()) {
            etContact.setError(getString(R.string.error_contact));
            etContact.requestFocus();
            return false;
        }

        return true;
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_msg)
                .setPositiveButton(R.string.yes_book, (dialog, which) -> executeBooking())
                .setNegativeButton(R.string.no_book, null)
                .show();
    }

    private void executeBooking() {
        String summary = getString(R.string.summary_visit,
                spService.getSelectedItem().toString(),
                selectedDate,
                etTime.getText().toString().trim(),
                etContact.getText().toString().trim(),
                cbSms.isChecked() ? "tak" : "nie");

        tvSummary.setText(summary);

        Snackbar.make(findViewById(R.id.main), R.string.booked_msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo_action, v -> tvSummary.setText(""))
                .show();
    }

    private void confirmClearForm() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.clear_title)
                .setMessage(R.string.clear_msg)
                .setPositiveButton("Tak", (dialog, which) -> clearFormWithUndo())
                .setNegativeButton("Nie", null)
                .show();
    }

    private void clearFormWithUndo() {
        previousService = spService.getSelectedItem() != null ? spService.getSelectedItem().toString() : "";
        previousDate = selectedDate;
        previousTime = etTime.getText().toString();
        previousContact = etContact.getText().toString();
        previousSms = cbSms.isChecked();
        previousTerms = cbTerms.isChecked();
        previousSummary = tvSummary.getText().toString();

        spService.setSelection(0);
        selectedDate = "";
        btnDate.setText(R.string.btn_date);
        etTime.setText("");
        etContact.setText("");
        cbSms.setChecked(false);
        cbTerms.setChecked(false);
        tvSummary.setText("");

        etTime.setError(null);
        etContact.setError(null);

        Snackbar.make(findViewById(R.id.main), R.string.cleared_msg, Snackbar.LENGTH_LONG)
                .setAction("Cofnij", v -> restoreForm())
                .show();
    }

    private void restoreForm() {
        if (!previousService.isEmpty()) {
            ArrayAdapter adapter = (ArrayAdapter) spService.getAdapter();
            int position = adapter.getPosition(previousService);
            if (position >= 0) {
                spService.setSelection(position);
            }
        }
        selectedDate = previousDate;
        if (!selectedDate.isEmpty()) {
            btnDate.setText(selectedDate);
        }
        etTime.setText(previousTime);
        etContact.setText(previousContact);
        cbSms.setChecked(previousSms);
        cbTerms.setChecked(previousTerms);
        tvSummary.setText(previousSummary);
    }
}