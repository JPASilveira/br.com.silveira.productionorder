package util;

public class BooleanString {
    public static String toString(boolean b) {
        return b ? "COMPOSTO" : "NÃO COMPOSTO";
    }

    public static boolean toBoolean(String s) {
        if (s.equals("COMPOSTO")) {
            return true;
        }
    return false;
    }
}
