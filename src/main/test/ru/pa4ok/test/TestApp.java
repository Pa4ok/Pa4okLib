package ru.pa4ok.test;

import ru.pa4ok.library.reflect.EnumHelper;

import java.util.Arrays;

public class TestApp
{
    public static void main(String[] args)
    {
        System.out.println(Arrays.toString(TestEnum.values()));
        EnumHelper.addEnumMapping(TestEnum.class);
        EnumHelper.addEnum(TestEnum.class, "THREE");
        System.out.println(Arrays.toString(TestEnum.values()));
    }
}

