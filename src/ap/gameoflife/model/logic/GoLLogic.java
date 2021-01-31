package ap.gameoflife.model.logic;

import ap.gameoflife.model.map.GoLCell;
import ap.gameoflife.model.map.GoLMap;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Game of life logic, used to control the generation of the new game of life map.
 */
public class GoLLogic
{
    // USE CASE //

    /**
     * @param in_oldMap : Game of life old map.
     * @param in_newMap : Game of life new map generation.
     */
    public static void nextGenerationCalculate(GoLMap in_oldMap, GoLMap in_newMap)
    {
        for (int rowIndex = GoLMap.getMinRows(); rowIndex <= in_newMap.getMaxRowIndex(); ++rowIndex)
        {
            for (int columnIndex = GoLMap.getMinColumns(); columnIndex <= in_newMap.getMaxColumnIndex(); ++columnIndex)
            {
                    in_newMap.setMapValue(rowIndex, columnIndex, getNewGenerationValue(in_oldMap, rowIndex, columnIndex));
            }
        }
    }

    /**
     * @param in_oldMap : Game of life old map.
     * @param in_newMap : Game of life new map generation.
     */
    public static void nextGenerationCalculateConcurrently(GoLMap in_oldMap, GoLMap in_newMap)
    {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        if (in_oldMap.getMaxRowIndex() < GoLMap.MINIMUM_LENGTH_FOR_CONCURRENCY || in_oldMap.getMaxColumnIndex() < GoLMap.MINIMUM_LENGTH_FOR_CONCURRENCY || numberOfThreads == 1)
        {
            nextGenerationCalculate(in_oldMap, in_newMap);
            return;
        }

        // Get each thread to read there own segments of the columns, so that there is higher chances of memory
        // being coalesced for the working threads.

        int numberOfColumns = in_oldMap.getMaxColumnIndex();
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
                nextSegmentGenerationCalculate(in_oldMap, in_newMap, finalStartColumn, finalEndColumn);
                latch.countDown();
            };
            service.submit(runnable);
            startColumn = endColumn + 1;
        }

        // Now do some processing on main thread as well
        endColumn = startColumn + columnsPerThread;
        nextSegmentGenerationCalculate(in_oldMap, in_newMap, startColumn, endColumn);

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

    // PRIVATE //

    /**
     * @param in_oldMap : Game of life map, old copy.
     * @param in_newMap : Game of life map, new generation.
     * @param in_startColumn : Starting column index.
     * @param in_endColumn : Last column index.
     */
    private static void nextSegmentGenerationCalculate(GoLMap in_oldMap, GoLMap in_newMap, int in_startColumn, int in_endColumn)
    {
        for (int rowIndex = GoLMap.getMinRows(); rowIndex <= in_newMap.getMaxRowIndex(); rowIndex++)
        {
            for (int columnIndex = in_startColumn; columnIndex <= in_endColumn; columnIndex++)
            {
                in_newMap.setMapValue(rowIndex, columnIndex, getNewGenerationValue(in_oldMap, rowIndex, columnIndex));
            }
        }
    }

    /**
     * @param in_map : Game of life map.
     * @param in_row : Row index.
     * @param in_column : Column index.
     * @return : Byte value representing the cell state for the next generation.
     */
    private static byte getNewGenerationValue(GoLMap in_map, int in_row, int in_column)
    {
        int aliveCount = countEdges(in_map, in_row, in_column);
        if ((aliveCount > 3) || (aliveCount < 2))
            return GoLCell.DEAD;
        else if ((in_map.getMapValue(in_row, in_column) == 0) && (aliveCount == 3))
            return GoLCell.ALIVE;
        else
            return in_map.getMapValue(in_row, in_column);
    }

    /**
     * @param in_map : Game of life map.
     * @param in_row : Row index.
     * @param in_column : Column index.
     * @return : Integer value representing number of ALIVE cells.
     */
    private static int countEdges(GoLMap in_map, int in_row, int in_column)
    {
        int aliveCount = 0;

        aliveCount += in_map.getMapValue(in_row - 1, in_column - 1);
        aliveCount += in_map.getMapValue(in_row - 1, in_column);
        aliveCount += in_map.getMapValue(in_row - 1, in_column + 1);

        aliveCount += in_map.getMapValue(in_row, in_column - 1);
        aliveCount += in_map.getMapValue(in_row, in_column + 1);

        aliveCount += in_map.getMapValue(in_row + 1, in_column - 1);
        aliveCount += in_map.getMapValue(in_row + 1, in_column);
        aliveCount += in_map.getMapValue(in_row + 1, in_column + 1);

        return aliveCount;
    }

    /**
     * @param in_map : Game of life map that you want to examine for any anomalies on the edges.
     * @return : Boolean, true if there is a ALIVE cell on the edges of the map. False if there
     * are no ALIVE cells on the edges.
     */
    private static boolean checkIfMapBoundaryIsValid(GoLMap in_map)
    {
        // Check north boundary
        int rowIndex = 0;
        int colIndex = 0;
        for (colIndex = 0; colIndex < in_map.getMaxColumnIndex() + 1; colIndex++)
        {
            if (in_map.getMapValue(rowIndex, colIndex) == GoLCell.ALIVE)
            {
                System.out.printf("Warning North : Row %d, Column %d\n", rowIndex, colIndex);
                return false;
            }
        }

        // Check east boundary
        rowIndex = 0;
        colIndex = in_map.getMaxColumnIndex() + 1;
        for (rowIndex = 0; rowIndex < in_map.getMaxRowIndex() + 1; rowIndex++)
        {
            if (in_map.getMapValue(rowIndex, colIndex) == GoLCell.ALIVE)
            {
                System.out.printf("Warning East : Row %d, Column %d\n", rowIndex, colIndex);
                return false;
            }
        }

        // Check south boundary
        rowIndex = in_map.getMaxRowIndex() + 1;
        colIndex = 0;
        for (colIndex = 0; colIndex < in_map.getMaxColumnIndex() + 1; colIndex++)
        {
            if (in_map.getMapValue(rowIndex, colIndex) == GoLCell.ALIVE)
            {
                System.out.printf("Warning South : Row %d, Column %d\n", rowIndex, colIndex);
                return false;
            }
        }

        // Check west boundary
        rowIndex = 0;
        colIndex = 0;
        for (rowIndex = 0; rowIndex < in_map.getMaxRowIndex() + 1; rowIndex++)
        {
            if (in_map.getMapValue(rowIndex, colIndex) == GoLCell.ALIVE)
            {
                System.out.printf("Warning West : Row %d, Column %d\n", rowIndex, colIndex);
                return false;
            }
        }
        return true;
    }
}