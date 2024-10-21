package debug;

import util.ResolutionCapture;

public class DebugResolutionCapture {
    public static void main(String[] args) {
        ResolutionCapture resolutionCapture = new ResolutionCapture();
        System.out.println("Width: " + resolutionCapture.getWidth() + " Height: " + resolutionCapture.getHeight());
    }
}
