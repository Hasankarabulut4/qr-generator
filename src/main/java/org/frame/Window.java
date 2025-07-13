package org.frame;

import org.api.QrGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter; // ComponentListener için adapter
import java.awt.event.ComponentEvent;    // ComponentEvent için
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Window extends JFrame {

    private String text = "Hello world";

    private Integer size = 150;

    public Dimension getScreenDimension() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }

    public Window() {
        var dimension = getScreenDimension();
        var height = dimension.height;
        var width = dimension.width;

        // set height and width values
        {
            var condition = true;
            var passed = false;
            while (condition) {
                if (!isDividableByFive(height)) {
                    height++;
                    passed = true;
                }
                if (!isDividableBySeven(width)) {
                    width++;
                    passed = true;
                }
                condition = passed;
                passed = false;
            }
        }
        // Initialize size
        setSize(width/2,height/2);

        // Start frame on center
        centreWindow(this);

        // Set layout
        setLayout(null);

        // Set Theme
        getContentPane().setBackground(Color.LIGHT_GRAY);



        // Set resizable
        setResizable(false);

        // Set icon
        setIconImage(new ImageIcon("icon.png").getImage());

        // Set title & name
        setTitle("Qr Creator");
        setName("Qr Creator");

        // Add qr image
        initializeQrImage(getWidth(), getHeight());

        // Add texts
        initializeTextsAndFields(getWidth(), getHeight());

        // Stop program on Exited
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private boolean isDividableByFive(Integer num) { return num % 5 == 0; }

    private boolean isDividableBySeven(Integer num) { return num % 7 == 0; }

    private void centreWindow(JFrame frame) {
        int x = getScreenDimension().width/2 - frame.getWidth()/2;
        int y = getScreenDimension().height/2 - frame.getHeight()/2;
        setBounds(x, y, frame.getWidth(), frame.getHeight());
    }

    public void initializeQrImage(int frameWidth, int frameHeight) {
        URL url;
        BufferedImage image;
        try {
            url = new URL(QrGenerator.getQrcode(text, size));
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = frameWidth/7;
        System.out.println("X : " + x);
        int y = frameHeight/5;
        System.out.println("Y : " + y);
        int width = x*2;
        System.out.println("Width : " + width);
        int height = y*2;
        System.out.println("Height : " + height);
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        JLabel imageComponent = new JLabel(imageIcon);
        imageComponent.setBounds(x,y,width,height);
        add(imageComponent);
    }

    public void initializeTextsAndFields(int frameWidth, int frameHeight) {
        {
            int x = frameWidth / 7 * 4;
            int y = frameHeight/5;
            int width = x/4*2;
            int height = y;
            JLabel test = new JLabel("Test label");
            test.setBounds(x,y,width,height);
            add(test);
        }
    }


    @Override
    public void repaint() {
        super.repaint();
    }
}