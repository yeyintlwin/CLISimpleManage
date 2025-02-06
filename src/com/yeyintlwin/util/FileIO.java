package com.yeyintlwin.util;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileIO {

	public void writeJSON2File(String fileName, String json) throws Exception {

		File file = new File(fileName);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fileWriter = new FileWriter(file);

		fileWriter.write(json);

		fileWriter.close();
	}

	public String readJSONFromFile(String fileName) throws Exception {

		String output = "";

		File file = new File(fileName);

		if (!file.exists()) {
			return "{}";
		}
		Scanner scanner = new Scanner(file);

		while (scanner.hasNextLine()) {
			output += scanner.nextLine();
		}

		scanner.close();

		return output;
	}

}
