package ptithcm.controller;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;
import ptithcm.entity.JobPositionEntity;
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
	@Qualifier("managerPassDataHandler")
	PassDataBetweenControllerHandler passDataBetweenControllerHandler;
	
	@Autowired
	@Qualifier("primaryKeyHandler")
	PrimaryKeyWithMoreDataHandler primaryKeyWithMoreDataHandler;
	
	private List<ShiftEntity> shifts;
	private String weekDateFromTo;

	
	private boolean[][] canDisplayCancelButtons = new boolean[3][7];
	
	
	@RequestMapping
	public String displayRegistrationTable(HttpServletRequest request,ModelMap map)
	{	setCanDisplayCancelButtons(false);
		weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		return displayMainView(request,map,weekDateFromTo);
	}
	
	@RequestMapping(value = "/Open",method = RequestMethod.GET)
	public String openSpecificShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDaySpecific = request.getParameter("openShift");
		String[] shift_Date = shiftDaySpecific.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");
		
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		ShiftDetailEntity shiftDetail = new ShiftDetailEntity(dateAfterPlus);
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, passDataBetweenControllerHandler.getData());
		ShiftEntity shift = (ShiftEntity) session.get(ShiftEntity.class,shift_Date[0]);
		
		shiftDetail.setStaff(staff);
		shiftDetail.setShift(shift);
		shiftDetail.setID_CTCA(primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(staff.getMANV(),primaryDateAfterPlus,shift.getIDCA()));
		session.save(shiftDetail);
		
		canDisplayCancelButtons[Integer.parseInt(shift_Date[0])-1][Integer.parseInt(shift_Date[1])-1] = true;
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		return functionDisplayMainView(request,map);
	}
	@RequestMapping(value = "/Cancel",method = RequestMethod.GET)
	public String cancelSpecificShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDaySpecific = request.getParameter("cancelShift");
		String managerId = passDataBetweenControllerHandler.getData();
		String[] shift_Date = shiftDaySpecific.split(",");
		String[] first_last_Date = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Date[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		
		String shiftDetailId = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(managerId,primaryDateAfterPlus,shift_Date[0]);
		ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, shiftDetailId);
		shiftDetailEntity.setShift(null);
		shiftDetailEntity.setStaff(null);
		shiftDetailEntity.setMistakeHistoryEntities(null);
		session.delete(shiftDetailEntity);
		canDisplayCancelButtons[Integer.parseInt(shift_Date[0])-1][Integer.parseInt(shift_Date[1])-1] = false;
		deleteAllStaffRegistrationsAtThisShift(primaryKeyWithMoreDataHandler.getPatternBaseOnDatas(primaryDateAfterPlus,shift_Date[0]));
		return functionDisplayMainView(request,map);
	}
	
	
	@RequestMapping(value = "/Search",method = RequestMethod.GET)
	public String searchRegistrationTable(HttpServletRequest request,ModelMap map) {
		
		weekDateFromTo = request.getParameter("searchButton");
		displayMainView(request,map,weekDateFromTo);
		return "/Manager/shiftRegistration";
	}
	
	@RequestMapping(value = "/AddStaff",method = RequestMethod.GET)
	public String addStaff(HttpServletRequest request, ModelMap map) {
		
		String shift_Date = request.getParameter("addStaffButton");
		String[] shift_Dates = shift_Date.split(",");
		String[] days = weekDateFromTo.split(" - ");
		String firstDateStr = castToJavaSQLDateFormat(days[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_Dates[1])-1);
		List<StaffEntity> staffs = getCanDoWorkStaffS(dateAfterPlus.toString(), shift_Dates[0]);
		map.addAttribute("staffs",staffs);
		return displayMainView(request,map,weekDateFromTo);
	}
	@RequestMapping(value = "/SaveAddStaff",method = RequestMethod.GET)
	public String saveAddStaff(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String staffId = request.getParameter("staffId");
		String toDoList = request.getParameter("TodoList");
		String shift_date = request.getParameter("saveAddChangeButton");
		String[] shift_dates = shift_date.split(",");
		
		String[] first_last_Date = weekDateFromTo.split(" - ");
		
		String firstDateStr = castToJavaSQLDateFormat(first_last_Date[0]);
		Date firstDate=Date.valueOf(firstDateStr);
		
		Date dateAfterPlus = sqlDatePlusDays(firstDate,Integer.parseInt(shift_dates[1])-1);
		String primaryDateAfterPlus = castToCreatePrimaryKeyFormat(dateAfterPlus.toString());
		
		ShiftDetailEntity shiftDetail = new ShiftDetailEntity(dateAfterPlus);
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, staffId.strip());
		ShiftEntity shift = (ShiftEntity) session.get(ShiftEntity.class,shift_dates[0]);
		
		shiftDetail.setStaff(staff);
		shiftDetail.setShift(shift);
		String idShiftDetail = primaryKeyWithMoreDataHandler.getPrimaryKeyBaseOnDatas(staffId.strip(), primaryDateAfterPlus, shift_dates[0]);
		shiftDetail.setID_CTCA(idShiftDetail);
		shiftDetail.setCONGVIEC(toDoList);
		session.save(shiftDetail);
		return displayMainView(request,map,weekDateFromTo);
	}
	
	private void setCanDisplayCancelButtons(boolean state) {
		for(int i =0;i<3;i++) {
			for(int j = 0;j<7;j++) {
				canDisplayCancelButtons[i][j] = state;
			}
		}
	}
	
	
	private Date sqlDatePlusDays(Date date, int days) {
	    return Date.valueOf(date.toLocalDate().plusDays(days));
	}
	private String castToSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2]+"/"+date[1]+"/"+date[0];
	}
	private String castToCreatePrimaryKeyFormat(String dateStr) {
		String[] date = dateStr.split("-");
		return date[2]+date[1]+date[0];
	}
	private String castToJavaSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2]+"-"+date[1]+"-"+date[0];
	}
	
	private void deleteAllStaffRegistrationsAtThisShift(String idPattern) {
		Session session = factory.getCurrentSession();
		String sql = "DELETE CHITIETCA WHERE ID_CTCA LIKE CONCAT('%',:idPattern)";
		Query query = session.createSQLQuery(sql);
		query.setString("idPattern", idPattern);
	}
	@SuppressWarnings("unchecked")
	private List<Object[]> getShiftDetailsData(String fromDate, String toDate){
		Session session = factory.getCurrentSession();
		String sql = "SELECT IDCA, DATEDIFF(day,:fromDate,THOIGIANDANGKI) FROM CHITIETCA "
					+ "WHERE THOIGIANDANGKI BETWEEN :fromDate AND :toDate";
		Query query = session.createSQLQuery(sql);
		query.setString("fromDate", fromDate);
		query.setString("toDate",toDate);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<ShiftEntity> getShifts(){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	private String displayMainView(HttpServletRequest request, ModelMap map,String weekDateFromTo) {
		if(shifts==null) {
			shifts = getShifts();
		}
		
		setCanDisplayCancelButtons(false);
		putDataToView(weekDateFromTo);
		map.addAttribute("weekSelection", weekDateFromTo);
		map.addAttribute("shifts",shifts);
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		return "/Manager/shiftRegistration"; 
	}
	private String functionDisplayMainView(HttpServletRequest request, ModelMap map) {
		
		if(shifts==null) {
			shifts = getShifts();
		}
		map.addAttribute("weekSelection", weekDateFromTo);
		map.addAttribute("shifts",shifts);
		map.addAttribute("canDisplayCancelButton",canDisplayCancelButtons);
		return "/Manager/shiftRegistration"; 
	}
	
	private void putDataToView(String weekDateFromTo) {
		//code here/ create object hold map for displaying
		
		List<Object[]> shiftDetailDatas = getSpecificShiftDetails(weekDateFromTo);
		for(var shiftDetailData:shiftDetailDatas) {
			Integer shiftId = Integer.parseInt(((String) shiftDetailData[0]).strip());
			Integer dayIndex = (Integer) shiftDetailData[1];
			canDisplayCancelButtons[shiftId-1][dayIndex] = true;
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<JobPositionEntity> getJobs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity WHERE TENVITRI!='Manager'";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getCanDoWorkStaffS(String date,String idCa){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity"
				+ " WHERE MANV NOT IN (SELECT staff.MANV FROM ShiftDetailEntity WHERE THOIGIANDANGKI = :date AND shift.IDCA = :idCa) "
				+" AND MANV IN(SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true AND priorityEntity.MAQUYEN = 'NV')";
		Query query = session.createQuery(hql);
		query.setString("date", date);
		query.setString("idCa", idCa);
		return query.list();
	}
	
	private List<Object[]> getSpecificShiftDetails(String week){
		String[] dates = week.split(" - ");
		dates[0] = castToSQLDateFormat(dates[0]);
		dates[1] = castToSQLDateFormat(dates[1]);
		return getShiftDetailsData(dates[0],dates[1]);
		
	}
	private String getFirstDateAndLastDayOfCurrentWeek() {
		 LocalDate now = LocalDate.now();
	     LocalDate first = now.with(previousOrSame(DayOfWeek.MONDAY));
	     LocalDate last = now.with(nextOrSame(DayOfWeek.SUNDAY));
	     DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     
	     return first.format(dateTimeFormatter)+" - "+ last.format(dateTimeFormatter);
	}
}
