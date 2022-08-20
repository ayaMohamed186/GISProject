package View;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Controller.FileOperations;
import Controller.invoiceController;
import Model.InvoiceHeader;
import Model.InvoiceLine;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.Font;
import javax.swing.JTextField;

public class Main {

	private JFrame frame;
	private JTable table_1;
	private final Action action = new SwingAction();
	DefaultTableModel tableModel;
	DefaultTableModel itemTableModel;

	ArrayList<InvoiceHeader> invoiceHeaderList;
	private JTextField textofDate;
	private JTextField textOfCustomerName;
	private JTable tableInvoiceLine;
	int table1RowIndex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1209, 644);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("Create Invoice\r\n");
		btnNewButton.setBounds(84, 502, 124, 38);
		frame.getContentPane().add(btnNewButton);

		JButton btnDeleteInvoice = new JButton("Delete Invoice\r\n");
		btnDeleteInvoice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table_1.getSelectedRow() != -1) {
					invoiceHeaderList.remove(table_1.getSelectedRow());

					DefaultTableModel headerTableModel = (DefaultTableModel) table_1.getModel();
					int rowsHeadrerCount = headerTableModel.getRowCount();
					for (int i = rowsHeadrerCount - 1; i >= 0; i--) {
						headerTableModel.removeRow(i);
					}
					for (InvoiceHeader invoice : invoiceHeaderList) {
						Object[] row = { invoice.invoiceNum, invoice.invoiceDate, invoice.CustomerName,
								invoice.invoiceTotalPrice };
						headerTableModel.addRow(row);
					}
				}
			}
		});
		btnDeleteInvoice.setBounds(358, 502, 124, 38);
		frame.getContentPane().add(btnDeleteInvoice);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 33, 529, 424);
		frame.getContentPane().add(scrollPane);

		JLabel labelOfInvoiceNumber = new JLabel("");
		labelOfInvoiceNumber.setBounds(711, 16, 45, 13);
		frame.getContentPane().add(labelOfInvoiceNumber);

		JLabel labelOfInvoiceTotal = new JLabel("");
		labelOfInvoiceTotal.setBounds(711, 121, 45, 13);
		frame.getContentPane().add(labelOfInvoiceTotal);

		textofDate = new JTextField();
		textofDate.setBounds(706, 46, 361, 19);
		frame.getContentPane().add(textofDate);
		textofDate.setColumns(10);

		textOfCustomerName = new JTextField();
		textOfCustomerName.setColumns(10);
		textOfCustomerName.setBounds(706, 81, 361, 19);
		frame.getContentPane().add(textOfCustomerName);

		String col[] = { "Number ", "Date ", "Customer name", "total " };
		tableModel = new DefaultTableModel(col, 0);
		table_1 = new JTable(tableModel);
		String colItem[] = { "Number ", "item Name ", "item Price", "Count ", "total " };

		// after click on one record in invoices
		table_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel tableModel = (DefaultTableModel) tableInvoiceLine.getModel();
				int rowsCount = tableModel.getRowCount();
				for (int i = rowsCount - 1; i >= 0; i--) {
					tableModel.removeRow(i);
				}
				table1RowIndex = table_1.getSelectedRow();
				InvoiceHeader selectecdInvoiceHeader = invoiceHeaderList.get(table1RowIndex);

				itemTableModel = new DefaultTableModel(colItem, 0);
				// display data of invoice in labels
				labelOfInvoiceNumber.setText(String.valueOf(selectecdInvoiceHeader.invoiceNum));
				textofDate.setText(selectecdInvoiceHeader.invoiceDate);
				textOfCustomerName.setText(selectecdInvoiceHeader.CustomerName);
				labelOfInvoiceTotal.setText(String.valueOf(selectecdInvoiceHeader.invoiceTotalPrice));

				for (InvoiceLine invoice : selectecdInvoiceHeader.invoiceLinesList) {
					Object[] row = { selectecdInvoiceHeader.invoiceNum, invoice.itemName, invoice.itemPrice,
							invoice.Count, (invoice.itemPrice * invoice.Count) };
					tableModel.addRow(row);
				}

			}
		});

		scrollPane.setViewportView(table_1);

		JLabel lblNewLabel = new JLabel("Invoices Table\r\n");
		lblNewLabel.setBounds(10, 0, 88, 23);
		frame.getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Invoice number\r\n");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(586, 10, 94, 23);
		frame.getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("invoice Date\r\n");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(586, 43, 94, 23);
		frame.getContentPane().add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Customer name");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2.setBounds(586, 78, 94, 23);
		frame.getContentPane().add(lblNewLabel_1_2);

		JLabel lblNewLabel_1_2_1 = new JLabel("Invoice total\r\n");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2_1.setBounds(586, 111, 94, 23);
		frame.getContentPane().add(lblNewLabel_1_2_1);

		JLabel lblNewLabel_1_2_1_1 = new JLabel("Invoice Items :\r\n\r\n\r\n");
		lblNewLabel_1_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1_2_1_1.setBounds(586, 199, 94, 23);
		frame.getContentPane().add(lblNewLabel_1_2_1_1);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(586, 232, 539, 225);
		frame.getContentPane().add(scrollPane_1);

		// items table
		tableInvoiceLine = new JTable();

		itemTableModel = new DefaultTableModel(colItem, 0);
		tableInvoiceLine = new JTable(itemTableModel);

		scrollPane_1.setViewportView(tableInvoiceLine);

		JButton btnSave = new JButton("Save\r\n");
		btnSave.setBounds(696, 502, 124, 29);
		frame.getContentPane().add(btnSave);

		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ArrayList<InvoiceLine> invoiceLineList = new ArrayList<>();
				InvoiceLine invoiceLine;
				DefaultTableModel tableModel = (DefaultTableModel) tableInvoiceLine.getModel();
				int rowsCount = tableModel.getRowCount();
				for (int i = rowsCount - 1; i >= 0; i--) {
					invoiceLine = new InvoiceLine();

					invoiceLine.itemName = (String) tableModel.getValueAt(i, 1);
					invoiceLine.itemPrice = Integer.parseInt((String) tableModel.getValueAt(i, 2));
					invoiceLine.Count = Integer.parseInt((String) tableModel.getValueAt(i, 3));
					invoiceLineList.add(invoiceLine);
				}

				if (table_1.getSelectedRow() != -1)
					table1RowIndex = table_1.getSelectedRow();
				InvoiceHeader oldInvoiceHeader = invoiceHeaderList.get(table1RowIndex);
				invoiceHeaderList.remove(table1RowIndex);

				String newDate = textofDate.getText();
				String newCustomerName = textOfCustomerName.getText();
				InvoiceHeader newInvoiceHeader = invoiceController.EditInvoice(oldInvoiceHeader.invoiceNum, newDate,
						newCustomerName, invoiceLineList);
				invoiceHeaderList.add(table1RowIndex, newInvoiceHeader);
				DefaultTableModel headerTableModel = (DefaultTableModel) table_1.getModel();
				int rowsHeadrerCount = headerTableModel.getRowCount();
				for (int i = rowsHeadrerCount - 1; i >= 0; i--) {
					headerTableModel.removeRow(i);
				}
				for (InvoiceHeader invoice : invoiceHeaderList) {
					Object[] row = { invoice.invoiceNum, invoice.invoiceDate, invoice.CustomerName,
							invoice.invoiceTotalPrice };
					headerTableModel.addRow(row);
				}
			}
		});

		JButton btnCancel = new JButton("Cancel\r\n");
		btnCancel.setBounds(981, 507, 124, 29);
		frame.getContentPane().add(btnCancel);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("File\r\n");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Load File\r\n");
		mntmNewMenuItem.setAction(action);
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save File \r\n");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FileOperations.SaveInvoices(invoiceHeaderList);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);

	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Load File");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		// action for load file item in menu
		public void actionPerformed(ActionEvent e) {
			// load invoice header file
			JOptionPane.showMessageDialog(null, "Choose invoice Header file ");
			JFileChooser chooser = new JFileChooser();
			int rc = chooser.showOpenDialog(null);

			while (rc == JFileChooser.APPROVE_OPTION && !chooser.getSelectedFile().getName().endsWith(".csv")) {
				JOptionPane.showMessageDialog(null,
						"The file " + chooser.getSelectedFile() + " is not csv source file.", "Open Error",
						JOptionPane.ERROR_MESSAGE);
				rc = chooser.showOpenDialog(null);
			}
			String inoiveHeaderPath = chooser.getSelectedFile().getPath();
			FileOperations.invoiceHeaderDataPath = inoiveHeaderPath;

			// load invoice line file
			JOptionPane.showMessageDialog(null, "Choose invoice line file ");
			JFileChooser chooser2 = new JFileChooser();
			int rc2 = chooser2.showOpenDialog(null);

			while (rc2 == JFileChooser.APPROVE_OPTION && !chooser2.getSelectedFile().getName().endsWith(".csv")) {
				JOptionPane.showMessageDialog(null,
						"The file " + chooser2.getSelectedFile() + " is not csv source file.", "Open Error",
						JOptionPane.ERROR_MESSAGE);
				rc2 = chooser.showOpenDialog(null);
			}
			String inoiveLinePath = chooser2.getSelectedFile().getPath();
			FileOperations.invoiceLineDataPath = inoiveLinePath;

			invoiceHeaderList = FileOperations.loadInvocies();
			for (InvoiceHeader invoice : invoiceHeaderList) {
				Object[] row = { invoice.invoiceNum, invoice.invoiceDate, invoice.CustomerName,
						invoice.invoiceTotalPrice };
				tableModel.addRow(row);
			}

		}
	}

	private class SwingAction_1 extends AbstractAction {
		public SwingAction_1() {
			putValue(NAME, "SwingAction_1");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {

		}
	}
}
