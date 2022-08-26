package ru.pa4ok.library.hidden;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenInt
{
    private boolean minus;
    private int[] parts;

    public HiddenInt(int original)
    {
        this.set(original);
    }

    public void set(int original)
    {
        this.minus = original < 0;
        original = Math.abs(original);

        this.parts = new int[2 + ThreadLocalRandom.current().nextInt(3)];
        for(int i=0; i<parts.length; i++) {
            if(original <= 0) {
                break;
            }
            if(i != parts.length-1) {
                this.parts[i] = ThreadLocalRandom.current().nextInt(original);
                original -= this.parts[i];
            } else {
                this.parts[i] = original;
            }
        }
    }

    public int get()
    {
        int original = 0;

        for (int part : this.parts) {
            original += part;
        }
        if(this.minus) {
            original *= -1;
        }

        return original;
    }
}
