package util;

import java.io.File;

public class PathUtil {
    public static String getCurrentDirectory() {
        try {
            return new File(PathUtil.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível determinar o diretório atual.");
        }
    }
}
