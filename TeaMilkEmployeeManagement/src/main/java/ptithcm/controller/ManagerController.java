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
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.Primarykeyable;
import ptithcm.entity.JobPositionEntity;
import ptithcm.entity.MistakeEntity;
import ptithcm.entity.StaffEntity;


@Transactional
@Controller
public class ManagerController {
	
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

	
	@Autowired
	@Qualifier("managerPassDataHandler")
	PassDataBetweenControllerHandler passDataBetweenControllerHandler;
	
	
	@RequestMapping(value = "Login-Form",method = RequestMethod.GET)
	public String login()
	{	
		return "Login";
	}
	@RequestMapping(value = "/CheckLogin",method = RequestMethod.POST)
	public String tryLogin(HttpServletRequest request,ModelMap model) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String priority = hasExistedAccount(userName,password);
		if(priority.strip().equals("AD"))
		{
			List<StaffEntity> staffs = getStaffs();
			List<StaffEntity> internalStaffs = getInteralStaffs();
			List<JobPositionEntity> jobs = getJobs();
			List<MistakeEntity> faults = getMistakes();
			castStaffs(internalStaffs);
			castJobs(jobs);
			castFaults(faults);
			model.addAttribute("staffs",staffs);
			return "/Admin/RecruitEmployee";
		}
		else if(priority.strip().equals("QL"))
		{
			passDataBetweenControllerHandler.setData(userName);
			return "redirect:/ManagerRegistration.htm";
		}
		else if(priority.strip().equals("NV"))
		{
			return "Login";
		}
		else
		{
			return "Login";
		}
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
	public List<StaffEntity> getStaffs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN' AND MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = True) ORDER BY TEN";
		Query query = session.createQuery(hql);
		return query.list();
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
	@SuppressWarnings("unchecked")
	public List<JobPositionEntity> getJobs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public String hasExistedAccount(String userName, String password) {
		String priority = "";
		Session session = factory.getCurrentSession();
		String hql = "SELECT priorityEntity.MAQUYEN FROM AccountEntity WHERE TENTK = :userName AND MK = :password AND TRANGTHAI = true";
		Query query = session.createQuery(hql);
		query.setString("userName", userName);
		query.setString("password", password);
		priority = (String) query.uniqueResult();
		return priority;
	}
}

