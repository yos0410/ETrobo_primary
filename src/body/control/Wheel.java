package body.control;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * 車輪制御クラス
 * 
 * @author
 *
 */
public class Wheel {
    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;

    private float forward;
    private float leftSpeed;
    private float rightSpeed;

    private float p;
    private float R_EDGE = 1.0f;// 1=右 -1=左
    private float L_EDGE = -1.0f;// 1=右 -1=左

    public Wheel(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * 制御する
     */
    public void control() {
        // ここ空欄
        leftSpeed = forward - R_EDGE * p;
        rightSpeed = forward + R_EDGE * p;
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
    public void control2() {
        // ここ空欄
        leftSpeed = forward - L_EDGE * p;
        rightSpeed = forward + L_EDGE * p;
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
    public void setPid(float p) {
        this.p = p;
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


}