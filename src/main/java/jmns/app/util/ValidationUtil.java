package jmns.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class to validate the given field.
 *
 * @author Selva & Parsu
 *
 */
public class ValidationUtil {

	//public static final String VALID_EMAIL_REGEXP = "[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+(?:\\.[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+)*@([a-zA-Z0-9](?:[-a-zA-Z0-9]*[a-zA-Z0-9] {2,6})?(?:\\.[a-zA-Z0-9](?:[-a-zA-Z0-9]*[a-zA-Z0-9])?)*(?:\\.[a-zA-Z0-9]{2,}+)|\\[(?:\\d{1,3}(?:\\.\\d{1,3}){3}|IPv6:[0-9A-Fa-f:]{4,39})\\])";
    //Correct One   public static final String VALID_EMAIL_REGEXP = "[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+(?:\\.[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+)*@([a-zA-Z0-9]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?)*(?:\\.[a-zA-Z0-9]{2,}+)|\\[(?:\\d{1,3}(?:\\.\\d{1,3}){3}|IPv6:[0-9A-Fa-f:]{4,39})\\])";
    //REmoved numbers after @ 
   //  working code  public static final String VALID_EMAIL_REGEXP = "[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+(?:\\.[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+)*@([a-zA-Z]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?(?:\\.[a-zA-Z]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?)*(?:\\.[a-zA-Z0-9]{2,}+)|\\[(?:\\d{1,3}(?:\\.\\d{1,3}){3}|IPv6:[0-9A-Fa-f:]{4,39})\\])";
// Removed from VALID_EMAIL_REGEXP chars {,} 
	  //public static final String VALID_EMAIL_REGEXP = "[-a-zA-Z0-9!#$%&'*+/=?^_'|~]+(?:\\.[-a-zA-Z0-9!#$%&'*+/=?^_'|~]+)*@([a-zA-Z]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?(?:\\.[a-zA-Z]{2,6}(?:[-a-zA-Z0-9]*[a-zA-Z0-9])?)*(?:\\.[a-zA-Z0-9]{2,}+)|\\[(?:\\d{1,3}(?:\\.\\d{1,3}){3}|IPv6:[0-9A-Fa-f:]{4,39})\\])";
		public static final String VALID_EMAIL_REGEXP = "[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+(?:\\.[-a-zA-Z0-9!#$%&'*+/=?^_'{|}~]+)*@([a-zA-Z0-9](?:[-a-zA-Z0-9]*[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[-a-zA-Z0-9]*[a-zA-Z0-9])?)*(?:\\.[a-zA-Z0-9]{2,}+)|\\[(?:\\d{1,3}(?:\\.\\d{1,3}){3}|IPv6:[0-9A-Fa-f:]{4,39})\\])";  
	/**
	 * Validate the name Field.
	 * validation rule : Allow one space, one apostrophe, one hyphen between alpha.
	 *
	 * @param text as String
	 * @return boolean
	 */
	public static boolean validateNameField(String text) {
		String alphaOnlyExp = "[a-zA-Z]+";

		if(!validateString(alphaOnlyExp, text.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""))){
			//the string may contain numbers,other special chars
			return false;
		}
		String endExp = "[a-zA-Z\\s-']+[\\s-']$";
		if(validateString(endExp, text)){
			//the string may end with space,hypen,quote
			return false;
		}

		String startExp = "[\\s-'][a-zA-Z\\s-']+$";
		if(validateString(startExp, text)){
			//the string may start with space,hypen,quote
			return false;
		}

