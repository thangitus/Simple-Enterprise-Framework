import dao.ChiTietLopHocDAO;
import entity.ChiTietLopHoc;

import java.sql.*;
import java.util.List;

class Main {
   public static void main(String args[]) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hibernateexercise", "root",
                                                             "1111");

         DatabaseMetaData metadata = connection.getMetaData();
         ResultSet resultSet = metadata.getTables(connection.getCatalog(), null, "%", null);

         while (resultSet.next()) {
            System.out.println(resultSet.getString("TABLE_NAME"));
         }
         // Create a result set

         Statement statement = connection.createStatement();

         ResultSet results = statement.executeQuery("SELECT * FROM chi_tiet_lop_hoc");

         // Get resultset metadata

         ResultSetMetaData resultSetMetaData = results.getMetaData();

         int columnCount = resultSetMetaData.getColumnCount();

         System.out.println("chi_tiet_lop_hoc columns : ");

         // Get the column names; column indices start from 1
         for (int i = 1; i <= columnCount; i++) {
            String columnName = resultSetMetaData.getColumnName(i);
            System.out.println(resultSetMetaData.getColumnType(i));
            System.out.println(columnName);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

      // - Test hibernate ------
      ChiTietLopHocDAO chiTietLopHocDAO = new ChiTietLopHocDAO();
      chiTietLopHocDAO.save(new ChiTietLopHoc("aaa","bbb","ccc"));
      List<ChiTietLopHoc> chiTietLopHocList = chiTietLopHocDAO.findAll();
      chiTietLopHocList
              .stream()
              .forEach(
                      instance->System.out.println(instance.getId()));
      chiTietLopHocDAO.close();
   }
}
