package jmns.app.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.tuple.Pair;



public class TestMongodb{
	
	 
	 public static void main(String arg[]){
		 
		 String us = "a6e2ce5a-702a-48d8-9d9e-ac7190260686";
		 
		System.out.println("Pass "+Base64.decodeBase64("rO0ABXQAE2NjeGNtdXNlcjpjY3hjbXBhc3M="));
		 
		System.out.println("us =  "+Base64.encodeBase64String(us.getBytes()));

		
		
		 /*String nameOne = "Sri";
			String name = "Murugan";
			String reportName=null;
			reportName=nameOne+"_"+name; 
			
			if(reportName.length()==1){
				reportName="EvaluationReport";
			}
			
			System.out.println("reportName length = "+reportName.length());
			System.out.println("reportName = "+reportName);
			*/
			
		/* String json = null;
		  String sportsName = "Football";
	        List<String> list = new ArrayList<String>();
	        
	        if (null!=sportsName && sportsName.equals("Football")) {
	                list.add("Lionel Messi");
	                list.add("Cristiano Ronaldo");
	                list.add("David Beckham");
	                list.add("Diego Maradona");
	        } else if (null!=sportsName && sportsName.equals("Cricket")) {
	                list.add("Sourav Ganguly");
	                list.add("Sachin Tendulkar");
	                list.add("Lance Klusener");
	                list.add("Michael Bevan");
	        } else if (null!=sportsName && sportsName.equals("Select Sports")) {
	                list.add("Select Player");
	        }else{
	        	list.add("Murugan");
	        }*/

	 /*     String aa = Base64.decodeBase64("rO0ABXQAJDI2MGQ4MzAyLWQ3NWEtNGJmYi1hZDk3LTJlMjRkYWNiZjEzMQ==");
	        
	        
	        json = new Gson().toJson(list);
	        
	        System.out.println("json ======>"+json);
	        */
		 
	//	 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		 
			//String dateInString = "7-Jun-2013";

		 /*String dateInString = "09/07/2013";
			try {

				Date date = formatter.parse(dateInString);
				System.out.println(date);
				System.out.println(formatter.format(date));

			} catch (ParseException e) {
				e.printStackTrace();
			}*/
		 
		 
			/* GregorianCalendar gc = new GregorianCalendar(2015, 2-1, 30);
			    java.util.Date monthEndDate = new java.util.Date(gc.getTime().getTime());
			    System.out.println(monthEndDate);
			    
			    TestMongodb tm = new TestMongodb();
			    Pair<Date, Date> aa = tm.getDateRange();
			    
			    System.out.println("Return begining" +aa.getLeft());
			    System.out.println("Return end" +aa.getRight());*/
			    
		 
		/* MongoCollection<Document> acCollection;
		 MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost"));
	     MongoDatabase blogDatabase = mongoClient.getDatabase("murugan");
		 acCollection = blogDatabase.getCollection("my_task");
		 
		 Iterable<Document> accountList = acCollection.find();
		 
		 for (Document document : accountList) {*/
			 
			/* System.out.println("DB Date = "+document.get("date").toString());
			 
			 try {				
				Date date = formatter.parse(document.get("date").toString());
				System.out.println("Dte Chg = "+date);				
				acCollection.updateOne(new Document("_id", document.get("_id").toString()),
				        new Document("$set", new Document("date", date)));				
			} catch (ParseException e) {				
				e.printStackTrace();
			}*/
			 
			/* Date date = document.getDate("date");
			 
			 System.out.println("DB Date = "+date);
			 
			 String closeDateStr = formatter.format(date); 					 
			  System.out.println("Chage Format date = " + closeDateStr);
			 
		 }*/
		 
		 
		 
		/* Mongo DB Query to Bson format
		 * 
		 * db.my_account.aggregate([ { 
			    $group: { 
			        _id: null, 
			        total: { 
			            $sum: "$amount" 
			        } 
			    } 
			} ] )*/
			
		/* List<Document> pipeline = new ArrayList<Document>();	 
		 pipeline.add(new Document("$match",new Document("userId", "f3ba13c6-ac78-4919-8520-4b4c5e9229f0").append("accountType", "Debit")));
	     pipeline.add(new Document("$group",new Document("_id", null).append("total", new Document("$sum", "$amount"))));
	     
	     
	     AggregateIterable<Document> doc =  acCollection.aggregate(pipeline);	       
	     System.out.println("doc==============>"+doc.first());
	     System.out.println("doc==============>"+doc.first().getLong("total"));*/
	 }
	 
	 
	 
	 public Pair<Date, Date> getDateRange() {
		    Date begining, end;

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_MONTH,
		                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		        setTimeToBeginningOfDay(calendar);
		        begining = calendar.getTime();
		    }

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_MONTH,
		                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		        setTimeToEndofDay(calendar);
		        end = calendar.getTime();
		    }

		    System.out.println("begining" +begining);
		    System.out.println("end" +end);
		    
		    return Pair.of(begining, end);
		}

		private static Calendar getCalendarForNow() {
		    Calendar calendar = GregorianCalendar.getInstance();
		    calendar.setTime(new Date());
		    return calendar;
		}

		private static void setTimeToBeginningOfDay(Calendar calendar) {
		    calendar.set(Calendar.HOUR_OF_DAY, 0);
		    calendar.set(Calendar.MINUTE, 0);
		    calendar.set(Calendar.SECOND, 0);
		    calendar.set(Calendar.MILLISECOND, 0);
		}
		
		private static void setTimeToEndofDay(Calendar calendar) {
		    calendar.set(Calendar.HOUR_OF_DAY, 23);
		    calendar.set(Calendar.MINUTE, 59);
		    calendar.set(Calendar.SECOND, 59);
		    calendar.set(Calendar.MILLISECOND, 999);
		}

}