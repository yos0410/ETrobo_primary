package body.control;

import game.Game;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.utility.Delay;

/**
 * 車輪制御クラス
 * 
 * @author
 *
 */
public class Wheel {
    Game game;
    private EV3LargeRegulatedMotor leftMotor;
    private EV3LargeRegulatedMotor rightMotor;

    private float forward;
    private float leftSpeed;
    private float rightSpeed;

    private float p;
    public float EDGE = 1.0f;// 1=右 -1=左
    // private float L_EDGE = -1.0f;// 1=右 -1=左

    public Wheel(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    /**
     * 制御する
     */

    public void Acceralation_control() {
        // ここ空欄
        leftSpeed = forward + EDGE * p;
        rightSpeed = forward - EDGE * p;
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


    public void R_control() {
        // ここ空欄
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

    public void L_control() {
        // ここ空欄
        leftSpeed = forward + EDGE * p;
        rightSpeed = forward - EDGE * p;
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

    public void turn_left() {
        leftMotor.setSpeed(190);
        rightMotor.setSpeed(300);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(700);
    }

    public void turn_right() {
        leftMotor.setSpeed(300);
        rightMotor.setSpeed(190);
        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(700);
    }

    public void stop() {

        leftMotor.stop(true);
        rightMotor.stop(true);

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