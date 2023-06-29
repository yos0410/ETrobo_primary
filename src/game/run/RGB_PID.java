package game.run;

import body.control.Wheel;
import body.measure.Course;
import game.Game;
public class RGB_PID {
    private Course course;
    private Wheel wheel;
    private Game game;

    private float forward = 150.0f;// 前進速度
    private float forward2 = 200.0f;// 前進速度
    private float forward3 = 200.0f;// 前進速度
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

    public RGB_PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    public void acceralation_run() {
        // 目標輝度値
        float target = course.getDivideRGB_Target();
        // 現在の輝度値
        float brightness = course.getDivideRGB();
        
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
        //加速⇓
        if (forward <= 400 ){
            this.forward += 5;
        }
    }
    /**
     * 走行する
     */
    public void run() {
        // 目標輝度値
        float target = course.getDivideRGB_Target();
        // 現在の輝度値
        float brightness = course.getDivideRGB();

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
        wheel.setForward(forward2);
        wheel.setPid(p);
        
    }
    public void acceralation_run2() {
        // 目標輝度値
        float target = course.getDivideRGB_Target();
        // 現在の輝度値
        float brightness = course.getDivideRGB();
        
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
        wheel.setForward(forward3);
        wheel.setPid(p);
        //加速⇓
        if (forward3 <= 400 ){
            this.forward3 += 5;
        }
    }

}
