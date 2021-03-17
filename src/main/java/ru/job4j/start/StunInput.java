package ru.job4j.start;

public class StunInput implements Input {
	private String[] answers;
	private int position = 0;
	
	public StunInput(String[] answers) {
		this.answers = answers;
	}
	
	public String ask(String question) {
		return answers[position++];
	}
	public int ask(String question, int[] range) {
		return Integer.parseInt(answers[position++]);
	}
}