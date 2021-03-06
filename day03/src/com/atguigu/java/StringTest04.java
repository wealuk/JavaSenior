package com.atguigu.java;

import org.junit.Test;

/**
 * int length()：返回字符串的长度： return value.length
 * char charAt(int index)： 返回某索引处的字符return value[index]
 * boolean isEmpty()：判断是否是空字符串：return value.length == 0
 * String toLowerCase()：使用默认语言环境，将 String 中的所有字符转换为小写(依然是不可变性，返回值是新的，但原来的没有改变)
 * String toUpperCase()：使用默认语言环境，将 String 中的所有字符转换为大写(见line32，不是在原来的value上改的，依然是创建了新的内存)
 * String trim()：返回字符串的副本，忽略前导空白和尾部空白
 * boolean equals(Object obj)：比较字符串的内容是否相同
 * boolean equalsIgnoreCase(String anotherString)：与equals方法类似，忽略大小写
 * String concat(String str)：将指定字符串连接到此字符串的结尾。 等价于用“+”
 * int compareTo(String anotherString)：比较两个字符串的大小
 * String	substring(int	beginIndex)： 返回一个新的字符串， 它是此字符串的从beginIndex开始截取到最后的一个子字符串。
 * String substring(int beginIndex, int endIndex) ：返回一个新字符串，它是此字 符串从beginIndex开始截取到endIndex(不包含)的一个子字符串。
 *
 * String replace(char oldChar, char newChar)：返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。
 * String replace(CharSequence target, CharSequence replacement)：使 用指定的字面值替换序列替换此字符串所有匹配字面值目标序列的子字符串。
 *
 * 以下的方法涉及到正则regex，暂时未学，了解就行
 * String replaceAll(String	regex,	String	replacement) ： 使 用 给 定 的replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
 * String replaceFirst(String	regex,	String	replacement) ： 使用给定的replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
 * 匹配：
 * boolean matches(String regex)：告知此字符串是否匹配给定的正则表达式。
 * 切片：
 * String[] split(String regex)：根据给定正则表达式的匹配拆分此字符串。
 * String[] split(String regex, int limit)：根据匹配给定的正则表达式来拆分此 字符串，最多不超过limit个，如果超过了，剩下的全部都放到最后一个元素中
 *
 *
 * boolean endsWith(String suffix)：测试此字符串是否以指定的后缀结束
 * boolean startsWith(String prefix)：测试此字符串是否以指定的前缀开始
 * boolean startsWith(String prefix, int toffset)：测试此字符串从指定索引开始的子字符串是否以指定前缀开始
 *
 *
 * @author shkstart
 * @create 2021-04-11 14:25
 */
public class StringTest04 {
    @Test
    public void test(){
        String s1 = "HelloWorld";
        System.out.println(s1.length());
        System.out.println(s1.charAt(0));
//        System.out.println(s1.charAt(10));
//        s1 = "";
        System.out.println(s1.isEmpty());

        String s2 = s1.toLowerCase();
        System.out.println(s1);//s1不可变的，仍然为原来的字符串
        System.out.println(s2);//改成小写的字符串

        String s3 = "  he  llo   world  ";
        String s4 = s3.trim();//去掉首尾的空格
        System.out.println(s3);
        System.out.println(s4);
    }

    @Test
    public void test1(){
        String s1 = "HelloWorld";
        String s2 = "helloworld";
        System.out.println(s1.equals(s2));//false
        System.out.println(s1.equalsIgnoreCase(s2));//true

        String s3 = "abc";
        String s4 = s3.concat("def");
        System.out.println(s4);

        String s5 = "abc";
        String s6 = new String("abe");
        System.out.println(s5.compareTo(s6));//-2.涉及到字符串排序

        String s7 = "北京尚硅谷教育";
        String s8 = s7.substring(2);
        System.out.println(s7);//s7依然没变
        System.out.println(s8);//第三个到后面的字符串

        String s9 = s7.substring(2,5);
        System.out.println(s9);//尚硅谷。左闭右开，就是[2,5)
    }
    @Test
    public void test2(){
        String str1 = "北京尚硅谷教育北京。";
        String str2 = str1.replace('北' , '东');

        System.out.println(str1);//依然没变
        System.out.println(str2);//东京尚硅谷教育东京。所有的'北'都变了

        String str3 = str1.replace("北京" , "上海");
        System.out.println(str3);

        System.out.println("*************");//以下方法涉及到正则，以后学了再来看
        String str = "12hello34world5java7891mysql456";
        //把字符串中的数字替换成,，如果结果中开头和结尾有，的话去掉
        String string = str.replaceAll("\\d+", ",").replaceAll("^,|,$", "");
        System.out.println(string);

        String stri = "12345";
        //判断str字符串中是否全部有数字组成，即有1-n个数字组成
        boolean matches = str.matches("\\d+");
        System.out.println(matches);

        String tel = "0571-4534289";
        //判断这是否是一个杭州的固定电话
        boolean result = tel.matches("0571-\\d{7,8}");
        System.out.println(result);

        str2 = "hello|world|java";  String[] strs = str.split("\\|");
        for (int i = 0; i < strs.length; i++) {
            System.out.println(strs[i]);
        }
        System.out.println();
        str3 = "hello.world.java";  String[] strs2 = str2.split("\\.");
        for (int i = 0; i < strs2.length; i++) {
            System.out.println(strs2[i]);
        }

    }
}
