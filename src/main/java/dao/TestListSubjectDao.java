package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	
	private String baseSql = "select * from test where school_cd=?";
	
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();
		
		try {
			while(rSet.next()) {
				TestListSubject testListSubject = new TestListSubject();
				
				testListSubject.setEntYear(studentDao.get(rSet.getInt("ent_year")));
				
				testListSubject.setEntYear(rSet.getInt("ent_year"));
				testListSubject.setStudentNo(rSet.getString("student_no"));
				testListSubject.setStudentName(rSet.getString("student_name"));
				testListSubject.setClassNum(rSet.getString("class_num"));
				testListSubject.setPoints(null);
				
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
				
				list.add(testListSubject);
			}
			
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		ResultSet rSet = null;
		
		String condition = "and ent_year=? and class_num=?";
		String order = " order by no asc";
		String conditionIsAttend = "";
		
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		
		try {
			// statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			statement = connection.prepareStatement(
					"select test.student_no, student.name, test.class_num, test.no, test.point"
					+ " from test where subject_cd=? and school_cd=? and class_num=?"
					+ " join student on test.student_no = student.no");
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
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