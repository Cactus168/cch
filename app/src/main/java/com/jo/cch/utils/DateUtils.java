package com.jo.cch.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 将日期根据格式转换为字符串
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static String getDate(Date date, String format){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm:ss" : format);
        return dateFormat.format(date);
    }
    /**
     * 将字符串日期根据格式转换为日期类型
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Date getDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format == null ? "yyyy-MM-dd HH:mm:ss" : format);
        return dateFormat.parse(dateStr);
    }
    /**
     * 根据日期返回年份
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 根据日期字符串返回年份
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getYear(String dateStr) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取日期的前几年
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeYear(Date date, int beforeYear){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -beforeYear);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取日期字符串的前几年
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeYear(String dateStr, int beforeYear) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -beforeYear);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取日期的后几年
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterYear(Date date, int afterYear){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, afterYear);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 获取日期字符串的后几年
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterYear(String dateStr, int afterYear) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, afterYear);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 根据日期返回月份
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 根据日期字符串返回月份
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getMonth(String dateStr) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日期的前几月
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeMonth(Date date, int beforeMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -beforeMonth);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日期字符串的前几月
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeMonth(String dateStr, int beforeMonth) throws ParseException {
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -beforeMonth);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日期的后几月
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterMonth(Date date, int afterMonth){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, afterMonth);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 获取日期的后几月
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterMonth(String dateStr, int afterMonth) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, afterMonth);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 根据日期返回天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 根据日期字符串返回天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getDay(String dateStr) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 获取日期的前几天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeDay(Date date, int beforeDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -beforeDay);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 获取日期字符串的前几天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getBeforeDay(String dateStr, int beforeDay) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -beforeDay);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 获取日期的后几天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterDay(Date date, int afterDay){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, afterDay);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 获取日期字符串的后几天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getAfterDay(String dateStr, int afterDay) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, afterDay);
        return calendar.get(Calendar.DATE);
    }
    /**
     * 获取某个日期的最后一天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getLastDayOfMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取某个日期字符串的最后一天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getLastDayOfMonth(String dateStr) throws ParseException{
        Date date = getDate(dateStr, null);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 获取某年某月的最后一天
     * @author huixiaoke
     * @date 2016-4-8
     */
    public static Integer getLastDayOfMonth(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 将字符串类型的日期变成yyyy-mm-dd格式
     * @param dateStr
     * @return
     */
    public static String dateStrFormat(String dateStr) {
        String rs = null;
        if(dateStr != null) {
            if(dateStr.contains("年")) {
                if(dateStr.contains("日")) {
                    rs = dateStr.replaceAll("年", "-").replaceAll("月", "-").replaceAll("日", "");
                }else {
                    rs = dateStr.replaceAll("年", "-").replaceAll("月", "-1");
                }
            }else if(dateStr.contains(".")) {
                rs = dateStr.replaceAll("\\.", "-");
            }else if(dateStr.contains("/")) {
                rs = dateStr.replaceAll("/", "-");
            }else if(dateStr.contains("-")) {
                rs = dateStr;
            }
        }
        return rs;
    }

    public static void main(String[] args) {
        String d1 = "2019年8月23日";
        String d2 = "2019年8月";
        String d3 = "2019.8.23";
        String d4 = "2019-07-27";
        String d5 = "2019/07/27";
        String rs1 = dateStrFormat(d1);
        System.out.println(rs1);
        String rs2 = dateStrFormat(d2);
        System.out.println(rs2);
        String rs3 = dateStrFormat(d3);
        System.out.println(rs3);
        String rs4 = dateStrFormat(d4);
        System.out.println(rs4);
        String rs5 = dateStrFormat(d5);
        System.out.println(rs5);

    }
}
