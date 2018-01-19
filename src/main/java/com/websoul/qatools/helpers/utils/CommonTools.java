package com.websoul.qatools.helpers.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import cucumber.api.DataTable;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.qameta.allure.Attachment;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: mvlastos
 * Date: 11/3/16
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("CommonTools")
public class CommonTools {

//    @Autowired
//    CrunchifyGetMD5ForFile crunchifyGetMD5ForFile;

    private final Logger slf4jLogger = LoggerFactory.getLogger(CommonTools.class);

    private static StopWatch stopWatch;
    private Object MD5;
    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String resultsForProperty = "";

    public static void gzipFile(String source_filepath, String destinaton_zip_filepath) {

        byte[] buffer = new byte[1024];

        try {

            FileOutputStream fileOutputStream = new FileOutputStream(destinaton_zip_filepath);

            GZIPOutputStream gzipOuputStream = new GZIPOutputStream(fileOutputStream);

            FileInputStream fileInput = new FileInputStream(source_filepath);

            int bytes_read;

            while ((bytes_read = fileInput.read(buffer)) > 0) {
                gzipOuputStream.write(buffer, 0, bytes_read);
            }

            fileInput.close();

            gzipOuputStream.finish();
            gzipOuputStream.close();

            System.out.println("The file was compressed successfully!");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public static void deleteFile(String filepath) {

        try {

            File file = new File(filepath);

            if (file.delete()) {
                System.out.println(file.getAbsolutePath() + " is deleted!");
            } else {
                System.out.println(file.getAbsolutePath() + " Delete operation is failed.");
            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public static void goToSleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static int slideBackDays(int days) {
        return days * -86400;
    }

    public static int slideBackHours(int hours) {
        return hours * -3600;
    }

    @Attachment(value = "Datatable", type = "text/plain")
    public static String attachDataTable(DataTable dataTable) {
        return CommonTools.convertMapToString(dataTable.asMap(String.class, String.class));
    }

    @Attachment(value = "Datatable", type = "text/plain")
    public static String attachDataTableMulti(DataTable dataTable) {
        return dataTable.toString();
    }

    @Attachment(value = "Runtime Logs", type = "text/plain")
    public static String attachLogs(String logs) {
        return logs;
    }

    public static String getResultsForProperty() {
        return resultsForProperty;
    }

    public static void setResultsForProperty(String message) {
        resultsForProperty += message;
    }

    @Attachment(value = "Video Link", type = "text/html")
    public String getVideoLinK(String videoLink) {
        return "<a href=\"" + videoLink + "\"> Press here to see the video for the test case in a new tab</a>";
    }

    @Attachment(value = "Results", type = "text/plain")
    public static String attachResults(String results) {
        return results;
    }

    @Attachment(value = "Visual Diff/Annotaded Diff", type = "text/html")
    public static String attachHmtlResults(String htmlResults) {
        return htmlResults;
    }


    public static Document loadXMLFromString(String xml) throws Exception {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setCoalescing(true);
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
            NodeList list = doc.getElementsByTagName("*");
            System.out.println("XML Elements: ");
            for (int i = 0; i < list.getLength(); i++) {
                // Get element
                Element element = (Element) list.item(i);
                System.out.println(element.getNodeName());

                if (element.getNodeName() == "xmlResponse") {
                    System.out.println("Status response: " + element.getAttributeNode("status").getValue());
                    NamedNodeMap childs = element.getAttributes();
                    //System.out.println(childs.getLength());
                    for (int k = 0; k < childs.getLength(); k++) {
                        //System.out.println(childs.item(k).getNodeValue());
                        if (childs.item(k).getNodeValue() == "errorcode")
                            System.out.println("Error code:" + childs.item(k).getNodeValue());
                        if (childs.item(k).getNodeValue() == "password")
                            System.out.println("Password:" + childs.item(k).getNodeValue());
                    }
                }
                if (element.getNodeName() == "param") {
                    System.out.println(element.getNodeValue());//.getTextContent());
                    System.out.println("Status param: " + element.getAttributeNode("name").getValue() + "     " + element.getNodeValue());
/*                    NamedNodeMap childs = element.getAttributes();
                    System.out.println(childs.getLength());


                    for (int k = 0; k < childs.getLength(); k++) {
                        System.out.println(childs.item(k).getNodeValue());
                        if (childs.item(k).getNodeValue() == "errorcode")
                            System.out.println("Error code:" + childs.item(k).getNodeValue() );
                        if (childs.item(k).getNodeValue()== "password")
                            System.out.println("Password:" + childs.item(k).getNodeValue() );
                    }*/
                }
            }

        } catch (ParserConfigurationException e)

        {
            return null;
        } catch (SAXException e)

        {
            return null;
        } catch (IOException e)

        {
            return null;
        }

        return doc;
    }

    /**
     * @return A value between 0 and group-1.
     */
    public static long md5Hash(String str, long group) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            byte[] data = str.getBytes();
            m.update(data, 0, data.length);
            BigInteger bi = new BigInteger(1, m.digest());
            BigInteger i = bi.mod(BigInteger.valueOf(Long.valueOf(group)));
            return i.longValue();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return 0;
        } catch (ArithmeticException ex) {
            return 0;
        }
    }

    public static String getTime() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    public static String getTimeWithOffset(int offset) {
        return String.valueOf(Calendar.getInstance().getTimeInMillis() + offset);
    }

    public static int getCurrentSecondsOfTheDay() {
        DateMidnight midnight = new DateMidnight();
        DateTime now = new DateTime();
        Duration duration = new Duration(midnight, now);
        return duration.toStandardSeconds().getSeconds();
    }

    public static int getCurrentDayOfTheWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.DAY_OF_WEEK);
    }

