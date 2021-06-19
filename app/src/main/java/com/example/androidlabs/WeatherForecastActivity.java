package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecastActivity extends AppCompatActivity {
    TextView currTV, minTV, maxTV, uvRate;
    ImageView wxIcon;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);

        // Define fields that need to be updated
        currTV = findViewById(R.id.tvCurrTemp);
        minTV = findViewById(R.id.tvMinTemp);
        maxTV = findViewById(R.id.tvMaxTemp);
        uvRate = findViewById(R.id.tvUVRating);
        wxIcon = findViewById(R.id.ivWeather);

        // Open AsyncTask
        String tempURL = "http://api.openweathermap.org/data/2.5/weather?q=carp,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";
        String uvURL = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
        ForecastQuery forecast = new ForecastQuery();
        forecast.execute(tempURL, uvURL);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String>{
        String currTemp, minTemp, maxTemp, UVRating;
        Bitmap wxPic;

        @Override
        protected String doInBackground(String... args) {
            String wxPicName;

            try {
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {

                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <Weather> start tag
                            currTemp = xpp.getAttributeValue(null, "value") + " \u2103";
                            Log.i("ForecastQuery: ", "current temperature is " + currTemp);
                            publishProgress(25);
                            minTemp = "Low: " + xpp.getAttributeValue(null, "min")
                                    + " \u2103";
                            Log.i("ForecastQuery: ", "minimum temperature is " + minTemp);
                            publishProgress(50);
                            maxTemp = "High: " + xpp.getAttributeValue(null, "max")
                                    + " \u2103";
                            Log.i("ForecastQuery: ", "maximum temperature is " + maxTemp);
                            publishProgress(75);
                        } else if(xpp.getName().equals("weather")){
                            // Get icon file name
                            wxPicName = xpp.getAttributeValue(null,"icon");
                            String iconURL = "http://openweathermap.org/img/w/" + wxPicName + ".png";
                            String imagePath = wxPicName + ".png";
                            Log.i("ForecastQuery: ", "Looking for " + imagePath);

                            // Check if file already exists
                            if (fileExists(imagePath)){
                                // Load from computer
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(imagePath);
                                }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                wxPic = BitmapFactory.decodeStream(fis);
                                Log.i("ForecastQuery: ", "Image loaded locally.");
                            } else{
                                // Download
                                URL icoUrl = new URL(iconURL);
                                HttpURLConnection icoConnection = (HttpURLConnection) icoUrl.openConnection();
                                icoConnection.connect();
                                int responseCode = icoConnection.getResponseCode();
                                if (responseCode == 200) {
                                    wxPic = BitmapFactory.decodeStream(icoConnection.getInputStream());
                                }
                                // Store
                                FileOutputStream outputStream = openFileOutput( imagePath, Context.MODE_PRIVATE);
                                wxPic.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                Log.i("ForecastQuery: ", "Image downloaded.");
                                outputStream.flush();
                                outputStream.close();
                            }
                            publishProgress(100);
                        } // end weather tag
                    }
                    eventType = xpp.next(); //move to the next xml event
                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            } // end first try block

            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[1]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                // convert string to JSON:
                JSONObject uvReport = new JSONObject(result);

                //get the double associated with "value"
                UVRating = "UV Index: " + (uvReport.getDouble("value"));

                Log.i("MainActivity", "The uv is now: " + UVRating) ;

            }
            catch (Exception e) {
                Log.e("Error", e.getMessage());
            }


            return "Done";
        }

        // Check if image already exists in my storage
        public boolean fileExists(String imageFile){
            File file = getBaseContext().getFileStreamPath(imageFile);
            return file.exists();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            currTV.setText(currTemp);
            minTV.setText(minTemp);
            maxTV.setText(maxTemp);
            wxIcon.setImageBitmap(wxPic);
            uvRate.setText(UVRating);
            pb.setVisibility(View.INVISIBLE);
        }
    }
}