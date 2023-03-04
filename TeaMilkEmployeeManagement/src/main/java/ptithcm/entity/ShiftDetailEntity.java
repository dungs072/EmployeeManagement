package ptithcm.entity;

import java.sql.Time;
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
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//
//import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "CHITIETCA")
public class ShiftDetailEntity {

	@Id
	private String ID_CTCA;

	@Column(name = "LUONGCA")
	private float LUONGCA;

	@Column(name = "CONGVIEC")
	private String CONGVIEC;
  
	@Column(name = "THOIGIANDILAM")
//	@Temporal(TemporalType.TIMESTAMP)
	private Time THOIGIANDILAM;

	@Column(name = "THOIGIANCHAMCONG")
//	@Temporal(TemporalType.TIMESTAMP)
	private Time THOIGIANCHAMCONG;

	@Column(name = "XACNHAN")
	private boolean XACNHAN;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MANV")
	private StaffEntity staff;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CA_MO")
	private OpenShiftEntity openshift;

	@OneToMany(mappedBy = "shiftDetailEntity", fetch = FetchType.EAGER)
	private Set<MistakeHistoryEntity> mistakeHistoryEntities = new HashSet<MistakeHistoryEntity>();

	public ShiftDetailEntity() {
	}

	public StaffEntity getStaff() {
		return staff;
	}

	public void setStaff(StaffEntity staff) {
		this.staff = staff;
	}

	public String getID_CTCA() {
		return ID_CTCA;
	}

	public void setID_CTCA(String iD_CTCA) {
		ID_CTCA = iD_CTCA;
	}

	public float getLUONGCA() {
		return LUONGCA;
	}

	public void setLUONGCA(float lUONGCA) {
		LUONGCA = lUONGCA;
	}

	public String getCONGVIEC() {
		return CONGVIEC;
	}

	public void setCONGVIEC(String cONGVIEC) {
		CONGVIEC = cONGVIEC;
	}

	public Time getTHOIGIANDILAM() {
		return THOIGIANDILAM;
	}

	public void setTHOIGIANDILAM(Time tHOIGIANDILAM) {
		THOIGIANDILAM = tHOIGIANDILAM;
	}

	public Time getTHOIGIANCHAMCONG() {
		return THOIGIANCHAMCONG;
	}

	public void setTHOIGIANCHAMCONG(Time tHOIGIANCHAMCONG) {
		THOIGIANCHAMCONG = tHOIGIANCHAMCONG;
	}

	public boolean isXACNHAN() {
		return XACNHAN;
	}

	public void setXACNHAN(boolean xACNHAN) {
		XACNHAN = xACNHAN;
	}

	public OpenShiftEntity getOpenshift() {
		return openshift;
	}

	public void setOpenshift(OpenShiftEntity openshift) {
		this.openshift = openshift;
	}

	public Set<MistakeHistoryEntity> getMistakeHistoryEntities() {
		return mistakeHistoryEntities;
	}

	public void setMistakeHistoryEntities(Set<MistakeHistoryEntity> mistakeHistoryEntities) {
		this.mistakeHistoryEntities = mistakeHistoryEntities;
	}

	public void addMistakeHistoryEntity(MistakeHistoryEntity mistakeHistoryEntity) {
		this.mistakeHistoryEntities.add(mistakeHistoryEntity);
	}
	public void deleteLink() {
		staff = null;
		openshift = null;
		setMistakeHistoryEntities(null);
	}

}
