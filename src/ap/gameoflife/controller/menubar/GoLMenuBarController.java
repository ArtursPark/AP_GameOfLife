package ap.gameoflife.controller.menubar;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.model.map.GoLCell;
import ap.gameoflife.view.GoLFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Controller connecting the model with the frame, to access the menu bar.
 */
public class GoLMenuBarController
{
    // LOCAL //

    private GoLModel golModel;
    private GoLFrame golFrame;

    // CONSTRUCTORS //

    public GoLMenuBarController(GoLFrame in_golFrame, GoLModel in_golModel)
    {
        golFrame = in_golFrame;
        golModel = in_golModel;

        golFrame.getMenuItemOpenFile().addActionListener(new GoLOpenFileListener());
        golFrame.getMenuItemSaveFile().addActionListener(new GoLSaveFileListener());
        golFrame.getMenuItemCloseProgram().addActionListener(new GoLCloseProgramListener());
    }

    // LISTENERS //

    /**
     * Menu item listener for open file. This will read the file into a game of life map.
     */
    private class GoLOpenFileListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(golFrame) == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                int height = 0;
                int width = 0;
                try
                {
                    FileReader reader = new FileReader(file);
                    height = reader.read();
                    width = reader.read();
                    golModel.newGame(height, width);
                    golFrame.getGolMainPanel().getGolMenuPanel().getGolFieldPanel().getHeightInput().setText(String.valueOf(height));
                    golFrame.getGolMainPanel().getGolMenuPanel().getGolFieldPanel().getWidthInput().setText(String.valueOf(width));
                    for (int i = golModel.getMinHeight(); i <= golModel.getMaxRowIndex(); i++)
                    {
                        for (int j = golModel.getMinWidth(); j <= golModel.getMaxColumnIndex(); j++)
                        {
                            golModel.setMapValue(i, j, (byte) reader.read());
                        }
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Error, reading game of life file to map.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Menu item listener for save file. This will write the game of life map into a file.
     */
    private class GoLSaveFileListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(golFrame) == JFileChooser.APPROVE_OPTION)
            {
                File file = fileChooser.getSelectedFile();
                try
                {
                    FileWriter writer = new FileWriter(file);
                    GoLCell[][] golMap = golModel.getMap();
                    writer.write(golModel.getMaxRowIndex());
                    writer.write(golModel.getMaxColumnIndex());
                    for (int i = golModel.getMinHeight(); i <= golModel.getMaxRowIndex(); i++)
                    {
                        for (int j = golModel.getMinWidth(); j <= golModel.getMaxColumnIndex(); j++)
                        {
                            writer.write(golMap[i][j].getCellValue());
                        }
                    }
                    writer.close();
                }
                catch (IOException e)
                {
                    System.out.println("Error, writing game of life map to file.");
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Menu item listener used to exit from the application.
     */
    private static class GoLCloseProgramListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent actionEvent)
        {
            System.exit(0);
        }
    }
}
