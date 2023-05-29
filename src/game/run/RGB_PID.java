package game.run;

import body.control.Wheel;
import body.measure.Course;

public class RGB_PID {
    private Course course;
    private Wheel wheel;

    private int forward = 200;// �O�i���x
    private int Kp = 800;
    private int Ki = 20;
    private int Kd = 250;
    // ���葖�s 200 800 20 250
    private int p;

    private int lasterr;
    private int last2err;

    private int err;// �P�x�l-�ڕW�P�x�l
    private int diff;// �����l
    private int integral;//

    public RGB_PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    /**
     * ���s����
     */
    public void run() {
        // �ڕW�P�x�l
        int target = course.getRGB_Target();
        // ���݂̋P�x�l
        int brightness = course.getRGB();

        // �G���[�l
        err = brightness - target;

        // �����l�̌v�Z
        diff = (err - lasterr) - (lasterr - last2err);

        //
        integral = err;

        // p�̌v�Z
        p = p + Kp * (err - lasterr) + Ki * integral + Kd * diff;

        // �O���O�X���
        last2err = lasterr;
        // �����O���
        lasterr = err;
        // ���x�A����ʐݒ�
        wheel.setForward(forward);
        wheel.setPid(p);
    }
}
