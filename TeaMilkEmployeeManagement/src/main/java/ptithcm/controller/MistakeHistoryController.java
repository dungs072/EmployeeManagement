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
	
	@SuppressWarnings("unchecked")
	private List<MistakeHistoryEntity> getMistakeHistory(String ownerId){
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeHistoryEntity WHERE shiftDetailEntity.staff.MANV = :ownerId";
		Query query = session.createQuery(hql);
		query.setString("ownerId", ownerId);
		return query.list();
	}

}
