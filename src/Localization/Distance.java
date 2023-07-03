package Localization;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

/**
 * �����v�N���X
 * 
 * @author ����
 *
 */

public class Distance {

    private final double TIRE_DIAMETER = 100.0; // �^�C�����a��10�p
    protected final double PI = 3.14159265358; // �~����

    private double distance = 0.0; // ���s����
    private double distance4msL = 0.0; // ���^�C����4ms�Ԃ̋���
    private double distance4msR = 0.0; // �E�^�C����4ms�Ԃ̋���
    private double preAngleL, preAngleR; // ���E���[�^��]�p�x�̉ߋ��l

    EV3LargeRegulatedMotor leftMotor;
    EV3LargeRegulatedMotor rightMotor;
    
    public Distance(EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
        init();
    }

    /* �������֐� */
    public void init() {
        // �e�ϐ��̒l�̏�����
        distance = 0.0;
        distance4msR = 0.0;
        distance4msL = 0.0;
        // ���[�^�p�x�̉ߋ��l�Ɍ��ݒl����
        preAngleL = leftMotor.getPosition();
        preAngleR = rightMotor.getPosition();
    }

    /* �����X�V�i4ms�Ԃ̈ړ������𖈉���Z���Ă���j */
    public void update() {
        double curAngleL = leftMotor.getPosition(); // �����[�^��]�p�x�̌��ݒl
        double curAngleR = rightMotor.getPosition(); // �E���[�^��]�p�x�̌��ݒl
        double distance4ms = 0.0; // 4ms�̋���

        // 4ms�Ԃ̑��s���� = ((�~���� * �^�C���̒��a) / 360) * (���[�^�p�x�ߋ��l - ���[�^�p�x���ݒl)
        distance4msL = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleL - preAngleL); // 4ms�Ԃ̍����[�^����
        distance4msR = ((PI * TIRE_DIAMETER) / 360.0) * (curAngleR - preAngleR); // 4ms�Ԃ̉E���[�^����
        distance4ms = (distance4msL + distance4msR) / 2.0; // ���E�^�C���̑��s�����𑫂��Ċ���
        distance += distance4ms;

        // ���[�^�̉�]�p�x�̉ߋ��l���X�V
        preAngleL = curAngleL;
        preAngleR = curAngleR;
    }

    /* ���s�������擾 */
    public double getDistance() {
        return distance;
    }

    /* �E�^�C����4ms�Ԃ̋������擾 */
    public double getDistance4msRight() {
        return distance4msR;
    }

    /* ���^�C����4ms�Ԃ̋������擾 */
    public double getDistance4msLeft() {
        return distance4msL;
    }
}