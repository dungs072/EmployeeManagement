package ptithcm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.PassDataBetweenControllerHandler;


@Controller

public class RankController {
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;
	@RequestMapping(value="rank", method = RequestMethod.GET)
	public String viewRank() {
		return returnToSpecificAccount();
	}
	
	
	
	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "/Admin/Rank";
		}
		else if(priority.equals("QL")){
			return "/Manager/Rank";
		}
		else {
			return "/Staff/Rank";
		}
	}
}
