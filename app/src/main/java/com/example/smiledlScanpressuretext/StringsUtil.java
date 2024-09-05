package com.example.smiledlScanpressuretext;

/**
 * @ClassName: StringUtils
 * @Description:TODO String工具类
 * @author: lvwenyan
 * @date: Jul 1, 2015 2:40:58 PM
 */
public class StringsUtil {
	/**
	 * 判断str是否为空
	 * @Title: isNotNull   
	 * @Description: TODO 
	 * @param: @param str
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 * @author: lvwenyan     
	 * @date:   Jul 1, 2015 5:41:23 PM
	 */
	public static boolean isNotNull(String str) {
		boolean ret = false;
		if (null != str) {
			if (str.length() > 0) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * 判断str是否相等
	 * @Title: isStrEqual   
	 * @Description: TODO 
	 * @param: @param str1
	 * @param: @param str2
	 * @param: @return      
	 * @return: boolean      
	 * @throws   
	 * @author: lvwenyan     
	 * @date:   Jul 2, 2015 9:32:14 AM
	 */
	public static boolean isEqual(String str1, String str2) {
		boolean ret = false;
		if (isNotNull(str1) && isNotNull(str2)) {
			if (str1.equalsIgnoreCase(str2)) {
				ret = true;
			}
		}
		return ret;
	}

	/**
	 * 判断数据是否包含特定int元素
	 * Name isArrayContainInteger
	 * Function (todo)
	 * @param [in]
	 * @param [out]
	 * @return boolean
	 */
	public static boolean isArrayContainInteger(int[] arr, int targetValue) {
		for (int s : arr) {
			if (targetValue == s)
				return true;
		}
		return false;
	}

	/**
	 * String转Int数组
	 * @Title: stringToInts   
	 * @Description: TODO 
	 * @param: @param s
	 * @param: @return      
	 * @return: int[]      
	 * @throws   
	 * @author: lvwenyan     
	 * @date:   Oct 16, 2015 3:32:58 PM
	 */
	public static int[] stringToInts(String s) {
		if (isNotNull(s)) {
			s.trim();
			s.replace(" ", "");
			int length = s.length();
			int[] n = new int[length];
			for (int i = 0; i < length; i++) {
				n[i] = Integer.parseInt(s.substring(i, i + 1));
			}
			return n;
		}
		return null;
	}

	public static String addStars(String str, int start, int end) {
		String ret = null;
		if (start >= str.length() || start < 0) { return str; }
		if (end > str.length() || end < 0) { return str; }
		if (start >= end) { return str; }
		String starStr = "";
		for (int i = start; i < end; i++) {
			starStr = starStr + "*";
		}
		ret = str.substring(0, start) + starStr + str.substring(end, str.length());
		return ret;
	}
	
	/**
   * 判断字符串是否为空（去除空格）
   *
   * @param str
   * @return
   */
  public static boolean isEmptyWithBlank(String str) {
    return (null == str || str.trim().length() == 0);
  }

  /**
   * 判断字符串是否为空
   *
   * @param str
   * @return
   */
  public static boolean isEmpty(String str) {
    return (str == null || str.length() == 0);
  }

  
  /**
   * 获取字符串长度 区分中文
   *
   * @param value
   * @return
   */
  public static int getStringLength(String value) {
    int valueLength = 0;
    String chinese = "[\u4e00-\u9fa5|\u3002|\uff1f|\uff01|\uff0c|\u3001|\uff1b|\uff1a|\u201c|\u201d" +
      "|\u2018|\u2019|\uff08|\uff09|\u300a|\u300b|\u3008|\u3009|\u3010|\u3011|\u300e|\u300f|\u300c" +
      "|\u300d|\ufe43|\ufe44|\u3014|\u3015|\u2026|\u2014|\uff5e|\ufe4f|\uffe5]";
    for (int i = 0; i < value.length(); i++) {
      String temp = value.substring(i, i + 1);
      if(temp.matches(chinese)) {
        valueLength += 2;
      }
      else {
        valueLength += 1;
      }
    }
    return valueLength;
  }

	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
}
