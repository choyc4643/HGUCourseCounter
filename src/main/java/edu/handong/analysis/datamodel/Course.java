package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVRecord;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	public Course(CSVRecord new_lines){
		// constructor에서 line을 받아 split해서 field초기화
		/* 필요에 따라 getter, setter 알아서 정의 */
		this.studentId = new_lines.get(0).trim();
		this.yearMonthGraduated =new_lines.get(1).trim();
		this.firstMajor =new_lines.get(2).trim();
		this.secondMajor =new_lines.get(3).trim();
		this.courseCode =new_lines.get(4).trim();
		this.courseName =new_lines.get(5).trim();
		this.courseCredit =new_lines.get(6).trim();
		this.yearTaken = Integer.parseInt(new_lines.get(7).trim());
		this.semesterCourseTaken = Integer.parseInt(new_lines.get(8).trim());
		
	}

	public String getStudentId() {
		return studentId;
	}

	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}

	public String getFirstMajor() {
		return firstMajor;
	}

	public String getSecondMajor() {
		return secondMajor;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public String getCourseCredit() {
		return courseCredit;
	}

	public String getYearTaken() {
		String yearTaken1;
		yearTaken1 =Integer.toString(yearTaken) + "-" + Integer.toString(semesterCourseTaken);
		return yearTaken1;
	}
	public int getIntegerYearTaken() {
		return yearTaken;
	}

	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	
	
}
