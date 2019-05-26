package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;


public class Student {
	
	private String studentId; //학번
	private ArrayList<Course> coursesTaken; // 학생이 들은 수업 목록
	private HashMap<String,Integer> semestersByYearAndSemester;
    // key: Year-Semester
    // e.g., 2003-1,
	// 그 학기에 들은 과목의 

	
	public Student(String studentId){ // constructor
		this.studentId = studentId;
		this.coursesTaken = new ArrayList<Course>();
		this.semestersByYearAndSemester = new HashMap<String,Integer>();
		
		//반복문 돌면서 studentId가 일치하면 ArrayList에 course 이름을 add
		
	}
	
	
	public String getStudentId() {
		return studentId;
	}


	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}



	public void addCourse(Course newRecord) {
		//1. addCourse method는 line을 읽으면서 생성된 Course instance를  
		//Student instance에 있는 coursesTaken ArrayList에 추가하는 method입니다.
			//students.add(new Score (line.split("\\|")[0],kor,eng,mat));
		
		coursesTaken.add(newRecord);
		
		if(!(semestersByYearAndSemester.containsKey(newRecord.getYearTaken()))) {
			semestersByYearAndSemester.put(newRecord.getYearTaken(),semestersByYearAndSemester.size()+1);
		}
	}
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		//2.getSemestersByYearAndSemester method는 수강한 년도와 학기 정보를 이용 해당 학생의 순차적인 학기 정보를 저장하는 hashmap을 만듭니다. 
		//예를들어 2001-1에 입학한 학생은 2001-1이 1학기 이고, 2001-2가 2학기 이며 2002-1이 3학기일 겁니다.  아래와 같은 데이터 구조가 됩니다. 
		//계절학기도 공식학기로 포함합니다.
		
//		HashMap<String, Integer> semesterAndYear = new HashMap<String, Integer>();

		
		return semestersByYearAndSemester;
	}
	
	
public int getNumCourseInNthSementer(int semester) {
		
		int count=0;
		String yearAndSemester=null;
		
		for(String sem : semestersByYearAndSemester.keySet()) {
			if(semestersByYearAndSemester.get(sem).equals(semester)) {
				yearAndSemester = sem;
				break;
			}
			
		}
		
		for(Course course1 : coursesTaken) {
			if(course1.getYearTaken().equals(yearAndSemester)) {
				count++;
			}
		}
		
		return count;
	}
	
	public String getStdId() {
		
		return this.studentId;
	}

		
	}
	/* field에 대한 getter setter 필요에 따라 추가 */

