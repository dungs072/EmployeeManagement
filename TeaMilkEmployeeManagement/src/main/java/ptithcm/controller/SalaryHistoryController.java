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

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.entity.SalaryBillEntity;

@Transactional
@Controller
@RequestMapping("/SalaryHistory")
public class SalaryHistoryController {
	
	@Autowired
	SessionFactory factory;
	
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler employerPassdataBetweenControllerHandler;
	
	@RequestMapping
	public String showSalaryHistory(HttpServletRequest request, ModelMap map) {
		String ownerId = employerPassdataBetweenControllerHandler.getData();
		List<SalaryBillEntity> salaryHistories = getSalaryHistory(ownerId);
		map.addAttribute("bills",salaryHistories);
		return displayPriorityView();
	}
	
	@SuppressWarnings("unchecked")
	private List<SalaryBillEntity> getSalaryHistory(String ownerId){
		Session session = factory.getCurrentSession();
		String hql = "FROM SalaryBillEntity WHERE staffEntity.MANV = :ownerId";
		Query query = session.createQuery(hql);
		query.setString("ownerId", ownerId);
		return query.list();
	}
	
	private String displayPriorityView() {
		String priorityId = employerPassdataBetweenControllerHandler.getAuthorityId().strip();
		if(priorityId.equals("QL")) {
			return "Manager/SalaryHistory";
		}
		else{
			return "Staff/SalaryHistory";
		}
	}

}