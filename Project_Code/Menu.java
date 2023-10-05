import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The main menu for my project this will take you to any of the three spots in
 * my project
 * 
 * @author Tyler Marois
 */
public class Menu extends JFrame implements ActionListener {

    private JButton watcher = new JButton("Process Watcher");
    private JButton raw = new JButton("Raw Results");
    private JButton group = new JButton("Group Results");

    /**
     * Constructor for the menu
     */
    public Menu() {
        super("Main Menu");

        Font font = new Font("Impact", Font.PLAIN, 50);

        watcher.setAlignmentX(Component.CENTER_ALIGNMENT);
        watcher.setFont(font);
        watcher.setFocusPainted(false);
        watcher.setForeground(Color.black);
        watcher.addActionListener(this);

        raw.setAlignmentX(Component.CENTER_ALIGNMENT);
        raw.setFont(font);
        raw.setFocusPainted(false);
        raw.setForeground(Color.black);
        raw.addActionListener(this);

        group.setAlignmentX(Component.CENTER_ALIGNMENT);
        group.setFont(font);
        group.setFocusPainted(false);
        group.setForeground(Color.black);
        group.addActionListener(this);

        this.add(watcher);
        this.add(raw);
        this.add(group);
        this.setLayout(new GridLayout(3, 1));
        this.getContentPane().setBackground(Color.decode("#4e535c"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * This is an implemented method from ActionListener,
     * I start by opening one of the table frames in a seperate thread to load the
     * data while playing the loading animation in the meantime
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == watcher) {
            this.dispose();
            JButton go = new JButton("Test!");
            final Inputs inputsPanel = new Inputs();
            final JLabel wrong = new JLabel("Wrong Input, Try Again!", SwingConstants.CENTER);
            final JFrame inputs = new JFrame("Input");
            wrong.setVisible(false);
            go.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    final String size = inputsPanel.size.getText();
                    final String speed = inputsPanel.speed.getText();
                    try {
                        int sizeNum = Integer.parseInt(size);
                        int speedNum = Integer.parseInt(speed);
                        new ProcessWatcher(sizeNum, speedNum);
                        inputs.dispose();
                    } catch (Exception ex) {
                        wrong.setVisible(true);
                        inputs.pack();
                    }
                }
            });

            inputs.setLayout(new BorderLayout(25, 25));
            inputs.add(inputsPanel, BorderLayout.CENTER);
            inputs.add(go, BorderLayout.EAST);
            inputs.add(wrong, BorderLayout.SOUTH);
            inputs.pack();
            inputs.setVisible(true);
            inputs.setLocationRelativeTo(null);
            inputs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        } else if (e.getSource() == raw) {
            this.dispose();
            final JFrame loading = new JFrame("Loading...");
            final Loading loadingPanel = new Loading();
            loading.add(loadingPanel);
            loading.pack();
            loading.setVisible(true);
            loading.setLocationRelativeTo(null);
            loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    new ResultsFrame();
                    loadingPanel.stop();
                    loading.dispose();
                }

            });
            thread.start();
        } else if (e.getSource() == group) {
            this.dispose();
            final JFrame loading = new JFrame("Loading...");
            final Loading loadingPanel = new Loading();
            loading.add(loadingPanel);
            loading.pack();
            loading.setVisible(true);
            loading.setLocationRelativeTo(null);
            loading.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    new GroupData();
                    loadingPanel.stop();
                    loading.dispose();
                }

            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        new Menu();
    }
}
