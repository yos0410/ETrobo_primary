package Localization;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * 距離計クラス
 * 
 * @author 高野
 *
 */

public class Distance {

    private final double TIRE_DIAMETER = 100.0; // タイヤ直径は10㎝
    protected final double PI = 3.14159265358; // 円周率

    private double distance = 0.0; // 走行距離
    private double distance4msL = 0.0; // 左タイヤの4ms間の距離
    private double distance4msR = 0.0; // 右タイヤの4ms間の距離
    private double preAngleL, preAngleR; // 左右モータ回転角度の過去値

    EV3LargeRegulatedMotor leftMotor;
    EV3LargeRegulatedMotor rightMotor;
    
    public Distance(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        init();
    }

    /* 初期化関数 */
    public void init() {
        // 各変数の値の初期化
        distance = 0.0;
        distance4msR = 0.0;
        distance4msL = 0.0;
        // モータ角度の過去値に現在値を代入
        preAngleL = leftMotor.getPosition();
        preAngleR = rightMotor.getPosition();
    }

    /* 距離更新（4ms間の移動距離を毎回加算している） */
    public void update() {
        double curAngleL = leftMotor.getPosition(); // 左モータ回転角度の現在値
        double curAngleR = rightMotor.getPosition(); // 右モータ回転角度の現在値
        double distance4ms = 0.0; // 4msの距離

        // 4ms間の走行距離 = ((円周率 * タイヤの直径) / 360) * (モータ角度過去値 - モータ角度現在値)
        distance4msL = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleL - preAngleL); // 4ms間の左モータ距離
        distance4msR = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleR - preAngleR); // 4ms間の右モータ距離
        distance4ms = (distance4msL + distance4msR) / 2.0; // 左右タイヤの走行距離を足して割る
        distance += distance4ms;

        // モータの回転角度の過去値を更新
        preAngleL = curAngleL;
        preAngleR = curAngleR;
    }

    /* 走行距離を取得 */
    public double getDistance() {
        return distance;
    }

    /* 右タイヤの4ms間の距離を取得 */
    public double getDistance4msRight() {
        return distance4msR;
    }

    /* 左タイヤの4ms間の距離を取得 */
    public double getDistance4msLeft() {
        return distance4msL;
    }
}