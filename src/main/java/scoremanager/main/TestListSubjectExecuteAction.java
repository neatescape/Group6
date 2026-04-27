package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		TestListSubjectDao tListSubDao = new TestListSubjectDao();
		
		List<TestListSubject> tListSub=null;
		Map<String, String> errors=new HashMap<>(); // エラーメッセージ
		
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
		
		Subject subject = subDao.get(subCd, school);
		
		// 10年前から1年後まで年をリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}
		// ログインユーザーの学校コードをもとに、クラス番号と科目の一覧を取得
		List<String> cNumSet = cNumDao.filter(school);
		List<Subject> subSet = subDao.filter(school);
		
		// セレクトボックスの選択肢
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", cNumSet);
		req.setAttribute("subject_set", subSet);
		
		// 入学年度、クラス、科目のいずれかが未入力の場合
		if (entYear == 0 || (classNum.equals("0")) || (subCd.equals("0"))) {
			errors.put("f1", "入学年度とクラスと科目を選択してください");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_list.jsp").forward(req, res);
			return;
		}
		
		tListSub = tListSubDao.filter(entYear, classNum, subject, school);
		
		// セレクトボックスのselectedを指定するための値
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subject);
		// 検索結果
		req.setAttribute("test_list_subject", tListSub);
		
		req.getRequestDispatcher("test_list_subject.jsp").forward(req, res);
	}
}