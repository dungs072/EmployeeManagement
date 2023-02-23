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

import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.bean.Primarykeyable;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
public class ManagerController {
	
	
	@Autowired
	SessionFactory factory;
	@Autowired
	@Qualifier("staffKeyHandler")
	IncrementNumberAndTextKeyHandler staffKeyHandler;
	
	@RequestMapping(value = "Login-Form",method = RequestMethod.GET)
	public String login()
	{	
		return "Login";
	}
	@RequestMapping(value = "/CheckLogin",method = RequestMethod.POST)
	public String tryLogin(HttpServletRequest request,ModelMap model) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if(hasExistedAccount(userName, password))
		{
			List<StaffEntity> staffs = getStaffs();
			castStaffs(staffs);
			model.addAttribute("staffs",staffs);
			return "/Admin/RecruitEmployee";
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
	
	public List<StaffEntity> getStaffs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV != 'ADMIN'";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public boolean hasExistedAccount(String userName, String password) {
		Session session = factory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM AccountEntity WHERE TENTK = :userName AND MK = :password";
		Query query = session.createQuery(hql);
		query.setString("userName", userName);
		query.setString("password", password);
		return (Long)query.uniqueResult()>0;
	}
}

