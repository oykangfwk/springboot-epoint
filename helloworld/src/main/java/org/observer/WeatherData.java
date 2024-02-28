package org.observer;

import lombok.Data;

import java.util.ArrayList;

/**
 * 气象站
 */
@Data
public class WeatherData implements Subject{

    private String qiwen;
    private String qiya;
    private String shidu;

    private ArrayList<Observer> arrayList;


    public WeatherData(){
        arrayList = new ArrayList<Observer>();
    }

    public void publish(String qiwen,String qiya,String shidu){
        this.qiwen=qiwen;
        this.qiya=qiya;
        this.shidu=shidu;
        notifyAlls();
    }

    @Override
    public void registerObserver(Observer observer) {
        arrayList.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        arrayList.remove(observer);
    }

    @Override
    public void notifyAlls() {
        for(Observer observer:arrayList){
            observer.update(qiwen,qiya,shidu);
        }
    }
}
