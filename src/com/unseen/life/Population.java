package com.unseen.life;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

class Population {

	private class Row extends HashSet<Integer> {
		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = -8083725112470121454L;
	}

	private class Culture extends HashMap<Integer,Row> {
		/**
		 * Serial Version UID
		 */
		private static final long serialVersionUID = 2666707086643176537L;

		private int mMinX = Integer.MAX_VALUE;
		private int mMinY = Integer.MAX_VALUE;
		
		public void add(
			final Coordinate i_coordinate)
		{
			Row row;
			if (containsKey(i_coordinate.y)) {
				row = get(i_coordinate.y);
			} else {
				row = new Row();
				put(i_coordinate.y, row);
			}
			row.add(i_coordinate.x);
			if (i_coordinate.x < mMinX) {
				mMinX = i_coordinate.y;
			}
			if (i_coordinate.y < mMinY) {
				mMinY = i_coordinate.y;
			}
			
		}

		public boolean hasCellAt(
			final Coordinate i_pos)
		{
			Row row = get(i_pos.y);
			return ((null != row) &&
				    (row.contains(i_pos.x)));
		}
		
		public int getMinX() {
			return mMinX;
		}
		
		public int getMinY() {
			return mMinY;
		}
	}
	
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
				final Coordinate cellPos = new Coordinate(x, entry.getKey());
				if (shouldHaveCell(cellPos)) {
					newCulture.add(cellPos);
				}
				for (Coordinate neighbour : Coordinate.NEIGHBOURS) {
					final Coordinate pos = neighbour.add(cellPos);
					if (shouldHaveCell(pos)) {
						newCulture.add(pos);
					}
				}
			}
		}
		mCulture = newCulture;
	}
	
	public void dump() {
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
			}
		}
		System.out.println();
	}
}