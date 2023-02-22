package ptithcm.entity;

import java.util.HashSet;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "DD/MM/YYYY")
	private Date NGAYSINH;
	
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
	
	@ManyToOne
	@JoinColumn(name = "MACV")
	private JobPositionEntity jobPosition;
	
	@OneToMany(mappedBy = "staff")
	private Set<ShiftDetailEntity> detailEntities = new HashSet<ShiftDetailEntity>();
	
	public StaffEntity() {}
	
	public StaffEntity(String firstName, String lastName, String gender, String idCardNumber, String phoneNumber, String address){
		HO = firstName;
		TEN = lastName;
		GIOITINH = gender;
		CCCD = idCardNumber;
		SDT = phoneNumber;
		DIACHI = address;
	}
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

	public Date getNGAYSINH() {
		return NGAYSINH;
	}

	public void setNGAYSINH(Date nGAYSINH) {
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
	

	public JobPositionEntity getJobPosition() {
		return jobPosition;
	}
	public void setJobPosition(JobPositionEntity jobPosition) {
		this.jobPosition = jobPosition;
	}
	
	public Set<ShiftDetailEntity> getDetailEntities() {
		return detailEntities;
	}

	public void setDetailEntities(Set<ShiftDetailEntity> detailEntities) {
		this.detailEntities = detailEntities;
	}
	public void addShiftDetailEntity(ShiftDetailEntity shiftDetailEntity) {
        this.detailEntities.add(shiftDetailEntity);
    }  
	
}
