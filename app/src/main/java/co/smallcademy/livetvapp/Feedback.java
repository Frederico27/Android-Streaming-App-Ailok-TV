package co.smallcademy.livetvapp;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.widget.RadioGroup;
import android.widget.Toast;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Feedback extends AppCompatActivity {
    EditText mensajen;
    Button manda;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);
        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mensajen = (EditText) findViewById(R.id.editTextFeedback);
        manda = (Button) findViewById(R.id.buttonSubmit);

        radioGroup = findViewById(R.id.radioGroupType);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            manda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mensajen.getText().toString();
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if ((selectedRadioButtonId != -1) && (!message.isEmpty())) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    String selectedValue = selectedRadioButton.getText().toString();
                    // Mengirim pesan ke bot Telegram
                    String token = "API_KEY
                    String chatId = "@ailoktv";
                    new SendMessageTask().execute(token, chatId, message, selectedValue);
                    Toast.makeText(Feedback.this, "Susessu manda Ita-bo'ot nia mensajen", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Feedback.this, "Halo Favor Prenxe kompletu", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class SendMessageTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            String token = params[0];
            String chatId = params[1];
            String message = params[2];
            String selectedValue = params[3];

            try {
                message = URLEncoder.encode(message, "UTF-8");
                selectedValue = URLEncoder.encode(selectedValue, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String url = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + chatId + "&text= NOTIFIKASAUN IMPORTANTE:" +"%0A%0A"+ "Tipu Mensajen: " + selectedValue + "%0A%0A" + "Mensajen: " + message;


            try {
                // Mengirim HTTP GET request ke URL bot Telegram
                // Menggunakan library yang sesuai, misalnya OkHttp atau HttpURLConnection
                // Di sini, contoh menggunakan OkHttp
                okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                // Lakukan sesuatu dengan respons yang diterima (jika diperlukan)
                System.out.println(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
