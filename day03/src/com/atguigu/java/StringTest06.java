package com.atguigu.java;

import org.junit.Test;

/**
 * 练习5：常见算法题目
 *
 * 1.模拟一个trim方法，去除字符串两端的空格。
 *
 * 2.将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反
 * 转为”abfedcg”
 *
 * 3.获取一个字符串在另一个字符串中出现的次数。
 * 比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
 *
 * 4.获取两个字符串中最大相同子串。比如：
 * str1 = "abcwerthelloyuiodef“;str2 = "cvhellobnm"
 * 提示：将短的那个串进行长度依次递减的子串与较长的串比较。
 *
 * 5.对字符串中字符进行自然顺序排序。
 * 提示：
 * 1）字符串变成字符数组。             2）对数组排序，选择，冒泡，Arrays.sort();
 * 3）将排序后的数组变成字符串。
 *  2,3,4见下面，其他两道可见答案
 *
 *
 * JVM中字符串常量池存放位置说明：
 * jdk1.6：字符串常量池存储在方法区(永久区)
 * jdk1.7：字符串常量池存储在堆空间
 * jdk1.8：字符串常量池存储在方法区(元空间)
 * @author shkstart
 * @create 2021-04-12 12:29
 */
public class StringTest06 {


    /*
    t2:将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反转为”abfedcg”

    方式一：转换为char[]
    */
    public String reverse(String str , int startIndex , int endIndex){
        if(str != null){
            char[] arr = str.toCharArray();
            for(int x = startIndex , y = endIndex;x < y;x++,y--){
                char temp = arr[x];
                arr[x] = arr[y];
                arr[y] = temp;
            }
            return new String(arr);
        }
        return null;
    }

    //方式二：使用String的拼接
    public String reverse1(String str , int startIndex , int endIndex){
        if(str != null){
            //第1部分
            String reverseStr = str.substring(0 , startIndex);
            //第2部分
            for(int i = endIndex;i >= startIndex;i--){
                reverseStr += str.charAt(i);
            }
            //第3部分
            reverseStr += str.substring(endIndex + 1);
        }
        return null;
    }

    //方式三：使用StringBuffer、StringBuilder替换String(在方式二的基础上进行的改进，String效率低了)
    public String reverse2(String str , int startIndex , int endIndex){
        if(str != null){
            StringBuilder builder = new StringBuilder(str.length());//不需要创建默认的16个长度

            //第1部分
            builder.append(str.substring(0 , startIndex));
            //第2部分
            for(int i = endIndex;i >= startIndex;i--){
                builder.append(str.charAt(i));
            }
            //第3部分
            builder.append(str.substring(endIndex + 1));

            return builder.toString();//赋给String的构造器也行
        }
        return null;
    }

    /*
    t3:获取一个字符串在另一个字符串中出现的次数。
     比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
     */

    /**
     * 获取subStr在mainStr中出现的次数
     * @param mainStr
     * @param subStr
     * @return
     */
    public int getCount(String mainStr , String subStr){
        int mainLength = mainStr.length();
        int subLength = subStr.length();
        int count = 0;
        int index = 0;

        if(mainLength >= subLength){
            //方式一：
//            while((index = mainStr.indexOf(subStr)) != -1){
//                count++;
//                mainStr = mainStr.substring(index + subStr.length());
//            }

            //方式二：对方式一的改进(无需不断的创建新char[])
            while((index = mainStr.indexOf(subStr , index)) != -1){
                count++;
                index += subStr.length();
            }

            return count;
        }else{
            return 0;
        }
    }

    /*
    t4；获取两个字符串中最大相同子串。比如：
         str1 = "abcwerthelloyuiodef“;str2 = "cvhellobnm"
         提示：将短的那个串进行长度依次递减的子串与较长的串比较。
     */
    //前提(降低难度)：两个字符串中只有一个最大相同子串
    public String getMaxSameString(String str1 , String str2){
        if(str1 != null && str2 != null){
            String maxStr = (str1.length() >= str2.length())? str1:str2;
            String minStr = (str1.length() < str2.length())? str1:str2;
            int length = minStr.length();//比如"abc"长度为3，那么就有abc与长的比，然后ab、bc比，然后就是a、c来比
            //故minStr长度为多少，就要递减多少次。

            for(int i = 0;i < length;i++){//第一个for用来看minStr最多能递减多少次

                for(int x = 0 , y = length - i;y <= length;x++,y++){//第二个for用来判断递减后有几种情况
                    String subStr = minStr.substring(x,y);
                    if(maxStr.contains(subStr)){
                        return subStr;
                    }
                }
            }
        }
        return null;
    }
    @Test
    public void test(){
        String str1 = "abc";
        String str2 = "adef";
        System.out.println(getMaxSameString(str1, str2));
    }

    // 如果存在多个长度相同的最大相同子串
    // 此时先返回String[]，后面可以用集合中的ArrayList替换，较方便
    public String[] getMaxSameSubString1(String str1, String str2) {
        if (str1 != null && str2 != null) {
            StringBuffer sBuffer = new StringBuffer();
            String maxString = (str1.length() > str2.length()) ? str1 : str2;
            String minString = (str1.length() > str2.length()) ? str2 : str1;

            int len = minString.length();
            for (int i = 0; i < len; i++) {
                for (int x = 0, y = len - i; y <= len; x++, y++) {
                    String subString = minString.substring(x, y);
                    if (maxString.contains(subString)) {
                        sBuffer.append(subString + ",");
                    }
                }
                System.out.println(sBuffer);
                if (sBuffer.length() != 0) {
                    break;//找到了就不需要再递减了(abc符合了，就不需要ab这些了)
                }
            }
            //运用了正则，去掉了末尾的“，”，然后以中间的“，”为界限划分成数组
            String[] split = sBuffer.toString().replaceAll(",$", "").split("\\,");
            return split;
        }

        return null;
    }
}
