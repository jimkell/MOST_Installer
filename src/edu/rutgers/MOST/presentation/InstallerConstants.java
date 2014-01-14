package edu.rutgers.MOST.presentation;

public class InstallerConstants {

	public static final String TITLE = "MOST - Setup";
	
	// Tried using left alignment, but it refused to work. The only way to get these to line up was with spaces.
	public static final String TOP_LABEL =  "Click 'Install' to install MOST in the following folder.                        "; 
	public static final String TOP_LABEL2 = "To select another folder, click Browse and select another folder.";
	public static final String DEFAULT_WINDOWS_INSTALL_PATH = "C:\\Program Files\\Rutgers_MOST";
	
	public static final String DIRECTORY_EXISTS_TITLE = "Directory Exists";
	public static final String DIRECTORY_EXISTS_MESSAGE = "Directory already exists. Continuing this installation may " +
			"overwrite the contents of this directory.\n Continue with installation?";
	
	public static final String INSTALL_PATH_FILE_CHOOSER_TITLE = "Browse for Install Path";
	
	public static final String NEW_FOLDER_CHECK_BOX_LABEL = "Create New Folder in Selected Directory";
	public static final String NEW_FOLDER_LABEL = "Enter Folder Name                  ";
	
	public static final String INSTALL_FRAME_CLOSE_TITLE = "Install";
	public static final String INSTALL_FRAME_CLOSE_MESSAGE = "Setup is not complete. If you quit the setup\n" +
			"program now, the program will not be installed.\n\n" +
			"You may run the setup at a later time\n" +
			"to complete the insatllation\n\n" +
			"To continue the installation. click Resume To quit\n" +
			"the setup program, click Exit Setup.\n\n" +
			"";
	
	public static final String INSTALLING = "Installing";
	public static final String PROCESSING_OOT = " .";
	public static final int MAX_NUM_DOTS = 21;
	
	public static final String CLICK_NEXT = "Installation complete. Click 'Next' to set up Gurobi";
	
	public static final String DESKTOP_SHORTCUT_CHECK_BOX_LABEL = "Create Desktop Shortcut";
	public static final String SHORTCUT_NAME = "MOST";
	
}
