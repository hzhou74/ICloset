package com.example.icloset.bean;

public class HistoryBean {
    String cloth;
    String trousers;
    String shoes;
    String weather;
    double temp;

    public HistoryBean(String cloth, String trousers, String shoes) {
        this.cloth = cloth;
        this.trousers = trousers;
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
        return cloth;
    }

    public void setCloth(String cloth) {
        this.cloth = cloth;
    }

    public String getTrousers() {
        return trousers;
    }

    public void setTrousers(String trousers) {
        this.trousers = trousers;
    }

    public String getShoes() {
        return shoes;
    }

    public void setShoes(String shoes) {
        this.shoes = shoes;
    }

}
