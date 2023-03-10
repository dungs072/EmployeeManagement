package ptithcm.entity;

import java.sql.Time;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CA")
public class ShiftEntity {

	@Id
	private String IDCA;
	@Column(name = "TENCA")
	private String TENCA;
	
	@Column(name = "THOIGIANBATDAU")
	private Time startShiftTime;
	
	@Column(name = "THOIGIANKETTHUC")
	private Time endShiftTime;
	
	@OneToMany(mappedBy = "shift",fetch  =FetchType.EAGER)
	private Collection<OpenShiftEntity> openShift;

	public ShiftEntity() {
	}

	public ShiftEntity(String tenca) {
		this.TENCA = tenca;
	}

	public String getIDCA() {
		return IDCA;
	}

	public void setIDCA(String iDCA) {
		IDCA = iDCA;
	}

	public String getTENCA() {
		return TENCA;
	}

	public void setTENCA(String tENCA) {
		TENCA = tENCA;
	}

	public Collection<OpenShiftEntity> getOpenShift() {
		return openShift;
	}

	public void setOpenShift(Collection<OpenShiftEntity> openShift) {
		this.openShift = openShift;
	}
	public Time getStartShiftTime() {
		return startShiftTime;
	}

	public void setStartShiftTime(Time startShiftTime) {
		this.startShiftTime = startShiftTime;
	}

	public Time getEndShiftTime() {
		return endShiftTime;
	}

	public void setEndShiftTime(Time endShiftTime) {
		this.endShiftTime = endShiftTime;
	}
}