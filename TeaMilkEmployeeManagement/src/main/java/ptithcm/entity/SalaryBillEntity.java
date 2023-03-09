package ptithcm.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PHIEULUONG")
public class SalaryBillEntity {

	@Id
	@GeneratedValue
	private int MAPHIEU;

	@Column(name = "THOIGIANNHAN")
	private Timestamp THOIGIANNHAN;

	@ManyToOne
	@JoinColumn(name = "MANV")
	private StaffEntity staffEntity;
	
	@Column(name = "LUONGNHAN")
	private float LUONGNHAN;


	
	public SalaryBillEntity() {}
	public SalaryBillEntity(Timestamp pickupDay, StaffEntity staffEntity) {
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
	public Timestamp getTHOIGIANNHAN() {
		return THOIGIANNHAN;
	}

	public void setTHOIGIANNHAN(Timestamp tHOIGIANNHAN) {
		THOIGIANNHAN = tHOIGIANNHAN;
	}

	public float getLUONGNHAN() {
		return LUONGNHAN;
	}
	public void setLUONGNHAN(float lUONGNHAN) {
		LUONGNHAN = lUONGNHAN;
	}
}
