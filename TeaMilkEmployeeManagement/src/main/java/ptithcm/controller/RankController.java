package ptithcm.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


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
	SessionFactory factory;

	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	long millis = System.currentTimeMillis();
	Date date_sql = new Date(millis);
	@SuppressWarnings("deprecation")
	int month = date_sql.toLocalDate().getMonthValue();
	
	/*
	 * public List<StaffEntity> getListStaff(){ Session session =
	 * factory.getCurrentSession(); String hql = "From StaffEntity"; Query query =
	 * session.createQuery(hql); List<StaffEntity> list = query.list(); return list;
	 * }
	 */
	/*
	 * public int[] getNumOfShift() { List<StaffEntity> list = getListStaff(); int
	 * num = list.size(); int a[] = new int [num]; Session session =
	 * factory.getCurrentSession(); String hql =
	 * "Select count(*) from ShiftDetailEntity where " +
	 * "openshift.NGAYLAMVIEC.getMonth() = :month and staff.MANV = :manv"; Query
	 * query = session.createQuery(hql); for(int i = 0; i < num ;i++) {
	 * query.setString("manv",list.get(i).getMANV());
	 * query.setString(month,"month"); int result = (int) query.uniqueResult(); a[i]
	 * = result; } return a; }
	 */
	
	
	
	@RequestMapping(value="rank", method = RequestMethod.GET)
	public String viewRank(ModelMap model) {
		List<Object[]> records = getRecordStaffs();
		Collections.sort(records,new sortNumOfShift());
		model.addAttribute("list", records);
		return "Admin/Rank";

	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getRecordStaffs() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT s, "
				+ "(SELECT COUNT(*) FROM ShiftDetailEntity WHERE staff.MANV = s.MANV AND month(openshift.NGAYLAMVIEC) = :month)"
				+ " FROM StaffEntity AS s WHERE s.MANV in (SELECT TENTK FROM AccountEntity as a WHERE a.priorityEntity.MAQUYEN = 'NV')";
		Query query = session.createQuery(hql);
		query.setLong("month", Long.valueOf(month));
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
