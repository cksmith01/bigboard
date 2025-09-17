package gov.utleg.bigboard.util;

public class HtmlBuilder {
	
	private StringBuilder html = new StringBuilder();
	
	public HtmlBuilder add(String item) {
		html.append(item+"\n");
		return this;
	}
	
	public HtmlBuilder div(String item) {
		add(Html.div(item));
		return this;
	}
	
	public HtmlBuilder span(String item) {
		add(Html.span(item));
		return this;
	}
	
	public HtmlBuilder h1(String item) {
		add(Html.h1(item));
		return this;
	}
	
	public HtmlBuilder h2(String item) {
		add(Html.h2(item));
		return this;
	}
	
	public HtmlBuilder h3(String item) {
		add(Html.h3(item));
		return this;
	}
	
	public HtmlBuilder h4(String item) {
		add(Html.h4(item));
		return this;
	}
	
	public HtmlBuilder b(String item) {
		add(Html.b(item));
		return this;
	}
	
	public HtmlBuilder i(String item) {
		add(Html.i(item));
		return this;
	}
	
	public HtmlBuilder p(String item) {
		add(Html.p(item));
		return this;
	}
	
	public HtmlBuilder pre(String item) {
		add(Html.pre(item));
		return this;
	}
	
	public HtmlBuilder ul() {
		add("<ul>");
		return this;
	}
	
	public HtmlBuilder ulEnd() {
		add("</ul>");
		return this;
	}
	
	public HtmlBuilder ol() {
		add("<ol>");
		return this;
	}
	
	public HtmlBuilder olEnd() {
		add("</ol>");
		return this;
	}
	
	public HtmlBuilder li(String item) {
		add(Html.li(item));
		return this;
	}
	
	public HtmlBuilder a(String url, String text) {
        add(Html.a(url, text));
        return this;
    }
	
	public String getHtml() {
		return Html.html("\n"+html.toString());
	}

}
