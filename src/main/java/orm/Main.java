package orm;

import java.io.File;
import java.util.List;

class Main {
   public static void main(String args[]) {
      SqlServer sqlServer = new SqlServer("root", "admin");
      List<String> databases = sqlServer.connectToServer();
      SqlDatabase sqlDatabase = sqlServer.connectToDatabase("hanks");
      File file = new File("E:\\Nam Four\\tmp");
      if (sqlDatabase != null) {
         sqlDatabase.generate(file);
      }
   }
}  