package net.qhhhq.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONUtils {
	/**
     * 格式化json
     * @param content
     * @return
     */
    public static String formatJson(String content) {

        StringBuffer sb = new StringBuffer();
        int index = 0;
        int count = 0;
        while(index < content.length()){
            char ch = content.charAt(index);
            if(ch == '{' || ch == '['){
                sb.append(ch);
                sb.append('\n');
                count++;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            }
            else if(ch == '}' || ch == ']'){
                sb.append('\n');
                count--;
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
                sb.append(ch);
            }
            else if(ch == ','){
                sb.append(ch);
                sb.append('\n');
                for (int i = 0; i < count; i++) {
                    sb.append('\t');
                }
            }
            else {
                sb.append(ch);
            }
            index ++;
        }
        return sb.toString();
    }
    /**
     * 把格式化的json紧凑
     * @param content
     * @return
     */
    public static String compactJson(String content) {
        String regEx="[\t\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }
}
