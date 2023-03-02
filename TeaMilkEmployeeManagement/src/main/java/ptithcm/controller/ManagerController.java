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
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	
	
	@RequestMapping(value = "Login-Form",method = RequestMethod.GET)
	public String login()
	{	
		return "Login";
	}

	@RequestMapping(value = "/CheckLogin", method = RequestMethod.POST)
	public String tryLogin(HttpServletRequest request, ModelMap model) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if(userName.isEmpty()&&password.isEmpty()) {
			model.addAttribute("UserNameMessage","username must not be blank!!!");
			model.addAttribute("PasswordMessage","password must not be blank!!!");
			return "Login";
		}
		if(userName.isEmpty())
		{
			model.addAttribute("UserNameMessage","username must not be blank!!!");
			return "Login";
		}
		
		
		if(password.isEmpty()) {
			model.addAttribute("PasswordMessage","password must not be blank!!!");
			return "Login";
		}
		String priority = hasExistedAccount(userName,password);
		
		if(priority==null) {
			model.addAttribute("UserNameMessage","wrong username or password!!!");
			model.addAttribute("PasswordMessage","wrong username or password!!!");
			return "Login";
		}
		
		if(priority.strip().equals("AD"))
		{
			staffPassDataBetweenControllerHandler.setData(userName);
			return "redirect:/Recruit.htm";
		}
		else if(priority.strip().equals("QL"))
		{
			staffPassDataBetweenControllerHandler.setData(userName);
			return "redirect:/ManagerRegistration.htm";
		}
		else if(priority.strip().equals("NV"))
		{
			staffPassDataBetweenControllerHandler.setData(userName);
			return "redirect:/StaffTimetable.htm";
		}
		else
		{
			return "Login";
		}
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
