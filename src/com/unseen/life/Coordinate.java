package com.unseen.life;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {
	private static final String COORDINATE_REGEX = 
		"\\((-?\\d+),(-?\\d+)\\)";

	public static Coordinate[] NEIGHBOURS = {
		new Coordinate(-1,-1),
		new Coordinate(0,-1),
		new Coordinate(1,-1),
		new Coordinate(-1,0),
		new Coordinate(1,0),
		new Coordinate(-1,1),
		new Coordinate(0,1),
		new Coordinate(1,1)
	};

	public final int x;
	public final int y;

	public Coordinate(
		final int i_x,
		final int i_y)
	{
		this.x = i_x;
		this.y = i_y;
	}

	public static List<Coordinate> fromString(
		final String i_source)
	{
		final List<Coordinate> coordinates = new ArrayList<Coordinate>();
		final Pattern pattern = Pattern.compile(COORDINATE_REGEX);
		final Matcher matcher = pattern.matcher(i_source);
		while(matcher.find()) {
			final int x = Integer.valueOf(matcher.group(1));
			final int y = Integer.valueOf(matcher.group(2));
			coordinates.add(new Coordinate(x,y));
		}
		return coordinates;
	}

	public Coordinate add(
		final Coordinate i_pos)
	{
		return new Coordinate(i_pos.x + this.x, i_pos.y + this.y);
	}
	
	public Coordinate add(
			final int i_x,
			final int i_y)
	{
		return new Coordinate(i_x + this.x, this.y + y);
	}
}
