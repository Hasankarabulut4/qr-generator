package org.frame;

import org.api.QrGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Window extends JFrame {

    private String Qrtext = "Hello world";
    private Integer size = 1000;

    private JLabel qrImageLabel;

    public Dimension getScreenDimension() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }

    public Window() {
        var dimension = getScreenDimension();
        var height = dimension.height;
        var width = dimension.width;

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
        setSize(width/2,height/2);
        centreWindow(this);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setResizable(false);
        setIconImage(new ImageIcon("icon.png").getImage());
        setTitle("Qr Creator");
        setName("Qr Creator");

        qrImageLabel = new JLabel();
        add(qrImageLabel);

        initializeQrImage(getWidth(), getHeight());


        initializeTextsAndFields(getWidth(), getHeight());

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
            url = new URL(QrGenerator.getQrcode(Qrtext, size));
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = frameWidth/7;
        int y = frameHeight/5;
        int width = x*2;
        int height = y*2;

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        qrImageLabel.setIcon(imageIcon);
        qrImageLabel.setBounds(x, y, width, height);

        qrImageLabel.revalidate();
        qrImageLabel.repaint();
    }

    public void initializeTextsAndFields(int frameWidth, int frameHeight) {
        {
            int x = frameWidth / 7 * 4;
            int y = frameHeight/5;
            int width = x/4*2;
            int height = y;
            JLabel textLabel = new JLabel("Text :"); // Değişken adı çakışmasın
            textLabel.setBounds(x,y,width,height);
            textLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));

            JTextField field = new JTextField("Hello World");
            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if (field.getText().length() >= 400) {
                        field.setText(field.getText().substring(399,399));
                    }
                    updateQrCode(field);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateQrCode(field);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateQrCode(field);
                }

                private void updateQrCode(JTextField field) {
                    if (field.getText().isEmpty()) {
                        Qrtext = ".";
                    } else {
                        Qrtext = field.getText();
                    }
                    initializeQrImage(getWidth(), getHeight());
                }
            });
            field.setBounds(x+50,y+45,width,height/5);
            add(textLabel);
            add(field);
        }
    }
}