    public static int getOffsetForHourOfDay(int shiftHourTarget) {
        shiftHourTarget = shiftHourTarget * 3600;
        return CommonTools.getCurrentSecondsOfTheDay() - shiftHourTarget;
    }

    public static int getOffsetForStartOfWeek(int dayOfWeek) {
        int offset = -((dayOfWeek - 1) * 86400) + (CommonTools.getCurrentSecondsOfTheDay());
        offset = offset + (86400 * 6);
        return offset;
    }

    /*
    * This methods shifts the time to the previous week at the day of dayToShift parameter
    * * @param  timezone
    *           The Timezone of the user
     * @param  dayToShift
    *           The day of Week to shift in int format - Monday=1/Sunday=7
    * */
    public static int getOffsetForDayOfWeek(String timezone, int dayToShift) {
        int offset;
        DateTimeZone zone = DateTimeZone.forID(timezone);
        DateTime dt = new DateTime(zone);
        int days = dt.getDayOfWeek();
        offset = ((dayToShift - days) * 86400) - 604800;
        return offset;
    }

    public static int getOffsetForDayOfMonth(String timezone, int dayofMonthToShift) {
        int offset;
        DateTimeZone zone = DateTimeZone.forID(timezone);
        DateTime dt = new DateTime(zone);
        int days = dt.getDayOfMonth();
        offset = ((dayofMonthToShift - days) * 86400) - 2592000;
        return offset;
    }

    public static Map<String, Integer> getDays() {
        Map<String, Integer> mapDays = new HashMap<String, Integer>();
        mapDays.put("Sunday", 1);
        mapDays.put("Monday", 2);
        mapDays.put("Tuesday", 3);
        mapDays.put("Wednesday", 4);
        mapDays.put("Thursday", 5);
        mapDays.put("Friday", 6);
        mapDays.put("Saturday", 7);
        return mapDays;
    }

    public static ArrayList<String> stringDeserializerSingleDelimiter(String response, String parameterDelimiter) {
        ArrayList<String> listOfParameters = new ArrayList<String>();
        StringTokenizer parametersTokenizer = new StringTokenizer(response, parameterDelimiter);
        while (parametersTokenizer.hasMoreTokens()) {
            listOfParameters.add(parametersTokenizer.nextToken());
        }
        return listOfParameters;
    }

