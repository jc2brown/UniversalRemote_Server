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
import org.eclipse.swt.widgets.DateTime;

public class GUIDesign extends Shell {
	
	public static final String CODE_STR 	= "Code";
	public static final String NAME_STR 	= "Name";
	public static final String ARGS_STR 	= "Arguments";
	public static final String KEY_STR 		= "Key";
	public static final String CMD_STR 		= "Command";
	public static final String AUTO_STR 	= "Auto";
	public static final String TR1_STR 		= "Trigger 1";
	public static final String TR2_STR 		= "Trigger 2";
	
	
	private Table table;
	TableColumn tblclmnCode;
	TableColumn tblclmnName;
	TableColumn tblclmnKey;
	TableColumn tblclmnCommand;
	TableColumn tblclmnArguments;
	TableColumn tblclmnAuto;
	TableColumn tblclmnTr1;
	TableColumn tblclmnTr2;
	
	private Text txtKey;
	private Text txtName;
	private Text txtCmd;
	private Text txtCode;
	private Link link;
	private Text txtArgs;
	private Button enableAuto;
	private DateTime dtTr1;
	private DateTime dtTr2;
	private Button btnNew;
	private Button btnDelete;
	private Button btnClear;
	private Button btnExecute;
	private Button btnSave;
	private Button btnEq;

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
		gd_grpCommands.widthHint = 1000;
		gd_grpCommands.heightHint = 590;
		grpCommands.setLayoutData(gd_grpCommands);
		grpCommands.setText("Commands");
		
		table = new Table(grpCommands, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1);
		gd_table.widthHint = 1000;
		gd_table.heightHint = 573;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		tblclmnCode = new TableColumn(table, SWT.NONE);
		tblclmnCode.setMoveable(true);
		tblclmnCode.setResizable(false);
		tblclmnCode.setWidth(80);
		tblclmnCode.setText(CODE_STR);
		
		tblclmnName = new TableColumn(table, SWT.NONE);
		tblclmnName.setMoveable(true);
		tblclmnName.setWidth(110);
		tblclmnName.setText(NAME_STR);
		
		tblclmnKey = new TableColumn(table, SWT.NONE);
		tblclmnKey.setMoveable(true);
		tblclmnKey.setWidth(80);
		tblclmnKey.setText(KEY_STR);
		
		tblclmnCommand = new TableColumn(table, SWT.NONE);
		tblclmnCommand.setMoveable(true);
		tblclmnCommand.setWidth(100);
		tblclmnCommand.setText(CMD_STR);
		
		tblclmnArguments = new TableColumn(table, SWT.NONE);
		tblclmnArguments.setMoveable(true);
		tblclmnArguments.setWidth(230);
		tblclmnArguments.setText(ARGS_STR);
		
		tblclmnAuto = new TableColumn(table, SWT.NONE);
		tblclmnAuto.setMoveable(true);
		tblclmnAuto.setResizable(false);
		tblclmnAuto.setWidth(50);
		tblclmnAuto.setText("Auto");
		
		tblclmnTr1 = new TableColumn(table, SWT.NONE);
		tblclmnTr1.setResizable(false);
		tblclmnTr1.setMoveable(true);
		tblclmnTr1.setWidth(60);
		tblclmnTr1.setText("Trigger 1");
		
		tblclmnTr2 = new TableColumn(table, SWT.NONE);
		tblclmnTr2.setResizable(false);
		tblclmnTr2.setMoveable(true);
		tblclmnTr2.setWidth(60);
		tblclmnTr2.setText("Trigger 2");
		
		Group grpTest = new Group(this, SWT.NONE);
		grpTest.setText("Edit Command");
		grpTest.setLayout(new GridLayout(3, false));
		GridData gd_grpTest = new GridData(SWT.LEFT, SWT.CENTER, true, true, 2, 1);
		gd_grpTest.heightHint = 624;
		gd_grpTest.widthHint = 600;
		grpTest.setLayoutData(gd_grpTest);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		btnNew = new Button(grpTest, SWT.NONE);
		btnNew.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnNew.setText("New");
		new Label(grpTest, SWT.NONE);
		
		btnDelete = new Button(grpTest, SWT.NONE);
		btnDelete.setText("Delete");
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
		
		Label lblAuto = new Label(grpTest, SWT.NONE);
		lblAuto.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAuto.setText("Auto");
		
		enableAuto = new Button(grpTest, SWT.CHECK);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Label lblOnTime = new Label(grpTest, SWT.NONE);
		lblOnTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOnTime.setText("Trigger 1");
		
		dtTr1 = new DateTime(grpTest, SWT.BORDER | SWT.TIME | SWT.SHORT);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		btnEq = new Button(grpTest, SWT.NONE);
		btnEq.setText(" = ");
		new Label(grpTest, SWT.NONE);
		
		Label lblOffTime = new Label(grpTest, SWT.NONE);
		lblOffTime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOffTime.setText("Trigger 2");
		
		dtTr2 = new DateTime(grpTest, SWT.BORDER | SWT.TIME | SWT.SHORT);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		btnSave = new Button(grpTest, SWT.NONE);
		btnSave.setText("Save");
		new Label(grpTest, SWT.NONE);
		
		btnClear = new Button(grpTest, SWT.NONE);
		btnClear.setText("Clear");
		new Label(grpTest, SWT.NONE);
		
		btnExecute = new Button(grpTest, SWT.NONE);
		btnExecute.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnExecute.setText("Execute");
		new Label(grpTest, SWT.NONE);
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWTplication");
		setSize(1250, 628);

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
