package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QUYEN")
public class PriorityEntity {
	
	@Id
	private String MAQUYEN;
	
	@Column(name ="MOTA")
	private String MOTA;
	
	public PriorityEntity() {}
	
	public PriorityEntity(String description) {
		this.MOTA = description;
	}


	public String getMAQUYEN() {
		return MAQUYEN;
	}


	public void setMAQUYEN(String mAQUYEN) {
		MAQUYEN = mAQUYEN;
	}


	public String getMOTA() {
		return MOTA;
	}


	public void setMOTA(String mOTA) {
		MOTA = mOTA;
	}
	
}
