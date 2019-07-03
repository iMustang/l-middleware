package middleware.activemq.entity;

import java.util.List;

/**
 * Teacher
 * description:
 */
public class Teacher {
	private String tearchername;
	private List<Student> studentList;

	public String getTearchername() {
		return tearchername;
	}

	public void setTearchername(String tearchername) {
		this.tearchername = tearchername;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}
}
