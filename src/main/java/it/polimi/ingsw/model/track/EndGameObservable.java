package it.polimi.ingsw.model.track;
import it.polimi.ingsw.controller.EndGameObserver;

public interface EndGameObservable {
  public void addToEndGameObserverList(EndGameObserver observerToAdd);
  public void removeFromEndGameObserverList(EndGameObserver observerToRemove);
  void notifyForEndGame();
}
