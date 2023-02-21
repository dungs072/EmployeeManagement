package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VITRICV")
public class JobPositionEntity {

	@Id
	private String MACV;
	
	@Column(name = "TENVITRI")
	private String TENVITRI;

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
	
	
}
