/*
Copyright Jim Brain and Brain Innovations, 2005.

This file is part of QLinkServer.

QLinkServer is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

QLinkServer is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with QLinkServer; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

@author Jim Brain
Created on Jul 23, 2005

*/
package org.jbrain.qlink.state;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.jbrain.qlink.*;
import org.jbrain.qlink.cmd.action.*;
import org.jbrain.qlink.dialog.*;
import org.jbrain.qlink.user.QHandle;
import org.jbrain.qlink.user.UserManager;

public class TestState extends AbstractAccountState {
  private static Logger _log = Logger.getLogger(AddUserNameState.class);
  public static final int PHASE_INITIAL = 1;
  private static EntryDialog _searchLibraryDialog;

  private DialogCallBack _searchLibraryCallBack =
      new DialogCallBack() {
        /* (non-Javadoc)
         * @see org.jbrain.qlink.state.DialogCallBack#handleResponse(org.jbrain.qlink.dialog.AbstractDialog, org.jbrain.qlink.cmd.action.Action)
         */
        public boolean handleResponse(AbstractDialog d, Action a) throws IOException {
          int id;
          String text;
          QHandle handle;

          _log.debug("We received " + a.getName() + " from entry dialog");
         
         if (a instanceof ZA) {
            text = ((ZA) a).getResponse();
            text = text.trim();
           _log.debug("We received " + text + " from entry dialog"); 
            
            if (text.length()<90) {
          //get datat out of the dadabase
         
         
        
          
          
               _session.send(((EntryDialog) d).getSuccessResponse(""));
              
				   
				                   _session.send(
                    new FileText("User name '" + text + "' successfully created.", false));
                
                _session.send(new FileText("          <PRESS F5 FOR MENU>", true));
               
                _session.setState(_intState);
                return true;
              } else {
               _session.send(
                    ((EntryDialog) d).getErrorResponse( "We're sorry, but The String is to long"));
              }
            
          } else if (a instanceof DialogCancel) {
            _session.send(new InitDataSend(0, 0, 0));
            _session.send(new FileText("cancel", false));
            _session.send(new FileText("", false));
            _session.send(new FileText("          <PRESS F5 FOR MENU>", true));
            _session.setState(_intState);
          }
          return false;
        }
      
};
	private QState _intState;
   
    
	static {
    // define a static dialog for this.
    _log.debug("Defining ADDNAME dialog");
    _searchLibraryDialog = new EntryDialog("SEARCH", EntryDialog.TYPE_MENU, EntryDialog.FORMAT_NONE);
    _searchLibraryDialog.addText("Enter the search phrase");
	_log.debug("instate");
    }
  
  
  
  public TestState(QSession session) {
    super(session, PHASE_INITIAL);
  }

  public void activate() throws IOException {
	  
	   _log.debug("Aktivate");
	    //   List l = UserManager.getSubAccountsforUser(_session.getUserID());

    if (0 < 5) {
      _intState = _session.getState();
      super.activate();
      EntryDialogState state =
         new EntryDialogState(_session, _searchLibraryDialog, _searchLibraryCallBack);
     state.activate();
    } else {
      _session.send(new InitDataSend(0, 0, 0));
      _session.send(
          new FileText(
              "You have created the maximum number of user names (5).  Please delete an unused screen name and try again.",
              false));
      _session.send(new FileText("", false));
      _session.send(new FileText("            <PRESS F5 FOR MENU>", true));
    }
  }

  public boolean execute(Action a) throws IOException {
    QState state;
    boolean rc = false;
     _log.debug("execute");

    // handle global stuff here
    switch (getPhase()) {
      case PHASE_INITIAL:
        break;
    }
    if (!rc) rc = super.execute(a);
    return rc;
  }
}
