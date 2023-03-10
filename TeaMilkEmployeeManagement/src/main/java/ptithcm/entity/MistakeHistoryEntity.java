package ptithcm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "LICHSULOI")
public class MistakeHistoryEntity {

	@Id
	private String ID_LSLOI;
	
	@Column(name = "MOTA")
	private String description;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "IDLOI")
	private MistakeEntity mistakeEntity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_CTCA")
	private ShiftDetailEntity shiftDetailEntity;

	@Column(name = "SOLANVIPHAM")
	private int SOLANVIPHAM;
	
	@Transient
	private boolean canDelete = false;

	public MistakeHistoryEntity() {
	}

	public MistakeHistoryEntity(MistakeEntity mistakeEntity, ShiftDetailEntity shiftDetailEntity) {
		this.mistakeEntity = mistakeEntity;
		this.shiftDetailEntity = shiftDetailEntity;
	}

	public String getID_LSLOI() {
		return ID_LSLOI;
	}

	public void setID_LSLOI(String iD_LSLOI) {
		ID_LSLOI = iD_LSLOI;
	}

	public MistakeEntity getMistakeEntity() {
		return mistakeEntity;
	}

	public void setMistakeEntity(MistakeEntity mistakeEntity) {
		this.mistakeEntity = mistakeEntity;
	}

	public ShiftDetailEntity getShiftDetailEntity() {
		return shiftDetailEntity;
	}

	public void setShiftDetailEntity(ShiftDetailEntity shiftDetailEntity) {
		this.shiftDetailEntity = shiftDetailEntity;
	}

	public int getSOLANVIPHAM() {
		return SOLANVIPHAM;
	}

	public void setSOLANVIPHAM(int sOLANVIPHAM) {
		SOLANVIPHAM = sOLANVIPHAM;
	}
	
	public void updateSOLANVIPHAM(int times) {
		SOLANVIPHAM = SOLANVIPHAM + times;
	}
	public void deleteLink() {
		shiftDetailEntity = null;
		mistakeEntity = null;
	}

	public boolean isCanDelete() {
		return canDelete;
	}

	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
