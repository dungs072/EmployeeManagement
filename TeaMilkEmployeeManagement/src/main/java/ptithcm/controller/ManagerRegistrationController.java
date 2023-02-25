package ptithcm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.ShiftEntity;

@Transactional
@Controller
@RequestMapping(value = "/ManagerRegistration")
public class ManagerRegistrationController {

	@Autowired
	SessionFactory factory;
	
	private List<ShiftEntity> shifts;
	
	
	@RequestMapping
	public String displayRegistrationTable(HttpServletRequest request,ModelMap map)
	{	
		return displayMainView(request,map);
	}
	
//	@RequestMapping(value = "/Open",method = RequestMethod.GET)
//	public String openSpecificShift(HttpServletRequest request, ModelMap map) {
//		
//	}
	
	
	@RequestMapping(value = "/Search",method = RequestMethod.GET)
	public String searchRegistrationTable(HttpServletRequest request,ModelMap map) {
		
		String weekFromTo = request.getParameter("week");
		String[] dates = weekFromTo.split(" - ");
		//dates[0] = castToSQLDateFormat(dates[0]);
		//dates[1] = castToSQLDateFormat(dates[1]);
		//List<ShiftDetailEntity> shiftDetails = getSpecificShiftDetails(dates[0],dates[1]);
		map.addAttribute("shifts",shifts);
		//putDataToView(map,shiftDetails);
		return "/Manager/shiftRegistration";
	}
	private String castToSQLDateFormat(String dateStr) {
		String[] date = dateStr.split("/");
		System.out.println(dateStr);
		return date[2]+"/"+date[1]+"/"+date[0];
	}
	
	
	private void putDataToView(ModelMap map,List<ShiftDetailEntity> shiftDetails) {
		List<ShiftDetailEntity> shifts1 = new ArrayList<ShiftDetailEntity>();
		List<ShiftDetailEntity> shifts2 = new ArrayList<ShiftDetailEntity>();
		List<ShiftDetailEntity> shifts3 = new ArrayList<ShiftDetailEntity>();
		for(var shiftDetail:shiftDetails) {
			if(shiftDetail.getShift().getIDCA().equals("1")) {
				shifts1.add(shiftDetail);
			}
			else if(shiftDetail.getShift().getIDCA().equals("2")) {
				shifts2.add(shiftDetail);
			}
			else if(shiftDetail.getShift().getIDCA().equals("3")) {
				shifts3.add(shiftDetail);
			}
		}
		//Map<String,Map<Date,ShiftDetail>> map = new HashMap()
		
	}
	
	private List<ShiftDetailEntity> getSpecificShiftDetailsBaseOnShiftNumber(String shiftNumber,List<ShiftDetailEntity> shiftDetails){
		List<ShiftDetailEntity> shifts = new ArrayList<ShiftDetailEntity>();
		for(var shiftDetail:shiftDetails) {
			if(shiftDetail.getShift().getIDCA().equals(shiftNumber)) {
				shifts.add(shiftDetail);
			}
		}
		return shifts;
	}
	
	@SuppressWarnings("unchecked")
	private List<ShiftDetailEntity> getSpecificShiftDetails(String fromDate, String toDate){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity WHERE THOIGIANDANGKI BETWEEN :fromDate AND :toDate";
		Query query = session.createQuery(hql);
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

	private String displayMainView(HttpServletRequest request, ModelMap map) {
		if(shifts==null) {
			shifts = getShifts();
		}
		map.addAttribute("shifts",shifts);
		return "/Manager/shiftRegistration"; 
	}
	

	
	
}
