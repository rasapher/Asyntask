package com.example.asyntask;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity; // Use androidx package
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText time;
    private TextView finalResult;
    private ProgressBar progressBar; // Added ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        time = findViewById(R.id.in_time);
        button = findViewById(R.id.btn_run);
        finalResult = findViewById(R.id.tv_result);
        progressBar = findViewById(R.id.progressBar); // Initialize ProgressBar

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sleepTime = time.getText().toString();
                if (!sleepTime.isEmpty()) {
                    new AsyncTaskRunner().execute(sleepTime);
                } else {
                    finalResult.setText("Please enter a time.");
                }
            }
        });
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE); // Show ProgressBar
        }

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                int sleepTime = Integer.parseInt(params[0]) * 1000;
                Thread.sleep(sleepTime);
                return "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Sleep interrupted!";
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return "Invalid number format!";
            }
        }

        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE); // Hide ProgressBar
            finalResult.setText(result);
        }
    }
}
