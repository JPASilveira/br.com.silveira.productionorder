package util;

import java.awt.Toolkit;
import java.awt.Dimension;

public class ResolutionCapture {
    private final int width;
    private final int height;

    public ResolutionCapture() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        this.width = screenSize.width;
        this.height = screenSize.height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}