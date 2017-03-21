package cn.org.once.cstack.cli.utils;

public interface Prompter {
    boolean promptConfirmation(String question);
    
    String promptChoice(String question, String... choices);

    String promptPassword(String question);
}
