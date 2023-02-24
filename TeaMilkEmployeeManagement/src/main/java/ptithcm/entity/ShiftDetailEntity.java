package ptithcm.entity;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CHITIETCA")
public class ShiftDetailEntity {
	
	@Id
	private String ID_CTCA;
	
	
	@Column(name = "LUONGCA")
	private float LUONGCA;
	
	@Column(name = "CONGVIEC")
	private String CONGVIEC;
	
	@Column(name = "THOIGIANDANGKI")
	@Temporal(TemporalType.DATE)
	private Date THOIGIANDANGKI;
	
	@Column(name = "THOIGIANDILAM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date THOIGIANDILAM;
	
	@Column(name = "THOIGIANCHAMCONG")
	@Temporal(TemporalType.TIMESTAMP)
	private Date THOIGIANCHAMCONG;
	
	@Column(name = "XACNHAN")
	private boolean XACNHAN;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MANV")  
	private StaffEntity staff;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDCA")  
	private ShiftEntity shift;
	
	@OneToMany(mappedBy = "shiftDetailEntity",fetch = FetchType.EAGER)
	private Set<MistakeHistoryEntity> mistakeHistoryEntities = new HashSet<MistakeHistoryEntity>();
	
	public ShiftDetailEntity() {}
	
	public ShiftDetailEntity(Date registerTime) {
		this.THOIGIANDANGKI = registerTime;
	}

	public StaffEntity getStaff() {
		return staff;
	}

	public void setStaff(StaffEntity staff) {
		this.staff = staff;
	}

	
	public ShiftEntity getShift() {
		return shift;
	}

	public void setShift(ShiftEntity shift) {
		this.shift = shift;
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
	

	public Date getTHOIGIANDANGKI() {
		return THOIGIANDANGKI;
	}

	public void setTHOIGIANDANGKI(Date tHOIGIANDANGKI) {
		THOIGIANDANGKI = tHOIGIANDANGKI;
	}

	public Date getTHOIGIANDILAM() {
		return THOIGIANDILAM;
	}

	public void setTHOIGIANDILAM(Date tHOIGIANDILAM) {
		THOIGIANDILAM = tHOIGIANDILAM;
	}

	public Date getTHOIGIANCHAMCONG() {
		return THOIGIANCHAMCONG;
	}

	public void setTHOIGIANCHAMCONG(Date tHOIGIANCHAMCONG) {
		THOIGIANCHAMCONG = tHOIGIANCHAMCONG;
	}

	public boolean isXACNHAN() {
		return XACNHAN;
	}

	public void setXACNHAN(boolean xACNHAN) {
		XACNHAN = xACNHAN;
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
	
	
}
