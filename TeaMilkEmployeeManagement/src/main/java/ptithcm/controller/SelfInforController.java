package ptithcm.controller;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

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
		String staffId = passDataBetweenControllerHandler.getData();
		StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, staffId);
		map.addAttribute("staff",staff);
		return "SelfInfor";
	}
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateSelfInfor(HttpServletRequest request,ModelMap map,StaffEntity updatedStaff) {
		Session session = factory.getCurrentSession();
		String birthdayStr = request.getParameter("birthday");
		if(!birthdayStr.isEmpty())
		{
			Date birthday = Date.valueOf(birthdayStr);
			updatedStaff.setNGAYSINH(birthday);
		}
		map.addAttribute("staff",updatedStaff);
		updateStaff(session,updatedStaff);
		return "SelfInfor";
	}
	private void updateStaff(Session session,StaffEntity newStaff) {
		String staffId = passDataBetweenControllerHandler.getData();
		StaffEntity oldStaff = (StaffEntity) session.get(StaffEntity.class,staffId);
		oldStaff.updateInfor(newStaff);
		session.saveOrUpdate(oldStaff);
	}

}
