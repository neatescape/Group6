package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		StudentDao stuDao = new StudentDao();
		SubjectDao subDao = new SubjectDao();
		TestDao testDao = new TestDao();
		String error = null; // エラーメッセージ
		
		// 入力値の取得
		String[] stuNoList = req.getParameterValues("regist");
		int no = Integer.parseInt(req.getParameter("count"));
		String subCd = req.getParameter("subject");
		
		List<Test> list = new ArrayList<>();
		
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
		
		for (String stuNo : stuNoList) {
			String key = "point_" + stuNo;
	        String point = req.getParameter(key);
	        if (point != null) {
	        	Test test = new Test();
	        	
	        	test.setStudent(stuDao.get(stuNo));
	        	test.setClassNum(stuDao.get(stuNo).getClassNum());
				test.setSubject(subDao.get(subCd, school));
				test.setSchool(school);
				test.setNo(no);
				test.setPoint(Integer.parseInt(point));
				
				list.add(test);
	        }
		}
		
		// 登録処理
		boolean result = testDao.save(list);
		
		// 結果に応じて画面遷移
		if (result) {
			req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
		} else {
			error = "変更に失敗しました";
			req.setAttribute("error", error);
			req.getRequestDispatcher("subjectUpdate.action").forward(req, res);
		}
	}
}