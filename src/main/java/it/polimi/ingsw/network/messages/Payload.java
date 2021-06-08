package it.polimi.ingsw.network.messages;

import it.polimi.ingsw.model.Chest;

import java.lang.reflect.Type;

public class Payload {
   private Type type;
   private Object content;

   //poi peylaod ha delle sottoclassi in base all' oggetto che ha come contenuto

   //l'interfaccia payload ha getPayloadType e getPayloadContent (in realtà con questa soluzione potrei farlo direttamente dal message quindi ha poco senso)

}


