package pl.jkkk.cps.view.controller.animationpanel;

import javafx.concurrent.Task;
import pl.jkkk.cps.view.exception.AnimationNotStartedException;

import java.util.concurrent.atomic.AtomicBoolean;

public class AnimationThread extends Thread {

    /*------------------------ FIELDS REGION ------------------------*/
    private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);
    private Thread thread;

    /*------------------------ METHODS REGION ------------------------*/
    public AtomicBoolean getIsAnimationRunning() {
        return isAnimationRunning;
    }

    public void startAnimation() {
        isAnimationRunning.set(true);
        run();
    }

    public void stopAnimation() {
        try {
            isAnimationRunning.set(false);
            thread.interrupt();
        } catch (NullPointerException e) {
            throw new AnimationNotStartedException();
        }
    }

    @Override
    public void run() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {

                // TODO CHANGE FOR ANIMATION CODE
                int i = 0;
                while (isAnimationRunning.get()) {
                    i++;
                    System.out.println("acdedededed " + i);
                }
                System.out.println("koniec");
                return null;
            }
        };

        thread = new Thread(task);
        thread.start();
    }
}
    