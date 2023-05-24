package game.run;

import body.control.Wheel;
import body.measure.Course;

public class PID {
    private Course course;
    private Wheel wheel;

    private float forward = 200.0f;// �O�i���x
    private float Kp = 800.0f;
    private float Ki = 20.0f;
    private float Kd = 250.0f;
    // ���葖�s 200 800 20 250
    private float p;

    private float lasterr;
    private float last2err;

    private float err;// �P�x�l-�ڕW�P�x�l
    private float diff;// �����l
    private float integral;//

    public PID(Course course, Wheel wheel) {
        this.course = course;
        this.wheel = wheel;
    }

    /**
     * ���s����
     */
    public void run() {
        // �ڕW�P�x�l
        float target = course.getTarget();
        // ���݂̋P�x�l
        float brightness = course.getBrightness();

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
