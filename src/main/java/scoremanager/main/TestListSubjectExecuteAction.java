package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		List<TestListSubject> tListSub=null;
		SubjectDao subDao = new SubjectDao();
		TestListSubjectDao tListSubDao = new TestListSubjectDao();
		//String error = null; // エラーメッセージ
		
		// 入力値の取得
		int entYear = Integer.parseInt(req.getParameter("f1"));
		String classNum = req.getParameter("f2");
		String subCd = req.getParameter("f3");
		
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
		
		tListSub = tListSubDao.filter(entYear, classNum, subDao.get(subCd, school), school);
				
		// レスポンス値をセット
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subCd);
		req.setAttribute("test_list_subject", tListSub);
		
		req.getRequestDispatcher("TestList.action").forward(req, res);
	}
}