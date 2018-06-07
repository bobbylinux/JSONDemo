package eptalab.net.jsondemo;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import eptalab.net.jsondemo.adapter.AttrazioniAdapter;
import eptalab.net.jsondemo.model.Attrazione;

public class MainActivity extends AppCompatActivity {


    ArrayList<Attrazione> attrazioni;
    AttrazioniAdapter attrazioniAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        attrazioni = new ArrayList<Attrazione>();
        ListView listView = findViewById(R.id.lst_attrazioni);

        DownloadTask task = new DownloadTask();

        task.execute("http://appacademy.it/book/attrazioni.json");

        attrazioniAdapter = new AttrazioniAdapter(getApplicationContext(), R.layout.item_attrazione, attrazioni);
        listView.setAdapter(attrazioniAdapter);
    }

    public class DownloadTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progressDialog = new ProgressDialog(MainActivity.this);
            this.progressDialog.setMessage("Caricamento. Attendere prego...");
            this.progressDialog.setTitle("Connessione al Server...");
            this.progressDialog.show();
            this.progressDialog.setCancelable(false);

        }

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data != -1) {
                    char cur = (char) data;
                    result += cur;
                    data = inputStreamReader.read();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Log.i("JSONDemo", result);

            try {
                JSONObject jsonObject = new JSONObject(result);

                String attractions = jsonObject.getString("attractions");

                //Log.i("JSONDemo", attractions);

                JSONArray jsonArray = new JSONArray(attractions);

                for (int index = 0; index < jsonArray.length(); index++) {
                    Attrazione attrazione = new Attrazione();
                    JSONObject jsonPart = jsonArray.getJSONObject(index);
                    String name = jsonPart.getString("name");
                    String type = jsonPart.getString("type");
                    String latitude = jsonPart.getString("latitude");
                    String longitude = jsonPart.getString("longitude");

                    attrazione.setName(name);
                    attrazione.setType(type);
                    attrazioni.add(attrazione);

                    Log.i("JSONDemo", name + " " + type + " " + latitude + " " + longitude);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.cancel();

            attrazioniAdapter.notifyDataSetChanged();
        }
    }
}
