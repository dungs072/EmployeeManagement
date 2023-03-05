package ptithcm.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.entity.AccountEntity;

@Transactional
@Controller
@RequestMapping(value = "/ChangePassword")
public class ChangePasswordController {
	
	@Autowired
	SessionFactory factory; 
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	
	@RequestMapping
	public String changePassword(HttpServletRequest request, ModelMap map) {
		
		return returnToSpecificAccount();
	}
	
	@RequestMapping(value = "saveChange",method = RequestMethod.GET)
	public String saveChangePassword(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String staffId = staffPassDataBetweenControllerHandler.getData();
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if(oldPassword.isEmpty()||newPassword.isEmpty()||confirmPassword.isEmpty())
		{
			if(oldPassword.isEmpty()) {
				map.addAttribute("oldPasswordMessage","current password must not be blank");
			}
			if(newPassword.isEmpty()) {
				map.addAttribute("newPasswordMessage","new password must not be blank");
			}
			if(confirmPassword.isEmpty()) {
				map.addAttribute("confirmPasswordMessage","confirm password must not be blank");
			}
			return returnToSpecificAccount();
		}
	
		
		AccountEntity ownedAccount = getAccountIfPasswordIsRight(staffId,oldPassword);
		if(ownedAccount==null) {
			map.addAttribute("oldPasswordMessage","your current password is wrong !!!!");
		}
		else {
			if(newPassword.equals(confirmPassword)) {
				MessageDigest md;
				String newHashPassword = "";
				try {
					md = MessageDigest.getInstance("MD5");
					md.update(newPassword.getBytes());
					byte[] digest = md.digest();
					newHashPassword = DatatypeConverter.printHexBinary(digest).toUpperCase();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ownedAccount.setMK(newHashPassword);
				session.saveOrUpdate(ownedAccount);
				map.addAttribute("successMessage","Change password successfully!");
			}
			else {
				map.addAttribute("failureMessage","your confirm password isn't match with new password");
			}
		}
		return returnToSpecificAccount();
	}
	private AccountEntity getAccountIfPasswordIsRight(String staffId,String oldPassword) {
		Session session = factory.getCurrentSession();
		MessageDigest md;
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
		String hql = "FROM AccountEntity WHERE TENTK = :staffId AND MK = :password";
		Query query = session.createQuery(hql);
		query.setString("staffId", staffId);
		query.setString("password", newHashPassword);
		return (AccountEntity) query.uniqueResult();
	}
	
	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "Admin/ChangePassword";
		}
		else if(priority.equals("QL")) {
			return "Manager/ChangePassword";
		}
		else {
			return "Staff/ChangePassword";
		}
	}
}
