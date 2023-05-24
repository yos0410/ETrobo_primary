package game.run;

import body.control.Wheel;
import body.measure.Course;

public class PID {
    private Course course;
    private Wheel wheel;

    private float forward = 200.0f;// 前進速度
    private float Kp = 800.0f;
    private float Ki = 20.0f;
    private float Kd = 250.0f;
    // 安定走行 200 800 20 250
    private float p;

    private float lasterr;
    private float last2err;

    private float err;// 輝度値-目標輝度値
    private float diff;// 微分値
    private float integral;//

    public PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    /**
     * 走行する
     */
    public void run() {
        // 目標輝度値
        float target = course.getTarget();
        // 現在の輝度値
        float brightness = course.getBrightness();

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
