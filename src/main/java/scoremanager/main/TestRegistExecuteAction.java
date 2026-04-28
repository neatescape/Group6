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
	        	// エラー文表示
	        	if (Integer.parseInt(point) < 0 || Integer.parseInt(point) > 100) {
	        		error = "0～100の範囲で入力してください";
	    			req.setAttribute("error", error);
	    			req.setAttribute("stuNo", stuNo);
	    			req.getRequestDispatcher("TestRegist.action?f1="+stuDao.get(stuNo).getEntYear()+"&f2="+stuDao.get(stuNo).getClassNum().trim()+"&f3="+subCd+"&f4="+no).forward(req, res);
	    			return;
	        	}
	        	
	        	Test test = new Test();
	        	
	        	test.setStudent(stuDao.get(stuNo));
	        	test.setClassNum(stuDao.get(stuNo).getClassNum().trim());
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
			req.getRequestDispatcher("TestRegist.action").forward(req, res);
		}
	}
}
