package util;

import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
import java.awt.FontFormatException;

public class FontLoader {

    public static Font loadFontRegular() {
        return loadFont("/view/styles/fonts/Ubuntu-Regular.ttf", 18f, Font.PLAIN);
    }

    public static Font loadFontRegularSmall() {
        return loadFont("/view/styles/fonts/Ubuntu-Regular.ttf", 14f, Font.PLAIN);
    }

    public static Font loadFontBold() {
        return loadFont("/view/styles/fonts/Ubuntu-Bold.ttf", 18f, Font.BOLD);
    }

    private static Font loadFont(String path, float size, int style) {
        try (InputStream inputStream = FontLoader.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                System.err.println("Font file not found: " + path);
                return new Font("Arial", style, (int) size);

            }
            return Font.createFont(Font.TRUETYPE_FONT, inputStream).deriveFont(style, size);
        } catch (FontFormatException | IOException e) {
            System.err.println("Fail to load font: " + e.getMessage());
            return new Font("Arial", style, (int) size);
        }
    }
}
