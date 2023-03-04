package ptithcm.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.MistakeEntity;
import ptithcm.entity.OpenShiftEntity;
import ptithcm.entity.SalaryBillEntity;
import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.StaffEntity;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;

@Transactional
@Controller
public class SalaryController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value="salary", method = RequestMethod.GET)
	public String showSalary(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql="From StaffEntity Where MANV != :admin";
		Query query = session.createQuery(hql);
		query.setString("admin", "ADMIN");
		List<StaffEntity> list = query.list();
		model.addAttribute("salaryStaff", list);
		return "/Admin/StaffSalary";
	}
	
	@RequestMapping(value="historySalary", method=RequestMethod.GET)
	public String historySalary(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("history");
		List<SalaryBillEntity> list = getHistorySalary(maNV);
		model.addAttribute("historySalary", list);
		model.addAttribute("idStaffHS", maNV);
		return showSalary(model);
	}
	
	
	@RequestMapping(value="paySalary", method= RequestMethod.GET)
	public String pay(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("idStaff");
		Float salary = Float.parseFloat(request.getParameter("salary"));
		makeSalaryBill(maNV, salary);
		return showSalary(model);
	}
	 public void makeSalaryBill(String maNV, float salary) {
		 Session session = factory.getCurrentSession();
		 long milies = System.currentTimeMillis();
		 Date date = new Date(milies);
		 StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, maNV);
		 staff.paySalary(salary);
		 session.saveOrUpdate(staff);
		 SalaryBillEntity bill = new SalaryBillEntity();
		 bill.setTHOIGIANNHAN(date);
		 bill.setStaffEntity(staff);
		 bill.setLUONGNHAN(salary);
		 session.save(bill);
	 }
	 
	 public List<SalaryBillEntity> getHistorySalary(String idNV){
		 Session session = factory.getCurrentSession();
		 String hql = "From SalaryBillEntity Where MANV = :id";
		 Query query = session.createQuery(hql);
		 query.setString("id", idNV);
		 List<SalaryBillEntity> list = query.list();
		 return list;
	 }
}