public class ComplexNumber {
    private double real;
    private double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
    }

    public ComplexNumber subtract(ComplexNumber other) {
        return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
    }

    public ComplexNumber multiply(ComplexNumber other) {
        double real = this.real * other.real - this.imaginary * other.imaginary;
        double imaginary = this.real * other.imaginary + this.imaginary * other.real;
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber divide(ComplexNumber other) {
        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        double real = (this.real * other.real + this.imaginary * other.imaginary) / denominator;
        double imaginary = (this.imaginary * other.real - this.real * other.imaginary) / denominator;
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber power(int n) {
        double modulus = Math.pow(getModulus(), n);
        double argument = n * getArgument();
        double real = modulus * Math.cos(argument);
        double imaginary = modulus * Math.sin(argument);
        return new ComplexNumber(real, imaginary);
    }

    public ComplexNumber squareRoot() {
        double modulus = Math.sqrt(getModulus());
        double argument = getArgument() / 2;
        double real = modulus * Math.cos(argument);
        double imaginary = modulus * Math.sin(argument);
        return new ComplexNumber(real, imaginary);
    }

    public double getModulus() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    public double getArgument() {
        return Math.atan2(imaginary, real);
    }

    @Override
    public String toString() {
        return String.format("%.2f + %.2fi", real, imaginary);
    }
}

import java.util.List;
        import java.util.Scanner;

public class CalculatorView {
    private Scanner scanner;

    public CalculatorView() {
        scanner = new Scanner(System.in);
    }

    public void displayHistory(List<String> history) {
        System.out.println("History:");
        for (String operation : history) {
            System.out.println(operation);
        }
    }

    public ComplexNumber getComplexNumber() {
        System.out.print("Enter real part: ");
        double real = scanner.nextDouble();
        System.out.print("Enter imaginary part: ");
        double imaginary = scanner.nextDouble();
        return new ComplexNumber(real, imaginary);
    }

    public char getOperator() {
        System.out.print("Enter operator (+, -, *, /, ^, sqrt): ");
        return scanner.next().charAt(0);
    }

    public int getPower() {
        System.out.print("Enter power: ");
        return scanner.nextInt();
    }
}
import java.util.ArrayList;
        import java.util.List;

public class CalculatorPresenter {
    private CalculatorModel model;
    private CalculatorView view;
    private List<String> history;

    public CalculatorPresenter(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;
        history = new ArrayList<>();
    }

    public void run() {
        while (true) {
            ComplexNumber a = view.getComplexNumber();
            char operator = view.getOperator();
            ComplexNumber b = null;
            if (operator == '^' || operator == 's') {
                b = view.getComplexNumber();
            }
            ComplexNumber result = null;
            String operation = "";
            switch (operator) {
                case '+':
                    result = model.add(a, b);
                    operation = String.format("%s + %s = %s", a, b, result);
                    break;
                case '-':
                    result = model.subtract(a, b);
                    operation = String.format("%s - %s = %s", a, b, result);
                    break;
                case '*':
                    result = model.multiply(a, b);
                    operation = String.format("%s * %s = %s", a, b, result);
                    break;
                case '/':
                    result = model.divide(a, b);
                    operation = String.format("%s / %s = %s", a, b, result);
                    break;
                case '^':
                    result = model.power(a, b);
                    operation = String.format("%s ^ %s = %s", a, b, result);
                    break;
                case 's':
                    result = model.squareRoot(a);
                    operation = String.format("sqrt(%s) = %s", a, result);
                    break;
                case 'h':
                    view.displayHistory(history);
                    continue;
                case 'q':
                    return;
                default:
                    System.out.println("Invalid operator");
                    continue;
            }
            history.add(operation);
            System.out.println(result);
        }
    }
}

import java.util.List;
        import java.util.ArrayList;

public class CalculatorModel {
    private List<String> history;

    public CalculatorModel() {
        history = new ArrayList<>();
    }

    public ComplexNumber add(ComplexNumber a, ComplexNumber b) {
        ComplexNumber result = a.add(b);
        history.add(String.format("%s + %s = %s", a, b, result));
        return result;
    }

    public ComplexNumber subtract(ComplexNumber a, ComplexNumber b) {
        ComplexNumber result = a.subtract(b);
        history.add(String.format("%s - %s = %s", a, b, result));
        return result;
    }

    public ComplexNumber multiply(ComplexNumber a, ComplexNumber b) {
        ComplexNumber result = a.multiply(b);
        history.add(String.format("%s * %s = %s", a, b, result));
        return result;
    }

    public ComplexNumber divide(ComplexNumber a, ComplexNumber b) {
        ComplexNumber result = a.divide(b);
        history.add(String.format("%s / %s = %s", a, b, result));
        return result;
    }

    public ComplexNumber power(ComplexNumber a, ComplexNumber b) {
        ComplexNumber result = a.power((int) b.getReal());
        history.add(String.format("%s ^ %s = %s", a, b, result));
        return result;
    }

    public ComplexNumber squareRoot(ComplexNumber a) {
        ComplexNumber result = a.squareRoot();
        history.add(String.format("sqrt(%s) = %s", a, result));
        return result;
    }

    public List<String> getHistory() {
        return history;
    }
}
public class Main {
    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        CalculatorPresenter presenter = new CalculatorPresenter(model, view);

        presenter.run();
    }
}
