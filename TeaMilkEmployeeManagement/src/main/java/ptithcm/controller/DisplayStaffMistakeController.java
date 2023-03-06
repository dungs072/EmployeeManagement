package ptithcm.controller;

import java.sql.Date;
import java.time.LocalDate;
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

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/DisplayStaffMistake")
public class DisplayStaffMistakeController {
	@Autowired
	SessionFactory factory;
	
	List<StaffEntity> staffs;
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	@RequestMapping
	public String showEmployee(ModelMap map) {
		return displayMainView(map);
		
	}
	
	@RequestMapping(value = "/ShowMistake")
	public String showEmployeeMistake(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String staffId = request.getParameter("staffId");
		List<MistakeHistoryEntity> mistakeHistoryList = getSpecificMistakeHistories(map,staffId);
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, staffId);
		map.addAttribute("specificStaff",staff);
		for(var mistakeHistory:mistakeHistoryList) {
			mistakeHistory.setCanDelete(canDeleteSpecificMistakeHistory(mistakeHistory));
		}
		map.addAttribute("mistakeHistoryList",mistakeHistoryList);
		return displayMainView(map);
	}
	@RequestMapping(value = "/DeleteMistake")
	public String deleteEmployeeMistake(HttpServletRequest request,ModelMap map) {
		Session session = factory.getCurrentSession();
		String mistakeHistoryId = request.getParameter("yesDeleteMistake");
		MistakeHistoryEntity mistakeHistory = (MistakeHistoryEntity) session.get(MistakeHistoryEntity.class, mistakeHistoryId);
		if(canDeleteSpecificMistakeHistory(mistakeHistory)){
			mistakeHistory.deleteLink();
			session.delete(mistakeHistory);
		}
		return displayMainView(map);
	}
	
	private boolean canDeleteSpecificMistakeHistory(MistakeHistoryEntity mistakeHistory) {
		Date currentDay = Date.valueOf(LocalDate.now());
		Date workDay = mistakeHistory.getShiftDetailEntity().getOpenshift().getNGAYLAMVIEC();
		return workDay.compareTo(currentDay)==0;
	}
	
	@SuppressWarnings("unchecked")
	private List<MistakeHistoryEntity> getSpecificMistakeHistories(ModelMap map,String staffId) {
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.staff.MANV = :staffId";
		Query query = session.createQuery(hql);
		query.setString("staffId",staffId);
		return query.list();
	}
	
	private String displayMainView(ModelMap map) {
		staffs = getStaffsMistake();
		map.addAttribute("staffs",staffs);
		return returnToSpecificAccount();
	}
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getStaffsMistake(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV IN "
				+ " (SELECT TENTK FROM AccountEntity WHERE priorityEntity.MAQUYEN='NV')";
		Query query = session.createQuery(hql);
		return query.list();
		
	}
	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "Admin/DisplayStaffInforMistake";
		}
		else{
			return "Manager/DisplayStaffInforMistake";
		}
	}
}
