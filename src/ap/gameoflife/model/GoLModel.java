package ap.gameoflife.model;

import ap.gameoflife.model.logic.GoLLogic;
import ap.gameoflife.model.map.GoLCell;
import ap.gameoflife.model.map.GoLMap;

/**
 * Instead of creating a new instance of map every time the game of life moves to the next generation, the model will
 * toggle between the two maps. True map is used when toggle is true and false map when toggle is false. This will save
 * time from having to allocate a new game of life map object each time. The name of this technique is called Ping-Pong Buffering.
 */
public class GoLModel
{
    // DEFAULT //

    public final static int PERIOD_DEFAULT = 500;

    private final static int ROWS_DEFAULT = 50;
    private final static int COLUMNS_DEFAULT = 50;

    // LOCAL //

    private GoLMap trueMap;
    private GoLMap falseMap;
    private boolean toggle = true;

    private int period;
    private int squareSize = 10;

    private boolean running = false;
    private boolean editable = false;

    // CONSTRUCTORS //

    public GoLModel(int in_rows, int in_columns)
    {
        trueMap = new GoLMap(in_rows, in_columns);
        falseMap = new GoLMap(in_rows, in_columns);
        setPeriod(PERIOD_DEFAULT);
    }

    public GoLModel()
    {
        this(ROWS_DEFAULT, COLUMNS_DEFAULT);
    }

    // USE CASE //

    /**
     * Initialize the new maps with new dimensions.
     * @param in_rows : New dimensions for rows.
     * @param in_columns : New dimensions for columns.
     */
    public void newGame(int in_rows, int in_columns)
    {
        trueMap = new GoLMap(in_rows, in_columns);
        falseMap = new GoLMap(in_rows, in_columns);
    }

    /**
     * Initializing the new game, will just clear the current maps.
     */
    public void newGame()
    {
        clearModel();
    }

    public void nextGeneration()
    {
        if (!isRunning())
            return;

        if (toggle)
            GoLLogic.nextGenerationCalculateConcurrently(trueMap, falseMap);
        else
            GoLLogic.nextGenerationCalculateConcurrently(falseMap, trueMap);
        toggle = !toggle;
    }

    public void randomModel()
    {
        if (toggle)
            trueMap.generateRandomMapConcurrently();
        else
            falseMap.generateRandomMapConcurrently();
    }

    public void clearModel()
    {
        trueMap.clearMap();
        falseMap.clearMap();
    }

    // SETTERS AND GETTERS //

    public GoLCell[][] getMap()
    {
        if (toggle)
            return trueMap.getMap();
        else
            return falseMap.getMap();
    }

    public void setMapValue(int in_row, int in_column, byte in_value)
    {
        if (toggle)
            trueMap.setMapValue(in_row, in_column, in_value);
        else
            falseMap.setMapValue(in_row, in_column, in_value);
    }

    public byte getMapValue(int in_row, int in_column)
    {
        if (toggle)
            return trueMap.getMapValue(in_row, in_column);
        else
            return falseMap.getMapValue(in_row, in_column);
    }

    public byte getOtherMapValue(int in_row, int in_column)
    {
        if (!toggle)
            return trueMap.getMapValue(in_row, in_column);
        else
            return falseMap.getMapValue(in_row, in_column);
    }

    public int getMaxRowIndex()
    {
        return trueMap.getMaxRowIndex();
    }

    public int getMaxColumnIndex()
    {
        return trueMap.getMaxColumnIndex();
    }

    public int getMinHeight()
    {
        return GoLMap.getMinRows();
    }

    public int getMinWidth()
    {
        return GoLMap.getMinColumns();
    }

    public int getPeriod()
    {
        return period;
    }

    public void setPeriod(int in_period)
    {
        this.period = in_period;
    }

    public int getSquareSize()
    {
        return squareSize;
    }

    public void setSquareSize(int in_squareSize)
    {
        this.squareSize = in_squareSize;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setRunning(boolean in_running)
    {
        this.running = in_running;
    }

    public boolean isEditable()
    {
        return editable;
    }

    public void setEditable(boolean in_editable)
    {
        this.editable = in_editable;
    }
}
