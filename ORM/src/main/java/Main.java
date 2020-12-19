import dao.LopHocDAO;
import dao.SinhVienDAO;
import entity.LopHoc;
import entity.SinhVien;

class Main {
   public static void main(String args[]) {
//      // - Test hibernate ------
//      LopHocDAO lopHocDAO = new LopHocDAO();
//      lopHocDAO.save(new LopHoc("321"));
//      lopHocDAO.close();

      SinhVienDAO sinhVienDAO = new SinhVienDAO();
//      sinhVienDAO.save(new SinhVien("123","abc", "xyz","cmd","123"));

//      sinhVienDAO.findAll().forEach(System.out::println);

      SinhVien sinhVien = sinhVienDAO.findByID("2222");
      sinhVienDAO.delete(sinhVien);
      sinhVienDAO.close();
   }
}
