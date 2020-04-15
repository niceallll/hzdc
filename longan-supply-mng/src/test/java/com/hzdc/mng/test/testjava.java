package com.hzdc.mng.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.longan.biz.dataobject.Item;
import com.longan.biz.utils.Base64;
import com.longan.biz.utils.BigDecimalUtils;
import com.longan.biz.utils.DateTool;
import com.longan.biz.utils.ExportTool;
import com.longan.biz.utils.Md5Encrypt;

public class testjava {
    private static Object lock = "lock";
    private static String lockName1 = ":lockName1:run0";
    private static String lockName2 = ":lockName2:run0";

    public static void main(String[] args) throws Exception {
	// Pattern pattern = Pattern.compile("5[0-9]{2}");
	// Matcher isNum = pattern.matcher(511 + "");
	// System.out.println(isNum.matches());
	// listarea();
	// qblib();
	// qblibfile();
	// phonelib();
	// qblibtabl();
	// qblibcopy();
	// pojofld();
	// testjson();
	// convarea();
	// testrand();
	// testthread();
	// testlock();
	// updorder();
	// updbiz();
	// selorder();
	// phoneno();
	// filterUid();
	// blacklist();
	// xlsarea();
	// convName();
	// getSql();
    }

    private static void convName() {
	try {
	    Set<String> cityHs = new HashSet<String>();
	    List<String> provinceList = getProvinceList();
	    List<String> cityList = getCityList();
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/号段.in.txt"), "utf-8"));
	    // BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("c:/temp/号段.out.txt")),
	    // "utf-8"));
	    // BufferedWriter bww = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new
	    // File("c:/temp/号段.outt.txt")),
	    // "utf-8"));
	    String str = null;
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] area = str.split(",");
		String mobile = area[0].trim();
		String type = area[1].trim();
		String cityName = area[2].trim();

		// if (mobile.startsWith("0")) {
		// } else {
		// bw.write(mobile + "," + type + "," + cityName + "\r\n");
		// }

		// for (String city : cityList) {
		// if (cityName.contains(city)) {
		// if (type.contains("联通")) {
		// bw.write(mobile + "," + city + "联通," + cityName + "\r\n");
		// } else if (type.contains("电信")) {
		// bw.write(mobile + "," + city + "电信," + cityName + "\r\n");
		// } else if (type.contains("移动")) {
		// bw.write(mobile + "," + city + "移动," + cityName + "\r\n");
		// } else {
		// bww.write(mobile + "," + type + "," + cityName + "\r\n");
		// }
		// break;
		// }
		// }

		// if (cityName.contains("省") || cityName.contains("市")) {
		// bw.write(mobile + "," + type + "," + cityName.replaceAll("省", "").replaceAll("市", "") + "\r\n");
		// } else {
		// bw.write(mobile + "," + type + "," + cityName + "\r\n");
		// }

		// if (cityName.contains("联通") || cityName.contains("电信") || cityName.contains("移动")) {
		// bw.write(mobile + "," + type + "," + cityName.replaceAll("联通", "").replaceAll("电信", "").replaceAll("移动", "")
		// + "\r\n");
		// } else {
		// bw.write(mobile + "," + type + "," + cityName + "\r\n");
		// }

		// if (cityName.equals("重庆")) {
		// bw.write(mobile + "," + type + ",重庆重庆" + "\r\n");
		// } else if (cityName.equals("上海")) {
		// bw.write(mobile + "," + type + ",上海上海" + "\r\n");
		// } else if (cityName.equals("天津")) {
		// bw.write(mobile + "," + type + ",天津天津" + "\r\n");
		// } else if (cityName.equals("北京")) {
		// bw.write(mobile + "," + type + ",北京北京" + "\r\n");
		// } else if (cityName.equals("吉林")) {
		// bw.write(mobile + "," + type + ",吉林吉林" + "\r\n");
		// } else if (cityName.equals("海南")) {
		// bw.write(mobile + "," + type + ",海南海口" + "\r\n");
		// } else {
		// bw.write(mobile + "," + type + "," + cityName + "\r\n");
		// }

		// boolean include = false;
		// for (String city : cityList) {
		// if (cityName.contains(city)) {
		// include = true;
		// break;
		// }
		// }
		// if (include) {
		// bw.write(mobile + "," + type + "," + cityName + "\r\n");
		// } else {
		// bww.write(mobile + "," + type + "," + cityName + "\r\n");
		// }

		boolean include = false;
		for (String city : cityList) {
		    if (cityName.contains(city)) {
			include = true;
			break;
		    }
		}

		if (!include && !cityHs.contains(cityName)) {
		    System.out.println(mobile + ":" + cityName);
		    cityHs.add(cityName);
		}

		include = false;
		for (String province : provinceList) {
		    if (cityName.contains(province)) {
			include = true;
			break;
		    }
		}

		if (!include) {
		    System.out.println(mobile + ":" + cityName);
		    cityHs.add(cityName);
		}
	    }
	    // bw.flush();
	    // bw.close();
	    // bww.flush();
	    // bww.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	System.out.println("finish");
    }

    private static void getSql() {
	System.out.println("start");
	try {
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);

	    Set<String> hsPart = new HashSet<String>();
	    HashMap<String, AreaUnit> hmArea = getAreaMap();
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/号段.in.txt"), "utf-8"));
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("c:/temp/号段.out.txt")),
		    "utf-8"));
	    BufferedWriter bww = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("c:/temp/号段.outt.txt")),
		    "utf-8"));
	    String str = null;
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] area = str.split(",");
		String mobilePart = area[0].trim();
		String typeName = area[1].trim();
		String cityName = area[2].trim();

		// boolean b = true;
		// if (hmArea.containsKey(cityName)) {
		// continue;
		// } else {
		// System.out.println(mobilePart + ":" + cityName);
		// if (b) {
		// continue;
		// }
		// }

		System.out.println(mobilePart);
		if (passpart(dbStm, mobilePart)) {
		    continue;
		}

		if (hsPart.contains(mobilePart)) {
		} else {
		    String carrierName = "";
		    int carrierType = 0;
		    if (typeName.contains("移动")) {
			carrierType = 3;
			carrierName = "移动";
		    } else if (typeName.contains("电信")) {
			carrierType = 2;
			carrierName = "电信";
		    } else if (typeName.contains("联通")) {
			carrierType = 1;
			carrierName = "联通";
		    } else {
			System.out.println(typeName);
		    }

		    AreaUnit areaUnit = hmArea.get(cityName);
		    System.out.println(cityName);
		    StringBuilder sb = new StringBuilder(
			    "insert into mobile_belong(mobile_part,province_name,city_name,mobile_type_name,area_code,carrier_name,carrier_type,province_code,city_code) values(");
		    sb.append("'").append(mobilePart).append("',");
		    sb.append("'").append(areaUnit.getProvinceName()).append("',");
		    sb.append("'").append(areaUnit.getCityName()).append("',");
		    sb.append("'").append(typeName).append("',");
		    sb.append("'").append(areaUnit.getAreaCode()).append("',");
		    sb.append("'").append(carrierName).append("',");
		    sb.append(carrierType).append(",");
		    sb.append("'").append(areaUnit.getProvinceCode()).append("',");
		    sb.append("'").append(areaUnit.getCityCode()).append("')");

		    System.out.println(sb.toString());
		    if (mobilePart.length() == 7) {
			bw.write(sb.toString() + ";\r\n");
		    } else {
			bww.write(sb.toString() + ";\r\n");
		    }
		    hsPart.add(mobilePart);
		}
	    }

	    bw.flush();
	    bw.close();
	    bww.flush();
	    bww.close();
	    dbStm.close();
	    dbCon.close();
	    hsPart.clear();
	    hmArea.clear();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	System.out.println("finish");
    }

    private static void listarea() {
	try {
	    String[][] list = { { "1705865", "银川" }, { "1781121", "银川" }, { "1781126", "银川" }, { "1781127", "银川" },
		    { "1781128", "银川" }, { "1781122", "石嘴山" }, { "1781123", "吴忠" }, { "1781129", "吴忠" }, { "1781124", "固原" },
		    { "1781125", "中卫" }, { "1781130", "银川" }, { "1781131", "银川" }, { "1781136", "银川" }, { "1781137", "银川" },
		    { "1781138", "银川" }, { "1781132", "石嘴山" }, { "1781133", "吴忠" }, { "1781134", "固原" }, { "1781139", "固原" },
		    { "1781135", "中卫" }, { "1784820", "银川" }, { "1784821", "银川" }, { "1784827", "银川" }, { "1784828", "银川" },
		    { "1784829", "银川" }, { "1784822", "石嘴山" }, { "1784823", "吴忠" }, { "1784824", "固原" }, { "1784825", "中卫" },
		    { "1784826", "中卫" }, { "1788770", "银川" }, { "1788771", "银川" }, { "1788777", "银川" }, { "1788778", "银川" },
		    { "1788779", "银川" }, { "1788772", "石嘴山" }, { "1788773", "吴忠" }, { "1788776", "吴忠" }, { "1788774", "固原" },
		    { "1788775", "中卫" }, { "1980950", "银川" }, { "1980951", "银川" }, { "1980956", "银川" }, { "1980957", "银川" },
		    { "1980958", "银川" }, { "1980952", "石嘴山" }, { "1980953", "吴忠" }, { "1980954", "固原" }, { "1980955", "中卫" },
		    { "1980959", "中卫" } };
	    HashMap<String, AreaUnit> hmArea = getAreaMap();
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);
	    System.out.println("start:" + list.length);
	    for (int i = 0; i < list.length; i++) {
		String mobilePart = list[i][0];
		if (passpart(dbStm, mobilePart)) {
		    continue;
		}
		String provinceName = "宁夏";
		String cityName = list[i][1];
		int carrierType = 3;
		String typeName = cityName + "移动";
		String carrierName = "移动";
		AreaUnit areaUnit = hmArea.get(provinceName + "_" + cityName);
		StringBuilder sb = new StringBuilder(
			"insert into mobile_belong(mobile_part,province_name,city_name,mobile_type_name,area_code,carrier_name,carrier_type,province_code,city_code) values(");
		sb.append("'").append(mobilePart).append("',");
		sb.append("'").append(provinceName).append("',");
		sb.append("'").append(cityName).append("',");
		sb.append("'").append(typeName).append("',");
		sb.append("'").append(areaUnit.getAreaCode()).append("',");
		sb.append("'").append(carrierName).append("',");
		sb.append(carrierType).append(",");
		sb.append("'").append(areaUnit.getProvinceCode()).append("',");
		sb.append("'").append(areaUnit.getCityCode()).append("');");

		System.out.println(sb.toString());
	    }
	    dbStm.close();
	    dbCon.close();
	    hmArea.clear();
	    System.out.println("finish:" + list.length);
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }

    private static void xlsarea() {
	try {
	    HashMap<String, AreaUnit> hmArea = getAreaMap();
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);
	    InputStream isIn = new FileInputStream("c:/temp/号段.2018.05.v1.xls");
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(
		    "c:/temp/号段.2018.05.tmp.csv"))));
	    HSSFWorkbook workbook = new HSSFWorkbook(isIn);
	    for (int i = 0; i < 6; i++) {
		HSSFSheet sheet = workbook.getSheetAt(i);
		int iRowLen = sheet.getLastRowNum();
		for (int iRow = 1; iRow < iRowLen; iRow++) {
		    Row row = sheet.getRow(iRow);
		    String mobilePart = row.getCell(1).getStringCellValue();
		    if (passpart(dbStm, mobilePart)) {
			continue;
		    }
		    String provinceName = row.getCell(2).getStringCellValue();
		    String cityName = row.getCell(3).getStringCellValue();
		    String carrierName = row.getCell(4).getStringCellValue();

		    int carrierType = 0;
		    String typeName = "";
		    if ("中国移动".equals(carrierName)) {
			carrierType = 3;
			typeName = cityName + "移动";
			carrierName = "移动";
		    } else if ("中国电信".equals(carrierName)) {
			carrierType = 2;
			typeName = cityName + "电信";
			carrierName = "电信";
		    } else if ("中国联通".equals(carrierName)) {
			carrierType = 1;
			typeName = cityName + "联通";
			carrierName = "联通";
		    }
		    AreaUnit areaUnit = hmArea.get(provinceName + "_" + cityName);
		    StringBuilder sb = new StringBuilder(
			    "insert into mobile_belong(mobile_part,province_name,city_name,mobile_type_name,area_code,carrier_name,carrier_type,province_code,city_code) values(");
		    sb.append("'").append(mobilePart).append("',");
		    sb.append("'").append(provinceName).append("',");
		    sb.append("'").append(cityName).append("',");
		    sb.append("'").append(typeName).append("',");
		    sb.append("'").append(areaUnit.getAreaCode()).append("',");
		    sb.append("'").append(carrierName).append("',");
		    sb.append(carrierType).append(",");
		    sb.append("'").append(areaUnit.getProvinceCode()).append("',");
		    sb.append("'").append(areaUnit.getCityCode()).append("')");

		    bw.write(sb.toString() + ";\r\n");
		    // dbStm.execute(sb.toString());
		}
		System.out.println("finish:" + iRowLen);
	    }
	    bw.close();
	    isIn.close();
	    dbStm.close();
	    dbCon.close();
	    hmArea.clear();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }

    private static boolean passpart(Statement dbStm, String mobilePart) throws Exception {
	ResultSet rsFld = dbStm.executeQuery("select count(1) from mobile_belong where mobile_part='" + mobilePart + "'");
	int count = 0;
	while (rsFld.next()) {
	    count = rsFld.getInt(1);
	}
	rsFld.close();
	if (count > 0)
	    return true;
	return false;
    }

    private static void filterUid() {
	try {
	    InputStream isIn = new FileInputStream("c:/1500783441982.uid.xls");
	    HSSFWorkbook workbook = new HSSFWorkbook(isIn);
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    HashSet<String> hs = new HashSet<String>();
	    int iRowLen = sheet.getLastRowNum();
	    int number = 0;
	    BufferedWriter bwOut = new BufferedWriter(new FileWriter("c:/uidlist.txt"));
	    for (int iRow = 0; iRow < iRowLen; iRow++) {
		Row row = sheet.getRow(iRow);
		String str1 = row.getCell(0).getStringCellValue();
		if (hs.contains(str1)) {
		    continue;
		}
		bwOut.write(str1 + ",");
		hs.add(str1);
		number++;
	    }
	    bwOut.flush();
	    bwOut.close();
	    isIn.close();
	    System.out.println("finish:" + number);
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }

    private static void testjson() {
	// JSONObject joOrder = JSONObject
	// .fromObject("{\"resultCode\":\"1000\",\"resultReason\":\"\",\"order\":{\"orderNo\":\"1445685613100370\",\"orderStatus\":3,\"prodPayType\":\"0\",\"incomeRate\":\"0\",\"channelOrderNo\":\"001\",\"prodId\":\"100885\",\"customer\":\"18857863377\",\"sum\":\"1\",\"orderSnap\":\"0.01|\u5b89\u5fbd\u8054\u901a50M\u6d41\u91cf\",\"prodParValue\":\"50\",\"createTime\":\"2015-10-24 19:20:13\",\"resultCode\":null,\"resultReason\":null,\"appId\":\"8\",\"prodType\":\"1\",\"appPrice\":\"1\"}}");
	// System.out.println(joOrder.get("resultCode").toString());
	// System.out.println(joOrder.getJSONObject("order").containsKey("orderStatus"));
	// JSONObject joOrder = JSONObject
	// .fromObject("{\"Code\":\"0\",\"Message\":\"OK\",\"Reports\":[{\"TaskID\":8,\"Mobile\":\"13602608888\",\"Status\":4,\"ReportTime\":\"2015-10-29 11:57:13\",\"ReportCode\":\"0：成功\"},{\"TaskID\":103,\"Mobile\":\"18205183720\",\"Status\":4,\"ReportTime\":\"2015-10-30 09:22:46\",\"ReportCode\":\"0：成功\"}]}");
	// JSONArray array = joOrder.getJSONArray("Reports");
	// System.out.println(array.getJSONObject(0).getString("TaskID"));
    }

    private static void testrand() {
	Random rm = new Random();
	for (int i = 0; i < 200; i++) {
	    double pross = (1 + rm.nextDouble()) * Math.pow(10, 12);
	    String fixLenthString = String.valueOf(pross);
	    System.out.println(fixLenthString.substring(2, 12 + 2));
	}
    }

    private static void testthread() {
	ScheduledExecutorService ses = new ScheduledThreadPoolExecutor(5);
	final ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
	final ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock();
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		try {
		    lock1.writeLock().lock();
		    System.out.println(new Date() + ":1");
		    lockName1 = ":lockName1:run1";
		    Thread.sleep(1000);
		} catch (Exception ex) {
		} finally {
		    System.out.println(new Date() + lockName1 + ":1");
		    System.out.println(new Date() + lockName2 + ":1");
		    lock1.writeLock().unlock();
		}
	    }
	}, 5, 5, TimeUnit.SECONDS);
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		try {
		    lock2.writeLock().lock();
		    System.out.println(new Date() + ":2");
		    lockName2 = ":lockName2:run2";
		    Thread.sleep(1000);
		} catch (Exception ex) {
		} finally {
		    System.out.println(new Date() + lockName1 + ":2");
		    System.out.println(new Date() + lockName2 + ":2");
		    lock2.writeLock().unlock();
		}
	    }
	}, 5, 5, TimeUnit.SECONDS);
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		try {
		    lock1.writeLock().lock();
		    System.out.println(new Date() + ":3");
		    lockName1 = ":lockName1:run3";
		    Thread.sleep(1000);
		} catch (Exception ex) {
		} finally {
		    System.out.println(new Date() + lockName1 + ":3");
		    System.out.println(new Date() + lockName2 + ":3");
		    lock1.writeLock().unlock();
		}
	    }
	}, 5, 5, TimeUnit.SECONDS);
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		try {
		    lock2.writeLock().lock();
		    System.out.println(new Date() + ":4");
		    lockName2 = ":lockName2:run4";
		    Thread.sleep(1000);
		} catch (Exception ex) {
		} finally {
		    System.out.println(new Date() + lockName1 + ":4");
		    System.out.println(new Date() + lockName2 + ":4");
		    lock2.writeLock().unlock();
		}
	    }
	}, 5, 5, TimeUnit.SECONDS);
	ses.scheduleAtFixedRate(new Runnable() {
	    public void run() {
		try {
		    lock1.writeLock().lock();
		    System.out.println(new Date() + ":5");
		    lockName1 = ":lockName1:run5";
		    Thread.sleep(1000);
		} catch (Exception ex) {
		} finally {
		    System.out.println(new Date() + lockName1 + ":5");
		    System.out.println(new Date() + lockName2 + ":5");
		    lock1.writeLock().unlock();
		}
	    }
	}, 5, 5, TimeUnit.SECONDS);
    }

    private static void testlock() {
	ExecutorService es = Executors.newFixedThreadPool(8);
	es.execute(new Runnable() {
	    public void run() {
		synchronized (lock) {
		    try {
			System.out.println("start wait");
			lock.wait(60 * 1000);
			System.out.println("end wait");
		    } catch (InterruptedException ex) {
			ex.printStackTrace(System.out);
		    }
		}
	    }
	});
	es.execute(new Runnable() {
	    public void run() {
		synchronized (lock) {
		    try {
			System.out.println("start notify");
			Thread.sleep(10 * 1000);
			lock.notifyAll();
			System.out.println("end notify");
		    } catch (InterruptedException ex) {
			ex.printStackTrace(System.out);
		    }
		}
	    }
	});
    }

    // mobile_type_name = city_name + carrier_name
    // carrier_type：联通(1) 电信(2) 移动(3)
    // area_code,province_code,city_code：按province_name、city_name从area_info表抓取
    // truncate table mobile_belong
    // alter table mobile_belong auto_increment=1
    private static void convarea() {
	try {
	    HashMap<String, AreaUnit> hmArea = getAreaMap();
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/号段.csv"), "UTF-8"));
	    String str = br.readLine();
	    int iPos = 0;
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] area = str.split(",");
		String mobilePart = StringUtils.replace(area[1].trim(), "\"", "");
		String carrierName = StringUtils.replace(area[2].trim(), "\"", "");
		String provinceName = StringUtils.replace(area[3].trim(), "\"", "");
		String cityName = StringUtils.replace(area[4].trim(), "\"", "");
		String typeName = cityName + carrierName;
		int carrierType = 3;
		if ("电信".equals(carrierName))
		    carrierType = 2;
		else if ("联通".equals(carrierName))
		    carrierType = 1;
		// System.out.println(provinceName + "_" + cityName);
		AreaUnit areaUnit = hmArea.get(provinceName + "_" + cityName);
		// System.out.println(areaUnit.getAreaCode() + "_" + areaUnit.getProvinceCode() + "_" + areaUnit.getCityCode());
		StringBuilder sb = new StringBuilder(
			"insert into mobile_belong(mobile_part,province_name,city_name,mobile_type_name,area_code,carrier_name,carrier_type,province_code,city_code) values(");
		sb.append("'").append(mobilePart).append("',");
		sb.append("'").append(provinceName).append("',");
		sb.append("'").append(cityName).append("',");
		sb.append("'").append(typeName).append("',");
		sb.append("'").append(areaUnit.getAreaCode()).append("',");
		sb.append("'").append(carrierName).append("',");
		sb.append(carrierType).append(",");
		sb.append("'").append(areaUnit.getProvinceCode()).append("',");
		sb.append("'").append(areaUnit.getCityCode()).append("')");

		System.out.println(sb.toString());
		dbStm.execute(sb.toString());
	    }
	    dbStm.close();
	    dbCon.close();
	    hmArea.clear();
	    br.close();
	    System.out.println("finish:" + iPos);
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private static List<String> getAreaCodeList() throws Exception {
	List<String> codeList = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	dbCon.setAutoCommit(true);
	Statement dbStm = dbCon.createStatement();
	ResultSet rsFld = dbStm.executeQuery("select area_code from area_info where type=2");
	while (rsFld.next()) {
	    String areaCode = rsFld.getString(1).trim();
	    codeList.add(areaCode);
	}
	rsFld.close();
	dbStm.close();
	dbCon.close();
	return codeList;
    }

    private static HashMap<String, AreaUnit> getAreaMap() throws Exception {
	HashMap<String, AreaUnit> hmArea = new HashMap<String, AreaUnit>();
	Connection dbCon = getLocalDbCon();
	dbCon.setAutoCommit(true);
	Statement dbStm = dbCon.createStatement();
	ResultSet rsFld = dbStm.executeQuery("select * from area_info");
	while (rsFld.next()) {
	    int type = rsFld.getInt(7);
	    if (type == 2) {
		String provinceCode = rsFld.getString(2).trim();
		String cityCode = rsFld.getString(3).trim();
		String provinceName = rsFld.getString(4).trim();
		String cityName = rsFld.getString(5).trim();
		String areaCode = rsFld.getString(6).trim();
		// hmArea.put(provinceName + "_" + cityName, new AreaUnit(provinceCode, cityCode, provinceName, cityName,
		// areaCode));
		// hmArea.put(provinceName + "_" + cityName, new AreaUnit(provinceCode, cityCode, provinceName, cityName,
		// areaCode));
		hmArea.put(provinceName + cityName, new AreaUnit(provinceCode, cityCode, provinceName, cityName, areaCode));
	    }
	}
	rsFld.close();
	dbStm.close();
	dbCon.close();
	return hmArea;
    }

    private static List<String> getProvinceList() throws Exception {
	List<String> provinceList = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	dbCon.setAutoCommit(true);
	Statement dbStm = dbCon.createStatement();
	ResultSet rsFld = dbStm.executeQuery("select province_name from area_info where type=1");
	while (rsFld.next()) {
	    String proviceName = rsFld.getString(1).trim();
	    provinceList.add(proviceName);
	}
	rsFld.close();
	dbStm.close();
	dbCon.close();
	return provinceList;
    }

    private static List<String> getCityList() throws Exception {
	List<String> cityList = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	dbCon.setAutoCommit(true);
	Statement dbStm = dbCon.createStatement();
	ResultSet rsFld = dbStm.executeQuery("select city_name from area_info where type=2");
	while (rsFld.next()) {
	    String cityName = rsFld.getString(1).trim();
	    cityList.add(cityName);
	}
	rsFld.close();
	dbStm.close();
	dbCon.close();
	return cityList;
    }

    private static void updorder() throws Exception {
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/update.list.csv"), "UTF-8"));
	    String str = br.readLine();
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] list = str.split(",");
		String gxhlNo = list[0].trim();
		String phoneNo = list[1].trim();
		String stat = list[3].trim();
		String desc = list[4].trim();
		String hzdcNo = list[5].trim();

		if (StringUtils.isNotBlank(gxhlNo)) {
		    StringBuilder sb = new StringBuilder("update supply_order set ");
		    sb.append("upstream_serialno = '").append(gxhlNo).append("'");
		    // sb.append("upstream_serialno = '").append(hzdcNo).append("'");
		    sb.append(" where id = ").append(hzdcNo);
		    System.out.println(sb.toString());
		    dbStm.execute(sb.toString());
		}
	    }
	    dbStm.close();
	    dbCon.close();
	    br.close();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private static void updbiz() {
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/update.list.csv"), "UTF-8"));
	    String str = br.readLine();
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    ResultSet dbRs = null;
	    dbCon.setAutoCommit(true);
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] list = str.split(",");
		String gxhlNo = list[0].trim();
		String phoneNo = list[1].trim();
		String stat = list[3].trim();
		String desc = list[4].trim();
		String hzdcNo = list[5].trim();
		if (StringUtils.isNotBlank(gxhlNo)) {
		    StringBuilder sbget = new StringBuilder("select biz_order_id from supply_order where ");
		    sbget.append(" id = ").append(hzdcNo);
		    dbRs = dbStm.executeQuery(sbget.toString());
		    if (dbRs.next()) {
			Long bizid = dbRs.getLong(1);
			StringBuilder sb = new StringBuilder("update biz_order set ");
			sb.append("upstream_serialno = '").append(gxhlNo).append("'");
			// sb.append("upstream_serialno = '").append(hzdcNo).append("'");
			sb.append(" where id = ").append(bizid);
			System.out.println(sb.toString());
			dbStm.execute(sb.toString());
		    }
		}
	    }
	    dbRs.close();
	    dbStm.close();
	    dbCon.close();
	    br.close();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private static void selorder() {
	try {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/temp/update.list.csv"), "UTF-8"));
	    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("c:/temp/sel.list.csv"))));
	    String str = br.readLine();
	    int iPos = 0;
	    Connection dbCon = getLocalDbCon();
	    Statement dbStm = dbCon.createStatement();
	    ResultSet dbRs = null;
	    dbCon.setAutoCommit(true);
	    while ((str = br.readLine()) != null && !("").equals(str)) {
		String[] list = str.split(",");
		String phoneNo = list[0].trim();
		String hzdcNo = list[1].trim();
		String stat = list[2].trim();
		String gxhlNo = list[3].trim();
		String desc = list[4].trim();
		StringBuilder sbSql = new StringBuilder(
			"select biz.id,biz.downstream_serialno from biz_order as biz,supply_order as supply where biz.id = supply.biz_order_id and biz.user_id = '104' ");
		sbSql.append(" and supply.id = ").append(hzdcNo);
		dbRs = dbStm.executeQuery(sbSql.toString());
		if (dbRs.next()) {
		    Long selfNo = dbRs.getLong(1);
		    String downNo = dbRs.getString(2);

		    StringBuilder sbCnt = new StringBuilder();
		    sbCnt.append(phoneNo).append(",").append(downNo).append(",");
		    if ("7".equals(stat))
			sbCnt.append(2);
		    else if ("8".equals(stat))
			sbCnt.append(3);
		    sbCnt.append(",").append(selfNo).append(",").append(desc).append("\r\n");
		    bw.write(sbCnt.toString());
		}
	    }
	    dbRs.close();
	    dbStm.close();
	    dbCon.close();
	    br.close();
	    bw.flush();
	    bw.close();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }

    private static void phoneno() throws Exception {
	int imax = 9624;
	int ipos = 0;
	BufferedWriter bw = new BufferedWriter(
		new OutputStreamWriter(new FileOutputStream(new File("c:/temp/phone.no.list.csv"))));
	HashSet<String> set = new HashSet<String>();
	String bizsql = "select distinct(item_uid) from biz_order where province_code=320000 and biz_id=102 order by id limit ";
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	ResultSet dbRs = null;
	dbCon.setAutoCommit(true);
	while (ipos < imax) {
	    System.out.println(ipos);
	    String cursql = bizsql + ipos + "," + (ipos + 1000);
	    dbRs = dbStm.executeQuery(cursql);
	    while (dbRs.next()) {
		String mobile = dbRs.getString(1);
		if (!set.contains(mobile)) {
		    set.add(mobile);
		    bw.write(mobile + "\r\n");
		}
	    }
	    ipos += 1000;
	}
	bw.flush();
	bw.close();
	dbRs.close();
	dbStm.close();
	dbCon.close();
	System.out.println("finish");
    }

    private static Connection getLocalDbCon() throws Exception {
	Class.forName("com.mysql.jdbc.Driver");
	Connection dbCon = DriverManager.getConnection(
		"jdbc:mysql://127.0.0.1:3306/longan?useUnicode=true&characterEncoding=utf-8", "root", "11111111");
	return dbCon;
    }

    private static void pojofld() {
	xmlroot item = new xmlroot();
	Field[] field = item.getClass().getDeclaredFields();
	for (int i = 0; i < field.length; i++) {
	    field[i].setAccessible(true);
	    System.out.println(field[i].getName());
	    if (field[i].getType().getName().contains("hzdc")) {
		Field[] sf = field[i].getType().getDeclaredFields();
		for (int ii = 0; ii < sf.length; ii++) {
		    sf[ii].setAccessible(true);
		    System.out.println(sf[ii].getName());
		    if (sf[ii].getType().getTypeParameters().length > 0)
			System.out.println(sf[ii].getType().getTypeParameters()[0]);
		}
	    }
	}
	// HashMap<String, Object> map = new HashMap<String, Object>();
	// map.put("itemName", "aaa");
	// map.put("itemSalesPrice", 100);
	// Item item = new Item();
	// Field[] field = item.getClass().getDeclaredFields();
	// for (int i = 0; i < field.length; i++) {
	// field[i].setAccessible(true);
	// if (map.containsKey(field[i].getName()))
	// System.out.println(field[i].get(item));
	// }
	// for (int i = 0; i < field.length; i++) {
	// field[i].setAccessible(true);
	// if (map.containsKey(field[i].getName())) {
	// field[i].set(item, map.get(field[i].getName()));
	// }
	// }
	// for (int i = 0; i < field.length; i++) {
	// field[i].setAccessible(true);
	// if (map.containsKey(field[i].getName()))
	// System.out.println(field[i].get(item));
	// }
    }

    private static void qblibcopy() throws Exception {
	List<QblibUnit> list = getDbList();
	List<String> sql = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	dbCon.setAutoCommit(false);
	while (list.size() > 0) {
	    for (QblibUnit unit : list) {
		StringBuilder sbins = new StringBuilder("insert into mobile_qblib_").append(unit.getAreaCode());
		sbins.append("(item_uid,province_name,city_name,area_code,province_code,city_code) values(");
		sbins.append("'").append(unit.getItemUid()).append("'");
		sbins.append(",'").append(unit.getProvinceName()).append("'");
		sbins.append(",'").append(unit.getCityName()).append("'");
		sbins.append(",'").append(unit.getAreaCode()).append("'");
		sbins.append(",'").append(unit.getProvinceCode()).append("'");
		sbins.append(",'").append(unit.getCityCode()).append("')");
		sql.add(sbins.toString());
		StringBuilder sbdel = new StringBuilder("delete from mobile_qblib where id=").append(unit.getId());
		sql.add(sbdel.toString());
	    }
	    for (String libsql : sql) {
		System.out.println(libsql);
		dbStm.execute(libsql);
	    }
	    dbCon.commit();
	    sql.clear();
	    list.clear();
	    list = getDbList();
	}
	dbStm.close();
	dbCon.close();
	System.out.println("finish");
    }

    private static void qblibtabl() throws Exception {
	List<String> codeList = getAreaCodeList();
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	dbCon.setAutoCommit(true);
	for (String code : codeList) {
	    if (!"0517".equals(code)) {
		String sql = "create table mobile_qblib_" + code + " like mobile_qblib";
		dbStm.execute(sql);
		System.out.println(sql);
	    }
	}
	codeList.clear();
	dbStm.close();
	dbCon.close();
    }

    private static void qblibfile() throws Exception {
	Map<String, AreaUnit> areamap = getAreaMap();
	AreaUnit areaUnit = areamap.get("淮安");
	areamap.clear();
	// Set<String> set = getset(areaUnit.getAreaCode());
	List<String> sql = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	dbCon.setAutoCommit(true);
	String str;
	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:/temp/2016.06/jiangsu淮安_132.txt"),
		"UTF-8"));
	System.out.println("start");
	while ((str = br.readLine()) != null) {
	    String mobile = str.trim();
	    // if (!set.contains(mobile)) {
	    // set.add(mobile);
	    if (areaUnit != null) {
		StringBuilder sb = new StringBuilder("insert into mobile_qblib_").append(areaUnit.getAreaCode());
		sb.append("(item_uid,province_name,city_name,area_code,province_code,city_code) values(");
		sb.append("'").append(mobile).append("'");
		sb.append(",'").append(areaUnit.getProvinceName()).append("'");
		sb.append(",'").append(areaUnit.getCityName()).append("'");
		sb.append(",'").append(areaUnit.getAreaCode()).append("'");
		sb.append(",'").append(areaUnit.getProvinceCode()).append("'");
		sb.append(",'").append(areaUnit.getCityCode()).append("')");
		sql.add(sb.toString());
	    }
	    // }
	    for (String libsql : sql) {
		System.out.println(libsql);
		try {
		    dbStm.execute(libsql);
		} catch (Exception ex) {
		}
	    }
	    sql.clear();
	}
	dbStm.close();
	dbCon.close();
	br.close();
	System.out.println("finish");
    }

    private static void qblib() throws Exception {
	Map<String, AreaUnit> areamap = getarea();
	int imax = 789559;
	int ipos = 0;
	Set<String> set = getset("");
	System.out.println(set.size());
	String bizsql = "select distinct(item_uid) from biz_order where biz_id=100 order by id limit ";
	List<String> sql = new ArrayList<String>();
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	ResultSet dbRs = null;
	dbCon.setAutoCommit(true);
	while (ipos < imax) {
	    String cursql = bizsql + ipos + "," + (ipos + 1000);
	    dbRs = dbStm.executeQuery(cursql);
	    while (dbRs.next()) {
		String mobile = dbRs.getString(1);
		if (!set.contains(mobile)) {
		    set.add(mobile);
		    AreaUnit areaUnit = areamap.get(mobile.substring(0, 7));
		    if (areaUnit != null) {
			StringBuilder sb = new StringBuilder(
				"insert into mobile_qblib(item_uid,province_name,city_name,area_code,province_code,city_code) values(");
			sb.append("'").append(mobile).append("'");
			sb.append(",'").append(areaUnit.getProvinceName()).append("'");
			sb.append(",'").append(areaUnit.getCityName()).append("'");
			sb.append(",'").append(areaUnit.getAreaCode()).append("'");
			sb.append(",'").append(areaUnit.getProvinceCode()).append("'");
			sb.append(",'").append(areaUnit.getCityCode()).append("')");
			sql.add(sb.toString());
		    }
		}
	    }
	    ipos += 1000;
	    for (String libsql : sql) {
		System.out.println(libsql);
		dbStm.execute(libsql);
	    }
	    sql.clear();
	    System.out.println(ipos);
	}
	dbRs.close();
	dbStm.close();
	dbCon.close();
	System.out.println("finish");
    }

    private static Integer[] phoneKeyList = { 212, 241, 224, 271, 339, 212, 302, 261, 202, 204, 320, 261, 212, 339, 502 };

    private static void phonelib() throws Exception {
	Map<String, AreaUnit> areamap = getAreaMap();
	AreaUnit areaUnit = areamap.get("池州");
	areamap.clear();
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	dbCon.setAutoCommit(true);
	int maxNum = 9999;
	DecimalFormat format = new DecimalFormat("0000");
	System.out.println("start");
	for (int i = 0; i < phoneKeyList.length; i++) {
	    for (int p = 0; p <= maxNum; p++) {
		String mobile = areaUnit.getAreaCode() + phoneKeyList[i].toString() + format.format(p);
		StringBuilder sb = new StringBuilder("insert into phone_qblib");
		sb.append("(item_uid,province_name,city_name,area_code,province_code,city_code) values(");
		sb.append("'").append(mobile).append("'");
		sb.append(",'").append(areaUnit.getProvinceName()).append("'");
		sb.append(",'").append(areaUnit.getCityName()).append("'");
		sb.append(",'").append(areaUnit.getAreaCode()).append("'");
		sb.append(",'").append(areaUnit.getProvinceCode()).append("'");
		sb.append(",'").append(areaUnit.getCityCode()).append("')");

		System.out.println(sb.toString());
		try {
		    dbStm.execute(sb.toString());
		} catch (Exception ex) {
		}
	    }
	}
	dbStm.close();
	dbCon.close();
	System.out.println("finish");
    }

    private static List<QblibUnit> getDbList() {
	List<QblibUnit> list = new ArrayList<QblibUnit>();
	try {
	    Connection dbCon = getLocalDbCon();
	    String listsql = "select * from mobile_qblib limit 0,1000";
	    Statement dbStm = dbCon.createStatement();
	    dbCon.setAutoCommit(true);
	    ResultSet dbRs = dbStm.executeQuery(listsql);
	    while (dbRs.next()) {
		QblibUnit unit = new QblibUnit();
		unit.setId(dbRs.getLong(1));
		unit.setItemUid(dbRs.getString(2));
		unit.setProvinceName(dbRs.getString(3));
		unit.setCityName(dbRs.getString(4));
		unit.setAreaCode(dbRs.getString(5));
		unit.setProvinceCode(dbRs.getString(6));
		unit.setCityCode(dbRs.getString(7));
		list.add(unit);
	    }
	    dbRs.close();
	    dbStm.close();
	    dbCon.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return list;
    }

    private static Set<String> getset(String areaCode) {
	Set<String> set = new HashSet<String>();
	try {
	    int ipos = 0;
	    Connection dbCon = getLocalDbCon();
	    String chksql = "select item_uid from mobile_qblib_" + areaCode + " limit ";
	    Statement dbStm = dbCon.createStatement();
	    ResultSet dbRs = null;
	    dbCon.setAutoCommit(true);
	    boolean run = false;
	    do {
		run = false;
		String cursql = chksql + ipos + "," + (ipos + 1000);
		dbRs = dbStm.executeQuery(cursql);
		while (dbRs.next()) {
		    run = true;
		    String mobile = dbRs.getString(1);
		    set.add(mobile);
		}
		ipos += 1000;
	    } while (run);
	    dbRs.close();
	    dbStm.close();
	    dbCon.close();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return set;
    }

    private static Map<String, AreaUnit> getarea() throws Exception {
	Map<String, AreaUnit> areamap = new HashMap<String, AreaUnit>();
	String areasql = "select mobile_part,province_name,city_name,area_code,province_code,city_code from mobile_belong where carrier_type=1";
	Connection dbCon = getLocalDbCon();
	Statement dbStm = dbCon.createStatement();
	ResultSet dbRs = null;
	dbCon.setAutoCommit(true);
	dbRs = dbStm.executeQuery(areasql);
	while (dbRs.next()) {
	    AreaUnit areaUnit = new AreaUnit();
	    String mobilePart = dbRs.getString(1);
	    areaUnit.setMobilePart(mobilePart);
	    areaUnit.setProvinceName(dbRs.getString(2));
	    areaUnit.setCityName(dbRs.getString(3));
	    areaUnit.setAreaCode(dbRs.getString(4));
	    areaUnit.setProvinceCode(dbRs.getString(5));
	    areaUnit.setCityCode(dbRs.getString(6));
	    areamap.put(mobilePart, areaUnit);
	}
	dbRs.close();
	dbStm.close();
	dbCon.close();
	return areamap;
    }

    private static void blacklist() {
	try {
	    InputStream isIn = new FileInputStream("c:/黑名单09082.xlsx");
	    Workbook workbook = new XSSFWorkbook(isIn);
	    Connection dbCon = getLocalDbCon();
	    dbCon.setAutoCommit(true);
	    Statement dbStm = dbCon.createStatement();
	    Sheet sheet = workbook.getSheetAt(0);
	    int iRowLen = sheet.getLastRowNum();
	    for (int iRow = 0; iRow < iRowLen; iRow++) {
		Row row = sheet.getRow(iRow);
		if (row.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
		    row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
		}
		String uid = row.getCell(0).getStringCellValue().trim();
		String city = row.getCell(2).getStringCellValue().replace(" ", "");
		if (containBlack(dbStm, uid)) {
		    // System.out.println(">0");
		    continue;
		}
		insertBalck(dbStm, uid, city);
		System.out.println(uid + ":" + city);
	    }
	    dbStm.close();
	    dbCon.close();
	    isIn.close();
	    System.out.println("finish");
	} catch (Exception ex) {
	    ex.printStackTrace(System.out);
	}
    }

    private static boolean containBlack(Statement dbStm, String uid) throws Exception {
	ResultSet rsFld = dbStm.executeQuery("select count(1) from black_list where item_uid='" + uid + "'");
	int num = 0;
	if (rsFld.next()) {
	    num = rsFld.getInt(1);
	}
	rsFld.close();
	return num > 0;
    }

    private static void insertBalck(Statement dbStm, String uid, String city) throws Exception {
	StringBuilder sb = new StringBuilder("insert into black_list");
	sb.append("(item_uid,city_name) values(");
	sb.append("'").append(uid).append("'");
	sb.append(",'").append(city).append("')");
	boolean run = dbStm.execute(sb.toString());
	if (!run) {
	    System.out.println(uid + ":" + city);
	}
    }

    public static class AreaUnit {
	private String provinceCode = null;
	private String cityCode = null;
	private String provinceName = null;
	private String cityName = null;
	private String areaCode = null;
	private String mobilePart = null;

	public AreaUnit() {
	}

	public AreaUnit(String provinceCode, String cityCode, String provinceName, String cityName, String areaCode) {
	    this.provinceCode = provinceCode;
	    this.cityCode = cityCode;
	    this.provinceName = provinceName;
	    this.cityName = cityName;
	    this.areaCode = areaCode;
	}

	public String getProvinceCode() {
	    return provinceCode;
	}

	public String getCityCode() {
	    return cityCode;
	}

	public String getProvinceName() {
	    return provinceName;
	}

	public String getCityName() {
	    return cityName;
	}

	public String getAreaCode() {
	    return areaCode;
	}

	public String getMobilePart() {
	    return mobilePart;
	}

	public void setMobilePart(String mobilePart) {
	    this.mobilePart = mobilePart;
	}

	public void setProvinceCode(String provinceCode) {
	    this.provinceCode = provinceCode;
	}

	public void setCityCode(String cityCode) {
	    this.cityCode = cityCode;
	}

	public void setProvinceName(String provinceName) {
	    this.provinceName = provinceName;
	}

	public void setCityName(String cityName) {
	    this.cityName = cityName;
	}

	public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode;
	}
    }

    public static class QblibUnit {
	private Long id;
	private String itemUid;
	private String provinceName;
	private String cityName;
	private String areaCode;
	private String provinceCode;
	private String cityCode;

	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
	}

	public String getItemUid() {
	    return itemUid;
	}

	public void setItemUid(String itemUid) {
	    this.itemUid = itemUid;
	}

	public String getProvinceName() {
	    return provinceName;
	}

	public void setProvinceName(String provinceName) {
	    this.provinceName = provinceName;
	}

	public String getCityName() {
	    return cityName;
	}

	public void setCityName(String cityName) {
	    this.cityName = cityName;
	}

	public String getAreaCode() {
	    return areaCode;
	}

	public void setAreaCode(String areaCode) {
	    this.areaCode = areaCode;
	}

	public String getProvinceCode() {
	    return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
	    this.provinceCode = provinceCode;
	}

	public String getCityCode() {
	    return cityCode;
	}

	public void setCityCode(String cityCode) {
	    this.cityCode = cityCode;
	}
    }
}
