package ptithcm.controller;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/home/")
public class HomeController {
	@Autowired
	SessionFactory factory;
	@RequestMapping("index")
	public String index(ModelMap model) {
		LocalTime time = LocalTime.now();
		int hour = time.getHour();
		String id = "CA03";
		if(hour>=7 && hour<=11) {
			id="CA01";
		}
		else if(hour >= 13 && hour<=17) {
			id="CA02";
		}
		else if(hour >=17 && hour <=21) {
			id="CA03";
		}
		long millis = System.currentTimeMillis(); 
		Date date_sql = new Date(millis);
		List<ShiftDetailEntity> list = getAShiftDetail(id,date_sql);
		model.addAttribute("shiftNow",list);
		return "/Admin/Home";
	}
	@RequestMapping(value="/updateSalary",method =RequestMethod.GET)
	public String updateSalary(HttpServletRequest request) {
		float salary;
		String currentStaff = request.getParameter("updateSalary");
		salary = Float.parseFloat(request.getParameter("salaryOfShift"));
		updateSalaryToDB(currentStaff, salary);
		return "/Admin/Home";
	}
	public void updateSalaryToDB(String maNV, Float salary) {
		Session session = factory.getCurrentSession();
		StaffEntity staff = (StaffEntity)(session.get(StaffEntity.class, maNV));
		staff.setLUONGTICHLUY(salary);
		session.saveOrUpdate(staff);
	}

	public List<ShiftDetailEntity> getAShiftDetail(String id, Date date){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity where IDCA = :id AND THOIGIANDANGKI = :date ";
		Query query = session.createQuery(hql);
		String d = date.toString();
		query.setString("id",id);
		query.setString("date",d );
		List<ShiftDetailEntity> list = query.list();
		return list;
	}
	
	
}
