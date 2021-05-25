package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.view.gui.ViewObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class ViewObservable {

    private static final List<ViewObserver> viewObservers = new ArrayList<>();

    /**
     * Adds an observer.
     *
     * @param observer the observer to be added.
     */
    public static void addObserver(ViewObserver observer) {
        viewObservers.add(observer);
    }

    protected static void notifySceneObserver(Message message) {
        for(ViewObserver observer : viewObservers) {
            observer.sendMessage(message);
        }
    }

    protected static void notifySetSocket(String ip, int port) {
        for(ViewObserver observer : viewObservers) {
            observer.setServerIP(ip);
            observer.setServerPort(port);
            observer.connectToServer();
        }
    }
}
