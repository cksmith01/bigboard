package gov.utleg.bigboard.util;

public class Strings {

    public static boolean notBlank(String s) {
        if (s == null) return false;
        if (s.trim().equals("")) return false;
        return true;
    }
    
    public static boolean isBlank(String s) {
        return (s == null || s.trim().equals("")) ? true:false;
    }
    
    public static boolean isNull(String s) {
        return (s == null) ? true:false;
    }
    
    public static boolean isEqual(String s1, String s2) {
        return (s1 == null && s2 == null) || (s1 != null && s2 != null && (s1.equals(s2)));
    }
    
    public static String safeTrim(String val) {
    	if (val != null)
    		return val.trim();
    	return val;
    }
    
    /**
     * Safe replace (checks for blank/null string before performing operation
     * @param str
     * @param oldChar
     * @param newChar
     * @return
     */
    public static String replace(String str, char oldChar, char newChar) {
    	if (Strings.notBlank(str)) {
    		return str.replace(oldChar, newChar);
    	}
    	return str;
    }
    
    public static String remove(String str, String theChar) {
    	if (Strings.notBlank(str)) {
    		return str.replace(theChar, "");
    	}
    	return str;
    }
    
    /**
     * Safe replace (checks for blank/null string before performing operation
     * @param str
     * @param oldStr
     * @param newStr
     * @return
     */
    public static String replace(String str, String oldStr, String newStr) {
    	if (Strings.notBlank(str)) {
    		return str.replaceAll(oldStr, newStr);
    	}
    	return str;
    }
    
    public static void out(Object str) {
    	System.out.println(str+"");
    }
    
    public static String check(String val, String _default) {
    	if (val == null || "".equals(val)) {
    		return _default;
    	}
    	return val;
    }
    
    public static String stripTags(String content) {
		return content.replaceAll("<[^>]*>", " ");
	}    
    
    public static void main(String[] args) {
    	System.out.println(Strings.replace("this`s is some funky`s shizzz`..", '`', '\'')); //CKS: Feb 19, 2020
	}
}
