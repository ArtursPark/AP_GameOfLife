package ap.gameoflife.view.menu.fields;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class GoLFieldPanel extends JPanel
{
    // LOCAL //

    private final JLabel periodLabel = new JLabel("<HTML><U>PERIOD</U></HTML>");
    private final JLabel heightLabel = new JLabel("<HTML><U>HEIGHT</U></HTML>");
    private final JLabel widthLabel = new JLabel("<HTML><U>WIDTH</U></HTML>");
    private final JLabel squareSizeLabel = new JLabel("<HTML><U>SQUARE SIZE</U></HTML>");

    private JFormattedTextField periodInput;
    private JFormattedTextField heightInput;
    private JFormattedTextField widthInput;
    private JFormattedTextField squareSizeInput;

    // CONSTRUCTORS //

    public GoLFieldPanel()
    {
        this.setLayout(new GridBagLayout());
        this.setBackground(Color.BLACK);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Adding field labels below
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 0;
        gridBagConstraints.insets = new Insets(0, 40, 0, 0);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        periodLabel.setBackground(Color.BLACK);
        periodLabel.setForeground(Color.WHITE);
        this.add(periodLabel, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        widthLabel.setBackground(Color.BLACK);
        widthLabel.setForeground(Color.WHITE);
        this.add(widthLabel, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        heightLabel.setBackground(Color.BLACK);
        heightLabel.setForeground(Color.WHITE);
        this.add(heightLabel, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        squareSizeLabel.setBackground(Color.BLACK);
        squareSizeLabel.setForeground(Color.WHITE);
        this.add(squareSizeLabel, gridBagConstraints);

        // Adding field input below
        gridBagConstraints.insets = new Insets(10, 5, 0, 5);

        NumberFormat longFormat = NumberFormat.getIntegerInstance();
        NumberFormatter numberFormatter = new NumberFormatter(longFormat);
        periodInput = new JFormattedTextField(numberFormatter);
        heightInput = new JFormattedTextField(numberFormatter);
        widthInput = new JFormattedTextField(numberFormatter);
        squareSizeInput = new JFormattedTextField(numberFormatter);

        numberFormatter.setValueClass(Integer.class);
        numberFormatter.setAllowsInvalid(false); // will only allow numbers

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        periodInput.setBackground(Color.WHITE);
        periodInput.setForeground(Color.BLACK);
        this.add(periodInput, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        widthInput.setBackground(Color.WHITE);
        widthInput.setForeground(Color.BLACK);
        this.add(widthInput, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        heightInput.setBackground(Color.WHITE);
        heightInput.setForeground(Color.BLACK);
        this.add(heightInput, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        squareSizeInput.setBackground(Color.WHITE);
        squareSizeInput.setForeground(Color.BLACK);
        this.add(squareSizeInput, gridBagConstraints);
    }

    // SETTERS AND GETTERS //

    public JFormattedTextField getPeriodInput()
    {
        return periodInput;
    }
    public JFormattedTextField getHeightInput()
    {
        return heightInput;
    }
    public JFormattedTextField getWidthInput()
    {
        return widthInput;
    }
    public JFormattedTextField getSquareSizeInput()
    {
        return squareSizeInput;
    }
}
