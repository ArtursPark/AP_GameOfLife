package ap.gameoflife.view.menu;

import ap.gameoflife.view.menu.buttons.GoLButtonsPanel;
import ap.gameoflife.view.menu.fields.GoLFieldPanel;

import javax.swing.*;
import java.awt.*;


/**
 * Menu panel used to contain 2 types of menus. First, is for the buttons. Second, is for input fields.
 */
public class GoLMenuPanel extends JPanel
{
    // LOCAL //

    private GoLButtonsPanel golButtonsPanel = new GoLButtonsPanel();
    private GoLFieldPanel golFieldPanel = new GoLFieldPanel();

    // CONSTRUCTORS //

    public GoLMenuPanel()
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;

        gridBagConstraints.gridx = 0;
        this.add(golButtonsPanel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        this.add(golFieldPanel, gridBagConstraints);
    }

    // SETTERS AND GETTERS //

    public GoLButtonsPanel getGolButtonsPanel()
    {
        return golButtonsPanel;
    }
    public GoLFieldPanel getGolFieldPanel()
    {
        return golFieldPanel;
    }
}
