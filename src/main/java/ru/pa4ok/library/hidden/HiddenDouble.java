package ru.pa4ok.library.hidden;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenDouble
{
    public static final double MAX_DIF = 0.001D;

    private boolean minus;
    private double[] parts;

    public HiddenDouble(double original)
    {
        this.set(original);
    }

    public void set(double original)
    {
        this.minus = original < 0;
        original = Math.abs(original);

        this.parts = new double[2 + ThreadLocalRandom.current().nextInt(3)];
        for(int i=0; i<parts.length; i++) {
            if(original <= 0) {
                break;
            }
            if(i != parts.length-1) {
                this.parts[i] = ThreadLocalRandom.current().nextDouble(original);
                original -= this.parts[i];
            } else {
                this.parts[i] = original;
            }
        }
    }

    public double get()
    {
        double original = 0;

        for (double part : this.parts) {
            original += part;
        }
        if(this.minus) {
            original *= -1;
        }

        return original;
    }

    public boolean checkDecimal(double actual)
    {
        double hidden = this.get();
        if(actual != hidden) {
            return !(Math.abs(actual - hidden) > MAX_DIF);
        }
        return true;
    }
}
