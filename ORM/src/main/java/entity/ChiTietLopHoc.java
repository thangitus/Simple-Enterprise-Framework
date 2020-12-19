package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "chi_tiet_lop_hoc")
public class ChiTietLopHoc {
    @EmbeddedId
    private ChiTietLopHocID id;

    @Column(name = "PhongHoc", length = 5)
    private String phongHoc;

    public ChiTietLopHoc(String maLop, String maMon, String phongHoc){
        id = new ChiTietLopHocID(maLop, maMon);
        this.phongHoc = phongHoc;
    }





    public ChiTietLopHoc() {

    }

    public ChiTietLopHocID getId() {
        return id;
    }

    public void setId(ChiTietLopHocID id) {
        this.id = id;
    }

    public String getPhongHoc() {
        return phongHoc;
    }

    public void setPhongHoc(String phongHoc) {
        this.phongHoc = phongHoc;
    }
}



@Embeddable
class ChiTietLopHocID implements Serializable {
    @Column(name = "MaLop", length = 10)
    private String maLop;
    @Column(name = "MaMon", length = 10)
    private String maMon;

    public ChiTietLopHocID(String maLop, String maMon){
        this.maLop = maLop;
        this.maMon = maMon;
    }

    public ChiTietLopHocID() {

    }
}
