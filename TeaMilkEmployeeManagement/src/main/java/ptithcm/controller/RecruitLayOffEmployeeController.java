package ptithcm.controller;

import java.sql.Date;
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

import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.entity.AccountEntity;
import ptithcm.entity.JobPositionEntity;
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
	
	private List<StaffEntity> staffList;
	private String currentStaffId;
	
	@RequestMapping
	public String recruitLayOffDisplay(ModelMap model) {
		staffList = getStaffs();
		model.addAttribute("staffs",staffList);
		return "/Admin/RecruitEmployee";
	}
	
	@RequestMapping(params = "saveAddEmployee")
	public String addEmloyee(ModelMap model,StaffEntity staff) {
		
		String staffId = staffKeyHandler.getNewKey("NV");
		
		addEmloyeeToDB(staff,staffId);
		staffList = getStaffs();
		model.addAttribute("staffs",staffList);
		return "/Admin/RecruitEmployee";
	}
	
	@RequestMapping(value = "/DeleteStaff",method = RequestMethod.GET)
	public String deleteEmployee(HttpServletRequest request,ModelMap model) {
		String staffId = request.getParameter("yes-warning");
		deleteEmployeeFromDB(staffId);
		staffList = getStaffs();
		model.addAttribute("staffs",staffList);
		return "/Admin/RecruitEmployee";
	}
	
	@RequestMapping(value = "/InforStaff", method = RequestMethod.GET)
	public String showInforEmployee(HttpServletRequest request, ModelMap model) {
		currentStaffId = request.getParameter("InforStaff");
		
		StaffEntity staff = getStaff(currentStaffId);
		List<JobPositionEntity> jobs = getJobs();
		if(staffList==null) {
			staffList = getStaffs();
		}
		
		model.addAttribute("staffs",staffList);
		model.addAttribute("staff",staff);
		model.addAttribute("jobs",jobs);
		return "/Admin/RecruitEmployee";
	}
	@RequestMapping(value = "/UpdateStaff", method = RequestMethod.GET)
	public String updateInforEmployee(HttpServletRequest request,ModelMap model, StaffEntity staff, Date birthday ) {
		Session session = factory.getCurrentSession();
		String maCV = request.getParameter("jobPosition");
		staff.setMANV(currentStaffId);
		staff.setNGAYSINH(birthday);
		
		System.out.print(maCV);
		JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, maCV);
		staff.setJobPosition(job);
		updateStaff(session,staff);
		staffList = getStaffs();
		model.addAttribute("staffs",staffList);
		return "/Admin/RecruitEmployee";
	}
	@RequestMapping(value = "/SearchStaff",method = RequestMethod.GET)
	public String searchEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchText = request.getParameter("searchInput");
		staffList = searchStaffList(session,searchText);
		model.addAttribute("staffs",staffList);
		return "/Admin/RecruitEmployee";
	}
	
	private void addEmloyeeToDB(StaffEntity staff,String id) {
		if(staff==null) {return;}
		staff.setMANV(id);
		AccountEntity account = new AccountEntity(id,"123",true,null);
		Session session = factory.getCurrentSession();
		session.save(staff);
		session.save(account);
	}
	private void deleteEmployeeFromDB(String id) {
		StaffEntity staff = new StaffEntity();
		staff.setMANV(id);
		Session session = factory.getCurrentSession();
		if(canDeletePermanentStaff(session,id))
		{
			AccountEntity account = new AccountEntity();
			account.setTENTK(id);
			
			session.delete(account);
			session.delete(staff);
		}
		else
		{
			disableAccount(session,id);
		}
		
	}
	
	private void disableAccount(Session session,String id) {
		AccountEntity account = (AccountEntity) session.load(AccountEntity.class, id);
		account.setTRANGTHAI(false);
		session.update(account);
	}
	private void updateStaff(Session session,StaffEntity newStaff) {

		StaffEntity oldStaff = (StaffEntity) session.get(StaffEntity.class,newStaff.getMANV());
		oldStaff.updateInfor(newStaff);
		
		session.saveOrUpdate(oldStaff);
	}
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchStaffList(Session session,String searchText) {
		if(searchText.isEmpty()) {return getStaffs();}
		
		String hql = "FROM StaffEntity WHERE HO+TEN LIKE CONCAT('%',:search,'%') ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search",searchText);
		return query.list();
	}
	private boolean canDeletePermanentStaff(Session session,String id) {
		String hql1 = "SELECT COUNT(*) FROM SalaryBillEntity WHERE staffEntity.MANV = :id";
		Query query1 = session.createQuery(hql1);
		query1.setString("id",id);
		long number1 = (long) query1.uniqueResult();
		String hql2 = "SELECT COUNT(*) FROM ShiftDetailEntity WHERE staff.MANV = :id";
		Query query2 = session.createQuery(hql2);
		query2.setString("id",id);
		long number2  = (long) query2.uniqueResult();
		return number1==0&&number2==0;
		
	}
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getStaffs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN' AND MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = True) ORDER BY TEN";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<JobPositionEntity> getJobs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public StaffEntity getStaff(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (StaffEntity) query.uniqueResult();
	}
}
