import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        System.out.println("Допускаются арабские числа от 1 до 10 романские от I до X, \n" +
                "различиные типы чисел в одном выражении не допустимы, для выхода введите exit");
        while(true){
            UserExp userExp = new UserExp();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите матиматическое выражение в формате a + b");
            String userInput = scanner.nextLine();
            if (userInput.matches( "exit")){
                break;
            } else if(!userExp.checkExp(userInput)){  // if wrong expression
                try {
                    throw new IOException();
                } catch (IOException e) {
                    System.out.println("Ошибка ввода, формат математической операции не удовлетворяет заданию (a + b), числа от 1 до 10 - два операнда и один оператор (+, -, /, *)");
                    break;
                }
            } else{
                try {
                    String output = calc(userInput);
                    System.out.println(output);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }

            }
        }



    }
    public static String calc (String input) throws IOException  {
        String [] myArray = input.split(" ");
        String firstNumber = myArray[0];
        String secondNumber = myArray[2];
        String operator = myArray[1];
        UserExp userExp = new UserExp();
        boolean checkFirst = userExp.checkRomanNumber(firstNumber);
        boolean checkSecond = userExp.checkRomanNumber(secondNumber);
        if(checkFirst && !checkSecond || !checkFirst && checkSecond){  //numbers have different types
            throw new IOException("используются одновременно разные системы счисления");
        }
        if (!checkFirst){     //numbers is arabian
            int intFirstNum = Integer.parseInt(firstNumber);
            int intSecondNum = Integer.parseInt(secondNumber);
            switch (operator){
                case "+":
                    return String.valueOf(intFirstNum + intSecondNum);
                case "-":
                    return String.valueOf(intFirstNum - intSecondNum);
                case "*":
                    return String.valueOf(intFirstNum * intSecondNum);
                case "/":
                    if (intSecondNum == 0){
                        throw new IOException("На ноль делить нельзя");
                    }
                    return String.valueOf(intFirstNum / intSecondNum);
            }
        }
        if (checkFirst){ // numbers is roman
            RomanNumber firstRoman = RomanNumber.valueOf(firstNumber);
            int firstNumberArabian = firstRoman.getArabicNumber();
            RomanNumber secondRoman = RomanNumber.valueOf(secondNumber);
            int secondNumberArabian = secondRoman.getArabicNumber();
            if((operator.matches("-")) && firstNumberArabian <= secondNumberArabian || (operator.matches("/")) && firstNumberArabian <= secondNumberArabian ) {
                throw new IOException("В римской системе нет отрицательных чисел и нуля");
            }
            switch (operator){
                case "+":
                    int result =(firstNumberArabian + secondNumberArabian);
                    return UserExp.convertArabianToRoman(result);
                case "-":
                    int result1 =(firstNumberArabian - secondNumberArabian);
                    return UserExp.convertArabianToRoman(result1);
                case "*":
                    int result2 = (firstNumberArabian * secondNumberArabian);
                    return UserExp.convertArabianToRoman(result2);
                case "/":
                    int result3 =(firstNumberArabian / secondNumberArabian);
                    return UserExp.convertArabianToRoman(result3);
            }


        }
        return input;
    }

static class UserExp{

        boolean checkExp(String strForCheck) {
            Pattern pattern = Pattern.compile("^((10)|[0-9]|I|II|III|IV|V|VI|VII|VIII|IX|X)\\s[+|\\-/*]\\s((10)|[0-9]|I|II|III|IV|V|VI|VII|VIII|IX|X)$");
            Matcher matcher = pattern.matcher(strForCheck);
            return matcher.find();
        }

        boolean checkRomanNumber(String number){
            Pattern pattern = Pattern.compile("^(I|II|III|IV|V|VI|VII|VIII|IX|X)$");
            Matcher matcher = pattern.matcher(number);
            return matcher.find();
        }

        static String convertArabianToRoman(int number){
            int [] intArray = {100,90,50,40,10,9,5,4,1};
            String [] romanArray = {"C","XC","L","XL","X","IX","V","IV","I"};
            StringBuilder roman = new StringBuilder();
            for(int i = 0; i < intArray.length; i++){
                while(number >= intArray[i]){
                    number = number - intArray[i];
                    roman.append(romanArray[i]);
                }
            }
            return roman.toString();
        }

        }
    }

