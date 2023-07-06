package game;

import body.control.Wheel;
import body.measure.Course;
import body.measure.Touch;
//import game.run.PID;
import game.run.RGB_PID;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import task.Beep;

/**
 * 驕ｶ�ｽｶ隰夲ｿｽ郢ｧ�ｽｯ郢晢ｽｩ郢ｧ�ｽｹ
 * 郢ｧ�ｽ､郢晢ｽｳ郢ｧ�ｽｹ郢ｧ�ｽｿ郢晢ｽｳ郢ｧ�ｽｹ郢ｧ雋櫁�ｰ闕ｳ�ｿｽ邵ｺ�ｽｫ邵ｺ蜷ｶ�ｽ狗ｸｺ貅假ｽ∫ｸｲ繝ｾingleton
 * 郢昜ｻ｣縺｡郢晢ｽｼ郢晢ｽｳ郢ｧ蜻域ｲｻ騾包ｽｨ
 * 
 * @author
 *
 */
public class Game {
    // 郢ｧ�ｽｿ郢ｧ�ｽｹ郢ｧ�ｽｯ邵ｺ�ｽｮ陷ｻ�ｽｼ邵ｺ�ｽｳ陷�ｽｺ邵ｺ諤懷ｱ楢ｬｨ�ｽｰ
    private int count;

    private static Game instance = new Game();

    EV3LargeRegulatedMotor leftMotor;
    EV3LargeRegulatedMotor rightMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;
    Touch touch;
    Course course;
    Wheel wheel;
    // PID pid;
    RGB_PID rgb_PID;
    int B_count = 0;
    int A_count = 0;
    int R_count = 0;
    int L_count = 0;

    public enum STATUS {
        CALIBRATION_WHITE, CALIBRATION_BLACK, WAITSTART, RUN, R_RUN, Turn_R_RUN, L_RUN, END, BLUE, GETLOG, ACCELERATION, R_RUN2
    };

    STATUS status;

    private Game() {
        this.leftMotor = new EV3LargeRegulatedMotor(MotorPort.C);
        this.rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        this.touchSensor = new EV3TouchSensor(SensorPort.S1);
        this.colorSensor = new EV3ColorSensor(SensorPort.S3);
        this.touch = new Touch(touchSensor);
        this.course = new Course(colorSensor);
        this.wheel = new Wheel(leftMotor, rightMotor);
        // this.pid = new PID(course, wheel);
        this.rgb_PID = new RGB_PID(course, wheel);
        status = STATUS.CALIBRATION_WHITE;

        // // 隴牙戟�ｽｩ貊�ﾂｰ髴��ｽ｢
        // for (int i = 0; i < 1500; i++) {
        // course.update();
        // wheel.control();
        // }

    }

    public static Game getInstance() {
        return instance;
    }

