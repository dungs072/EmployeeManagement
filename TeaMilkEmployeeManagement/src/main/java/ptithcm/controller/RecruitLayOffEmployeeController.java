package ptithcm.controller;

import javax.xml.bind.DatatypeConverter;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import ptithcm.bean.FormatString;
import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.bean.PassDataBetweenControllerHandler;
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
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;

	@Autowired
	@Qualifier("formatText")
	FormatString formatString;

	private List<StaffEntity> staffList;
	private String currentStaffId;

	@RequestMapping
	public String recruitLayOffDisplay(ModelMap model) {
		staffList = getStaffs();
		List<StaffEntity> internalStaffs = getInteralStaffs();
		List<JobPositionEntity> jobs = getJobs();
		List<MistakeEntity> faults = getMistakes();
		castStaffs(internalStaffs);
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
		model.addAttribute("isShowJob",true);
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
			String jobId = request.getParameter("jobId");
			String staffId = staffKeyHandler.getNewKey("NV");
			JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, jobId);
			staff.setHO(formatString.getTextFormat(staff.getHO()));
			staff.setTEN(formatString.getTextFormat(staff.getTEN()));
			if(staff.getDIACHI()!=null) {
				staff.setDIACHI(formatString.getTextFormat(staff.getDIACHI()));
			}
			if(job.getHINHTHUC().equals("FULL TIME")) {
				staff.setLUONGTICHLUY(job.getLUONGTHEOGIO());
			}
			staff.setJobPosition(job);
			addEmloyeeToDB(staff, staffId);
			model.addAttribute("staffIdValue",staffId);
			model.addAttribute("addSuccess",true);
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
		deleteEmployeeFromDB(staffId,model);
		staffList = getStaffs();
		List<JobPositionEntity> jobs = getJobs();
		model.addAttribute("staffs", staffList);
		model.addAttribute("jobs", jobs);
		handleCheckInput(model);
		return "/Admin/RecruitEmployee";
	}
	@RequestMapping(value = "/DisableStaff",method = RequestMethod.GET)
	public String disableEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String staffId = request.getParameter("yes-disable-warning");
		disableAccount(session, staffId.strip());
		staffList = getStaffs();
		model.addAttribute("staffs", staffList);
		model.addAttribute("disableSuccess",true);
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

	@RequestMapping(value = "/UpdateStaff", method = RequestMethod.POST)
	public String updateInforEmployee(HttpServletRequest request, ModelMap model, StaffEntity staff) {
		
		Session session = factory.getCurrentSession();
		List<JobPositionEntity> jobs = getJobs();
		if(staff.getHO().isBlank()||staff.getTEN().isBlank()) {
			staffList = getStaffs();
			StaffEntity oldstaff = getStaff(currentStaffId);
			model.addAttribute("updateSuccess",false);
			model.addAttribute("staffs", staffList);
			model.addAttribute("staff", oldstaff);
			model.addAttribute("jobs", jobs);
			handleCheckInput(model);
			return "/Admin/RecruitEmployee";
		}
		if(hasIdentificationCardNumberInDB(staff.getCCCD(),currentStaffId)) {
			StaffEntity oldstaff = getStaff(currentStaffId);
			model.addAttribute("isWrongIDCard","true");
			model.addAttribute("idCardMessage","The new id number existed");
			staffList = getStaffs();
			model.addAttribute("updateSuccess",false);
			model.addAttribute("staffs", staffList);
			model.addAttribute("staff", oldstaff);
			model.addAttribute("jobs", jobs);
			handleCheckInput(model);
			return "/Admin/RecruitEmployee";
		}
		else if(hasPhoneNumberInDB(staff.getSDT(),currentStaffId)) {
			StaffEntity oldstaff = getStaff(currentStaffId);
			model.addAttribute("isWrongPhoneNumber","true");
			model.addAttribute("phoneMessage","The new phone number existed");
			staffList = getStaffs();
			model.addAttribute("updateSuccess",false);
			model.addAttribute("staffs", staffList);
			model.addAttribute("staff", oldstaff);
			model.addAttribute("jobs", jobs);
			handleCheckInput(model);
			return "/Admin/RecruitEmployee";
		}
		String maCV = request.getParameter("jobId");
		JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, maCV);
		StaffEntity oldstaff = getStaff(currentStaffId);
		if(oldstaff.getJobPosition().getHINHTHUC().equals("PART TIME")&&oldstaff.getLUONGTICHLUY()>0) {
			if(job.getHINHTHUC().equals("FULL TIME")) {
				model.addAttribute("staffs", staffList);
				model.addAttribute("canUpdateStaffWithSalary",true);
				model.addAttribute("staffSalaryLeft",oldstaff.getLUONGTICHLUY());
				return "/Admin/RecruitEmployee";
			}
		}
		
		String birthdayStr = request.getParameter("birthday");
		if (!birthdayStr.isEmpty()) {
			Date birthday = Date.valueOf(birthdayStr);
			staff.setNGAYSINH(birthday);
		}
		if(staff.getEMAIL()!=null&&!staff.getEMAIL().isEmpty()) {
			staff.setEMAIL(staff.getEMAIL().strip());
		}
		if(staff.getDIACHI()!=null&&!staff.getDIACHI().isEmpty()) {
			staff.setDIACHI(formatString.getTextFormat(staff.getDIACHI()));
		}
		staff.setHO(formatString.getTextFormat(staff.getHO()));
		staff.setTEN(formatString.getTextFormat(staff.getTEN()));
		staff.setMANV(currentStaffId);
		
		
		if(job.getHINHTHUC().equals("FULL TIME")) {
			if(oldstaff.getJobPosition().getMACV().equals(job.getMACV())) {
				staff.setLUONGTICHLUY(oldstaff.getLUONGTICHLUY());
			}
			else {
				staff.setLUONGTICHLUY(job.getLUONGTHEOGIO());
			}
			
		}
		staff.setJobPosition(job);
		updateStaff(session, staff);

	
		staffList = getStaffs();
		model.addAttribute("staffs", staffList);
		model.addAttribute("updateSuccess",true);
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
		
		String defaultPassword = "123";
		MessageDigest md;
		String newHashPassword = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(defaultPassword.getBytes());
			byte[] digest = md.digest();
			newHashPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account.setMK(newHashPassword);
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
			staff.setLUONGTICHLUY(staff.getJobPosition().getLUONGTHEOGIO());
		}
		
		String defaultPassword = "123";
		MessageDigest md;
		String newHashPassword = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(defaultPassword.getBytes());
			byte[] digest = md.digest();
			newHashPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		AccountEntity account = new AccountEntity(id, newHashPassword, true, priority);

		session.save(staff);
		session.save(account);
	}

	private void deleteEmployeeFromDB(String id,ModelMap map) {
		StaffEntity staff = new StaffEntity();
		staff.setMANV(id);
		Session session = factory.getCurrentSession();
		if (canDeletePermanentStaff(session, id)) {
			AccountEntity account = new AccountEntity();
			account.setTENTK(id);

			session.delete(account);
			session.delete(staff);
			map.addAttribute("canDelete",true);
		} else {
			map.addAttribute("canDelete",false);
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
			newStaff.setLUONGTICHLUY(newStaff.getJobPosition().getLUONGTHEOGIO());
		}
		
		AccountEntity account = (AccountEntity) session.get(AccountEntity.class, newStaff.getMANV());
		account.setPriorityEntity(priority);
		StaffEntity oldStaff = (StaffEntity) session.get(StaffEntity.class, newStaff.getMANV());
		if(oldStaff.getJobPosition().getHINHTHUC().equals("FULL TIME")&&!newStaff.getJobPosition().getTENVITRI().equals("PART TIME")) {
			oldStaff.setLUONGTICHLUY(0);	
		}
		oldStaff.updateInfor(newStaff);
		if(newStaff.getLUONGTICHLUY()>0) {
			oldStaff.setLUONGTICHLUY(newStaff.getLUONGTICHLUY());
		}
		session.saveOrUpdate(oldStaff);
	}

	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchStaffList(Session session,String searchText) {
		if(searchText.isEmpty()) {return getStaffs();}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true) AND"
				+ " ((HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%')) OR"
				+ "  (jobPosition.HINHTHUC LIKE CONCAT ('%',:search,'%')))"
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
		if(number1>0) {return false;}
		String hql2 = "SELECT COUNT(*) FROM ShiftDetailEntity WHERE staff.MANV = :id";
		Query query2 = session.createQuery(hql2);
		query2.setString("id", id);
		long number2 = (long) query2.uniqueResult();
		if(number2>0) {return false;}
		String hql3 = "SELECT COUNT(*) FROM OpenShiftEntity WHERE staff.MANV = :id";
		Query query3 = session.createQuery(hql3);
		query3.setString("id", id);
		long number3 = (long) query3.uniqueResult();
		if(number3>0) {return false;}
		return true;

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
