package ptithcm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LOI")
public class MistakeEntity {
	@Id
	private String IDLOI;
	
	@Column(name = "MOTA")
	private String MOTA;
	
	@OneToMany(mappedBy = "mistakeEntity",fetch = FetchType.EAGER)
	private Set<MistakeHistoryEntity> mistakeHistoryEntities = new HashSet<MistakeHistoryEntity>();

	public MistakeEntity() {}
	
	public MistakeEntity(String description) {
		this.MOTA = description;
	}
	public String getIDLOI() {
		return IDLOI;
	}

	public void setIDLOI(String iDLOI) {
		IDLOI = iDLOI;
	}

	public String getMOTA() {
		return MOTA;
	}

	public void setMOTA(String mOTA) {
		MOTA = mOTA;
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
