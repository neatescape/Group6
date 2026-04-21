package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		if (teacher == null) {
			teacher = new Teacher();
			School school = new School();
			school.setCd("tes");
			school.setName("テスト校");
		    teacher.setSchool(school);
		}
		
		SubjectDao subDao=new SubjectDao();
		List<Subject> subjects = subDao.filter(teacher.getSchool());
		
		req.setAttribute("subjects", subjects);
		
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}
}