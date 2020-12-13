import java.sql.*;

class Main {
   public static void main(String args[]) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "admin");

         DatabaseMetaData metadata = connection.getMetaData();
         ResultSet rs = metadata.getCatalogs();

         while (rs.next()) {
            String dbName = rs.getString("TABLE_CAT");
            System.out.println(dbName);
            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, "root",
                                                                  "admin");
            ResultSet resultSet = dbConnection.getMetaData()
                                              .getTables(null, null, "%", null);
            while (resultSet.next()) {
               System.out.println(resultSet.getString("TABLE_NAME"));
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}  