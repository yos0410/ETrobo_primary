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
 * �^�X�N�Ǘ��N���X
 * 
 * @author
 */
public class TaskManager {
    // ���Z�^�X�N
    private GameTask gameTask;
    // ���O�^�X�N
    private LogTask logTask;
    // �X�P�W���[��
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> futureGame;
    private ScheduledFuture<?> futureLog;
    private CountDownLatch countDownLatch;

    public TaskManager() {
        // �^�X�N������ �J�n
        LCD.drawString("Initialize", 0, 0);
        // �X�P�W���[������
        scheduler = Executors.newScheduledThreadPool(2);
        countDownLatch = new CountDownLatch(1);
        // �^�X�N����
        gameTask = new GameTask(countDownLatch, Game.getInstance());
        gameTask.setPriority(Thread.MAX_PRIORITY);
        logTask = new LogTask(Game.getInstance(), Log.getInstance());
        logTask.setPriority(Thread.NORM_PRIORITY);

        // �^�X�N�������I��
        LCD.clear();
        Beep.ring();
    }

    /**
     * �^�X�N�̃X�P�W���[�����O
     */

    public void schedule() {// Rate����Delay�ɕύX����
        futureGame = scheduler.scheduleWithFixedDelay(gameTask, 0, 12, TimeUnit.MILLISECONDS);
        futureLog = scheduler.scheduleWithFixedDelay(logTask, 0, 1000, TimeUnit.MILLISECONDS);
    }
    /*
     * ���Z�^�X�N���I������܂ő҂�
     */

    public void await() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {

        }
    }
    /*
     * �^�X�N�̎��s�̎������ƃX�P�W���[���̃V���b�g�_�E��
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