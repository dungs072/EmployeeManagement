package ptithcm.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.previousOrSame;

import static java.time.temporal.TemporalAdjusters.nextOrSame;

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
import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;
import ptithcm.bean.ShiftDataUI;
import ptithcm.entity.AccountEntity;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.entity.OpenShiftEntity;
import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.ShiftEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping(value = "/ManagerRegistration")
public class ManagerRegistrationController {

	@Autowired
	SessionFactory factory;

	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler passDataBetweenControllerHandler;

	@Autowired
	@Qualifier("primaryKeyHandler")
	PrimaryKeyWithMoreDataHandler primaryKeyWithMoreDataHandler;

	private List<ShiftEntity> shifts;
	private String weekDateFromTo;

	private boolean[][] canDisplayCancelButtons = new boolean[3][7];
	private boolean[][] canDisplayOpenButtons = new boolean[3][7];
	private ListShiftDataUI[][] shiftUIHash = new ListShiftDataUI[3][7];

	@RequestMapping
	public String displayRegistrationTable(HttpServletRequest request, ModelMap map) {
		setCanDisplayCancelButtons(false);
		weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		return displayMainView(request, map, weekDateFromTo);
	}
	
	@RequestMapping(value = "/Open",method = RequestMethod.GET)
	public String openSpecificShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDaySpecific = request.getParameter("yesOpenShift");
		int maxStaff = Integer.parseInt(request.getParameter("maxStaff"));
		String[] shift_Date = shiftDaySpecific.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");
		
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		OpenShiftEntity openShift = new OpenShiftEntity(dateAfterPlus,maxStaff);
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, passDataBetweenControllerHandler.getData());
		ShiftEntity shift = (ShiftEntity) session.get(ShiftEntity.class,shift_Date[0]);
		
		openShift.setStaff(staff);
		openShift.setShift(shift);
		openShift.setID_CA_MO(primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus,shift.getIDCA()));
		session.save(openShift);
		
		int row = Integer.parseInt(shift_Date[0])-1;
		int col = Integer.parseInt(shift_Date[1])-1;
		shiftUIHash[row][col] = new ListShiftDataUI();
		shiftUIHash[row][col].setMaxStaff(maxStaff);
		canDisplayCancelButtons[row][col] = true;
		
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		return functionDisplayMainView(request,map);
	}
	@RequestMapping(value = "/Cancel",method = RequestMethod.GET)
	public String cancelSpecificShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDaySpecific = request.getParameter("cancelShift");
		String[] shift_Date = shiftDaySpecific.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		
		String openShiftId = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus,shift_Date[0]);
		
		OpenShiftEntity openShiftEntity = (OpenShiftEntity) session.get(OpenShiftEntity.class, openShiftId);
		
		openShiftEntity.deleteAllLinks();
		for(var item:openShiftEntity.getDetailEntities()) {
			item.deleteLink();
			session.delete(item);
		}
		deleteShiftDetails(openShiftId,session);
		session.delete(openShiftEntity);
		
		int row = Integer.parseInt(shift_Date[0])-1;
		int col = Integer.parseInt(shift_Date[1])-1;
		shiftUIHash[row][col] = null;
		canDisplayCancelButtons[row][col] = false;
		return functionDisplayMainView(request,map);
	}
	
	
	@RequestMapping(value = "/Search",method = RequestMethod.GET)
	public String searchRegistrationTable(HttpServletRequest request,ModelMap map) {
		
		weekDateFromTo = request.getParameter("searchButton");
		if(weekDateFromTo.isEmpty()) {
			weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		}
		return displayMainView(request,map,weekDateFromTo);
	}

	@RequestMapping(value = "/AddStaff", method = RequestMethod.GET)
	public String addStaff(HttpServletRequest request, ModelMap map) {

		String shift_Date = request.getParameter("addStaffButton");
		String[] shift_Dates = shift_Date.split(",");
		String[] days = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(days[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Dates[1])-1);
		List<StaffEntity> staffs = getCanDoWorkStaffs(dateAfterPlus.toString(), shift_Dates[0]);
		map.addAttribute("staffs",staffs);
		return displayMainView(request,map,weekDateFromTo);
	}
	
	@RequestMapping(value = "/SaveAddStaff",method = RequestMethod.GET)
	public String saveAddStaff(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		if(request.getParameter("staffInfor")==null) {
			return functionDisplayMainView(request,map);
		}
		String toDoList = request.getParameter("TodoList");
		String[] staffInfor = (request.getParameter("staffInfor").toString()).split(",");
		String[] shift_date = (request.getParameter("saveAddChangeButton").toString()).split(",");
		String[] days = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(days[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		String openShiftId = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus,shift_date[0]);
		String shiftDetailId = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(staffInfor[0], openShiftId);
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, staffInfor[0]);
		OpenShiftEntity openShift = (OpenShiftEntity) session.get(OpenShiftEntity.class, openShiftId);
		ShiftDetailEntity shiftDetail = new ShiftDetailEntity();
		shiftDetail.setID_CTCA(shiftDetailId);
		shiftDetail.setStaff(staff);
		shiftDetail.setOpenshift(openShift);
		shiftDetail.setCONGVIEC(toDoList);
		session.save(shiftDetail);
		
		int row = Integer.parseInt(shift_date[0])-1;
		int col = Integer.parseInt(shift_date[1])-1;
		
		shiftUIHash[row][col].addShiftDataUI(new ShiftDataUI(shiftDetailId,staffInfor[1],staffInfor[2],toDoList));
		shiftUIHash[row][col].calculateLeftStaff();
		
		return functionDisplayMainView(request,map);
		
	}
	@RequestMapping(value = "/DeleteStaffFromShift",method = RequestMethod.GET)
	public String deleteStaffFromShift(HttpServletRequest request,ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDetailId = request.getParameter("yesWarningStaffButton");
		if(!checkCanDeleteStaffFromShift(shiftDetailId)) {return functionDisplayMainView(request,map);}
		ShiftDetailEntity shiftDetailEntity = getDateAndShiftId(shiftDetailId);
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		String currentDateStr = shiftDetailEntity.getOpenshift().getNGAYLAMVIEC().toString().strip();
		Date currentDate = Date.valueOf(currentDateStr);
		
		int dateIndex = sqlDateMinsDays(firstDate,currentDate);
		int shiftIndex = Integer.parseInt(shiftDetailEntity.getOpenshift().getShift().getIDCA().toString().strip())-1;
		
		shiftUIHash[shiftIndex][dateIndex].deleteShiftDataUI(shiftDetailId);
		shiftUIHash[shiftIndex][dateIndex].calculateLeftStaff();
		shiftDetailEntity.setOpenshift(null);
		shiftDetailEntity.setStaff(null);
		shiftDetailEntity.setMistakeHistoryEntities(null);
		session.delete(shiftDetailEntity);
		return functionDisplayMainView(request,map);
	}
	@RequestMapping(value = "/SettingStaffInShift",method = RequestMethod.GET)
	public String settingStaffInShift(HttpServletRequest request,ModelMap map) {
		Session session = factory.getCurrentSession();
		String toDoList = request.getParameter("toDoListInput");
		String shiftDetailId = request.getParameter("saveChangeSettingShift");
		
		ShiftDetailEntity shiftDetail = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, shiftDetailId);
		shiftDetail.setCONGVIEC(toDoList);
		session.saveOrUpdate(shiftDetail);
		
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		String currentDateStr = shiftDetail.getOpenshift().getNGAYLAMVIEC().toString().strip();
		Date currentDate = Date.valueOf(currentDateStr);
		
		int dateIndex = sqlDateMinsDays(firstDate,currentDate);
		int shiftIndex = Integer.parseInt(shiftDetail.getOpenshift().getShift().getIDCA().toString().strip())-1;
		shiftUIHash[shiftIndex][dateIndex].getShiftDataUI(shiftDetailId).setAdditionalJobs(toDoList);
		return functionDisplayMainView(request,map);
	}
	
	@RequestMapping(value = "/updateMaxStaff",method = RequestMethod.GET)
	public String setMaxStaff(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shift_date = request.getParameter("yesSettingMaxStaff");
		String openShiftId = getOpenShiftId(shift_date);
		String maxStaffStr = request.getParameter("maxStaffSetting");
		
		OpenShiftEntity openShiftEntity = (OpenShiftEntity) session.get(OpenShiftEntity.class, openShiftId);
		openShiftEntity.setSOLUONGDANGKI(Integer.parseInt(maxStaffStr));
		session.saveOrUpdate(openShiftEntity);
		
		String[] shift_dates = shift_date.split(",");
		Integer row = Integer.parseInt(shift_dates[0])-1;
		Integer col = Integer.parseInt(shift_dates[1])-1;
		shiftUIHash[row][col].setMaxStaff(Integer.parseInt(maxStaffStr));
		return functionDisplayMainView(request,map);
	}
	
	@RequestMapping(value = "/confirmShifts",method = RequestMethod.GET)
	public String confirmShifts(HttpServletRequest request,ModelMap map) {
		updateConfirmState(true);
		resetUI();
		putDataToView(weekDateFromTo);
		return displayMainViewDontDeleteOldUI(request, map);
	}
	@RequestMapping(value = "/cancelConfirmShifts",method = RequestMethod.GET)
	public String cancelConfirmShifts(HttpServletRequest request,ModelMap map){
		updateConfirmState(false);
		return displayMainViewDontDeleteOldUI(request, map);
	}
	@RequestMapping(value = "/showShiftDetail",method = RequestMethod.GET)
	public String showShiftDetail(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDetailId = request.getParameter("shiftDetailButton");
		ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, shiftDetailId);
		List<MistakeHistoryEntity> mistakeHistories = getMistakeHistories(shiftDetailId.strip(), session);
		map.addAttribute("mistakeHistories",mistakeHistories);
		map.addAttribute("shiftDetailEntity",shiftDetailEntity);
		return displayMainViewDontDeleteOldUI(request, map);
	}
	private List<MistakeHistoryEntity> getMistakeHistories(String shiftDetailId,Session session){
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.ID_CTCA = :shiftDetailId";
		Query query = session.createQuery(hql);
		query.setString("shiftDetailId", shiftDetailId);
		return query.list();
	}
	private void updateConfirmState(boolean state) {
		Session session = factory.getCurrentSession();
		long millis=System.currentTimeMillis();  
		Date currentDate = new Date(millis);
		currentDate = sqlDatePlusDays(currentDate, 1);
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		String lastSateStr = castToJavaSQLDateFormat(first_last_Date[1]);
		Date lastDate=Date.valueOf(lastSateStr);
		Date firstDateCanChoose = null;
		if(firstDate.compareTo(currentDate)==1) {
			firstDateCanChoose = firstDate;
		}
		else {
			firstDateCanChoose = currentDate;
		}
		
		List<ShiftDetailEntity> shiftDetails = getShiftDetails(firstDateCanChoose.toString(),lastDate.toString());
		for(var shiftDetail: shiftDetails) {
			shiftDetail.setXACNHAN(state);
			session.saveOrUpdate(shiftDetail);
			int row = Integer.parseInt(shiftDetail.getOpenshift().getShift().getIDCA().strip())-1;
			int col = sqlDateMinsDays(firstDate, shiftDetail.getOpenshift().getNGAYLAMVIEC());
			shiftUIHash[row][col].getShiftDataUI(shiftDetail.getID_CTCA().strip()).setConfirmed(state);
		}
		if(state) {
			deleteUnconfirmationShifts(firstDate,firstDateCanChoose);
		}

	}
	@SuppressWarnings("unchecked")
	private void deleteUnconfirmationShifts(Date firstDay,Date currentDay) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity WHERE openshift.NGAYLAMVIEC BETWEEN :firstDay AND :currentDay AND"
				+ " XACNHAN = false";
		Query query=  session.createQuery(hql);
		query.setString("firstDay", firstDay.toString());
		query.setString("currentDay", currentDay.toString());
		List<ShiftDetailEntity> list = query.list();
		for(var shift:list) {
			shift.deleteLink();
			session.delete(shift);
		}
	}
	private void getConfirmState() {
		Session session = factory.getCurrentSession();
		long millis=System.currentTimeMillis();  
		Date currentDate = new Date(millis);
		currentDate = sqlDatePlusDays(currentDate, 1);
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		String lastSateStr = castToJavaSQLDateFormat(first_last_Date[1]);
		Date lastDate=Date.valueOf(lastSateStr);
		Date firstDateCanChoose = null;
		if(firstDate.compareTo(currentDate)==1) {
			firstDateCanChoose = firstDate;
		}
		else {
			firstDateCanChoose = currentDate;
		}
		
		List<ShiftDetailEntity> shiftDetails = getShiftDetails(firstDateCanChoose.toString(),lastDate.toString());
		for(var shiftDetail: shiftDetails) {
			int row = Integer.parseInt(shiftDetail.getOpenshift().getShift().getIDCA().strip())-1;
			int col = sqlDateMinsDays(firstDate, shiftDetail.getOpenshift().getNGAYLAMVIEC());
			shiftUIHash[row][col].getShiftDataUI(shiftDetail.getID_CTCA().strip()).setConfirmed(shiftDetail.isXACNHAN());
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
	private void setCanInteractWithUI() {
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
	    long millis=System.currentTimeMillis();  
		Date currentDate = new Date(millis);
		int dateIndex = sqlDateMinsDays(firstDate,currentDate);
		for(int i =0;i<3;i++) {
			for(int j =0;j<7;j++) {
				if(j<=dateIndex) {
					canDisplayOpenButtons[i][j] = false;
				}
				else {
					canDisplayOpenButtons[i][j] = true;
				}
				if(shiftUIHash[i][j]==null) {continue;}
				if(j<=dateIndex) {
					shiftUIHash[i][j].setCanInteract(false);
					
				}
				else {
					shiftUIHash[i][j].setCanInteract(true);
					
				}
			}
		}
		
	}
	
	private String getOpenShiftId(String shift_date) {
		String[] shift_Date = shift_date.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");
		
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		return primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(primaryDateAfterPlus,shift_Date[0]);
	}
	
	private boolean checkCanDeleteStaffFromShift(String shiftDetailId) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM ShiftDetailEntity AS S WHERE S.ID_CTCA = :shiftDetailId"
				+ " AND S.mistakeHistoryEntities IS NOT EMPTY";
		Query query =session.createQuery(hql);
		query.setString("shiftDetailId", shiftDetailId);
		return (long)query.uniqueResult()==0;
		
	}
	
	@SuppressWarnings("unchecked")
	private ShiftDetailEntity getDateAndShiftId(String shiftDetailId){
		
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity WHERE ID_CTCA = :shiftDetailId";
		Query query = session.createQuery(hql);
		query.setString("shiftDetailId",shiftDetailId);
		List<ShiftDetailEntity> list =  query.list();
		return list.get(0);
	}
	
//	private boolean canDeleteShiftDetailId = 
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getCanDoWorkStaffs(String dateAfterPlus,String idCa){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE "
				+ "MANV NOT IN (SELECT staff.MANV FROM ShiftDetailEntity WHERE openshift.NGAYLAMVIEC = :dateAfterPlus"
				+ "	AND openshift.shift.IDCA = :idCa) "
				+ " AND MANV IN (SELECT TENTK FROM AccountEntity WHERE priorityEntity.MAQUYEN = 'NV' AND TRANGTHAI = true)";
		
		Query query = session.createQuery(hql);
		query.setString("dateAfterPlus", dateAfterPlus);
		query.setString("idCa",idCa);
		return query.list();
	}
	private void deleteShiftDetails(String openShiftId, Session session) {
		String sql = "DELETE FROM CHITIETDANGKICA WHERE ID_CTCA LIKE CONCAT('%',:openShiftId)";
		Query query = session.createSQLQuery(sql);
		query.setString("openShiftId", openShiftId);
		query.executeUpdate();
	}
	
	private void setCanDisplayCancelButtons(boolean state) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				canDisplayCancelButtons[i][j] = state;
			}
		}
	}

	private Date sqlDatePlusDays(Date date, int days) {
		return Date.valueOf(date.toLocalDate().plusDays(days));
	}

	private int sqlDateMinsDays(Date date1, Date date2) {
		long time_difference = date2.getTime() - date1.getTime();
		return (int) Math.floor(((float)time_difference)/(1000.0*60.0*60.0*24.0)%365.0);
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
	@SuppressWarnings("unchecked")
	private List<ShiftEntity> getShifts() {
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	private String displayMainViewDontDeleteOldUI(HttpServletRequest request, ModelMap map) {
		if(shifts==null) {
			shifts = getShifts();
		}
		setCanInteractWithUI();
		//putStaffRegistrationDataToView();

		map.addAttribute("weekSelection", weekDateFromTo);
		map.addAttribute("shifts",shifts);
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		map.addAttribute("canDisplayOpenButton",canDisplayOpenButtons);
		map.addAttribute("shiftStaffs",shiftUIHash);
		return "/Manager/shiftRegistration"; 
	}
	private String displayMainView(HttpServletRequest request, ModelMap map,String weekDateFromTo) {
		if(shifts==null) {
			shifts = getShifts();
		}
		
		resetUI();
		putDataToView(weekDateFromTo);
		getConfirmState();
		setCanInteractWithUI();
		//putStaffRegistrationDataToView();
		map.addAttribute("weekSelection", weekDateFromTo);
		map.addAttribute("shifts",shifts);
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		map.addAttribute("canDisplayOpenButton",canDisplayOpenButtons);
		map.addAttribute("shiftStaffs",shiftUIHash);
		return "/Manager/shiftRegistration"; 
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
		for(var openShifts:openShiftss) {
			OpenShiftEntity openShift = (OpenShiftEntity) openShifts[0];
			
			Integer shiftIndex = Integer.parseInt(openShift.getShift().getIDCA().strip());
			String currentDateStr = (openShift.getNGAYLAMVIEC().toString().strip());
			Date currentDate = Date.valueOf(currentDateStr);
			int dateIndex = sqlDateMinsDays(baseDate,currentDate);
			Integer maxStaff = openShift.getSOLUONGDANGKI();
			
			if(shiftUIHash[shiftIndex-1][dateIndex]==null)
			{
				shiftUIHash[shiftIndex-1][dateIndex] = new ListShiftDataUI();
			}
			shiftUIHash[shiftIndex-1][dateIndex].setMaxStaff(maxStaff);
			canDisplayCancelButtons[shiftIndex-1][dateIndex] = true;
			if(openShifts[1]!=null) {
				ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) openShifts[1];
				StaffEntity staff = shiftDetailEntity.getStaff();
				AccountEntity account = (AccountEntity) session.get(AccountEntity.class, staff.getMANV().strip());
				String fullName = staff.getHO().strip()+" "+staff.getTEN().strip();
				String jobName = staff.getJobPosition().getTENVITRI().strip();
				ShiftDataUI dataUI = new ShiftDataUI(shiftDetailEntity.getID_CTCA().strip(),fullName,jobName,shiftDetailEntity.getCONGVIEC());
				dataUI.setConfirmed(shiftDetailEntity.isXACNHAN());
				dataUI.setEnable(account.isTRANGTHAI());
				shiftUIHash[shiftIndex-1][dateIndex].addShiftDataUI(dataUI);
				shiftUIHash[shiftIndex-1][dateIndex].calculateLeftStaff();
			}
			
		}
	
	}

	private void resetUI() {
		for(int i =0;i<3;i++) {
			for(int j =0;j<7;j++) {

				shiftUIHash[i][j] = null;
			}
		}
		setCanDisplayCancelButtons(false);
	}
	private String functionDisplayMainView(HttpServletRequest request, ModelMap map) {
		
		if(shifts==null) {
			shifts = getShifts();
		}
		map.addAttribute("weekSelection", weekDateFromTo);
		map.addAttribute("shifts",shifts);
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		map.addAttribute("shiftStaffs",shiftUIHash);
		map.addAttribute("canDisplayOpenButton",canDisplayOpenButtons);
		return "/Manager/shiftRegistration"; 
	}
	

	private String getFirstDateAndLastDayOfCurrentWeek() {
		LocalDate now = LocalDate.now();
		LocalDate first = now.with(previousOrSame(DayOfWeek.MONDAY));
		LocalDate last = now.with(nextOrSame(DayOfWeek.SUNDAY));
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return first.format(dateTimeFormatter) + " - " + last.format(dateTimeFormatter);
	}
}
