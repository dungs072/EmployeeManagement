package ptithcm.controller;

import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
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

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;
import ptithcm.bean.StaffShiftDataUI;
import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.ShiftEntity;
import ptithcm.entity.StaffEntity;


@Transactional
@Controller
@RequestMapping(value = "/StaffTimetable")
public class StaffTimetableController {
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
	
	private StaffShiftDataUI[][] staffShiftDataUIs = new StaffShiftDataUI[3][7];

	@RequestMapping
	public String displayView(HttpServletRequest request, ModelMap map) {
		weekDateFromTo = getFirstDateAndLastDayOfCurrentWeek();
		map.addAttribute("week",weekDateFromTo);
		return displayMainView(request, map);
	}

	@RequestMapping(value = "Search",method = RequestMethod.GET)
	public String searchWeek(HttpServletRequest request, ModelMap map) {
		weekDateFromTo = request.getParameter("searchButton");
		return displayMainView(request,map);
	}
	@RequestMapping(value = "/Detail",method = RequestMethod.GET)
	public String showDetail(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String shiftDetailId = request.getParameter("detailButton");
		ShiftDetailEntity shiftDetail = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, shiftDetailId);
		map.addAttribute("shiftDetailEntity",shiftDetail);
		return displayMainView(request,map);
	}


	private String displayMainView(HttpServletRequest request, ModelMap map) {
		if (shifts == null) {
			shifts = getShifts();
		}
		resetDataUI();
		putDataToView(weekDateFromTo);
		map.addAttribute("shifts", shifts);
		map.addAttribute("shiftStaffs", staffShiftDataUIs);
		return "/Staff/workingTimeTable";
	}

	private void resetDataUI() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				staffShiftDataUIs[i][j] = null;
			}
		}
	}


	@SuppressWarnings("unchecked")
	private void putDataToView(String weekDateFromTo) {
		String ownerId = staffPassDataBetweenControllerHandler.getData();
		String[] dates = weekDateFromTo.split(" - ");
		Date baseDate = Date.valueOf(castToJavaSQLDateFormat(dates[0]));
		dates[0] = castToSQLDateFormat(dates[0]);
		dates[1] = castToSQLDateFormat(dates[1]);

		Session session = factory.getCurrentSession();

		String hql = "FROM ShiftDetailEntity WHERE openshift.NGAYLAMVIEC BETWEEN :firstDate AND :lastDate"
				+ " AND staff.MANV = :staffId AND XACNHAN = true";
		Query query = session.createQuery(hql);
		query.setString("firstDate", dates[0]);
		query.setString("lastDate", dates[1]);
		query.setString("staffId", ownerId);
		List<ShiftDetailEntity> shiftDetailList = query.list();
		
		for(var shiftDetail:shiftDetailList) {
			StaffEntity staff = shiftDetail.getStaff();
			int row = Integer.parseInt(shiftDetail.getOpenshift().getShift().getIDCA().strip())-1;
			int col = sqlDateMinsDays(baseDate,shiftDetail.getOpenshift().getNGAYLAMVIEC());
			staffShiftDataUIs[row][col] = new StaffShiftDataUI(staff.getHO()+" "+staff.getTEN(),true);
			staffShiftDataUIs[row][col].setAdditionalJob(shiftDetail.getCONGVIEC());
			staffShiftDataUIs[row][col].setShiftDetailId(shiftDetail.getID_CTCA());
		}
		

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


	private int sqlDateMinsDays(Date date1, Date date2) {
		long time_difference = date2.getTime() - date1.getTime();

		return (int) ((time_difference / (1000 * 60 * 60 * 24)) % 365);
	}

	private String castToSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2] + "/" + date[1] + "/" + date[0];
	}

	private String castToJavaSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		return date[2] + "-" + date[1] + "-" + date[0];
	}
}
