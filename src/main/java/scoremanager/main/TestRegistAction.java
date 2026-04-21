package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
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
		
		String entYearStr=""; // 入力された入学年度
		String classNum=""; // 入力されたクラス番号
		String subjectCd=""; // 入力された科目コード
		String noStr="";	// 入力されたテスト回数
		int entYear=0;		// 入学年度
		int no=0;			// テスト回数
		// List<Student> students=null; // 学生リスト
		// List<Test> tests=null; // 得点リスト
		LocalDate todaysDate=LocalDate.now(); // LocalDateインスタンスを取得
		int year=todaysDate.getYear(); // 現在の年を取得
		// StudentDao sDao=new StudentDao(); // 学生Dao
		ClassNumDao cNumDao=new ClassNumDao();
		SubjectDao subDao=new SubjectDao();
		// TestDao testDao=new TestDao();
		// Map<String, String> errors=new HashMap<>(); // エラーメッセージ
		
		// リクエストパラメーターの取得
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectCd = req.getParameter("f3");
		noStr = req.getParameter("f4");
		
		// int型に直す
		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}
		if (noStr != null) {
			no = Integer.parseInt(noStr);
		}
		
		// 10年前から1年後まで年をリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}
		// ログインユーザーの学校コードをもとに、クラス番号と科目の一覧を取得
		List<String> cNumSet = cNumDao.filter(teacher.getSchool());
		List<Subject> subSet = subDao.filter(teacher.getSchool());
		
		// DBからデータ取得
		//tests = testDao.filter(entYear, classNum, subDao.get(subjectCd, teacher.getSchool()), no, teacher.getSchool());
		
		List<TestListSubject> testListsBySubject = new ArrayList<>();
		
		// レスポンス値をセット
		// セレクトボックスのselectedを指定するための値
		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("f4", no);
		// セレクトボックスの選択肢
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", cNumSet);
		req.setAttribute("subject_set", subSet);
		// 検索結果
		req.setAttribute("test_list_subject", testListsBySubject);
		
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);
	}
}