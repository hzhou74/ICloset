package com.example.icloset.bean;

public class HistoryBean {
    String top;
    String bottom;
    String shoes;
    String weather;
    double temp;

    public HistoryBean(String top, String bottom, String shoes) {
        this.top = top;
        this.bottom = bottom;
        this.shoes = shoes;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getCloth() {
        return top;
    }

    public void setCloth(String top) {
        this.top = top;
    }

    public String getBottom() {
        return bottom;
    }

    public void setBottom(String bottom) {
        this.bottom = bottom;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

}
