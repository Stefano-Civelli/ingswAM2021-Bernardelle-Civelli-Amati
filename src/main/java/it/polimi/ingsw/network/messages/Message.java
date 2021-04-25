package it.polimi.ingsw.network.messages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.action.Action;
import it.polimi.ingsw.utility.GSON;

//TODO aggiugere receiver e setReceiver
public class Message {

   //provare a vedere se deserializzando LoginMessage posso impostare questi attributi private
   private String username = null; //username del Client mittente,
   private MessageType messageType;
   private String payload = null; //usiamo il payload in questo modo:
   //-da Server a Client per 1)notificare gli update -> Oggetti serializzati 2)messaggi di servizio;
   //-da Client a Server per contenere le Action in formato Json

   private static final Gson gsonBuilder = new GsonBuilder().serializeNulls().enableComplexMapKeySerialization().create();

   public Message(MessageType messageType) {
      this.messageType = messageType;
   }

   public Message(MessageType messageType, String username) {
      this.messageType = messageType;
      this.username = username;
   }

   public Message(MessageType messageType, String username, String payload) {
      this.messageType = messageType;
      this.username = username;
      this.payload = payload;
   }

   public Message(MessageType messageType, String username, Object object) {
      this.messageType = messageType;
      this.username = username;
      this.payload = gsonBuilder.toJson(object);
   }

   public MessageType getMessageType() {
      return messageType;
   }

   public String getPayload() { return payload; }

   public Action getAction() { return GSON.buildAction(payload); } //se registriamo i sottotipi di Action sul builder probabilmente deserializza lui correttamente sulla sottoclasse

   public void setUsername(String usr) {
      this.username = usr;
   }

   public String getUsername() {
      return username;
   }
}