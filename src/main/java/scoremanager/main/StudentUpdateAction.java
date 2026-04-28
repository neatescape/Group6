// 杉本
package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {
	
	public void execute(
		HttpServletRequest req, HttpServletResponse res
	) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		String no=req.getParameter("no");
		StudentDao sDao=new StudentDao();
		Student students = sDao.get(no);
		ClassNumDao cNumDao=new ClassNumDao();
		
		List<String> list = cNumDao.filter(teacher.getSchool());
		
		req.setAttribute("ent_year", students.getEntYear());
		req.setAttribute("no", students.getNo());
		req.setAttribute("name", students.getName());
		req.setAttribute("class_num_set", list);
		req.setAttribute("isAttend", students.isAttend());

		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}
