package edu.kbtu.university;

import edu.kbtu.university.users.UsersSmokeTest;

/**
 * Lightweight entry point for the current main branch.
 *
 * The full interactive console depends on modules that are still skeletons in
 * main. Until those APIs land, running the app executes the users smoke test
 * and exits instead of waiting forever for console input.
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        if (args.length > 0 && "console".equalsIgnoreCase(args[0])) {
            System.out.println("Interactive console is not wired in the current main branch yet.");
            System.out.println("Run without arguments, or with 'demo', to execute the smoke test.");
            return;
        }

        UsersSmokeTest.main(args);
    }
}
