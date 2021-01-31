package ap.gameoflife.controller.buttons;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.view.menu.buttons.GoLButtonsPanel;
import ap.gameoflife.view.menu.fields.GoLFieldPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Button controller, connecting the model states to the button panel.
 */
public class GoLButtonsController
{
    // LOCAL //

    private GoLButtonsPanel golButtonPanel;
    private GoLModel golModel;

    // CONSTRUCTORS //

    public GoLButtonsController(GoLButtonsPanel in_golButtonPanel, GoLModel in_golModel)
    {
        golButtonPanel = in_golButtonPanel;
        golModel = in_golModel;

        // Add action listeners to the buttons
        golButtonPanel.getStartStopButton().addActionListener(new GoLStartStopListener());
        golButtonPanel.getStartStopButton().setText(golButtonPanel.getStartButtonText());

        golButtonPanel.getEditModeButton().addActionListener(new GoLEditListener());
        golButtonPanel.getResetButton().addActionListener(new GoLResetListener());
        golButtonPanel.getRandomButton().addActionListener(new GoLRandomListener());

        switchOnOffTextFields();
    }

    // USE CASE //

    private void switchOnOffTextFields()
    {
        for (Component component:golButtonPanel.getParent().getComponents())
        {
            if (component.getClass() == GoLFieldPanel.class)
            {
                for (Component field:((GoLFieldPanel) component).getComponents())
                {
                    field.setEnabled(golModel.isEditable());
                }
            }
        }
    }

    // LISTENERS //

    private class GoLStartStopListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            golModel.setRunning(!golModel.isRunning());
            if (golModel.isRunning())
            {
                golButtonPanel.getEditModeButton().setEnabled(false);
                golButtonPanel.getStartStopButton().setText(golButtonPanel.getStopButtonText());
            }
            else
            {
                golButtonPanel.getEditModeButton().setEnabled(true);
                golButtonPanel.getStartStopButton().setText(golButtonPanel.getStartButtonText());
            }
        }
    }

    private class GoLEditListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            golModel.setEditable(!golModel.isEditable());
            if (golModel.isEditable())
            {
                golButtonPanel.getStartStopButton().setEnabled(false);
                golButtonPanel.getEditModeButton().setBackground(Color.GREEN);
            }
            else
            {
                golButtonPanel.getStartStopButton().setEnabled(true);
                golButtonPanel.getEditModeButton().setBackground(Color.RED);
            }
            switchOnOffTextFields();
        }
    }

    private class GoLResetListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            golModel.clearModel();
            golButtonPanel.getParent().getParent().repaint();
        }
    }

    private class GoLRandomListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            golModel.randomModel();
            golButtonPanel.getParent().getParent().repaint();
        }
    }
}
