package ap.gameoflife.view.game;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.model.map.GoLCell;
import ap.gameoflife.model.map.GoLMap;

import javax.swing.*;
import java.awt.*;

/**
 * This class acts more like a controller than view. Paints the game of life map onto the panel.
 */
public class GoLMapPanel extends JPanel
{
    // DEFAULT //

    private final static int BORDER_SIZE = 10;
    private final static int PAINT_OFFSET_SIZE = 1; // By adding this we paint a cell ahead, giving a smoother scrolling.

    // LOCAL //

    private GoLModel golModel;
    private JViewport viewport;

    // CONSTRUCTORS //

    public GoLMapPanel(GoLModel in_golModel, JViewport in_viewport)
    {
        golModel = in_golModel;
        this.viewport = in_viewport;
        setBackground(Color.BLACK);
    }

    // OVERRIDE //

    /**
     * The paint override will only paint the segment of the map that is being viewed. Also take a
     * note that not re-painting the DEAD cells, increased the code performance by almost 4 times.
     * The paint component also draws the borders which are good to have when trying to edit the
     * map.
     *
     * @param in_graph : See java documentation.
     */
    @Override
    public void paintComponent(Graphics in_graph)
    {
        super.paintComponent(in_graph);

        // First, calculate height/width
        int minHeight = this.viewport.getViewPosition().y / golModel.getSquareSize() + GoLMap.getMinRows() - PAINT_OFFSET_SIZE;
        int maxHeight = this.viewport.getSize().height / golModel.getSquareSize() + minHeight + 2 * PAINT_OFFSET_SIZE;

        int minWidth = this.viewport.getViewPosition().x / golModel.getSquareSize() + GoLMap.getMinColumns() - PAINT_OFFSET_SIZE;
        int maxWidth = this.viewport.getSize().width / golModel.getSquareSize() + minWidth + 2 * PAINT_OFFSET_SIZE;

        if (maxHeight > golModel.getMaxRowIndex())
            maxHeight = golModel.getMaxRowIndex();

        if (maxWidth > golModel.getMaxColumnIndex())
            maxWidth = golModel.getMaxColumnIndex();

        // Second, go through calculated height/width, and paint the cells
        for (int i = minHeight; i <= maxHeight; i++)
        {
            for (int j = minWidth; j <= maxWidth; j++)
            {
                // Do not paint DEAD cells if they are in the same location between new and old maps
                if (golModel.getOtherMapValue(i, j) == golModel.getMapValue(i, j) && golModel.getMapValue(i, j) == GoLCell.DEAD)
                    continue;

                if (golModel.getMapValue(i, j) == GoLCell.ALIVE)
                    in_graph.setColor(Color.GREEN);
                else
                    in_graph.setColor(Color.BLACK);

                in_graph.fillRect((j - 1) * golModel.getSquareSize(), (i - 1) * golModel.getSquareSize(), golModel.getSquareSize(), golModel.getSquareSize());
            }
        }

        // Third, paint boundaries
        // Paint south boundary
        in_graph.setColor(Color.WHITE);
        int rowInitial = golModel.getMaxRowIndex() * golModel.getSquareSize();
        int height = BORDER_SIZE;
        int columnInitial = minWidth;
        int width = golModel.getMaxColumnIndex() * golModel.getSquareSize() + BORDER_SIZE;
        in_graph.fillRect(columnInitial, rowInitial, width, height);
        // Paint east boundary
        rowInitial = minHeight;
        height = maxHeight * golModel.getSquareSize() + BORDER_SIZE;
        columnInitial = maxWidth * golModel.getSquareSize();
        width = BORDER_SIZE;
        in_graph.fillRect(columnInitial, rowInitial, width, height);
    }

    @Override
    public Dimension getPreferredSize()
    {
        int prefHeight = golModel.getSquareSize() * golModel.getMaxRowIndex();
        int prefWidth = golModel.getSquareSize() * golModel.getMaxColumnIndex();
        return new Dimension(prefWidth, prefHeight);
    }
}
