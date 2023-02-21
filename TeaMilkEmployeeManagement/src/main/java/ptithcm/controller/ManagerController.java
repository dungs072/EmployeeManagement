package ptithcm.controller;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.ShiftEntity;

@Transactional
@Controller
public class ManagerController {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "Login",method = RequestMethod.GET)
	public String StringshowForm(ModelMap model)
	{	
		
		List<ShiftEntity> shifts = getShifts();
		
		model.addAttribute("shift",shifts.get(0));
		return "Login";
	}
	
	public List<ShiftEntity> getShifts(){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
}

