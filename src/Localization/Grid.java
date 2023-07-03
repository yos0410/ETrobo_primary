package Localization;
import Localization.Distance;

/**
 * ���W�ړ��N���X
 * 
 * @author ����
 *
 */

public class Grid {
    private final double GRID_SIZE = 100.0; // ���W�̃}�X���i100mm�j
    private double grid_distance = 0.0; // ���ݍ��W����ڕW���W�܂ł̋���
    private double grid_direction = 0.0; // ���ݍ��W����ڕW���W�̕���
    Distance distance;

    /* �������֐� */
    public void init() {
        grid_distance = 0.0;
        grid_direction = 0.0;
    }

    /* ���Wa������Wb�܂ł̈ړ�������ݒ肷��֐� */
    public void setDistance(int aX, int aY, int bX, int bY) {
        grid_distance = Math.sqrt(Math.pow((bX - aX), 2) + Math.pow((bY - aY), 2)) * GRID_SIZE;
    }

    /* ���Wa������Wb�܂ł̈ړ��������擾����֐� */
    public double getDistance() {
        return grid_distance;
    }

    /* �ڕW���W�̕��ʂ�ݒ肷��֐� */
    public void setDirection(int aX, int aY, int bX, int bY) {
        double targetDir = 0.0; // �ڕW����

        // ���Wa������Wb�ւ̕��ʁi���W�A���j���擾
        targetDir = Math.atan2(bY - aY, bX - aX);
        // ���W�A������x�ɕϊ�
        targetDir = targetDir * 180.0 / distance.PI;

        grid_direction = targetDir;
    }

    /* �ڕW���W�̕��ʂ��擾����֐� */
    public double getDirection() {
        return grid_direction;
    }
}