    /**
     * 陞ｳ貊灘多邵ｺ蜷ｶ�ｽ�
     * 
     * @return 陞ｳ貊灘多闕ｳ�ｽｭ邵ｺ�ｽｯ false邵ｲ竏ｫ�ｽｵ繧��ｽｺ�ｿｽ隴弱ｅ�ｿｽ�ｽｯ true 郢ｧ螳夲ｽｿ譁絶�
     */
    public boolean run() {
        switch (status) {
        case GETLOG:
            touch.update();
            course.update();
            break;
        case CALIBRATION_WHITE:
            touch.update();
            course.update();
            if (touch.isUpped()) {
                course.setRGB_White(course.getRGB());
                Beep.ring();
                status = STATUS.CALIBRATION_BLACK;
            }

            break;
        case CALIBRATION_BLACK:
            touch.update();
            course.update();
            if (touch.isUpped()) {
                course.setRGB_Black(course.getRGB());
                course.setRGB_Target((course.getRGB_White() + course.getRGB_Black()) / 2);
                Beep.ring();
                status = STATUS.WAITSTART;
            }
            break;
        case WAITSTART:

            touch.update();
            course.update();

            if (touch.isUpped()) {

                Beep.ring();
                Log.time();
                A_count++;
                status = STATUS.ACCELERATION;
            }
            break;
        // 陷会ｿｽ鬨ｾ貅倪�郢ｧ�ｿｽ
        case ACCELERATION:
            if (A_count == 1) {
                // forward=200邵ｺ�ｽｮ陜｣�ｽｴ陷ｷ�ｿｽ85陜玲ｧｭ�ｿ･郢ｧ蟲ｨ�ｼ櫁摎讒ｭ�ｼ�邵ｺ貅假ｽ臥ｸｺ�ｽｧ4驕伜�ｵ�ｿ･郢ｧ蟲ｨ�ｼ�
                for (float i = 0; i <= 170; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.acceralation_run();
                    wheel.R_control();
                }
                R_count++;
                status = STATUS.R_RUN2;
            } else if (A_count == 2) {
                for (float i = 0; i < 110; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.acceralation_run2();
                    wheel.R_control();
                }
                R_count++;
                status = STATUS.R_RUN2;
            }

            // L_count++;
            // status = STATUS.L_RUN;
            // 郢ｧ�ｽｫ郢晢ｽｼ郢晄じ�ｿｽ�ｽｮ隰�蜿･辯慕ｸｺ�ｽｧcase陞溽判蟲ｩ邵ｺ蜉ｱ笳�邵ｺ�ｿｽ遶搾ｿｽ
            // if () {
            //
            // }

            break;

        case R_RUN:

            touch.update();
            course.update();
            rgb_PID.run();
            wheel.R_control();
            if (R_count == 1) {
                A_count++;
                status = STATUS.ACCELERATION;
            }

            if (course.getcolorID() == 2) {
                B_count++;
                status = STATUS.BLUE;
            }
            break;
        case L_RUN:

            if (L_count == 1) {
                for (float i = 0; i < 120; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.deceleration_run();
                    wheel.L_control();
                }
                A_count++;
                status = STATUS.ACCELERATION;
            } else if (L_count == 2) {

                touch.update();
                course.update();
                rgb_PID.run();
                wheel.L_control();
                // if (course.getcolorID() == 2) {
                // B_count++;
                // // status = STATUS.BLUE;
                // }

            } else {
                touch.update();
                course.update();
                rgb_PID.run();
                wheel.L_control();
            }

            break;
        case R_RUN2:

            if (R_count == 1) {
                for (float i = 0; i < 120; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.deceleration_run();
                    wheel.R_control();
                }
                A_count++;
                status = STATUS.ACCELERATION;
            } else if (R_count == 2) {

                for (float i = 0; i < 130; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.deceleration_run();
                    wheel.R_control();
                }
                R_count++;
                status = STATUS.R_RUN2;
                // if (course.getcolorID() == 2) {
                // B_count++;
                // // status = STATUS.BLUE;
                // }

            } else {
                touch.update();
                course.update();
                rgb_PID.run();
                wheel.R_control();

                if (course.getcolorID() == 2) {
                    B_count++;
                    status = STATUS.BLUE;
                }
            }

            break;

        case BLUE:
            // course.update();
            // rgb_PID.run();
            // wheel.control();
            // if (course.getcolorID() == 7) {
            // status = STATUS.L_RUN;

            if (B_count == 1) {
                // wheel.stop();
                // Delay.msDelay(2000);
                leftMotor.setSpeed(170);
                rightMotor.setSpeed(170);
                leftMotor.backward();
                rightMotor.forward();
                Delay.msDelay(250);

                for (int i = 0; i <= 60; i++) {
                    touch.update();
                    course.update();
                    rgb_PID.run();
                    wheel.L_control();
                }
                leftMotor.setSpeed(170);
                rightMotor.setSpeed(170);
                leftMotor.forward();
                rightMotor.backward();
                Delay.msDelay(200);

                status = STATUS.R_RUN2;
            }

            if (B_count == 3) {
                leftMotor.setSpeed(130);
                rightMotor.setSpeed(200);
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(1000);
                status = STATUS.L_RUN;
                // course.update();
                // rgb_PID.run();
                // wheel.control2();
                // course.hsv_H() <=155
                // if (course.getcolorID() == -1) {
                // leftMotor.setSpeed(230);
                // rightMotor.setSpeed(160);
                // leftMotor.forward();
                // rightMotor.forward();
                // Delay.msDelay(500);
                // status = STATUS.R_RUN;
                // }
            } else if (B_count == 4) {
                leftMotor.setSpeed(270);
                rightMotor.setSpeed(150);
                leftMotor.forward();
                rightMotor.forward();
                Delay.msDelay(1000);
                status = STATUS.L_RUN;
                // course.update();
                // rgb_PID.run();
                // wheel.control();
                // course.hsv_H() <=195
                // if (course.getcolorID() == -1) {
                // leftMotor.setSpeed(160);
                // rightMotor.setSpeed(230);
                // leftMotor.forward();
                // rightMotor.forward();
                // Delay.msDelay(500);
                // status = STATUS.L_RUN;
                // }

            }

            break;

        default:
            break;
        }

        if (status == STATUS.END) {
            return true;
        } else {
            return false;
        }
    }

    public void countUp() {
        count++;
    }

    public int getB_count() {
        return B_count;
    }

    public int getA_count() {
        return A_count;
    }

    public int getR_count() {
        return R_count;
    }

    public int getL_count() {
        return L_count;
    }

    public STATUS getStatus() {
        return status;
    }
}