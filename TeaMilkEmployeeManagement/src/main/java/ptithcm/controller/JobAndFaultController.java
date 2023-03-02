package ptithcm.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

		toggleJobDeleteButton(jobs);
		toggleFaultDeleteButton(mistakes);

		model.addAttribute("faults", mistakes);
		model.addAttribute("jobs", jobs);

		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/AddJob", method = RequestMethod.GET)
	public String addJob(ModelMap model, JobPositionEntity job) {
		String jobId = jobKeyHandler.getNewKey("CV");
		addJobToDB(job, jobId);
		updateAllTable(model);

		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/AddFault", method = RequestMethod.GET)
	public String addFault(ModelMap model, MistakeEntity fault) {
		String faultId = faultKeyHandler.getNewKey("L");
		addFaultToDB(fault, faultId);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/DeleteJob", method = RequestMethod.GET)
	public String deleteJob(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		JobPositionEntity job = new JobPositionEntity();
		String jobId = request.getParameter("yes-job-warning");
		job.setMACV(jobId);
		if (canDeleteJob(jobId, session)) {
			session.delete(job);
		}
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/DeleteFault", method = RequestMethod.GET)
	public String deleteFault(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		MistakeEntity fault = new MistakeEntity();
		String faultId = request.getParameter("yes-fault-warning");
		fault.setIDLOI(faultId);
		if (canDeleteJob(faultId, session)) {
			session.delete(fault);
		}
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	// just show detail job to modify VITRICONGVIEC
	@RequestMapping(value = "/ShowJob", method = RequestMethod.GET)
	public String showDetailJob(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		String jobId = request.getParameter("InforJob");

		JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, jobId);
		model.addAttribute("showJob", job);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/ShowFault", method = RequestMethod.GET)
	public String showDetailFault(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		String faultId = request.getParameter("InforFault");
		MistakeEntity fault = (MistakeEntity) session.get(MistakeEntity.class, faultId);
		model.addAttribute("showFault", fault);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	// modify VITRICONGVIEC(send data to database)
	@RequestMapping(value = "/UpdateJob", method = RequestMethod.GET)
	public String updateDetailJob(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		String jobId = request.getParameter("updateJobId");
		String nameJob = request.getParameter("updateTENVITRI");
		JobPositionEntity job = (JobPositionEntity) session.get(JobPositionEntity.class, jobId);
		job.setTENVITRI(nameJob);
		session.saveOrUpdate(job);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	@RequestMapping(value = "/UpdateFault", method = RequestMethod.GET)
	public String updateDetailFault(ModelMap model, HttpServletRequest request) {
		Session session = factory.getCurrentSession();
		String faultId = request.getParameter("updateFaultId");
		String desFault = request.getParameter("updateMOTA");
		MistakeEntity fault = (MistakeEntity) session.get(MistakeEntity.class, faultId);
		fault.setMOTA(desFault);
		session.saveOrUpdate(fault);
		updateAllTable(model);
		return "/Admin/JobAndFaultManager";
	}

	private void toggleJobDeleteButton(List<JobPositionEntity> jobs) {
		Session session = factory.getCurrentSession();
		for (var job : jobs) {
			if (job.getTENVITRI().strip().equals("Manager")) {
				job.setCanUpdate(false);
			}
			job.setCanDelete(canDeleteJob(job.getMACV(), session));
		}
	}

	private void toggleFaultDeleteButton(List<MistakeEntity> faults) {
		Session session = factory.getCurrentSession();
		for (var fault : faults) {
			fault.setCanDelete(canDeleteFault(fault.getIDLOI(), session));
		}
	}

	private void updateAllTable(ModelMap model) {
		List<JobPositionEntity> jobs = getJobs();
		List<MistakeEntity> faults = getFaults();
		toggleJobDeleteButton(jobs);
		toggleFaultDeleteButton(faults);
		model.addAttribute("jobs", jobs);
		model.addAttribute("faults", faults);
	}

	private void addJobToDB(JobPositionEntity job, String id) {
		if (job == null) {
			return;
		}
		job.setMACV(id);
		Session session = factory.getCurrentSession();
		session.save(job);
	}

	private void addFaultToDB(MistakeEntity fault, String id) {
		if (fault == null) {
			return;
		}
		fault.setIDLOI(id);
		Session session = factory.getCurrentSession();
		session.save(fault);
	}

	private boolean canDeleteJob(String id, Session session) {

		String hql = "SELECT COUNT(*) FROM StaffEntity WHERE jobPosition.MACV = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (long) query.uniqueResult() == 0;
	}

	@SuppressWarnings("unused")
	private boolean canDeleteFault(String id, Session session) {
		String hql = "SELECT COUNT(*) FROM MistakeHistoryEntity WHERE mistakeEntity.IDLOI = :id";
		Query query = session.createQuery(hql);
		query.setString("id", id);
		return (long) query.uniqueResult() == 0;
	}

	@SuppressWarnings("unchecked")
	private List<MistakeEntity> getFaults() {
		Session session = factory.getCurrentSession();
		String hql = "FROM MistakeEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	private List<JobPositionEntity> getJobs() {
		Session session = factory.getCurrentSession();
		String hql = "FROM JobPositionEntity";
		Query query = session.createQuery(hql);
		return query.list();
	}

}
