package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.utility.GSON;

/**
 * Represents a message between client and server in network
 */
public class Message {
   //provare a vedere se deserializzando LoginMessage posso impostare questi attributi private
   private String username = null; //username del Client mittente,
   private final MessageType messageType;
   private String payload = null;

   /**
    * construct a message with the specified MessageType
    * @param messageType the new message messageType
    */
   public Message(MessageType messageType) {
      this.messageType = messageType;
   }

   /**
    * construct a message with an username, a messageType and an empty payload
    * @param username the sender/receiver username
    * @param messageType the new message messageType
    */
   public Message(String username, MessageType messageType) {
      this.username = username;
      this.messageType = messageType;
   }

   /**
    * construct a message with a payload, a messageType and an empty username
    * @param messageType the new message messageType
    * @param payload the new message payload as a String
    */
   public Message(MessageType messageType, String payload) {
      this.messageType = messageType;
      this.payload = payload;
   }

   /**
    * construct a message with a payload as an Object, a messageType and an empty username
    * @param messageType the new message messageType
    * @param object the payload Object
    */
   public Message(MessageType messageType, Object object) {
      this.messageType = messageType;
      this.payload = GSON.getGsonBuilder().toJson(object);
   }

   /**
    * construct a message with an username, a payload as a String and a messageType
    * @param username the sender/receiver username
    * @param messageType the new message messageType
    * @param payload the new message payload as a String
    */
   public Message(String username, MessageType messageType, String payload) {
      this.username = username;
      this.messageType = messageType;
      this.payload = payload;
   }

   /**
    * construct a message with an username, a payload as an Object and a messageType
    * @param username the sender/receiver username
    * @param messageType the new message messageType
    * @param object the new message payload as an Object
    */
   public Message(String username, MessageType messageType, Object object) {
      this.messageType = messageType;
      this.username = username;
      this.payload = GSON.getGsonBuilder().toJson(object);
   }

   public MessageType getMessageType() {
      return messageType;
   }

   /**
    * returns message payload as a String
    * @return the message payload String
    */
   public String getPayload() { return payload; }

   /**
    * returns message payload as a java object of the specified type
    * @param myType the type of the returned object
    * @param <T> the generic return type (same as parameter myType)
    * @return the message payload as a java object
    */
   public <T> T getPayloadByType(Class<T> myType){
      return GSON.getGsonBuilder().fromJson(payload, myType);
   }

   /**
    * if the messageType is ACTION this method returns the action object contained as payload in the message
    * if the payload doesn't contain an action returns null
    * @return the ACTION contained as payload
    */
   public Action getAction() {
      if(this.messageType.equals(MessageType.ACTION))
         return GSON.buildAction(payload);
      else
         return null;
   }

   public void setUsername(String usr) {
      this.username = usr;
   }

   public String getUsername() {
      return username;
   }
}