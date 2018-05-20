package com.revature.bank.launcher;

import com.revature.bank.promts.LoginPrompt;
import com.revature.bank.promts.Prompt;

public class BankLauncher {
	public static void main(String[] args) {
		Prompt currentPrompt = new LoginPrompt();

		while (true) {
			currentPrompt = currentPrompt.run();
		}
	}

}
