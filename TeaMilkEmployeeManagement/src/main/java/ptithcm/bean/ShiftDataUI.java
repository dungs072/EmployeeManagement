package ptithcm.bean;

public class ShiftDataUI {

	private String fullName;
	
	private String jobPositionName;
	private String staffId;

	public ShiftDataUI(String staffId,String fullName, String jobPositionName) {
		this.fullName = fullName;
		this.setStaffId(staffId);
		this.jobPositionName = jobPositionName;
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
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	
	
}
