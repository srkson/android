package com.example.test3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView labelRecipes;
    private ImageView slika;
    private ProgressDialog dijalogZaCekanje;
    private Button preuzmi;
    private static String url = "https://api.edamam.com/api/recipes/v2?type=public&q=potato%2C%20chicken%2C%20onion&app_id=b97ec164&app_key=a36ae9523c4c9b48a975d5c715491461&field=label&field=image";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        preuzmi = findViewById(R.id.buttonDownload);
        labelRecipes = findViewById(R.id.labelRecipes);
        labelRecipes.setMovementMethod(new ScrollingMovementMethod());
        slika = findViewById(R.id.slika);
        preuzmi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Api.getJSON(url, new ReadDataHandler(){
            @Override
            public void handleMessage(Message msg) {

                String odgovor = getJson();


                try {
                    JSONObject object = new JSONObject(odgovor);
                    JSONArray array = (JSONArray) object.get("hits");

                    //array = new JSONArray(odgovor);

                    LinkedList<Recipe> recipes = Recipe.parseJSONArray(array);

                    //labelRecipes.setText("ALL RECIPES: \n\n");
                    String prikaz = "ALL RECIPES: \n\n";

                    for (Recipe recipe: recipes){
                        prikaz += "[" + recipe.getName() + "]" + "\n\n" + recipe.getImgpath() + "\n\n";
                    }

                    labelRecipes.setText(prikaz);
                    new TaskPreuzimanjeSlike().execute(recipes.get(0).getImgpath());

                } catch (Exception e) {e.printStackTrace();}
            }
        });
    }

    public class TaskPreuzimanjeSlike extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... param) {
            try {
                java.net.URL url = new java.net.URL(param[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                input.close();
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dijalogZaCekanje = ProgressDialog.show(MainActivity.this, "cekanje...", "Slka se preuzima");
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            slika.setImageBitmap(result);
            dijalogZaCekanje.dismiss();
        }


    }
}