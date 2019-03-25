package jcescudero15.tuenti.es.mytuentiapp.utils;

public interface BasePresenter<T> {
    void takeView(T view);

    void dropView();
}