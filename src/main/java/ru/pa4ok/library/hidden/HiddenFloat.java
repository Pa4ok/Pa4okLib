package ru.pa4ok.library.hidden;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenFloat
{
    private boolean minus;
    private float[] parts;

    public HiddenFloat(float original)
    {
        this.set(original);
    }

    public void set(float original)
    {
        this.minus = original < 0;
        original = Math.abs(original);

        this.parts = new float[2 + ThreadLocalRandom.current().nextInt(3)];
        for(int i=0; i<parts.length; i++) {
            if(original <= 0) {
                break;
            }
            if(i != parts.length-1) {
                this.parts[i] = (float) ThreadLocalRandom.current().nextDouble(original);
                original -= this.parts[i];
            } else {
                this.parts[i] = original;
            }
        }
    }

    public float get()
    {
        float original = 0;

        for (float part : this.parts) {
            original += part;
        }
        if(this.minus) {
            original *= -1;
        }

        return original;
    }
}
