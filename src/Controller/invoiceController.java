package Controller;

import java.util.ArrayList;

import Model.InvoiceHeader;
import Model.InvoiceLine;

public class invoiceController {

	public static InvoiceHeader EditInvoice(int invoiceNo ,String date , String customerName , ArrayList <InvoiceLine> invoiceLine ) {
		InvoiceHeader invoiceHeader = new InvoiceHeader();
		invoiceHeader.invoiceNum= invoiceNo;
		invoiceHeader.invoiceDate = date;
		invoiceHeader.CustomerName = customerName;
		invoiceHeader.invoiceLinesList = invoiceLine ;
		for (InvoiceLine invoiceLine2 : invoiceLine) {
			invoiceHeader.invoiceTotalPrice+=invoiceLine2.Count*invoiceLine2.itemPrice;
		}
		
		return invoiceHeader;
	}
}
