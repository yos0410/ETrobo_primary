package game;

import body.control.Wheel;
import body.measure.Course;
import body.measure.Touch;
import game.run.PID;
import game.run.RGB_PID;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import task.Beep;

/**
 * 競技クラス インスタンスを単一にするため、Singleton パターンを採用
 * 
 * @author
 *
 */
public class Game {
    // タスクの呼び出し回数
    private int count;

    private static Game instance = new Game();

    EV3LargeRegulatedMotor leftMotor;
    EV3LargeRegulatedMotor rightMotor;
    EV3TouchSensor touchSensor;
    EV3ColorSensor colorSensor;
    Touch touch;
    Course course;
    Wheel wheel;
    PID pid;
    RGB_PID rgb_PID;
    int B_count = 0;

    public enum STATUS {
        CALIBRATION_WHITE, CALIBRATION_BLACK, WAITSTART, RUN, R_RUN, L_RUN, END, BLUE,
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
        this.pid = new PID(course, wheel);
        this.rgb_PID = new RGB_PID(course, wheel);
        status = STATUS.CALIBRATION_WHITE;

         // 暖機運転
         for (int i = 0; i < 1500; i++) {
         course.update();
         wheel.control();
         }

    }

    public static Game getInstance() {
        return instance;
    }

    /**
     * 実施する
     * 
     * @return 実施中は false、終了時は true を返す
     */
    public boolean run() {
        switch (status) {
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
                status = STATUS.R_RUN;
            }
            break;

        case R_RUN:
            touch.update();
            course.update();
            rgb_PID.run();
            wheel.control();
            if (course.getTrueRGB_Blue()) {
                B_count++;
                status = STATUS.BLUE;
            }
            break;
        case L_RUN:
            touch.update();
            course.update();
            rgb_PID.run();
            wheel.control2();
            if (course.getTrueRGB_Blue()) {
                B_count++;
                status = STATUS.BLUE;
            }
            break;

        case BLUE:

            if (B_count == 1) {
                wheel.turn_left();
                status = STATUS.L_RUN;
            } else if (B_count == 2) {
                wheel.turn_right();
                status = STATUS.R_RUN;
            } else {
                status = STATUS.R_RUN;
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

    public STATUS getStatus() {
        return status;
    }
}