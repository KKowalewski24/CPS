package pl.jkkk.cps.view.controller.animationpanel;

import javafx.concurrent.Task;
import javafx.scene.chart.LineChart;
import pl.jkkk.cps.view.exception.AnimationNotStartedException;

import java.util.concurrent.atomic.AtomicBoolean;

public class AnimationThread {

    /*------------------------ FIELDS REGION ------------------------*/
    private AtomicBoolean isAnimationRunning = new AtomicBoolean(false);
    private Thread thread;

    /*------------------------ METHODS REGION ------------------------*/
    public AtomicBoolean getIsAnimationRunning() {
        return isAnimationRunning;
    }

    public void startAnimation(LineChart chartSignalX,
                               LineChart chartSignalY, LineChart chartCorrelation) {
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

    private void run() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                while (isAnimationRunning.get()) {
//                    start time
//                   step()

//                    end time
//                    time-ile trwał call

                }
                return null;
            }
        };

        thread = new Thread(task);
        thread.start();
    }
}
    