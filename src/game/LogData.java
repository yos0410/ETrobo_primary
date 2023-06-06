package game;

import game.Game.STATUS;

/**
 * ログデータ（一件分）クラス
 * 
 * @author
 *
 */
public class LogData {
    private STATUS status;
    private float RGB_brightness;
    private int R;
    private int G;
    private int B;
    private float forward;
    private float turn;
    private float leftSpeed;
    private float rightSpeed;
    private float color;
    private long time;

    public LogData(long time, STATUS status, float RGB_brightness, int R, int G,int B ,float forward, float leftSpeed, float rightSpeed) {
        this.time = time;
        this.status = status;
        this.RGB_brightness = RGB_brightness;
        this.R = R;
        this.G = G;
        this.B = B;
        this.forward = forward;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;

    }

    public long getTime() {
        return time;
    }

    public STATUS getStatus() {
        return status;
    }

    public float RGB_brightness() {
        return RGB_brightness;
    }
    public float getR() {
        return R;
    }
    public float getG() {
        return G;
    }
    public float getB() {
        return B;
    }

    public float getForward() {
        return forward;
    }

    public float getTurn() {
        return turn;
    }

    public float getLeftSpeed() {
        return leftSpeed;
    }

    public float getRightSpeed() {
        return rightSpeed;
    }

    public float getColor() {
        return color;
    }

}