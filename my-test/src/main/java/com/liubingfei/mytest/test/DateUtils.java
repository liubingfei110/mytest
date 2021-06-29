//package com.liubingfei.mytest.test;
//
//import org.apache.commons.lang3.StringUtils;
//import org.junit.Test;
//
//import java.text.SimpleDateFormat;
//import java.time.*;
//import java.time.format.DateTimeFormatter;
//import java.time.temporal.ChronoUnit;
//import java.time.temporal.TemporalAdjusters;
//import java.util.*;
//
///**
// * @Author: LiuBingFei
// * @Date: 2020-06-08 9:29
// * @Description: 日期转换
// */
//public class DateUtils {
//    //Year, month, day, hour, minute, second
//    public static final String YEAR = "yyyy";
//
//    public static final String YEAR_MONTH = "yyyy-MM";
//
//    public static final String YEAR_MONTH_DAY = "yyyy-MM-dd";
//
//    public static final String YEAR_MONTH_DAY_H = "yyyy-MM-dd HH";
//
//    public static final String YEAR_MONTH_DAY_H_M = "yyyy-MM-dd HH:mm";
//
//    public static final String YEAR_MONTH_DAY_H_M_S = "yyyy-MM-dd HH:mm:ss";
//
//    public static final String DAY_H_M = "dd-HH:mm";
//
//    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
//        ZoneId zoneId = ZoneId.systemDefault();
//        ZonedDateTime zdt = localDateTime.atZone(zoneId);
//        return Date.from(zdt.toInstant());
//    }
//
//    public static String localDateTimeToString(LocalDateTime localDateTime, String format) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//        String dateStr = dateTimeFormatter.format(localDateTime);
//        return dateStr;
//    }
//
//    public static String dateToString(Date date, String format) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//        Instant instant = date.toInstant();
//        ZoneId zoneId = ZoneId.systemDefault();
//        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
//        String dateStr = dateTimeFormatter.format(localDateTime);
//        return dateStr;
//    }
//
//    public static LocalDate dateToLocalDate(Date date) {
//        Instant instant = date.toInstant();
//        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
//        LocalDate localDate = zdt.toLocalDate();
//        return localDate;
//    }
//
//
//    public static Date stringToDate(String dateString, String format) {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//        LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
//        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
//        Date date = Date.from(instant);
//        return date;
//    }
//
//    public static LocalDate stringToLocalDate(String dateString) {
//        return stringToLocalDate(dateString, YEAR_MONTH_DAY);
//    }
//
//    public static LocalDate stringToLocalDate(String dateString, String format) {
//        if (StringUtils.isNotBlank(dateString)) {
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//            return LocalDate.parse(dateString, dateTimeFormatter);
//        }
//        return null;
//    }
//
//    public static Date localDateToDate(LocalDate localDate) {
//        if(Objects.isNull(localDate)){
//            throw new BusinessException("时间格式转换失败：null");
//        }
//        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
//        Instant instant1 = zonedDateTime.toInstant();
//        Date date = Date.from(instant1);
//        return date;
//    }
//
//    public static String localDateToString(LocalDate localDate) {
//        if (Objects.nonNull(localDate)) {
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(YEAR_MONTH_DAY);
//            return localDate.format(dateTimeFormatter);
//        }
//        return StringUtils.EMPTY;
//    }
//
//    public static String localDateToString(LocalDate localDate, String format) {
//        if(Objects.isNull(localDate)){
//
//        }
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
//        String dateStr = localDate.format(dateTimeFormatter);
//        return dateStr;
//    }
//
//    @Test
//    public void test7() {
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
//        String dateStr = LocalDate.now().format(dateTimeFormatter);
//        System.out.println(dateStr);
//    }
//
//    /**
//     * 日期加减
//     *
//     * @param localDate  日期
//     * @param operation  加、减
//     * @param dateNumber 日期数字：1,2,3...
//     * @param dateUnit   日期单位：年、月、日
//     * @return
//     */
//    public static LocalDate operateDate(LocalDate localDate, String operation, Integer dateNumber, String dateUnit) {
//        if ("年".equals(dateUnit) && "加".equals(operation)) {
//            return localDate.plusYears(dateNumber);
//        }
//        if ("月".equals(dateUnit) && "加".equals(operation)) {
//            return localDate.plusMonths(dateNumber);
//        }
//        if ("日".equals(dateUnit) && "加".equals(operation)) {
//            return localDate.plusDays(dateNumber);
//        }
//        if ("年".equals(dateUnit) && "减".equals(operation)) {
//            return localDate.minusYears(dateNumber);
//        }
//        if ("月".equals(dateUnit) && "减".equals(operation)) {
//            return localDate.minusMonths(dateNumber);
//        }
//        if ("日".equals(dateUnit) && "减".equals(operation)) {
//            return localDate.minusDays(dateNumber);
//        }
//        throw new BusinessException("dateUnit为日期单位：年、月或者日；operation为操作类型：加或者减");
//    }
//
//    /**
//     * 查询日期间隔
//     *
//     * @param startDateStr
//     * @param endDateStr
//     * @param unit
//     * @return
//     */
//    public Long dateDuration(String startDateStr, String endDateStr, ChronoUnit unit) {
//        LocalDate start = LocalDate.parse(startDateStr);
//        LocalDate end = LocalDate.parse(endDateStr);
//        Long result = 0L;
//        if (!ChronoUnit.YEARS.equals(unit) && !ChronoUnit.MONTHS.equals(unit) && !ChronoUnit.DAYS.equals(unit)) {
//            throw new BusinessException("unit值不对");
//        }
//        if (ChronoUnit.YEARS.equals(unit)) {
//            result = start.until(end, unit);
//        }
//        if (ChronoUnit.MONTHS.equals(unit)) {
//            result = start.until(end, unit);
//        }
//        if (ChronoUnit.DAYS.equals(unit)) {
//            result = start.until(end, unit);
//        }
//        return result;
//    }
//
//    public static List<String> getNowNumMonthList(Integer monthAgoNum, Integer monthAfterNum) {
//        monthAgoNum = Objects.isNull(monthAgoNum) ? 0 : monthAgoNum;
//        monthAfterNum = Objects.isNull(monthAfterNum) ? 0 : monthAfterNum;
//        LocalDate now = LocalDate.now();
//        String startDate = localDateToString(now.minusMonths(monthAgoNum), YEAR_MONTH);
//        String endDate = localDateToString(now.plusMonths(monthAfterNum), YEAR_MONTH);
//        return getTimeList(startDate, endDate);
//    }
//
//    //这个就是方法
//    public static List<String> getTimeList(String startDate, String endDate) {
//        SimpleDateFormat sdf;
//        int calendarType;
//        switch (startDate.length()) {
//            case 10:
//                sdf = new SimpleDateFormat("yyyy-MM-dd");
//                calendarType = Calendar.DATE;
//                break;
//            case 7:
//                sdf = new SimpleDateFormat("yyyy-MM");
//                calendarType = Calendar.MONTH;
//                break;
//            case 4:
//                sdf = new SimpleDateFormat("yyyy");
//                calendarType = Calendar.YEAR;
//                break;
//            default:
//                return null;
//        }
//
//        List<String> result = new ArrayList<>();
//        Calendar min = Calendar.getInstance();
//        Calendar max = Calendar.getInstance();
//        try {
//            min.setTime(sdf.parse(startDate));
//            min.add(calendarType, 0);
//            max.setTime(sdf.parse(endDate));
//            max.add(calendarType, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Calendar curr = min;
//        while (curr.before(max)) {
//            result.add(sdf.format(min.getTime()));
//            curr.add(calendarType, 1);
//        }
//        return result;
//    }
//
//    /**
//     * @param startDate 开始日期
//     * @param endDate   结束日期
//     * @return boolean
//     * @Author: myh
//     * @Date: 2021-04-12 15:58
//     * @Description: 比较日期类时间大小
//     */
//    public static int compareStringDate(String startDate, String endDate) {
//        LocalDate startDateTime = stringToLocalDate(startDate, YEAR_MONTH_DAY);
//        LocalDate endDateTime = stringToLocalDate(endDate, YEAR_MONTH_DAY);
//        return startDateTime.compareTo(endDateTime);
//    }
//
//    public static List<BusinessKeyValueForDateResponse> getKeyValueForDate(String startDateStr, String endDateStr, String shardType) {
//        LocalDate startDate = stringToLocalDate(startDateStr, YEAR_MONTH_DAY);
//        LocalDate endDate = stringToLocalDate(endDateStr, YEAR_MONTH_DAY);
//        return getKeyValueForDate(startDate, endDate, shardType);
//    }
//
//    /** 20210513产品要求去掉按周、按季度、按半年拆分周期,且周期拆分方式由本月15到下月15改为月初到月末 */
//
//    /**
//     * @param startDate 开始时间
//     * @param endDate   结束时间
//     *                  //     * @param shardType 分片类型
//     * @return java.util.List<com.zkml.response.business.BusinessKeyValueForDateResponse>
//     * @Author: myh
//     * @Date: 2021-04-12 10:07
//     * @Description: 根据一段时间区间，按月份拆分成多个时间段
//     */
////    public static List<BusinessKeyValueForDateResponse> getKeyValueForDate(LocalDate startDate, LocalDate endDate, String shardType) {
////        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
////        switch (shardType) {
////            case "DAY":
////                dateTimeList = getKeyValueForDay(startDate, endDate);
////                break;
////            case "WEEK":
////                dateTimeList = getKeyValueForWeek(startDate, endDate);
////                break;
////            case "MONTH":
////                dateTimeList = getKeyValueForMonth(startDate, endDate, 1L);
////                break;
////            case "QUARTERLY":
////                dateTimeList = getKeyValueForMonth(startDate, endDate, 3L);
////                break;
////            case "HALF_YEAR":
////                dateTimeList = getKeyValueForMonth(startDate, endDate, 6L);
////                break;
////            case "YEAR":
////                dateTimeList = getKeyValueForMonth(startDate, endDate, 12L);
////                break;
////            default:
////                break;
////        }
////        return dateTimeList;
////    }
////
////    public static List<BusinessKeyValueForDateResponse> getKeyValueForDay(LocalDate startDate, LocalDate endDate) {
////        LocalDate cycleEndTime;
////        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
////        while (true) {
////            cycleEndTime = startDate.plusDays(1L);
////            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
////            if (cycleEndTime.compareTo(endDate) > 0) {
////                response.setStartTime(endDate)
////                        .setEndTime(endDate);
////                dateTimeList.add(response);
////                break;
////            }
////            response.setStartTime(startDate)
////                    .setEndTime(startDate);
////            dateTimeList.add(response);
////            startDate = cycleEndTime;
////        }
////        return dateTimeList;
////    }
////
////    public static List<BusinessKeyValueForDateResponse> getKeyValueForWeek(LocalDate startDate, LocalDate endDate) {
////        LocalDate cycleEndTime;
////        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
////        while (true) {
////            cycleEndTime = startDate.plusDays(6L);
////            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
////            if (cycleEndTime.compareTo(endDate) >= 0) {
////                response.setStartTime(startDate)
////                        .setEndTime(endDate);
////                dateTimeList.add(response);
////                break;
////            }
////            response.setStartTime(startDate)
////                    .setEndTime(cycleEndTime);
////            dateTimeList.add(response);
////            startDate = cycleEndTime.plusDays(1L);
////        }
////        return dateTimeList;
////    }
////
////    public static List<BusinessKeyValueForDateResponse> getKeyValueForMonth(LocalDate startDate, LocalDate endDate, Long plusMonths) {
////        LocalDate cycleEndTime;
////        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
////        while (true) {
////            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
////            cycleEndTime = startDate.plusMonths(plusMonths).minusDays(1L);
////            if (cycleEndTime.compareTo(endDate) >= 0) {
////                response.setStartTime(startDate)
////                        .setEndTime(endDate);
////                dateTimeList.add(response);
////                break;
////            }
////            response.setStartTime(startDate)
////                    .setEndTime(cycleEndTime);
////            dateTimeList.add(response);
////            startDate = cycleEndTime.plusDays(1L);
////        }
////        return dateTimeList;
////    }
//    public static List<BusinessKeyValueForDateResponse> getKeyValueForDate(LocalDate startDate, LocalDate endDate, String shardType) {
//        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
//        switch (shardType) {
//            case "DAY":
//                dateTimeList = getKeyValueForDay(startDate, endDate);
//                break;
//            case "MONTH":
//                dateTimeList = getKeyValueForMonth(startDate, endDate);
//                break;
//            case "YEAR":
//                dateTimeList = getKeyValueForYear(startDate, endDate);
//                break;
//            default:
//                break;
//        }
//        return dateTimeList;
//    }
//
//    public static List<BusinessKeyValueForDateResponse> getKeyValueForDay(LocalDate startDate, LocalDate endDate) {
//        LocalDate cycleEndTime;
//        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
//        while (true) {
//            cycleEndTime = startDate.plusDays(1L);
//            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
//            if (cycleEndTime.compareTo(endDate) > 0) {
//                response.setStartTime(endDate)
//                        .setEndTime(endDate);
//                dateTimeList.add(response);
//                break;
//            }
//            response.setStartTime(startDate)
//                    .setEndTime(startDate);
//            dateTimeList.add(response);
//            startDate = cycleEndTime;
//        }
//        return dateTimeList;
//    }
//
//    public static List<BusinessKeyValueForDateResponse> getKeyValueForMonth(LocalDate startDate, LocalDate endDate) {
//        LocalDate cycleEndTime;
//        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
//        while (true) {
//            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
//            cycleEndTime = startDate.with(TemporalAdjusters.lastDayOfMonth());
//            if (cycleEndTime.compareTo(endDate) >= 0) {
//                response.setStartTime(startDate)
//                        .setEndTime(endDate);
//                dateTimeList.add(response);
//                break;
//            }
//            response.setStartTime(startDate)
//                    .setEndTime(cycleEndTime);
//            dateTimeList.add(response);
//            startDate = cycleEndTime.plusDays(1L).with(TemporalAdjusters.firstDayOfMonth());
//        }
//        return dateTimeList;
//    }
//
//    public static List<BusinessKeyValueForDateResponse> getKeyValueForYear(LocalDate startDate, LocalDate endDate) {
//        LocalDate cycleEndTime;
//        List<BusinessKeyValueForDateResponse> dateTimeList = new ArrayList<>();
//        while (true) {
//            BusinessKeyValueForDateResponse response = new BusinessKeyValueForDateResponse();
//            cycleEndTime = startDate.with(TemporalAdjusters.lastDayOfYear());
//            if (cycleEndTime.compareTo(endDate) >= 0) {
//                response.setStartTime(startDate)
//                        .setEndTime(endDate);
//                dateTimeList.add(response);
//                break;
//            }
//            response.setStartTime(startDate)
//                    .setEndTime(cycleEndTime);
//            dateTimeList.add(response);
//            startDate = cycleEndTime.plusDays(1L).with(TemporalAdjusters.firstDayOfYear());
//        }
//        return dateTimeList;
//    }
//
//}