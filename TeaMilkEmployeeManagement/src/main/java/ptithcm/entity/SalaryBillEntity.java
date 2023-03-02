package ptithcm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "PHIEULUONG")
public class SalaryBillEntity {

	@Id
	@GeneratedValue
	private int MAPHIEU;

	@Column(name = "THOIGIANNHAN")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "DD/MM/YYYY")
	private Date THOIGIANNHAN;

	@ManyToOne
	@JoinColumn(name = "MANV")
	private StaffEntity staffEntity;
	
	@Column(name = "LUONGNHAN")
	private float LUONGNHAN;

	public StaffEntity getStaffEntity() {
		return staffEntity;
	}

	public void setStaffEntity(StaffEntity staffEntity) {
		this.staffEntity = staffEntity;
	}

	public float getLUONGNHAN() {
		return LUONGNHAN;
	}

	public void setLUONGNHAN(float lUONGNHAN) {
		LUONGNHAN = lUONGNHAN;
	}

	
	public SalaryBillEntity() {}
	public SalaryBillEntity(Date pickupDay, StaffEntity staffEntity) {
		this.THOIGIANNHAN = pickupDay;
		this.staffEntity = staffEntity;
	}

	public int getMAPHIEU() {
		return MAPHIEU;
	}

	public void setMAPHIEU(int mAPHIEU) {
		MAPHIEU = mAPHIEU;
	}
	public StaffEntity getStaffEntity() {
		return staffEntity;
	}
	public void setStaffEntity(StaffEntity staffEntity) {
		this.staffEntity = staffEntity;
	}
	public Date getTHOIGIANNHAN() {
		return THOIGIANNHAN;
	}

	public void setTHOIGIANNHAN(Date tHOIGIANNHAN) {
		THOIGIANNHAN = tHOIGIANNHAN;
	}

	public float getLUONGNHAN() {
		return LUONGNHAN;
	}
	public void setLUONGNHAN(float lUONGNHAN) {
		LUONGNHAN = lUONGNHAN;
	}


}
