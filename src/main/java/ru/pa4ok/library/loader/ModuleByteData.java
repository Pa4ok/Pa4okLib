package ru.pa4ok.library.loader;

import ru.pa4ok.library.util.GsonUtil;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ModuleByteData
{
    private Set<ClassByteData> classes;

    public ModuleByteData(Collection<ClassByteData> classes)
    {
        this.classes = new HashSet<>(classes);
    }

    public static byte[] toBytes(ModuleByteData moduleByteData)
    {
        return GsonUtil.gson.toJson(moduleByteData).getBytes(StandardCharsets.UTF_8);
    }

    public static ModuleByteData fromBytes(byte[] bytes)
    {
        return GsonUtil.gson.fromJson(new String(bytes, StandardCharsets.UTF_8), ModuleByteData.class);
    }

    public byte[] toBytes()
    {
        return GsonUtil.gson.toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleByteData that = (ModuleByteData) o;
        return Objects.equals(classes, that.classes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classes);
    }

    @Override
    public String toString() {
        return "ModuleByteData{" +
                "classes=" + classes +
                '}';
    }

    public Set<ClassByteData> getClasses() {
        return classes;
    }

    public void setClasses(Set<ClassByteData> classes) {
        this.classes = classes;
    }
}
