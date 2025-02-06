package com.yeyintlwin.util;

import java.util.Scanner;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static String coloredStringInput(Scanner scanner) {
		String input;
		System.out.print(ConsoleColors.GREEN);
		input = scanner.nextLine();
		System.out.print(ConsoleColors.RESET);
		return input;
	}

	public static Integer coloredIntInput(Scanner scanner) {
		Integer input;
		System.out.print(ConsoleColors.GREEN);
		input = scanner.nextInt();
		System.out.print(ConsoleColors.RESET);
		return input;
	}

	public static void printError(String message) {
		System.out.println(ConsoleColors.RED + message + ConsoleColors.RESET);
	}

	public static void printSuccess(String message) {
		System.out.println(ConsoleColors.BLUE + message + ConsoleColors.RESET);
	}

	public static void printWarning(String message) {
		System.out.println(ConsoleColors.YELLOW + message + ConsoleColors.RESET);
	}

}
