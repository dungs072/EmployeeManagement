package ptithcm.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
		if (hour >= 7 && hour <= 12) {
			idShiftShow = "1";
		} else if (hour > 12 && hour < 17) {
			idShiftShow = "2";
		} else if (hour >= 17 && hour < 24) {
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
		if(list.size()>0) {
			model.addAttribute("startTime",list.get(0).getOpenshift().getShift().getStartShiftTime());
			model.addAttribute("endTime",list.get(0).getOpenshift().getShift().getEndShiftTime());
		}
		return returnToSpecificAccount();
		}
	}

	@RequestMapping(value = "/updateSalary", method = RequestMethod.GET)
	public String updateSalary(HttpServletRequest request, ModelMap model) {
		long now = System.currentTimeMillis();
		Time time = new Time(now);
		float salary;
		String currentStaffSalary = request.getParameter("updateSalaryInfor");
		String[] infor = currentStaffSalary.split(",");
		salary = Float.parseFloat(infor[1]);
		updateSalaryToDB(infor[0], salary, time);
		return showDetailShift(model);
	}

	public void updateSalaryToDB(String maNV, Float salary, Time time) {
		Session session = factory.getCurrentSession();
		List<ShiftDetailEntity> list = getIdAStaffInShiftDetail(maNV, idShiftShow, date_sql);
		String IdAStaffInShiftDetail = list.get(0).getID_CTCA();
		ShiftDetailEntity shift = (ShiftDetailEntity) session.get(ShiftDetailEntity.class, IdAStaffInShiftDetail);
		if(shift.getTHOIGIANCHAMCONG()==null) {
			shift.setTHOIGIANCHAMCONG(time);
		}
		
		StaffEntity staff = (StaffEntity) (session.get(StaffEntity.class, maNV));
		if(!staffPassDataBetweenControllerHandler.getData().contains("AD")||shift.getLUONGCA()==0) {
			float salaryPerHour = staff.getJobPosition().getLUONGTHEOGIO();
			LocalTime startWorkShiftTime = shift.getTHOIGIANDILAM().toLocalTime();
			LocalTime endWorkShiftTime = shift.getTHOIGIANCHAMCONG().toLocalTime();
			LocalTime startShiftTime = shift.getOpenshift().getShift().getStartShiftTime().toLocalTime();
			LocalTime endShiftTime = shift.getOpenshift().getShift().getEndShiftTime().toLocalTime();
		    

			System.out.println(startWorkShiftTime.toString());
			System.out.println(startShiftTime.toString());
			System.out.println(endWorkShiftTime.toString());
			System.out.println(endShiftTime);
			int startWorkEndShiftValue = startWorkShiftTime.compareTo(endShiftTime);
			int endWorkStartShiftValue = endWorkShiftTime.compareTo(startShiftTime);
			if(startWorkEndShiftValue>=0 || endWorkStartShiftValue<=0) {
				salary = 0f;
			}
			else {
				int startValue = startWorkShiftTime.compareTo(startShiftTime);
				int endValue = endWorkShiftTime.compareTo(endShiftTime);
				
				
				LocalTime startTime = startValue>=0?startWorkShiftTime:startShiftTime;
				LocalTime endTime = endValue>=0?endShiftTime:endWorkShiftTime;
				
				System.out.println(startTime.toString());
				System.out.println(endTime.toString());
				
				long differenceHours = startTime.until(endTime, ChronoUnit.HOURS);
		        salary = salaryPerHour* differenceHours;
			}
			
		}
		
		float oldSalary = shift.getLUONGCA();
		shift.setLUONGCA(salary);
		
		session.update(shift);
		
		staff.editSalary(oldSalary,salary);
		session.saveOrUpdate(staff);
	}
	
	@RequestMapping(value="/setFault", method=RequestMethod.GET)
	public String setFault(HttpServletRequest request, ModelMap model) {
		String maNV = request.getParameter("idStaff");
		String fault = request.getParameter("fault");
		String descrip = request.getParameter("punishWay");
		int times = Integer.parseInt(request.getParameter("times"));
		List<OpenShiftEntity> openshift = getIdAOpenShift(idShiftShow, date_sql);
		String idcamo = openshift.get(0).getID_CA_MO();
		String idmistake = getIdMistake(fault);
		updateFaultToDB(maNV, idcamo, idmistake, times,descrip);
		return showDetailShift(model);
	}
	
	public void updateFaultToDB(String maNV, String idcamo, String idloi, int times,String descrip) {
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
			mistakehistory.setDescription(descrip);
			session.save(mistakehistory);
		}
		else {
			check.updateSOLANVIPHAM(times);
			check.setDescription(descrip);
			session.update(check);
		}
	}
	
	@RequestMapping(value = "/Edit")
	public String editSalaryOfThisShift(HttpServletRequest request, ModelMap map) {
		Session session = factory.getCurrentSession();
		String[] rawData = request.getParameter("yesEdit").split(",");
		String newSalaryStr = request.getParameter("editInput");
		ShiftDetailEntity shiftDetailEntity = (ShiftDetailEntity) session.get(ShiftDetailEntity.class,rawData[0].strip());
		float oldSalary = Float.parseFloat(rawData[1].strip());
		float newSalary = Float.parseFloat(newSalaryStr);
		StaffEntity staffEntity = shiftDetailEntity.getStaff();
		staffEntity.editSalary(oldSalary,newSalary);
		shiftDetailEntity.setLUONGCA(newSalary);
		session.update(shiftDetailEntity);
		session.update(staffEntity);
		return displayMainViewFunction(map,shiftDetailEntity);
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
		String hql = "FROM ShiftDetailEntity where ID_CA_MO = :id and XACNHAN = :true and "
				+ " staff.MANV IN (SELECT TENTK FROM AccountEntity WHERE TRANGTHAI = true)";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		query.setBoolean("true", true);
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
			if(list.size()>0) {
				model.addAttribute("startTime",list.get(0).getOpenshift().getShift().getStartShiftTime());
				model.addAttribute("endTime",list.get(0).getOpenshift().getShift().getEndShiftTime());
			}
			return returnToSpecificAccount();
		}
	}
	
	@RequestMapping("/homeAnother")
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
			if(list.size()>0) {
				model.addAttribute("startTime",list.get(0).getOpenshift().getShift().getStartShiftTime());
				model.addAttribute("endTime",list.get(0).getOpenshift().getShift().getEndShiftTime());
			}
			return returnToSpecificAccount();
		}
	}
	

	private String displayMainViewFunction(ModelMap model,ShiftDetailEntity shiftDetail) {
		List<OpenShiftEntity> listOpenId = getIdAOpenShift(idShiftShow, date_sql);
		if(listOpenId.isEmpty()) {
			return "/Admin/Home";
		}
		else {
			String idcamo = listOpenId.get(0).getID_CA_MO();
			List<ShiftDetailEntity> list = getAShiftDetail(idcamo);
			for(var sh:list) {
				if(sh.getID_CTCA().strip().equals(shiftDetail.getID_CTCA().strip())) {
					sh.setLUONGCA(shiftDetail.getLUONGCA());
				}
			}
			List<MistakeEntity> listMistake = getMistake();
			model.addAttribute("shiftNow", list);
			model.addAttribute("idca", idShiftShow);
			model.addAttribute("getDate", date_sql);
			model.addAttribute("faults", listMistake);
			if(list.size()>0) {
				model.addAttribute("startTime",list.get(0).getOpenshift().getShift().getStartShiftTime());
				model.addAttribute("endTime",list.get(0).getOpenshift().getShift().getEndShiftTime());
			}
			
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
