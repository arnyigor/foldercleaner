package com.arny.java.data.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static long strToNum(String str) {
        int i = 0;
        long num = 0;
        while (i < str.length()) {
            num += str.charAt(i++) - '0'; //Minus the ASCII code of '0' to get the value of the charAt(i++).
        }
        return num;
    }

//    CREATE TABLE `test` (`_id`	INTEGER PRIMARY KEY AUTOINCREMENT, `id` TEXT, `title` TEXT);

    public static String getFieldsStr(Collection<Field> fields) {
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            builder.append(field.getName());
            builder.append(",");
        }
        return builder.toString();
    }

    public static int parseInt(String str) {
        int i = 0, n = 0, sign = 1;
        if (str.charAt(0) == '-') {
            i = 1;
            sign = -1;
        }
        for (; i < str.length(); i++) {
            n *= 10;
            n += str.charAt(i) - 48;
        }
        return sign * n;
    }

    public static String randomAlphaNumeric(int count) {
        String numString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * numString.length());
            builder.append(numString.charAt(character));
        }
        return builder.toString();
    }

    public static String[][] getFileArray(String inputFileName) {
        int Minit = 0, Ninit = 0;
        try {
            FileInputStream fstream = new FileInputStream(inputFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                //читаем первую строку
                String[] colRows = strLine.split(" ");
                Minit = Integer.parseInt(colRows[0]);
                Ninit = Integer.parseInt(colRows[1]);
                break;
            }
        } catch (IOException e) {
            System.out.println("Ошибка");
        }
        String[][] startArr = new String[Minit + 1][Ninit + 1];
        try {
            FileInputStream fstream = new FileInputStream(inputFileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String newLine;
            int m = 0, l = 0;
            while ((newLine = br.readLine()) != null) {
                //пропуск первой строчки
                if (m == 0) {
                    m = -1;
                    l = 0;
                    continue;
                }
                //запись с 0 индекса
                startArr[l] = newLine.trim().split(" ");
                l++;
            }
        } catch (IOException e) {
            System.out.println("Ошибка");
        }
        return startArr;
    }

    public static String pad(int c) {
        if (c >= 10) {
            return String.valueOf(c);
        } else {
            return "0" + String.valueOf(c);
        }
    }

    public static void writeSimpleText(String outputFileName, String text) {
        try (FileWriter writer = new FileWriter(outputFileName, false)) {
            writer.write(text);
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void writeRandomArray(String outputFileName, int m, int n, String[][] strArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strArr.length; i++) {
            for (int j = 0; j < strArr[i].length; j++) {
                sb.append(strArr[i][j] + " ");
            }
            sb.append('\n');
        }

        try (FileWriter writer = new FileWriter(outputFileName, false)) {
            writer.write(m + " " + n);
            writer.append('\n');
            writer.write(sb.toString());
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void show2dStringArray(String[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");//используем для отладки
            }
            System.out.println();//используем для отладки новая строка
        }
    }

    /**
     * функции генерации массива рандомными символами размерности M*N
     *
     * @param M строки
     * @param N столбцы
     * @return массив
     */
    public static String[][] randomStrArr(int M, int N) {
        String[][] arr = new String[M][N];
        Random random = new Random();
        String symbols = "QWERTYUIOPLKJHGFDSAZXCVBNMmnbvcxzlkjhgfdsaqwertyuiop1234567890";
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                String randomChar = String.valueOf(symbols.charAt(random.nextInt(symbols.length())));
                arr[i][j] = randomChar;
            }
        }
        return arr;
    }

    /**
     * функция составляет из массива слово и выводит символы с индексами
     *
     * @param word искомое слово
     * @param arr  входящий массив
     * @return String || Impossible
     */
    public static String findWordInArr(String word, String[][] arr) {
        int ksumm = 0;
        StringBuilder builder = new StringBuilder();//много конкатенаций,используем билдер
        for (int k = 0; k < word.length(); k++) {
            arr:
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    String c = String.valueOf(word.charAt(k));
                    if (c.equals(arr[i][j])) {
                        arr[i][j] = "";// больше не используем
                        ksumm++;
                        builder.append(c).append(" - (").append(i).append(",").append(j).append(")\n");
                        break arr;//смотрим снова по массиву
                    }
                }
            }
        }
        //сверяем контрольную сумму
        if (ksumm == word.length()) {
            return String.valueOf(builder);
        } else {
            return String.valueOf("Impossible");
        }
    }

    public static int[][] randomIntArray(int M, int N) {
        //Зполняем массив рандомно
        int[][] grid = new int[M][M];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = (int) (Math.random() * 10);
                System.out.print(grid[i][j] + " ");
            }
            System.out.println("");

        }
        return grid;
    }

    public static String getSpiralArray(int[][] matrix, int size) {
        StringBuilder buffer = new StringBuilder();
        int i = matrix[0].length / 2;
        int j = i;
        // задаем границы движения
        int min_i = i;
        int max_i = i; // влево вправо
        int min_j = j;
        int max_j = j; // вверх вниз
        int d = 0; // сначала пойдем влево
        for (int a = 0; a < size; a++) {
            //массив перевернут по i j
            buffer.append(matrix[j][i]).append(" ");
            switch (d) {
                case 0:
                    i -= 1;  // движение влево
                    if (i < min_i) { // проверка выхода за заполненную центральную часть слева
                        d = 3; // меняем направление
                        min_i = i; // увеличиваем заполненную часть влево
                    }
                    break;
                case 1:  // движение вверх проверка сверху
                    j -= 1;
                    if (j < min_j) {
                        d = 0;
                        min_j = j;
                    }
                    break;
                case 2:  // движение вправо проверка справа
                    i += 1;
                    if (i > max_i) {
                        d = 1;
                        max_i = i;
                    }
                    break;
                case 3:  // движение вниз проверка снизу
                    j += 1;
                    if (j > max_j) {
                        d = 2;
                        max_j = j;
                    }
                    break;
            }
        }
        return buffer.toString();
    }

    public static long randLong(long min, long max) {
        Random rnd = new java.util.Random();
        if (min > max) {
            throw new IllegalArgumentException("min>max");
        }
        if (min == max) {
            return min;
        }
        long n = rnd.nextLong();
        //abs (use instead of Math.abs, which might return min value) :
        n = n == Long.MIN_VALUE ? 0 : n < 0 ? -n : n;
        //limit to range:
        n = n % (max - min);
        return min + n;
    }

    public static int randInt(int min, int max) {
        Random rnd = new java.util.Random();
        int range = max - min + 1;
        return rnd.nextInt(range) + min;
    }

    public static boolean matcher(String regex, String string) {
        return Pattern.matches(regex, string);
    }

    @Nullable
    public static String match(@Nullable String where, @Nullable String pattern, int groupnum) {
        if (where != null && pattern != null) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(where);
            while (m.find()) {
                if (!m.group(groupnum).equals("")) {
                    return m.group(groupnum);
                }
            }
        }

        return null;
    }

    public static String stringContains(String where, String[] cases, String[] answers) {
        for (int i = 0; i < cases.length; i++) {
            if (where.contains(cases[i])) return answers[i];
        }
        return where;
    }

    public static boolean stringContain(String where, String[] cases) {
        for (String aCase : cases) {
            if (where.contains(aCase)) return true;
        }
        return false;
    }

    public static String stringMatch(String where, String[] cases) {
        for (String s : cases) {
            if (matcher("(?i).*" + s + ".*", where.toLowerCase())) return s;
        }
        return null;
    }

    public static int[] bubbleSort(int[] arr) {
        for (int i = arr.length - 1; i >= 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                }
            }
        }
        return arr;
    }

    public static Class<?> determineType(Field field, Object object) {
        Class<?> type = object.getClass();
        return (Class<?>) getType(type, field).type;
    }

    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("\nKey : " + entry.getKey()
                    + " Value : " + entry.getValue());
        }
    }

    public static <T> void printList(List<T> list) {
        for (T t : list) {
            System.out.println("\n" + list.getClass().getSimpleName() + " listItem:" + t);
        }
    }

    public static String getThread() {
        return Thread.currentThread().getName();
    }

    public static void optionResult(String title, String message, boolean success) {
        int messType = success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE;
        JOptionPane.showMessageDialog(null, message, title, messType);
    }

    @NotNull
    public static String getSubstring(String resp, int total) {
        if (resp != null) {
            if (resp.length() > total) {
                return resp.substring(0, total);
            }
            return resp;
        } else {
            return "";
        }
    }

    public static void printAllProps() {
        Properties prop = System.getProperties();
        Set<Object> keySet = prop.keySet();
        for (Object obj : keySet) {
            System.out.println("System Property: {"
                    + obj.toString() + ","
                    + System.getProperty(obj.toString()) + "}");
        }
    }

    public static class TypeInfo {
        Type type;
        Type name;

        public TypeInfo(Type type, Type name) {
            this.type = type;
            this.name = name;
        }

    }

    public static void setJsonParam(JSONObject params, String col, Object val) {
        try {
            params.put(col, val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getJsonObjectToHashMap(JSONObject params) {
        HashMap<String, String> mapParams = new HashMap<>();
        try {
            for (Map.Entry<String, Object> entry : jsonToMap(params).entrySet()) {
                mapParams.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapParams;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();
        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();
        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    private static TypeInfo getType(Class<?> clazz, Field field) {
        TypeInfo type = new TypeInfo(null, null);
        if (field.getGenericType() instanceof TypeVariable<?>) {
            TypeVariable<?> genericTyp = (TypeVariable<?>) field.getGenericType();
            Class<?> superClazz = clazz.getSuperclass();

            if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) clazz.getGenericSuperclass();
                TypeVariable<?>[] superTypeParameters = superClazz.getTypeParameters();
                if (!Object.class.equals(paramType)) {
                    if (field.getDeclaringClass().equals(superClazz)) {
                        // this is the root class an starting point for this search
                        type.name = genericTyp;
                        type.type = null;
                    } else {
                        type = getType(superClazz, field);
                    }
                }
                if (type.type == null || type.type instanceof TypeVariable<?>) {
                    // lookup if type is not found or type needs a lookup in current concrete class
                    for (int j = 0; j < superClazz.getTypeParameters().length; ++j) {
                        TypeVariable<?> superTypeParam = superTypeParameters[j];
                        if (type.name.equals(superTypeParam)) {
                            type.type = paramType.getActualTypeArguments()[j];
                            Type[] typeParameters = clazz.getTypeParameters();
                            if (typeParameters.length > 0) {
                                for (Type typeParam : typeParameters) {
                                    TypeVariable<?> objectOfComparison = superTypeParam;
                                    if (type.type instanceof TypeVariable<?>) {
                                        objectOfComparison = (TypeVariable<?>) type.type;
                                    }
                                    if (objectOfComparison.getName().equals(((TypeVariable<?>) typeParam).getName())) {
                                        type.name = typeParam;
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
            }
        } else {
            type.type = field.getGenericType();
        }

        return type;
    }

    public static boolean isObjectsIqual(Object o1, Object o2) throws IllegalAccessException {
        if (!o1.getClass().getSimpleName().equals(o2.getClass().getSimpleName())) {
            return false;
        }
        Field[] clsFields1 = o1.getClass().getDeclaredFields();
        Field[] clsFields2 = o2.getClass().getDeclaredFields();

        boolean result = false;
        for (Field field1 : clsFields1) {
            field1.setAccessible(true); //Additional line
            System.out.println("isObjectIquals: fld 1= " + "(" + field1.getType() + ") " + field1.getName() + " = " + field1.get(o1) + ", ");
            boolean hasFieldName = false;
            for (Field field2 : clsFields2) {
                if (hasFieldName) {
                    break;
                }
                field2.setAccessible(true); //Additional line
                System.out.println("isObjectIquals: fld 2= " + "(" + field2.getType() + ") " + field2.getName() + " = " + field2.get(o2) + ", ");
                if (field1.getType().getSimpleName().equals(field2.getType().getSimpleName())) {
                    hasFieldName = true;
                    if (field1.getName().equals(field2.getName())) {
                        boolean isArr = field1.getType().isArray();
                        if (isArr) {
                            String klass1 = field1.get(o1).getClass().getSimpleName();
                            String klass2 = field2.get(o2).getClass().getSimpleName();
                            System.out.println(klass1);
                            System.out.println(klass2);
                            String arr1 = null;
                            String arr2 = null;
                            if (klass1.equals("Float[]") && klass2.equals("Float[]")) {
                                arr1 = Arrays.toString((Float[]) field1.get(o1));
                                arr2 = Arrays.toString((Float[]) field2.get(o2));
                            }
                            if (klass1.equals("Double[]") && klass2.equals("Double[]")) {
                                arr1 = Arrays.toString((Double[]) field1.get(o1));
                                arr2 = Arrays.toString((Double[]) field2.get(o2));
                            }
                            System.out.println(arr1);
                            System.out.println(arr2);
                            if (arr1 != null && arr2 != null && arr1.equals(arr2)) {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        } else {
                            if (field1.get(o1).toString().equals(field2.get(o2).toString())) {
                                result = true;
                            } else {
                                result = false;
                                break;
                            }
                        }
                    }
                }
                field2.setAccessible(false); //Additional line
                if (hasFieldName && !result) {
                    break;
                }
            }
            field1.setAccessible(false); //Additional line
            if (!result) {
                break;
            }
        }
        return result;
    }

    public static boolean empty(@Nullable Object obj ) {
        return UtilsKt.empty(obj);
    }

    public static <T> boolean contains(ArrayList<T> array, T v) {
        for (T e : array) {
            if (v.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void fill(List<T> list, T val) {
        for (int i = 0; i < list.size(); i++)
            list.set(i, val);
    }

    public static <T> boolean equals(final T[] array, T v) {
        for (T e : array) {
            if (v.equals(e)) {
                return true;
            }
        }
        return false;
    }

    public static byte[] object2Bytes(Object o) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object bytes2Object(byte raw[]) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(raw);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertToSQLTable(Object o) {
        Collection<Field> fields = getFields(o.getClass());
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE IF NOT EXISTS `");
        builder.append(o.getClass().getSimpleName());
        builder.append("` (`_id` INTEGER PRIMARY KEY AUTOINCREMENT, ");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fldType = field.getType().getSimpleName();
                String msg = " `" + field.getName() + "` " + UtilsKt.getSQLType(fldType) + ",";
                builder.append(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        builder.append(")");
        String res = builder.toString();
        res = res.replaceAll("\\,\\)$", ");");
        return res;
    }

    public static <T> String fillSQLTable(List<T> objects, String[] fldsToSave) {
        Object o = objects.get(0);
        Collection<Field> fields = new ArrayList<>();
        if (fldsToSave.length > 0) {
            ArrayList<String> tosave = new ArrayList<>(Arrays.asList(fldsToSave));
            for (Field field : getFields(o.getClass())) {
                int pos = Collections.binarySearch(tosave, field.getName(), String::compareToIgnoreCase);
                if (pos >= 0) {
                    fields.add(field);
                }
            }
        } else {
            fields = getFields(o.getClass());
        }
        StringBuilder preBuilder = new StringBuilder();
        preBuilder.append("INSERT INTO `");
        preBuilder.append(o.getClass().getSimpleName());
        preBuilder.append("` (");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String msg = field.getName();
                preBuilder.append(msg);
                preBuilder.append(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        preBuilder.append(")");
        preBuilder.deleteCharAt(preBuilder.length() - 2);
        preBuilder.append(" VALUES(");
        String preFields = preBuilder.toString();
        StringBuilder res = new StringBuilder();
        for (Object object : objects) {
            res.append(preFields);
            for (Field field : fields) {
                field.setAccessible(true);
                try {
                    String cls = field.getType().getSimpleName();
                    Object val = field.get(object);
                    if (cls.equalsIgnoreCase("String")) {
                        res.append("'");
                        String valRes = val == null ? "" : String.valueOf(val);
                        res.append(valRes);
                        res.append("'");
                    } else {
                        String valRes = val == null ? "0" : String.valueOf(val);
                        res.append(valRes);
                    }
                    res.append(",");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                field.setAccessible(false);
            }
            res.deleteCharAt(res.length() - 1);
            res.append(");\n");
        }
        return res.toString();
    }

    public static Boolean getFullEquals(Object parent, Object other) {
        if (other == null) return false;
        if (!other.getClass().getSimpleName().equals(parent.getClass().getSimpleName())) return false;
        Collection<Field> fieldsP = getFields(parent.getClass());
        if (fieldsP.size() != getFields(other.getClass()).size()) return false;
        for (Field fieldP : fieldsP) {
            fieldP.setAccessible(true);
            try {
                Object o = fieldP.get(parent);
                Object o2 = fieldP.get(other);
                boolean equals = o.equals(o2);
                if (!equals) return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            fieldP.setAccessible(false);
        }
        return true;
    }

    public static String getFields(Object o) {
        Collection<Field> fields = getFields(o.getClass());
        StringBuilder builder = new StringBuilder();
        builder.append(o.getClass().getSimpleName()).append("(");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String msg = field.getName() + ":'" + field.get(o) + "'(" + field.getType().getSimpleName() + ");";
                builder.append(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        builder.append(") \n");
        return builder.toString();
    }

    /**
     * Get all fields of a class.
     *
     * @param clazz The class.
     * @return All fields of a class.
     */
    public static Collection<Field> getFields(Class<?> clazz) {
        Map<String, Field> fields = new HashMap<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (!field.getName().equalsIgnoreCase("shadow$_klass_") && !field.getName().equalsIgnoreCase("serialVersionUID") && !field.getName().equalsIgnoreCase("$change") && !field.getName().equalsIgnoreCase("shadow$_monitor_")) {
                    if (!fields.containsKey(field.getName())) {
                        fields.put(field.getName(), field);
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return fields.values();
    }

    public static String getFields(Object cls, String[] include) {
        Collection<Field> fields = getFields(cls.getClass());
        StringBuilder builder = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String msg = "\n" + field.getName() + ":" + field.get(cls) + "; ";
                for (String s : include) {
                    if (s.equalsIgnoreCase(field.getName())) {
                        builder.append(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
        return builder.toString();
    }

}
