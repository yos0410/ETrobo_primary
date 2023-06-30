package body.measure;

import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

/**
 * タッチ計測クラス
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
        // 空白
        this.touchSensor = touchSensor;
        sensorMode = touchSensor.getTouchMode();
        value  = new float[sensorMode.sampleSize()];
        }

    /**
     * 更新する
     */
    @Override
    public void update() {
        // タッチセンサが押されたか（今の状態）
        sensorMode.fetchSample(value, 0);
        isPressed = ((int) value[0] != 0);

        // タッチセンサが離されたか（前回と今の状態を比較）
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