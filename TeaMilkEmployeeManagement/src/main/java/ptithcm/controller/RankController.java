package ptithcm.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ptithcm.bean.PassDataBetweenControllerHandler;
class sortNumOfShift implements Comparator<Object[]> {

	@Override
	public int compare(Object[] o1, Object[] o2) {
		if ((long) o1[1] > (long) o2[1]) {
			return -1;
		} else if ((long) o1[1] < (long) o2[1]) {
			return 1;
		} else {
			return 0;
		}
	}
}

@Controller
@Transactional
public class RankController {

	@Autowired
	SessionFactory factory;

	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;

	@RequestMapping(value = "rank", method = RequestMethod.GET)
	public String viewRank(ModelMap model) {
		long millis = System.currentTimeMillis();
		Date date_sql = new Date(millis);

		int month = date_sql.toLocalDate().getMonthValue();
		int year = date_sql.toLocalDate().getYear();
		String monthStr = date_sql.toLocalDate().getMonth().toString();
		String monthSelection = monthStr+",   "+Integer.toString(year);
		List<Object[]> records = getRecordStaffs(month, year,date_sql);
		Collections.sort(records, new sortNumOfShift());
		model.addAttribute("list", records);
		model.addAttribute("monthSelection",monthSelection);
		return returnToSpecificAccount();
	}

	@RequestMapping(value = "showInMonth", method = RequestMethod.GET)
	public String showInMonth(HttpServletRequest request, ModelMap model) {
		String getday ="1 "+request.getParameter("startDate");
		long millis = System.currentTimeMillis();
		Date date_sql = new Date(millis);
		int month = date_sql.toLocalDate().getMonthValue();
		int year = date_sql.toLocalDate().getYear();
		if(!getday.equals("1 ")) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
			LocalDate date = LocalDate.parse(getday, formatter);
			month = date.getMonthValue();
			year = date.getYear();
		}
		List<Object[]> records = getRecordStaffs(month, year,date_sql);
		Collections.sort(records, new sortNumOfShift());
		
		String monthStr = Month.of(month).name();
		String monthSelection = monthStr+",   "+Integer.toString(year);
		model.addAttribute("list", records);
		model.addAttribute("monthSelection",monthSelection);
		return returnToSpecificAccount();
	}

	public List<Object[]> getRecordStaffs(int month, int year,Date currentDate) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT s, (SELECT COUNT(*) FROM ShiftDetailEntity "
				+ "WHERE staff.MANV = s.MANV AND month(openshift.NGAYLAMVIEC) = :month AND year(openshift.NGAYLAMVIEC) = :year "
				+ " AND openshift.NGAYLAMVIEC<:currentDate)"
				+ " FROM StaffEntity AS s WHERE s.MANV in (SELECT TENTK FROM AccountEntity as a WHERE a.priorityEntity.MAQUYEN = 'NV')";
		Query query = session.createQuery(hql);
		query.setLong("month", Long.valueOf(month));
		query.setLong("year", Long.valueOf(year));
		query.setString("currentDate", currentDate.toString());
		return query.list();
	}

	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if (priority.equals("AD")) {
			return "/Admin/Rank";
		} else if (priority.equals("QL")) {
			return "/Manager/Rank";
		} else {
			return "/Staff/Rank";
		}
	}
}
