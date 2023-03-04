package ptithcm.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ptithcm.entity.MistakeEntity;
import ptithcm.entity.OpenShiftEntity;
import ptithcm.entity.ShiftDetailEntity;
import ptithcm.entity.StaffEntity;
import ptithcm.entity.MistakeHistoryEntity;
import ptithcm.bean.PassDataBetweenControllerHandler;
import ptithcm.bean.PrimaryKeyWithMoreDataHandler;

@Transactional
@Controller
public class HomeController{
	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("staffPassDataHandler")
	PassDataBetweenControllerHandler staffPassDataBetweenControllerHandler;

	@Autowired
	@Qualifier("primaryKeyHandler")
	PrimaryKeyWithMoreDataHandler PrimaryKeyWithMoreDataHandler;
	
	String idShiftShow = "1";
	long millis = System.currentTimeMillis();
	Date date_sql = new Date(millis);
	
	@RequestMapping("/home")
	public String showShift(ModelMap model) {
		LocalTime time = LocalTime.now();
		int hour = time.getHour();
		if (hour >= 7 && hour < 11) {
			idShiftShow = "1";
		} else if (hour >= 13 && hour < 17) {
			idShiftShow = "2";
		} else if (hour >= 17 && hour < 21) {
			idShiftShow = "3";
		}
		List<OpenShiftEntity> listOpenId = getIdAOpenShift(idShiftShow, date_sql);
		if(listOpenId.isEmpty()) {
			return returnToSpecificAccount();
		}
		else {
		String idcamo = listOpenId.get(0).getID_CA_MO();
		List<ShiftDetailEntity> list = getAShiftDetail(idcamo);
		List<MistakeEntity> listMistake = getMistake();
		model.addAttribute("shiftNow", list);
		model.addAttribute("idca", idShiftShow);
		model.addAttribute("getDate", date_sql);
		model.addAttribute("faults", listMistake);
		return returnToSpecificAccount();
		}
	}

	@RequestMapping(value = "/updateSalary", method = RequestMethod.GET)
	public String updateSalary(HttpServletRequest request, ModelMap model) {
		float salary;
		String currentStaff = request.getParameter("updateSalary");
		salary = Float.parseFloat(request.getParameter("salaryOfShift"));
		updateSalaryToDB(currentStaff, salary);
		return showDetailShift(model);
	}

