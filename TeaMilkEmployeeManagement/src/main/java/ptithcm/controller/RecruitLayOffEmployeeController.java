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
import ptithcm.bean.Primarykeyable;
import ptithcm.entity.AccountEntity;
import ptithcm.entity.JobPositionEntity;
import ptithcm.entity.MistakeEntity;
import ptithcm.entity.PriorityEntity;
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
	
	@Autowired
	@Qualifier("jobKeyHandler")
	IncrementNumberAndTextKeyHandler jobKeyHandler;
	
	@Autowired
	@Qualifier("faultKeyHandler")
	IncrementNumberAndTextKeyHandler faultKeyHandler;

	private List<StaffEntity> staffList;
	private String currentStaffId;

	@RequestMapping
	public String recruitLayOffDisplay(ModelMap model) {
		staffList = getStaffs();
		List<StaffEntity> internalStaffs = getInteralStaffs();
		List<JobPositionEntity> jobs = getJobs();
		List<MistakeEntity> faults = getMistakes();
		castStaffs(internalStaffs);
		castJobs(jobs);
		castFaults(faults);
		model.addAttribute("staffs",staffList);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(value = "/ShowJobPosition", method = RequestMethod.GET)
	public String showJobPosition(ModelMap model) {
		List<JobPositionEntity> jobs = getJobs();
		staffList = getStaffs();
		model.addAttribute("jobs", jobs);
		model.addAttribute("staffs", staffList);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(params = "saveAddEmployee")
	public String addEmloyee(HttpServletRequest request, ModelMap model, StaffEntity staff) {
		handleCheckInput(model);
		if(hasIdentificationCardNumberInDB(staff.getCCCD())) {
			model.addAttribute("isWrongIDCard","true");
			model.addAttribute("idCardMessage","This id number existed");
		}
		else if(hasPhoneNumberInDB(staff.getSDT())) {
			model.addAttribute("isWrongPhoneNumber","true");
			model.addAttribute("phoneMessage","This phone number existed");
		}
		else {
			Session session = factory.getCurrentSession();
			String jobId = request.getParameter("add-jobId");
			String staffId = staffKeyHandler.getNewKey("NV");
			JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, jobId);
			staff.setHO(staff.getHO().strip());
			staff.setTEN(staff.getTEN().strip());
			if(staff.getDIACHI()!=null&&!staff.getDIACHI().isEmpty()) {
				staff.setDIACHI(staff.getDIACHI().strip());
			}
			staff.setJobPosition(job);
			addEmloyeeToDB(staff, staffId);
			model.addAttribute("staffIdValue",staffId);
		}
		

		staffList = getStaffs();
		List<JobPositionEntity> jobs = getJobs();
		model.addAttribute("staffs",staffList);
		model.addAttribute("jobs",jobs);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(value = "/DeleteStaff", method = RequestMethod.GET)
	public String deleteEmployee(HttpServletRequest request, ModelMap model) {
		String staffId = request.getParameter("yes-warning");
		deleteEmployeeFromDB(staffId);
		staffList = getStaffs();
		List<JobPositionEntity> jobs = getJobs();
		model.addAttribute("staffs", staffList);
		model.addAttribute("jobs", jobs);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(value = "/InforStaff", method = RequestMethod.GET)
	public String showInforEmployee(HttpServletRequest request, ModelMap model) {
		currentStaffId = request.getParameter("InforStaff");

		StaffEntity staff = getStaff(currentStaffId);
		List<JobPositionEntity> jobs = getJobs();
		if (staffList == null) {
			staffList = getStaffs();
		}

		model.addAttribute("staffs", staffList);
		model.addAttribute("staff", staff);
		model.addAttribute("jobs", jobs);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(value = "/UpdateStaff", method = RequestMethod.GET)
	public String updateInforEmployee(HttpServletRequest request, ModelMap model, StaffEntity staff) {
		
		Session session = factory.getCurrentSession();
		String maCV = request.getParameter("jobId");
		String birthdayStr = request.getParameter("birthday");
		if (!birthdayStr.isEmpty()) {
			Date birthday = Date.valueOf(birthdayStr);
			staff.setNGAYSINH(birthday);
		}
		if(staff.getEMAIL()!=null&&!staff.getEMAIL().isEmpty()) {
			staff.setEMAIL(staff.getEMAIL().strip());
		}
		if(staff.getDIACHI()!=null&&!staff.getDIACHI().isEmpty()) {
			staff.setDIACHI(staff.getDIACHI().strip());
		}

		staff.setMANV(currentStaffId);

		JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, maCV);

		staff.setJobPosition(job);
		updateStaff(session, staff);

	
		staffList = getStaffs();
		model.addAttribute("staffs", staffList);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}

	@RequestMapping(value = "/SearchStaff", method = RequestMethod.GET)
	public String searchEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchText = request.getParameter("searchInput");
		staffList = searchStaffList(session, searchText);
		model.addAttribute("staffs", staffList);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}
	
	@RequestMapping(value = "/ResetPassword",method = RequestMethod.GET)
	public String resetPassword(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String staffId = request.getParameter("yes-reset-warning");
		AccountEntity account = (AccountEntity) session.get(AccountEntity.class, staffId);
		account.setMK("123");
		session.saveOrUpdate(account);
		model.addAttribute("staffs",staffList);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}
	
	private void handleCheckInput(ModelMap model) {
		model.addAttribute("isWrongIDCard","false");
		model.addAttribute("isWrongPhoneNumber","false");
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
	
	
	private boolean hasIdentificationCardNumberInDB(String idCard) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE CCCD = :idCard";
		Query query = session.createQuery(hql);
		query.setString("idCard", idCard);
		return (long)query.uniqueResult()>0;
	}
	private boolean hasPhoneNumberInDB(String phoneNumber) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE SDT = :phoneNumber";
		Query query = session.createQuery(hql);
		query.setString("phoneNumber", phoneNumber);
		return (long)query.uniqueResult()>0;
	}
	
	private void addEmloyeeToDB(StaffEntity staff,String id) {
		if(staff==null) {return;}
		staff.setMANV(id);
		Session session = factory.getCurrentSession();
		PriorityEntity priority = (PriorityEntity) session.get(PriorityEntity.class, "NV");
		if (staff.getJobPosition().getTENVITRI().equals("Manager")) {
			priority = (PriorityEntity) session.get(PriorityEntity.class, "QL");
		}
		AccountEntity account = new AccountEntity(id, "123", true, priority);

		session.save(staff);
		session.save(account);
	}

	private void deleteEmployeeFromDB(String id) {
		StaffEntity staff = new StaffEntity();
		staff.setMANV(id);
		Session session = factory.getCurrentSession();
		if (canDeletePermanentStaff(session, id)) {
			AccountEntity account = new AccountEntity();
			account.setTENTK(id);

			session.delete(account);
			session.delete(staff);
		} else {
			disableAccount(session, id);
		}

	}

	private void disableAccount(Session session, String id) {
		AccountEntity account = (AccountEntity) session.load(AccountEntity.class, id);
		account.setTRANGTHAI(false);
		session.update(account);
	}

	private void updateStaff(Session session, StaffEntity newStaff) {
		PriorityEntity priority = (PriorityEntity) session.get(PriorityEntity.class, "NV");
		if (newStaff.getJobPosition().getTENVITRI().equals("Manager")) {
			priority = (PriorityEntity) session.get(PriorityEntity.class, "QL");
		}
		AccountEntity account = (AccountEntity) session.get(AccountEntity.class, newStaff.getMANV());
		account.setPriorityEntity(priority);
		StaffEntity oldStaff = (StaffEntity) session.get(StaffEntity.class, newStaff.getMANV());
		oldStaff.updateInfor(newStaff);
		session.saveOrUpdate(oldStaff);
	}

	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchStaffList(Session session,String searchText) {
		if(searchText.isEmpty()) {return getStaffs();}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true) AND"
				+ " (HO+TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%'))"
				+ " ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search", searchText);
		return query.list();
	}

	private boolean canDeletePermanentStaff(Session session, String id) {
		String hql1 = "SELECT COUNT(*) FROM SalaryBillEntity WHERE staffEntity.MANV = :id";
		Query query1 = session.createQuery(hql1);
		query1.setString("id", id);
		long number1 = (long) query1.uniqueResult();
		String hql2 = "SELECT COUNT(*) FROM ShiftDetailEntity WHERE staff.MANV = :id";
		Query query2 = session.createQuery(hql2);
		query2.setString("id", id);
		long number2 = (long) query2.uniqueResult();
		return number1 == 0 && number2 == 0;

	}

	@SuppressWarnings("unchecked")
	private List<StaffEntity> getStaffs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN' AND MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = True) ORDER BY TEN";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	private List<JobPositionEntity> getJobs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	private StaffEntity getStaff(String id) {
		Session session = factory.getCurrentSession();
		return (StaffEntity) session.get(StaffEntity.class, id);
//		String hql = "FROM StaffEntity WHERE MANV = :id";
//		Query query = session.createQuery(hql);
//		query.setString("id", id);
//		return (StaffEntity) query.uniqueResult();
	}

	@SuppressWarnings("unused")
	private JobPositionEntity getJob(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity WHERE MACV = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (JobPositionEntity) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	private void castStaffs(List<? extends Primarykeyable> keys) {
		staffKeyHandler.initialKeyHandler((List<Primarykeyable>) keys);
	}
	
	@SuppressWarnings("unchecked")
	private void castFaults(List<? extends Primarykeyable> keys) {
		faultKeyHandler.initialKeyHandler((List<Primarykeyable>) keys);
	}
	
	@SuppressWarnings("unchecked")
	private void castJobs(List<? extends Primarykeyable> keys) {
		jobKeyHandler.initialKeyHandler((List<Primarykeyable>) keys);
	}
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getInteralStaffs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN'";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MistakeEntity> getMistakes(){
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

}
