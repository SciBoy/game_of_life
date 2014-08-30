package com.unseen.life;

import java.util.List;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalSize;

public class DisplayPopulation {
	private final Population mPopulation;
	private final Terminal mTerminal;
	private final Screen mScreen;
	private int mOffsetX = 0;
	private int mOffsetY = 0;
	
	public DisplayPopulation(
			final Population iPopulation)
	{
		mPopulation = iPopulation;
		mTerminal = TerminalFacade.createTerminal();
		mTerminal.setCursorVisible(false);
		mScreen = new Screen(mTerminal);
	}
	
	public void start() {
		mScreen.startScreen();
	}
	
	private void drawPopulation() {
		final TerminalSize size = mScreen.getTerminalSize();
		final List<Coordinate> cells = mPopulation.getCellsWithin(
				mOffsetX,
				mOffsetY,
				mOffsetX + size.getColumns(),
				mOffsetY + size.getRows());
		for (Coordinate pos : cells) {
			mScreen.putString(
					pos.x - mOffsetX,
					pos.y - mOffsetY,
					"*",
					Terminal.Color.BLUE,
					Terminal.Color.BLACK);
		}
	}
	
	public void draw() {
		mScreen.clear();
		mScreen.putString(
				0,
				0,
				"Generation " + Integer.toString(mPopulation.getGeneration()),
				Terminal.Color.RED,
				Terminal.Color.BLACK);
		mScreen.putString(
				20,
				0,
				"X=" + Integer.toString(mOffsetX),
				Terminal.Color.GREEN,
				Terminal.Color.BLACK);
		mScreen.putString(
				30,
				0,
				"X=" + Integer.toString(mOffsetX),
				Terminal.Color.GREEN,
				Terminal.Color.BLACK);
		drawPopulation();
	}
	
	public void drawCalculationTime(
			final long i_nanoTime) 
	{
		mScreen.putString(
				40,
				0,
				"Calculation time:" + Long.toString(i_nanoTime / 1000) + "µs",
				Terminal.Color.GREEN,
				Terminal.Color.BLACK);
	}
	
	public void refresh() {
		mScreen.refresh();
	}
	
	public void stop() {
		mScreen.stopScreen();
	}
	
	public void centerPopulation() {
		final TerminalSize size = mScreen.getTerminalSize();
		mOffsetX = mPopulation.getCenterX() - ( size.getColumns() / 2 );
		mOffsetY = mPopulation.getCenterY() - ( size.getRows() / 2 );
	}
}
