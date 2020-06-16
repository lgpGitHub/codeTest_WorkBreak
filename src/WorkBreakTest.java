import java.util.*;

/**
 * 字符串匹配
 */
public class WorkBreakTest {

    public static final String[] defaultStringArr = {"i", "like", "sam", "sung", "samsung", "mobile", "ice", "cream", "man go"};

    /**
     * 获取移动数
     *
     * @param c      查找的字符
     * @param target 需要匹配的字符集合
     * @return 移动数
     */
    public static int getMoveValue(char c, char[] target) {
        int len = target.length;
        //如果匹配到最后一位
        if (c == target[len - 1]) {
            return len;
        }
        for (int i = len; i >= 1; i--) {
            if (target[i - 1] == c)
                return len - i;
        }
        return len;
    }

    /**
     * 获取字段串出现的位置
     *
     * @param targetString 完整的字符串
     * @param searchString 需要匹配的字符串
     * @return 字符串出现位置，-2表示出错，-1表示不存在
     */
    public static int getIndex(String targetString, String searchString) {
        if (("".equals(searchString) || null == searchString) || ("".equals(targetString) || null == targetString)) {
            return -2;//字符串不能为空
        }

        //去掉空格匹配
        char[] tCharArr = targetString.replaceAll(" ","").toCharArray();
        char[] sCharArr = searchString.replaceAll(" ","").toCharArray();

        int t_length = tCharArr.length;
        int s_length = sCharArr.length;

        //完整字符串比需要匹配的字符串短，说明一定不存在
        if (t_length < s_length) {
            return -1;
        }
        int i = s_length, j;

        while (i <= t_length) {
            j = s_length;
            //从后匹配，如果相同，则比较下一个
            while (j > 0 && tCharArr[i - 1] == sCharArr[j - 1]) {
                j--;
                i--;
            }
            //如果完全一致，则返回当前值
            if (0 == j) {
                return i;
            } else {
                //不一致，则将完整的右移移动量后再重新匹配
                i = i + getMoveValue(tCharArr[i - 1], sCharArr);
            }
        }
        return -1;
    }

