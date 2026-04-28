// 河端
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	
	// private String baseSql = "";
	
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		
		try {
			while(rSet.next()) {
				TestListStudent testListStudent = new TestListStudent();
				
				testListStudent.setSubjectName(rSet.getString("subject_name"));
				testListStudent.setSubjectCd(rSet.getString("subject_cd"));
				testListStudent.setNum(rSet.getInt("num"));
				testListStudent.setPoint((Integer)rSet.getObject("point"));
				
				list.add(testListStudent);
			}
			
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		ResultSet rSet = null;
		
		try {
			statement = connection.prepareStatement(
					"select subject.name as subject_name, test.subject_cd, test.no as num, test.point"
					+ " from test join subject on test.subject_cd = subject.cd and test.school_cd = subject.school_cd"
					+ " where test.student_no=?"
					+ " order by test.subject_cd asc");
			statement.setString(1, student.getNo());
			rSet = statement.executeQuery();
			
			list = postFilter(rSet);
			
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		
		return list;
	}
}
