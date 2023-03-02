package ptithcm.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CA_MO")
public class OpenShiftEntity {

	@Id
	private String ID_CA_MO;

	@Column(name = "NGAYLAMVIEC")
	private Date NGAYLAMVIEC;

	@Column(name = "SOLUONGDANGKI")
	private int SOLUONGDANGKI;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDCA")
	private ShiftEntity shift;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MANV")
	private StaffEntity staff;

	@OneToMany(mappedBy = "openshift", fetch = FetchType.EAGER)
	private Set<ShiftDetailEntity> detailEntities = new HashSet<ShiftDetailEntity>();

	public OpenShiftEntity() {
	}

	public String getID_CA_MO() {
		return ID_CA_MO;
	}

	public void setID_CA_MO(String iD_CA_MO) {
		ID_CA_MO = iD_CA_MO;
	}

	public Date getNGAYLAMVIEC() {
		return NGAYLAMVIEC;
	}

	public void setNGAYLAMVIEC(Date nGAYLAMVIEC) {
		NGAYLAMVIEC = nGAYLAMVIEC;
	}

	public int getSOLUONGDANGKI() {
		return SOLUONGDANGKI;
	}

	public void setSOLUONGDANGKI(int sOLUONGDANGKI) {
		SOLUONGDANGKI = sOLUONGDANGKI;
	}

	public ShiftEntity getShift() {
		return shift;
	}

	public void setShift(ShiftEntity shift) {
		this.shift = shift;
	}

	public StaffEntity getStaff() {
		return staff;
	}

	public void setStaff(StaffEntity staff) {
		this.staff = staff;
	}

	public Set<ShiftDetailEntity> getDetailEntities() {
		return detailEntities;
	}

	public void setDetailEntities(Set<ShiftDetailEntity> detailEntities) {
		this.detailEntities = detailEntities;
	}

	public void addShiftDetailEntities(ShiftDetailEntity detailEntity) {
		this.detailEntities.add(detailEntity);
	}

}
