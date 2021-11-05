package com.atguigu.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 标准流练习：创建一个类似于Scanner的类，可以从键盘读取各种类型数据并返回
 * notes：其实就是在上节内容基础上将从键盘读取的字符串转换成其他类型的数据
 * 1.首先将System.in字节流转换为字符流
 * 2.通过转换后的字符流调用readLine()就可以获取从键盘输入的字符串。---完成了获取字符串的方法。
 * 3.其他数据都可以通过获取的字符串调用方法转换就行了。
 *
 * @author shkstart
 * @create 2021-05-09 13:34
 */
public class IOTest09 {
    // Read a string from the keyboard
    public static String readString() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // Declare and initialize the string
        String string = "";

        // Get the string from the keyboard
        try {
            string = br.readLine();

        } catch (IOException ex) {
            System.out.println(ex);
        }

        // Return the string obtained from the keyboard
        return string;
    }

    // Read an int value from the keyboard
    public static int readInt() {
        return Integer.parseInt(readString());
    }

    // Read a double value from the keyboard
    public static double readDouble() {
        return Double.parseDouble(readString());
    }

    // Read a byte value from the keyboard
    public static double readByte() {
        return Byte.parseByte(readString());
    }

    // Read a short value from the keyboard
    public static double readShort() {
        return Short.parseShort(readString());
    }

    // Read a long value from the keyboard
    public static double readLong() {
        return Long.parseLong(readString());
    }

    // Read a float value from the keyboard
    public static double readFloat() {
        return Float.parseFloat(readString());
    }
}
