// 河端
package scoremanager.main;

import java.sql.SQLException;

import bean.Teacher;
import dao.TeacherDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LoginExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		Teacher teacher = new Teacher();
		TeacherDao tDao = new TeacherDao();
		String error = null;
		
		// 入力値の取得
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		
		try {
			// ログイン処理
			teacher = tDao.login(id, password);
		} catch (SQLException sqle) {
			// システムエラー発生時
			req.getRequestDispatcher("error.jsp").forward(req, res);
			return;
		}
		
		// ID及びパスワードが一致する教師データがない場合
		if (teacher == null) {
			error = "ログインに失敗しました。IDまたはパスワードが正しくありません。";
			req.setAttribute("error", error);
			req.getRequestDispatcher("Login.action").forward(req, res);
			return;
		}
		
		teacher.setAuthenticated(true);
		session.setAttribute("user", teacher);
		
		req.getRequestDispatcher("Menu.action").forward(req, res);
	}
}
