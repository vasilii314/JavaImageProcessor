package imaging.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageComponent extends JComponent {
    protected BufferedImage image;

    public ImageComponent(BufferedImage image) {
        setImage(image);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        Dimension dim;
        if (image != null) {
            dim = new Dimension(800, 800);
        } else {
            dim = new Dimension(50, 50);
        }

        setPreferredSize(dim);
        setMinimumSize(dim);
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (image != null) {
            var ins = getInsets();
            var x = (getWidth() - image.getWidth()) / 2 - ins.left;
            var y = (getHeight() - image.getHeight()) / 2 - ins.top;
            g.drawImage(image, x, y, this);
        }
    }

    public static void showInFrame(String title, BufferedImage src) {
        var window = new JFrame(title);
        window.add(new JScrollPane(new ImageComponent(src)), BorderLayout.CENTER);
        window.pack();
        window.setVisible(true);
    }
}
