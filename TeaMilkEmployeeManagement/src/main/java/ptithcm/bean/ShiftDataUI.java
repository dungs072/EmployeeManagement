package ptithcm.bean;

public class ShiftDataUI {

	private String fullName;

	private String jobPositionName;
	private String shiftDetailId;
	
	private String additionalJobs;
	
	private boolean isConfirmed = false;
	

	public ShiftDataUI(String shiftDetailId,String fullName, String jobPositionName,String additionalJobs) {
		this.fullName = fullName;
		this.shiftDetailId = shiftDetailId;
		this.jobPositionName = jobPositionName;
		this.additionalJobs = additionalJobs;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getJobPositionName() {
		return jobPositionName;
	}

	public void setJobPositionName(String jobPositionName) {
		this.jobPositionName = jobPositionName;
	}


	public String getShiftDetailId() {
		return shiftDetailId;
	}
	public void setShiftDetailId(String id) {
		this.shiftDetailId = id;
	}
	public String getAdditionalJobs() {
		return additionalJobs;
	}
	public void setAdditionalJobs(String additionalJobs) {
		this.additionalJobs = additionalJobs;
	}
	
	public boolean getIsConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	
}
