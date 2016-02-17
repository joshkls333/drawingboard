/**
*
*  Author :  Josh Klaus
*  Date    :  11/27/15
*  Homework Assignment : 5 & 6
*  Objective  :  A class that creates a GUI Drawing Board
*              that allows the user to draw an image, save 
*              that picture into a file that can be reloaded.
*               
*/

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import java.io.*;

public class DrawingBoard_draft implements Serializable
{
    public static void main(String args[]) throws Exception
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                JFrame frame = new JFrame();
 
                frame.setTitle("Drawing Board");
                frame.getContentPane().add(new DrawingBoardPanel());
                frame.setSize(1000,600);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
        
 //               frame.pack();
                frame.setVisible(true);

            }
        });
    }
}

////////////////// class that holds the container for the Panel ////////////

class DrawingBoardPanel extends JPanel
{
    JLabel cfHeader = new JLabel("Drawing Board");

    JButton saveButton, loadButton, colorButton, 
            exitButton, clearButton,
            undoButton, drawButton, rectButton,
            circleButton, lineButton, fillButton;

    JComboBox brushBox;

    String[] brush = {"Brush" , "Brush - Thick", "Brush - Dash", "Brush - *"};

    JPanel bottomButtonPanel, topButtonPanel;

    DrawingCanvas paintshop;

    int startX, startY;

    int currentX, currentY, oldX, oldY;

    int prevX, prevY;

    String msg = "";

    boolean check;

    int width;
    int height;


    boolean dragging = false;

    Point pointStart = null;
    Point pointEnd   = null;

    Color tmpColor = Color.BLACK;


//////////////////// Constructor for the Layout /////////////////////////////

    public DrawingBoardPanel() 
    {

        cfHeader.setFont(new Font("Arial", Font.BOLD, 35));

        setLayout(new BorderLayout());
 
        buildTopButtonPanel();

        buildBottomButtonPanel();

        paintshop = new DrawingCanvas();
        paintshop.addMouseListener(new MyMouseListener());
        paintshop.addMouseMotionListener(new MyMouseMotionListener());

        add(topButtonPanel, BorderLayout.NORTH);

        add(paintshop, BorderLayout.CENTER);

        add(bottomButtonPanel, BorderLayout.SOUTH);

    }

    private void buildTopButtonPanel()
    {
        topButtonPanel = new JPanel();
        topButtonPanel.setLayout(new GridLayout(1,4));

        rectButton = new JButton("RECTANGLE");
        rectButton.setToolTipText("<html><h3>Click this button to draw a rectangle.</h3></html>");
        rectButton.addActionListener(new ButtonListener());


        circleButton = new JButton("CIRCLE");
        circleButton.setToolTipText("<html><h3>Click this button to draw a circle.</h3></html>");

        lineButton = new JButton("LINE");
        lineButton.setToolTipText("<html><h3>Click this button to draw a line.</h3></html>");

        fillButton = new JButton("FILL");
        fillButton.setToolTipText("<html><h3>Click this button to fill the Rectangle, Circle, or Line.</h3></html>");

        topButtonPanel.add(rectButton);
        topButtonPanel.add(circleButton);
        topButtonPanel.add(lineButton);
        topButtonPanel.add(fillButton);
    }

    private void buildBottomButtonPanel()
    {
        bottomButtonPanel = new JPanel();
        bottomButtonPanel.setLayout(new GridLayout(1,8));

        saveButton = new JButton("SAVE");
        saveButton.addActionListener(new ButtonListener());
        saveButton.setToolTipText("<html><h3>Click this button to Save your picture to a file.</h3></html>");

        loadButton = new JButton("LOAD");
        loadButton.setToolTipText("<html><h3>Click this button to Load a picture from a file.</h3></html>");
        loadButton.addActionListener(new ButtonListener());

        colorButton = new JButton("COLOR");
        colorButton.setToolTipText("<html><h3>Click this button to select a color to paint with.</h3></html>");
        colorButton.addActionListener(new ButtonListener());

        exitButton = new JButton("EXIT");
        exitButton.setToolTipText("<html><h3>Click this button to Exit the program.</h3></html>");
        exitButton.addActionListener(new ButtonListener());

        brushBox = new JComboBox(brush);
        brushBox.setToolTipText("<html><h3>Select a brush style from box.</h3></html>");
        brushBox.addActionListener(new ComboBoxListener());

        clearButton = new JButton("CLEAR");
        clearButton.setToolTipText("<html><h3>Click this button to clear the drawing board.</h3></html>");
        clearButton.addActionListener(new ButtonListener());

        undoButton = new JButton("UNDO");
        undoButton.setToolTipText("<html><h3>Click this button to switch the brush to erase mode.</h3></html>");
        undoButton.addActionListener(new ButtonListener());

        drawButton = new JButton("DRAW");
        drawButton.setToolTipText("<html><h3>Click this button to switch the brush to draw mode.</h3></html>");
        drawButton.addActionListener(new ButtonListener());

        bottomButtonPanel.add(saveButton);
        bottomButtonPanel.add(loadButton);
        bottomButtonPanel.add(colorButton);
        bottomButtonPanel.add(exitButton);
        bottomButtonPanel.add(brushBox);
        bottomButtonPanel.add(clearButton);
        bottomButtonPanel.add(undoButton);
        bottomButtonPanel.add(drawButton);

    }

