package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

public class Main {

   public static void main(String[] args) {
      String[] empty = {};
      if(args[0].equals("c"))
         Client.main(empty);
      if(args[0].equals("s"))
         Server.main(empty);
      if(args[0].equals("l")) {
         empty = new String[]{"l"};
         Client.main(empty);
      }
   }
}
