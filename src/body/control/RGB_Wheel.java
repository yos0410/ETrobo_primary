package body.control;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * ŽÔ—Ö§ŒäƒNƒ‰ƒX
 * 
 * @author
 *
 */
public class RGB_Wheel {
    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;

    private float forward;
    private float leftSpeed;
    private float rightSpeed;

    private float p;
    private int EDGE = 1;// 1=‰E -1=¶

    public RGB_Wheel(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * §Œä‚·‚é
     */
    public void control() {
        // ‚±‚±‹ó—“
        leftSpeed = forward - EDGE * p;
        rightSpeed = forward + EDGE * p;
        leftMotor.setSpeed(leftSpeed);
        rightMotor.setSpeed(rightSpeed);

        if (leftSpeed >= 0) {
            leftMotor.forward();
        } else {
            leftMotor.backward();
        }
        if (rightSpeed >= 0) {
            rightMotor.forward();
        } else {
            rightMotor.backward();
        }
    }
    


    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getForward() {
        return forward;
    }

    public float getLeftSpeed() {
        return leftSpeed;
    }

    public float getRightSpeed() {
        return rightSpeed;
    }

    public void setPid(float p) {
        this.p = p;
    }

}