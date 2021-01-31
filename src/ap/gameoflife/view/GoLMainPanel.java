package ap.gameoflife.view;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.view.game.GoLGamePanel;
import ap.gameoflife.view.menu.GoLMenuPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Main panel, used to contain most of the app functionality.
 */
public class GoLMainPanel extends JPanel
{
    // LOCAL //

    private GoLMenuPanel golMenuPanel = new GoLMenuPanel();
    private GoLGamePanel golGamePanel;

    // CONSTRUCTORS //

    public GoLMainPanel(GoLModel in_golModel)
    {
        golGamePanel = new GoLGamePanel(in_golModel);

        this.setLayout(new BorderLayout());
        this.add(golMenuPanel, BorderLayout.NORTH);
        this.add(golGamePanel, BorderLayout.CENTER);
    }

    // SETTERS AND GETTERS //

    public GoLGamePanel getGolGamePanel()
    {
        return golGamePanel;
    }
    public GoLMenuPanel getGolMenuPanel()
    {
        return golMenuPanel;
    }
}
