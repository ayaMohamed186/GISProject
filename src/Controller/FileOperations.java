package Controller;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


import Model.InvoiceHeader;
import Model.InvoiceLine;



public class FileOperations {



   public static String invoiceHeaderDataPath = "E:\\InvoiceHeader.csv";
    public static String invoiceLineDataPath = "E:\\InvoiceLines.csv";



   public static ArrayList<InvoiceHeader> loadInvocies() {
        ArrayList<InvoiceHeader> invoiceHeaderList = new ArrayList<InvoiceHeader>();
        try {



           BufferedReader invoiceHeaderCsvReader = new BufferedReader(new FileReader(invoiceHeaderDataPath));



           String row;
            int invoiceHeadeCounter = 1;



           InvoiceHeader invoice;
            InvoiceLine lines;
            while ((row = invoiceHeaderCsvReader.readLine()) != null && !row.isEmpty()) {
                BufferedReader invoiceLineCsvReader = new BufferedReader(new FileReader(invoiceLineDataPath));
                int invoiceLineCounter = 1;
                if (invoiceHeadeCounter == 1) {
                    invoiceHeadeCounter++;
                    continue;
                }
                invoice = new InvoiceHeader();
                String[] data = row.split(",");
                invoice.invoiceNum = Integer.parseInt(data[0]);
                invoice.invoiceDate = data[1];
                invoice.CustomerName = data[2];



               invoice.invoiceLinesList = new ArrayList<InvoiceLine>();
                while ((row = invoiceLineCsvReader.readLine()) != null) {
                    if (invoiceLineCounter == 1) {
                        invoiceLineCounter++;
                        continue;
                    }
                    String[] lineData = row.split(",");
                    if (invoice.invoiceNum == Integer.parseInt(lineData[0])) {
                        lines = new InvoiceLine();
                        lines.invoiceNum = Integer.parseInt(lineData[0]);
                        lines.itemName = lineData[1];
                        lines.itemPrice = Integer.parseInt(lineData[2]);
                        lines.Count = Integer.parseInt(lineData[3]);



                       invoice.invoiceLinesList.add(lines);
                        invoice.invoiceTotalPrice += (lines.Count * lines.itemPrice);
                    }
                }
                invoiceHeaderList.add(invoice);
                invoiceLineCsvReader.close();
            }



           invoiceHeaderCsvReader.close();



       } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
        } catch (IOException e) {
            System.out.println("IOException");
        }



       return invoiceHeaderList;
    }



   public static void SaveInvoices(ArrayList<InvoiceHeader> invoicesList)
    {
        try {
            FileWriter invoiceHeaderCsvWriter = new FileWriter("invoiceHeaderDataPath");
            FileWriter invoiceLinerCsvWriter = new FileWriter("invoiceLineDataPath");
            
            invoiceHeaderCsvWriter.append("invoiceNum");
            invoiceHeaderCsvWriter.append(",");
            invoiceHeaderCsvWriter.append("invoiceDate");
            invoiceHeaderCsvWriter.append(",");
            invoiceHeaderCsvWriter.append("CustomerName");
            invoiceHeaderCsvWriter.append("\n");
            
            invoiceLinerCsvWriter.append("invoiceNum");
            invoiceLinerCsvWriter.append(",");
            invoiceLinerCsvWriter.append("itemName");
            invoiceLinerCsvWriter.append(",");
            invoiceLinerCsvWriter.append("Count");
            invoiceLinerCsvWriter.append(",");
            invoiceLinerCsvWriter.append("itemPrice");
            invoiceLinerCsvWriter.append("\n");
            
            for (InvoiceHeader invoiceHeader : invoicesList) {
                invoiceHeaderCsvWriter.append(String.valueOf(invoiceHeader.invoiceNum));
                invoiceHeaderCsvWriter.append(",");
                invoiceHeaderCsvWriter.append(invoiceHeader.invoiceDate);
                invoiceHeaderCsvWriter.append(",");
                invoiceHeaderCsvWriter.append(invoiceHeader.CustomerName);
                invoiceHeaderCsvWriter.append("\n");
                for (InvoiceLine invoiceLine : invoiceHeader.invoiceLinesList) {
                    invoiceLinerCsvWriter.append(String.valueOf(invoiceLine.invoiceNum));
                    invoiceLinerCsvWriter.append(",");
                    invoiceLinerCsvWriter.append(invoiceLine.itemName);
                    invoiceLinerCsvWriter.append(",");
                    invoiceLinerCsvWriter.append(String.valueOf(invoiceLine.Count));
                    invoiceLinerCsvWriter.append(",");
                    invoiceLinerCsvWriter.append(String.valueOf(invoiceLine.itemPrice));
                    invoiceLinerCsvWriter.append("\n");
                }
                
            }
            invoiceLinerCsvWriter.flush();
            invoiceLinerCsvWriter.close();
            invoiceHeaderCsvWriter.flush();
            invoiceHeaderCsvWriter.close();
            } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}