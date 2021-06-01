package it.polimi.ingsw.view;

import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.utility.GSON;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientModelState implements ClientModelUpdaterInterface, ClientStateViewer{

  private String clientUsername;
  private SimpleGameState simpleGameState;
  private LinkedHashMap<String, SimplePlayerState> simplePlayerStateMap;

  public ClientModelState() {
    this.simpleGameState = new SimpleGameState();
    this.simplePlayerStateMap = new LinkedHashMap<>();
  }

  @Override
  public void setClientUsername(String clientUsername) {
    this.clientUsername = clientUsername;
  }

  @Override
  public void chestUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).chestUpdate(stateUpdate);
  }

  @Override
  public void warehouseUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).warehouseUpdate(stateUpdate);
  }

  @Override
  public void leaderUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).activatedLeaderUpdate(stateUpdate);
  }

  @Override
  public void leaderSetup(String username, String stateUpdate) {
        SimplePlayerState playerState = new SimplePlayerState();
        this.simplePlayerStateMap.put(username, playerState);
        playerState.setupLeaderCard(stateUpdate);
  }

  @Override
  public void marketUpdate(String stateUpdate) {
    simpleGameState.updateMarket(stateUpdate);
  }

  @Override
  public void marketSetup(String stateUpdate) {
    simpleGameState.constructMarket(stateUpdate);
  }

  @Override
  public void cardSlotUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).cardSlotUpdate(stateUpdate);
  }

  @Override
  public void trackUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).trackUpdate(stateUpdate);
  }

  @Override
  public void vaticanUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).vaticanReportUpdate(stateUpdate);
  }

  @Override
  public void devDeckUpdate(String stateUpdate) {
    simpleGameState.updateDeck(stateUpdate);
  }

  @Override
  public void devDeckSetup(String stateUpdate) {
    simpleGameState.constructDeck(stateUpdate);
  }

  @Override
  public void tempChestUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).tempChestUpdate(stateUpdate);
  }

  @Override
  public void chestMergeUpdate(String username) {
    getSimplePlayerState(username).mergeTempChest(); //merge chest of the "old" currentPlayer if that current changes
  }

  @Override
  public void discardedLeaderUpdate(String username, String stateUpdate) {
    getSimplePlayerState(username).discardLeader(Integer.parseInt(stateUpdate));
  }

  @Override
  public void lorenzoTrackUpdate(String stateUpdate) {

  }

  @Override
  public void lorenzoShuffleUpdate() {

  }

  @Override
  public void lorenzoDevDeckUpdate(String stateUpdate) {

  }

  @Override
  public void gameStartedSetup(Message msg) {
    ArrayList<String> players = GSON.getGsonBuilder().fromJson(msg.getPayload(), ArrayList.class);
    SimplePlayerState currentPlayerState = getSimplePlayerState();
    this.simplePlayerStateMap = new LinkedHashMap<>();

    for(String s : players) {
      if (s.equals(this.clientUsername))
        this.simplePlayerStateMap.put(s, currentPlayerState);
      else
        this.simplePlayerStateMap.put(s, new SimplePlayerState()); //the array is ordered to give the right amount of resouces to each player
    }
  }


  /**
   * @return this client's simpleplayerstate
   */
  @Override
  public SimplePlayerState getSimplePlayerState() {
    return this.simplePlayerStateMap.get(this.clientUsername);
  }

  /**
   * returns the simpleplayerstate belonging to the specified player
   *
   * @param username the username of the players whose simpleplayerstate is to be returned
   * @return the specified player's simpleplayerstate
   */
  @Override
  public SimplePlayerState getSimplePlayerState(String username) {
    return this.simplePlayerStateMap.get(username);
  }

//  @Override
//  public List<SimplePlayerState> otherSimplePlayerStates(){
//    return simplePlayerStateMap.entrySet().stream()
//            .filter(x -> !x.getKey().equals(this.username))
//            .map(x -> x.getValue())
//            .collect(Collectors.toList());
//  }

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

  //TODO migliorarla
  @Override
  public int getPlayerTurnPosition(){
    int i = 1;
    for(Map.Entry<String, SimplePlayerState> entry : simplePlayerStateMap.entrySet()){
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