		String[] tempDataArr = text.split(" ");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaHypApoExp = "[a-zA-Z-']+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaHypApoExp,tempData)){
						//the string may contain space or empty
						return false;
				}
			}
		}

		tempDataArr = text.split("-");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaSpaceApoExp = "[a-zA-Z\\s']+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaSpaceApoExp, tempData)){
						//the string may contain hypen or empty
						return false;
				}
			}
		}

		tempDataArr = text.split("'");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaSpaceHypExp = "[a-zA-Z\\s-]+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaSpaceHypExp, tempData)){
						//the string may contain quote or empty
						return false;
				}
			}
		}

		return true;
	}
	
	
	public static boolean validateNameforAlphanumeric(String text) {
		String alphaOnlyExp = "[a-zA-Z0-9]+";

		if(!validateString(alphaOnlyExp, text.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""))){
			//the string may contain numbers,other special chars
			return false;
		}
		String endExp = "[a-zA-Z\\s-']+[\\s-']$";
		if(validateString(endExp, text)){
			//the string may end with space,hypen,quote
			return false;
		}

		String startExp = "[\\s-'][a-zA-Z0-9\\s-']+$";
		if(validateString(startExp, text)){
			//the string may start with space,hypen,quote
			return false;
		}

		String[] tempDataArr = text.split(" ");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaHypApoExp = "[a-zA-Z0-9-']+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaHypApoExp,tempData)){
						//the string may contain space or empty
						return false;
				}
			}
		}

		tempDataArr = text.split("-");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaSpaceApoExp = "[a-zA-Z0-9\\s']+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaSpaceApoExp, tempData)){
						//the string may contain hypen or empty
						return false;
				}
			}
		}

		tempDataArr = text.split("'");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaSpaceHypExp = "[a-zA-Z0-9\\s-]+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaSpaceHypExp, tempData)){
						//the string may contain quote or empty
						return false;
				}
			}
		}

		return true;
	}

	private static boolean validateString(String exp,String text){
        Pattern pattern = Pattern.compile(exp);
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}
	
	
	public static boolean validatePhoneNumber(String phoneNumber){
		
		Boolean isvalid = Boolean.FALSE;
		
		String phone = "123-456-9999";

	    String phoneNumberPattern = "(\\d-)?(\\d{3}-)?\\d{3}-\\d{4}";

	    isvalid = phone.matches(phoneNumberPattern);
		
		return isvalid;
		
	}
	
	
	/**
	 * Validate the Double Field.
	 * validation rule : compare the given Double value with less than that of 9999999999999.00
	 * valid is 13 digit without decimal point 
	 *
	 * @param dollarValue
	 * @return boolean
	 */
	public static boolean validateDoubleValue(Double doubleValue){
		
		Boolean isvalid = Boolean.FALSE;
		//if(Double.compare(9999999999.99, doubleValue)!=-1)
		if(Double.compare(9999999999999.00, doubleValue)!=-1)
		{
			isvalid=Boolean.TRUE;
		}		
		return isvalid;
		
	}
	
	/**
	 * Validate the Integer Field.
	 * validation rule : compare the given Integer value with less than that of 9999
	 * valid is 4 digit without decimal point 
	 *
	 * @param level
	 * @return boolean
	 */
	public static boolean validateIntegerValue(Integer level){
		
		Boolean isvalid = Boolean.FALSE;
		
		
		//if(Double.compare(9999999999.99, doubleValue)!=-1)
		if(Integer.compare(999, level)!=-1  && level >= 0 )
		{
			isvalid=Boolean.TRUE;
		}		
		return isvalid;
		
	}	
	
	public static boolean validateIntegerValueForVCPaid(Integer level){
		
		Boolean isvalid = Boolean.FALSE;
		
		
		//if(Double.compare(9999999999.99, doubleValue)!=-1)
		if(Integer.compare(99999999, level)!=-1 )
		{
			isvalid=Boolean.TRUE;
		}		
		
		if(level <0 )
		{
			isvalid=Boolean.FALSE;
		}	
		
		if (String.valueOf(level).contains(".")){
			isvalid=Boolean.FALSE;
		}
		return isvalid;
		
	}	
	
