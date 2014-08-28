package com.unseen.life;

import java.util.List;
import java.util.Map;

class Population {

	private Culture mCulture = new Culture();

	public Population(
		final String i_initialPopulation) 
	{
		final List<Coordinate> coordinates = Coordinate.fromString(i_initialPopulation);
		for(Coordinate coordinate : coordinates) {
			mCulture.add(coordinate);
		}
	}

	private int getNofNeighbours(
		final Coordinate i_pos) 
	{
		int neighbours = 0;
		for (Coordinate offset : Coordinate.NEIGHBOURS) {
			final Coordinate pos = offset.add(i_pos);
			if (mCulture.hasCellAt(pos)) {
				neighbours++;
			}
		}
		return neighbours;
	}
	
	private boolean shouldHaveCell(
			final Coordinate i_pos)
	{
		final int nofNeighbours = getNofNeighbours(i_pos);
		if (mCulture.hasCellAt(i_pos)) {
			return ((nofNeighbours > 1) &&
				    (nofNeighbours < 4));
		} else {
			return (3 == nofNeighbours);
		}
	}
	
	public void advance() {
		final Culture newCulture = new Culture();
		for (Map.Entry<Integer,Row> entry : mCulture.entrySet()) {
			for (Integer x : entry.getValue()) {
				final Coordinate cellPos = 
						addCellIfStillAlive(newCulture, entry, x);
				addNewbornCells(newCulture, cellPos);
			}
		}
		mCulture = newCulture;
	}

	private Coordinate addCellIfStillAlive(final Culture newCulture,
			final Map.Entry<Integer, Row> i_entry, 
			final Integer i_x) 
	{
		final Coordinate cellPos = new Coordinate(i_x, i_entry.getKey());
		if (shouldHaveCell(cellPos)) {
			newCulture.add(cellPos);
		}
		return cellPos;
	}

	private void addNewbornCells(final Culture newCulture,
			final Coordinate cellPos) {
		for (Coordinate neighbour : Coordinate.NEIGHBOURS) {
			final Coordinate pos = neighbour.add(cellPos);
			if (shouldHaveCell(pos)) {
				newCulture.add(pos);
			}
		}
	}
	
	public void dump() {
		System.out.println("--------------");
		int lastY = mCulture.getMinY();
		for (Map.Entry<Integer,Row> entry : mCulture.entrySet()) {
			final int yPos = entry.getKey() - lastY;
			lastY = entry.getKey();
			for (int i = 0; i < yPos; i++) {
				System.out.println();
			}
			int lastX = mCulture.getMinX();
			for (Integer x : entry.getValue()) {
				final int xPos = x - lastX;
				lastX = x;
				for (int i = 0; i < xPos; i++) {
					System.out.print(" ");
				}
				System.out.print("*");
				lastX++;
			}
		}
		System.out.println();
	}
}