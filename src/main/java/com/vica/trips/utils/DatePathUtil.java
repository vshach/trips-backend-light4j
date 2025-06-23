package com.vica.trips.utils;

import com.networknt.config.Config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;


public class DatePathUtil {

	public static String FILE_DIRECTORY;// = "C:/vicas/professional/tripsProject/uploads/";
	static {
		// Code here runs once when the class is loaded
		Map<String, Object> values = Config.getInstance().getJsonMapConfig("values");
		FILE_DIRECTORY = (String) values.get("photoUploadDir");
	}

	//from database to storage
	//2023-01-16  -> 2023/01Jan-16/ +filename will go here afterwards
    //this is for the path on the disk
	public static String convertToFilepathOnDisk(String dateString ) throws DateTimeParseException{
				
		System.out.println("fileDirectory: "+ FILE_DIRECTORY);
		
		LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		String datePath = splitDateIntoFolders( date );
		String fullPath = "";
    	if (FILE_DIRECTORY.endsWith("/"))
    		fullPath = FILE_DIRECTORY + datePath;
    	else
    		fullPath = FILE_DIRECTORY + "/"+datePath;
    	System.out.println("fullPath: "+ fullPath );
    	return fullPath;
	}	  
	
	//from database to storage
	//2023-01-16  -> 2023/01Jan-16/ +filename will go here afterwards
	//this is for the URL to serve the file
	public static String splitDateIntoFolders(LocalDate date ) {
					
		StringBuffer sb = new StringBuffer( );
		sb.append( date.getYear() );
		sb.append( "/" );
		int monthVal = date.getMonthValue();
		if (monthVal<9)
			sb.append( "0" );
		sb.append( monthVal );
		String monthName = date.getMonth().getDisplayName( TextStyle.SHORT, Locale.CANADA);
		if ( '.' == monthName.charAt( monthName.length()-1))
			monthName = monthName.substring(0, monthName.length()-1);
		sb.append( monthName );
		sb.append("-");
		if ( date.getDayOfMonth()<10)
			sb.append("0");
		sb.append( date.getDayOfMonth());
		sb.append( "/" );
		String datePath = sb.toString();				
		return datePath;
	}
}
