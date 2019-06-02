package edu.handong.analysis.utils;

import java.util.ArrayList;
//import java.io.IOException;
import java.io.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;

public class Utils {

   public static ArrayList<CSVRecord> getLines(String file,boolean removeHeader) {

      ArrayList<CSVRecord> lines = new ArrayList<CSVRecord>();

      try {
         CSVParser parser = new CSVParser(new FileReader(file), CSVFormat.DEFAULT.withHeader()); 
         
         for(CSVRecord a : parser) 
            lines.add(a);
         
         if(removeHeader == true)
            lines.remove(0);

         parser.close();

      }catch (FileNotFoundException e) {
         System.out.println("Not exist file" + file);
         System.exit(0);

      }catch(IOException e){
         System.out.println(e);
      }

      return lines;
   }


   public static void writeAFile(ArrayList<String> lines, String targetFileName) {

      PrintWriter pw = null;

      try {
         
         File result = new File(targetFileName);

         if(!result.exists()) {
            result.getParentFile().mkdirs();
         }
         pw = new PrintWriter(result);

      } catch(FileNotFoundException e) {
         System.out.println("Error opening the file " + targetFileName);
         System.exit(0);
      }

      for(String line: lines) {
         pw.println(line);
      }

      pw.close();
   }




}