    public static Map<String, String> stringDeserializerDoubleDelimiter(String response, String parameterDelimiter, String keyValueDelimiter) {
        Map<String, String> keyValueParametersMap = new HashMap<String, String>();
        StringTokenizer parametersTokenizer = new StringTokenizer(response, parameterDelimiter);
        while (parametersTokenizer.hasMoreTokens()) {
            String token = parametersTokenizer.nextToken();
            StringTokenizer keyValueTokenizer = new StringTokenizer(token, keyValueDelimiter);
            if (keyValueTokenizer.countTokens() != 2) {
                throw new RuntimeException("Invalid format of key value");
            }
            keyValueParametersMap.put(keyValueTokenizer.nextToken(), keyValueTokenizer.nextToken());
        }
        return keyValueParametersMap;
    }

    public static int getPositionOfKeyInDataTable(DataTable dataTable, String key) {
        int i = 0;
        while (!dataTable.getGherkinRows().get(i).getCells().get(0).equals(key)) {
            i++;
        }
        return i;
    }

    public static boolean isKeyExistingHorizontal(DataTable dataTable, String key) {
        boolean exists = false;
        int i = 0;
        while (i < dataTable.getGherkinRows().get(0).getCells().size()) {
            if (dataTable.getGherkinRows().get(0).getCells().get(i).equals(key)) {
                exists = true;
            }
            i++;
        }
        return exists;
    }

    public static String getValueOfKeyInDataTable(DataTable dataTable, String key) {
        int i = 0;
        while (!dataTable.getGherkinRows().get(i).getCells().get(0).equals(key)) {
            i++;
        }
        return dataTable.getGherkinRows().get(i).getCells().get(1);
    }

    public static String getValueOfKeyInDataTableHorizontal(DataTable dataTable, String key, int row) {
        int i = 0;
        while (!dataTable.getGherkinRows().get(0).getCells().get(i).equals(key)) {
            i++;
        }
        return dataTable.getGherkinRows().get(row + 1).getCells().get(i);
    }

    public static boolean isKeyExistingVertical(DataTable dataTable, String key) {
        int j = 0;
        while (j < dataTable.getGherkinRows().size()) {
            if (dataTable.getGherkinRows().get(0).getCells().get(j).equals(key)) {
                return true;
            }
            j++;
        }
        return false;
    }

    public static String getSingleValueOfKeyInDataTableVertical(DataTable dataTable, String key) {
        int i = 0;
        while (!dataTable.getGherkinRows().get(i).getCells().get(0).equals(key)) {
            i++;
        }
        return dataTable.getGherkinRows().get(i).getCells().get(1);
    }

    public static List<String> getValueSetOfKeyInDataTableVertical(DataTable dataTable, String key) {
        int j = 0;
        while (!dataTable.getGherkinRows().get(0).getCells().get(j).equals(key)) {
            j++;
        }
        int i = 1;
        List<String> valueSet = new ArrayList<String>();
        while (i < dataTable.getGherkinRows().size()) {
            valueSet.add(dataTable.getGherkinRows().get(i).getCells().get(j));
            i++;
        }
        return valueSet;
    }

