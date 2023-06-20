package body.measure;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * �^�b�`�v���N���X
 * 
 * @author 
 *
 */
public class Touch implements Measure {
    private EV3TouchSensor touchSensor;
    private SensorMode sensorMode;
    private float value[];
    private boolean isPressed;
    private boolean isPressedOld;
    private boolean isUpped;

    public Touch(EV3TouchSensor touchSensor) {
        // ��
        this.touchSensor = touchSensor;
        sensorMode = touchSensor.getTouchMode();
        value  = new float[sensorMode.sampleSize()];
        }

    /**
     * �X�V����
     */
    @Override
    public void update() {
        // �^�b�`�Z���T�������ꂽ���i���̏�ԁj
        sensorMode.fetchSample(value, 0);
        isPressed = ((int) value[0] != 0);

        // �^�b�`�Z���T�������ꂽ���i�O��ƍ��̏�Ԃ��r�j
        if (isPressedOld == true && isPressed == false ) {
            isUpped = true;
        } else {
            isUpped = false;
        }
        isPressedOld = isPressed;
    }

    public boolean isUpped() {
        return isUpped;
    }

}