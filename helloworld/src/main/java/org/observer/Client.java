package org.observer;

public class Client {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        Sina sina = new Sina();
        Sohu sohu = new Sohu();
        weatherData.registerObserver(sina);
        weatherData.registerObserver(sohu);
        weatherData.publish("30","200","100");
    }
}
