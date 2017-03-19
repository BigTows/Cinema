package ru.bigtows.util;

import java.util.List;

/**
 * Created by bigtows on 19/03/2017.
 */
public class GeneralObservabel implements Observable{
    private List<Observer> observers;
    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers)
            observer.update();
    }
}
