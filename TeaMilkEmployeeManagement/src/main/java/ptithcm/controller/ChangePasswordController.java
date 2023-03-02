package ptithcm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping(value = "/ChangePassword")
public class ChangePasswordController {
	@RequestMapping
	public String changePassword(HttpServletRequest request, ModelMap map) {
		return "ChangePassword";
	}
	
//	public String saveChangePassword(HttpServletRequest request, ModelMap map) {
//		
//	}
}
