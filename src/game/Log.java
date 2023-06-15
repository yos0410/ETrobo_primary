package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lejos.hardware.lcd.LCD;

/**
 * ���O�N���X �C���X�^���X��P��ɂ��邽�߁ASingleton �p�^�[�����̗p
 * 
 * @author
 *
 */
public class Log {
    // �^�X�N�̌Ăяo����
    private int count;

    private static Log instance = new Log();

    private static long startTime;

    private Game game;

    private static List<LogData> logList = new ArrayList<LogData>();

    private Log() {
    }

    public static void time() {
        startTime = System.currentTimeMillis();
    }

    public static Log getInstance() {
        return instance;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void disp() {
        LCD.clear();

        switch (game.status) {
        case CALIBRATION_WHITE:
        case CALIBRATION_BLACK:
            dispCalibration();
            break;

        case WAITSTART:
            dispWaitStart();
            break;
        case BLUE:
        case R_RUN:
        case L_RUN:
            dispRun();
            break;
        default:
            break;
        }
    }

    private void dispCalibration() {
        LCD.drawString("CALIBRATION", 0, 0);
        LCD.drawString("White", 0, 2);
        LCD.drawString(Float.toString(game.course.getRGB_White()), 11, 2);
        LCD.drawString("Black", 0, 3);
        LCD.drawString(Float.toString(game.course.getRGB_Black()), 11, 3);
        LCD.drawString("Target", 0, 4);
        LCD.drawString(Float.toString(game.course.getRGB_Target()), 11, 4);
        LCD.drawString("Brightness", 0, 5);
        LCD.drawString(Float.toString(game.course.getRGB()), 11, 5);
    }

    private void dispWaitStart() {
        LCD.drawString("WAIT START", 0, 0);
        LCD.drawString("White", 0, 2);
        LCD.drawString(Float.toString(game.course.getRGB_White()), 11, 2);
        LCD.drawString("Black", 0, 3);
        LCD.drawString(Float.toString(game.course.getRGB_Black()), 11, 3);
        LCD.drawString("Target", 0, 4);
        LCD.drawString(Float.toString(game.course.getRGB_Target()), 11, 4);
        LCD.drawString("Brightness", 0, 5);
        LCD.drawString(Float.toString(game.course.getRGB()), 11, 5);
    }

    private void dispRun() {
        LCD.drawString("RUN", 0, 0);
        LCD.drawString("White", 0, 2);
        LCD.drawString(Float.toString(game.course.getRGB_White()), 11, 2);
        LCD.drawString("Black", 0, 3);
        LCD.drawString(Float.toString(game.course.getRGB_Black()), 11, 3);
        LCD.drawString("Target", 0, 4);
        LCD.drawString(Float.toString(game.course.getRGB_Target()), 11, 4);
        LCD.drawString("Brightness", 0, 5);
        LCD.drawString(Float.toString(game.course.getRGB()), 11, 5);
        LCD.drawString("Forward", 0, 6);
        LCD.drawString(Float.toString(game.wheel.getForward()), 11, 6);
        LCD.drawString("LeftSpeed", 0, 7);
        LCD.drawString(Float.toString(game.wheel.getLeftSpeed()), 11, 7);
        LCD.drawString("RightSpeed", 0, 8);
        LCD.drawString(Float.toString(game.wheel.getRightSpeed()), 11, 8);

    }

    public void countUp() {
        count++;
    }

    /**
     * �ǉ�����
     */
    public void add() {
        logList.add(new LogData(System.currentTimeMillis() - startTime, game.getStatus(), game.course.getRGB(),
                game.course.getR(), game.course.getG(), game.course.getB(), game.wheel.getForward(),game.course.getcolorID(),
                game.wheel.getLeftSpeed(), game.wheel.getRightSpeed()));
    }

    /**
     * �t�@�C���ɕۑ�����
     */
    public void write() {
        try {
            StringBuilder sb = new StringBuilder();
            // �w�b�_�[��
            sb.append("white,black,target\r\n");
            sb.append(Float.toString(game.course.getRGB_White()));
            sb.append(",");
            sb.append(Float.toString(game.course.getRGB_Black()));
            sb.append(",");
            sb.append(Float.toString(game.course.getRGB_Target()));
            sb.append("\r\n\r\n");
            // ���R�[�h��
            sb.append("time,status,RGB,R,G,B,colorID,forward,leftspeed,rightspeed\r\n");
            for (LogData data : logList) {
                sb.append(Long.toString(data.getTime()));
                sb.append(",");
                sb.append(data.getStatus().toString());
                sb.append(",");
                sb.append(Float.toString(data.RGB_brightness()));
                sb.append(",");
                sb.append(Float.toString(data.getR()));
                sb.append(",");
                sb.append(Float.toString(data.getG()));
                sb.append(",");
                sb.append(Float.toString(data.getB()));
                sb.append(",");
                sb.append(Integer.toString(data.getcolorID()));
                sb.append(",");
                sb.append(Float.toString(data.getForward()));
                sb.append(",");
                sb.append(Float.toString(data.getLeftSpeed()));
                sb.append(",");
                sb.append(Float.toString(data.getRightSpeed()));
                sb.append(",");
                sb.append("\r\n");
            }
            
            File file = new File("log.csv");
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(sb.toString());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}