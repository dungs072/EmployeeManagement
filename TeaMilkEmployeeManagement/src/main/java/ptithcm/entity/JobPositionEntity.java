package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import ptithcm.bean.Primarykeyable;

@Entity
@Table(name = "VITRICV")
public class JobPositionEntity implements Primarykeyable {

	@Id
	private String MACV;

	@Column(name = "TENVITRI")
	private String TENVITRI;

	@Transient
	private boolean canDelete;
	@Transient
	private boolean canUpdate = true;

	public JobPositionEntity() {
	}

	public JobPositionEntity(String positionName) {
		TENVITRI = positionName;
	}

	public String getMACV() {
		return MACV;
	}

	public void setMACV(String mACV) {
		MACV = mACV;
	}

	public String getTENVITRI() {
		return TENVITRI;
	}

	public void setTENVITRI(String tENVITRI) {
		TENVITRI = tENVITRI;
	}

	@Override
	public String getPrimaryKey() {
		return MACV;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public boolean isCanUpdate() {
		return canUpdate;
	}

	public void setCanUpdate(boolean canUpdate) {
		this.canUpdate = canUpdate;
	}

}
