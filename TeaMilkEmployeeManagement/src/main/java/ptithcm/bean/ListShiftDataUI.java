package ptithcm.bean;

import java.util.ArrayList;

public class ListShiftDataUI {
	private ArrayList<ShiftDataUI> listShiftDataUI;
	
	public ListShiftDataUI() {
		listShiftDataUI = new ArrayList();
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
	
}
