package orm;

import java.util.List;

class Main {
   public static void main(String args[]) {
      SqlServer mySqlServer = new SqlServer("root", "admin");
      List<String> databases = mySqlServer.connectToServer();
      for (String database : databases)
         System.out.println(database);
      mySqlServer.connectToDatabase("hibernateexercise");
   }
}  