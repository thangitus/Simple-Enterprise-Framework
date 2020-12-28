package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sinh_vien")
public class SinhVien {
    @Id
    @Column (name = "MSSV")
    String mssv;
    @Column (name = "HoVaTen")
    String hoVaTen;
    @Column (name = "GioiTinh")
    String gioiTinh;
    @Column (name = "CMND")
    String cmnd;
    @Column (name = "MaLop")
    String maLop;

    public SinhVien(String mssv, String hoVaTen, String gioiTinh, String cmnd, String maLop) {
        this.mssv = mssv;
        this.hoVaTen = hoVaTen;
        this.gioiTinh = gioiTinh;
        this.cmnd = cmnd;
        this.maLop = maLop;
    }

    public SinhVien() {
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }
}
