package main;

import workshop.Workshop;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader bfr = new BufferedReader(in);
        Workshop workshop = Workshop.getInstance(bfr);

        // run workshop
        workshop.runEngine();
    }
}
