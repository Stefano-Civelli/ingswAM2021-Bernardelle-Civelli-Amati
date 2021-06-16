package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

public class Main {

   public static void main(String[] args) {
      String[] empty = {};
      System.out.println("string di tes5");
      if(args.length == 0)
         Client.main(empty); //runs client in GUI mode

      if(args[0].equals("-c")) {
         empty = new String[]{"cli"};
         Client.main(empty); //run client in CLI mode
      }

      if(args[0].equals("-s"))
         Server.main(empty); //run server

      if(args[0].equals("-l")) { // for local
         empty = new String[]{"l"};
         Client.main(empty);
      }

      System.out.println("Something wrong with the parameters you inserted.\n Try relaunching the jar file ");
   }

}
