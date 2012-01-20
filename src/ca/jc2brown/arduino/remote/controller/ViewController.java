package ca.jc2brown.arduino.remote.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;

import ca.jc2brown.arduino.remote.gui.View;
import ca.jc2brown.arduino.remote.model.Executable;
import ca.jc2brown.arduino.remote.service.ExecutableService;
import ca.jc2brown.arduino.remote.service.SerialService;


public class ViewController {

	public static final boolean DEBUG = true; 
	public static final boolean INFO = true;

	
	private ExecutableService exeService; 
	private SerialService serialService;
		
	private Display display;
	private View view;
	
	private Long activeCode;
	private String sortColumn;
	private boolean sortReverse;
	private boolean edit;
	
	public ViewController(SerialService serialService, ExecutableService exeService, Display display) {
		this.serialService = serialService;
		this.exeService = exeService;
		this.display = display;
		edit = false;
		view = new View(this, display);
		view.open();
		view.layout();
		activeCode = null;
		sortColumn = View.CODE_STR;
		sortReverse = false;
		updateView();
	}

	public void setActiveCode(Long activeCode) {
		this.activeCode = activeCode;
		updateView();
	}
	
	public void setCode(Long code) {
		view.setCode(code);
		view.redraw();
	}
	
	
	public void setViewItems() {
		Set<Executable> exeSet = exeService.getExeSet();
		List<Executable> exeList = new ArrayList<Executable>(exeSet);
		
		Comparator<Executable> cmp = null;
		
		if ( sortColumn.equals(View.NAME_STR) ) {
			cmp = Executable.CMP_NAME;	
		} else if ( sortColumn.equals(View.KEY_STR) ) {
			cmp = Executable.CMP_KEY;	
		} else if ( sortColumn.equals(View.CODE_STR) ) {
			cmp = Executable.CMP_CODE;	
		} else if ( sortColumn.equals(View.CMD_STR) ) {
			cmp = Executable.CMP_CMD;	
		} else if ( sortColumn.equals(View.ARGS_STR) ) {
			cmp = Executable.CMP_ARGS;	
		}
		
		Collections.sort(exeList, cmp);
		if ( sortReverse ) {
			Collections.reverse(exeList);
		}
		view.setItems(exeList);
	}
	
	public void updateView() {
		setViewItems();
		Executable activeExe = exeService.getExe(activeCode);
		if ( activeExe == null ) {
			view.clear();
		} else {
			view.load(activeExe);
		}
		view.setEdit(edit);
		view.setSelectedCode(activeCode);
	}
	
	




	public boolean isShellDisposed() {
		return view.isDisposed();
	}
	
	public void destroy() {
		display.dispose();
	}
	
	
	public SelectionListener saveListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {
			if ( ! edit ) {
				edit = true;
				updateView();
				return;
			}
			if ( ! view.isValid() ) {
				MessageBox mb = new MessageBox(view, SWT.DIALOG_TRIM | SWT.OK | SWT.ICON_WARNING);
		        mb.setText("Save Error");
		        mb.setMessage("Input is not valid.");

		        mb.open();
				return;
			}
			activeCode = null;
			edit = false;
			Executable exe = view.unload();
			debug("Saving exe: " + exe );
			exeService.save(exe);
			exeService.store();
			updateView();
		}		
	};
	
	public SelectionListener clearListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {
			edit = false;
			activeCode = null;
			debug("Clear");
			updateView();
		}		
	};
	
	public SelectionListener newListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {
			debug("New");
			activeCode = null;
			edit = true;
			updateView();
		}		
	};
	
	public SelectionListener deleteListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {	
			Long code = view.getSelectedCode();
			if ( code != null ) {
				debug("Deleting code: " + code );
				exeService.deleteExe(code);
			}
			edit = false;
			updateView();
		}		
	};
	
	public SelectionListener executeListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {	
			Long code = view.getSelectedCode();
			if ( code != null ) {
				debug("Simulating code: " + code );
				serialService.simulate(code);
			}
			edit = false;
			updateView();
		}		
	};
	
	public SelectionListener cmdLinkListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {
			FileDialog fd = new FileDialog(view, SWT.OPEN);
			String exePath = fd.open();
			if ( exePath != null ) {
				view.setCmd(exePath);
			}
		}		
	};	
	
	public SelectionListener selectListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {
			edit = false;
			Long code = view.getSelectedCode();
			Executable exe = exeService.getExe(code);
			activeCode = exe.getCode();
			updateView();
		}		
	};
	
	public SelectionListener sortListener = new SelectionListener() {
		public void widgetDefaultSelected(SelectionEvent event) {}
		public void widgetSelected(SelectionEvent event) {		
			String newSortColumn = ((TableColumn)event.getSource()).getText();
			if ( sortColumn.equals(newSortColumn) ) {
				sortReverse = ! sortReverse;
			}
			sortColumn = newSortColumn;
			updateView();
		}		
	};
	
	
	public static void debug(Object o) {
		if ( DEBUG ) {
			System.out.println("ViewController:\t" + o);
		}
	}
	public static void info(Object o) {
		if ( INFO ) {
			System.out.println("ViewController:\t" + o);
		}
	}



	public boolean isEdit() {
		return edit;
	}
	
}