package ptithcm.controller;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/CheckLogin")
public class RecruitLayOffEmployeeController {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(params = "saveAddEmployee")
	public String addEmloyee(ModelMap model,StaffEntity staff) {
		staff.setMANV("NV2");
		addEmloyeeToDB(staff);
		model.addAllAttributes(model);
		return "/Admin/RecruitEmployee";
	}
	private void addEmloyeeToDB(StaffEntity staff) {
		if(staff==null) {return;}
		Session session = factory.getCurrentSession();
		session.save(staff);
//		 try {   
//			   session.beginTransaction();
//			   
//			   session.getTransaction().commit();
//			   System.out.println("Data is Added");
//			   
//		} finally {
//			   // TODO: handle finally clause
//			   session.close();
//			   factory.close();
//		}
	}
}
