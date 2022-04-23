package cn.stopyc.bean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.regex.Pattern;

/**
 * @program: qg-engineering-management-system
 * @description: Xss过滤器
 * @author: stop.yc
 * @create: 2022-04-23 21:19
 **/
public class XssHttpServletRequestWrapperFilter extends HttpServletRequestWrapper {

    HttpServletRequest orgRequest = null;
    public XssHttpServletRequestWrapperFilter(HttpServletRequest request) {
        super(request);
        orgRequest = request;
    }
    @Override
    public String getParameter(String name) {
        String value = super.getParameter(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
            value = HTMLEncode(value);
        }
        return value;
    }

    /**
     * spring是使用的getParameterValues方法
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] value = super.getParameterValues(name);
        if (value == null) {
            return null;
        }
        for (int i = 0;i <value.length;i++) {
            value[i] = xssEncode(value[i]);
            value[i] = HTMLEncode(value[i]);
        }
        return value;
    }

    /**
     * 对一些特殊字符进行转义
     *
     *
     */
    public static String HTMLEncode(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("＜");
            } else if (character == '>') {
                result.append("＞");
            } else if (character == '\"') {
                result.append("｀");
            } else {
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做xss过滤。 如果需要获得原始的值，则通过super.getHeaders(name)来获取
     * getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {

        String value = super.getHeader(xssEncode(name));
        if (value != null) {
            value = xssEncode(value);
        }
        return value;
    }

    /**
     * 将容易引起xss漏洞的半角字符直接替换成全角字符
     * 目前xssProject对注入代码要求是必须开始标签和结束标签(如&lt;script&gt;&lt;/script&gt;)正确匹配才能解析，否则报错；因此只能替换调xssProject换为自定义实现
     *
     * @param s
     * @return
     */
    private static String xssEncode(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        String result = stripXSS(s);
        if (null != result) {
            result = escape(result);
        }

        return result;

    }

    public static String escape(String s) {
        StringBuilder sb = new StringBuilder(s.length() + 16);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '>':
                    sb.append('＞');// 全角大于号
                    break;
                case '<':
                    sb.append('＜');// 全角小于号
                    break;
                case '\'':
                    sb.append('‘');// 全角单引号
                    break;
                case '\"':
                    sb.append('“');// 全角双引号
                    break;
                case '\\':
                    sb.append('＼');// 全角斜线
                    break;
                case '%':
                    sb.append('％'); // 全角冒号
                    break;
                default:
                    sb.append(c);
                    break;
            }

        }
        return sb.toString();
    }

    private static String stripXSS(String value) {
        if (value != null) {
            // Avoid null characters
            value = value.replaceAll("", "");
            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("&lt;script&gt;(.*?)&lt;/script&gt;", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome &lt;/script&gt; tag
            scriptPattern = Pattern.compile("&lt;/script&gt;", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome &lt;script ...&gt; tag
            scriptPattern = Pattern.compile("&lt;script(.*?)&gt;",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid οnlοad= expressions
            scriptPattern = Pattern.compile("onload(.*?)=",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("&lt;iframe&gt;(.*?)&lt;/iframe&gt;", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("&lt;/iframe&gt;", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Remove any lonesome &lt;script ...&gt; tag
            scriptPattern = Pattern.compile("&lt;iframe(.*?)&gt;",
                    Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return value;
    }

    /**
     * 获取最原始的request
     *
     * @return
     */
    public HttpServletRequest getOrgRequest() {
        return orgRequest;
    }

    /**
     * 获取最原始的request的静态方法
     *
     * @return
     */
    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
        if (req instanceof XssHttpServletRequestWrapperFilter) {
            return ((XssHttpServletRequestWrapperFilter) req).getOrgRequest();
        }
        return req;
    }

}
