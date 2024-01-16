package business_system;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;

import java.applet.*;

public class test extends Applet{
	static public int closeOperation;
	static JFrame jf;  
	
	public void paint (Graphics g)
    {
		int rectX, rectY, rectWidth, rectHeight;
		rectX = 10;
		rectY = 10;
		rectWidth = 200;
		rectHeight = 150;
		

			
			//g.clearRect(0, 0, jf.getWidth(), jf.getHeight());
			
			//g.drawString("hello" + Integer.toString(i), 40, 30);
			//g.drawRect(rectX, rectY, rectWidth, rectHeight);	
    }
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//PrinterJob job = PrinterJob.getPrinterJob();
		jf = new JFrame();
		test t = new test();
		String WindowTitle = "ni**a balls";
		//jf.getContentPane().add(t, BorderLayout.CENTER);
		jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		jf.pack();
		jf.setSize(800, 450);
		jf.setTitle(WindowTitle);
		jf.setVisible(true);
		
		//Printing_Manager.run(jf);
		Printing_Manager printer = new Printing_Manager();
		
		DefaultTableModel tableModel = new DefaultTableModel();
	    JTable table = new JTable(tableModel);
	    tableModel.addColumn("Languages");
	    tableModel.insertRow(0, new Object[] { "CSS" , "lol"});
	    tableModel.insertRow(0, new Object[] { "HTML5", "lol2"});
	    tableModel.insertRow(0, new Object[] { "JavaScript", "lol3"});
	    tableModel.insertRow(0, new Object[] { "jQuery", "lol4"});
	    tableModel.insertRow(0, new Object[] { "AngularJS", "lol5"});
	    tableModel.insertRow(tableModel.getRowCount(), new Object[] { "ExpressJS" });
	    
	    jf.add(new JScrollPane(table));

	    
	    BufferedImage image = null;
	    try {
			image = ImageIO.read(new File("src/bar code.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    printer.printImage(image);
		//printer.printTable(table);
		
		//printer.run();
	}
}