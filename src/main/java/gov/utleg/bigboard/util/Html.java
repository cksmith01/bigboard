package gov.utleg.bigboard.util;

import org.springframework.stereotype.Component;

@Component
public class Html {
	
	public static final String BR = "<br />";
	
    public static String tag(String tag, String content) {
        return "<"+tag+">"+content+"</"+tag+">";
    }

    public static String ul(String content) {
        return tag("ul", content);
    }
    
    public static String li(String content) {
        return tag("li", content);
    }

    public static String h1(String content) {
        return tag("h1", content);
    }

    public static String h2(String content) {
        return tag("h2", content);
    }

    public static String h3(String content) {
        return tag("h3", content);
    }
    
    public static String h4(String content) {
        return tag("h4", content);
    }
    
    public static String html(String content) {
        return tag("html", content);
    }
    
    public static String b(String content) {
        return tag("b", content);
    }
    
    public static String p(String content) {
        return tag("p", content);
    }
    
    public static String i(String content) {
        return tag("i", content);
    }
    
    public static String pre(String content) {
        return tag("pre", content);
    }

    public static String div(String content) {
        return tag("div", content);
    }

    public static String span(String content) {
        return tag("span", content);
    }
    
    public static String a(String url, String text) {
        return "<a href='"+url+"'>"+ text+"</a>";
    }
}