    /**
     * 获取结果语句
     *
     * @param type            匹配模式
     * @param targetString    目标字符串
     * @param newSearchString 新的词典字符串
     * @return
     */
    public static String getResultString(String type, String targetString, String newSearchString) {
        String[] searchStrArr = newSearchString.split(",");
        String finalSearchString = "";
        Map<Integer, String> resultMap = new HashMap<>();
        switch (type) {
            case "1":
                finalSearchString = StringArrToString(defaultStringArr,",");
                resultMap.putAll(getResultMap(defaultStringArr, targetString));
                break;
            case "2":
                finalSearchString = newSearchString;
                resultMap.putAll(getResultMap(searchStrArr, targetString));
                break;
            case "3":
                List<String> tempList =  new ArrayList<>();
                tempList.addAll(Arrays.asList(defaultStringArr));
                for (int i = 0; i < searchStrArr.length; i++) {
                    if(!tempList.contains(searchStrArr[i])){
                        tempList.add(searchStrArr[i]);
                    }
                }
                String[] newSearchStrArr = new String[tempList.size()];
                tempList.toArray(newSearchStrArr);
                finalSearchString = StringArrToString(newSearchStrArr,",");
                resultMap.putAll(getResultMap(newSearchStrArr, targetString));
                break;
            default:
                return "无匹配值";
        }
        String[] finalSearchStrArr = finalSearchString.split(",");
        //排序输出
        if (null != resultMap && !resultMap.isEmpty()) {
            StringBuffer resultString = new StringBuffer();
            List<Integer> sortList = new ArrayList<>();
            Map<Integer, String> matchMap = new HashMap<>();
            List<String> resultList = new ArrayList<>();
            for (Map.Entry<Integer, String> entry : resultMap.entrySet()) {
                int key = entry.getKey();
                String value = entry.getValue();
                if (key < 0) {
                    matchMap.put(key,value);
                } else {
                    sortList.add(key);
                }
            }
            int mapSize = matchMap.size();
            //获取排列组合
            List<String> tempKeyList = new ArrayList<>();
            List<String> tempValueList = new ArrayList<>();
            for (Map.Entry<Integer, String> entry1 : matchMap.entrySet()) {
                String key = String.valueOf(Math.abs(entry1.getKey()));
                String value = entry1.getValue().replace(key+",", "");
                tempKeyList.add(key);
                tempValueList.add(value);
            }
            getResultList(tempKeyList.size(), resultList);

            for (int i = 0; i < resultList.size(); i++) {
                List<Integer> tempList = new ArrayList<>();
                tempList.addAll(sortList);
                String replaceKey = resultList.get(i);
                for (int j = 0; j < tempKeyList.size(); j++) {
                    String tempReplaceKey = replaceKey.substring(j,j+1);
                    String tempKey = tempKeyList.get(j);
                    String tempValue = tempValueList.get(j);

                    if("1".equals(tempReplaceKey)){
                        String[] tempKeyArr = tempKey.split(",");
                        int index_0 = tempList.indexOf(Integer.valueOf(tempKeyArr[0]));
                        if(index_0 >= 0){
                            tempList.remove(index_0);
                        }
                        if(tempKeyArr.length > 1){
                            int index_1 = tempList.indexOf(Integer.valueOf(tempKeyArr[1]));
                            if(index_1 >= 0){
                                tempList.remove(index_1);
                            }
                        }
                        String[] tempValueArr = tempValue.split(",");
                        int value_0 = Integer.valueOf(tempValueArr[0]);
                        if(!tempList.contains(value_0)){
                            tempList.add(value_0);
                        }
                        if(tempValueArr.length > 1){
                            int value_1 = Integer.valueOf(tempValueArr[1]);
                            if(!tempList.contains(value_1)){
                                tempList.add(value_1);
                            }
                        }
                    }else{
                        String[] tempKeyArr = tempKey.split(",");
                        int index_0 = Integer.valueOf(tempKeyArr[0]);
                        if(!tempList.contains(index_0)){
                            tempList.add(index_0);
                        }
                        if(tempKeyArr.length > 1){
                            int index_1 = Integer.valueOf(tempKeyArr[1]);
                            if(tempList.contains(index_1)){
                                tempList.add(index_1);
                            }
                        }
                        String[] tempValueArr = tempValue.split(",");
                        int value_0 = tempList.indexOf(Integer.valueOf(tempValueArr[0]));
                        if(value_0 >= 0){
                            tempList.remove(value_0);
                        }
                        if(tempValueArr.length > 1){
                            int value_1 = tempList.indexOf(Integer.valueOf(tempValueArr[1]));
                            if(value_1 >= 0){
                                tempList.remove(value_1);
                            }
                        }
                    }
                }

                Collections.sort(tempList);
                StringBuffer newBf = new StringBuffer();
                for (int j = 0; j < tempList.size(); j++) {
                    int sortNum = tempList.get(j);
                    newBf.append(finalSearchStrArr[sortNum]).append(" ");
                }
                if(!resultString.toString().contains(newBf.toString())){
                    resultString.append(newBf);
                    resultString.append("\n");
                }
            }
            resultString.insert(0,"匹配结果为：\n");
            return resultString.toString();
        } else {
            return "无匹配值";
        }
    }

    /**
     * string数组转String
     * @param strArr 需要转换的数组
     * @param token 分隔符
     */
    public static String StringArrToString(String[] strArr, String token){
        StringBuffer sb = new StringBuffer();
        if(null == token || "".equals(token)){
            token = ",";
        }
        if(strArr.length > 0){
            for (String string:
            strArr) {
                if(null != string && !"".equals(string)){
                    if(sb.length() > 0){
                        sb.append(token).append(string);
                    }else{
                        sb.append(string);
                    }
                }
            }
        }
        if(sb.length() > 0){
            return sb.toString();
        }else{
            return null;
        }
    }

    /**
     * 递归获取排序组合
     * @param keySize
     * @param resultList
     * @return
     */
    public static List<String> getResultList(int keySize, List<String> resultList){
        int totalCount = (int) Math.pow(2,keySize);
        for (int i = 0; i < totalCount; i++) {
            StringBuilder tempValue = new StringBuilder(Integer.toBinaryString(i));
            int len = tempValue.length();
            if(len < keySize){
                for (int j = 0; j < (keySize-len); j++) {
                    tempValue.insert(0, "0");
                }
            }
            resultList.add(tempValue.toString());
        }
        return resultList;
    }

