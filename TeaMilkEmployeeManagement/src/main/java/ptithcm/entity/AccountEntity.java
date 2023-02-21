package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAIKHOAN")
public class AccountEntity {
	
	@Id
	private String TENTK;
	
	@Column(name ="MK")
	private String MK;
	
	@Column(name ="TRANGTHAI")
	private boolean TRANGTHAI;
	
	@Column(name ="MAQUYEN")
	private String MAQUYEN;
	
	public String getTENTK() {
		return TENTK;
	}

	public void setTENTK(String tENTK) {
		TENTK = tENTK;
	}

	public String getMK() {
		return MK;
	}

	public void setMK(String mK) {
		MK = mK;
	}

	public boolean isTRANGTHAI() {
		return TRANGTHAI;
	}

	public void setTRANGTHAI(boolean tRANGTHAI) {
		TRANGTHAI = tRANGTHAI;
	}

	public String getMAQUYEN() {
		return MAQUYEN;
	}

	public void setMAQUYEN(String mAQUYEN) {
		MAQUYEN = mAQUYEN;
	}
}
