package Localization;
import Localization.Distance;

/**
 * ���ʌv�N���X
 * 
 * @author ����
 *
 */

public class Direction {
    
        private final double TREAD = 135.0; // �ԑ̃g���b�h���i���E�̃^�C���̐ڒn�ʂ̒��S�Ԃ̋����j
        private double direction = 0.0; // ���݂̕���
        Distance distance;

        /* ������ */
        public void init() {
            direction = 0.0;
        }

        /* ���ʂ��擾(�E���񂪐��]) */
        public double getDirection() {
            return direction;
        }

        /* ���ʂ��X�V */
        public void update() {
            //(360 / (2 * �~���� * �ԑ̃g���b�h��)) * (�E�i�s���� - ���i�s����)
            direction += (360.0 / (2.0 * distance.PI * TREAD)) * (distance.getDistance4msLeft() - distance.getDistance4msRight());
        }
    }
