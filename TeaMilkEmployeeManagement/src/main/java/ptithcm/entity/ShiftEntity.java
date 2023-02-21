package ptithcm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CA")
public class ShiftEntity {
	
	@Id
	private String IDCA;
	
	@Column(name = "TENCA")
	private int TENCA;

	public String getIDCA() {
		return IDCA;
	}

	public void setIDCA(String iDCA) {
		IDCA = iDCA;
	}

	public int getTENCA() {
		return TENCA;
	}

	public void setTENCA(int tENCA) {
		TENCA = tENCA;
	}


	
	
}
