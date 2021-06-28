package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updatecontainers.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that contains game state client side
 */
public class ClientModelState implements ClientModelUpdaterInterface, ClientStateViewer {

  private String clientUsername;
  private SimpleGameState simpleGameState;
  private LinkedHashMap<String, SimplePlayerState> simplePlayerStateMap;

  /**
   * Constructor for ClientModelState class
   */
  public ClientModelState() {
    this.simpleGameState = new SimpleGameState();
    this.simplePlayerStateMap = new LinkedHashMap<>();
  }

  @Override
  public void setClientUsername(String clientUsername) {
    this.clientUsername = clientUsername;
  }

  @Override
  public void chestUpdate(String username, ChestUpdate stateUpdate) {
    getSimplePlayerState(username).chestUpdate(stateUpdate);
  }

  @Override
  public void warehouseUpdate(String username, WarehouseUpdate stateUpdate) {
    getSimplePlayerState(username).warehouseUpdate(stateUpdate);
  }

  @Override
  public void leaderUpdate(String username, LeaderUpdate stateUpdate) {
    getSimplePlayerState(username).activatedLeaderUpdate(stateUpdate);
  }

  @Override
  public void leaderSetup(String username, LeaderSetup stateUpdate) {
        SimplePlayerState playerState = new SimplePlayerState();
        this.simplePlayerStateMap.put(username, playerState);
        playerState.setupLeaderCard(stateUpdate);
  }

  @Override
  public void marketUpdate(MarketUpdate stateUpdate) {
    simpleGameState.updateMarket(stateUpdate);
  }

  @Override
  public void marketSetup(MarketSetup stateUpdate) {
    simpleGameState.constructMarket(stateUpdate);
  }

  @Override
  public void cardSlotUpdate(String username, CardSlotUpdate stateUpdate) {
    getSimplePlayerState(username).cardSlotUpdate(stateUpdate);
  }

  @Override
  public void trackUpdate(String username, TrackUpdate stateUpdate) {
    getSimplePlayerState(username).trackUpdate(stateUpdate);
  }

  @Override
  public void vaticanUpdate(String username, VaticanReport stateUpdate) {
    getSimplePlayerState(username).vaticanReportUpdate(stateUpdate);
  }

  @Override
  public void devDeckUpdate(DevelopCardDeckUpdate stateUpdate) {
    simpleGameState.updateDeck(stateUpdate);
  }

  @Override
  public void devDeckSetup(DevelopCardDeckSetup stateUpdate) {
    simpleGameState.constructDeck(stateUpdate);
  }

  @Override
  public void tempChestUpdate(String username, ChestUpdate stateUpdate) {
    getSimplePlayerState(username).tempChestUpdate(stateUpdate);
  }

  @Override
  public void chestMergeUpdate(String username) {
    getSimplePlayerState(username).mergeTempChest(); //merge chest of the "old" currentPlayer if that current changes
  }

  @Override
  public void discardedLeaderUpdate(String username, LeaderUpdate stateUpdate) {
    int i=0;
    for(Integer id : getSimplePlayerState(username).getNotActiveLeaderCards())
      if(stateUpdate.getCardId() == id)
        getSimplePlayerState(username).discardLeader(i);
      else
        i++;
  }

  @Override
  public void lorenzoTrackUpdate(TrackUpdate stateUpdate) {
    int move = stateUpdate.getPlayerPosition();
    this.simpleGameState.updateLorenzoPosition(move);
  }

  @Override
  public void lorenzoShuffleUpdate() {/*does nothing*/}

  @Override
  public void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate) { this.simpleGameState.updateDeck(stateUpdate); }

  @Override
  public void gameStartedSetup(List<String> players) {
    SimplePlayerState currentPlayerState = getSimplePlayerState();
    this.simplePlayerStateMap = new LinkedHashMap<>();

    for(String s : players) {
      if (s.equals(this.clientUsername))
        this.simplePlayerStateMap.put(s, currentPlayerState);
      else
        this.simplePlayerStateMap.put(s, new SimplePlayerState()); //the array is ordered to give the right amount of resources to each player
    }
  }

  @Override
  public SimplePlayerState getSimplePlayerState() {
    return this.simplePlayerStateMap.get(this.clientUsername);
  }

  @Override
  public SimplePlayerState getSimplePlayerState(String username) {
    return this.simplePlayerStateMap.get(username);
  }

  @Override
  public List<String> otherPlayersUsername(){
    return simplePlayerStateMap.entrySet().stream()
            .filter(x -> !x.getKey().equals(this.clientUsername))
            .map(x -> x.getKey())
            .collect(Collectors.toList());
  }

  @Override
  public List<String> usernameList(){
    return new ArrayList<>(this.simplePlayerStateMap.keySet());
  }

  @Override
  public SimpleGameState getSimpleGameState() {
    return simpleGameState;
  }

  @Override
  public int getPlayerTurnPosition(){
    int i = 1;
    for(Map.Entry<String, SimplePlayerState> entry : simplePlayerStateMap.entrySet()) {
      if(entry.getKey().equals(this.clientUsername))
        return i;
      i++;
    }
    return -1;
  }

  @Override
  public String getUsername() {
    return this.clientUsername;
  }
}