package task;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import game.Game;
import game.Log;
import lejos.hardware.lcd.LCD;

/**
 * タスク管理クラス
 * 
 * @author
 */
public class TaskManager {
    // 競技タスク
    private GameTask gameTask;
    // ログタスク
    private LogTask logTask;
    // スケジューラ
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
    private CountDownLatch countDownLatch;

    public TaskManager() {
        // タスク初期化 開始
        LCD.drawString("Initialize", 0, 0);
        // スケジューラ生成
        scheduler = Executors.newScheduledThreadPool(2);
        countDownLatch = new CountDownLatch(1);
        // タスク生成
        gameTask = new GameTask(countDownLatch, Game.getInstance());
        gameTask.setPriority(Thread.MAX_PRIORITY);
        logTask = new LogTask(Game.getInstance(), Log.getInstance());
        logTask.setPriority(Thread.NORM_PRIORITY);

        // タスク初期化終了
        LCD.clear();
        Beep.ring();
    }

    /**
     * タスクのスケジューリング
     */

    public void schedule() {// RateからDelayに変更した
        futureGame = scheduler.scheduleWithFixedDelay(gameTask, 0, 12, TimeUnit.MILLISECONDS);
        futureLog = scheduler.scheduleWithFixedDelay(logTask, 0, 1000, TimeUnit.MILLISECONDS);
    }
    /*
     * 競技タスクが終了するまで待つ
     */

    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {

        }
    }
    /*
     * タスクの実行の取り消しとスケジューラのシャットダウン
     */

    public void shutdown() {
        if (futureLog != null) {
            futureLog.cancel(true);
        }
        if (futureGame != null) {
            futureGame.cancel(true);
        }
        scheduler.shutdownNow();
        Log.getInstance().write();
    }
}