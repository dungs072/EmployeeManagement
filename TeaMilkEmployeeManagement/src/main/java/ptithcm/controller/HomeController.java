package ptithcm.controller;

import java.util.Date;
import java.time.LocalTime;
import java.util.List;
import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.ShiftDetailEntity;

@Transactional
@Controller
@RequestMapping("/home/")
public class HomeController {
	@Autowired
	SessionFactory factory;
	@RequestMapping("index")
	public String index(ModelMap model) {
		LocalTime time = LocalTime.now();
		int hour = time.getHour();
		int id=0;
		if(hour>=7 && hour<=11) {
			id=1;
		}
		else if(hour >= 13 && hour<=17) {
			id=2;
		}
		else if(hour >=17 && hour <=21) {
			id=3;
		}
		long millis = System.currentTimeMillis(); 
		Date date_sql = new Date(millis);
		List<ShiftDetailEntity> list = getAShiftDetail(id,date_sql);
		model.addAttribute("shiftNow",list);
		return "/Admin/Home";
	}
	
	@SuppressWarnings("unchecked") 	
	public List<ShiftDetailEntity> getAShiftDetail(int id, Date date){
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity where IDCA = :id AND THOIGIANDANGKI = :date ";
		Query query = session.createQuery(hql);
		String idd = Integer.toString(id);
		SimpleDateFormat DateFor = new SimpleDateFormat("YYYY-MM-DD");
		String stringDate= DateFor.format(date);
		query.setString("id",idd);

		query.setString("date",stringDate);
		List<ShiftDetailEntity> list = query.list();
		return list;
	}
}
