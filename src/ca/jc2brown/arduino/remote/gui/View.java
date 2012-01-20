package ca.jc2brown.arduino.remote.gui;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Link;

import ca.jc2brown.arduino.remote.controller.ViewController;
import ca.jc2brown.arduino.remote.model.Executable;
import ca.jc2brown.arduino.remote.util.Util;

public class View extends Shell {
	
	public static final String CODE_STR = "Code";
	public static final String NAME_STR = "Name";
	public static final String ARGS_STR = "Arguments";
	public static final String KEY_STR = "Key";
	public static final String CMD_STR = "Command";
	
	
	private Text txtName;
	private Text txtKey;
	private Text txtCode;
	private Text txtCmd;
	private Text txtArgs;
	private Table table;
	TableColumn tblclmnCode;
	TableColumn tblclmnName;
	TableColumn tblclmnKey;
	TableColumn tblclmnCommand;
	TableColumn tblclmnArguments;
	private Link link;
	private Button btnSave;

	public View(ViewController vc, Display display) {
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
		

		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		
		Button btnExecute = new Button(grpTest, SWT.NONE);
		btnExecute.setText("Execute");
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		new Label(grpTest, SWT.NONE);
		

		table.addSelectionListener(vc.selectListener);
		tblclmnName.addSelectionListener(vc.sortListener);
		tblclmnKey.addSelectionListener(vc.sortListener);
		tblclmnCode.addSelectionListener(vc.sortListener);
		tblclmnCommand.addSelectionListener(vc.sortListener);
		tblclmnArguments.addSelectionListener(vc.sortListener);
		link.addSelectionListener(vc.cmdLinkListener);
		btnNew.addSelectionListener(vc.newListener);
		btnDelete.addSelectionListener(vc.deleteListener);
		btnSave.addSelectionListener(vc.saveListener);
		btnClear.addSelectionListener(vc.clearListener);
		btnExecute.addSelectionListener(vc.executeListener);
		
		setText(Display.getAppName());
		setSize(1146, 628);
		clear();
	}

	
	public void setCode(Long code) {
		txtCode.setText(Util.toHex(code));
	}
	
	public void setCmd(String cmd) {
		txtCmd.setText(cmd);
	}
	
	public void setItems(List<Executable> list) {
		table.removeAll();
		for ( Executable exe : list ) {
			TableItem item = new TableItem(table, SWT.NONE); 
			item.setText(exe.toStringArray());
		}
	}
	
	public void setSelectedCode(Long code) {
		for ( TableItem item : table.getItems() ) {
			String codeStr = item.getText();
			if ( Util.toHex(codeStr).equals(code) ) {
				table.setSelection(item);
				return;
			}
		}
		table.setSelection(-1);
	}
	

	public void load(Executable exe) {
		txtName.setText(exe.getName());
		txtKey.setText(exe.getKey());
		txtCode.setText(Util.toHex(exe.getCode()));
		txtCmd.setText(exe.getCmd());
		txtArgs.setText(exe.getArgs());
	}
	
	public Executable unload() {
		String name = txtName.getText();
		String key = txtKey.getText();
		Long code = Util.toHex(txtCode.getText());
		String cmd = txtCmd.getText();
		String args = txtArgs.getText();
		return new Executable(code, key, name, cmd, args);
	}
	
	public void clear() {
		txtKey.setText("");
		txtCmd.setText("");
		txtCode.setText("");
		txtName.setText("");
		txtArgs.setText("");
	}
	
	
	public boolean isValid() {
		boolean valid = true;
		
		// Empty fields
		if ( Util.isEmpty(txtKey.getText()) ) {
			valid = false;
		}
		if ( Util.isEmpty(txtCmd.getText()) ) {
			valid = false;
		}	
		if ( Util.isEmpty(txtCode.getText()) ) {
			valid = false;
		}
		if ( Util.isEmpty(txtName.getText()) ) {
			valid = false;
		}
		
		// Invalid fields
		try {
			Util.toHex(txtCode.getText());
		} catch ( NumberFormatException e ) {
			valid = false;
		}
		
		return valid;
	}
	
	
	// Don't touch this
	protected void checkSubclass() {}

	
	public void setEdit(boolean edit) {
		link.setEnabled(edit);
		txtName.setEditable(edit);
		txtKey.setEditable(edit);
		txtCmd.setEditable(edit);
		txtArgs.setEditable(edit);
		btnSave.setText( ( edit ? "Save" : "Edit" ) );
	}
	
	public Long getSelectedCode() {
		String[] selectedItem = getSelectedItem();
		if ( selectedItem == null ) {
			return null;
		}
		String codeStr = selectedItem[Executable.iCode];
		Long code = Util.toHex(codeStr);
		return code;
	}

	public String[] getSelectedItem() {
		TableItem[] item = table.getSelection();
		if ( item.length == 0 ) {
			return null;
		}
		String[] a = new String[item.length];
		for ( int i = 0; i < item.length; i += 1 ) {
			a[i] = item[i].getText();
		}
		return a;
	}


	public void setSortColumn(TableColumn sortColumn) {
		table.setSortColumn(sortColumn);
	}
}
