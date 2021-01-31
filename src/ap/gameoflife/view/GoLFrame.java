package ap.gameoflife.view;

import ap.gameoflife.controller.menubar.GoLMenuBarController;
import ap.gameoflife.controller.mouse.GoLGameMouseController;
import ap.gameoflife.controller.buttons.GoLButtonsController;
import ap.gameoflife.controller.fields.GoLFieldsController;
import ap.gameoflife.controller.timer.GoLTimeController;
import ap.gameoflife.model.GoLModel;

import javax.swing.*;
import java.awt.*;

/**
 * Frame of the map, here the model and all the views/panels are connected. Including, the menu bar and frame.
 */
public class GoLFrame extends JFrame
{
    // GLOBAL //

    private final static int FRAME_MIN_HEIGHT = 700;
    private final static int FRAME_MIN_WIDTH = 700;

    // LOCAL //

    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItemOpenFile;
    private JMenuItem menuItemSaveFile;
    private JMenuItem menuItemCloseProgram;

    private GoLModel golModel = new GoLModel();
    private GoLMainPanel golMainPanel;

    // CONSTRUCTORS //

    public GoLFrame(String in_appName)
    {
        super(in_appName);
        addSystemLookAndFeel();

        golMainPanel = new GoLMainPanel(golModel);

        // Set up menu bar
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        // Set up menu items
        menuItemOpenFile = new JMenuItem("Open GoL Map File");
        menu.add(menuItemOpenFile);
        menuItemSaveFile = new JMenuItem("Save GoL Map File");
        menu.add(menuItemSaveFile);
        menuItemCloseProgram = new JMenuItem("Close");
        menu.add(menuItemCloseProgram);
        this.setJMenuBar(menuBar);

        // Set up frame
        this.setContentPane(golMainPanel);
        this.setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up controllers
        GoLMenuBarController golMenuBarController = new GoLMenuBarController(this, golModel);
        GoLGameMouseController golGameMouseController = new GoLGameMouseController(golMainPanel.getGolGamePanel().getGolMapPanel(), golModel);
        GoLButtonsController golButtonsController = new GoLButtonsController(golMainPanel.getGolMenuPanel().getGolButtonsPanel(), golModel);
        GoLFieldsController golFieldsController = new GoLFieldsController(golMainPanel.getGolMenuPanel().getGolFieldPanel(), golModel);
        GoLTimeController golTimeController = new GoLTimeController(golMainPanel.getGolGamePanel(), golModel);
    }

    /**
     * Use this to have the app look like the OS that its being run on.
     */
    private void addSystemLookAndFeel()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            System.out.println("Error, setting system feel and look.");
            e.printStackTrace();
        }
    }

    // SETTERS AND GETTERS //

    public GoLMainPanel getGolMainPanel()
    {
        return golMainPanel;
    }
    public JMenu getMenu()
    {
        return menu;
    }
    public JMenu getSubmenu()
    {
        return submenu;
    }
    public JMenuItem getMenuItemOpenFile()
    {
        return menuItemOpenFile;
    }
    public JMenuItem getMenuItemSaveFile()
    {
        return menuItemSaveFile;
    }
    public JMenuItem getMenuItemCloseProgram()
    {
        return menuItemCloseProgram;
    }
}