	public void updateSalaryToDB(String maNV, Float salary) {
		Session session = factory.getCurrentSession();
		StaffEntity staff = (StaffEntity) (session.get(StaffEntity.class, maNV));
		staff.updateSalary(salary);
		session.saveOrUpdate(staff);
		List<ShiftDetailEntity> list = getIdAStaffInShiftDetail(maNV, idShiftShow, date_sql);
		String IdAStaffInShiftDetail = list.get(0).getID_CTCA();
		ShiftDetailEntity shift = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, IdAStaffInShiftDetail);
		shift.setTRANGTHAILUONG(true);
		shift.setLUONGCA(salary);
		session.update(shift);
	}
	
	@RequestMapping(value="/setFault", method=RequestMethod.GET)
	public String setFault(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("idStaff");
		String fault = request.getParameter("fault");
		int times = Integer.parseInt(request.getParameter("times"));
		List<OpenShiftEntity> openshift = getIdAOpenShift(idShiftShow, date_sql);
		String idcamo = openshift.get(0).getID_CA_MO();
		String idmistake = getIdMistake(fault);
		updateFaultToDB(maNV, idcamo, idmistake, times);
		return showDetailShift(model);
	}
	
	public void updateFaultToDB(String maNV, String idcamo, String idloi, int times) {
		Session session = factory.getCurrentSession();
		List<ShiftDetailEntity> list = getIdAStaffInShiftDetail(maNV, idShiftShow, date_sql);
		String id = list.get(0).getID_CTCA();
		ShiftDetailEntity shiftdetail = (ShiftDetailEntity) session.get(ShiftDetailEntity.class,id);
		MistakeEntity mistake = (MistakeEntity) session.get(MistakeEntity.class, idloi);
		String idMistakeHistory = PrimaryKeyWithMoreDataHandler.getPatternBaseOnDatas(id, idloi) ;
		MistakeHistoryEntity check = (MistakeHistoryEntity) session.get(MistakeHistoryEntity.class, idMistakeHistory);
		if(check == null) {
			MistakeHistoryEntity mistakehistory = new MistakeHistoryEntity();
			mistakehistory.setID_LSLOI(idMistakeHistory);
			mistakehistory.setMistakeEntity(mistake);
			mistakehistory.setShiftDetailEntity(shiftdetail);
			mistakehistory.setSOLANVIPHAM(times);
			session.save(mistakehistory);
		}
		else {
			check.updateSOLANVIPHAM(times);
			session.update(check);
		}
	}

	public String getIdMistake (String mota) {
		Session session = factory.getCurrentSession();
		String hql="Select IDLOI From MistakeEntity Where MOTA = :mota";
		Query query = session.createQuery(hql);
		query.setString("mota", mota);
		String id = (String) query.uniqueResult();
		return id;
	}
	
	public List<ShiftDetailEntity> getAShiftDetail(String id) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ShiftDetailEntity where ID_CA_MO = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		List<ShiftDetailEntity> list = query.list();
		return list;
	}
	
	public List<OpenShiftEntity> getIdAOpenShift(String id, Date date) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OpenShiftEntity where IDCA = :id AND NGAYLAMVIEC = :date ";
		Query query = session.createQuery(hql);
		String d = date.toString();
		query.setString("id", id);
		query.setString("date", d);
		List<OpenShiftEntity> result = query.list();
		return result;
	}
	
	public List<ShiftDetailEntity> getIdAStaffInShiftDetail(String maNV, String id, Date date) {
		Session session = factory.getCurrentSession();
		List<OpenShiftEntity> list = getIdAOpenShift(id, date);
		String idCaMo = list.get(0).getID_CA_MO();
		String hql = "FROM ShiftDetailEntity where ID_CA_MO = :id AND MANV = :maNV";
		Query query = session.createQuery(hql);
		query.setString("id", idCaMo);
		query.setString("maNV", maNV);
		List<ShiftDetailEntity> result = query.list();
		return result;
	}
	

	@RequestMapping(value="/showDetail", method = RequestMethod.GET)
	public String showDetailShift(HttpServletRequest request, ModelMap model) {
		idShiftShow = request.getParameter("idCa");
		List<OpenShiftEntity> listOpenId = getIdAOpenShift(idShiftShow, date_sql);
		if(listOpenId.isEmpty()) {
			return returnToSpecificAccount();
		}
		else{
			String idcamo = listOpenId.get(0).getID_CA_MO();
			List<ShiftDetailEntity> list = getAShiftDetail(idcamo);
			List<MistakeEntity> listMistake = getMistake();
			model.addAttribute("shiftNow", list);
			model.addAttribute("idca", idShiftShow);
			model.addAttribute("getDate", date_sql);
			model.addAttribute("faults", listMistake);
			return returnToSpecificAccount();
		}
	}
	
	public String showDetailShift(ModelMap model) {
		List<OpenShiftEntity> listOpenId = getIdAOpenShift(idShiftShow, date_sql);
		if(listOpenId.isEmpty()) {
			return "/Admin/Home";
		}
		else {
			String idcamo = listOpenId.get(0).getID_CA_MO();
			List<ShiftDetailEntity> list = getAShiftDetail(idcamo);
			List<MistakeEntity> listMistake = getMistake();
			model.addAttribute("shiftNow", list);
			model.addAttribute("idca", idShiftShow);
			model.addAttribute("getDate", date_sql);
			model.addAttribute("faults", listMistake);
			return returnToSpecificAccount();
		}
	}

	public List<MistakeEntity> getMistake(){
		Session session = factory.getCurrentSession();
		String hql="FROM MistakeEntity";
		Query query = session.createQuery(hql);
		List<MistakeEntity> list = query.list();
		return list;
	}
	@RequestMapping(value="/checkin", method=RequestMethod.GET)
	public String checkin(HttpServletRequest request, ModelMap model) {
		long now = System.currentTimeMillis();
		Time time = new Time(now);
		String maNV = request.getParameter("maNV");
		List<ShiftDetailEntity> list = getIdAStaffInShiftDetail(maNV, idShiftShow, date_sql);
		String idctca = list.get(0).getID_CTCA();
		updateTimeCheckInToDB(time, idctca);
		return showDetailShift(model);
	}
	
	public void updateTimeCheckInToDB(Time time, String idCtCa) {
		Session session = factory.getCurrentSession();
		ShiftDetailEntity checkin = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, idCtCa);
		checkin.setTHOIGIANDILAM(time);
		session.update(checkin);
	}
	private String returnToSpecificAccount() {
		String priority = staffPassDataBetweenControllerHandler.getAuthorityId().strip();
		if(priority.equals("AD")) {
			return "Admin/Home";
		}
		else{
			return "Manager/Home";
		}
	}
	
	
}
