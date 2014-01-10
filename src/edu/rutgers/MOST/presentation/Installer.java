package edu.rutgers.MOST.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Installer  extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JButton fileButton = new JButton("Browse");
	public static JButton okButton = new JButton("    OK    ");
	public static JButton cancelButton = new JButton("  Cancel  ");
	public static final JTextField textField = new JTextField();
	public static JLabel topLabel = new JLabel(InstallerConstants.TOP_LABEL);
	
	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public boolean fileSelected;
	
	public Installer() {

		setTitle(InstallerConstants.TITLE);		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		getRootPane().setDefaultButton(okButton);
		
		//textField.setText("");

	    fileSelected = false;
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbLabel = Box.createHorizontalBox();
		Box hbMetab = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();

		//JLabel topLabel = new JLabel();
		//topLabel.setText(GraphicalInterfaceConstants.GUROBI_PATH_LABEL);
		topLabel.setSize(new Dimension(150, 10));
		//top, left, bottom. right
		topLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
		topLabel.setAlignmentX(CENTER_ALIGNMENT);

		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.add(topLabel);
		labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		hbLabel.add(labelPanel);
		
		topLabel.setMinimumSize(new Dimension(200, 15));
		textField.setEditable(false);
		textField.setBackground(Color.white);

		okButton.setMnemonic(KeyEvent.VK_O);
		okButton.setEnabled(false);
		JLabel blank = new JLabel("    "); 
		cancelButton.setMnemonic(KeyEvent.VK_C);
		

		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				enableOKButton();
			}
			public void removeUpdate(DocumentEvent e) {
				enableOKButton();
			}
			public void insertUpdate(DocumentEvent e) {
				enableOKButton();
			}

			public void enableOKButton() {
				if (textField.getText() != null && textField.getText().length() > 0) {
					okButton.setEnabled(true);
					//LocalConfig.getInstance().hasMetabolitesFile = true;
				} else {
					okButton.setEnabled(false);
					//LocalConfig.getInstance().hasMetabolitesFile = false;
				}
			}
		});
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		textPanel.add(textField);
		textPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

		JLabel blank2 = new JLabel("      ");
		JLabel blank3 = new JLabel("      ");
		
		hbMetab.add(blank2);
		hbMetab.add(fileButton);
		hbMetab.add(textPanel);
		hbMetab.add(blank3);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		buttonPanel.add(blank);
		buttonPanel.add(cancelButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,20,15,20));

		hbButton.add(buttonPanel);

		vb.add(hbLabel);
		vb.add(hbMetab);
		vb.add(hbButton);
		add(vb);	
		
		ActionListener fileButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				
			}
		};
		
		ActionListener okButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				
			}
		};
		
		ActionListener cancelButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				
			}
		}; 
		
		fileButton.addActionListener(fileButtonActionListener);
		okButton.addActionListener(okButtonActionListener);
		cancelButton.addActionListener(cancelButtonActionListener);
		
	} 	

	// from http://stackoverflow.com/questions/5368724/how-to-copy-a-folder-and-all-its-subfolders-and-files-into-another-folder
	public static void copyDirectory(File sourceLocation , File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}

			String[] children = sourceLocation.list();
			for (int i=0; i<children.length; i++) {
				copyDirectory(new File(sourceLocation, children[i]),
						new File(targetLocation, children[i]));
			}
		} else {

			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation);

			// Copy the bits from instream to outstream
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	public static void main(String[] args) throws Exception {
		//based on code from http://stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("images/most16.jpg").getImage()); 
		icons.add(new ImageIcon("images/most32.jpg").getImage());

		Installer frame = new Installer();
		frame.setIconImages(icons);
		frame.setSize(400, 150);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		File sourceDir = new File("C:\\MOST");
		//File destFile = new File("C:\\downloads\\MOST.jar");
		File dir = new File("C:\\downloads\\test");
		if (!dir.exists()) {
			dir.mkdir();
		} else {
//			System.out.println("Directory exists. Do you wish to install MOST in this directory?");
//			File destFile = new File("C:\\downloads\\test\\MOST.jar");
//			if (destFile.exists()) {
//				System.out.println("File exists. Do you wish to overwrite the file?");
//			}
		}
		File destDir = new File("C:\\downloads\\test");
		copyDirectory(sourceDir, destDir);
//		File destDir = new File("C:\\downloads\\test");
//		File destFile = new File("C:\\downloads\\test\\MOST.jar");
//		copyFile(sourceFile, destFile);
		
	}
}









