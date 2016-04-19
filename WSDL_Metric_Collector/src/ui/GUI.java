package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import fileManager.FileManager;
import fileManager.StringVector;

import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.GridLayout;

public class GUI extends JFrame {

	private FileManager fileManager;

	private JPanel contentPane;
	private DefaultTableModel defaultTableModel_fileComparator ;
	private JTable jTable_fileComparator;

	private Vector<StringVector> wsdlMetrics;

	private File selectedDirectory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("WSDL Metrics Collector");
		setMaximumSize(new Dimension(3072, 1024));
		setMinimumSize(new Dimension(1536, 728));
		setPreferredSize(new Dimension(1536, 728));

		fileManager = new FileManager();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JComboBox<String> cbWSDLList = new JComboBox<String>();
		cbWSDLList.setEnabled(false);

		JComboBox<Integer> cbWSDLToSkip = new JComboBox<Integer>();
		cbWSDLToSkip.setEnabled(false);

		JButton btnCreateWekaFiles = new JButton("Create Weka File(s)");
		btnCreateWekaFiles.setEnabled(false);
		btnCreateWekaFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
			}
		});

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLoadWsdl = new JMenuItem("Load WSDL files");
		mntmLoadWsdl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(); 
				chooser.setCurrentDirectory(new java.io.File(".wsdl"));
				chooser.setDialogTitle("Select folder where WSDL files are stored");

				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					selectedDirectory = chooser.getCurrentDirectory();

					cbWSDLList.removeAllItems();
					File[] listOfFiles = selectedDirectory.listFiles();
					int counterFiles = 0;
					for (File file : listOfFiles) {
						if (file.isFile()) {
							cbWSDLList.addItem(file.getName());
							counterFiles++;
						}
					}

					cbWSDLToSkip.removeAllItems();
					for(int i=0; i<counterFiles; i++){
						cbWSDLToSkip.addItem(i);
					}

					if(defaultTableModel_fileComparator != null)
						defaultTableModel_fileComparator.setDataVector(new String[][]{{}}, new String[]{});


					btnCreateWekaFiles.setEnabled(true);
					cbWSDLList.setEnabled(true);
					cbWSDLToSkip.setEnabled(true);
				}
				else{
					//			    	System.out.println("No Selection");
				}
			}
		});
		mnFile.add(mntmLoadWsdl);



		JMenu mnConfig = new JMenu("Configuration");
		menuBar.add(mnConfig);


		JPanel panel = new JPanel();
		mnConfig.add(panel);
		panel.setLayout(new GridLayout(6, 1, 5, 5));

		JLabel lblStartingWSDL = new JLabel("Select the last WSDL file to be processed before generate a Weka file");
		panel.add(lblStartingWSDL);


		panel.add(cbWSDLList);

		JLabel lblInterval = new JLabel("Select the number of WSDL files to be skipped before generate a new Weka file");
		panel.add(lblInterval);


		panel.add(cbWSDLToSkip);

		panel.add(btnCreateWekaFiles);

		JMenuItem mntmCreateWekaFile = new JMenuItem("Create Weka File");
		mntmCreateWekaFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String wekaFile = "@RELATION wsdlFilesMetrics\n\n"
						+ "@ATTRIBUTE Messages NUMERIC\n"
						+ "@ATTRIBUTE PortTypes NUMERIC\n"
						+ "@ATTRIBUTE Operations NUMERIC\n"
						+ "@ATTRIBUTE Coupling NUMERIC\n\n"
						+ "@DATA\n";

				/*
				 * Create a directory, or clean the existing one.
				 */
				File newFolder = new File(selectedDirectory+"/WekaFiles/");
				if(!newFolder.mkdirs()){
					File[] wekaFiles = newFolder.listFiles();
					for (File file : wekaFiles) {
						if (file.isFile()) {
							file.delete();
						}
					}
				}
				/*
				 * Next lines are used create a new TableModel each time parameters change
				 */
				defaultTableModel_fileComparator = new DefaultTableModel();

				defaultTableModel_fileComparator.addColumn("File Name");
				defaultTableModel_fileComparator.addColumn("Messages");
				defaultTableModel_fileComparator.addColumn("PortTypes");
				defaultTableModel_fileComparator.addColumn("Operations");
				defaultTableModel_fileComparator.addColumn("Coupling");
				defaultTableModel_fileComparator.addColumn("Weka File Generated");

				jTable_fileComparator.setModel(defaultTableModel_fileComparator);

				/*
				 * Center data in cells
				 */
				DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
				centerRenderer.setHorizontalAlignment( DefaultTableCellRenderer.CENTER );
				for(int i=0; i<defaultTableModel_fileComparator.getColumnCount(); i++)
					jTable_fileComparator.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
				/*
				 * Define 500px as width to the File Name column
				 */
				jTable_fileComparator.getColumnModel().getColumn(0).setPreferredWidth(500);
				/*
				 * 
				 */
				File[] wsdlFiles = selectedDirectory.listFiles();
				int fileCounter = 0;
				int skipCounter = 0;
				for (File wsdlFile : wsdlFiles) {
					if (wsdlFile.isFile()) {
						String wekaGenerationMark = "";
						if(fileCounter == cbWSDLList.getSelectedIndex()){
							wekaGenerationMark = "<--<--=";
						}					
						else if(fileCounter > cbWSDLList.getSelectedIndex()){
							if(skipCounter == fileCounter){
								wekaGenerationMark = "<--<--=";								
							}
						}
						/*
						 * JTable data load
						 */
						wsdlMetrics = fileManager.readMetrics(wsdlFile);
						defaultTableModel_fileComparator.addRow(
								new Object[]{wsdlFile.getName(), 
										wsdlMetrics.elementAt(0).size(), 
										wsdlMetrics.elementAt(1).size(), 
										wsdlMetrics.elementAt(2).size(),
										String.format("%.4f", ((float)wsdlMetrics.elementAt(1).size()/(float)wsdlMetrics.elementAt(2).size())),
										wekaGenerationMark});
						/*
						 * Weka file data load
						 */
						wekaFile += wsdlMetrics.elementAt(0).size() + ","
								+ wsdlMetrics.elementAt(1).size() + "," 
								+ wsdlMetrics.elementAt(2).size() + ","
								+ String.format("%.4f", ((float)wsdlMetrics.elementAt(1).size()/(float)wsdlMetrics.elementAt(2).size())) + "\n";

						java.util.Date date= new java.util.Date();
						if(fileCounter == cbWSDLList.getSelectedIndex()){
							fileManager.writeFile(selectedDirectory+"/WekaFiles/"+date.getTime()+".arff", wekaFile);
							skipCounter = fileCounter+(cbWSDLToSkip.getSelectedIndex()+1);
						}					
						else if(fileCounter > cbWSDLList.getSelectedIndex()){
							if(skipCounter == fileCounter){
								fileManager.writeFile(selectedDirectory+"/WekaFiles/"+date.getTime()+".arff", wekaFile);
								skipCounter = fileCounter+(cbWSDLToSkip.getSelectedIndex()+1);
							}
						}

						fileCounter++;
					}
				}	
			}
		});
		menuBar.add(mntmCreateWekaFile);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		jTable_fileComparator = new JTable();
		jTable_fileComparator.setFont(new Font("Verdana", Font.PLAIN, 16));
		jTable_fileComparator.setRowHeight(20);
		jTable_fileComparator.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		JScrollPane scrollPane = new JScrollPane(jTable_fileComparator);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

}
