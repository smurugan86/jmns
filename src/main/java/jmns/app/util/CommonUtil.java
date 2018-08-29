package jmns.app.util;

import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jmns.app.domain.RateVO;
import jmns.app.domain.UserVO;

import org.apache.commons.lang3.tuple.Pair;
import org.bson.Document;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.ui.Model;

import sun.misc.BASE64Encoder;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class CommonUtil{
	
	 // helper function to get session cookie as string
	  public static String getSessionCookie(final HttpServletRequest request,String cookieName) {
        if (request.getCookies() == null) {
        	//System.out.println("getSessionCookie is Empty @ services Side");
            return null;
        }
        //System.out.println("getSessionCookie is ");
        for (Cookie cookie : request.getCookies()) {
        	//System.out.println(cookie.getName()+" "+cookie.getValue());
            if (cookie.getName().equals(cookieName)) {
                try {
					return URLDecoder.decode(cookie.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
        return null;
    }
	  
	  public static Date stringToDate(String dateString){
		  SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		  try {
				Date date = formatter.parse(dateString);
				/*System.out.println(date);
				System.out.println(formatter.format(date));*/
				return date;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		return null;		  
	  }
    
	  public static String dateToString(Date date){		  
		  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");					
		  String closeDateStr = sdf.format(date); 					 
		  /*System.out.println(closeDateStr);*/
		  return closeDateStr;			  
	  }
	  
	  
	  
	  public static Pair<Date, Date> getCurrentMonthDateRange() {
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
		    return Pair.of(begining, end);
		}
	  
	  public static void main(String args[]){
		  CommonUtil com = new CommonUtil();
		  Pair<Date, Date>  dd = com.getCurrentWeekDateRange();
		  System.out.println("Week start"+dd.getLeft());
		  System.out.println("Week end"+dd.getRight());
		  
		 
		  Pair<Date, Date>  mm = com.getCurrentMonthDateRange();
		  System.out.println("Month start"+mm.getLeft());
		  System.out.println("Month end"+mm.getRight());
		  
		  Pair<Date, Date>  yy = com.getCurrentYearDateRange();
		  System.out.println("Year start"+yy.getLeft());
		  System.out.println("Year end"+yy.getRight());
		  
	  }

	  
	  public static Pair<Date, Date> getCurrentYearDateRange() {
		    Date begining, end;

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_YEAR,
		                calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		        setTimeToBeginningOfDay(calendar);
		        begining = calendar.getTime();
		    }

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_YEAR,
		                calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		        setTimeToEndofDay(calendar);
		        end = calendar.getTime();
		    }		    
		    return Pair.of(begining, end);
		}
	  
	  public static Pair<Date, Date> getCurrentWeekDateRange() {
		    Date begining, end;

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_WEEK,
		                calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		        setTimeToBeginningOfDay(calendar);
		        begining = calendar.getTime();
		    }

		    {
		        Calendar calendar = getCalendarForNow();
		        calendar.set(Calendar.DAY_OF_WEEK,
		                calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		        setTimeToEndofDay(calendar);
		        end = calendar.getTime();
		    }		    
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
		
	  
		public static String convertLongToString(long spendDollars) {			
				DecimalFormat formatter = new DecimalFormat("Rs ###,###,###.00");
				return formatter.format(spendDollars);			
		}

		public static boolean taskValidation(Model model, String date,
				String title, String userStory, String description) {
			// TODO Auto-generated method stub
			boolean ischeck = true;
	        
			 if(StringUtil.isBlank(date)){
				 model.addAttribute("edate", "Date Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(title)){
				 model.addAttribute("etitle", "Title Required");
				 ischeck =  false;
			 }
			 if(StringUtil.isBlank(userStory)){
				 model.addAttribute("euserStory", "UserStory Required");
				 ischeck =  false;
			 }
			if(StringUtil.isBlank(description)){
				model.addAttribute("edescription", "Description Required");
				ischeck =  false;
			 }
			
			return ischeck;
		}

		public static boolean noteValidation(Model model, String date,
				String title, String description) {
			boolean ischeck = true;
			
			 if(StringUtil.isBlank(date)){
				 model.addAttribute("edate", "Date Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(title)){
				 model.addAttribute("etitle", "Title Required");
				 ischeck =  false;
			 }			 
			if(StringUtil.isBlank(description)){
				model.addAttribute("edescription", "Description Required");
				ischeck =  false;
			 }
			
			return ischeck;
		}

		public static boolean accountValidation(Model model, String date,
				String accountType, String categoryName, String description,
				long amount) {
				
			boolean ischeck = true;
			
			 if(StringUtil.isBlank(date)){
				 model.addAttribute("edate", "Date Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(accountType)){
				 model.addAttribute("eaccountType", "Account Type Required");
				 ischeck =  false;
			 }
			 if(StringUtil.isBlank(categoryName)){
				 model.addAttribute("ecategoryName", "Category Required");
				 ischeck =  false;
			 }
			if(StringUtil.isBlank(description)){
				model.addAttribute("edescription", "Description Required");
				ischeck =  false;
			 }
			
			if(amount==0){
				model.addAttribute("eamount", "Amount Required");
				ischeck =  false;
			}
			return ischeck;
		}

		public static boolean categoryValidation(Model model,
				String categoryType, String categoryName) {
			
			boolean ischeck = true;
							
			 if(StringUtil.isBlank(categoryType)){
				 model.addAttribute("ecategoryType", "Category Type Required");
				 ischeck =  false;
			 }
			 if(StringUtil.isBlank(categoryName)){
				 model.addAttribute("ecategoryName", "Category Required");
				 ischeck =  false;
			 }
			
			return ischeck;
		}

		public static boolean userValidation(Model model, UserVO userVO,boolean isUpdate) {
			
			boolean ischeck = true;
	        
			 if(StringUtil.isBlank(userVO.getFirstName())){
				 model.addAttribute("efirstName", "FirstName Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(userVO.getLastName())){
				 model.addAttribute("elastName", "LastName Required");
				 ischeck =  false;
			 }
			 if(!isUpdate && StringUtil.isBlank(userVO.getEmail())){
				 model.addAttribute("eemail", "Email Required");
				 ischeck =  false;
			 }
			if(!isUpdate && StringUtil.isBlank(userVO.getPassword())){
				model.addAttribute("epassword", "Password Required");
				ischeck =  false;
			 }
			
			if(!isUpdate && StringUtil.isBlank(userVO.getVerify())){
				model.addAttribute("everify", "Verify Password Required");
				ischeck =  false;
			 }
			
			if(!isUpdate && !userVO.getPassword().equals(userVO.getVerify())){
				model.addAttribute("epassword", "Password & Verify Password is miss matched");
				ischeck =  false;
			}
			
			return ischeck;
		}

		public static boolean postValidation(Model model, String tags,
				String title, String post) {
			boolean ischeck = true;
			
			 if(StringUtil.isBlank(tags)){
				 model.addAttribute("etags", "Tag Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(title)){
				 model.addAttribute("etitle", "Title Required");
				 ischeck =  false;
			 }			 
			if(StringUtil.isBlank(post)){
				model.addAttribute("epost", "Post Required");
				ischeck =  false;
			 }
			
			return ischeck;
		}
		
		
		public static List<String> extractTags(String tags) {

		        // probably more efficent ways to do this.
		        //
		        // whitespace = re.compile('\s')
		        tags = tags.replaceAll("\\s", "");
		        String tagArray[] = tags.split(",");

		        // let's clean it up, removing the empty string and removing dups
		        List<String> cleaned = new ArrayList<String>();
		        for (String tag : tagArray) {
		            if (!tag.equals("") && !cleaned.contains(tag)) {
		                cleaned.add(tag);
		            }
		        }

		        return cleaned;
		    }
		 
		
		public static String makePasswordHash(String password, String salt) {
	        try {
	            String saltedAndHashed = password + "," + salt;
	            MessageDigest digest = MessageDigest.getInstance("MD5");
	            digest.update(saltedAndHashed.getBytes());
	            BASE64Encoder encoder = new BASE64Encoder();
	            byte hashedBytes[] = (new String(digest.digest(), "UTF-8")).getBytes();
	            return encoder.encode(hashedBytes) + "," + salt;
	        } catch (NoSuchAlgorithmException e) {
	            throw new RuntimeException("MD5 is not available", e);
	        } catch (UnsupportedEncodingException e) {
	            throw new RuntimeException("UTF-8 unavailable?  Not a chance", e);
	        }
	    }
		
		public static Random getRandom() {
			final ThreadLocal<Random> random = new ThreadLocal<Random>();
	        Random result = random.get();
	        if (result == null) {
	            result = new Random();
	            random.set(result);
	        }
	        return result;
	    }

		public static MongoCollection<Document> getMongoDBConnection(
				String collectionName) {			
			 MongoClient mongoClient = new MongoClient(new MongoClientURI(Constants.DB_URL));
		     MongoDatabase myDatabase = mongoClient.getDatabase(Constants.DB_NAME);
		     MongoCollection<Document>  userCollection = myDatabase.getCollection(collectionName);			 
			return userCollection;
		}

		public static Document convertRequestToUserVO(
				UserVO userVO) {			
			Document user = userVO.UserVOToDoc(userVO);
			return user;
		}

		public static boolean rateValidation(Model model, RateVO rateVO) {			
			boolean ischeck = true;	        
			 if(StringUtil.isBlank(rateVO.getQuantity())){
				 model.addAttribute("equantity", "Quantity Required");
				 ischeck =  false;
			 }			 
			 if(StringUtil.isBlank(rateVO.getProductName())){
				 model.addAttribute("eproductName", "ProductName Required");
				 ischeck =  false;
			 }			 
			 if(rateVO.getAmount()==0){
				model.addAttribute("eamount", "Amount Required");
				ischeck =  false;
			 }			 
			return ischeck;
		}

		
}