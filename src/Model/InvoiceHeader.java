package Model;

import java.util.ArrayList;

public class InvoiceHeader {

	public int invoiceNum; 
	public String invoiceDate;
	public String CustomerName; 
	public ArrayList<InvoiceLine> invoiceLinesList ;
	public int invoiceTotalPrice = 0 ;
	
}
