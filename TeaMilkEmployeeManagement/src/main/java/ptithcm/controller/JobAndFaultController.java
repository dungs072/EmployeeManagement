package ptithcm.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.IncrementNumberAndTextKeyHandler;
import ptithcm.entity.AccountEntity;
import ptithcm.entity.JobPositionEntity;
import ptithcm.entity.MistakeEntity;
import ptithcm.entity.StaffEntity;

@Transactional
@Controller
@RequestMapping("/JobAndFault")
public class JobAndFaultController {

	
	@Autowired
	SessionFactory factory;
	
	@Autowired
	@Qualifier("jobKeyHandler")
	IncrementNumberAndTextKeyHandler jobKeyHandler;
	
	@Autowired
	@Qualifier("faultKeyHandler")
	IncrementNumberAndTextKeyHandler faultKeyHandler;
	
	@RequestMapping
	public String jobFaultDisplay(ModelMap model) {
		
		List<MistakeEntity> mistakes = getFaults();
		List<JobPositionEntity> jobs = getJobs();
		
		model.addAttribute("faults",mistakes);
		model.addAttribute("jobs",jobs);
		
		return "/Admin/JobAndFaultManager";
	}
	@RequestMapping(value = "/AddJob",method = RequestMethod.GET)
	public String addJob(ModelMap model,JobPositionEntity job) {
		String jobId = jobKeyHandler.getNewKey("CV");
		addJobToDB(job,jobId);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}
	
	@RequestMapping(value = "/AddFault",method = RequestMethod.GET)
	public String addFault(ModelMap model, MistakeEntity fault) {
		String faultId = faultKeyHandler.getNewKey("L");
		addFaultToDB(fault,faultId);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}
	
	@RequestMapping(value = "/DeleteJob", method = RequestMethod.GET)
	public String deleteJob(ModelMap model,String id) {
		Session session = factory.getCurrentSession();
		JobPositionEntity job = new JobPositionEntity();
		job.setMACV(id);
		if(canDeleteJob(id,session))
		{
			session.delete(job);
		}
		else
		{
			
		}
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}
	
	private void updateAllTable(ModelMap model) {
		List<JobPositionEntity> jobs = getJobs();
		List<MistakeEntity> faults = getFaults();
		model.addAttribute("jobs",jobs);
		model.addAttribute("faults",faults);
	}
	
	private void addJobToDB(JobPositionEntity job,String id) {
		if(job==null) {return;}
		job.setMACV(id);
		Session session = factory.getCurrentSession();
		session.save(job);
	}
	
	private void addFaultToDB(MistakeEntity fault,String id) {
		if(fault==null) {return;}
		fault.setIDLOI(id);
		Session session = factory.getCurrentSession();
		session.save(fault);
	}
	
	private boolean canDeleteJob(String id,Session session) {
		
		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE jobPosition.MACV = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (long)query.uniqueResult()==0;
	}
	
	@SuppressWarnings("unused")
	private boolean canDeleteFault(String id,Session session) {
		String hql = "SELECT COUNT(*) FROM MistakeHistoryEntity WHERE mistakeEntity.IDLOI = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (long)query.uniqueResult()==0;
	}
	
	@SuppressWarnings("unchecked")
	private List<MistakeEntity> getFaults()
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	private List<JobPositionEntity> getJobs(){
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	
}
