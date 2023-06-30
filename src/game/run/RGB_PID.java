package game.run;

import body.control.Wheel;
import body.measure.Course;
import game.Game;
public class RGB_PID {
    private Course course;
    private Wheel wheel;
    private Game game;

    private float forward = 150.0f;// 蜑埼�ｲ騾溷ｺｦ
    private float forward2 = 200.0f;// 蜑埼�ｲ騾溷ｺｦ
    private float forward3 = 150.0f;// 蜑埼�ｲ騾溷ｺｦ
    private float Kp = 800.0f;
    private float Ki = 5.0f;
    private float Kd = 250.0f;
//    private float Kp2 = 800.0f;
//    private float Ki2 = 20.0f;
//    private float Kd2 = 250.0f;
    
    // 螳牙ｮ夊ｵｰ陦� 200 800 20 250
    private float p;

    private float lasterr;
    private float last2err;

    private float err;// 霈晏ｺｦ蛟､-逶ｮ讓呵ｼ晏ｺｦ蛟､
    private float diff;// 蠕ｮ蛻�蛟､
    private float integral;//

    public RGB_PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    public void acceralation_run() {
        // 逶ｮ讓呵ｼ晏ｺｦ蛟､
        float target = course.getDivideRGB_Target();
        // 迴ｾ蝨ｨ縺ｮ霈晏ｺｦ蛟､
        float brightness = course.getDivideRGB();
        
        // 繧ｨ繝ｩ繝ｼ蛟､
        err = brightness - target;
        
        // 蠕ｮ蛻�蛟､縺ｮ險育ｮ�
        diff = (err - lasterr) - (lasterr - last2err);
        
        //
        integral = err;
        
        // p縺ｮ險育ｮ�
        p = p + Kp * (err - lasterr) + Ki * integral + Kd * diff;
        
        // 蜑榊屓繧貞燕縲�蝗槭↓
        last2err = lasterr;
        // 莉雁屓繧貞燕蝗槭↓
        lasterr = err;
        // 騾溷ｺｦ縲∵桃菴憺㍼險ｭ螳�
        wheel.setForward(forward);
        wheel.setPid(p);
        //蜉�騾溪∮
        if (forward <= 400 ){
            this.forward += 5;
        }
    }
    /**
     * 襍ｰ陦後☆繧�
     */
    public void run() {
        // 逶ｮ讓呵ｼ晏ｺｦ蛟､
        float target = course.getDivideRGB_Target();
        // 迴ｾ蝨ｨ縺ｮ霈晏ｺｦ蛟､
        float brightness = course.getDivideRGB();

        // 繧ｨ繝ｩ繝ｼ蛟､
        err = brightness - target;

        // 蠕ｮ蛻�蛟､縺ｮ險育ｮ�
        diff = (err - lasterr) - (lasterr - last2err);

        //
        integral = err;

        // p縺ｮ險育ｮ�
        p = p + Kp * (err - lasterr) + Ki * integral + Kd * diff;

        // 蜑榊屓繧貞燕縲�蝗槭↓
        last2err = lasterr;
        // 莉雁屓繧貞燕蝗槭↓
        lasterr = err;
        // 騾溷ｺｦ縲∵桃菴憺㍼險ｭ螳�
        wheel.setForward(forward2);
        wheel.setPid(p);
        
    }
    public void acceralation_run2() {
        // 逶ｮ讓呵ｼ晏ｺｦ蛟､
        float target = course.getDivideRGB_Target();
        // 迴ｾ蝨ｨ縺ｮ霈晏ｺｦ蛟､
        float brightness = course.getDivideRGB();
        
        // 繧ｨ繝ｩ繝ｼ蛟､
        err = brightness - target;
        
        // 蠕ｮ蛻�蛟､縺ｮ險育ｮ�
        diff = (err - lasterr) - (lasterr - last2err);
        
        //
        integral = err;
        
        // p縺ｮ險育ｮ�
        p = p + Kp * (err - lasterr) + Ki * integral + Kd * diff;
        
        // 蜑榊屓繧貞燕縲�蝗槭↓
        last2err = lasterr;
        // 莉雁屓繧貞燕蝗槭↓
        lasterr = err;
        // 騾溷ｺｦ縲∵桃菴憺㍼險ｭ螳�
        wheel.setForward(forward3);
        wheel.setPid(p);
        //蜉�騾溪∮
        if (forward3 <= 400 ){
            this.forward3 += 5;
        }
    }

}
