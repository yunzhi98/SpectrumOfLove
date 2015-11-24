package com.logIn.unit;

/**
 * Created by wb-xuzhenbin on 2015/11/24.
 */
public class UnicodeToString {
    /**
     * ��unicodeת����String
     */
    public static String asciiToNative(String ascii) {
        int n = ascii.length() / 6;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0, j = 2; i < n; i++, j += 6) {
            String code = ascii.substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            sb.append(ch);
        }
        return sb.toString();
    }
    /**
     * ������ת��Unicode��
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//���ַ�Χ \u4e00-\u9fa5 (����)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }
    public static void main (String [] args){
        String value = "��һҳ";
        String back = chinaToUnicode(value);
        System.out.print(back);
    }
}
