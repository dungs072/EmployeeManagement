package ptithcm.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.entity.AccountEntity;
import ptithcm.entity.StaffEntity;


@Transactional
@Controller
@RequestMapping("/EnabledStaff")
public class EnableEmployeeController {
	@Autowired
	SessionFactory factory;
	
	private List<StaffEntity> staffList;
	
	@RequestMapping
	public String recruitLayOffDisplay(ModelMap model) {
		return displayMainView(model);
	}
	
	@RequestMapping(value = "/Enable",method = RequestMethod.GET)
	public String enableEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String staffId = request.getParameter("yes-warning");
		AccountEntity account = (AccountEntity) session.get(AccountEntity.class, staffId);
		account.setTRANGTHAI(true);
		MessageDigest md;
		String oldPassword = "123";
		String newHashPassword = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(oldPassword.getBytes());
			byte[] digest = md.digest();
			newHashPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		account.setMK(newHashPassword);
		session.saveOrUpdate(account);
		return displayMainView(model);
	}
	
	
	@RequestMapping(value = "/SearchStaff",method = RequestMethod.GET)
	public String searchEmployee(HttpServletRequest request, ModelMap model) {
		Session session = factory.getCurrentSession();
		String searchText = request.getParameter("searchInput");
		staffList = searchStaffList(session,searchText);
		model.addAttribute("staffs",staffList);
		return "/Admin/EnableEmployee";
	}
	
	
	private String displayMainView(ModelMap map) {
		List<StaffEntity> disabledStaffs = getDisabledStaff();
		map.addAttribute("staffs",disabledStaffs);
		return "/Admin/EnableEmployee";
	}
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> searchStaffList(Session session,String searchText) {
		if(searchText.isEmpty()) {return getDisabledStaff();}
		
		String hql = "FROM StaffEntity WHERE MANV !='ADMIN' AND"
				+ " MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true) AND"
				+ " (HO LIKE CONCAT('%',:search,'%')) OR "
				+ " (TEN LIKE CONCAT('%',:search,'%')) OR "
				+ " (jobPosition.TENVITRI LIKE CONCAT ('%',:search,'%'))"
				+ " ORDER BY TEN";
		Query query = session.createQuery(hql);
		query.setParameter("search",searchText);
		return query.list();
	}
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	private List<StaffEntity> getDisabledStaff(){
		Session session = factory.getCurrentSession();
		String hql = "FROM StaffEntity WHERE MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = false)";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
}
