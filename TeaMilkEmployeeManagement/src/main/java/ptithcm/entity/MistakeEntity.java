package ptithcm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ptithcm.bean.Primarykeyable;

@Entity
@Table(name = "LOI")
public class MistakeEntity implements Primarykeyable {
	@Id
	private String IDLOI;

	@Column(name = "TENLOI")
	private String MOTA;

	@OneToMany(mappedBy = "mistakeEntity", fetch = FetchType.EAGER)
	private Set<MistakeHistoryEntity> mistakeHistoryEntities = new HashSet<MistakeHistoryEntity>();

	@Transient
	private boolean canDelete;

	public MistakeEntity() {
	}

	public MistakeEntity(String description) {
		this.MOTA = description;
		canDelete = true;
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

	@Override
	public String getPrimaryKey() {
		return IDLOI;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

}
