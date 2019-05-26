package edu.handong.analysis.datamodel;

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

	public Course(String line){
		// constructor에서 line을 받아 split해서 field초기화
		/* 필요에 따라 getter, setter 알아서 정의 */
		this.studentId = line.split(",")[0].trim();
		this.yearMonthGraduated =line.split(",")[1].trim();
		this.firstMajor =line.split(",")[2].trim();
		this.secondMajor =line.split(",")[3].trim();
		this.courseCode =line.split(",")[4].trim();
		this.courseName =line.split(",")[5].trim();
		this.courseCredit =line.split(",")[6].trim();
		this.yearTaken = Integer.parseInt(line.split(",")[7].trim());
		this.semesterCourseTaken = Integer.parseInt(line.split(",")[8].trim());
		
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
		yearTaken1 =String.valueOf(yearTaken) + "-" + String.valueOf(semesterCourseTaken);
		return yearTaken1;
	}

	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	
	
}
