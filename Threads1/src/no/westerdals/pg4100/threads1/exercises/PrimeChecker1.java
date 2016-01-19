package no.westerdals.pg4100.threads1.exercises;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PrimeChecker1 extends JFrame {
	private static final long serialVersionUID = 1L;
	private ExecutorService executor;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	private JButton checkButton, helpButton;
	private JTextField numberField;
	private JTextArea displayArea;
	private Checker1 checker;
	//123211177 is a prime
	public PrimeChecker1() {
		super("Tests if a number is a prime");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		executor = Executors.newCachedThreadPool();
		
		northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(3, 2, 10, 10));
		northPanel.add(new JLabel("Number to be checked:"));
		numberField = new JTextField();
		northPanel.add(numberField);
		add(northPanel, BorderLayout.NORTH);
		
		centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 1, 10, 10));
		displayArea = new JTextArea();
		centerPanel.add(new JScrollPane(displayArea));
		add(centerPanel, BorderLayout.CENTER);

		southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(1, 2, 10, 10));
		checkButton = new JButton("Start check");
		checkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long number = Long.parseLong(numberField.getText());
				checker = new Checker1(number);
				//new Thread(checker).start();
				executor.execute(checker);
			}
		});
		southPanel.add(checkButton);
		helpButton = new JButton("Help");
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,
						"OMG!!! This was hard work.\n"
								+ " Sorry for taking so long.");
			}
		});
		southPanel.add(helpButton);
		add(southPanel, BorderLayout.SOUTH);

		setSize(600, 250);
		setVisible(true);
	}

	public static void main(String[] args) {
		new PrimeChecker1();
		System.out.println("PrimeChecker1: main() ending");
	}
	
	private class Checker1 implements Runnable {
		private long number;
		
		public Checker1(long number) {
			this.number = number;
			//check();
		}

		// Checks the number
		public void check() {
			long i = 2;
			for (; i < number; i++)
				if (number % i == 0) 
					break;
			if (i == number) {
				displayArea.append(
						"The number " + number + " is a prime\n");
			} else {
				displayArea.append(
						"The number " + number
						+ " is not a prime\n");
			}
		}

		@Override
		public void run() {
			check();
		}
	}
}