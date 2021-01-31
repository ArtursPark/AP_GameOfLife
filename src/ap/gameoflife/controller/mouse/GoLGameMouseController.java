package ap.gameoflife.controller.mouse;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.model.map.GoLCell;
import ap.gameoflife.model.map.GoLMap;
import ap.gameoflife.view.game.GoLMapPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse controller, connecting the model and map panel. Using this controller we can edit the game of life map.
 */
public class GoLGameMouseController implements MouseListener
{
    // LOCAL //

    private GoLMapPanel golMapPanel;
    private GoLModel golModel;

    // CONSTRUCTORS //

    public GoLGameMouseController(GoLMapPanel in_golMapPanel, GoLModel in_golModel)
    {
        golMapPanel = in_golMapPanel;
        golModel = in_golModel;

        // Add action listeners to the buttons
        golMapPanel.addMouseListener(this);
    }

    // OVERRIDE //

    /**
     * Set the value on the game of life map based on the mouse press of the mouse being pressed on and paint it.
     * @param mouseEvent : See java documentation.
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent)
    {
        if (!golModel.isEditable())
            return;

        Point p = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(p, golMapPanel);

        int height_p = p.y / golModel.getSquareSize() + GoLMap.getMinRows();
        int width_p = p.x / golModel.getSquareSize() + GoLMap.getMinColumns();

        if (height_p < 0 || height_p > golModel.getMaxRowIndex() ||
                width_p < 0 || width_p > golModel.getMaxColumnIndex())
            return;

        byte value = golModel.getMapValue(height_p, width_p);
        if (value == GoLCell.ALIVE)
            golModel.setMapValue(height_p, width_p, GoLCell.DEAD);
        else
            golModel.setMapValue(height_p, width_p, GoLCell.ALIVE);
        golMapPanel.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent)
    {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent)
    {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent)
    {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent)
    {
    }
}
