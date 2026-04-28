// murakami
package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;


public class SubjectDeleteExecuteAction extends Action {
	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
			// cdを取得する
			String cd = request.getParameter("cd");
			String name = request.getParameter("name");
			
			// SubjectDaoを実体化する
			SubjectDao sd = new SubjectDao();
			
			// ログイン中教師の学校を取得
			HttpSession session = request.getSession();
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
			// Schoolを実体化する
			School school = teacher.getSchool();
			
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(school);
			
			// deleteを実行する
			boolean count = sd.delete(subject);
			
			if (count) {
				request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
			} else {
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
	}
}
