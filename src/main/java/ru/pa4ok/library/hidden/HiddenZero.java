package ru.pa4ok.library.hidden;

import java.util.concurrent.ThreadLocalRandom;

public class HiddenZero
{
    public static final HiddenZero INSTANCE = new HiddenZero();

    private HiddenZero() {
    }

    public int getInt() {
        return ThreadLocalRandom.current().nextInt(1);
    }

    public float getFloat() {
        return getInt();
    }

    public double getDouble() {
        return getInt();
    }
}
