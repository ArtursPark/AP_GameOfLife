package ap.gameoflife.model.map;

import java.util.concurrent.*;

/**
 * Game of life map. The map is created with padding as to avoid unnecessary and expensive boundary checks.
 *
 * TODO : Potential Changes :
 *  1) Changing the map structure. If the matrix becomes a sparse matrix and big enough, it is less expensive to hold the matrix information
 *  in a CSR/ELL/JDS/COO storage format or even a hybrid.
 */
public class GoLMap
{
    // DEFAULT //

    public final static int MIN_ROWS = 1;
    public final static int MIN_COLUMNS = 1;
    public final static int MINIMUM_LENGTH_FOR_CONCURRENCY = 100;

    private final static int PADDING_SIZE = 2;
    private final static byte DEFAULT_CELL_VALUE = 0;

    // LOCAL //

    private GoLCell[][] map;

    private int maxRowIndex = MIN_ROWS;
    private int maxColumnIndex = MIN_COLUMNS;

    private int trueRowLength = MIN_ROWS;
    private int trueColumnLength = MIN_COLUMNS;

    // CONSTRUCTORS //

    public GoLMap(int in_rows, int in_columns)
    {
        if (in_rows < GoLMap.MIN_ROWS || in_columns < GoLMap.MIN_COLUMNS)
        {
            System.out.printf("Warning : Map() : input size is smaller than MIN_WIDTH(%d)/MIN_HEIGHT(%d) : in_height = %d, in_width = %d\n", GoLMap.MIN_COLUMNS, GoLMap.MIN_ROWS, in_rows, in_columns);
            return;
        }

        /* Padded size 1->H+1 */
        /* Padded size if H = 10, 1->11 */
        maxRowIndex = in_rows;
        maxColumnIndex = in_columns;

        /* Padded size 0->H+2 */
        trueRowLength = in_rows + PADDING_SIZE; // Height padding set to 2
        trueColumnLength = in_columns + PADDING_SIZE; // Width padding set to 2

        map = new GoLCell[trueRowLength][trueColumnLength];
        initializeMap(map);
    }

    // USE CASE //

    /**
     * @param in_map : input map that needs to be initialized.
     */
    private void initializeMap(GoLCell[][] in_map)
    {
        for (int i = 0; i < trueRowLength; i++)
        {
            in_map[i] = new GoLCell[trueColumnLength];
            for (int j = 0; j < trueColumnLength; j++)
            {
                in_map[i][j] = new GoLCell(GoLMap.DEFAULT_CELL_VALUE);
            }
        }
    }

    /**
     * This will generate random values on this map.
     */
    public void generateRandomMap()
    {
        for (int r = MIN_ROWS; r <= maxRowIndex; r++)
        {
            for (int c = MIN_COLUMNS; c <= maxColumnIndex; c++)
            {
                double v = Math.random();
                if (v < 0.5)
                    setMapValue(r, c, GoLCell.DEAD);
                else
                    setMapValue(r, c, GoLCell.ALIVE);
            }
        }
    }

    /**
     * This will set random values through out the map concurrently.
     */
    public void generateRandomMapConcurrently()
    {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        if (getMaxRowIndex() < MINIMUM_LENGTH_FOR_CONCURRENCY || getMaxColumnIndex() < MINIMUM_LENGTH_FOR_CONCURRENCY || numberOfThreads == 1)
        {
            generateRandomMap();
            return;
        }

        // Get each thread to read there own segments of the columns, so that there is higher chances of memory
        // being coalesced for the working threads.

        int numberOfColumns = getMaxColumnIndex();
        int columnsPerThread = numberOfColumns / numberOfThreads - 1;
        int offsetColumns = numberOfColumns % numberOfThreads;

        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads - 1);

        int startColumn = GoLMap.getMinColumns();
        int endColumn = -1;
        for (int threadIndex = 0; threadIndex < (numberOfThreads - 1); ++threadIndex)
        {
            if (offsetColumns-- > 0) // Reduce offset value and add it to the end column until it reaches zero
                endColumn = startColumn + columnsPerThread + 1;
            else
                endColumn = startColumn + columnsPerThread;

            int finalStartColumn = startColumn;
            int finalEndColumn = endColumn;
            Runnable runnable = () ->
            {
                setRandomMapValuesForColumns(finalStartColumn, finalEndColumn);
                latch.countDown();
            };
            service.submit(runnable);
            startColumn = endColumn + 1;
        }

        // Now do some processing on main thread as well
        endColumn = startColumn + columnsPerThread;
        setRandomMapValuesForColumns(startColumn, endColumn);

        try
        {
            latch.await();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error, waiting for the latch.");
            e.printStackTrace();
        }
    }

    private void setRandomMapValuesForColumns(int in_startColumn, int in_endColumn)
    {
        for (int rowIndex = GoLMap.getMinRows(); rowIndex <= getMaxRowIndex(); rowIndex++)
        {
            for (int columnIndex = in_startColumn; columnIndex <= in_endColumn; columnIndex++)
            {
                setMapValue(rowIndex, columnIndex, getRandomCellValue());
            }
        }
    }

    private byte getRandomCellValue()
    {
        double v = ThreadLocalRandom.current().nextDouble();
        if (v < 0.5)
            return GoLCell.DEAD;
        else
            return GoLCell.ALIVE;
    }

    /**
     * Will set each cell value to DEAD.
     */
    public void clearMap()
    {
        for (int r = MIN_ROWS; r <= maxRowIndex; ++r)
        {
            for (int c = MIN_COLUMNS; c <= maxColumnIndex; ++c)
            {
                setMapValue(r, c, GoLCell.DEAD);
            }
        }
    }

    public void printMap()
    {
        System.out.println();
        for (int r = MIN_ROWS; r <= maxRowIndex; ++r)
            for (int c = MIN_COLUMNS; c <= maxColumnIndex; ++c)
                System.out.print(this.getMapValue(r, c) + " ");
        System.out.println();
    }

    // SETTER AND GETTERS //

    public GoLCell[][] getMap()
    {
        return map;
    }

    public int getMaxRowIndex()
    {
        return maxRowIndex;
    }

    public int getMaxColumnIndex()
    {
        return maxColumnIndex;
    }

    public static int getMinRows()
    {
        return MIN_ROWS;
    }

    public static int getMinColumns()
    {
        return MIN_COLUMNS;
    }

    public byte getMapValue(int in_row, int in_column)
    {
        return map[in_row][in_column].getCellValue();
    }

    public void setMapValue(int in_row, int in_column, byte in_val)
    {
        try
        {
            map[in_row][in_column].setCellValue(in_val);
        }
        catch (IndexOutOfBoundsException e)
        {
            System.out.printf("Warning : Out of bounds, row = %d, column = %d\n", in_row, in_column);
        }
    }
}
