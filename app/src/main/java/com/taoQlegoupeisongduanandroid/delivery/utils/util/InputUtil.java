package com.taoQlegoupeisongduanandroid.delivery.utils.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class InputUtil {

	/**
	 * 只允许输入汉字
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
     */
	public static String stringFilter(String str)throws PatternSyntaxException {
		// 只允许字母和数字    [^a-zA-Z0-9]
		String   regEx  =  "[^\\u4E00-\\u9FFF]{0,10}";
		Pattern p   =   Pattern.compile(regEx);
		Matcher m   =   p.matcher(str);
		String result = m.replaceAll("").trim();
		return   result.length()<=10?result:result.substring(0,10);
	}

	/**
	 * 身份证只允许输入数字和x
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilterIdCard(String str)throws PatternSyntaxException {
		// 只允许字母和数字    [^a-zA-Z0-9]
		String   regEx  =  "[^X0-9]";
		Pattern p   =   Pattern.compile(regEx);
		Matcher m   =   p.matcher(str);
		String result = m.replaceAll("").trim();
		if(result.length()>18){
			result = result.substring(0,18);
		}
		return   result;
	}


	/**
	 * 只允许输入汉字字母和数字
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
     */
	public static String stringFilter2(String str)throws PatternSyntaxException {
		// 只允许字母和数字    [^a-zA-Z0-9]
		String   regEx  =  "[^a-zA-Z0-9\\u4E00-\\u9FFF\\-]";
		Pattern p   =   Pattern.compile(regEx);
		Matcher m   =   p.matcher(str);
		return   m.replaceAll("").trim();
	}

	/**
	 * 只允许输入数字和小数点
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String pointFilter(String str)throws PatternSyntaxException {
		// 只允许字母和数字    [^a-zA-Z0-9]
		String   regEx  =  "[^0-9\\.]";
		Pattern p   =   Pattern.compile(regEx);
		Matcher m   =   p.matcher(str);
		String result = m.replaceAll("").trim();
		if(result.length()>10){
			result = result.substring(0,10);
		}
		return   result;
	}

	/**
	* 只允许输入数字和小数点,小数点后只能有两位
	* @param str
	* @return
			* @throws PatternSyntaxException
	*/
	public static String pointFilterDouble(String str)throws PatternSyntaxException {
		// 只允许字母和数字    [^a-zA-Z0-9]
		String   regEx  =  "[^0-9\\.]";
		Pattern p   =   Pattern.compile(regEx);
		Matcher m   =   p.matcher(str);
		String result = m.replaceAll("").trim();
		int index = result.indexOf(".");
		if(index != -1 && index != result.length()){
			String prs = result.substring(index+1,result.length());
			if(prs.length()>2){
				result = result.substring(0,result.length()-1);
			}
		}
		return   result;
	}
}
