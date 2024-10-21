package util;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;

public class FontLoader {

    public static Font loadFontRegular() {
        return loadFont("src/view/styles/fonts/Ubuntu-Regular.ttf", 14f, Font.PLAIN);
    }

    public static Font loadFontBold() {
        return loadFont("src/view/styles/fonts/Ubuntu-Bold.ttf", 16f, Font.BOLD);
    }

    private static Font loadFont(String path, float size, int style) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            System.err.println("Fail to load font: " + e.getMessage());
        }

        return new Font("Arial", style, (int) size);
    }
}
