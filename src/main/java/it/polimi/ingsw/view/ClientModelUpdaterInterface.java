package it.polimi.ingsw.view;

import it.polimi.ingsw.model.updatecontainers.*;

import java.util.List;

/**
 * Interface used to receive all the updates from the client
 */
public interface ClientModelUpdaterInterface {
  /**
   * Sets the username of this client, which is passed as parameter
   * @param username, username of this client
   */
  void setClientUsername(String username);

  /**
   * Updates the chest of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void chestUpdate(String username, ChestUpdate stateUpdate);

  /**
   * Updates the warehouse of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void warehouseUpdate(String username, WarehouseUpdate stateUpdate);

  /**
   * Updates not activated LeaderCards of the player whose name is the one passed as parameter activating one of them
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void leaderUpdate(String username, LeaderUpdate stateUpdate);

  /**
   * Setups non activated LeaderCards of the player whose name is the one passed as parameter
   * @param username, username of the player on which the setup needs to take place
   * @param stateUpdate, setup content
   */
  void leaderSetup(String username, LeaderSetup stateUpdate);

  /**
   * Updates the market
   * @param stateUpdate, update content
   */
  void marketUpdate(MarketUpdate stateUpdate);

  /**
   * Setups the market
   * @param stateUpdate, setup content
   */
  void marketSetup(MarketSetup stateUpdate);

  /**
   * Updates the CardSlots of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void cardSlotUpdate(String username, CardSlotUpdate stateUpdate);

  /**
   * Updates the track of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void trackUpdate(String username, TrackUpdate stateUpdate);

  /**
   * Updates the vatican cells of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void vaticanUpdate(String username, VaticanReport stateUpdate);

  /**
   * Updates the DevelopCard Deck
   * @param stateUpdate, update content
   */
  void devDeckUpdate(DevelopCardDeckUpdate stateUpdate);

  /**
   * Setups the DevelopCard Deck
   * @param stateUpdate, setup content
   */
  void devDeckSetup(DevelopCardDeckSetup stateUpdate);

  /**
   * Updates the temporary chest of the player whose name is the one passed as parameter
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void tempChestUpdate(String username, ChestUpdate stateUpdate);

  /**
   * Merges the temporary chest of the player whose name is the one passed as parameter into his chest
   * @param username, username of the player on which the update needs to take place
   */
  void chestMergeUpdate(String username);

  /**
   * Updates not activated LeaderCards of the player whose name is the one passed as parameter discarding one of them
   * @param username, username of the player on which the update needs to take place
   * @param stateUpdate, update content
   */
  void discardedLeaderUpdate(String username, LeaderUpdate stateUpdate);

  /**
   * Updates Lorenzo's track
   * @param stateUpdate, update content
   */
  void lorenzoTrackUpdate(TrackUpdate stateUpdate);

  /**
   * Updates Lorenzo because of having shuffled
   */
  void lorenzoShuffleUpdate();

  /**
   * Updates the DevelopCard Deck because Lorenzo has discarded a DevelopCard from the Deck
   * @param stateUpdate, update content
   */
  void lorenzoDevDeckUpdate(DevelopCardDeckUpdate stateUpdate);

  /**
   * Setups players turn order
   * @param stateUpdate, update content
   */
  void gameStartedSetup(List<String> stateUpdate);
}