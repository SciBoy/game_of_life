package com.unseen.life;

import java.util.TreeMap;

public class Culture extends TreeMap<Integer,Row> {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2666707086643176537L;

	private int mMinX = Integer.MAX_VALUE;
	private int mMinY = Integer.MAX_VALUE;
	private long mSumX = 0;
	private long mSumY = 0;
	
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
		if (!row.contains(i_coordinate.x)) {
			row.add(i_coordinate.x);
			mSumX += i_coordinate.x;
			mSumY += i_coordinate.y;
		}
		if (i_coordinate.x < mMinX) {
			mMinX = i_coordinate.x;
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
	
	public int getCenterX() {
		return (int)(mSumX / size());
	}
	
	public int getCenterY() {
		return (int)(mSumY / size());
	}
}