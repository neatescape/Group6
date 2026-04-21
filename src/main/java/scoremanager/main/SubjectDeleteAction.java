package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectDeleteAction extends Action {
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
			
		String cd = req.getParameter("cd");
		SubjectDao sd = new SubjectDao();
		
		// ログイン中教師の学校を取得
		Teacher teacher = (Teacher)session.getAttribute("user");
		if (teacher == null) {
			teacher = new Teacher();
			School school = new School();
			school.setCd("tes");
			school.setName("テスト校");
			teacher.setId("admin1");
			teacher.setPassword("password");
			teacher.setName("管理者1");
		    teacher.setSchool(school);
		}
		School school = teacher.getSchool();
		
		// Subjectの情報を取得する
		Subject subject = sd.get(cd, school);
		
		// 値をセット
		req.setAttribute("subject_name", subject.getName());
		req.setAttribute("subject_cd", subject.getCd());
		
		req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
	}
}