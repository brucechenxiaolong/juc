package com.java.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public abstract class DateTimeUtil {

    static{
        System.setProperty("user.timezone", "Etc/GMT-8");
    }
    /****************日期****************/
    public static final String DATE = "yyyy-MM-dd";

    public static final String DATE_CN = "yyyy年MM月dd日";

    public static final String DATE_SLASH = "yyyy/MM/dd";

    public static final String DATE_COMPACT = "yyyyMMdd";

    /****************时间****************/
    public static final String TIME = "HH:mm:ss";

    public static final String TIME_SHORT = "HH:mm";

    public static final String TIME_LONG = "HH:mm:ss.SSS";

    /**************日期时间**************/
    public static final String DATETIME = DATE + " " + TIME;

    public static final String DATETIME_SHORT = DATE + " " + TIME_SHORT;

    public static final String DATETIME_LONG = DATE + " " + TIME_LONG;

    public static final String DATETIME_CN = DATE_CN + " " + TIME;

    public static final String DATETIME_CN_SHORT = DATE_CN + " " + TIME_SHORT;

    public static final String DATETIME_CN_LONG = DATE_CN + " " + TIME_LONG;

    public static final String DATETIME_SLASH = DATE_SLASH + " " + TIME;

    public static final String DATETIME_SLASH_SHORT = DATE_SLASH + " " + TIME_SHORT;

    public static final String DATETIME_SLASH_LONG = DATE_SLASH + " " + TIME_LONG;

    public static final String YEAR = "yyyy";

    public static final String MONTH = "yyyy-MM";

    public static final String MONTH_CN = "yyyy年MM月";

    public static final String MONTH_SLASH = "yyyy/MM";

    public static final String MONTH_COMPACT = "yyyyMM";

    /****************正则表达式缓存****************/
    private static final Map<String, Pattern> REG_MAP = new ConcurrentHashMap<String, Pattern>();
    /**
     * 根据日期时间格式化字符串获取对应的正则匹配表达式
     * @param format
     * @return
     */
    public static Pattern getPattern(String format) {
        Pattern p = REG_MAP.get(format);
        if (p != null || format == null) {
            return p;
        }
        if ("MILLISECOND".equals(format)) {
            /**
             * 毫秒数, 匹配纯数字
             * 为防止误识别, 11位以上的数字才识别为毫秒数
             * 因此自动识别毫秒数不支持北京时间1970-01-01 08:00:00.000到1970-01-02 11:46:39.999
             */
            p = Pattern.compile("(\\-\\d+|\\d{11,})");
            REG_MAP.put(format, p);
            return p;
        }
        String pString = format;
        // 年
        pString= pString.replaceAll("y+", "([0-9]{1,4})");
        // 月
        pString= pString.replaceAll("M+", "([0]?[1-9]|1[0-2])");
        // 日
        pString= pString.replaceAll("d+", "([0]?[1-9]|[1-2][0-9]|[3][0-1])");
        // 时
        pString= pString.replaceAll("H+", "([0-1]?[0-9]|2[0-3])");
        // 分
        pString= pString.replaceAll("m+", "([0-5]?[0-9])");
        // 秒
        pString= pString.replaceAll("s+", "([0-5]?[0-9])");
        // 毫秒
        pString= pString.replaceAll("S+", "([0-9]{1,3})");

        p = Pattern.compile(pString);
        REG_MAP.put(format, p);
        return p;
    }

    /**
     * 取得合适的SimpleDateFormat;
     * @param format String
     * @return SimpleDateFormat
     */
    public static DateTimeFormatter getAdaptDateFormat(String format) {
        if (format != null) {
            return DateTimeFormat.forPattern(format);
        } else {
            return DateTimeFormat.forPattern(DATETIME);
        }
    }

    /**
     * 当前时区与标准时的毫秒差
     */
    private static int timeOffset = -1;

    /**
     * [日期]按格式转为字符[日期]
     *
     * @param date
     * @param format
     * @return
     */
    public static String date2string(Date date, final String format) {
        if (date != null) {
            if (timeOffset == -1) {
                timeOffset = TimeZone.getDefault().getRawOffset();
            }
            if (format != null) {
                return getAdaptDateFormat(format).print(date.getTime());
            } else if ((date.getTime() + timeOffset) % 86400000 == 0) {
                // 格式化为日期
                return getAdaptDateFormat(DATE).print(date.getTime());
            } else if (date.getTime() % 1000 == 0) {
                // 格式化为日期时间
                return getAdaptDateFormat(DATETIME).print(date.getTime());
            } else {
                // 格式化为长日期时间
                return getAdaptDateFormat(DATETIME_LONG).print(date.getTime());
            }
        }
        return null;
    }

    /**
     * [日期 时间]按格式转为字符[日期 时间]
     *
     * @param date
     * @param format
     * @return
     */
    public static String datetime2string(Date date, final String format) {
        if (date != null) {
            return getAdaptDateFormat(format).print(date.getTime());
        }
        return null;
    }

    /**
     * 取当前详细[日期]yyyy-MM-dd
     *
     * @return String
     */
    public static String getCurrentDate() {
        return getAdaptDateFormat(DATE).print(System.currentTimeMillis());
    }

    /**
     * 取当前详细[日期 时间]yyyy-MM-dd HH:mm:ss
     *
     * @return String
     */
    public static String getCurrentTime() {
        return getAdaptDateFormat(DATETIME).print(System.currentTimeMillis());
    }

    /**
     * 按指定格式取当前[日期 时间]
     *
     * @param format String 指定格式
     * @return String
     */
    public static String getCurrentTime(final String format) {
        return getAdaptDateFormat(format).print(System.currentTimeMillis());
    }

    /**
     * 将字符[日期 时间]按格式转为Date 当字符不能正确匹配格式时,返回null
     * @param dateString 字符[日期 时间]
     * @return Date
     */
    public static Date string2date(String dateString) {
        String format = getDateFormat(dateString);
        return string2date(dateString, format);
    }

    /**
     * 将字符[日期 时间]按格式转为Date 当字符不能正确匹配格式时,返回null
     * @param dateString 字符[日期 时间]
     * @return Date
     * @throws Exception
     */
    public static Date string2date(String dateString, String format)  {
        if (format != null) {
            if ("MILLISECOND".equals(format)) {
                return new Date(Long.valueOf(dateString));
            } else {
                DateFormat df =  new SimpleDateFormat(format);
                try {
                    return df.parse(dateString);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return null;
                }
                //return new Date(getAdaptDateFormat(format).parseMillis(dateString));
            }
        }
        return null;
    }


    public static String getDateFormat(String dateString) {
        if (dateString == null) {
            return null;
        }
        // "-"分隔格式:yyyy-MM-dd
        if (getPattern(DATE).matcher(dateString).lookingAt()) {
            if (getPattern(DATE).matcher(dateString).matches()) {
                return DATE;
            }
            if (getPattern(DATETIME).matcher(dateString).matches()) {
                return DATETIME;
            }
            if (getPattern(DATETIME_LONG).matcher(dateString).matches()) {
                return DATETIME_LONG;
            }
            if (getPattern(DATETIME_SHORT).matcher(dateString).matches()) {
                return DATETIME_SHORT;
            }
        }
        // "/"分隔格式:yyyy/MM/dd
        if (getPattern(DATE_SLASH).matcher(dateString).lookingAt()) {
            if (getPattern(DATE_SLASH).matcher(dateString).matches()) {
                return DATE_SLASH;
            }
            if (getPattern(DATETIME_SLASH).matcher(dateString).matches()) {
                return DATETIME_SLASH;
            }
            if (getPattern(DATETIME_SLASH_LONG).matcher(dateString).matches()) {
                return DATETIME_SLASH_LONG;
            }
            if (getPattern(DATETIME_SLASH_SHORT).matcher(dateString).matches()) {
                return DATETIME_SLASH_SHORT;
            }
        }
        // 中文年月日格式:yyyy年MM月dd日
        if (getPattern(DATE_CN).matcher(dateString).lookingAt()) {
            if (getPattern(DATE_CN).matcher(dateString).matches()) {
                return DATE_CN;
            }
            if (getPattern(DATETIME_CN).matcher(dateString).matches()) {
                return DATETIME_CN;
            }
            if (getPattern(DATETIME_CN_LONG).matcher(dateString).matches()) {
                return DATETIME_CN_LONG;
            }
            if (getPattern(DATETIME_CN_SHORT).matcher(dateString).matches()) {
                return DATETIME_CN_SHORT;
            }
        }
        // 8位日期格式:
        if (getPattern(DATE_COMPACT).matcher(dateString).matches()) {
            if (getPattern(YEAR).matcher(dateString).matches()) {
                return YEAR;
            }

            if (getPattern(MONTH_COMPACT).matcher(dateString).matches()) {
                return MONTH_COMPACT;
            }
            return DATE_COMPACT;
        }
        // 毫秒数(完整匹配9位以上数字)
        if (getPattern("MILLISECOND").matcher(dateString).matches()) {
            return "MILLISECOND";
        }

        if (getPattern(YEAR).matcher(dateString).matches()) {
            return YEAR;
        }

        if (getPattern(MONTH).matcher(dateString).matches()) {
            return MONTH;
        }

        if (getPattern(MONTH_CN).matcher(dateString).matches()) {
            return MONTH_CN;
        }

        if (getPattern(MONTH_SLASH).matcher(dateString).matches()) {
            return MONTH_SLASH;
        }

        return null;
    }


    /**
     * @author dc
     * @param oldTime
     * @param newTime
     * @param itype(
     * case 1:
     *     返回相差天数 未满1天不算一天 (小时忽略)
     * case 2:
     *  返回相差小时 未满1小时不算一小时 (分钟忽略)
     * case 3：
     * 返回相差分钟数 未满一分钟不算一分钟(秒数忽略)
     * case 4:
     * 返回相差秒数 未满一秒钟不算一秒钟(豪秒数忽略)
     * case 5:
     * 返回相差毫秒
     * default:
     * 返回相差总时间：
     * 如 1天1小时1分1秒1毫秒
     * 格式：1,1,1,1,1
     * )
     * @return
     */
    public static String getTimeDifference(String oldTime,String newTime,int itype) {
        String timeStr = "";
        Date date1 = string2date(oldTime);
        Date date2 = string2date(newTime);
        long timeDifference = date2.getTime()-date1.getTime();
        long diffSeconds = timeDifference / 1000;
        long diffMinutes = diffSeconds / 60;
        long diffHours = diffMinutes / 60;
        long diffDays = diffHours / 24;
        switch (itype) {
            case 1:
                timeStr = String.valueOf(diffDays);
                break;
            case 2:
                timeStr = String.valueOf(diffHours);
                break;
            case 3:
                timeStr = String.valueOf(diffMinutes);
                break;
            case 4:
                timeStr = String.valueOf(diffSeconds);
                break;
            case 5:
                timeStr = String.valueOf(timeDifference);
                break;
            default:
                timeStr = diffDays+","+(diffHours % 24)+","+(diffMinutes % 60)
                        + ","+(diffSeconds % 60)+","+(timeDifference % 1000);
                break;
        }
        return timeStr;
    }

    public static void main(String[] args) {
        //1.日期转string格式字符串
        System.out.println(DateTimeUtil.date2string(new Date(), "yyyy-MM-dd HH:mm:ss"));


    }

}
