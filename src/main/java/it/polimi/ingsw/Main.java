package it.polimi.ingsw;

import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.server.Server;

/**
 * The class containing the first main method called when running the jar file
 */
public abstract class Main {

   /**
    * The name of the executing jar file
    */
   public static final String jarName;

   /**
    * The string printed on -h or --help program argument
    */
   public static final String helpString;

   /**
    * The string printed when the program starts with wrongs arguments
    */
   public static final String errorString;

   static {

      String tmpJarName = "<jarname>.jar";
      try {
         tmpJarName = new java.io.File(Main.class.getProtectionDomain()
                 .getCodeSource()
                 .getLocation()
                 .getPath()).getName();
      } catch (Exception ignored) { }
      jarName = tmpJarName;

      helpString =   "Usage: java -jar " + jarName + " [OPTION]\n" +
                     "Option    Long option    Meaning" +
                     "-s        --server       start the game server\n" +
                     "-g        --gui          start the game client whit GUI\n" +
                     "-c        --cli          start the game client in CLI\n" +
                     "Options cannot be combined.\n" +
                     "With no argument will be started the game client in CLI mode.\n";

      errorString =  "Unrecognized options\n" +
                     "Type 'java -jar " + jarName + " -h' for a list of available options.\n";

   }

   /**
    * The first method called when running the jar file.
    * Checks the program arguments and calls the correct methods.
    *
    * @param args arguments to specify to run the server ("-s"), the client in GUI ("-g") or the client in CLI ("-c")
    */
   public static void main(String[] args) {
      boolean err = true;

      if(args.length == 0) {
         err = false;
         Client.main(new String[]{}); //runs client in GUI mode
      }

      if(args.length == 1) {
         if (args[0].equals("-g") || args[0].equals("--gui")) {
            err = false;
            Client.main(new String[]{}); //run client in GUI mode
         }

         if (args[0].equals("-c") || args[0].equals("--cli")) {
            err = false;
            Client.main(new String[]{"cli"}); //run client in CLI mode
         }

         if (args[0].equals("-s") || args[0].equals("--server")) {
            err = false;
            Server.main(new String[]{}); //run server
         }

         if (args[0].equals("-h") || args[0].equals("--help")) {
            err = false;
            System.out.print(helpString);
         }
      }

      if(err)
         System.out.print(errorString);
   }

}