    /**
     * 获取匹配结果
     *
     * @param searchStrArr 需要匹配字符串
     * @param targetString 完整的字符串
     * @return 结果集合
     */
    public static Map<Integer, String> getResultMap(String[] searchStrArr, String targetString) {
        Map<Integer, String> resultMap = new HashMap<>();
        List<String> resultStrList = new ArrayList<>();
        for (int i = 0; i < searchStrArr.length; i++) {
            int index = getIndex(targetString, searchStrArr[i]);
            if (-2 == index) {
                return null;
            } else if (0 <= index) {
                if (isExistsStr(searchStrArr, searchStrArr[i])) {
                    String indexString = String.valueOf(i);
                    for (int j = 0; j < searchStrArr.length; j++) {
                        if(searchStrArr[j].length() > 1){
                            int containIndex = searchStrArr[i].indexOf(searchStrArr[j]);
                            if (containIndex >= 0 && containIndex != index && !searchStrArr[j].equals(searchStrArr[i])) {
                                indexString = indexString + "," + j;
                                resultMap.put((0 - i), indexString);
                            }else if(searchStrArr[j].contains(" ") && searchStrArr[j].replaceAll(" ", "").equals(searchStrArr[i])){
                                indexString = indexString + "," + j;
                                resultMap.put((0 - i), indexString);
                            }
                        }
                    }
                } else {
                    resultStrList.add(searchStrArr[i]);
                    resultMap.put(i, searchStrArr[i]);
                }
            }
        }
        return resultMap;
    }

    /**
     * 是否包含字符串
     * @param searchStrArr 匹配词典
     * @param str 匹配字符串
     * @return 是否存在
     */
    public static boolean isExistsStr(String[] searchStrArr, String str) {
        boolean preMatch = false;
        boolean suffMatch = false;
        for (int i = 0; i < searchStrArr.length; i++) {
            if(str.equals(searchStrArr[i])){
                continue;
            }
            if(str.startsWith(searchStrArr[i]) && !str.endsWith(searchStrArr[i])){
                preMatch = true;
            }
            if(!str.startsWith(searchStrArr[i]) && str.endsWith(searchStrArr[i])){
                suffMatch = true;
            }
            if(!suffMatch && !suffMatch && searchStrArr[i].contains(" ")){
                if(searchStrArr[i].replaceAll(" ","").equals(str)){
                    return true;
                }
            }
        }
        return preMatch && suffMatch;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请选择匹配模式：");
        System.out.println("1：默认");
        System.out.println("2：自定义");
        System.out.println("3：自定义+默认");

        boolean repeat = true;

        while (repeat) {
            String tempString = scan.nextLine();
            String targetString = "";
            String newSearchString = "";
            switch (tempString) {
                case "1":
                    repeat = false;
                    System.out.println("请输入需要匹配的字符串");
                    targetString = scan.nextLine();
                    while (null == targetString || "".equals(targetString)) {
                        System.out.println("请输入需要匹配的字符串");
                        targetString = scan.nextLine();
                    }
                    System.out.println(getResultString("1", targetString, newSearchString));
                    break;
                case "2":
                    repeat = false;
                    System.out.println("请输入新的词典，用英文逗号“,”隔开");
                    newSearchString = scan.nextLine();
                    while (null == newSearchString || "".equals(newSearchString)) {
                        System.out.println("请输入新的词典，用英文逗号“,”隔开");
                        newSearchString = scan.nextLine();
                    }
                    System.out.println("请输入需要匹配的字符串");
                    targetString = scan.nextLine();
                    while (null == targetString || "".equals(targetString)) {
                        System.out.println("请输入需要匹配的字符串");
                        targetString = scan.nextLine();
                    }
                    System.out.println(getResultString("2", targetString, newSearchString));
                    break;
                case "3":
                    repeat = false;
                    System.out.println("请输入新的词典，用英文逗号“,”隔开");
                    newSearchString = scan.nextLine();
                    while (null == newSearchString || "".equals(newSearchString)) {
                        System.out.println("请输入新的词典，用英文逗号“,”隔开");
                        newSearchString = scan.nextLine();
                    }
                    System.out.println("请输入需要匹配的字符串");
                    targetString = scan.nextLine();
                    while (null == targetString || "".equals(targetString)) {
                        System.out.println("请输入需要匹配的字符串");
                        targetString = scan.nextLine();
                    }
                    System.out.println(getResultString("3", targetString, newSearchString));
                    break;
                default:
                    repeat = true;
                    System.out.println("输入有误，请重新选择");
                    break;
            }
        }

    }


}