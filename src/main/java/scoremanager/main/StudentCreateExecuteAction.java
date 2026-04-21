package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentCreateExecuteAction extends Action {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		StudentDao sDao = new StudentDao();
		
		String entYearStr = req.getParameter("entyear");   	
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String classNum = req.getParameter("classnum");
		boolean isAttend = false;
		String error = null;
		int judg = 1;
		
		if (entYearStr.equals("0")) {
			error = "入学年度を選択してください";
			req.setAttribute("error", error);
			req.setAttribute("judg", judg);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
			return;
		}
		
		Student checkstudent = sDao.get(no);
		
		if (checkstudent != null) {
			error = "学籍番号が重複しています";
			req.setAttribute("error", error);
			judg++;
			req.setAttribute("judg", judg);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
			return;
		}
		
		isAttend = true;
		
		Student student = new Student();
		int entYear = Integer.parseInt(entYearStr);
		
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());
		
		boolean line = sDao.save(student);
		
		if (line) {
			req.getRequestDispatcher("student_create_done.jsp").forward(req, res);
		} else {
			error = "エラーが発生しました";
			judg = 0;
			req.setAttribute("error", error);
			req.getRequestDispatcher("StudentCreate.action").forward(req, res);
		}
		
	}
}