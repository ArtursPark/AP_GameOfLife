package ap.gameoflife.controller.fields;

import ap.gameoflife.model.GoLModel;
import ap.gameoflife.view.menu.fields.GoLFieldPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Fields controller, connecting the model states to the field panel.
 */
public class GoLFieldsController
{
    // LOCAL //

    private GoLFieldPanel golFieldPanel;
    private GoLModel golModel;

    // CONSTRUCTORS //

    public GoLFieldsController(GoLFieldPanel in_golFieldPanel, GoLModel in_golModel)
    {
        golFieldPanel = in_golFieldPanel;
        golModel = in_golModel;

        // Add document listeners to the fields
        golFieldPanel.getWidthInput().getDocument().addDocumentListener(new GoLWidthListener());
        golFieldPanel.getHeightInput().getDocument().addDocumentListener(new GoLHeightListener());
        golFieldPanel.getPeriodInput().getDocument().addDocumentListener(new GoLPeriodListener());
        golFieldPanel.getSquareSizeInput().getDocument().addDocumentListener(new GoLSquareSizeListener());

        // Set up default values
        golFieldPanel.getPeriodInput().setText(Integer.toString(golModel.getPeriod()));
        golFieldPanel.getPeriodInput().setEnabled(golModel.isEditable());
        golFieldPanel.getWidthInput().setText(Integer.toString(golModel.getMaxColumnIndex()));
        golFieldPanel.getWidthInput().setEnabled(golModel.isEditable());
        golFieldPanel.getHeightInput().setText(Integer.toString(golModel.getMaxRowIndex()));
        golFieldPanel.getHeightInput().setEnabled(golModel.isEditable());
        golFieldPanel.getSquareSizeInput().setText(Integer.toString(golModel.getSquareSize()));
        golFieldPanel.getSquareSizeInput().setEnabled(golModel.isEditable());
    }

    // USE CASE //

    private void updateMapWidth(int in_width)
    {
        if (in_width < 1)
            return;

        if (in_width == golModel.getMaxColumnIndex())
            return;

        golModel.newGame(golModel.getMaxRowIndex(), in_width);
        SwingUtilities.getRoot(golFieldPanel).repaint();
    }

    private void updateMapHeight(int in_height)
    {
        if (in_height < 1)
            return;

        if (in_height == golModel.getMaxRowIndex())
            return;

        golModel.newGame(in_height, golModel.getMaxColumnIndex());
        SwingUtilities.getRoot(golFieldPanel).repaint();
    }

    private void updateTimerPeriod(int in_period)
    {
        if (golModel.getPeriod() != in_period)
            golModel.setPeriod(in_period);
    }

    private void updateSquareSizePeriod(int in_squareSize)
    {
        if (in_squareSize < 1)
            in_squareSize = 1;

        if (golModel.getSquareSize() != in_squareSize)
            golModel.setSquareSize(in_squareSize);

        SwingUtilities.getRoot(golFieldPanel).repaint();
    }

    // LISTENERS //

    private class GoLWidthListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        private void updateDimensions()
        {
            String newWidthString = golFieldPanel.getWidthInput().getText().replace(",", "");

            if (newWidthString.equals(""))
                return;

            int newWidth = Integer.parseInt(newWidthString);
            updateMapWidth(newWidth);
        }
    }

    private class GoLHeightListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        private void updateDimensions()
        {
            String newHeightString = golFieldPanel.getHeightInput().getText().replace(",", "");

            if (newHeightString.equals(""))
                return;

            int newHeight = Integer.parseInt(newHeightString);
            updateMapHeight(newHeight);
        }
    }

    private class GoLPeriodListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        private void updateDimensions()
        {
            String newPeriodString = golFieldPanel.getPeriodInput().getText().replace(",", "");

            if (newPeriodString.equals(""))
                return;

            int newPeriod = Integer.parseInt(newPeriodString);
            updateTimerPeriod(newPeriod);
        }
    }

    private class GoLSquareSizeListener implements DocumentListener
    {
        @Override
        public void insertUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent)
        {
            updateDimensions();
        }

        private void updateDimensions()
        {
            String newSquareSizeString = golFieldPanel.getSquareSizeInput().getText().replace(",", "");

            if (newSquareSizeString.equals(""))
                return;

            int newSquareSize = Integer.parseInt(newSquareSizeString);
            updateSquareSizePeriod(newSquareSize);
        }
    }
}
