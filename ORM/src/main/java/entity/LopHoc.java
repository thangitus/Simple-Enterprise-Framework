package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lop_hoc")
public class LopHoc {
    @Id
    @Column(name = "MaLop")
    String maLop;

    public LopHoc(){

    }

    public LopHoc(String maLop) {
        this.maLop = maLop;
    }
}
