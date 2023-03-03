package ptithcm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Transactional
@Controller
@RequestMapping(value = "/Logout")
public class LogoutController {

	@RequestMapping
	public String logOutAccount(HttpServletRequest request, ModelMap map) {
		
		return "redirect:/Login-Form.htm";
	}
}
