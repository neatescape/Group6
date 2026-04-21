package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class LoginAction extends Action {
	
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String id = req.getParameter("id");
		
		req.setAttribute("id", id);
		
		req.getRequestDispatcher("login.jsp").forward(req, res);
	}
}