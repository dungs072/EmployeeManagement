package ptithcm.bean;

public class StaffShiftDataUI {
	private String fullName;
	
	private boolean isConfirmed;
	
	private boolean isOwned;
	private String staffId;
	private String shiftDetailId;
	private String additionalJob;
	
	
	public StaffShiftDataUI(String fullName, boolean isConfirmed) {
		this.fullName = fullName;
		this.isConfirmed = isConfirmed;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public boolean getIsConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public boolean getIsOwned() {
		return isOwned;
	}

	public void setOwned(boolean isOwned) {
		this.isOwned = isOwned;
	}
	
	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getShiftDetailId() {
		return shiftDetailId;
	}

	public void setShiftDetailId(String shiftDetailId) {
		this.shiftDetailId = shiftDetailId;
	}

	public String getAdditionalJob() {
		return additionalJob;
	}

	public void setAdditionalJob(String additionalJob) {
		this.additionalJob = additionalJob;
	}

	
	
}
