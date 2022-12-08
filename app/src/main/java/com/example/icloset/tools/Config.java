package com.example.icloset.tools;

public class Config {

    public static String getWeatherTag(String weather){
        String tag = "";
        switch (weather){
            case "Windy":
                tag = "Windy";
                break;
            case "Rain":
                tag = "Rain";
                break;
            case "Snow":
                tag = "Snow";
                break;
            case "Clouds":
            case "Sunny":
                tag = "Sunny";
                break;
        }
        return tag;
    }

    public static String getTemperatureTag(String weather){
        String tag = "";
        switch (weather){
            case "Hot":
                tag = "Hot";
                break;
            case "Warm":
                tag = "Warm";
                break;
            case "Cold":
                tag = "Cold";
                break;
            case "Freeze":
                tag = "Freeze";
                break;
        }
        return tag;
    }

    public static String getTempTag(double temp){
        if(temp>=27){
            return "Hot";
        }else if(temp>=17){
            return "Warm";
        }else if(temp>=1){
            return "Cold";
        }else {
            return "Freeze";
        }
    }

}
