import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.*;

public class ReturnBookMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BOOK RETURN SYSTEM");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        
        ImageIcon img = new ImageIcon("Agile Access.png");//create and Image Icon
        frame.setIconImage(img.getImage());//change icon of frame
        
        //Bg Panel with image
        BackgroundPanelH bgPanel = new BackgroundPanelH("AA bg.png");
        bgPanel.setVisible(true);
        
        ReturnBookPanel returnPanel = new ReturnBookPanel(() ->
        {
            frame.dispose(); // close current window
            JFrame issueFrame = new JFrame("BOOK ISSUE SYSTEM");
            issueFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            issueFrame.setSize(900, 600);
            issueFrame.setLocationRelativeTo(null);

            BackgroundPanelH issueBg = new BackgroundPanelH("AA bg.png");
            BookIssueForm form = new BookIssueForm(issueFrame);
            form.setOpaque(false);
            issueBg.addForm(form);
            issueFrame.setContentPane(issueBg);
            issueFrame.setVisible(true);
        });

        returnPanel.setOpaque(false);
        bgPanel.addForm(returnPanel);
        frame.setContentPane(bgPanel);
        frame.setVisible(true);
    
    }
    
}

/*
import javax.swing.*;
import java.awt.*;

public class ReturnBookMain {
    public static void main(String[] args) {
        // Modern Swing: Use invokeLater for thread safety
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BOOK RETURN SYSTEM");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Changed to EXIT
            frame.setSize(900, 600); // Larger window for better UI
            frame.setLocationRelativeTo(null);
            
            // Set app icon (with error handling)
            try {
                ImageIcon img = new ImageIcon("Agile Access.png");
                frame.setIconImage(img.getImage());
            } catch (Exception e) {
                System.err.println("Icon not found");
            }

            // Background with modern features
            BackgroundPanel bgPanel = new BackgroundPanel("AA bg.png");
            bgPanel.setBlurEnabled(true); // New blur feature

            // Return book panel
            ReturnBookPanel returnPanel = new ReturnBookPanel(() -> {
                frame.dispose();
                openIssueFrame(); // Extracted method for clarity
            });

            bgPanel.addForm(returnPanel);
            frame.setContentPane(bgPanel);
            frame.setVisible(true);
        });
    }

    private static void openIssueFrame() {
        JFrame issueFrame = new JFrame("BOOK ISSUE SYSTEM");
        issueFrame.setSize(900, 600);
        issueFrame.setLocationRelativeTo(null);
        
        BackgroundPanel issueBg = new BackgroundPanel("AA bg.png");
        BookIssueForm form = new BookIssueForm(issueFrame);
        
        issueBg.addForm(form);
        issueFrame.setContentPane(issueBg);
        issueFrame.setVisible(true);
    }
}
*/