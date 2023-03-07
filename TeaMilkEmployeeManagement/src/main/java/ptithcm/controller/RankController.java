package ptithcm.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.entity.StaffEntity;


class sortNumOfShift implements Comparator<Object[]>{

	@Override
	public int compare(Object[] o1, Object[] o2) {
		if((long)o1[1] > (long)o2[1]) {
			return -1;
		}
		else if((long)o1[1]<(long)o2[1]) {
			return 1;
		}
		else {
		return 0;
		}
	}
}


@Controller
@Transactional
public class RankController  {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	@RequestMapping(value="rank", method = RequestMethod.GET)
	public String viewRank(ModelMap model) {
		long millis = System.currentTimeMillis();
		Date date_sql = new Date(millis);
		
		int month = date_sql.toLocalDate().getMonthValue();
		int year = date_sql.toLocalDate().getYear();
		List<Object[]> records = getRecordStaffs(month, year);
		Collections.sort(records,new sortNumOfShift());
		model.addAttribute("list", records);
		return "Admin/Rank";

	}
	
	/*
	 * @RequestMapping (value = "showInMonth", method = RequestMethod.GET) public
	 * String showInMonth(HttpServletRequest request, ModelMap model) { String
	 * getday = request.getParameter("startDate"); System.out.println(getday); long
	 * date1 = Date.parse(getday); Date date = new Date(date1); int month =
	 * date.toLocalDate().getMonthValue(); int year = date.toLocalDate().getYear();
	 * List<Object[]> records = getRecordStaffs(month, year);
	 * Collections.sort(records,new sortNumOfShift()); model.addAttribute("list",
	 * records); return "Admin/Rank"; }
	 */
	
	public List<Object[]> getRecordStaffs(int month, int year) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT s, (SELECT COUNT(*) FROM ShiftDetailEntity "
				+ "WHERE staff.MANV = s.MANV AND month(openshift.NGAYLAMVIEC) = :month AND year(openshift.NGAYLAMVIEC) = :year)"
				+ " FROM StaffEntity AS s WHERE s.MANV in (SELECT TENTK FROM AccountEntity as a WHERE a.priorityEntity.MAQUYEN = 'NV')";
		Query query = session.createQuery(hql);
		query.setLong("month", Long.valueOf(month));
		query.setLong("year", Long.valueOf(year));
		return query.list();
	}

	
	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "/Admin/Rank";
		}
		else if(priority.equals("QL")){
			return "/Manager/Rank";
		}
		else {
			return "/Staff/Rank";
		}
	}
}
