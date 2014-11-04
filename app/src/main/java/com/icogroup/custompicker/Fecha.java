package com.icogroup.custompicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class Fecha {

	public static String getFechaNotas() {
		
		String fecha = "";
		String year = "", month = "", day = "";

		Calendar c = Calendar.getInstance();
		day = String.valueOf(c.get(Calendar.DATE));
		month = String.valueOf(c.get(Calendar.MONTH) + 1); // Porque enero empieza en 0
		year = String.valueOf(c.get(Calendar.YEAR));

		if(Integer.parseInt(day) < 10) day = "0" + day;
		if(Integer.parseInt(month) < 10) month = "0" + month;
		
		fecha = day + "|" + month + "|" + year;
		
		return fecha;
	}
	
	public static String getFechaTimeline() {
		
		String fecha = "";
		String year = "", month = "", day = "";

		Calendar c = Calendar.getInstance();
		day = String.valueOf(c.get(Calendar.DATE));
		month = String.valueOf(c.get(Calendar.MONTH));
		year = String.valueOf(c.get(Calendar.YEAR));

		if(Integer.parseInt(day) < 10) day = "0" + day;
		if(Integer.parseInt(month) < 10) month = "0" + month;
		
		fecha = day + " " + month + " " + year;
		
		return fecha;
	}
	
	public static String getDiasRestantes(String fecha){
		String restantes = "";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		try {
			date = simpleDateFormat.parse(fecha.replace(" ", "-"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = GregorianCalendar.getInstance();
		long tiempo = date.getTime() - calendar.getTime().getTime();
		long dias = TimeUnit.MILLISECONDS.toDays(tiempo);	
		
		if(dias == 1)		
			restantes = "Queda //" + String.valueOf(dias) + " d�a// para ";
		else if(dias == 0)
			restantes = "Hoy es el //d�a// para " ;
		else if(dias > 1)
			restantes = "Quedan //" + String.valueOf(dias) + " d�as// para ";
		else 
			restantes = "negativo";
		
//		Log.i("getDiasRestantes","getDiasRestantes: " + dias + " restantes " + restantes);
		
		return restantes;
	}
	
	public static String getFechaMenu(){
		
		String fecha = "";
		String year = "", month = "", day = "";

		Calendar c = Calendar.getInstance();
		day = String.valueOf(c.get(Calendar.DATE));
		month = String.valueOf(c.get(Calendar.MONTH));
		year = String.valueOf(c.get(Calendar.YEAR));

		if(Integer.parseInt(day) < 10) day = "0" + day;
//		if(Integer.parseInt(month) < 10) month = "0" + month;		
		
		 if (Integer.parseInt(month) == 0)
		  {
			 month = "Jan ";
		  }
		  else if (Integer.parseInt(month) == 1)
		  {
			 month = "Feb ";
		  }
		  else if (Integer.parseInt(month) == 2)
		  {
		   month = "Mar ";
		  }
		  else if (Integer.parseInt(month) == 3)
		  {
		   month = "Apr ";
		  }
		  else if (Integer.parseInt(month) == 4)
		  {
		   month = "May ";
		  }
		  else if (Integer.parseInt(month) == 5)
		  {
		   month = "Jun ";
		  }
		  else if (Integer.parseInt(month) == 6)
		  {
		   month = "Jul ";
		  }
		  else if (Integer.parseInt(month) == 7)
		  {
		   month = "Aug ";
		  }
		  else if (Integer.parseInt(month) == 8)
		  {
		   month = "Sep ";
		  }
		  else if (Integer.parseInt(month) == 9)
		  {
		   month = "Oct ";
		  }
		  else if (Integer.parseInt(month) == 10)
		  {
		   month = "Mov ";
		  }
		  else if (Integer.parseInt(month) == 11)
		  {
		   month = "Dec ";
		  }

		 fecha = month + ". " + day + ", " + year;
		 
		return fecha;
	}
	
	public String getDate(int weekday, int month, int day)
	 {
	  String fecha = "";
	  
	  if (weekday == 1)
	  {
	   fecha = fecha + "Sun, ";
	  }
	  else if (weekday == 2)
	  {
	   fecha = fecha + "Mon, ";
	  }
	  else if (weekday == 3)
	  {
	   fecha = fecha + "Tue, ";
	  }
	  else if (weekday == 4)
	  {
	   fecha = fecha + "Wed, ";
	  }
	  else if (weekday == 5)
	  {
	   fecha = fecha + "Thu, ";
	  }
	  else if (weekday == 6)
	  {
	   fecha = fecha + "Fri, ";
	  }
	  else if (weekday == 7)
	  {
	   fecha = fecha + "Sat, ";
	  }
	  
	  if (month == 1)
	  {
	   fecha = fecha + "Jan ";
	  }
	  else if (month == 2)
	  {
	   fecha = fecha + "Feb ";
	  }
	  else if (month == 3)
	  {
	   fecha = fecha + "Mar ";
	  }
	  else if (month == 4)
	  {
	   fecha = fecha + "Apr ";
	  }
	  else if (month == 5)
	  {
	   fecha = fecha + "May ";
	  }
	  else if (month == 6)
	  {
	   fecha = fecha + "Jun ";
	  }
	  else if (month == 7)
	  {
	   fecha = fecha + "Jul ";
	  }
	  else if (month == 8)
	  {
	   fecha = fecha + "Aug ";
	  }
	  else if (month == 9)
	  {
	   fecha = fecha + "Sep ";
	  }
	  else if (month == 10)
	  {
	   fecha = fecha + "Oct ";
	  }
	  else if (month == 11)
	  {
	   fecha = fecha + "Mov ";
	  }
	  else if (month == 12)
	  {
	   fecha = fecha + "Dec ";
	  }
	  
	  fecha = fecha + day;
	  
	  return fecha;
	  
	 }
	
	public static String parseToNumber(String mes){
		
		if (mes.equals("Ene"))
			mes = "1";
		else if (mes.equals("Feb"))
			mes = "2";
		else if (mes.equals("Mar"))
			mes = "3";
		else if (mes.equals("Abr"))
			mes = "4";
		else if (mes.equals("May"))
			mes = "5";
		else if (mes.equals("Jun"))
			mes = "6";
		else if (mes.equals("Jul"))
			mes = "7";
		else if (mes.equals("Ago"))
			mes = "8";
		else if (mes.equals("Sep"))
			mes = "9";
		else if (mes.equals("Oct"))
			mes = "10";
		else if (mes.equals("Nov"))
			mes = "11";
		else if (mes.equals("Dic"))
			mes = "12";
		
		return mes;
	}
	
	public static boolean compareWithCurrentDate(String fecha){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		try {
			String[] split = fecha.split(" ");
			date = simpleDateFormat.parse(split[0] + "-" + Fecha.parseToNumber(split[1]) + "-" + split[2]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar calendar = GregorianCalendar.getInstance();
		long tiempo = date.getTime() - calendar.getTime().getTime();
		
		if(tiempo > 0)
			return true;
		
		return false;
	}
	
	public static boolean compareTwoDates(String fecha1, String fecha2){
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date1 = new Date();
		Date date2 = new Date();
		try {
			String[] split1 = fecha1.split(" ");
			String[] split2 = fecha2.split(" ");
			date1 = simpleDateFormat.parse(split1[0] + "-" + Fecha.parseToNumber(split1[1]) + "-" + split1[2]);
			date2 = simpleDateFormat.parse(split2[0] + "-" + Fecha.parseToNumber(split2[1]) + "-" + split2[2]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long tiempo = date1.getTime() - date2.getTime();
		
		if(tiempo > 0)
			return true;
		
		return false;
	}

}
