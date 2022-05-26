package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    Button button;
    TextView temperature, feelTemp, windSpeed, pressure, humidity, country, cityName, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperature = findViewById(R.id.temp);
        feelTemp = findViewById(R.id.feels);
        windSpeed = findViewById(R.id.wind);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        country  = findViewById(R.id.country);
        cityName = findViewById(R.id.city_nam);
        editText = findViewById(R.id.editCityName);
        description = findViewById(R.id.description);
        button = findViewById(R.id.searchButton);
        imageView = findViewById(R.id.imageView);

        button.setOnClickListener(view -> {

            editText.onEditorAction(EditorInfo.IME_ACTION_DONE); // close keyboard after entering the city name and clicking the button.
            new displayWeatherInfo().execute();
        });

    }

    @SuppressLint("StaticFieldLeak")
    public class displayWeatherInfo extends AsyncTask<Void, Void, Void> {
        String webContent; // Used to store all of the content from the url.
        @Override
        protected Void doInBackground(Void... params) {
            final String cityName = editText.getText().toString(); // the name of the city taken from the user.

            try {
                String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&appid=e53301e27efa0b66d05045d91b2742d3&units=imperial";
                // create the url
                URL url = new URL(urlString);
                // open the url stream, wrap it an a few "readers"
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

                // read the content into a single string
                webContent = reader.readLine();
                reader.close();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @SuppressLint({"ResourceType", "SetTextI18n"})
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           temperature.setText(getTemp(webContent) +"°F");
           feelTemp.setText( getFeelLikeTemp(webContent) +"°F");
           windSpeed.setText(getWindSpeed(webContent) +" mph");
           pressure.setText(getPressure(webContent) + " hPa");
           humidity.setText(getHumidity(webContent)+"%");
           country.setText(getCountry(webContent) + ":");
           cityName.setText(getName(webContent));

           String formattedDescription = toUpper(getDescription(webContent)); // format description
           description.setText(formattedDescription);

           String iconVal = getIcon(webContent); // Get the string designation of the icon.
           Picasso.get().load("http://openweathermap.org/img/wn/"+iconVal+"@2x.png").fit().into(imageView); // display in the imageview object


        }
    }

    public String toUpper(String description) {
        // Change every word of the description to start with a capital letter

        // stores each characters to a char array
        char[] arr = description.toCharArray();
        boolean space = true;

        for(int i = 0; i < arr.length; i++) {

            // if the array element is a letter
            if(Character.isLetter(arr[i])) {
                // check space is present before the letter
                if(space) {

                    // change the letter into uppercase
                    arr[i] = Character.toUpperCase(arr[i]);
                    space = false;
                }
            }

            else {
                // if the new character is not character
                space = true;
            }
        }

        // convert the char array to the string
         return String.valueOf(arr);
    }



    public String getTemp(String content) {
        // Read the contents of the url, and parse the temperature value from it. then return it as a double.
        // Use the jsoup library.

        String s = "temp" ;
        int index = content.indexOf(s);
        int index2 = index+6; // beginning of the number value for temperature.
        ArrayList<Character> arr = new ArrayList<>(); // to store the chars of the temperature value

        // add all of the relevant chars into the array.
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == ',' || content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}') // avoid adding these characters
                break;
            arr.add(content.charAt(index2+i)); // add to arraylist
        }
        // Convert the character array into a string.
        StringBuilder temp = new StringBuilder(arr.size());
        for(Character ch: arr){

            temp.append(ch); // Make a string using the characters of the arraylist.
        }
        return temp.toString();
    }





    public String getFeelLikeTemp(String content) {

        String s = "feels_like" ; // key word to look for
        int index = content.indexOf(s); // get the starting index of the key word

        int index2 = index+12; // The starting index of the value we want.
        ArrayList<Character> arr = new ArrayList<>(); // array list to store the chars of the value.


        // add all of the relevant chars into the array.
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == ',' || content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}')
                break;
            arr.add(content.charAt(index2+i));
        }
        // Convert the character array into a string
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character c: arr) {

            builder.append(c);
        }
        return builder.toString();
    }






    public String getWindSpeed(String content) {
        // Gets the windspeed value.
        String s = "speed"; // The key word to look for
        int index = content.indexOf(s); // Gets the starting index of the word "speed"

        int index2 = index+7; // Index of the starting value of speed.
        ArrayList<Character> arr = new ArrayList<>(); // character arraylist to store speed value.

        // add all of the relevant chars into the array.
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == ',' || content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}')
                break;
            arr.add(content.charAt(index2+i)); // add only the relevant values.
        }

        StringBuilder builder = new StringBuilder(arr.size()); // Convert the character array to a string.
        for(Character c: arr)
        {
            builder.append(c);
        }
        return builder.toString();
    }


    public String getHumidity (String content) {
        // Get the humidity value
        String s = "humidity"; // Key word to look for in content
        int index = content.indexOf(s); // The starting index of the word "humidity"
        int index2 = index+10; // Starting index of the value for humidity.
        ArrayList<Character> arr = new ArrayList<>(); // the character arraylist to store the chars of the humidity value.

        // add all of the relevant chars into the array.
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == '}' || content.charAt(index2+i) == ',' || content.charAt(index2+i) == '"')
                break;
            arr.add(content.charAt(index2+i));
        }

        // convert the character arraylist into a string.
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr) {

            builder.append(ch);
        }
        return builder.toString();
    }


    public String getCountry(String content) {
        // Gets the country initials of a city/state.

        String s = "country"; // Key word to look for in the string
        int index = content.indexOf(s); // Starting index of the word "country"
        int index2 = index+10; // Starting index of the initial.
        ArrayList<Character> arr = new ArrayList<>(); // character arraylist to store intitials.

        // Add the relevant characters into the arraylist
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == '"' || content.charAt(index2+i) == ',' || content.charAt(index2+i) == '}')
                break;
            arr.add(content.charAt(index2+i));
        }

        // Convert the character arraylist to a string.
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr){

            builder.append(ch);
        }
        return builder.toString();
    }




    public String getPressure(String content) {
        // Get pressure value.

        String s = "pressure"; // Key word to look for in the string
        int index = content.indexOf(s); // starting index of the word pressure
        int index2 = index+10; // starting index of the value of pressure.
        ArrayList<Character> arr = new ArrayList<>(); // arraylist to start the chars of the pressure.

        // Add the relevant characters into the arraylist
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == ',' || content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}')
                break;
            arr.add(content.charAt(index2+i));
        }

        //convert the character arraylist to a string
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr){

            builder.append(ch);
        }
        return builder.toString();
    }


    public String getName(String content) {
        // Gets the name of the city/state

        String s = "name"; // The key word to look for in the string
        int index = content.indexOf(s); // The starting index of the word "name"
        int index2 = index+7; // The starting index of the name of the city/state.
        ArrayList<Character> arr = new ArrayList<>(); // The arraylist to store the chars of the name value.

        // Add the chars to the array
        for(int i = 0; i < 30; i++) {

            if(content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}' || content.charAt(index2+i) == ',')
                break;
            arr.add(content.charAt(index2+i));
        }
        // convert the character arraylist to a string
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr){

            builder.append(ch);
        }
        return builder.toString();
    }

    public String getDescription(String content) {
        // Get the Weather description

        String s = "description"; // key word to look for in the string
        int index = content.indexOf(s); // the starting index of the word "description"
        int index2 = index+14; // starting index of the description itself
        ArrayList<Character> arr = new ArrayList<>(); // the character arraylist to store the description

        // Add the relevant characters into the arraylist
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}' || content.charAt(index2+i) == ',')
                break;
            arr.add(content.charAt(index2+i));
        }

        // Convert the character ArrayList into a string
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr){

            builder.append(ch);
        }
        return builder.toString();
    }


    public String getIcon(String content) {
        // Gets the icon code

        String s = "icon"; // the key word to look for
        int index = content.indexOf(s); // the starting index of the word "icon"
        int index2 = index+7; // the starting index of the code value
        ArrayList<Character> arr = new ArrayList<>(); // used to store the characters of the code.

        // loop to add the relevant characters into the arraylist
        for(int i = 0; i < 20; i++) {

            if(content.charAt(index2+i) == '"' || content.charAt(index2+i) == '}' || content.charAt(index2+i) == ',')
                break;
            arr.add(content.charAt(index2+i));
        }

        // convert the character array to a string
        StringBuilder builder = new StringBuilder(arr.size());
        for(Character ch: arr){

            builder.append(ch);
        }
        return builder.toString();
    }


}
