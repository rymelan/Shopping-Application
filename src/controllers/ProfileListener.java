package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logic.DatabaseHandler;

public class ProfileListener implements ActionListener{
	private DatabaseHandler dbh = new DatabaseHandler();
	private String change;
	
	public ProfileListener(String type) {
		change = type;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
