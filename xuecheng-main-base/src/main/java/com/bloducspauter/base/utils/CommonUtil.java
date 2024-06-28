package com.bloducspauter.base.utils;



import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mr.M
 *
 */
public class CommonUtil {

	public static String hiddenMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			return "";
		}
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}

	public static BigDecimal strToBigDecimal(String s, Boolean isFen) {
		if (StringUtils.isBlank(s)) {
			return null;
		}
		if (!NumberUtils.isNumber(s)) {
			return null;
		}
		BigDecimal bd = new BigDecimal(s);
		if (isFen != null && isFen){
			bd = bd.divide(new BigDecimal(100), 2);
		}
		return bd;
	}

	public static final Pattern SHI_FEN_MIAO_PATTERN = Pattern.compile("(\\d{1,2}[：:]){0,2}\\d{1,2}");

	public static Long shiFenMiaoToSeconds (String shifenmiao) {
		if (StringUtils.isBlank(shifenmiao)) {
			return 0L;
		}
		long totalSeconds = 0L;
		shifenmiao = shifenmiao.replaceAll(" ", "");
		boolean matched = SHI_FEN_MIAO_PATTERN.matcher(shifenmiao).matches();
		if (matched) {
			List<String> sfmList = new ArrayList<>();
			StringTokenizer st = new StringTokenizer(shifenmiao,":：");
			while (st.hasMoreTokens()) {
				sfmList.add(st.nextToken());
			}
			Collections.reverse(sfmList);
			String[] sfmArr = sfmList.toArray(new String[0]);
			for (int i = 0; i < sfmArr.length; i++) {
				if (i == 0) {
					totalSeconds += Long.parseLong(sfmArr[i]);
				} else if (i == 1) {
					totalSeconds += Long.parseLong(sfmArr[i]) * 60;
				} else if (i == 2) {
					totalSeconds += Long.parseLong(sfmArr[i]) * 3600;
				}
			}
		}
		return totalSeconds;
	}

	/**
	 * 将下划线映射到骆驼命名使用的正则表达式, 预编译正则用于提高效率
	 */
	private static final Pattern PATTERN_FOR_UTC = Pattern.compile("_([a-z])");

	/**
	 * 将下划线映射到骆驼命名
	 *
	 */
	public static String mapUnderscoreToCamelCase(String str) {
		// 先转成全小写
		str = str.toLowerCase();
		final Matcher matcher = PATTERN_FOR_UTC.matcher(str);
		while (matcher.find()) {
			str = str.replaceAll(matcher.group(), matcher.group(1).toUpperCase());
		}
		return str;
	}

	/**
	 * 将骆驼命名映射到下划线, 必须是标准的驼峰命名, 否则会出现奇怪的结果
	 *
	 */
	public static String mapCamelCaseToUnderscore(String str) {
		return str.replaceAll("([A-Z])","_$1").toUpperCase();
	}

	public static void main(String[] args) {

        LocalDateTime time1 = LocalDateTime.of(2019,8,8,8,8);
		LocalDateTime time2 = LocalDateTime.of(2019,9,9,8,8);
		Duration duration = Duration.between(time1, time2);
		System.out.println(duration.getSeconds());
		System.out.println(duration.isNegative());
		System.out.println(time1.until(time2, ChronoUnit.SECONDS));
	}
}

