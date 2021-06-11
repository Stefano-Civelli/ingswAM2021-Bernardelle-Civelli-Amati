package it.polimi.ingsw.network.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.utility.GSON;
import it.polimi.ingsw.utility.Pair;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//TODO aggiugere receiver e setReceiver
public class Message {
   //provare a vedere se deserializzando LoginMessage posso impostare questi attributi private
   private String username = null; //username del Client mittente,
   private MessageType messageType;
   private String payload = null; //usiamo il payload in questo modo:
   //-da Server a Client per 1)notificare gli phaseUpdate -> Oggetti serializzati 2)messaggi di servizio;
   //-da Client a Server per contenere le Action in formato Json


   public Message(MessageType messageType) {
      this.messageType = messageType;
   }

   public Message(String username, MessageType messageType) {
      this.username = username;
      this.messageType = messageType;
   }

   public Message(MessageType messageType, String payload) {
      this.messageType = messageType;
      this.payload = payload;
   }

   public Message(MessageType messageType, Object object) {
      this.messageType = messageType;
      this.payload = GSON.getGsonBuilder().toJson(object);
   }

   public Message(String username, MessageType messageType, String payload) {
      this.username = username;
      this.messageType = messageType;
      this.payload = payload;
   }

   public Message(String username, MessageType messageType, Object object) {
      this.messageType = messageType;
      this.username = username;
      this.payload = GSON.getGsonBuilder().toJson(object);
   }

   public MessageType getMessageType() {
      return messageType;
   }

   public String getPayload() { return payload; }

   public <T> T getPayloadByType(Class<T> myType){
      //Type token = new TypeToken<T>(){}.getType();
      return GSON.getGsonBuilder().fromJson(payload, myType);
   }


   public Action getAction() { return GSON.buildAction(payload); } //se registriamo i sottotipi di Action sul builder probabilmente deserializza lui correttamente sulla sottoclasse

   public void setUsername(String usr) {
      this.username = usr;
   }

   public String getUsername() {
      return username;
   }
}