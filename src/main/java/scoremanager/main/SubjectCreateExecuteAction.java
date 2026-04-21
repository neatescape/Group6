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

public class SubjectCreateExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		SubjectDao subDao = new SubjectDao();
		String error = null; // エラーメッセージ
		
		// 入力値の取得
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");
		
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
		
		// 値をセット
		req.setAttribute("cd", cd);
		req.setAttribute("name", name);
		
		// 科目コードが3文字でない場合
		if (cd.length() != 3) {
			error = "科目コードは3文字で入力してください";
			req.setAttribute("error", error);
			req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
			return;
		}
		
		// 科目コードの一覧を取得
		List<Subject> subjects = subDao.filter(teacher.getSchool());
		for (Subject subject : subjects) {
			// 科目コードが重複している場合
			if (cd.equals(subject.getCd())) {
				error = "科目コードが重複しています";
				req.setAttribute("error", error);
				req.getRequestDispatcher("SubjectCreate.action").forward(req, res);
				return;
			}
		}
		
		Subject sub = new Subject();
		sub.setCd(cd);
		sub.setName(name);
		sub.setSchool(school);
		
		// 登録処理
		boolean result = subDao.save(sub);
		
		// 結果に応じて画面遷移
		if (result) {
			req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
		} else {
			error = "科目コードが重複しています";
			req.setAttribute("error", error);
			req.getRequestDispatcher("subjectCreate.action").forward(req, res);
		}
	}
}