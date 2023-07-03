package Localization;
import Localization.Distance;

/**
 * 座標移動クラス
 * 
 * @author 高野
 *
 */

public class Grid {
    private final double GRID_SIZE = 100.0; // 座標のマス幅（100mm）
    private double grid_distance = 0.0; // 現在座標から目標座標までの距離
    private double grid_direction = 0.0; // 現在座標から目標座標の方位
    Distance distance;

    /* 初期化関数 */
    public void init() {
        grid_distance = 0.0;
        grid_direction = 0.0;
    }

    /* 座標aから座標bまでの移動距離を設定する関数 */
    public void setDistance(int aX, int aY, int bX, int bY) {
        grid_distance = Math.sqrt(Math.pow((bX - aX), 2) + Math.pow((bY - aY), 2)) * GRID_SIZE;
    }

    /* 座標aから座標bまでの移動距離を取得する関数 */
    public double getDistance() {
        return grid_distance;
    }

    /* 目標座標の方位を設定する関数 */
    public void setDirection(int aX, int aY, int bX, int bY) {
        double targetDir = 0.0; // 目標方位

        // 座標aから座標bへの方位（ラジアン）を取得
        targetDir = Math.atan2(bY - aY, bX - aX);
        // ラジアンから度に変換
        targetDir = targetDir * 180.0 / distance.PI;

        grid_direction = targetDir;
    }

    /* 目標座標の方位を取得する関数 */
    public double getDirection() {
        return grid_direction;
    }
}