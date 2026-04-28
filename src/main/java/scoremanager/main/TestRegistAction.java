package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestRegistAction extends Action {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
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
		
		// リクエストパラメーターの取得
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subCd = req.getParameter("f3");
		String noStr = req.getParameter("f4");
		int entYear = -1;
		int no = -1;
		Subject subject = new Subject();
		List<Test> tests = null; // 得点リスト
		
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		TestDao testDao = new TestDao();
		Map<String, String> errors=new HashMap<>(); // エラーメッセージ
		
		// 
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		if (noStr != null) {
			no = Integer.parseInt(noStr);
		}
		if (subCd != null) {
			subject = subDao.get(subCd, school);
		}
		
		// 10年前から1年後まで年をリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}
		// ログインユーザーの学校コードをもとに、クラス番号と科目の一覧を取得
		List<String> cNumSet = cNumDao.filter(teacher.getSchool());
		List<Subject> subSet = subDao.filter(teacher.getSchool());
		// テスト回数のセットを作成
		List<Integer> noSet = new ArrayList<Integer>();
		noSet.add(1);
		noSet.add(2);
		
		// レスポンス値をセット
		// セレクトボックスのselectedを指定するための値
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subject);
		req.setAttribute("f4", no);
		// セレクトボックスの選択肢
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", cNumSet);
		req.setAttribute("subject_set", subSet);
		req.setAttribute("no_set", noSet);
		
		// 入学年度、クラス、科目、回数のいずれかが未入力の場合
		if (entYear == 0 || (classNum != null && classNum.equals("0")) || (classNum != null && subCd.equals("0")) || no == 0) {
			errors.put("f1", "入学年度とクラスと科目と回数を選択してください");
			req.setAttribute("errors", errors);
			req.getRequestDispatcher("test_regist.jsp").forward(req, res);
			return;
		}
		
		// DBからデータ取得
		if (entYear != -1 && classNum != null && subCd != null && no != -1) {
			tests = testDao.filter(entYear, classNum.trim(), subject, no, school);
			
			// 更新だけでなく追加する機能も作りましたがいらなかったらコメントアウト
			// -------------------------------------------------
			// 登録されていない学生も出力するためのもの
			// 入学年度とクラスの条件が合う学生を取得する
			StudentDao stuDao = new StudentDao();
			List<Student> students = stuDao.filter(school, entYear, classNum, true);
			
			// 学生が重複しないように処理をする
			// testsに入っていない学生を入れる
			if (tests != null) {
				List<Student> studentRemove = new ArrayList<>();
				for (Test test : tests) {
					for (Student student : students) {
						if (test.getStudent().getNo() == student.getNo()) {
							studentRemove.add(student);
						}
					}
				}
				students.removeAll(studentRemove);
			}
			
			// testsにstudentを入れる
			if (students != null) {
				for (Student student : students) {
					Test test = new Test();
					test.setStudent(student);
					test.setClassNum(classNum);
					test.setSubject(subject);
					test.setSchool(school);
					test.setNo(no);
					tests.add(test);
				}
			}
			// ---------------------------------------
		}
		
		// 検索結果
		req.setAttribute("tests", tests);
		
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}
