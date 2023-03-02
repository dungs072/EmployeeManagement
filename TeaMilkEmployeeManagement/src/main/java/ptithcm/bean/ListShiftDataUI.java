package ptithcm.bean;

import java.util.ArrayList;

public class ListShiftDataUI {
	private ArrayList<ShiftDataUI> listShiftDataUI;
	
	private int maxStaff = 0;
	
	private int leftStaff = 0;
	
	private boolean canInteract = true;
	
	
	public ListShiftDataUI() {
		listShiftDataUI = new ArrayList<ShiftDataUI>();
	}

	public ArrayList<ShiftDataUI> getListShiftDataUI() {
		return listShiftDataUI;
	}

	public void setListShiftDataUI(ArrayList<ShiftDataUI> listShiftDataUI) {
		this.listShiftDataUI = listShiftDataUI;
	}

	public void addShiftDataUI(ShiftDataUI data) {
		listShiftDataUI.add(data);
	}
	public void deleteShiftDataUI(String shiftDetail) {
		for(var shiftDataUI:listShiftDataUI) {
			if(shiftDataUI.getShiftDetailId().equals(shiftDetail))
			{
				listShiftDataUI.remove(shiftDataUI);
				break;
			}
		}
	}
	public ShiftDataUI getShiftDataUI(String shiftDetail) {
		for(var shiftDataUI:listShiftDataUI) {
			if(shiftDataUI.getShiftDetailId().equals(shiftDetail))
			{
				return shiftDataUI;
			}
		}
		return null;
	}
	public int getMaxStaff() {
		return maxStaff;
	}
	public void setMaxStaff(int maxStaff) {
		this.maxStaff = maxStaff;
		calculateLeftStaff();
	}
	public int getLeftStaff() {
		return leftStaff;
	}
	public void setLeftStaff(int leftStaff) {
		this.leftStaff = leftStaff;
	}
	
	public int getNumberStaffInShift() {
		return listShiftDataUI.size();
	}
	public void calculateLeftStaff()
	{
		setLeftStaff(maxStaff - listShiftDataUI.size());
	}
	public boolean isCanInteract() {
		return canInteract;
	}
	public void setCanInteract(boolean canInteract) {
		this.canInteract = canInteract;
	}
	
}
