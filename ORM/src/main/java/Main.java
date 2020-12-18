import dao.ChiTietLopHocDAO;
import entity.ChiTietLopHoc;

import java.sql.*;
import java.util.List;

class Main {
   public static void main(String args[]) {
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
