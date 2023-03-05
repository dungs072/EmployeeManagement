package ptithcm.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(mappedBy = "jobPosition",fetch  =FetchType.EAGER)
	private Collection<StaffEntity> staffs;

	public Collection<StaffEntity> getStaffs() {
		return staffs;
	}

	public void setStaffs(Collection<StaffEntity> staffs) {
		this.staffs = staffs;
	}

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
