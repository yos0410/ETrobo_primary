package body.measure;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * 路面計測クラス
 * 
 * @author
 *
 */
public class Course implements Measure {
    private EV3ColorSensor colorSensor;
    private SensorMode sensorMode;
    private SensorMode sensorMode2;
    private SensorMode sensorMode3;
    private float value[];
    private float value2[];
    private float value3[];

    private float white;
    private float black;
    private float target;
    private float brightness;
    private float color;
    
    private int color_R;
    private int color_G;
    private int color_B;
    private float RGB;
    private float RGB_white;
    private float RGB_black;
    private float RGB_target;

    int divite = 10000000;

    public Course(EV3ColorSensor colorSensor) {
        Course3(colorSensor);

        white = 0.4f;
        black = 0.0f;
        target = (white + black) / 2.0f;
    }

    public void Course2(EV3ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
        sensorMode2 = colorSensor.getColorIDMode();
        value2 = new float[sensorMode2.sampleSize()];
    }

    public void Course3(EV3ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
        sensorMode3 = colorSensor.getRGBMode();
        value3 = new float[sensorMode3.sampleSize()];
    }

    /**
     * 更新する
     */
    @Override
    public void update() {
        // ここ空欄
        sensorMode.fetchSample(value, 0);
        brightness = value[0];

    }

    public void update2() {
        sensorMode2.fetchSample(value2, 0);
        color = value2[0];
    }

    public void update3() {
        sensorMode3.fetchSample(value3, 0);
        color_R = (int) (value3[0] * 255);
        color_G = (int) (value3[1] * 255);
        color_B = (int) (value3[2] * 255);
        RGB = ((color_R << 16) & 0xFF0000) | ((color_G << 8) & 0xFF00) | (color_B & 0xFF);
    }

    public float getDivideRGB() {
        return RGB / divite;
    }

    public float getDivideRGB_Target() {
        return RGB_target / divite;
    }

    public float getRGB() {
        return RGB;
    }

    public int getR() {
        return color_R;
    }

    public int getG() {
        return color_G;
    }

    public int getB() {
        return color_B;
    }

    public void setRGB_White(float RGB_white) {
        this.RGB_white = RGB_white;
    }

    public float getRGB_White() {
        return RGB_white;
    }

    public void setRGB_Black(float RGB_black) {
        this.RGB_black = RGB_black;
    }

    public float getRGB_Black() {
        return RGB_black;
    }

    public void setRGB_Target(float RGB_target) {
        this.RGB_target = RGB_target;
    }

    public float getRGB_Target() {
        return RGB_target;
    }

    public boolean getTrueRGB_Blue() {
        if (color_B >= 150 || color_B <= 255) {
            return true;
        }
        return false;
    }
    
    
   /////////////////////////////////////////////////////////

    public float getWhite() {
        return white;
    }

    public void setWhite(float white) {
        this.white = white;
    }

    public float getBlack() {
        return black;
    }

    public void setBlack(float black) {
        this.black = black;
    }

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public float getBrightness() {
        return brightness;
    }

    public float getColor() {
        return color;
    }

    public boolean getTrueBlue() {
        if (color == 2) {
            return true;
        }
        return false;
    }

}