    class DrawingCanvas extends JPanel
    {

        public DrawingCanvas()
        {    
            this.setPreferredSize(new Dimension(600,400));
            this.setBackground(Color.white);
        }

 
        public void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            g.setColor(tmpColor);
 //           g.drawString(msg, currentX, currentY);
            g.drawLine(oldX, oldY, currentX, currentY);
 //           g.drawRect(oldX, oldY, width, height);

        }


   
    }
    class ComboBoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String selection = (String) brushBox.getSelectedItem();

            if(selection == "Brush")
            {
//                paintshop.g2.setStroke(new BasicStroke(1));
            }
            else if(selection == "Brush - Thick")
            {
//                paintshop.g2.setStroke(new BasicStroke(6));
            }
            else if(selection == "Brush - Dash")
            {
/*
                paintshop.g2.setStroke(new BasicStroke(2.0f, // Width
                           BasicStroke.CAP_SQUARE,    // End cap
                           BasicStroke.JOIN_MITER,    // Join style
                           10.0f,                     // Miter limit
                           new float[] {16.0f,20.0f}, // Dash pattern
                           0.0f));    
*/
            }
            else if(selection == "Brush - *")
            {
//                paintshop.g2.setStroke(new TextStroke("*", Arial, true, true));
            }

        }
    }
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == saveButton)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Specify a file to save");
//                fileChooser.setFileFilter(new FileTypeFilter(".img", "Image File"));

                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
               
                int result = fileChooser.showSaveDialog(null);

//                imageContent = new Image(paintshop, paintshop.BORDER);
                
                if (result == JFileChooser.APPROVE_OPTION) 
                {
//                    Image content = imageContent.getImage();
                    File fileToSave = fileChooser.getSelectedFile();
                    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
                    try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Serial")))
                    {
 //                       FileWriter fw = new FileWriter(fileToSave.getPath());
                        oos.writeObject(this);
//                        fw.flush();
//                        fw.close();
                    } catch (Exception e2) {
                        JOptionPane.showMessageDialog(null, e2.getMessage());
                    }
                }

            }
            else if (e.getSource() == loadButton)
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(paintshop);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }

            }
            else if (e.getSource() == colorButton)
            {
                Color c = JColorChooser.showDialog(null, "Choose a Color", paintshop.getBackground());
                      
                if (c != null)
                    tmpColor = c;
//                paintshop.repaint();
            }
            else if (e.getSource() == exitButton)
            {
                System.exit(1);
            }
/* 
            else if (e.getSource() == brushButton)
            {
                paintshop.g2.setStroke( new BasicStroke(6));
            }
*/
            else if (e.getSource() == undoButton)
            {
                tmpColor = Color.white;
//                paintshop.g.setStroke( new BasicStroke(6));
 
            }
            else if (e.getSource() == drawButton)
            {

//                paintshop.g.setStroke( new BasicStroke(1));
                tmpColor = Color.black;
            }

            else if (e.getSource() == clearButton)
            {
 //               paintshop.clear();
            }
            else if (e.getSource() == rectButton)
            {
                check = true;

                repaint();

            }

        }
    }
    class MyMouseListener extends MouseAdapter
    {

        public void mousePressed(MouseEvent e) 
        {
            // save coord x,y when mouse is pressed
            oldX = e.getX();
            oldY = e.getY();
            repaint();
        }
        
    }
 
    class MyMouseMotionListener extends MouseMotionAdapter 
    {
        public void mouseDragged(MouseEvent e) 
        {
            // coord x,y when drag mouse
            currentX = e.getX();
            currentY = e.getY();

            msg = "*";

            repaint();

            width  = currentX - oldX;
            height = currentY - oldY;

//            oldX = currentX;
//            oldY = currentY;
                    

        }
        
    }    
//////// inner class for holding the ComboBoxListener for brush ///////

    class ComboBoxListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String selection = (String) brushBox.getSelectedItem();

            if(selection == "Brush *")
            {
                wordCheck = false;
                astCheck = true;
                msg = "*";
                check = true;
                repaint();
            }
            else if(selection == "Normal")
            {
                check = false;
                paintshop.g2.setStroke(new BasicStroke(1));
            }
            else if(selection == "Thick")
            {
                check = false;
                paintshop.g2.setStroke(new BasicStroke(6));
            }
            else if(selection == "Dash")
            {
                check = false;
                paintshop.g2.setStroke(new BasicStroke(2, 
                           BasicStroke.CAP_SQUARE,   
                           BasicStroke.JOIN_MITER,
                           10,  
                           new float[] {16,20},
                           0));    
            }
            else if(selection == "Word")
            {
                astCheck = false;
                wordCheck = true;
                msg = "Word";
                check = true;
                repaint();
            }

        }
    }


}
