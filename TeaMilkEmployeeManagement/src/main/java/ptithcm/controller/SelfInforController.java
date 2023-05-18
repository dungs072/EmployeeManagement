package ptithcm.controller;

import java.sql.Date;

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
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping(value = "/SelfInfor")
public class SelfInforController {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler passDataBetweenControllerHandler;
	
	@RequestMapping
	public String showSelfInfor(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		return displayMainView(session,map);
	}
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateSelfInfor(HttpServletRequest request,ModelMap map,StaffEntity updatedStaff) {
		
		Session session = factory.getCurrentSession();
		if(updatedStaff.getHO().isBlank()) {
			return displayMainView(session,map);
		}
		if(updatedStaff.getTEN().isBlank()) {
			return displayMainView(session,map);
		}
		String staffId = passDataBetweenControllerHandler.getData();
		if(hasIdentificationCardNumberInDB(updatedStaff.getCCCD().strip(),staffId)) {
			map.addAttribute("idCardMessage","New id number existed");
			return displayMainView(session,map);
		}
		if(hasPhoneNumberInDB(updatedStaff.getSDT().strip(),staffId)) {
			map.addAttribute("phoneMessage","New phone number existed");
			return displayMainView(session,map);
		}
		
		String birthdayStr = request.getParameter("birthday");
		if(!birthdayStr.isEmpty())
		{
			Date birthday = Date.valueOf(birthdayStr);
			updatedStaff.setNGAYSINH(birthday);
		}
		updateStaff(session,updatedStaff);
		map.addAttribute("staff",updatedStaff);
		map.addAttribute("updateSuccess",true);
		return returnToSpecificAccount();
	}
	
	private boolean hasIdentificationCardNumberInDB(String idCard, String staffId) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE CCCD = :idCard AND MANV!=:staffId";
		Query query = session.createQuery(hql);
		query.setString("idCard", idCard);
		query.setString("staffId", staffId);
		return (long)query.uniqueResult()>0;
	}
	private boolean hasPhoneNumberInDB(String phoneNumber, String staffId) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE SDT = :phoneNumber AND MANV!=:staffId";
		Query query = session.createQuery(hql);
		query.setString("phoneNumber", phoneNumber);
		query.setString("staffId", staffId);
		return (long)query.uniqueResult()>0;
	}
	
	private void updateStaff(Session session,StaffEntity newStaff) {
		String staffId = passDataBetweenControllerHandler.getData();
		StaffEntity oldStaff = (StaffEntity) session.get(StaffEntity.class,staffId);
		newStaff.setJobPosition(oldStaff.getJobPosition());
		oldStaff.updateInfor(newStaff);
		session.saveOrUpdate(oldStaff);
	}
	private String displayMainView(Session session,ModelMap map) {
		String staffId = passDataBetweenControllerHandler.getData();
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, staffId);
		map.addAttribute("staff",staff);
		return returnToSpecificAccount();
	}
	private String returnToSpecificAccount() {
		String priority = passDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "Admin/SelfInfor";
		}
		else if(priority.equals("QL")) {
			return "Manager/SelfInfor";
		}
		else {
			return "Staff/SelfInfor";
		}
	}

}
