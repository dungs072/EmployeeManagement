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
	@Column(name = "TENCA")
	private String TENCA;
	

	public ShiftEntity() {}
	
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
}