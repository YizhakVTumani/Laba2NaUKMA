import acm.graphics.*;
import acm.program.*;

import java.awt.*;
import java.util.random.*;

//Author: Hella Nikita
//File: HelloProgram.java


public class HelloProgram extends ConsoleProgram {

    RandomGenerator rgen = RandomGenerator.getDefault();

    public void run() {
        setFont(new Font("Arial", Font.PLAIN, 18));

        surname();
        squareTable();
        squareTableOddNumbers();
        do {
            int n = readInt("Enter numbers that will sum: ") ;
            while (n < 0){
                n = readInt("Enter positive numbers that will sum: ") ;
            }
            sumOfNNumbers(n);
        }while(readInt("If you want to repeat press 1: ") == 1);
        do {
            int n = readInt("Enter numbers (odd) that will sum: ") ;
            while (n < 0){
                n = readInt("Enter positive numbers (odd) that will sum: ") ;
            }
            sumOfNOddNumbers(n);
        }while(readInt("If you want to repeat press 1: ") == 1);
        do {
            int n = readInt("Enter numbers (fractions) that will sum: ") ;
            while (n < 0){
                n = readInt("Enter positive numbers (fractions) that will sum: ") ;
            }
            sumOfFractions(n);
        }while(readInt("If you want to repeat press 1: ") == 1);
        squareTableOfTwo();
        do {
            int n = readInt("Enter positive numbers that will be factorialed: ") ;
            while (n < 0){
                n = readInt("Enter positive numbers that will be factorialed: ") ;
            }
            factorial(n);
        }while(readInt("If you want to repeat press 1: ") == 1);
        functionCalculator();
        do {
            randomNumbersAvg();
        }while(readInt("If you want to repeat press 1: ") == 1);
        do {
            int n = readInt("Enter positive cost of Yablukas: ");
            while (n <= 0){
                n = readInt("Enter positive cost of Yablukas: ");
            }
            priceOfTheYabluka(n);
        }while(readInt("If you want to repeat press 1: ") == 1);
        do {
            minusAndPlusExercise();
        }while(readInt("If you want to repeat press 1: ") == 1);
        do {
            avgAndSumOfEnteredNumbers();
        }while(readInt("If you want to repeat press 1: ") == 1);

        println("This program finds the smallest number");
        println("To stop execution press 0");
        do {
            int min = 0;
            int x = 1;
            while (x != 0) {
                x = readInt("Write value of x: ");
                if (x != 0) {
                    min = min(x, min);
                }
            }
            println("Min number is: " + min);
        }while(readInt("Do you want to continue? press 1: ") == 1);
        do {
            primeNumber();
        }while(readInt("Do you want to continue? press 1: ") == 1);
        do {
            guessTheNumberGame();
        }while(readInt("Do you want to continue? press 1: ") == 1);
    }
    private void surname(){
        for (int i = 0; i < 10; i++){
            println("Hella Nikita");
        }
    }
    private void squareTable(){
        for (int i = 1; i <= 10; i++){
            println(i + " --> " + Math.pow(i, 2));
        }
    }
    private void squareTableOddNumbers(){
        for (int i = 1; i < 10; i +=2){
                println(i+ " --> " + Math.pow(i, 2));
        }
    }
    private void sumOfNNumbers(int n){
        int sum = 0;
        for (int i = 1; i <= n; i++){
            sum += i;
        }
        println(sum);
    }
    private void sumOfNOddNumbers(int n){
        int sum = 0;
        for (int i = 1; i <= n; i += 2){
            sum += i;
        }
        println(sum);
    }
    private void sumOfFractions(int n){
        double sum = 0;
        for (int i = 1; i <= n; i++){
            sum += (double) 1 /i;
        }
        println(sum);
    }
    private void squareTableOfTwo(){
        for (int i = 0; i <= 10; i++){
            println("2^" + i + " --> " + Math.pow(2, i));
        }
    }
    private void factorial(int n){
        for(int i = n - 1; i > 0; i--){
            n *= i;
        }
        if(n == 0){
            println(1);
        }
        else{
            println("factorial is: " + n);
        }
    }
    private void functionCalculator(){
        for (double i = -2; i <= 2; i += 0.5){
            println("The answer for x = " + i + " is: " + ((-2.4 * Math.pow(i, 2)) + (5 * i) - 3));
        }
    }
    private void randomNumbersAvg(){
        double sum = 0;
        int number = 0;
        for (int i = 0; i < 10; i ++){
            number = rgen.nextInt(1, 10);
            println("Random Numbers: " + number);
            sum += number;
        }
        println("Result is: " + sum/10);
    }
    private void priceOfTheYabluka(int n){
        for (int i = 1; i <= 10; i++){
            println("The price of " + (i * 100) + " g is: " + (n * i));
        }
    }
    private void minusAndPlusExercise(){
        int mark = 0;
        int number;
        int number2;
        int ex;
        String operator;
        for (int i = 1; i <= 10; i++){
            ex = rgen.nextInt(0, 2);
            if (ex == 1){
                number = rgen.nextInt(1, 100);
                number2 = rgen.nextInt(1,100 - number);
                operator = "+";
            }
            else {
                number = rgen.nextInt(1, 100);
                number2 = rgen.nextInt(number, 100);
                operator = "-";
            }
            println("Number 1 is: " + number2);
            println("Operator is: " + operator);
            println("Number 2 is: " + number);
            if (ex == 1 && readInt("Enter the answer: ") == number + number2){
                mark++;
                println("You are right!");
            }
            else if(ex != 1 && readInt("Enter the answer: ") == number2 - number){
                mark++;
                println("You are right!");
            }
        }
        println("Your mark is: " +  mark);
        if(mark >= 9){
            println("Perfect!");
        }
        else if (mark > 7) {
            println("Not bad!");
        }
        else if( mark > 5){
            println("Could be better!");
        }
        else{
            println("Bad!");
        }
    }
    private void avgAndSumOfEnteredNumbers(){
        double sum = 0;
        int quantity = 0;
        int n;
        do {
            quantity++;
            n = readInt("Enter numbers. To finish press 0: ");
            sum += n;
        }while(n != 0);
        if (sum == 0){
            println("Division by zero!");
        }
        else{
            println("Avg is: " + sum/(quantity - 1));
            println("Sum is: " + sum);
        }
    }
    private int min(int x, int min){ // finds min number of entered
        min = Math.min(min, x);
        if(min == 0){
            return x;
        }
        else {
            return min;
        }
    }
    private void primeNumber(){
        boolean flag = false;
        int n = readInt("Enter your number: ");
        if(n == 0){
            println("Your number is 0!");
        }
        else {
            for (int i = 2; i < n; i++) {
                if (n % i == 0) {
                    flag = true;
                }
            }
            if (flag) {
                println("Your number is NOT prime");
            } else {
                println("Your number is prime");
            }
        }
    }
    private void guessTheNumberGame(){
        int result = rgen.nextInt(0, 100);
        int steps = 0;
        while (true) {
            if(steps < 7) {
                steps++;
                int guess = readInt("Guess: ");
                if (result == guess) {
                    println("Congratulations! You win!");
                    break;
                } else if (result > guess) {
                    println("Take higher!");
                } else {
                    println("Take lower!");
                }
            }
            else{
                println("You lost");
                break;
            }
        }
        if (steps <= 7) {
            println("It took you " + steps + " steps.");
        }
    }
}