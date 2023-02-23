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
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/Recruit")
public class RecruitLayOffEmployeeController {
	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("staffKeyHandler")
	IncrementNumberAndTextKeyHandler staffKeyHandler;
	
	
	@RequestMapping(params = "saveAddEmployee")
	public String addEmloyee(ModelMap model,StaffEntity staff) {
		
		String staffId = staffKeyHandler.getNewKey("NV");
		
		addEmloyeeToDB(staff,staffId);
		List<StaffEntity> staffs = getStaffs();
		model.addAttribute("staffs",staffs);
		return "/Admin/RecruitEmployee";
	}
	
	@RequestMapping(value = "/DeleteStaff",method = RequestMethod.GET)
	public String deleteEmployee(HttpServletRequest request,ModelMap model) {
		String staffId = request.getParameter("yes-warning");
		deleteEmployeeFromDB(staffId);
		List<StaffEntity> staffs = getStaffs();
		model.addAttribute("staffs",staffs);
		return "/Admin/RecruitEmployee";
	}
	
	private void addEmloyeeToDB(StaffEntity staff,String id) {
		if(staff==null) {return;}
		staff.setMANV(id);
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
	private void deleteEmployeeFromDB(String id) {
		StaffEntity staff = new StaffEntity();
		staff.setMANV(id);
		Session session = factory.getCurrentSession();
		session.delete(staff);
	}
	public List<StaffEntity> getStaffs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN'";
		Query query = session.createQuery(hql);
		return query.list();
	}
}
