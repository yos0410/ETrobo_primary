package Localization;
import Localization.Distance;

/**
 * 方位計クラス
 * 
 * @author 高野
 *
 */

public class Direction {
    
        private final double TREAD = 135.0; // 車体トレッド幅（左右のタイヤの接地面の中心間の距離）
        private double direction = 0.0; // 現在の方位
        Distance distance;

        /* 初期化 */
        public void init() {
            direction = 0.0;
        }

        /* 方位を取得(右旋回が正転) */
        public double getDirection() {
            return direction;
        }

        /* 方位を更新 */
        public void update() {
            //(360 / (2 * 円周率 * 車体トレッド幅)) * (右進行距離 - 左進行距離)
            direction += (360.0 / (2.0 * distance.PI * TREAD)) * (distance.getDistance4msLeft() - distance.getDistance4msRight());
        }
    }
