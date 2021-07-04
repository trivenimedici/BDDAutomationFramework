package com.bdd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.bdd.variables.GlobalVariables.*;
import org.apache.log4j.Logger;

public class Log {
	private static Logger logger = Logger.getLogger(Log.class.getName());

	public static void setLogDirectoryName() {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm.ss");
			LocalDateTime now = LocalDateTime.now();
			System.out.println(dtf.format(now));
			String datetime = dtf.format(now);
			datetime = datetime.replace("/", "-").replace(":", "-").replace(" ", "_time-");
			logFolderName = "Log_date-" + datetime;
			if (new File("ExecutionReports\\ExecutionLogs").exists()) {
				new File("ExecutionReports\\ExecutionLogs" + "\\" + logFolderName).mkdir();
			} else {
				new File("ExecutionReports\\ExecutionLogs").mkdir();
				new File("ExecutionReports\\ExecutionLogs\\" + logFolderName).mkdir();
			}
			logFolder = "ExecutionReports\\ExecutionLogs\\" + logFolderName;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void startTestScript(String TestscriptName) {
		logger.info("*************************************************************");
		logger.info("************ Execution started for : " + TestscriptName + " ************");
		logger.info("*************************************************************");
	}

	public static void endTestScript(String testscriptName, String testStatus) {
		logger.info("*************************************************************");
		logger.info("************ Test execution status is " + testStatus + " ************");
		logger.info("************ Execution ended for : " + testscriptName + " ************");
		logger.info("*************************************************************");
	}

	public static void info(String message) {
		logger.info(message);
	}

	public static void info(double message) {
		logger.info(message);
	}

	public static void info(Object message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(Object object) {
		logger.error("Failed :" + object);
	}

	public static void fatal(String message) {
		logger.fatal(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}

	public static String setLogfile(String scenarioName) throws IOException {
		String logFileName = logFolder + "\\" + ScenarioName + "_log.txt";
		File file = new File(logFileName);
		if (file.createNewFile()) {
			logger.info("log file is created -" + logFileName);
		} else {
			logger.info("log file already exists -" + logFileName);
		}
		return logFileName;
	}

	public static void copyFile(String logFileName) {
		FileInputStream instream = null;
		FileOutputStream outstream = null;
		try {
			File infile = new File("logger.logs");
			File outfile = new File(logFileName);
			instream = new FileInputStream(infile);
			outstream = new FileOutputStream(outfile);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = instream.read(buffer)) > 0) {
				outstream.write(buffer, 0, length);
				instream.close();
				outstream.close();
				System.out.println("File copied successfully");
				logger.info("File copied successfully");
				new FileWriter("logger.logs").close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void renameFile(String newfileName) {
		new File("src\\main\\resources\\logs.txt").renameTo(new File(newfileName));
		new File("src\\main\\resources\\logs.txt").delete();
	}
	
	public static void clearTextFile(String logFileName) throws FileNotFoundException {
		PrintWriter writer= new PrintWriter(logFileName);
		writer.print("");
		writer.close();
	}
	
	
	public static void blankLinesDeleter(String logFileName) throws IOException {
		File oldfile=new File(logFileName);
		Scanner deleter= new Scanner(oldfile);
		System.out.println(deleter);
		String nonblankData="";
		while(deleter.hasNextLine()) {
			String currentLine=deleter.nextLine();
			if(!currentLine.isEmpty()) {
				nonblankData +=currentLine+System.lineSeparator();
			}
		}
		PrintWriter writer= new PrintWriter(new FileWriter(logFileName));
		writer.print(nonblankData);
		writer.close();
	}

	public static void warn(String string, Throwable e) {
		logger.warn(string);
		
	}
}
