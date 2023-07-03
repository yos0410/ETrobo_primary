package Localization;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * £vNX
 * 
 * @author ì
 *
 */

public class Distance {

    private final double TIRE_DIAMETER = 100.0; // ^C¼aÍ10p
    protected final double PI = 3.14159265358; // ~ü¦

    private double distance = 0.0; // s£
    private double distance4msL = 0.0; // ¶^CÌ4msÔÌ£
    private double distance4msR = 0.0; // E^CÌ4msÔÌ£
    private double preAngleL, preAngleR; // ¶E[^ñ]pxÌßl

    EV3LargeRegulatedMotor leftMotor;
    EV3LargeRegulatedMotor rightMotor;
    
    public Distance(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        init();
    }

    /* ú»Ö */
    public void init() {
        // eÏÌlÌú»
        distance = 0.0;
        distance4msR = 0.0;
        distance4msL = 0.0;
        // [^pxÌßlÉ»Ýlðãü
        preAngleL = leftMotor.getPosition();
        preAngleR = rightMotor.getPosition();
    }

    /* £XVi4msÔÌÚ®£ðñÁZµÄ¢éj */
    public void update() {
        double curAngleL = leftMotor.getPosition(); // ¶[^ñ]pxÌ»Ýl
        double curAngleR = rightMotor.getPosition(); // E[^ñ]pxÌ»Ýl
        double distance4ms = 0.0; // 4msÌ£

        // 4msÔÌs£ = ((~ü¦ * ^CÌ¼a) / 360) * ([^pxßl - [^px»Ýl)
        distance4msL = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleL - preAngleL); // 4msÔÌ¶[^£
        distance4msR = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleR - preAngleR); // 4msÔÌE[^£
        distance4ms = (distance4msL + distance4msR) / 2.0; // ¶E^CÌs£ð«µÄé
        distance += distance4ms;

        // [^Ìñ]pxÌßlðXV
        preAngleL = curAngleL;
        preAngleR = curAngleR;
    }

    /* s£ðæ¾ */
    public double getDistance() {
        return distance;
    }

    /* E^CÌ4msÔÌ£ðæ¾ */
    public double getDistance4msRight() {
        return distance4msR;
    }

    /* ¶^CÌ4msÔÌ£ðæ¾ */
    public double getDistance4msLeft() {
        return distance4msL;
    }
}