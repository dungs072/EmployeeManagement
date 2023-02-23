package ptithcm.entity;

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
	
	@Column(name = "MOTA")
	private String MOTA;
	
	@OneToMany(mappedBy = "shift",fetch = FetchType.EAGER)
	private Set<ShiftDetailEntity> detailEntities = new HashSet<ShiftDetailEntity>();

	public ShiftEntity() {}
	
	public ShiftEntity(String mota) {
		this.MOTA = mota;
	}
		
	

	public String getIDCA() {
		return IDCA;
	}

	public void setIDCA(String iDCA) {
		IDCA = iDCA;
	}

	public String getMOTA() {
		return MOTA;
	}

	public void setMOTA(String mOTA) {
		MOTA = mOTA;
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
