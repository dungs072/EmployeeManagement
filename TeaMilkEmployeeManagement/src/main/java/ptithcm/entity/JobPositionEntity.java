package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ptithcm.bean.Primarykeyable;

@Entity
@Table(name = "VITRICV")
public class JobPositionEntity implements Primarykeyable{

	@Id
	private String MACV;
	
	@Column(name = "TENVITRI")
	private String TENVITRI;
	
	public JobPositionEntity() {}
	
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
	
	
}
