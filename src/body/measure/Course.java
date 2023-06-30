package body.measure;

import java.util.Arrays;
import java.util.Collections;

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
    private float color_R2;
    private float color_G2;
    private float color_B2;
    private float RGB;
    private float RGB_white;
    private float RGB_black;
    private float RGB_target;

    private int colorID_bor = -1;
    private int colorID_bk = 0;
    private int colorID_wh = 1;
    private int colorID_bl = 2;
    private int colorID_gr = 3;
    private int colorID_ye = 4;
    private int colorID_re = 5;
    private int colorID_br = 6;

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
        color_R2 = value[0];
        color_G2 = value[1];
        color_B2 = value[2];
        RGB = ((color_R << 16) & 0xFF0000) | ((color_G << 8) & 0xFF00) | (color_B & 0xFF);

        sensorMode2.fetchSample(value2, 0);
        colorID = (int) (value2[0]);
    }

    public float hsv_v() {
        float V = Math.max(color_R2, Math.max(color_G2, color_B2));
        return V;
    }

    public float hsv_min() {
        float V_min = Math.min(color_R2, Math.min(color_G2, color_B2));
        return V_min;
    }

    public float hsv_s() {
        float V_min = Math.min(color_R2, Math.min(color_G2, color_B2));
        float S = (hsv_v() - V_min) / hsv_v();
        return S;
    }

    public float hsv_h() {
        if (hsv_min() == hsv_v()) {
            return 0;
        } else if (hsv_min() == color_R2) {
            float H = 60 * (color_B2 - color_G2) / (hsv_v() - hsv_min()) + 180;
            return H;
        } else if (hsv_min() == color_G2) {
            float H2 = 60 * (color_R2 - color_B2) / (hsv_v() - hsv_min()) + 300;
            return H2;
        } else if (hsv_min() == color_B2) {
            float H3 = 60 * (color_G2 - color_R2) / (hsv_v() - hsv_min()) + 60;
            return H3;
        }
        return 361;
    }

    public float hsv_H() {
        if (Math.signum(hsv_h()) == -1 || hsv_h() > 360) {
            float H = (hsv_h() + 360) % 360;
            return H;
        } else {
            return hsv_h();
        }
    }

    public float getS() {
        float S = hsv_s() * 100;
        return S;
    }

    public float getV() {
        float V = hsv_v() * 100;
        return V;
    }

    // HSVの色判定////////////////////////////////////////////////////////////////////////////////
    public int getJudge_hsv() {
        if (getS() < 80) {
            if (color_R <= 5 && color_G <= 5 && color_B <= 5 && hsv_H() >= 300) {
                return colorID_bk;

            } else if (color_R >= 25 && color_G >= 25 && color_B >= 25) {
                return colorID_wh;
            } // else if (color_R >= 6 && color_R <= 24 && color_G >= 6 &&
              // color_G <= 24 && color_B >= 6 && color_B <= 24) {
            return colorID_bor;
            // }
        } else if (color_R <= 5 && color_G <= 5 && color_B <= 5 && hsv_H() >= 300) {
            return colorID_bk;
        }
        return colorID_bl;
    }
    // public float getJudge_hsv() {
    // if(hsv_s() <= 165){
    // if (color) {
    //
    // }
    // }
    // }

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