package ptithcm.controller;

import java.sql.Timestamp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import ptithcm.entity.AccountEntity;

import ptithcm.entity.SalaryBillEntity;

import ptithcm.entity.StaffEntity;

@Transactional
@Controller
public class SalaryController {
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value="salary", method = RequestMethod.GET)
	public String showSalary(ModelMap model) {
		return displayMainView(model);
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
	
	@RequestMapping(value = "searchSalary",method = RequestMethod.GET)
	public String searchSalary(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchInput = request.getParameter("searchInput");
		List<StaffEntity> enabledList = searchEnabledStaffs(session,searchInput);
		List<StaffEntity> disabledList = searchDisabledStaffs(session,searchInput);
		if(enabledList==null&&disabledList==null) {
			return displayMainView(model);
		}
		model.addAttribute("salaryStaff", enabledList);
		model.addAttribute("disabledStaff",disabledList);
		return "/Admin/StaffSalary";
	}
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchEnabledStaffs(Session session,String searchText) {
		if(searchText.isEmpty()) {return null;}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true) AND"
				+ " (HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%'))"
				+ " ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchDisabledStaffs(Session session,String searchText) {
		if(searchText.isEmpty()) {return null;}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = false) AND"
				+ " (HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%'))"
				+ " ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}
	
	public void makeSalaryBill(String maNV, float salary) {
		 Session session = factory.getCurrentSession();
		 long milies = System.currentTimeMillis();
		 Timestamp currentDateTime = new Timestamp(milies);
		 StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, maNV);
		 AccountEntity account = (AccountEntity) session.get(AccountEntity.class, maNV);
		 if(!account.getPriorityEntity().getMAQUYEN().strip().equals("QL")) {
			 staff.paySalary(salary);
		 }
		 session.saveOrUpdate(staff);
		 SalaryBillEntity bill = new SalaryBillEntity();
		 bill.setTHOIGIANNHAN(currentDateTime);
		 bill.setStaffEntity(staff);
		 bill.setLUONGNHAN(salary);
		 session.save(bill);
	 }
	 
	 public List<SalaryBillEntity> getHistorySalary(String idNV){
		 Session session = factory.getCurrentSession();
		 String hql = "From SalaryBillEntity Where MANV = :id ORDER BY THOIGIANNHAN DESC";
		 Query query = session.createQuery(hql);
		 query.setString("id", idNV);
		 List<SalaryBillEntity> list = query.list();
		 return list;
	 }
	 public List<StaffEntity> getStaffs(){
		 Session session = factory.getCurrentSession();
			String hql="From StaffEntity Where MANV != :admin";
			Query query = session.createQuery(hql);
			query.setString("admin", "ADMIN");
			return query.list();
	 }
	 public List<StaffEntity> getEnabledStaffs(){
		 Session session = factory.getCurrentSession();
			String hql="From StaffEntity Where MANV != :admin AND"
					+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true)";
			Query query = session.createQuery(hql);
			query.setString("admin", "ADMIN");
			return query.list();
	 }
	 public List<StaffEntity> getDisabledStaffs(){
		 Session session = factory.getCurrentSession();
			String hql="From StaffEntity Where MANV != :admin AND"
					+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = false)";
			Query query = session.createQuery(hql);
			query.setString("admin", "ADMIN");
			return query.list();
	 }
	 private String displayMainView(ModelMap model) {
		 	List<StaffEntity> enabledList = getEnabledStaffs();
			model.addAttribute("salaryStaff", enabledList);
			List<StaffEntity> disabledList = getDisabledStaffs();
			model.addAttribute("disabledStaff",disabledList);
			return "/Admin/StaffSalary";
	 }
}
