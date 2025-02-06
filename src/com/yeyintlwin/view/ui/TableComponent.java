package com.yeyintlwin.view.ui;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TableComponent {

	public TableComponent() {
	}

	private static TableComponent theTable;
	private static String[][] theData;

	public static TableComponent createTable() {

		theTable = new TableComponent();

		return theTable;
	}

	public TableComponent setTableData(String[][] data) {
		theData = data;
		return theTable;
	}

	public void print() {

		/*
		 * leftJustifiedRows - If true, it will add "-" as a flag to format string to
		 * make it left justified. Otherwise right justified.
		 */
		boolean leftJustifiedRows = false;

		/*
		 * Table to print in console in 2-dimensional array. Each sub-array is a row.
		 */

		/*
		 * Calculate appropriate Length of each column by looking at width of data in
		 * each column.
		 * 
		 * Map columnLengths is <column_number, column_length>
		 */
		Map<Integer, Integer> columnLengths = new HashMap<>();
		Arrays.stream(theData).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
			if (columnLengths.get(i) == null) {
				columnLengths.put(i, 0);
			}
			if (columnLengths.get(i) < a[i].length()) {
				columnLengths.put(i, a[i].length());
			}
		}));
		/*
		 * System.out.println("columnLengths = " + columnLengths);/
		 * 
		 * /* Prepare format String
		 */
		final StringBuilder formatString = new StringBuilder("");
		String flag = leftJustifiedRows ? "-" : "";
		columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
		formatString.append("|\n");
		/* System.out.println("formatString = " + formatString.toString()); */

		/*
		 * Prepare line for top, bottom & below header row.
		 */
		String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
			String templn = "+-";
			templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
					(a1, b1) -> a1 + b1);
			templn = templn + "-";
			return ln + templn;
		}, (a, b) -> a + b);
		line = line + "+\n";
		/* System.out.println("Line = " + line); */

		/*
		 * Print table
		 */
		System.out.print(line);
		Arrays.stream(theData).limit(1).forEach(a -> System.out.printf(formatString.toString(), a));
		System.out.print(line);

		Stream.iterate(1, (i -> i < theData.length), (i -> ++i))
				.forEach(a -> System.out.printf(formatString.toString(), theData[a]));
		System.out.print(line);

	}


}
