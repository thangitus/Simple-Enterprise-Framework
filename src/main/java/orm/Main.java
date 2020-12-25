package orm;

import gradleGenerate.GradleGen;
import orm.config.PersistenceConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class Main {
   public static void main(String args[]) {
      SqlServer sqlServer = new SqlServer("root", "1111");
      List<String> databases = sqlServer.connectToServer();
      SqlDatabase sqlDatabase = sqlServer.connectToDatabase("hibernateexercise");
      File file = new File("E:\\Nam Four\\tmp");
      if (sqlDatabase != null) {
         sqlDatabase.generate(file);
      }

      List<String> entityClasses = new ArrayList<>();
      entityClasses.add("entity.SinhVien");
      entityClasses.add("entity.LopHoc");
      PersistenceConfig persistenceConfig = new PersistenceConfig(entityClasses, "root","1111","com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/hibernateexercise");
      persistenceConfig.generate(new File("E:\\Nam Four\\tmp\\persistence.xml"));

      GradleGen gradleGen = new GradleGen();
      gradleGen.generate(new File("E:\\Nam Four\\tmp"));
   }
}
