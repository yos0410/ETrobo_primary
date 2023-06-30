package task;

import game.Game;
import game.Log;
import game.Game.STATUS;
import lejos.hardware.lcd.LCD;

/**
 * ログタスク
 * 
 * @author
 */

public class LogTask extends Thread {

    private Game game;
    private Log log;

    public LogTask(Game game, Log log) {
        this.game = game;
        this.log = log;
        log.setGame(game);
    }

    @Override
    public void run() {
        log.countUp();
        log.disp();
        if (game.getStatus() == STATUS.GETLOG ||game.getStatus() == STATUS.R_RUN || game.getStatus() == STATUS.L_RUN || game.getStatus() == STATUS.BLUE
                || game.getStatus() == STATUS.ACCELERATION) {
            log.add();
        }

    }
}