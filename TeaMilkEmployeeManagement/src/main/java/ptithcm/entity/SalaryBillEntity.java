package ptithcm.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private String MAPHIEU;
	
	
	@Column(name = "THOIGIANNHAN")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "DD/MM/YYYY")
	private Date THOIGIANNHAN;
	
	@ManyToOne
	@JoinColumn(name = "MANV")
	private StaffEntity staffEntity;
	
	public SalaryBillEntity() {}
	public SalaryBillEntity(Date pickupDay, StaffEntity staffEntity) {
		this.THOIGIANNHAN = pickupDay;
		this.staffEntity = staffEntity;
	}

	public String getMAPHIEU() {
		return MAPHIEU;
	}

	public void setMAPHIEU(String mAPHIEU) {
		MAPHIEU = mAPHIEU;
	}

	public Date getTHOIGIANNHAN() {
		return THOIGIANNHAN;
	}

	public void setTHOIGIANNHAN(Date tHOIGIANNHAN) {
		THOIGIANNHAN = tHOIGIANNHAN;
	}

	public StaffEntity getStaff() {
		return staffEntity;
	}

	public void setStaff(StaffEntity staff) {
		this.staffEntity = staff;
	}
	
	
	

}
