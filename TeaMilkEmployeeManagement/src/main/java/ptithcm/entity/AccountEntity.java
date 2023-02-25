package ptithcm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAIKHOAN")
public class AccountEntity {
	
	@Id
	private String TENTK;
	
	@Column(name ="MK")
	private String MK;
	
	@Column(name ="TRANGTHAI")
	private boolean TRANGTHAI;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "MAQUYEN")
	private PriorityEntity priorityEntity;
	
	public AccountEntity() {}
	
	public AccountEntity(String userName, String password, boolean active, PriorityEntity priorityEntity) {
		this.TENTK = userName;
		this.MK = password;
		this.TRANGTHAI = active;
		this.priorityEntity = priorityEntity;
	}
	
	public String getTENTK() {
		return TENTK;
	}

	public void setTENTK(String tENTK) {
		TENTK = tENTK;
	}

	public String getMK() {
		return MK;
	}

	public void setMK(String mK) {
		MK = mK;
	}

	public boolean isTRANGTHAI() {
		return TRANGTHAI;
	}

	public void setTRANGTHAI(boolean tRANGTHAI) {
		TRANGTHAI = tRANGTHAI;
	}

	public PriorityEntity getPriorityEntity() {
		return priorityEntity;
	}

	public void setPriorityEntity(PriorityEntity priorityEntity) {
		this.priorityEntity = priorityEntity;
	}


}
