package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;



public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {

		students = new HashMap<String, Student>();
		
		Student onestudent= new Student (lines.get(0).split(",")[0].trim());
		
		
		for(String new_lines : lines) {
			Course course = new Course(new_lines);
			
			if(onestudent.getStudentId().equals(course.getStudentId())){
				
				onestudent.addCourse(course);//Course수강한 과목들은 넣어주어야한다.
				students.put(onestudent.getStudentId(),onestudent); 
				
			}else {
				
				onestudent = new Student(course.getStudentId());
				onestudent.addCourse(course);
			}
			
		}
		
		return students; // do not forget to return a proper variable.

		
		// TODO: Implement this method
		//lines를 읽고 students에 알맞는 key값(studentId)과 Student 값을 넣어준다.
	}
	// 여기서 인스턴스를 만드려면 id 가 필요하지 Course 안에 있는 아이디 사용,코스 인스턴스 먼저 만들고 라인 첫번째 것 겟 겟 스튜
	//코스. 겟 스튜던트 아이디. 

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */

	//sortedStudents를 받아서 필요한 정보만 ArrayList에 저장
	
private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		
		String first , last;
		ArrayList<String> result = new ArrayList<String>();
		result.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		for (Student sort :sortedStudents.values()) {
			first = sort.getStudentId() + "," + Integer.toString(sort.getSemestersByYearAndSemester().size());
		

			for(int i=1 ; i<= sort.getSemestersByYearAndSemester().size();i++) {
				last = "," + Integer.toString(i) + "," + Integer.toString(sort.getNumCourseInNthSementer(i));
				 
				
				result.add(first+last);
				
		}

		}
		
		return result; // do not forget to return a proper variable.
	}
	
	
	
//	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
//		String result;
//		
//		for(Map.Entry<String, Student> stud : sortedStudents.entrySet()) {
//			result = stud.getKey() + "," + stud.get(Map.getKey()).getSemestersByYearAndSemester().size();
//			 
//		}
//		// TODO: Implement this method
//		
//		return null; // do not forget to return a proper variable.
//	}
}
