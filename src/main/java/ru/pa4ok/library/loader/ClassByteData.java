package ru.pa4ok.library.loader;

import java.util.Arrays;
import java.util.Objects;

public class ClassByteData
{
    private String name;
    private byte[] bytes;

    public ClassByteData(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassByteData that = (ClassByteData) o;
        return Objects.equals(name, that.name) && Arrays.equals(bytes, that.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public String toString() {
        return "ClassByteData{" +
                "name='" + name + '\'' +
                ", bytes=" + bytes.length + 'b' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
