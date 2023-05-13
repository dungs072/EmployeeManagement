package ptithcm.bean;

import java.util.ArrayList;

public class ListStaffShiftDataUI {

	private ArrayList<StaffShiftDataUI> list;
	
	
	private boolean canSettingShift;
	private int leftStaff;
	private String fullNameManager;
	public String getFullNameManager() {
		return fullNameManager;
	}
	public void setFullNameManager(String fullNameManager) {
		this.fullNameManager = fullNameManager;
	}
	public ListStaffShiftDataUI() {
		list = new ArrayList<StaffShiftDataUI>();

	}
	public StaffShiftDataUI getStaffShitDataUI(String staffId) {
		for(var shiftDetail:list) {
			if(shiftDetail.getStaffId().equals(staffId)) {
				return shiftDetail;
			}
		}
		return null;
	}
	
	public void deleteStaffShiftDataUI(String shiftDetailId) {
		for(var shiftDetail:list) {
			if(shiftDetail.getShiftDetailId().equals(shiftDetailId)) {
				list.remove(shiftDetail);
				break;
			}
		}
	}
	
	public void addStaffShiftDataUI(StaffShiftDataUI staffUI) {
		list.add(staffUI);
	}

	public int getLeftStaff() {
		return leftStaff;
	}

	public void setLeftStaff(int leftStaff) {
		this.leftStaff = leftStaff;
	}
	
	public void calculateLeftStaff(int maxStaff) {
		setLeftStaff(maxStaff-list.size());
	}

	public ArrayList<StaffShiftDataUI> getList() {
		return list;
	}
	public boolean isCanSettingShift() {
		return canSettingShift;
	}
	public void setCanSettingShift(boolean canSettingShift) {
		this.canSettingShift = canSettingShift;
	}
	
	
}
