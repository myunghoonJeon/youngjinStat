package mainpages;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.PrintQuality;


public class PrintSolution {

	protected static BufferedImage printPage;
	public static BufferedImage getPrintPage() {return printPage;}
	public static void print(Component frame) {
		print(0,0,frame,0);	
	}
	public static void print(int width, int height, Component frame, int orientation) {	
		
		PrinterJob printerJob = PrinterJob.getPrinterJob();
				
		Paper paper = printerJob.defaultPage().getPaper();
		//paper.setSize(width, height);
		
		PageFormat format = printerJob.defaultPage();
		//format = printerJob.pageDialog(format);
				
//		System.out.printf("Param Size : %d %d\n", width, height);
//		System.out.printf("Component Size : %d %d\n", frame.getWidth(), frame.getHeight());
//		System.out.printf("Format Size : %f %f\n", format.getWidth(), format.getHeight());
//		System.out.printf("Page Size : %f %f\n", paper.getWidth(), paper.getHeight());
		
		format.setOrientation(orientation);
		format.setPaper(paper);
		
		printPage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
		frame.paint(printPage.getGraphics());
		
		printerJob.setPrintable(new Printable() {			
			@Override
			public int print(Graphics graphics, PageFormat format, int index)
					throws PrinterException {
				graphics.translate((int)(format.getImageableX()), (int)(format.getImageableY()));
				
				if(index == 0){										
					Graphics2D g = (Graphics2D) graphics;
					g.setClip(0, 0, (int)PrintSolution.printPage.getWidth(), (int)PrintSolution.printPage.getHeight());
					g.setBackground(Color.WHITE);
					g.clearRect(0, 0, (int)PrintSolution.printPage.getWidth(), (int)PrintSolution.printPage.getHeight());
					if(format.getOrientation() == PageFormat.LANDSCAPE) {
						double scaleW = format.getImageableWidth() / PrintSolution.printPage.getWidth();
						double scaleH = format.getImageableHeight() / PrintSolution.printPage.getHeight();								
						double scale = Math.min(scaleH, scaleW);
						
						g.scale(scale, scale);												
					}
					else {
						double scaleW = format.getImageableWidth() / PrintSolution.printPage.getWidth();
						double scaleH = format.getImageableHeight() / PrintSolution.printPage.getHeight();								
						double scale = Math.min(scaleH, scaleW);
						
						g.scale(scale, scale);												
					}
					g.drawImage(PrintSolution.printPage, 0, 0, (int)PrintSolution.printPage.getWidth(), (int)PrintSolution.printPage.getHeight(), null);
					//g.drawString("Now 0.5scale : " + System.currentTimeMillis()/1000, 50, 50);
					g.dispose();
					try {
						//ImageIO.write(PrintSolution.printPage, "png", new File("b:\\shot.png"));						
					} catch (Exception e) {
						e.printStackTrace();
					}
					return Printable.PAGE_EXISTS;
				}
				return Printable.NO_SUCH_PAGE;
			}
		});
		
		try{
			PrintRequestAttributeSet printAttributes = new HashPrintRequestAttributeSet();				
			printAttributes.add(PrintQuality.HIGH);
			
			if(printerJob.printDialog(printAttributes)) {
				printerJob.print(printAttributes);
			}
		}catch(Exception pe){
			System.out.println("Printingfailed : "+pe.getMessage());
		}
	}
	
}

