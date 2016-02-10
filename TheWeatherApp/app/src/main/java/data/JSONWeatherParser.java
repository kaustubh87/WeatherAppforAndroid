package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import util.Utils;

/**
 * Created by Kaustubh on 2/9/2016.
 */
public class JSONWeatherParser {

    public static Weather getWeather(String data){
        //create JSONObject from data
        Weather weather = new Weather();

        try {
            JSONObject jsonObject = new JSONObject(data);
            Place place = new Place();
            JSONObject coorObj = Utils.getObject("coord",jsonObject);
            place.setLat(Utils.getFloat("lat",coorObj));
            place.setLon(Utils.getFloat("lon",coorObj));

            //Get the sys obj

            JSONObject sysObject = Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObject));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObject));
            place.setSunset(Utils.getInt("sunset",sysObject));
            place.setCity(Utils.getString("name",jsonObject));

            weather.place = place;

            //weather is an Array Object

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setWeatherId(Utils.getInt("id",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description",jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("main",jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            JSONObject mainObj = Utils.getObject("main" ,jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity",mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure",mainObj));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min",mainObj));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max",mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp",mainObj));

            JSONObject windObject = Utils.getObject("wind",jsonObject);
            weather.wind.setSpeed(Utils.getFloat("speed",windObject));
            weather.wind.setDeg(Utils.getFloat("deg",windObject));

            JSONObject cloudObj = Utils.getObject("clouds" ,jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            return weather;

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return null;

    }
}
