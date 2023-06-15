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

    private float value[];
    private float value2[];

    private int colorID;

    private int color_R;
    private int color_G;
    private int color_B;
    private float RGB;
    private float RGB_white;
    private float RGB_black;
    private float RGB_target;

    int divite = 10000000;

    public Course(EV3ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
        sensorMode = colorSensor.getRGBMode();
        value = new float[sensorMode.sampleSize()];

        sensorMode2 = colorSensor.getColorIDMode();
        value2 = new float[sensorMode2.sampleSize()];

    }

    /**
     * 更新する
     */
    @Override
    public void update() {
        sensorMode.fetchSample(value, 0);
        color_R = (int) (value[0] * 255);
        color_G = (int) (value[1] * 255);
        color_B = (int) (value[2] * 255);
        RGB = ((color_R << 16) & 0xFF0000) | ((color_G << 8) & 0xFF00) | (color_B & 0xFF);

        sensorMode2.fetchSample(value2, 0);
        colorID = (int) (value2[0]);
    }

    public int getcolorID() {
        return colorID;
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
        // color_B >= 10 && color_R <= 6
        // color_B >= 9 && color_G <= 13 && color_R <=7
        // color_R >= 10 && color_R <= 11 && color_G >= 16 && color_G <= 17 &&
        // color_B >= 16 いけるやつ
        if (color_R >= 10 && color_R <= 11 && color_G >= 16 && color_G <= 17 && color_B >= 16) {
            return true;
        }
        return false;
    }
}