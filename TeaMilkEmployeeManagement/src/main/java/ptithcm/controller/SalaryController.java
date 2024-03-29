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
	private List<StaffEntity> enabledList;
	private List<StaffEntity> disabledList;
	private String searchTextInput = "";
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value="salary", method = RequestMethod.GET)
	public String showSalary(ModelMap model) {
		searchTextInput = "";
		return displayMainView(model);
	}
	
	@RequestMapping(value="ShowSalary", method = RequestMethod.GET)
	public String showSpecificSalary(ModelMap model) {
		return displayMainView(model);
	}
	
	
	@RequestMapping(value="historySalary", method=RequestMethod.GET)
	public String historySalary(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("history");
		List<SalaryBillEntity> list = getHistorySalary(maNV);
		model.addAttribute("historySalary", list);
		model.addAttribute("idStaffHS", maNV);
		return displayMainView(model);
	}
	
	
	@RequestMapping(value="paySalary", method= RequestMethod.GET)
	public String pay(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("idStaff");
		Float salary = Float.parseFloat(request.getParameter("salary"));
		makeSalaryBill(maNV, salary);
		model.addAttribute("updateSuccess",true);
		return displayMainView(model);
	}
	
	@RequestMapping(value = "searchSalary",method = RequestMethod.GET)
	public String searchSalary(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchInput = request.getParameter("searchInput");
		searchTextInput = searchInput;
		
		enabledList = searchEnabledStaffs(session,searchInput);
		disabledList = searchDisabledStaffs(session,searchInput);
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
				+ " ((HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%')) OR"
				+ " (jobPosition.HINHTHUC LIKE CONCAT ('%',:search,'%')))"
				+ " ORDER BY LUONGTICHLUY DESC";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchDisabledStaffs(Session session,String searchText) {
		if(searchText.isEmpty()) {return null;}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = false) AND"
				+ " ((HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%')))"
				+ " ORDER BY LUONGTICHLUY DESC";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}
	
	public void makeSalaryBill(String maNV, float salary) {
		 Session session = factory.getCurrentSession();
		 long milies = System.currentTimeMillis();
		 Timestamp currentDateTime = new Timestamp(milies);
		 StaffEntity staff = (StaffEntity) session.get(StaffEntity.class, maNV);
		 if(staff.getJobPosition().getHINHTHUC().equals("FULL TIME")) {
			 staff.setLUONGTICHLUY(staff.getJobPosition().getLUONGTHEOGIO());
			 
		 }
		 else {
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
					+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true) "
					+ " ORDER BY LUONGTICHLUY DESC";
			Query query = session.createQuery(hql);
			query.setString("admin", "ADMIN");
			return query.list();
	 }
	 public List<StaffEntity> getDisabledStaffs(){
		 Session session = factory.getCurrentSession();
			String hql="From StaffEntity Where MANV != :admin AND"
					+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = false) "
					+ " ORDER BY LUONGTICHLUY DESC)";
			Query query = session.createQuery(hql);
			query.setString("admin", "ADMIN");
			return query.list();
	 }
	 private String displayMainView(ModelMap model) {
		 	Session session = factory.getCurrentSession();
			enabledList = searchEnabledStaffs(session,searchTextInput);
			disabledList = searchDisabledStaffs(session,searchTextInput);
			if(enabledList==null) {
				enabledList = getEnabledStaffs();
			}
			if(disabledList==null) {
				disabledList = getDisabledStaffs();
			}
		 	
			model.addAttribute("salaryStaff", enabledList);
			
			model.addAttribute("disabledStaff",disabledList);
			return "/Admin/StaffSalary";
	 }
}
