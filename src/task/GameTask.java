package task;

import java.util.concurrent.CountDownLatch;
import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import game.Game;
/**
 * 競技タスク
 * 
 * @author 
 */
public class GameTask extends Thread {
    private CountDownLatch countDownLatch;
    
    Game game;

    public GameTask(CountDownLatch countDownLatch,Game game) {
        this.countDownLatch = countDownLatch;
        this.game = game;
    }

    @Override
    public void run() {
        if (Button.ESCAPE.isDown() || game.run()) {
            countDownLatch.countDown();
            Beep.ring();
        }
        game.countUp();
    }
}