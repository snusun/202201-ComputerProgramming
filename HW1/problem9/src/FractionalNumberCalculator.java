public class FractionalNumberCalculator {
    public static void printCalculationResult(String equation) {
        // DO NOT change the skeleton code.
        // You can add codes anywhere you want.
        String[] splitEq = equation.split(" ");
        FractionalNumber num1;
        FractionalNumber num2;

        if (splitEq[0].contains("/")) {
            num1 = new FractionalNumber(splitEq[0].split("/")[0], splitEq[0].split("/")[1]);
        } else {
            num1 = new FractionalNumber(splitEq[0].split("/")[0]);
        }

        if (splitEq[2].contains("/")) {
            num2 = new FractionalNumber(splitEq[2].split("/")[0], splitEq[2].split("/")[1]);
        } else {
            num2 = new FractionalNumber(splitEq[2].split("/")[0]);
        }

        String operator = splitEq[1];

        // 약분 필요
        switch (operator) {
            case "+":
                FractionalNumber add = num1.add(num2);
                if(add.getDenominator()==1){
                    System.out.println(add.getNumerator());
                } else {
                    System.out.println(add.getNumerator() + "/" + add.getDenominator());
                }
                break;
            case "-":
                FractionalNumber subtract = num1.subtract(num2);
                if(subtract.getNumerator()>0 && subtract.getDenominator()<0){
                    subtract = new FractionalNumber(subtract.getNumerator()*-1, subtract.getDenominator()*-1);
                }
                if(subtract.getDenominator()==1){
                    System.out.println(subtract.getNumerator());
                } else {
                    System.out.println(subtract.getNumerator() + "/" + subtract.getDenominator());
                }
                break;
            case "*":
                FractionalNumber multiply = num1.multiply(num2);
                if(multiply.getDenominator()==1){
                    System.out.println(multiply.getNumerator());
                } else {
                    System.out.println(multiply.getNumerator() + "/" + multiply.getDenominator());
                }
                break;
        }
    }
}

class FractionalNumber {
    private int numerator = 1;
    private int denominator = 1;

    FractionalNumber(String numerator, String denominator) {
        this.numerator = Integer.parseInt(numerator);
        this.denominator = Integer.parseInt(denominator);
    }

    FractionalNumber(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    FractionalNumber(String numerator) {
        this.numerator = Integer.parseInt(numerator);
    }

    int getNumerator() {
        return numerator;
    }

    int getDenominator() {
        return denominator;
    }

    private static int gcd(int num, int den) {
        if (den == 0) {
            return num;
        }
        return gcd(den, num % den);
    }

    private static int lcm(int den1, int den2) {
        int numGCD = gcd(den1, den2);
        return (den1 * den2) / numGCD;
    }

    public FractionalNumber add(FractionalNumber operand) {
        int deno = lcm(this.denominator, operand.denominator);
        int numer = ((deno / this.denominator) * this.numerator) + ((deno / operand.denominator) * operand.numerator);
        return reduce(new FractionalNumber(numer, deno));
    }

    public FractionalNumber subtract(FractionalNumber operand) {
        int deno = lcm(this.denominator, operand.denominator);
        int numer = ((deno / this.denominator) * this.numerator) - ((deno / operand.denominator) * operand.numerator);
        return reduce(new FractionalNumber(numer, deno));
    }

    public FractionalNumber multiply(FractionalNumber operand) {
        int numer = this.numerator * operand.numerator;
        int deno = this.denominator * operand.denominator;
        return reduce(new FractionalNumber(numer, deno));
    }

    public FractionalNumber reduce(FractionalNumber frac) {
        if(frac.getDenominator() == frac.getNumerator()){
            return new FractionalNumber(1,1);
        } else {
            int gcd = gcd(frac.getDenominator(), frac.getNumerator());
            return new FractionalNumber(frac.getNumerator()/gcd, frac.getDenominator()/gcd);
        }
    }
}
