package ru.pa4ok.library.hidden;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenLong
{
    private boolean minus;
    private long[] parts;

    public HiddenLong(long original)
    {
        this.set(original);
    }

    public void set(long original)
    {
        this.minus = original < 0;
        original = Math.abs(original);

        this.parts = new long[2 + ThreadLocalRandom.current().nextInt(3)];
        for(int i=0; i<parts.length; i++) {
            if(original <= 0) {
                break;
            }
            if(i != parts.length-1) {
                this.parts[i] = ThreadLocalRandom.current().nextLong(original);
                original -= this.parts[i];
            } else {
                this.parts[i] = original;
            }
        }
    }

    public long get()
    {
        long original = 0;

        for (long part : this.parts) {
            original += part;
        }
        if(this.minus) {
            original *= -1;
        }

        return original;
    }
}