public static boolean validateDoubleValueDynamicDigits(Double doubleValue,int Length){
		
		Boolean isvalid = Boolean.FALSE;
		//if(Double.compare(9999999999.99, doubleValue)!=-1)
		if (Length == 12){
			if(Double.compare(999999999.00, doubleValue)!=-1)
			{
				isvalid=Boolean.TRUE;
			}
		}	
		if (Length == 16){
			if(Double.compare(9999999999999.00, doubleValue)!=-1)
			{
				isvalid=Boolean.TRUE;
			}
		}
		return isvalid;
	}	
	
	
	
	public static boolean validateSpecialCharacter(String alphaNumericString)
	{
		Boolean isValid;
		//AlphaNumeric without any special characters
		Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
		Matcher matcher = pattern.matcher(alphaNumericString);
		isValid= matcher.find();
		return isValid;
		
	}
	
	
	
	/**
	 * Retrieving  actual number of days in a month for a particular year
	 * @param month
	 * @param year
	 * @return
	 */

	public static int getActualMaximumDays(int month, int year) {
		int date = 1;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}
	
	/**
	 * validation rule : compare the given Double value with less than that of 9999999999.99.
	 * 
	 * @param dateValue
	 * @return boolean
	 */
	public static boolean checkValidYear(Date dateValue) {
		Boolean isValid=Boolean.TRUE;
		
		if (dateValue!=null)
		{
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy");
			int year=Integer.parseInt(simpleDateFormat.format(dateValue));
			if (year<1900)
			{
				isValid=Boolean.FALSE;
			}
		}
		
		return isValid;
	}
	
	
	public static boolean validateNameforAlphanumericwithHyphensAndApostrophes(String text) {
		String alphaOnlyExp = "[a-zA-Z0-9\\-']+";

		if(!validateString(alphaOnlyExp, text.replaceAll(" ", "").replaceAll("-", "").replaceAll("'", ""))){
			//the string may contain numbers,other special chars
			return false;
		}
		String endExp = "[a-zA-Z\\s]+[\\s]$";
		if(validateString(endExp, text)){
			//the string may end with space,hypen,quote
			return false;
		}

		String startExp = "[\\s][a-zA-Z0-9\\s-']+$";
		if(validateString(startExp, text)){
			//the string may start with space,hypen,quote
			return false;
		}

		String[] tempDataArr = text.split(" ");
		if(tempDataArr != null && tempDataArr.length > 0){
			String alphaHypApoExp = "[a-zA-Z0-9-']+";
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				if(!validateString(alphaHypApoExp,tempData)){
						//the string may contain space or empty
						return false;
				}
			}
		}

		tempDataArr = text.split("-");
		if(tempDataArr != null && tempDataArr.length > 0){
			/*String alphaSpaceApoExp = "[a-zA-Z0-9']+";*/
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				/*if(!validateString(alphaSpaceApoExp, tempData)){
						//the string may contain hypen or empty
						return false;
				}*/
			}
		}

		tempDataArr = text.split("'");
		if(tempDataArr != null && tempDataArr.length > 0){
			/*String alphaSpaceHypExp = "[a-zA-Z0-9\\s-]+";*/
			for (int i = 0; i < tempDataArr.length; i++) {
				String tempData = tempDataArr[i];
				if(validateString(startExp,tempData)){
					//the string may start with space,hypen,quote
					return false;
				}
				/*if(!validateString(alphaSpaceHypExp, tempData)){
						//the string may contain quote or empty
						return false;
				}*/
			}
		}

		return true;
	}

	
	
	//Nagapriya edited this code for special chars <>,{},[]
	public static boolean validateforAlphanumericwithFewSpecialcharacter(String text) {
		//String alphaOnlyExp ="[a-zA-Z0-9\\s-*&()!@#$%^|\":;?/_+=.,'~`]+";
		String alphaOnlyExp ="[a-zA-Z0-9\\s-*&()!@#$%^|\":;?/_+=.,'~`\\\\]+";
	
		if(!validateString(alphaOnlyExp, text)){
			
			return false;
		}
		return true;
	}
	
	/*
	 * Added by Pounraj Manikandan on 26 Feb 2012 
	 * Replaces json structure characters
	 */
	public static String replaceJsonStructureCharacters(String jsonValue)
	{
		if(jsonValue != null && jsonValue.contains("&#x7b;")){
			jsonValue = jsonValue.replaceAll("&#x7b;", "{");
		}
		if(jsonValue != null && jsonValue.contains("&#x7d;")){
			jsonValue = jsonValue.replaceAll("&#x7d;", "}");
		}		
		if(jsonValue != null && jsonValue.contains("&#x22;")){
			jsonValue = jsonValue.replaceAll("&#x22;", "\"");
		}	
		return jsonValue;
	}
	
    /*
     *Added by SivamuruganKS on March 1 2013
     *Not Null Checker 
     */
	public static boolean validateField(String fieldValue){
		if(fieldValue == null || fieldValue.trim().equals("")){
		  return false;
		}
	return true;		
	}
	
	/*
     *Added by SivamuruganKS on March 1 2013
     *Unique Value Checker 
     */
	/**
	 * escapeSpecialCharacters, escapes any special characters in a string
	 * 
	 * @param stringToEscape
	 * @return stringToEscape
	 */
	public static String escapeSpecialCharacters(String stringToEscape) {
		
		// special characters to escape
		Pattern pattern = Pattern.compile("[$#^&*()+\\\\:;?./|]");
		Matcher matcher = pattern.matcher(stringToEscape);
		if (matcher.find()) {
			// escape special characters
			stringToEscape = matcher.replaceAll("\\\\$0");
		}
		return stringToEscape;
	}
	
	
	/**
	 * Validate the Level Field.
	 * validation rule : compare the given order value with less than that of 999
	 * valid is 3 digit without decimal point 
	 *
	 * @param level
	 * @return boolean
	 */
	public static boolean validateOrderValue(Integer level){
		
		Boolean isvalid = Boolean.FALSE;
		
		
		//if(Double.compare(9999999999.99, doubleValue)!=-1)
		if(Integer.compare(999, level)!=-1  && level > 0 )
		{
			isvalid=Boolean.TRUE;
		}		
		return isvalid;
		
	}


	
	public static void main(String a[]){
		//validateIntegerValueForVCPaid(99999999999);		
	}


	public static boolean fewSpecialCharacterValidation(String stringValue) {
		if (stringValue.contains("&#x7b;") || stringValue.contains("&#x7d;") || stringValue.contains("&#x5b;") ||
				stringValue.contains("&#x5d;") || stringValue.contains("&#x3c;") || stringValue.contains("&#x3e;")	) {
			return false;
				
		}		
		return true;
	}
	
    /*
     *Added by SivamuruganKS on December 26 2014
     *
     *Text-Length Should be below <= 2000, <=150
     *  
     */	
	public static boolean validateTextLength(String fieldValue,int fieldSize){
		
		if (fieldValue.length() <= fieldSize){
			
			return Boolean.TRUE;
		
		}else{ 
			return Boolean.FALSE;
		}
	}
}
