package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends Dao {
	
	private String baseSql = "select * from test where school_cd=?";
	// 1件取得
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from test where "
					+ "student_no=? and subject_cd=? and school_cd=? and no=?");
			statement.setString(1, student.getNo());
			statement.setString(2, subject.getCd());
			statement.setString(3, school.getCd());
			statement.setInt(4, no);
			
			ResultSet rSet = statement.executeQuery();
			
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			SchoolDao schoolDao = new SchoolDao();
			
			if (rSet.next()) {				
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), schoolDao.get(rSet.getString("school_cd"))));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
			} else {
				test = null;
			}
			
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
		
		return test;
	}
	
	private List<Test> postFilter(ResultSet rSet, School school) {
		List<Test> list = new ArrayList<>();
		
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();
		
		try {
			while(rSet.next()) {
				Test test = new Test();
					
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), schoolDao.get(rSet.getString("school_cd"))));
				test.setSchool(schoolDao.get(rSet.getString("school_cd")));
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
				
				list.add(test);
			}
			
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		ResultSet rSet = null;
		
		String condition = "and ent_year=? and class_num=? and no=?";
		String order = " order by student_no asc";
		
		try {
			statement = connection.prepareStatement(baseSql + condition + order);
			statement.setString(1, school.getCd());
			statement.setInt(2, entYear);
			statement.setString(3, classNum);
			statement.setInt(4, no);
			rSet = statement.executeQuery();
			
			list = postFilter(rSet, school);
			
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
	
	
	
	
	
	
	
	
	
	