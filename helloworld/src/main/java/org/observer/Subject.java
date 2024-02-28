package org.observer;

/**
 * 观察者主体接口
 */
public interface Subject {

    public void registerObserver(Observer observer);

    public void deleteObserver(Observer observer);

    public void notifyAlls();
}
