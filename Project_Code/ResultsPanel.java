import java.awt.*;
import javax.swing.*;

/**
 * This is a panel that holds what Test this was and the data from it in a table
 * 
 * @author Tyler Marois
 */
public class ResultsPanel extends JPanel {

    private JTable data;
    private JLabel text = new JLabel("", SwingConstants.CENTER);

    /**
     * Constructor for results panel
     * 
     * @param dataIn  The data to be shown in the table
     * @param columns The column headers
     * @param title   Title of the test performed
     */
    public ResultsPanel(String[][] dataIn, String[] columns, String title) {

        data = new JTable(dataIn, columns);
        data.setFont(new Font("Impact", Font.PLAIN, 15));
        data.setRowHeight(25);

        data.getTableHeader().setFont(new Font("Impact", Font.PLAIN, 20));
        data.getTableHeader().setBackground(Color.decode("#2c70de"));

        JScrollPane sp = new JScrollPane(data);
        sp.setPreferredSize(new Dimension(450, 200));

        text.setFont(new Font("Impact", Font.PLAIN, 60));
        text.setForeground(Color.black);
        text.setText(title);
        this.setLayout(new BorderLayout());
        this.add(text, BorderLayout.PAGE_START);
        this.add(sp, BorderLayout.CENTER);
        this.setSize(500, 200);
    }
}
