package ap.gameoflife.view.menu.buttons;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing buttons contained in a grid bag layout.
 */
public class GoLButtonsPanel extends JPanel
{
    // LOCAL //

    private JButton randomButton = new JButton("RANDOM");
    private JButton resetButton = new JButton("RESET");
    private JButton editModeButton = new JButton("EDIT MODE");
    private JButton startStopButton = new JButton("START");

    private String startButtonText = "START";
    private String stopButtonText = "STOP";

    // CONSTRUCTORS //

    public GoLButtonsPanel()
    {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Adding buttons below
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        startStopButton.setBackground(Color.BLACK);
        startStopButton.setForeground(Color.WHITE);
        startStopButton.setBorderPainted(false);
        this.add(startStopButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        editModeButton.setBackground(Color.RED);
        editModeButton.setBorderPainted(false);
        this.add(editModeButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        resetButton.setBackground(Color.BLACK);
        resetButton.setForeground(Color.WHITE);
        resetButton.setBorderPainted(false);
        this.add(resetButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        randomButton.setBackground(Color.BLACK);
        randomButton.setForeground(Color.WHITE);
        randomButton.setBorderPainted(false);
        this.add(randomButton, gridBagConstraints);
    }

    // SETTERS AND GETTERS //

    public JButton getStartStopButton()
    {
        return startStopButton;
    }

    public JButton getResetButton()
    {
        return resetButton;
    }

    public JButton getEditModeButton()
    {
        return editModeButton;
    }

    public JButton getRandomButton()
    {
        return randomButton;
    }

    public String getStartButtonText()
    {
        return startButtonText;
    }

    public void setStartButtonText(String startButtonText)
    {
        this.startButtonText = startButtonText;
    }

    public String getStopButtonText()
    {
        return stopButtonText;
    }

    public void setStopButtonText(String stopButtonText)
    {
        this.stopButtonText = stopButtonText;
    }
}
