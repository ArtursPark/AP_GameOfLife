package ap.gameoflife.view.game;

import ap.gameoflife.model.GoLModel;

import javax.swing.*;


/**
 * This class is a scroll panel, a container to accommodate a map.
 */
public class GoLGamePanel extends JScrollPane
{
    // LOCAL //

    private GoLMapPanel golMapPanel;

    // CONSTRUCTORS //

    public GoLGamePanel(GoLModel in_golModel)
    {
        golMapPanel = new GoLMapPanel(in_golModel, this.getViewport());

        this.setViewportView(golMapPanel);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    }

    // SETTERS AND GETTERS //

    public GoLMapPanel getGolMapPanel()
    {
        return golMapPanel;
    }
}
