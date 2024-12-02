package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * Class to create UI components.
 */
public class UIFactory {
    static Color backgroundColour = new Color(240, 244, 250);
    /**
     * Creates and returns JButton with default appearance.
     * @param name name of button
     * @return button created
     */
    static JButton createButton(String name) {
        final Color defaultColour = new Color(110, 137, 186);
        final Color pressedColour =new Color(66, 90, 133);
        final Color hoverColour = new Color(85, 112, 161);
        final Color textColour = Color.WHITE;

        return createButton(name, defaultColour, pressedColour, hoverColour, textColour);
    }

    /**
     * Creates and returns JButton with consistent appearance.
     * @param name name of button
     * @param defaultColour default colour of button
     * @param pressedColour colour of button when clicked
     * @param hoverColour colour of button when hovered
     * @param textColour text colour of button
     * @return button created
     */
    static JButton createButton(String name, Color defaultColour, Color pressedColour, Color hoverColour, Color textColour) {
        final boolean[] isHovered = {false};
        final boolean[] isPressed = {false};

        final JButton button = new JButton(name) {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color buttonColor = defaultColour; // default colour
                if (isPressed[0]) {
                    buttonColor = pressedColour; // pressed colour
                } else if (isHovered[0]) {
                    buttonColor = hoverColour; // hover colour
                }

                g2.setColor(buttonColor);
                g2.fillRoundRect(5, 2, getWidth()-10, getHeight()-4, 15, 15);

                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                // no border
            }
        };

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Gill Sans", Font.PLAIN, 16));
        button.setForeground(textColour);
        button.setBounds(0, 0, 100, 25);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered[0] = true;
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovered[0] = false;
                button.repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                isPressed[0] = true;
                button.repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                isPressed[0] = false;
                button.repaint();
            }
        });

        return button;
    }

    /**
     * Creates and returns JButton with consistent appearance within message.
     * @param name name of button
     * @return button created
     **/
    static JButton createMessageButton(String name) {
        final boolean[] isHovered = {false};
        final boolean[] isPressed = {false};
        final Color defaultColour = new Color(123, 139, 168); // default text color
        final Color[] textColour = {defaultColour};

        final JButton button = new JButton(name) {
            @Override
            protected void paintComponent(Graphics g) {
                final Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (isPressed[0]) {
                    textColour[0] = defaultColour.darker(); // pressed colour
                } else {
                    textColour[0] = defaultColour;
                }
                g2.setColor(textColour[0]);
                setForeground(textColour[0]);
                super.paintComponent(g);

                // underline text when hovered over
                if (isHovered[0]) {
                    g2.setColor(textColour[0]);
                    final FontMetrics fm = g2.getFontMetrics();
                    final int textWidth = fm.stringWidth(name);
                    g2.drawLine((getWidth() - textWidth) / 2, getHeight() - 2, (getWidth() + textWidth) / 2, getHeight() - 2);
                }
            }

            @Override
            protected void paintBorder(Graphics g) {
                // no border
            }
        };

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(true);
        button.setBorder(null);
        button.setFont(getTextFont());
        button.setForeground(textColour[0]);

        final FontMetrics fm = button.getFontMetrics(button.getFont());
        final int textWidth = fm.stringWidth(name);
        final int textHeight = fm.getHeight();
        button.setPreferredSize(new Dimension(textWidth+15, textHeight));
        button.setBounds(0, 0, textWidth+15, textHeight);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered[0] = true;
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                isHovered[0] = false;
                button.repaint();
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                isPressed[0] = true;
                button.repaint();
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                isPressed[0] = false;
                button.repaint();
            }
        });

        return button;
    }

    static Font getTitleFont() {
        return new Font("Helvetica Neue", Font.BOLD, 24);
    }

    static Font getTextFont() {
        return new Font("Helvetica Neue", Font.PLAIN, 14);
    }
}
