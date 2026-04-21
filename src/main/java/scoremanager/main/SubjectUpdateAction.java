package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateAction extends Action {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String cd = req.getParameter("cd");
		req.setAttribute("cd", cd);
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}