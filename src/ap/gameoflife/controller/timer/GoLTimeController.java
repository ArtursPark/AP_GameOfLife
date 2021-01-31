package ap.gameoflife.controller.timer;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.view.game.GoLGamePanel;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * This controller runs on a separate thread. This is used to invoke the generation of the new generation game of life
 * map and invoking the paint components.
 */
public class GoLTimeController implements Runnable
{
    // LOCAL //

    private GoLGamePanel golGamePanel;
    private GoLModel golModel;

    // CONSTRUCTORS //

    public GoLTimeController(GoLGamePanel in_golGamePanel, GoLModel in_golModel)
    {
        this.golGamePanel = in_golGamePanel;
        this.golModel = in_golModel;

        new Thread(this).start();
    }

    // USE CASE //

    /**
     * This thread will run and invoke the generation of the next game of life map. The map will be generated on swing
     * thread because this way, there will be no data race / race condition when painting/resetting/generating the map.
     *
     * Revalidate is required to update the scroll bars on the game panel.
     */
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Thread.sleep(golModel.getPeriod());
            }
            catch (InterruptedException e)
            {
                System.out.println("Error, putting thread to sleep.");
                System.exit(0);
            }
            if (golModel.isRunning())
            {
                try
                {
                    SwingUtilities.invokeAndWait(() -> golModel.nextGeneration());
                }
                catch (InterruptedException | InvocationTargetException e)
                {
                    System.out.println("Error, invoking next generation of game of life on swing thread.");
                    System.exit(0);
                }
            }
            golGamePanel.getGolMapPanel().revalidate();
            golGamePanel.repaint();
        }
    }
}
