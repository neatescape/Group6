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
	
	//private String baseSql = ""
	
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		try {
			statement = connection.prepareStatement("select * from test where"
					+ " student_no=? and subject_cd=? and school_cd=? and no=?");
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
		
		try {
			StudentDao studentDao = new StudentDao();
			SubjectDao subjectDao = new SubjectDao();
			
			while(rSet.next()) {
				Test test = new Test();
				
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				test.setSubject(subjectDao.get(rSet.getString("subject_cd"), school));
				test.setSchool(school);
				test.setNo(rSet.getInt("no"));
				test.setPoint(rSet.getInt("point"));
				test.setClassNum(rSet.getString("class_num"));
				
				list.add(test);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		ResultSet rSet = null;
		
		try {
			statement = connection.prepareStatement(
					"select student_no, subject_cd, test.school_cd, test.no, point, test.class_num"
					+ " from test join student on test.student_no = student.no and test.class_num = student.class_num"
					+ " where ent_year=? and test.class_num=? and subject_cd=? and test.no=? and test.school_cd=?"
					+ " order by student_no asc");
			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, subject.getCd());
			statement.setInt(4, no);
			statement.setString(5, school.getCd());
			
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
	
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();
		
		PreparedStatement statement = null;
		
		int count = 0;
		
		try {
			for (Test test : list) {
				Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());
				if (old == null) {
					statement = connection.prepareStatement(
							"insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");
					statement.setString(1, test.getStudent().getNo());
					statement.setString(2, test.getSubject().getCd());
					statement.setString(3, test.getSchool().getCd());
					statement.setInt(4, test.getNo());
					statement.setInt(5, test.getPoint());
					statement.setString(6, test.getClassNum());
				} else {
					statement = connection.prepareStatement(
							"update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=? and class_num=?");
					statement.setInt(1, test.getPoint());
					statement.setString(2, test.getStudent().getNo());
					statement.setString(3, test.getSubject().getCd());
					statement.setString(4, test.getSchool().getCd());
					statement.setInt(5, test.getNo());
					statement.setString(6, test.getClassNum());
				}
				count = statement.executeUpdate();
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
		
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
//	private boolean save(Test test, Connection connection) {
//		
//		
//		return true;
//	}
}