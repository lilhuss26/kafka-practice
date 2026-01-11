package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // First: Send the event
        AddEevnt eventor = new AddEevnt();
        eventor.sendEvent();

        // Second: Start consuming
        StoreEvent storer = new StoreEvent();
        storer.startConsuming();
    }
}