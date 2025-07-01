// ImageButtonFactory.java - helper class to create JButton with image icons

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ImageButtonFactory {
    public static JButton createImageButton(String imagePath, int width, int height, String tooltip) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(scaled));

        button.setContentAreaFilled(false);// transparent background
        button.setBorder(BorderFactory.createLineBorder(new Color(65,105,225),2)); 
        button.setBorderPainted(true);
        button.setFocusPainted(false);// no focus outline

        if (tooltip != null && !tooltip.isEmpty()) {
            button.setToolTipText(tooltip);
        }

        return button;
    }
} 