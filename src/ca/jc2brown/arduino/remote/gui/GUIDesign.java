package ca.jc2brown.arduino.remote.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Link;

public class GUIDesign extends Shell {
	
	public static final String CODE_STR = "Code";
	public static final String NAME_STR = "Name";
	public static final String ARGS_STR = "Arguments";
	public static final String KEY_STR = "Key";
	public static final String CMD_STR = "Command";
	
	private Text txtKey;
	private Text txtName;
	private Text txtCmd;
	private Text txtCode;
	private Table table;
	TableColumn tblclmnCode;
	TableColumn tblclmnName;
	TableColumn tblclmnKey;
	TableColumn tblclmnCommand;
	TableColumn tblclmnArguments;
	private Link link;
	private Text txtArgs;
	private Button btnSave;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			GUIDesign shell = new GUIDesign(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public GUIDesign(Display display) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new GridLayout(3, false));
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");
		
		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);
		
		MenuItem mntmClose = new MenuItem(menu_1, SWT.NONE);
		mntmClose.setText("Close");
		
		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);
		
		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");
		
		Menu menu_3 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_3);
		
		Group grpCommands = new Group(this, SWT.NONE);
		grpCommands.setLayout(new GridLayout(1, false));
		GridData gd_grpCommands = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_grpCommands.widthHint = 862;
		gd_grpCommands.heightHint = 590;
		grpCommands.setLayoutData(gd_grpCommands);
		grpCommands.setText("Commands");
		
		table = new Table(grpCommands, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_table.widthHint = 728;
		gd_table.heightHint = 573;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tblclmnCode = new TableColumn(table, SWT.NONE);
		tblclmnCode.setWidth(73);
		tblclmnCode.setText(CODE_STR);
		
		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setWidth(102);
		tblclmnName.setText(NAME_STR);
		
		tblclmnKey = new TableColumn(table, SWT.NONE);
		tblclmnKey.setWidth(52);
		tblclmnKey.setText(KEY_STR);
		
		tblclmnCommand = new TableColumn(table, SWT.NONE);
		tblclmnCommand.setWidth(300);
		tblclmnCommand.setText(CMD_STR);
		
		tblclmnArguments = new TableColumn(table, SWT.NONE);
		tblclmnArguments.setWidth(194);
		tblclmnArguments.setText(ARGS_STR);
		
		Group grpTest = new Group(this, SWT.NONE);
		grpTest.setText("Edit Command");
		grpTest.setLayout(new GridLayout(3, false));
		GridData gd_grpTest = new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 1);
		gd_grpTest.heightHint = 624;
		gd_grpTest.widthHint = 500;
		grpTest.setLayoutData(gd_grpTest);
		
		Button btnNew = new Button(grpTest, SWT.NONE);
		btnNew.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNew.setText("New");
		new Label(grpTest, SWT.NONE);
		
		Button btnDelete = new Button(grpTest, SWT.NONE);
		btnDelete.setText("Delete");
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Label lblName = new Label(grpTest, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name");
		
		txtName = new Text(grpTest, SWT.BORDER);
		GridData gd_txtName = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtName.widthHint = 240;
		txtName.setLayoutData(gd_txtName);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Label lblKey = new Label(grpTest, SWT.NONE);
		lblKey.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKey.setText("Key");
		
		txtKey = new Text(grpTest, SWT.BORDER);
		GridData gd_txtKey = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtKey.widthHint = 120;
		txtKey.setLayoutData(gd_txtKey);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Label lblCode = new Label(grpTest, SWT.NONE);
		lblCode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCode.setText("Code");
		
		txtCode = new Text(grpTest, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtCode = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_txtCode.widthHint = 120;
		txtCode.setLayoutData(gd_txtCode);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		link = new Link(grpTest, SWT.NONE);
		link.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		link.setText("<a>Cmd</a>");
		
		txtCmd = new Text(grpTest, SWT.BORDER);
		txtCmd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Label lblArgs = new Label(grpTest, SWT.NONE);
		lblArgs.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblArgs.setText("Args");
		
		txtArgs = new Text(grpTest, SWT.BORDER);
		txtArgs.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		btnSave = new Button(grpTest, SWT.NONE);
		btnSave.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnSave.setText("Save");
		new Label(grpTest, SWT.NONE);
		
		Button btnClear = new Button(grpTest, SWT.NONE);
		btnClear.setText("Clear");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWTplication");
		setSize(1146, 628);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	protected Link getLink() {
		return link;
	}
	public Button getBtnSave() {
		return btnSave;
	}
}
