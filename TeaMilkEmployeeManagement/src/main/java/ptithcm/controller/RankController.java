package ptithcm.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import ptithcm.bean.PassDataBetweenControllerHandler;


@Transactional
@Controller
@RequestMapping("/Rank")
public class RankController {
	
	@Autowired
	SessionFactory factory;

	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	@RequestMapping()
	public String viewRank() {
		List<Object[]> staffs = getRecordStaffs();
		
		for(var staff:staffs) {
			System.out.println(staff[0]+" "+staff[1]);
		}
		return returnToSpecificAccount();
	}
	
	@SuppressWarnings("unchecked")
	private List<Object[]> getRecordStaffs() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT s.MANV, (SELECT COUNT(*) FROM ShiftDetailEntity WHERE staff.MANV = s.MANV) "
				+ " FROM StaffEntity AS s";
		Query query = session.createQuery(hql);
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
