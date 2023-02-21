package ptithcm.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NHANVIEN")
public class StaffEntity {
	
	@Id
	private String MANV;
	
	@Column(name ="HO")
	private String HO;
	
	@Column(name = "TEN")
	private String TEN;
	
	@Column(name = "GIOITINH")
	private String GIOITINH;
	
	@Column(name = "NGAYSINH")
	private String NGAYSINH;
	
	@Column(name = "CCCD")
	private String CCCD;
	
	@Column(name = "SDT")
	private String SDT;
	
	@Column(name = "EMAIL")
	private String EMAIL;
	
	@Column(name = "DIACHI")
	private String DIACHI;
	
	@Column(name = "LUONGTICHLUY")
	private float LUONGTICHLUY;
	
	@Column(name = "MACV")
	private String MACV;
	
	public String getMANV() {
		return MANV;
	}

	public void setMANV(String mANV) {
		MANV = mANV;
	}

	public String getHO() {
		return HO;
	}

	public void setHO(String hO) {
		HO = hO;
	}

	public String getTEN() {
		return TEN;
	}

	public void setTEN(String tEN) {
		TEN = tEN;
	}

	public String getGIOITINH() {
		return GIOITINH;
	}

	public void setGIOITINH(String gIOITINH) {
		GIOITINH = gIOITINH;
	}

	public String getNGAYSINH() {
		return NGAYSINH;
	}

	public void setNGAYSINH(String nGAYSINH) {
		NGAYSINH = nGAYSINH;
	}

	public String getCCCD() {
		return CCCD;
	}

	public void setCCCD(String cCCD) {
		CCCD = cCCD;
	}

	public String getSDT() {
		return SDT;
	}

	public void setSDT(String sDT) {
		SDT = sDT;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public String getDIACHI() {
		return DIACHI;
	}

	public void setDIACHI(String dIACHI) {
		DIACHI = dIACHI;
	}

	public float getLUONGTICHLUY() {
		return LUONGTICHLUY;
	}

	public void setLUONGTICHLUY(float lUONGTICHLUY) {
		LUONGTICHLUY = lUONGTICHLUY;
	}

	public String getMACV() {
		return MACV;
	}

	public void setMACV(String mACV) {
		MACV = mACV;
	}
}
