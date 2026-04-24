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

public class SubjectUpdateExecuteAction extends Action {

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
		
		// 科目コードの一覧を参照し、科目コードが存在するか確認
		boolean existsCd = false;
		List<Subject> subjects = subDao.filter(teacher.getSchool());
		for (Subject subject : subjects) {
			if (cd.equals(subject.getCd())) {
				existsCd = true;
				break;
			}
		}
		
		// 科目コードが別の画面で削除されている場合
		if (existsCd == false) {
			error = "科目が存在していません";
			req.setAttribute("error", error);
			req.getRequestDispatcher("SubjectUpdate.action").forward(req, res);
			return;
		}
		
		// Subject インスタンス生成
		Subject sub = new Subject();
		sub.setCd(cd);
		sub.setName(name);
		sub.setSchool(school);
		
		// 登録処理
		boolean result = subDao.save(sub);
		
		// 結果に応じて画面遷移
		if (result) {
			req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
		} else {
			error = "変更に失敗しました";
			req.setAttribute("error", error);
			req.getRequestDispatcher("subjectUpdate.action").forward(req, res);
		}
	}
}