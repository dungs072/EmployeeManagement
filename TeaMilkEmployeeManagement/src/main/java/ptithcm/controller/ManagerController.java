package ptithcm.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.ShiftEntity;

@Transactional
@Controller
public class ManagerController {
	
	@Autowired
	SessionFactory factory;
	
	@RequestMapping(value = "Login-Form",method = RequestMethod.GET)
	public String login()
	{	
		return "Login";
	}
	@RequestMapping(value = "/CheckLogin",method = RequestMethod.POST)
	public String tryLogin(HttpServletRequest request) {
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if(hasExistedAccount(userName, password))
		{
			return "/Admin/MainPage";
		}
		else
		{
			return "Login";
		}
	}
	
	public List<ShiftEntity> getShifts(){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftEntity";
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

