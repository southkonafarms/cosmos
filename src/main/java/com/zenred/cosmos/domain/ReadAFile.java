package com.zenred.cosmos.domain;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class ReadAFile {

	private static Logger logger = Logger.getLogger(ReadAFile.class);

	public ReadAFile() {
		super();
	}
	
	public static String readTmpDirectoyForPattern(String pattern0, String pattern1){
		String fileName = "";
		String [] files;
		
		File file = new File("/tmp");
		files = file.list();
		for (String nextFile : files){
			if(nextFile.contains(pattern0) && nextFile.contains(pattern1)){
				fileName = nextFile;
				break;
			}
		}
		return fileName;
	}
	
	/**
	 * 
	 * @param fileName
	 * @return what is read as a string
	 */
	public static String readAsString(String fileName) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			File myObj = new File(fileName);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				stringBuilder.append(data);
			}
			myReader.close();
		} catch (FileNotFoundException fnfe) {
			logger.error(fnfe);
			fnfe.printStackTrace();
		}

		return stringBuilder.toString();
	}
}