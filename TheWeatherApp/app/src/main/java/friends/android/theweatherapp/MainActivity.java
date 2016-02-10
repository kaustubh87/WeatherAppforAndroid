package friends.android.theweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.JSONWeatherParser;
import data.WeatherHTTPClient;
import model.Weather;


public class MainActivity extends ActionBarActivity {

    private TextView cityName, temp, description, humidity, pressure, wind, sunrise, sunset, updated;
    private ImageView iconView;
    Weather weather = new Weather();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityName = (TextView)findViewById(R.id.cityText);
        iconView = (ImageView)findViewById(R.id.icon);
        temp = (TextView)findViewById(R.id.tempText);
        description = (TextView)findViewById(R.id.cloudText);
        humidity = (TextView)findViewById(R.id.humidityText);
        pressure = (TextView)findViewById(R.id.pressureText);
        wind = (TextView)findViewById(R.id.windText);
        sunrise = (TextView)findViewById(R.id.riseText);
        sunset = (TextView)findViewById(R.id.setText);
        updated = (TextView)findViewById(R.id.updateText);
        renderWeatherData("Dallas,US");



    }

    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric&appid=b161615baf212d9c55c45892cd3d6e66"});


    }

    private class WeatherTask extends AsyncTask<String, Void, Weather>{
        @Override
        protected Weather doInBackground(String... strings) {
            String data = ((new WeatherHTTPClient()).getWeatherData(strings[0]));
            weather = JSONWeatherParser.getWeather(data);

            Log.v("Data: " ,weather.place.getCity());

            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            DateFormat dm = DateFormat.getTimeInstance();
            String sunriseDate = dm.format(new Date(weather.place.getSunrise()));
            String sunsetDate = dm.format(new Date(weather.place.getSunset()));
            String updateDate = dm.format(new Date(weather.place.getLastupdate()));

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());

            cityName.setText(weather.place.getCity() +", " +weather.place.getCountry());
            temp.setText(""+tempFormat+"C");
            humidity.setText("Humidity: " +weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " +weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " +weather.wind.getSpeed() +"mps");
            sunrise.setText("Sunrise: " +sunriseDate);
            sunset.setText("Sunset: " + sunsetDate);
            updated.setText("Updated: " +updateDate);
            description.setText("Condition: " +weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() +")");

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_cityId) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
