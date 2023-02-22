package ptithcm.entity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LICHSULOI")
public class MistakeHistoryEntity {
	
	@Id
	private String ID_LSLOI;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IDLOI")
	private MistakeEntity mistakeEntity;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CTCA")
	private ShiftDetailEntity shiftDetailEntity;
	
	@Column(name = "SOLANVIPHAM")
	private int SOLANVIPHAM;

	public MistakeHistoryEntity() {}
	
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
	
	
}
