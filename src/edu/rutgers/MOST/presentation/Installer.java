package edu.rutgers.MOST.presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.apache.log4j.Logger;

public class Installer  extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//log4j
	static Logger log = Logger.getLogger(Installer.class);
	
	public static JButton fileButton = new JButton("Browse");	
	public static final JTextField directoryTextField = new JTextField();
	public static JLabel topLabel = new JLabel(InstallerConstants.TOP_LABEL);
	public static JLabel topLabel2 = new JLabel(InstallerConstants.TOP_LABEL2);
	public static JCheckBox newFolderCheckBox = new JCheckBox(InstallerConstants.NEW_FOLDER_CHECK_BOX_LABEL);
	public static JLabel newFolderLabel = new JLabel(InstallerConstants.NEW_FOLDER_LABEL);
	public static final JTextField newFolderField = new JTextField();
	public static JCheckBox desktopShortcutCheckBox = new JCheckBox(InstallerConstants.DESKTOP_SHORTCUT_CHECK_BOX_LABEL);
	public static JButton okButton = new JButton("  Install  ");
	public static JButton cancelButton = new JButton("  Cancel  ");
	public static JButton nextButton = new JButton(" Next >> ");
	public static JLabel installingLabel = new JLabel();
	
	boolean install;
	boolean done;
	
	private String path;

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public boolean fileSelected;
	
	private Timer timer;
	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	private int dotCount;
	
	public int getDotCount() {
		return dotCount;
	}

	public void setDotCount(int dotCount) {
		this.dotCount = dotCount;
	}
	
	public Installer() {

		setTitle(InstallerConstants.TITLE);	
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				Object[] options = {" Resume ", "Exit Setup",};
				int choice = JOptionPane.showOptionDialog(null, 
						InstallerConstants.INSTALL_FRAME_CLOSE_MESSAGE, 
						InstallerConstants.INSTALL_FRAME_CLOSE_TITLE, 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[0]);
				if (choice == JOptionPane.YES_OPTION) {
					
				}
				if (choice == JOptionPane.NO_OPTION) {
					setVisible(false);
					dispose();
				}
			}
		});	

		getRootPane().setDefaultButton(okButton);

	    fileSelected = false;
	    install = false;
	    done = false;
		
		//box layout
		Box vb = Box.createVerticalBox();

		Box hbLabel = Box.createHorizontalBox();
		Box hbLabel2 = Box.createHorizontalBox();
		Box hbDirectory = Box.createHorizontalBox();
		Box hbNewFolderCheck = Box.createHorizontalBox();
		Box hbNewFolder = Box.createHorizontalBox();
		Box hbDesktopShortcut = Box.createHorizontalBox();
		Box hbButton = Box.createHorizontalBox();
		Box hbInstallingLabel = Box.createHorizontalBox();

		topLabel.setSize(new Dimension(150, 10));
		//top, left, bottom. right
		topLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
		topLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		topLabel2.setSize(new Dimension(150, 10));
		//top, left, bottom. right
		topLabel2.setBorder(BorderFactory.createEmptyBorder(3, 0, 10, 0));
		topLabel2.setAlignmentX(LEFT_ALIGNMENT);

		topLabel.setMinimumSize(new Dimension(200, 15));
		topLabel2.setMinimumSize(new Dimension(200, 15));
		
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
		labelPanel.add(topLabel);
		labelPanel.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		hbLabel.add(labelPanel);
		
		JPanel labelPanel2 = new JPanel();
		labelPanel2.setLayout(new BoxLayout(labelPanel2, BoxLayout.X_AXIS));	
		labelPanel2.add(topLabel2);
		labelPanel2.setAlignmentX(LEFT_ALIGNMENT);
		labelPanel2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		hbLabel2.add(labelPanel2);
		
		directoryTextField.setEditable(false);
		directoryTextField.setBackground(Color.white);
		directoryTextField.setText(InstallerConstants.DEFAULT_WINDOWS_INSTALL_PATH);
		// if path in text area does not exist, make new folder will be disabled. 
		maybeEnableCheckBox();
		newFolderLabel.setEnabled(false);
		newFolderField.setEnabled(false);

		okButton.setMnemonic(KeyEvent.VK_I);
		okButton.setEnabled(true);
		JLabel blank = new JLabel("    "); 		
		cancelButton.setMnemonic(KeyEvent.VK_C);
		JLabel blankB = new JLabel("    "); 
		nextButton.setMnemonic(KeyEvent.VK_N);
		nextButton.setEnabled(false);
		
		directoryTextField.setPreferredSize(new Dimension(260, 25));
		directoryTextField.setMaximumSize(new Dimension(260, 25));
		directoryTextField.setMinimumSize(new Dimension(260, 25));
		
		JPanel textPanel = new JPanel();
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
		textPanel.add(directoryTextField);
		textPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

		JLabel blank2 = new JLabel("      ");
		JLabel blank3 = new JLabel("      ");
		
		fileButton.setMnemonic(KeyEvent.VK_B);
		
		hbDirectory.add(blank2);
		hbDirectory.add(fileButton);
		hbDirectory.add(textPanel);
		hbDirectory.add(blank3);
		
		newFolderCheckBox.setMnemonic(KeyEvent.VK_F);
		
		JPanel newFolderCheckPanel = new JPanel();
		newFolderCheckPanel.setLayout(new BoxLayout(newFolderCheckPanel, BoxLayout.X_AXIS));
		newFolderCheckPanel.add(newFolderCheckBox);
		newFolderCheckPanel.setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
		
		JLabel blank4 = new JLabel("                                        ");
		
		hbNewFolderCheck.add(newFolderCheckPanel);
		hbNewFolderCheck.add(blank4);
		
		newFolderField.setPreferredSize(new Dimension(200, 25));
		newFolderField.setMaximumSize(new Dimension(200, 25));
		newFolderField.setMinimumSize(new Dimension(200, 25));
		
		JPanel newFolderPanel = new JPanel();
		newFolderPanel.setLayout(new BoxLayout(newFolderPanel, BoxLayout.X_AXIS));
		newFolderPanel.add(newFolderLabel);
		newFolderPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		
		newFolderLabel.setDisplayedMnemonic('E');
		newFolderLabel.setLabelFor(newFolderField);

		hbNewFolder.add(newFolderPanel);
		//hbNewFolder.setAlignmentX(LEFT_ALIGNMENT);

		JPanel newFolderFieldPanel = new JPanel();
		newFolderFieldPanel.setLayout(new BoxLayout(newFolderFieldPanel, BoxLayout.X_AXIS));
		newFolderFieldPanel.add(newFolderField);
		newFolderFieldPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
		
		hbNewFolder.add(newFolderFieldPanel);
		
		desktopShortcutCheckBox.setMnemonic(KeyEvent.VK_D);
		desktopShortcutCheckBox.setSelected(true);
		
		JPanel desktopShortcutPanel = new JPanel();
		desktopShortcutPanel.setLayout(new BoxLayout(desktopShortcutPanel, BoxLayout.X_AXIS));
		desktopShortcutPanel.add(desktopShortcutCheckBox);
		desktopShortcutPanel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
		
		JLabel blank5 = new JLabel("                                                                    ");
		
		hbDesktopShortcut.add(desktopShortcutPanel);
		hbDesktopShortcut.add(blank5);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(okButton);
		buttonPanel.add(blank);
		buttonPanel.add(cancelButton);
		buttonPanel.add(blankB);
		buttonPanel.add(nextButton);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5,20,15,20));

		hbButton.add(buttonPanel);
		
		JPanel installingLabelPanel = new JPanel();
		installingLabelPanel.setLayout(new BoxLayout(installingLabelPanel, BoxLayout.X_AXIS));
		installingLabelPanel.add(installingLabel);
		installingLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		
		hbInstallingLabel.add(installingLabelPanel);

		vb.add(hbLabel);
		vb.add(hbLabel2);
		vb.add(hbDirectory);
		vb.add(hbNewFolderCheck);
		vb.add(hbNewFolder);
		vb.add(hbDesktopShortcut);
		vb.add(hbButton);
		vb.add(hbInstallingLabel);
		add(vb);
		
		//Set up timer to drive animation events.
		timer = new Timer(100, new TimeListener());
		dotCount = 0;
		
		ActionListener fileButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				JTextArea output = null;
				JFileChooser fileChooser = new JFileChooser(); 
				fileChooser.setDialogTitle(InstallerConstants.INSTALL_PATH_FILE_CHOOSER_TITLE);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);							
				fileChooser.setCurrentDirectory(new File("C:\\"));
				//... Open a file dialog.
				int retval = fileChooser.showOpenDialog(output);
				if (retval == JFileChooser.APPROVE_OPTION) {
					//... The user selected a file, get it, use it.          	
					File file = fileChooser.getSelectedFile();
					directoryTextField.setText(file.getPath());
					maybeEnableCheckBox();
				}
			}
		};
		
		ActionListener newFolderCheckBoxActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				if (newFolderCheckBox.isSelected()) {
					newFolderLabel.setEnabled(true);
					newFolderField.setEnabled(true);
				} else {
					newFolderLabel.setEnabled(false);
					newFolderField.setEnabled(false);
				}
			}
		};
		
		ActionListener okButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				install();			
			}
		};
		
		ActionListener cancelButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				setVisible(false);
				dispose();
			}
		}; 
		
		ActionListener nextButtonActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent prodActionEvent) {
				setVisible(false);
				dispose();
				// need to launch MOST programmatically here
			}
		}; 
		
		newFolderCheckBox.addActionListener(newFolderCheckBoxActionListener);
		
		fileButton.addActionListener(fileButtonActionListener);
		okButton.addActionListener(okButtonActionListener);
		cancelButton.addActionListener(cancelButtonActionListener);
		nextButton.addActionListener(nextButtonActionListener);
		
	} 	

	public boolean install() {
		File sourceDir = new File("dist");
		File destDir = new File(directoryTextField.getText());
		if (newFolderCheckBox.isSelected() && newFolderField.getText().length() > 0) {
			String newDirName = directoryTextField.getText() + "\\" + newFolderField.getText();
			destDir = new File(newDirName);
			if (destDir.exists()) {
				Object[] options = {"    Yes    ", "    No    ",};
				int choice = JOptionPane.showOptionDialog(null, 
						InstallerConstants.DIRECTORY_EXISTS_MESSAGE, 
						InstallerConstants.DIRECTORY_EXISTS_TITLE, 
						JOptionPane.YES_NO_OPTION, 
						JOptionPane.QUESTION_MESSAGE, 
						null, options, options[0]);
				if (choice == JOptionPane.YES_OPTION) {
					install = true;
				}
				// set old equation
				if (choice == JOptionPane.NO_OPTION) {
					install = false;
					return false;
				}
			} else {
				destDir.mkdir();
			}
		}
		// only show message if directory exists and is not a newly created folder
		if (destDir.exists() && !newFolderCheckBox.isSelected()) {
			Object[] options = {"    Yes    ", "    No    ",};
			int choice = JOptionPane.showOptionDialog(null, 
					InstallerConstants.DIRECTORY_EXISTS_MESSAGE, 
					InstallerConstants.DIRECTORY_EXISTS_TITLE, 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.QUESTION_MESSAGE, 
					null, options, options[0]);
			if (choice == JOptionPane.YES_OPTION) {
				install = true;
			}
			// set old equation
			if (choice == JOptionPane.NO_OPTION) {
				install = false;
				return false;
			}
		} else {
			install = true;
		}
		if (install) {
			dotCount = 0;
			timer.start();
			try {
				copyDirectory(sourceDir, destDir);
				if (desktopShortcutCheckBox.isSelected()) {
					// create desktop shortcut
					writeVBSScriptFile(destDir.getPath());
					// from http://stackoverflow.com/questions/13586213/how-to-execute-vbs-script-in-java
					Runtime.getRuntime().exec("wscript.exe " + "MOST.vbs");
				}				
				done = true;
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return false;		
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
	
	public void maybeEnableCheckBox() {
		File f = new File(directoryTextField.getText());
		if (f.exists()) {
			newFolderCheckBox.setEnabled(true);
		} else {
			newFolderCheckBox.setEnabled(false);
		}	
	}
	
	class TimeListener implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			dotCount += 1;
			StringBuffer dotBuffer = new StringBuffer();
			int numDots = dotCount % (InstallerConstants.MAX_NUM_DOTS + 1);
			for (int i = 0; i < numDots; i++) {
				dotBuffer.append(" .");
			}
			installingLabel.setText(InstallerConstants.INSTALLING + dotBuffer.toString());
			// allows install animation to run even if install is fast. on slower
			// systems, may take longer than 2 seconds to install
			if (dotCount >= 20) {
				if (done) {
					timer.stop();	
					okButton.setEnabled(false);
					cancelButton.setEnabled(false);
					nextButton.setEnabled(true);
					installingLabel.setText(InstallerConstants.CLICK_NEXT);
				}
			}			
		}
	}

	// create vbs file to create desktop shortcut
	public void writeVBSScriptFile(String path) {
		Writer writer = null;
		StringBuffer bfr = new StringBuffer();
		// vbs file from http://www.tomshardware.com/forum/52871-45-creating-desktop-shortcuts-command-line
		bfr.append("set WshShell = WScript.CreateObject(\"WScript.Shell\")\n");
		bfr.append("strDesktop = WshShell.SpecialFolders(\"AllUsersDesktop\")\n");
		bfr.append("set oShellLink = WshShell.CreateShortcut(strDesktop & \"\\" + InstallerConstants.SHORTCUT_NAME + ".lnk\")\n");
		bfr.append("oShellLink.TargetPath = \"" + path + "\\MOST.exe\"\n");
		bfr.append("oShellLink.WindowStyle = 1\n");
		bfr.append("oShellLink.IconLocation = \"" + path + "\\etc\\most32.ico\"\n");
		bfr.append("oShellLink.Description = \"Shortcut Script\"\n");
		bfr.append("oShellLink.WorkingDirectory = \"" + path + "\"\n");
		bfr.append("oShellLink.Save");
		//System.out.println(bfr.toString());
		try {
			File file = new File("MOST.vbs");
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(bfr.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		//based on code from http://stackoverflow.com/questions/6403821/how-to-add-an-image-to-a-jframe-title-bar
		final ArrayList<Image> icons = new ArrayList<Image>(); 
		icons.add(new ImageIcon("etc/most16.jpg").getImage()); 
		icons.add(new ImageIcon("etc/most32.jpg").getImage());

		Installer frame = new Installer();
		frame.setIconImages(icons);
		frame.setSize(400, 370);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	
		
		//File sourceDir = new File("C:\\MOST");
//		File sourceDir = new File("dist");
		//File destFile = new File("C:\\downloads\\MOST.jar");
		//File dir = new File("C:\\downloads\\test");
//		File destDir = new File("C:\\Program Files\\Rutgers\\MOST");
//		if (!destDir.exists()) {
//			destDir.mkdir();
//		} else {
//			System.out.println("Directory exists. Do you wish to install MOST in this directory?");
//			File destFile = new File("C:\\downloads\\test\\MOST.jar");
//			if (destFile.exists()) {
//				System.out.println("File exists. Do you wish to overwrite the file?");
//			}
//		}
//		File destDir = new File(textField.getText());
//		File destDir = new File("C:\\Program Files\\Rutgers_MOST");
//		copyDirectory(sourceDir, destDir);
//		File destDir = new File("C:\\downloads\\test");
//		File destFile = new File("C:\\downloads\\test\\MOST.jar");
//		copyFile(sourceFile, destFile);
		
	}
}









