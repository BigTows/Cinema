package ru.bigtows.util;

/**
 * Created by bigtows on 19/03/2017.
 */
public interface Observable {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
