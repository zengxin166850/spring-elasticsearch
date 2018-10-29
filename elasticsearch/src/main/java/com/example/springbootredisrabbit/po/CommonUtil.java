package com.example.springbootredisrabbit.po;


import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if (pObj.equals(""))
            return true;
        if (pObj instanceof String) {
            if (((String)pObj).length() == 0) {
                return true;
            }
        }
        else if (pObj instanceof Collection) {
            if (((Collection)pObj).size() == 0) {
                return true;
            }
        }
        else if (pObj instanceof Map) {
            if (((Map)pObj).size() == 0) {
                return true;
            }
        }
        else if (pObj.getClass().isArray()) {
            if (Array.getLength(pObj) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if (pObj.equals(""))
            return false;
        if (pObj instanceof String) {
            if (((String)pObj).length() == 0) {
                return false;
            }
        }
        else if (pObj instanceof Collection) {
            if (((Collection)pObj).size() == 0) {
                return false;
            }
        }
        else if (pObj instanceof Map) {
            if (((Map)pObj).size() == 0) {
                return false;
            }
        }
        else if (pObj.getClass().isArray()) {
            if (Arrays.asList(pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof BigDecimal) {
            BigDecimal zero = BigDecimal.ZERO;
            BigDecimal obj = (BigDecimal) pObj;
            zero = zero.setScale(obj.scale());
            return !(zero.compareTo(obj) == 0);
        }
        return true;
    }

    /**
     * 数据格式化.
     *
     * @param pattern the pattern
     * @param i the i
     * @return the string
     */
    public static String codeFormat(String pattern, Object value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 获取上一天.
     *
     * @param currentDate the current date
     * @return the next date str
     * @throws ParseException the parse exception
     */
    public static String getYesterdayStr(String currentDate, SimpleDateFormat format)
        throws ParseException {
        Date date = format.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String nextDate = format.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 根据日期获取星期
     *
     * @param strdate
     * @return
     */
    public static String getWeekDayByDate(String strdate, SimpleDateFormat format) {
        final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = format.parse(strdate);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    /**
     * 生成固定长度的随机字符和数字
     *
     * @param len
     * @return
     */
    public static String generateRandomCharAndNumber(Integer len) {
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < len; i++) {
            int intRand = (int)(Math.random() * 52);
            int numValue = (int)(Math.random() * 10);
            char base = (intRand < 26) ? 'A' : 'a';
            char c = (char)(base + intRand % 26);
            if (numValue % 2 == 0) {
                sb.append(c);
            }
            else {
                sb.append(numValue);
            }
        }
        return sb.toString();
    }

    /**
     * 方法描述：将系统限定的路径转换为绝对正确的路径
     *
     * @param originalPath
     * @return
     */
    public static String getGeneralFilePath(String originalPath) {
        if ((null != originalPath) && !("".equals(originalPath))) {
            String strPath[] = originalPath.split("\\\\|/");
            originalPath = "";
            // 拼接jar路径
            for (int i = 0; i < strPath.length; i++) {
                if (!("".equals(strPath[i])) && !("".equals(strPath[i].trim()))) {
                    originalPath = originalPath + strPath[i].trim();
                    if (i < strPath.length - 1) {
                        originalPath = originalPath + File.separator;
                    }
                }
            }
        }
        return originalPath;
    }


    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @param srcFile 源文件File
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    @SuppressWarnings("resource")
    public static long copyFile(File srcFile, File destFile)
        throws Exception {
        long copySizes = 0;
        FileChannel fcin = new FileInputStream(srcFile).getChannel();
        FileChannel fcout = new FileOutputStream(destFile).getChannel();
        long size = fcin.size();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        copySizes = size;
        return copySizes;
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        }
        else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * @Title: getRandomNumber
     * @Description: 获取随机数
     * @param count 位数，如果是1就产生1位的数字，如果是2就产生2位数字，依次类推
     * @return
     */
    public static String getRandomNumber(int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            int rand = (int)(Math.random() * 10);
            result += rand;
        }
        return result;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public static Date getDateByMonthNumber(Date date, Integer month) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date getDateByYearNumber(Date date, Integer year) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    public static Date getDateByDayNumber(Date date, Integer day) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    // 序列化
    public static byte[] serialize(Object obj)
        throws IOException {
        ObjectOutputStream obi = null;
        ByteArrayOutputStream bai = null;
        bai = new ByteArrayOutputStream();
        obi = new ObjectOutputStream(bai);
        obi.writeObject(obj);
        byte[] byt = bai.toByteArray();
        obi.close();
        bai.close();
        return byt;
    }

    // 反序列化
    public static Object unserizlize(byte[] byt)
        throws Exception {
        ObjectInputStream oii = null;
        ByteArrayInputStream bis = null;
        bis = new ByteArrayInputStream(byt);
        oii = new ObjectInputStream(bis);
        Object obj = oii.readObject();
        oii.close();
        bis.close();
        return obj;
    }

    public static ResourceBundle newInstance(String filePath) {
        return ResourceBundle.getBundle(filePath);
    }
}