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
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/DisplayStaffMistake")
public class DisplayStaffMistakeController {
	@Autowired
	SessionFactory factory;
	
	List<StaffEntity> staffList;
	private String searchTextInput = "";
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	@RequestMapping
	public String showEmployee(ModelMap map) {
		searchTextInput = "";
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
		map.addAttribute("deleteSuccess",true);
		return displayMainView(map);
	}
	@RequestMapping(value = "/SearchStaff", method = RequestMethod.GET)
	public String searchEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchText = request.getParameter("searchInput");
		searchTextInput = searchText;
		staffList = searchStaffList(session, searchText);
		model.addAttribute("staffs", staffList);
		return returnToSpecificAccount();
	}
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchStaffList(Session session,String searchText) {
		if(searchText.isEmpty()) {return getStaffsMistake();}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE priorityEntity.MAQUYEN='NV') AND"
				+ " ((HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%')) OR"
				+ " (jobPosition.HINHTHUC LIKE CONCAT ('%',:search,'%')))"
				+ " ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}
	
	
	private boolean canDeleteSpecificMistakeHistory(MistakeHistoryEntity mistakeHistory) {
		Date currentDay = Date.valueOf(LocalDate.now());
		Date workDay = mistakeHistory.getShiftDetailEntity().getOpenshift().getNGAYLAMVIEC();
		return workDay.compareTo(currentDay)==0;
	}
	
	@SuppressWarnings("unchecked")
	private List<MistakeHistoryEntity> getSpecificMistakeHistories(ModelMap map,String staffId) {
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.staff.MANV = :staffId ORDER BY shiftDetailEntity.openshift.NGAYLAMVIEC DESC";
		Query query = session.createQuery(hql);
		query.setString("staffId",staffId);
		return query.list();
	}
	
	private String displayMainView(ModelMap map) {
		Session session = factory.getCurrentSession();
		staffList = searchStaffList(session, searchTextInput);
		map.addAttribute("staffs",staffList);
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
