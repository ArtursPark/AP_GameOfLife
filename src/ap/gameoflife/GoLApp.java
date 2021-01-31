package ap.gameoflife;

import ap.gameoflife.view.GoLFrame;

public class GoLApp
{
    // GLOBAL //

    public final static String APP_NAME = "ArtursPark Game of Life";

    // LOCAL //

    private final GoLFrame gameOfLifeFrame;

    // CONSTRUCTORS //

    public GoLApp()
    {
        gameOfLifeFrame = new GoLFrame(APP_NAME);
        gameOfLifeFrame.pack();
        gameOfLifeFrame.setVisible(true);
    }
}
