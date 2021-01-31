package ap.gameoflife.model.map;

/**
 * Game of life cells, which should be set only using the ALIVE or DEAD variables.
 */
public class GoLCell
{
    // GLOBAL //

    public final static byte ALIVE = 1;
    public final static byte DEAD = 0;

    // LOCAL //

    byte cellValue = 0;

    // CONSTRUCTORS //

    public GoLCell()
    {
        this((byte) 0);
    }

    public GoLCell(byte in_cellValue)
    {
        cellValue = in_cellValue;
    }

    // SETTERS AND GETTERS //

    /*
        Setters and getters are mutex locked because
        we use thread pools to generate the next game
        of life map.
    */

    /*
      Hmm, although at the same time mutex might not
      be required...

      1) Generation of new game happens on swing
      thread.

      2) During generation of new game we only
      write to new map, no thread writes to
      same place twice.

      3) Unable to edit the map during generation
      calculations.
     */

    /**
     * Method used to retrieve the byte value within the cell, use GoLCell.ALIVE or GOLCell.DEAD to compare the result
     * @return : Returns a byte value, 1 is ALIVE and 0 is DEAD, use GoLCell default values
     */
    public synchronized byte getCellValue()
    {
        return cellValue;
    }

    /**
     * Method used to set the cell value based on the byte parameter
     * @param in_cellValue : Byte parameter, 1 is ALIVE and 0 is DEAD, suggested to be set using GoLCell.ALIVE or GOLCell.DEAD
     */
    public synchronized void setCellValue(byte in_cellValue)
    {
        cellValue = (in_cellValue > DEAD) ? ALIVE : DEAD;
    }

    /**
     * Method used to set the cell value based on the cell parameter
     * @param in_cell : Cell parameter
     */
    public synchronized void setCellValue(GoLCell in_cell)
    {
        cellValue = in_cell.getCellValue();
    }

    /**
     * Method used to switch the state of the cell, from being ALIVE to DEAD, and DEAD to ALIVE
     */
    public synchronized void switchCell()
    {
        cellValue = (cellValue == ALIVE) ? DEAD : ALIVE;
    }
}
