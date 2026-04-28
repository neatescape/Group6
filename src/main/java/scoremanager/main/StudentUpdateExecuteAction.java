// 杉本
package scoremanager.main;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
    	
    	HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
        String no = req.getParameter("no");
        String name = req.getParameter("name");
        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("classnum");
        boolean isAttend = req.getParameter("is_attend") != null;
        // ログイン中教師の学校を取得
        School school = teacher.getSchool();

        // Student インスタンス生成（更新用）
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(school);

        // DAO を使って更新処理
        StudentDao dao = new StudentDao();
        boolean result = dao.save(student);   // save が INSERT/UPDATE 両対応

        // 結果に応じて画面遷移
        if (result) {
            req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
        } else {
            req.setAttribute("student", student);
            req.getRequestDispatcher("student_update.jsp").forward(req, res);
        }
    }
}
