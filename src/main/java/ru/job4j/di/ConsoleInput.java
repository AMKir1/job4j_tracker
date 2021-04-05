package ru.job4j.di;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.job4j.start.Input;
import ru.job4j.start.MenuOutException;

import java.util.Scanner;
@Component
@Scope("prototype")
public class ConsoleInput implements Input {
	private final Scanner scanner = new Scanner(System.in);
		
	public String ask(String question) {
		System.out.print(question);
		return scanner.nextLine();
	}
	
	public int ask(String question, int[] range) {
		int key = Integer.valueOf(this.ask(question));
		boolean exist = false;
		for (int value : range) {
			if (value == key) {
				exist = true;
				break;
			}
		}
		if (exist) {
			return key;
		} else {
			throw new MenuOutException("Out of menu range.");
		}
	}
}