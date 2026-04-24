package scoremanager.main;

import java.util.List;

import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//HttpSession session = req.getSession();
		
		List<TestListStudent> tListStu=null;
		StudentDao stuDao = new StudentDao();
		TestListStudentDao tListStuDao = new TestListStudentDao();
		//String error = null; // エラーメッセージ
		
		// 入力値の取得
		String stuNo = req.getParameter("f4");
		
		// ログイン中教師の学校を取得
//		Teacher teacher = (Teacher)session.getAttribute("user");
//		if (teacher == null) {
//			teacher = new Teacher();
//			School school = new School();
//			school.setCd("tes");
//			school.setName("テスト校");
//			teacher.setId("admin1");
//			teacher.setPassword("password");
//			teacher.setName("管理者1");
//		    teacher.setSchool(school);
//		}
//		School school = teacher.getSchool();
		
		tListStu = tListStuDao.filter(stuDao.get(stuNo));
				
		// レスポンス値をセット
		req.setAttribute("f4", stuNo);
		req.setAttribute("test_list_student", tListStu);
		
		req.getRequestDispatcher("TestList.action").forward(req, res);
	}
}