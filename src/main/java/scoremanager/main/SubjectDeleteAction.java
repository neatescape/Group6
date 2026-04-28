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


public class SubjectDeleteAction extends Action {
	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {
			// cdを取得する
			String cd = request.getParameter("cd");
			// SubjectDaoを実体化する
			SubjectDao sd = new SubjectDao();
			
			// Schoolを実体化する
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
			School school = teacher.getSchool();
			
			// Subjectの情報を取得する
			Subject subject = sd.get(cd, school);
			
			// subject_nameとsubject_cdをセットする
			request.setAttribute("subject_name", subject.getName());
			request.setAttribute("subject_cd", subject.getCd());
			
			request.getRequestDispatcher("subject_delete.jsp").forward(request, response);

	}
}
