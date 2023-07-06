package test;

import task.TaskManager;

public class Primary1_test {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.schedule();
        taskManager.await();
        taskManager.shutdown();
    }
}
    