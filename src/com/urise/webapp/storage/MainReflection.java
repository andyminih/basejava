package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;

public class MainReflection {
    public static void main(String[] args) {
        final Resume r = new Resume();

        try {
            final String s = r.getClass().getMethod("toString").invoke(r).toString();
            System.out.println(s);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        //} catch (InvocationTargetException e) {
        //    throw new RuntimeException(e);
        //} catch (IllegalAccessException e) {
        //    throw new RuntimeException(e);
        }
    }
}
