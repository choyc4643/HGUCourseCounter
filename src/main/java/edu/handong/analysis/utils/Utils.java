package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Utils {

	public static void writeAFile(ArrayList<String> lines, String targetFilePath) {
		PrintWriter bufferedWriter = null;
 
		try{
			
		    File file = new File(targetFilePath);
	        if(!file.exists()) {
	       		file.getParentFile().mkdirs();
	        }
                      
	        bufferedWriter = new PrintWriter(file);
                       
        }
		
		catch (IOException e) {
    		System.out.println(e);
        }

		for(String line : lines) {
        	bufferedWriter.println(line);
        }

        bufferedWriter.close();
		
	}

	
	//처음 카테고리를 제외한 data file의 정보를 ArrayList에 라인단위로 저장.
	public static ArrayList<String> getLines(String file, boolean removeHeader) {
		ArrayList<String> lines = new ArrayList<String>();

		try{
            //파일 객체 생성
            File dataFile = new File(file);
            //입력 스트림 생성
            FileReader filereader = new FileReader(dataFile);
            //입력 버퍼 생성
            BufferedReader bufReader = new BufferedReader(filereader);
   	     	String line = "";
   	     	
            while((line = bufReader.readLine()) != null) {
       
        		lines.add(line);
            }
            if(removeHeader)
            	lines.remove(0);
            //.readLine()은 끝에 개행문자를 읽지 않는다.            
            bufReader.close();
        }catch (FileNotFoundException e) {
        	System.out.println("File not exist");
        	System.exit(0);
            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
            System.exit(0);
        }


			
		return lines;
	}

}
