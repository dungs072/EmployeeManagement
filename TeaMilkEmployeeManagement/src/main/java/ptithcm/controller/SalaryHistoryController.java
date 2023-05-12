package ptithcm.controller;

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
import ptithcm.entity.SalaryBillEntity;

@Transactional
@Controller
@RequestMapping("/SalaryHistory")
public class SalaryHistoryController {
	private String priorityId ="";
	@Autowired
	SessionFactory factory;
	
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler employerPassdataBetweenControllerHandler;
	
	@RequestMapping
	public String showSalaryHistory(HttpServletRequest request, ModelMap map) {
		if(employerPassdataBetweenControllerHandler!=null) {
			if(employerPassdataBetweenControllerHandler.getAuthorityId()!=null) {
				priorityId = employerPassdataBetweenControllerHandler.getAuthorityId().trim();
			}
			
		}
		String ownerId = employerPassdataBetweenControllerHandler.getData();
		List<SalaryBillEntity> salaryHistories = getSalaryHistory(ownerId);
		map.addAttribute("bills",salaryHistories);
		return displayPriorityView();
	}
	@RequestMapping(value = "Search", method = RequestMethod.GET)
	public String searchHistorySalary(HttpServletRequest request, ModelMap map) {
		if(employerPassdataBetweenControllerHandler!=null) {
			if(employerPassdataBetweenControllerHandler.getAuthorityId()!=null) {
				priorityId = employerPassdataBetweenControllerHandler.getAuthorityId().trim();
			}
			
		}
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String ownerId = employerPassdataBetweenControllerHandler.getData();
		List<SalaryBillEntity> salaryHistories;
		if(fromDate.isEmpty()||toDate.isEmpty()) {
			salaryHistories = getSalaryHistory(ownerId);
		}
		else {
			salaryHistories = getSalaryHistoryBetweenDates(ownerId,fromDate,toDate);
		}
		
		
		map.addAttribute("bills",salaryHistories);
		return displayPriorityView();
	}
	
	@SuppressWarnings("unchecked")
	private List<SalaryBillEntity> getSalaryHistory(String ownerId){
		Session session = factory.getCurrentSession();
		String hql = "FROM SalaryBillEntity WHERE staffEntity.MANV = :ownerId "
				+ "	ORDER BY THOIGIANNHAN DESC";
		Query query = session.createQuery(hql);
		query.setString("ownerId", ownerId);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	private List<SalaryBillEntity> getSalaryHistoryBetweenDates(String ownerId,String fromDay, String toDay){
		Session session = factory.getCurrentSession();
		String hql = "FROM SalaryBillEntity WHERE staffEntity.MANV = :ownerId AND THOIGIANNHAN BETWEEN :fromDay AND :toDay"
				+ "	ORDER BY THOIGIANNHAN DESC";
		Query query = session.createQuery(hql);
		query.setString("ownerId", ownerId);
		query.setString("fromDay", fromDay);
		query.setString("toDay", toDay);
		return query.list();
	}
	
	private String displayPriorityView() {
		if(priorityId.equals("QL")) {
			return "Manager/SalaryHistory";
		}
		else{
			return "Staff/SalaryHistory";
		}
	}

}