    public static int getIdForDayOfWeek(String day) {
        assert day != null;
        final String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++)
            if (days[i].equalsIgnoreCase(day)) return i + 1;
        return -1;
    }

    /* The following method counts the total days of months for different months (28 /31 /30 days per month).
     * The method is not properly working for leap years.
     * */
    public static int getTotalDays(int nofMonths) {
        int totalDays = 0;
        Calendar cPrevious = Calendar.getInstance();
        for (int i = 0; i < nofMonths; i = i + 1) {
            cPrevious.roll(Calendar.MONTH, false);
            int temp = Calendar.DAY_OF_MONTH + (cPrevious.getActualMaximum(cPrevious.DAY_OF_MONTH) - cPrevious.DAY_OF_MONTH);
            totalDays = totalDays + temp;
        }
        return totalDays;
    }

    public static String getValueFromJson(String json, String key) {
        json = json.replaceAll(" ", "").replace("\n", "").replace("\r", "");
        Pattern pattern = Pattern.compile("\"" + key + "\":(.*),\"");
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
//            /System.out.println(matcher.group(1).split(",")[0]);
            return matcher.group(1).split(",")[0];
        } else {
            return null;
        }
    }

    public static String getCorrectAnswerForSkip(String jsonAnswers, String key) {
        Object obj = JSONValue.parse(jsonAnswers);
        JSONArray array = (JSONArray) obj;
        String result = null;
        for (int i = 1; i <= array.size(); i++) {
            String element = array.get(i - 1).toString();
            if (element.contains(key)) {
                result = String.valueOf(i);
            }
        }
        return result;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static void printMap(Map<?, ?> results) {
        Iterator it = results.entrySet().iterator();
        while (it.hasNext()) {
            Entry pairs = (Entry) it.next();
            System.out.println(pairs.getKey() + " = " + pairs.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    public static boolean evaluateMap(Map<?, ?> results, String desiredResult) {
        boolean result = true;
        Iterator it = results.entrySet().iterator();
        while (it.hasNext()) {
            Entry pairs = (Entry) it.next();
            if (!pairs.getValue().toString().equals(desiredResult)) {
                result = false;
                break;
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        return result;
    }

    public static boolean wordCharactersExistInCharacterSet(String word, String characterSet) {
        boolean valid = true;
        int length = word.length();
        int i = 0;
        while (i < length && valid) {
            valid = characterSet.toUpperCase().contains(word.substring(i, i + 1).toUpperCase());
            i++;
        }
        return valid;
    }

    public static String getValueFromStringWithDelimiterBasedOnPattern(String text, String pattern, String prefixDelimiter, String suffixDelimiter) {
        Pattern patternRegex = Pattern.compile(pattern + prefixDelimiter + "(.*?)" + suffixDelimiter);
        Matcher matcher = patternRegex.matcher(text);
        String value = null;
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }

    public static Map<String, String> getStringDiff(String original, String replaced, String delimiter) {
        Map<String, String> results = new HashMap<String, String>();
        if (original.split(delimiter) != null && replaced.split(delimiter) != null) {
            for (int i = 0; i < replaced.split(delimiter).length; i++) {
                if (!replaced.split(delimiter)[i].equals(original.split(delimiter)[i])) {
                    results.put(original.split(delimiter)[i], replaced.split(delimiter)[i]);
                }
            }
        }
        return results;
    }

    public static boolean compareMaps(Map<String, String> original, Map<String, String> replaced) {
        Set<String> values1 = new HashSet<String>(original.values());
        Set<String> values2 = new HashSet<String>(replaced.values());
        return values1.equals(values2);
    }

    public static Map<String, String> convert2ColumnDatatableToMap(DataTable dt) {
        Map<String, String> results = new HashMap<String, String>();
        for (int i = 0; i < dt.getGherkinRows().size(); i++) {
            results.put(dt.getGherkinRows().get(i).getCells().get(0), dt.getGherkinRows().get(i).getCells().get(1));
        }
        return results;
    }

    public static Map<String, String> convert2RowDatatableToMap(DataTable dt) {
        Map<String, String> results = new HashMap<String, String>();
        for (int i = 0; i < dt.getGherkinRows().size(); i++) {
            results.put(dt.getGherkinRows().get(0).getCells().get(i), dt.getGherkinRows().get(1).getCells().get(i));
        }
        return results;
    }

    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();

    public static String asciiToHex(String msgParameter) {
        Charset charset = Charset.forName("UnicodeBigUnmarked");
        CharsetEncoder encoder = charset.newEncoder();
        try {
            ByteBuffer bbuf;

            bbuf = encoder.encode(CharBuffer.wrap(msgParameter));

            byte[] barray = new byte[bbuf.limit()];
            bbuf.get(barray, 0, bbuf.limit());

            char[] data = Hex.encodeHex(barray);
            StringBuffer strBuff = new StringBuffer();
            int len = data.length;
            for (int i = 0; i < len; ++i) {
                if (i % 2 == 0) strBuff.append("%");

                strBuff.append(data[i]);
            }
            msgParameter = strBuff.toString();
        } catch (CharacterCodingException e) {
            System.err.println(e.getMessage());
        }
        return msgParameter;

    }

    public static String convertMapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(System.getProperty("line.separator"));
            }
            String value = map.get(key);
            stringBuilder.append((key != null ? key : ""));//URLEncoder.encode(key, "UTF-8") : ""));
            stringBuilder.append("=");
            stringBuilder.append((value != null ? value : "")); //URLEncoder.encode(value, "UTF-8") : "");
        }

        return stringBuilder.toString();
    }

    public static Map<String, String> convertStringToMap(String toMap) {
        return Splitter.on("|").withKeyValueSeparator("_").split(toMap);
    }

    public static void createStopWatch(String stopwatchName) {
        stopWatch = new StopWatch(stopwatchName);
    }

    public static void startStopwatchForTask(String taskName) {
        if (stopWatch != null) {
            stopStopwatch();
            stopWatch.start(taskName);
        }
    }

    public static void stopStopwatch() {
        if (stopWatch != null && stopWatch.isRunning()) stopWatch.stop();
    }

    public static boolean isStopwatchRunning() {
        if (stopWatch == null) return false;
        else return true;
    }


    public static String getTasksTimesInPrettyFormat() {
        if (isStopwatchRunning()) return stopWatch.prettyPrint();
        else return "No performance metrics found";
    }

    public static double getTasksTotalTimeInseconds() {
        return stopWatch.getTotalTimeSeconds();
    }

    public static long getTasksTotalTimeInMillis() {
        return stopWatch.getTotalTimeMillis();
    }

    public static void printMetricsFromTable(Table metricsTable) {
        Table<String, Long, Long> finalDiffs = HashBasedTable.create();

        //Todo: functionize below code to be reusable
        System.out.println("\n---[Find all metrics for each corId]-----");

        Map<String, Map<String, String>> map = metricsTable.rowMap();

//        List<Map<String,String> > finalDiffs = new ArrayList<Map<String,String>>();


        for (String row : map.keySet()) {
            Map<String, String> tmp = map.get(row);
            Set<Entry<String, String>> entries = tmp.entrySet();

            Comparator<Entry<String, String>> valueComparator = new Comparator<Entry<String, String>>() {
                @Override
                public int compare(Entry<String, String> e1, Entry<String, String> e2) {
                    String v1 = e1.getValue();
                    String v2 = e2.getValue();
                    return v1.compareTo(v2);
                }
            };
            List<Entry<String, String>> listOfEntries = new ArrayList<Entry<String, String>>(entries);
            Collections.sort(listOfEntries, valueComparator);
            LinkedHashMap<String, String> sortedByValue = new LinkedHashMap<String, String>(listOfEntries.size());
            LinkedHashMap<String, String> sortedByValueWithDiffs = new LinkedHashMap<String, String>(listOfEntries.size());

            // copying entries from List to Map
            for (Entry<String, String> entry : listOfEntries) {
                sortedByValue.put(entry.getKey(), entry.getValue());
            }

            List<String> keys = new ArrayList<String>(sortedByValue.keySet());
            List<String> values = new ArrayList<String>(sortedByValue.values());


            for (int i = 0; i < keys.size(); i++) {
                if (i == 0) sortedByValueWithDiffs.put(keys.get(i), values.get(i));
                else {
                    sortedByValueWithDiffs.put(keys.get(i), String.valueOf(Long.parseLong(values.get(i)) - Long.parseLong(values.get(i - 1))));

                    finalDiffs.put(keys.get(i), Long.parseLong(values.get(i)) - Long.parseLong(values.get(i - 1)), Long.parseLong(values.get(i)) - Long.parseLong(values.get(i - 1)));
                }
            }

            //System.out.println("\nHashMap after sorting entries by values ");
            Set<Entry<String, String>> entrySetSortedByValue = sortedByValue.entrySet();
//            for (Entry<String, String> mapping : entrySetSortedByValue) {
//                System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
//            }
            System.out.println("\nCorrelation ID " + row);
            entrySetSortedByValue = sortedByValueWithDiffs.entrySet();
            for (Entry<String, String> mapping : entrySetSortedByValue) {
                System.out.println(mapping.getKey() + " ==> " + mapping.getValue());
            }
        }


        //Find average
        Map<String, Map<Long, Long>> mapDiffs = finalDiffs.rowMap();
        StringBuilder finalResult = new StringBuilder();
        for (String row : mapDiffs.keySet()) {
            long diffSum = 0;
            Map<Long, Long> tmp = mapDiffs.get(row);

            Set<Entry<Long, Long>> entries1 = tmp.entrySet();

            List<Entry<Long, Long>> listOfEntries1 = new ArrayList<Entry<Long, Long>>(entries1);
            for (Entry<Long, Long> entry : listOfEntries1) {
                diffSum += entry.getValue();
            }
            finalResult.append("\nTopic: " + row);
            finalResult.append("\nAverage time : " + diffSum / listOfEntries1.size());
            finalResult.append("\nMaximum time : " + Collections.max(tmp.values(), null));
        }

        attachLogs(finalResult.toString());

    }

    public static int getMonthInNumber() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int getYearInNumber() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }


    static public String getMD5ForString(String original) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(original.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void writeFile(String fileContent, String filename) throws IOException {
        File resultsFile = new File("" + filename + ".txt");

        if (resultsFile.exists()) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFile, false));
            writer.write(fileContent);
            writer.close();
        } else {
            BufferedWriter writer = new BufferedWriter(new FileWriter(resultsFile, true));
            writer.write(fileContent);
            writer.close();
        }

    }

    public void saveResponse(String response, String serviceName, String uid) throws IOException {
        FileUtils.writeStringToFile(new File("src/test/resources/quality/" + serviceName + "_" + uid + ".json"), response);
    }

    public String getJsonBodyFromFile(String bodyFile, String type) throws IOException {
        File jsonFile = null;

        jsonFile = new File("src/test/resources/apis/" + type + "/" + bodyFile);

        slf4jLogger.info("JSON File loaded: " + jsonFile);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile));
        String line;
        StringBuilder jsonString = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            jsonString.append(line);
        }
        return jsonString.toString();
    }

    public String encodeJWT(String jwtKey, String userID, String userName) throws UnsupportedEncodingException {

        String s = Jwts.builder().claim("user_id", userID).claim("user_name", userName).claim("issuer", "Workable").claim("issued_at", new GregorianCalendar().getTime()).claim("device", "5s").signWith(SignatureAlgorithm.HS256, jwtKey.getBytes("UTF-8")).compact();
        slf4jLogger.info("JWT token created : {}", s);

        return s;
    }

    public String encodeJWTHEader(String secret) throws UnsupportedEncodingException {
        slf4jLogger.info("Iat " + System.currentTimeMillis());
        slf4jLogger.info("Exp " + System.currentTimeMillis() + TimeUnit.DAYS.toMillis(100));
        String s = Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(100))).signWith(SignatureAlgorithm.HS256, secret.getBytes("UTF-8"))

                .compact();
        slf4jLogger.info("JWT token created : {}", s);

        return s;
    }

    public String generateRandomString(int length) {

        StringBuffer randStr = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    private int getRandomNumber() {
        int randomInt = 0;
        Random randomGenerator = new Random();
        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    public static double roundDouble(double value) {
        return Math.round(value * 100.0) / 100.0;
    }


    public static void main(String args[]) throws IOException {
        CommonTools.writeFile("sdasdadsasd", "hello");
    }

    public static void printBeanNames(ApplicationContext context){
        String[] beanNames = context.getBeanDefinitionNames();
        Arrays.stream(beanNames)
                .filter(n -> !n.contains("springframework"))
                .forEach(System.out::println);
    }
}
