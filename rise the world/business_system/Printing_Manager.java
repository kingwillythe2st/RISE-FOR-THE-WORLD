package business_system;
import java.awt.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import javax.print.*;
import javax.print.attribute.*;

public class Printing_Manager implements Printable {

	private static JTextArea bill;
	
	public Printing_Manager()
	{
		bill = new JTextArea();
	}
	
    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
        
        /**
         * User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        /* Now we perform our rendering */

        g.setFont(new Font("Roman", 0, 8));
        g.drawString("Hello world !", 0, 10);

        return PAGE_EXISTS;
    }

    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.print("Error printing string");
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Error printing bytes");
        }
    }

    public void printJFrame(String printerName, Object frame) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(frame, flavor, null);

            job.print(doc, null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Error printing bytes");
        }
    }

    public void printImage(BufferedImage image)
    {
    	PrinterJob printJob = PrinterJob.getPrinterJob();
    	printJob.setPrintable(new Printable() {
    	        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
    	                if (pageIndex != 0) {
    	                    return NO_SUCH_PAGE;
    	                }
    	                graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    	                return PAGE_EXISTS;
    	        }
    	});     
    	try {
    	    printJob.print();
    	} catch (PrinterException e1) {             
    	    e1.printStackTrace();
    	}
    }
    
    public void printTable(JTable table)
    {
    	//DefaultTableModel df = (DefaultTableModel) table.getModel();
        String textToAdd = "";
        
    	for(int i = 0; i < table.getRowCount(); i++)
        {
    		textToAdd += "\n\t" + Integer.toString(1 + i);
    		 

    			for(int j = 0; j < table.getColumnCount(); j++)
    			{
    				String item = table.getValueAt(i, j).toString();      
    				textToAdd = textToAdd + "\t" + item;
    			}
    		
        }
        bill.setText(bill.getText() + textToAdd);
        try {
			bill.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private PrintService findPrintService(String printerName, PrintService[] services)
    {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }

    public static void run() {

        Printing_Manager printerService = new Printing_Manager();

        
        List<String> printerList = printerService.getPrinters();
        System.out.print("Connected printing services are shown below. Please select one\n");
        System.out.println(printerList);

        Scanner in = new Scanner(System.in);
        
        String s = in.nextLine().strip();
        //print some stuff. Change the printer name to your thermal printer name.
        
        //printerService.printString(s, "\n\n nigga balls \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        //printerService.printJFrame(s, (Object)frame);

        try {
			bill.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // cut that paper!
        byte[] cutP = new byte[] { 0x1d, 'V', 1 };

        printerService.printBytes(s, cutP);
    }
    
}