package edu.handong.analysis;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.CSVRecord;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;



public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	String input;	//-i
	String output;	// -o
	String analysis;	//-a
	String courseCode;	//-c
	String startYear; //-s 
	String endYear;	// -e
	boolean help;	//-h

	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {

		Options options = createOptions();


		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}



		}




		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		String dataPath = input; // csv file to be analyzed
		String resultPath = output; // the file path where the results are saved.
		ArrayList<CSVRecord> lines = Utils.getLines(dataPath, true);
		ArrayList<String> linesToBeSaved = null ;

		students = loadStudentCourseRecords(lines);

		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 


		if(analysis.equals("1")) {
			// Generate result lines to be saved.
			linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
		}
		else  {
			linesToBeSaved = countNumberOfCoursesTakenInEachSemester2(sortedStudents);
		}

		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath);
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<CSVRecord> lines) {

		students = new HashMap<String, Student>();
		int start = Integer.parseInt(startYear);
		int end = Integer.parseInt(endYear);



		Student onestudent= new Student (lines.get(0).get(0).trim());


		for(CSVRecord new_lines : lines) {
			Course course = new Course(new_lines);

			if(course.getIntegerYearTaken() >= start && course.getIntegerYearTaken() <= end ) {
				System.out.println();

				if(onestudent.getStudentId().equals(course.getStudentId())){

					onestudent.addCourse(course);	//Course수강한 과목들은 넣어주어야한다.
					students.put(onestudent.getStudentId(),onestudent); 

				}else {

					onestudent = new Student(course.getStudentId());
					onestudent.addCourse(course);
				}
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

	private ArrayList<String> countNumberOfCoursesTakenInEachSemester2(Map<String, Student> sortedStudents) {

		int courseCount , yearCount = 0;
		ArrayList<String> all = new ArrayList<String>();
		String line,course = null;
		all.add("Year,Semester,CouseCode,CourseName,TotalStudents,StudentsTaken,Rate");

		for (int i = Integer.parseInt(startYear) ; i<= Integer.parseInt(endYear) ; i++) {
			for(int j=1 ; j<=4 ; j++) {
				String year= Integer.toString(i) +"-"+ Integer.toString(j);

				courseCount = 0 ;
				yearCount = 0;
				course = null;

				for(Student std : sortedStudents.values()) {
					if(std.yearCheck(year)) {

						yearCount ++;
						if(std.courseCodeCheck(courseCode)) {
							courseCount ++;

							if(course == null) {
								course =std.getCourseName(courseCode) ;

							}
						}
					}
				}


				if(courseCount != 0) {
					float rate = (float)courseCount/(float)yearCount;
					double rate2 = Math.round(rate*100);

					line = year.split("-")[0] + "," + year.split("-")[1] 
							+ "," + courseCode + ","  + course + "," + yearCount + "," +
							courseCount + "," + Double.toString(rate2)+"%";
					all.add(line);

				}
			}

		}

		return all; // do not forget to return a proper variable.
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");
			courseCode= cmd.getOptionValue("c");
			startYear = cmd.getOptionValue("s");
			endYear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");


		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}      


	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("input path")
				.required()
				.build());

		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("output path")
				.required()
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()     // this option is intended not to have an option value but just an option
				.argName("Analysis option")
				.required() // this is an optional option. So disabled required().
				.build());

		options.addOption(Option.builder("c").longOpt("course")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("Course code")
				.build());

		options.addOption(Option.builder("s").longOpt("start")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());

		options.addOption(Option.builder("e").longOpt("end")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());         

		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Help")
				.build());

		return options;
	}
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

}
