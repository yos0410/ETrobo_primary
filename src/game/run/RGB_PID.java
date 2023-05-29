package game.run;

import body.control.Wheel;
import body.measure.Course;

public class RGB_PID {
    private Course course;
    private Wheel wheel;

    private int forward = 200;// 前進速度
    private int Kp = 800;
    private int Ki = 20;
    private int Kd = 250;
    // 安定走行 200 800 20 250
    private int p;

    private int lasterr;
    private int last2err;

    private int err;// 輝度値-目標輝度値
    private int diff;// 微分値
    private int integral;//

    public RGB_PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    /**
     * 走行する
     */
    public void run() {
        // 目標輝度値
        int target = course.getRGB_Target();
        // 現在の輝度値
        int brightness = course.getRGB();

        // エラー値
        err = brightness - target;

        // 微分値の計算
        diff = (err - lasterr) - (lasterr - last2err);

        //
        integral = err;

        // pの計算
        p = p + Kp * (err - lasterr) + Ki * integral + Kd * diff;

        // 前回を前々回に
        last2err = lasterr;
        // 今回を前回に
        lasterr = err;
        // 速度、操作量設定
        wheel.setForward(forward);
        wheel.setPid(p);
    }
}
