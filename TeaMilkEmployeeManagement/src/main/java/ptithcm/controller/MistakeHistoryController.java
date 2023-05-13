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
@RequestMapping("/MistakeHistory")
public class MistakeHistoryController {
	
	@Autowired
	SessionFactory factory;
	
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler employerPassdataBetweenControllerHandler;
	
	@RequestMapping
	public String showMistakeHistory(HttpServletRequest request, ModelMap map) {
		String ownerId = employerPassdataBetweenControllerHandler.getData();
		List<MistakeHistoryEntity> mistakeHistories = getMistakeHistory(ownerId);
		map.addAttribute("histories",mistakeHistories);
		return "Staff/MistakeHistory";
	}
	@RequestMapping(value = "/Search", method = RequestMethod.GET)
	public String searchMistakeHistory(HttpServletRequest request, ModelMap map) {

		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String ownerId = employerPassdataBetweenControllerHandler.getData();
		List<MistakeHistoryEntity> mistakeHistories;
		if(fromDate.isEmpty()||toDate.isEmpty()) {
			mistakeHistories = getMistakeHistory(ownerId);
		}
		else {
			mistakeHistories = getMistakeHistoryInSpecificDates(ownerId,fromDate,toDate);
		}
		
		
		map.addAttribute("histories",mistakeHistories);
		return "Staff/MistakeHistory";
	}
	
	@SuppressWarnings("unchecked")
	private List<MistakeHistoryEntity> getMistakeHistory(String ownerId){
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.staff.MANV = :ownerId ORDER BY shiftDetailEntity.openshift.NGAYLAMVIEC DESC";
		Query query = session.createQuery(hql);
		query.setString("ownerId", ownerId);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	private List<MistakeHistoryEntity> getMistakeHistoryInSpecificDates(String ownerId,String fromDate,String toDate){
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.staff.MANV = :ownerId AND shiftDetailEntity.openshift.NGAYLAMVIEC BETWEEN :fromDate AND :toDate ORDER BY shiftDetailEntity.openshift.NGAYLAMVIEC DESC";
		Query query = session.createQuery(hql);
		
		query.setString("ownerId", ownerId);
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		return query.list();
	}

}
