package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		StudentDao stuDao = new StudentDao();
		TestListStudentDao tListStuDao = new TestListStudentDao();
		
		List<TestListStudent> tListStu=null;
		//String error = null; // エラーメッセージ
		
		// 入力値の取得
		String stuNo = req.getParameter("f4");
		
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
		
		Student student = stuDao.get(stuNo);
		
		tListStu = tListStuDao.filter(student);
				
		// レスポンス値をセット
		req.setAttribute("f4", student);
		req.setAttribute("test_list_student", tListStu);
		
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}