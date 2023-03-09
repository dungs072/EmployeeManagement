package ptithcm.controller;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.ListShiftDataUI;
import ptithcm.bean.ListStaffShiftDataUI;
import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;
import ptithcm.bean.ShiftDataUI;
import ptithcm.bean.StaffShiftDataUI;
import ptithcm.entity.OpenShiftEntity;
import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.ShiftEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping(value = "/StaffRegisterShift")
public class StaffRegisterShiftController {
	@Autowired
	SessionFactory factory;

	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;

	@Autowired
	@Qualifier("primaryKeyHandler")
	PrimaryKeyWithMoreDataHandler primaryKeyWithMoreDataHandler;

	private List<ShiftEntity> shifts;
	private String weekDateFromTo;

	private ListStaffShiftDataUI[][] shiftUIHash = new ListStaffShiftDataUI[3][7];
	private boolean[][] canRegisterList = new boolean[3][7];

	@RequestMapping
	public String displayView(HttpServletRequest request, ModelMap map) {
		weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		return displayMainView(request, map);
	}

	@RequestMapping(value = "Register", method = RequestMethod.GET)
	public String registerShift(HttpServletRequest request, ModelMap map) {
		String ownerId = staffPassDataBetweenControllerHandler.getData();
		Session session = factory.getCurrentSession();
		String shift_date = request.getParameter("registerShift");
		String[] shift_Date = shift_date.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");

		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate = Date.valueOf(firstDateStr);

		Date dateAfterPlus = sqlDatePlusDays(firstDate, Integer.parseInt(shift_Date[1]) - 1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		String openShiftId = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus,
				shift_Date[0]);
		OpenShiftEntity openShiftEntity = (OpenShiftEntity) session.get(OpenShiftEntity.class, openShiftId);
		StaffEntity staffEntity = (StaffEntity) session.get(StaffEntity.class, ownerId);
		ShiftDetailEntity shiftDetailEntity = new ShiftDetailEntity();
		shiftDetailEntity.setStaff(staffEntity);
		shiftDetailEntity.setOpenshift(openShiftEntity);
		shiftDetailEntity.setID_CTCA(primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(ownerId, openShiftId));
		session.save(shiftDetailEntity);

		int row = Integer.parseInt(shift_Date[0]) - 1;
		int col = Integer.parseInt(shift_Date[1]) - 1;

		if (shiftUIHash[row][col] == null) {
			shiftUIHash[row][col] = new ListStaffShiftDataUI();
		}

		StaffShiftDataUI staffDataUI = new StaffShiftDataUI(
				staffEntity.getHO().strip() + " " + staffEntity.getTEN().strip(), false);
		staffDataUI.setShiftDetailId(shiftDetailEntity.getID_CTCA().strip());
		staffDataUI.setOwned(true);
		shiftUIHash[row][col].addStaffShiftDataUI(staffDataUI);
		shiftUIHash[row][col].calculateLeftStaff(openShiftEntity.getSOLUONGDANGKI());
		shiftUIHash[row][col].setCanSettingShift(true);
		canRegisterList[row][col] = false;
		return functionDisplayMainView(request, map);
	}

