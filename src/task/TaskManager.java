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
 * 繧ｿ繧ｹ繧ｯ邂｡逅�繧ｯ繝ｩ繧ｹ
 * 
 * @author
 */
public class TaskManager {
    // 遶ｶ謚�繧ｿ繧ｹ繧ｯ
    private GameTask gameTask;
    // 繝ｭ繧ｰ繧ｿ繧ｹ繧ｯ
    private LogTask logTask;
    // 繧ｹ繧ｱ繧ｸ繝･繝ｼ繝ｩ
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
    private CountDownLatch countDownLatch;

    public TaskManager() {
        // 繧ｿ繧ｹ繧ｯ蛻晄悄蛹� 髢句ｧ�
        LCD.drawString("Initialize", 0, 0);
        // 繧ｹ繧ｱ繧ｸ繝･繝ｼ繝ｩ逕滓��
        scheduler = Executors.newScheduledThreadPool(2);
        countDownLatch = new CountDownLatch(1);
        // 繧ｿ繧ｹ繧ｯ逕滓��
        gameTask = new GameTask(countDownLatch, Game.getInstance());
        gameTask.setPriority(Thread.MAX_PRIORITY);
        logTask = new LogTask(Game.getInstance(), Log.getInstance());
        logTask.setPriority(Thread.NORM_PRIORITY);

        // 繧ｿ繧ｹ繧ｯ蛻晄悄蛹也ｵゆｺ�
        LCD.clear();
        Beep.ring();
    }

    /**
     * 繧ｿ繧ｹ繧ｯ縺ｮ繧ｹ繧ｱ繧ｸ繝･繝ｼ繝ｪ繝ｳ繧ｰ
     */

<<<<<<< HEAD
    public void schedule() {// Rate����Delay�ɕύX����
        futureGame = scheduler.scheduleWithFixedDelay(gameTask, 0, 10, TimeUnit.MILLISECONDS);
=======

    public void schedule() {// RateからDelayに変更した
        futureGame = scheduler.scheduleWithFixedDelay(gameTask, 0, 10, TimeUnit.MILLISECONDS);

    


>>>>>>> refs/remotes/origin/master
        futureLog = scheduler.scheduleWithFixedDelay(logTask, 0, 1000, TimeUnit.MILLISECONDS);
    }
    /*
     * 遶ｶ謚�繧ｿ繧ｹ繧ｯ縺檎ｵゆｺ�縺吶ｋ縺ｾ縺ｧ蠕�縺､
     */

    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {

        }
    }
    /*
     * 繧ｿ繧ｹ繧ｯ縺ｮ螳溯｡後�ｮ蜿悶ｊ豸医＠縺ｨ繧ｹ繧ｱ繧ｸ繝･繝ｼ繝ｩ縺ｮ繧ｷ繝｣繝�繝医ム繧ｦ繝ｳ
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