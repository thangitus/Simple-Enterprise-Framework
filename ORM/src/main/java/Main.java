import dao.LopHocDAO;
import dao.SinhVienDAO;
import entity.LopHoc;
import entity.SinhVien;

class Main {
   public static void main(String args[]) {
      // - Test hibernate ------

      //Create lop hoc ma lop = 321
      LopHocDAO lopHocDAO = new LopHocDAO();
      lopHocDAO.save(new LopHoc("321"));
      lopHocDAO.close();

      //Create sinh vien
      SinhVienDAO sinhVienDAO = new SinhVienDAO();
      sinhVienDAO.save(new SinhVien("123","abc", "xyz","cmd","321"));
      sinhVienDAO.save(new SinhVien("234","phat", "nam","cmd","321"));

      //Find all sinh vien
      sinhVienDAO.findAll().forEach(sv -> {System.out.println(sv.getHoVaTen());});

      //Delete sinh vien mssv = 123
      SinhVien sinhVien = sinhVienDAO.findByID("123");
      sinhVienDAO.delete(sinhVien);

      //Find all sinh vien
      System.out.println("after delete sv mssv = 123");
      sinhVienDAO.findAll().forEach(sv -> {System.out.println(sv.getHoVaTen());});

      sinhVienDAO.close();
   }
}