	@RequestMapping(value = "CancelRegistration",method = RequestMethod.GET)
	public String cancelRegistrationShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDetailId = request.getParameter("yesWarningStaffButton");
		
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, shiftDetailId);
		OpenShiftEntity openShiftEntity = shiftDetailEntity.getOpenshift();
		shiftDetailEntity.deleteLink();
		session.delete(shiftDetailEntity);
		int col = sqlDateMinsDays(firstDate,openShiftEntity.getNGAYLAMVIEC());
		int row = Integer.parseInt(openShiftEntity.getShift().getIDCA().strip())-1;

		shiftUIHash[row][col].deleteStaffShiftDataUI(shiftDetailId);
		shiftUIHash[row][col].calculateLeftStaff(openShiftEntity.getSOLUONGDANGKI());
		canRegisterList[row][col] = true;
		return functionDisplayMainView(request,map);
	}
	
	@RequestMapping(value = "Search",method = RequestMethod.GET)
	public String searchWeek(HttpServletRequest request, ModelMap map) {
		weekDateFromTo = request.getParameter("searchButton");
		if(weekDateFromTo.isBlank()) {
			weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		}
		return displayMainView(request,map);
	}

	private String getOpenShiftId(String shift_date) {
		String[] shift_Date = shift_date.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");

		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate = Date.valueOf(firstDateStr);

		Date dateAfterPlus = sqlDatePlusDays(firstDate, Integer.parseInt(shift_Date[1]) - 1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		return primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus, shift_Date[0]);
	}

	private String displayMainView(HttpServletRequest request, ModelMap map) {
		if (shifts == null) {
			shifts = getShifts();
		}
		resetDataUI();
		
		putDataToView(weekDateFromTo);
		map.addAttribute("shifts", shifts);
		map.addAttribute("shiftStaffs", shiftUIHash);
		map.addAttribute("canRegisterShift", canRegisterList);
		map.addAttribute("week",weekDateFromTo);
		return "/Staff/registerShift";
	}

	private String functionDisplayMainView(HttpServletRequest request, ModelMap map) {
		if (shifts == null) {
			shifts = getShifts();
		}
		map.addAttribute("shifts", shifts);
		map.addAttribute("shiftStaffs", shiftUIHash);
		map.addAttribute("canRegisterShift", canRegisterList);
		return "/Staff/registerShift";

	}

	private void resetDataUI() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				shiftUIHash[i][j] = null;
			}
		}
		setCanRegisterList(false);
	}

	private void setCanRegisterList(boolean state) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				canRegisterList[i][j] = state;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void putDataToView(String weekDateFromTo) {
		String[] dates = weekDateFromTo.split(" - ");
		Date baseDate = Date.valueOf(castToJavaSQLDateFormat(dates[0]));
		dates[0] = castToSQLDateFormat(dates[0]);
		dates[1] = castToSQLDateFormat(dates[1]);
		Session session = factory.getCurrentSession();

		String hql = "FROM OpenShiftEntity AS OSE LEFT JOIN OSE.shiftDetailEntites AS SDE"
				+ " WHERE NGAYLAMVIEC BETWEEN :firstDate AND :lastDate";
		Query query = session.createQuery(hql);
		query.setString("firstDate", dates[0]);
		query.setString("lastDate", dates[1]);
		List<Object[]> openShiftss = query.list();
		String ownerId = staffPassDataBetweenControllerHandler.getData();
		
		//get firstDate
		long millis=System.currentTimeMillis();  
		Date currentDateNow = new Date(millis);
		currentDateNow = sqlDatePlusDays(currentDateNow, 1);
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		Date firstDateCanChoose = null;
		if(firstDate.compareTo(currentDateNow)==1) {
			firstDateCanChoose = firstDate;
		}
		else {
			firstDateCanChoose = currentDateNow;
		}
		//get firstDate
		
		for (var openShifts : openShiftss) {
			OpenShiftEntity openShift = (OpenShiftEntity) openShifts[0];

			Integer shiftIndex = Integer.parseInt(openShift.getShift().getIDCA().strip());
			String currentDateStr = (openShift.getNGAYLAMVIEC().toString().strip());
			Date currentDate = Date.valueOf(currentDateStr);
			int dateIndex = sqlDateMinsDays(baseDate, currentDate);
			Integer maxStaff = openShift.getSOLUONGDANGKI();

			if (shiftUIHash[shiftIndex - 1][dateIndex] == null) {
				shiftUIHash[shiftIndex - 1][dateIndex] = new ListStaffShiftDataUI();
			}
			canRegisterList[shiftIndex - 1][dateIndex] = true;
			if (openShifts[1] != null) {
				ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) openShifts[1];
				StaffEntity staff = shiftDetailEntity.getStaff();
				String fullName = staff.getHO().strip() + " " + staff.getTEN().strip();
				StaffShiftDataUI dataUI = new StaffShiftDataUI(fullName, shiftDetailEntity.isXACNHAN());
				dataUI.setOwned(shiftDetailEntity.getStaff().getMANV().strip()
						.equals(staffPassDataBetweenControllerHandler.getData()));
				dataUI.setStaffId(staff.getMANV().strip());
				dataUI.setShiftDetailId(shiftDetailEntity.getID_CTCA().strip());
				shiftUIHash[shiftIndex - 1][dateIndex].addStaffShiftDataUI(dataUI);

				if (shiftUIHash[shiftIndex - 1][dateIndex].getStaffShitDataUI(ownerId) != null) {
					canRegisterList[shiftIndex - 1][dateIndex] = false;
				}

			}
			shiftUIHash[shiftIndex - 1][dateIndex].calculateLeftStaff(maxStaff);
			if(firstDateCanChoose.compareTo(openShift.getNGAYLAMVIEC())==1) {
				shiftUIHash[shiftIndex-1][dateIndex].setCanSettingShift(false);
			}
			else
			{
				shiftUIHash[shiftIndex-1][dateIndex].setCanSettingShift(true);
			}
			
		}

	}
	@SuppressWarnings("unchecked")
	private List<ShiftDetailEntity> getShiftDetails(String firstDay,String lastDay){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity WHERE openshift.NGAYLAMVIEC BETWEEN :firstDay AND :lastDay";
		Query query =session.createQuery(hql);
		query.setString("firstDay", firstDay);
		query.setString("lastDay", lastDay);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	private List<ShiftEntity> getShifts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	private String getFirstDateAndLastDayOfCurrentWeek() {
		LocalDate now = LocalDate.now();
		LocalDate first = now.with(previousOrSame(DayOfWeek.MONDAY));
		LocalDate last = now.with(nextOrSame(DayOfWeek.SUNDAY));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return first.format(dateTimeFormatter) + " - " + last.format(dateTimeFormatter);
	}

	private Date sqlDatePlusDays(Date date, int days) {
		return Date.valueOf(date.toLocalDate().plusDays(days));
	}

	private int sqlDateMinsDays(Date date1, Date date2) {
		long time_difference = date2.getTime() - date1.getTime();

		return (int) ((time_difference / (1000 * 60 * 60 * 24)) % 365);
	}

	private String castToSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2] + "/" + date[1] + "/" + date[0];
	}

	private String castToCreatePrimaryKeyFormat(String dateStr) {
		String[] date = dateStr.split("-");
		return date[2] + date[1] + date[0];
	}

	private String castToJavaSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2] + "-" + date[1] + "-" + date[0];
	}

}
