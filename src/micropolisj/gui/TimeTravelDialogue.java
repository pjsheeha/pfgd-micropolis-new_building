// This file is part of MicropolisJ.
// Copyright (C) 2013 Jason Long
// Portions Copyright (C) 1989-2007 Electronic Arts Inc.
//
// MicropolisJ is free software; you can redistribute it and/or modify
// it under the terms of the GNU GPLv3, with additional terms.
// See the README file, included in this distribution, for details.

package micropolisj.gui;
import java.io.File;  // Import the File class
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import micropolisj.engine.*;
import static micropolisj.gui.MainWindow.formatFunds;
import static micropolisj.gui.MainWindow.formatGameDate;

public class TimeTravelDialogue extends JDialog
{
	Micropolis engine;
	MainWindow mainw;
	JSpinner taxRateEntry;
	int myYear;


	
	JLabel yearRequest = new JLabel();
	JLabel yearAlloc= new JLabel();
	JSlider yearEntry;



	JLabel taxRevenueLbl = new JLabel();

	static ResourceBundle strings = MainWindow.strings;

	JCheckBox pauseBtn = new JCheckBox(strings.getString("budgetdlg.pause_game"));

	private void applyChange()
	{
		
		int newRoadPct = ((Number) yearEntry.getValue()).intValue();
		

	}

	private void loadBudgetNumbers(boolean updateEntries)
	{

	}
	private JComponent makeFundingRatesPane()
	{
		JPanel fundingRatesPane = new JPanel(new GridBagLayout());
		fundingRatesPane.setBorder(BorderFactory.createEmptyBorder(8,0,8,0));

		GridBagConstraints c0 = new GridBagConstraints();
		c0.gridx = 0;
		c0.weightx = 0.25;
		c0.anchor = GridBagConstraints.WEST;
		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 1;
		c1.weightx = 0.25;
		c1.anchor = GridBagConstraints.EAST;
		GridBagConstraints c2 = new GridBagConstraints();
		c2.gridx = 2;
		c2.weightx = 0.5;
		c2.anchor = GridBagConstraints.EAST;
		GridBagConstraints c3 = new GridBagConstraints();
		c3.gridx = 3;
		c3.weightx = 0.5;
		c3.anchor = GridBagConstraints.EAST;

		c1.gridy = c2.gridy = c3.gridy = 0;
		fundingRatesPane.add(new JLabel(strings.getString("timedlg.name")), c1);
		//fundingRatesPane.add(new JLabel(strings.getString("budgetdlg.requested_hdr")), c2);
		//fundingRatesPane.add(new JLabel(strings.getString("budgetdlg.allocation_hdr")), c3);

		c0.gridy = c1.gridy = c2.gridy = c3.gridy = 1;


		c0.gridy = c1.gridy = c2.gridy = c3.gridy = 2;
		fundingRatesPane.add(new JLabel(strings.getString("timedlg.travel_year")), c0);



		return fundingRatesPane;
	}


	private JComponent makeTaxPane()
	{
		JPanel pane = new JPanel(new GridBagLayout());
		pane.setBorder(BorderFactory.createEmptyBorder(0,0,8,0));

		GridBagConstraints c0 = new GridBagConstraints();
		GridBagConstraints c1 = new GridBagConstraints();
		GridBagConstraints c2 = new GridBagConstraints();

		c0.gridx = 0;
		c0.anchor = GridBagConstraints.WEST;
		c0.weightx = 0.25;
		c1.gridx = 1;
		c1.anchor = GridBagConstraints.EAST;
		c1.weightx = 0.25;
		c2.gridx = 2;
		c2.anchor = GridBagConstraints.EAST;
		c2.weightx = 0.5;

		c0.gridy = c1.gridy = c2.gridy = 0;
		pane.add(new JLabel(strings.getString("budgetdlg.tax_rate_hdr")), c1);
		pane.add(new JLabel(strings.getString("budgetdlg.annual_receipts_hdr")), c2);

		c0.gridy = c1.gridy = c2.gridy = 1;
		pane.add(new JLabel(strings.getString("budgetdlg.tax_revenue")), c0);
		pane.add(taxRateEntry, c1);
		pane.add(taxRevenueLbl, c2);

		return pane;
	}
	public TimeTravelDialogue(Window owner, Micropolis engine)
	{
		super(owner);
		setTitle(strings.getString("timedlg.title"));
		int ourYear = myYear + 1900;
		this.engine = engine;
		this.myYear = engine.myYear;
		
		//System.out.println(yearEntry);
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		add(mainBox, BorderLayout.CENTER);
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		mainBox.add(makeFundingRatesPane());
		taxRevenueLbl.setText("year: "+yearEntry);
		yearAlloc.setText(""+engine.myYear);

		JButton continueBtn = new JButton(strings.getString("timedlg.continue"));
		continueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				onContinueClicked();
			}});
		buttonPane.add(continueBtn);


		loadBudgetNumbers(true);
		setAutoRequestFocus_compat(false);
		pack();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(owner);
		getRootPane().registerKeyboardAction(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}},
			KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
			JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
	private void onContinueClicked()
	{

		
		if (engine.cityTime > 48*2) {

		int theYear = (int)Math.floor(engine.cityTime/48);
		engine.myYear = engine.myYear - 1;
		engine.cityTime -= 48;
		engine.doTimeTravel();
		

		
		}
		else {
			System.out.println("You can't go back in time yet.");
		}

		dispose();
	}
	private void setAutoRequestFocus_compat(boolean v)
	{
		try
		{
			if (super.getClass().getMethod("setAutoRequestFocus", boolean.class) != null) {
				super.setAutoRequestFocus(v);
			}
		}
		catch (NoSuchMethodException e) {
			// ok to ignore
		}
	}



}
