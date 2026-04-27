package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {
	
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
		
		
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDao cNumDao = new ClassNumDao();
		SubjectDao subDao = new SubjectDao();
		//Map<String, String> errors=new HashMap<>(); // エラーメッセージ
		
		
		// 10年前から1年後まで年をリストに追加
		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}
		// ログインユーザーの学校コードをもとに、クラス番号と科目の一覧を取得
		List<String> cNumSet = cNumDao.filter(school);
		List<Subject> subSet = subDao.filter(school);
		
		// レスポンス値をセット
		// セレクトボックスの選択肢
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", cNumSet);
		req.setAttribute("subject_set", subSet);
		
		req.getRequestDispatcher("test_list.jsp").forward(req, res);
	}
}