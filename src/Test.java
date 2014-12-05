import java.math.BigInteger;


public class Test {

	/**
	 * 一套加密算法，String Encrypt(String splain,String skey)，算法过程如下：
	 * 1 对splain的第1字节和第2字节对换，3和4对换，以此类推；
	 * 2 对上面的结果,各字节的高4位和低4位对换
	 * 3 对上面的结果，按skey的长度进行分块，与skey进行各字节异或；
	 * 4 对上面的结果进行十六进制编码输出。
	 * 如splain="abcdefghijklmnopqrstuvwxyz";skey="hello";输出的结果为"4e732a5a093ee31acaf9aed38aba689e427b2b580f32eb1bc8ff"。
	 * 请写出解密程序String Decrypt(String scipher,String skey)。
	 * 
	 */
	public static void main(String[] args) {
		
		String result=Decrypt("4e732a5a093ee31acaf9aed38aba689e427b2b580f32eb1bc8ff", "hello");
		System.out.println("the final result is: "+result);
		
	}
	public static String Decrypt(String scipher,String skey1){

		String skey=skey1;
		String skeyto2=StrToBinstr(skey);
		int slength=skeyto2.length();
		

		String s=scipher;
		String sto2=hexString2binaryString(s);//十六进制转二进制
		
		//按skey进行分块
		String s2[]=new String[1024];//分块结果存放
		int i=0;//分块数
		int cut=sto2.length();//用于判断
		while(cut>=slength){
			int start,end;
			start=slength*i;
			end=slength*(i+1);
			s2[i]=sto2.substring(start, end);
			i++;
			cut=cut-slength;
		}
		
//		//异或 BigInteger
		Long res[]=new Long[1024];//异或结果存放
		for(int j=0;j<=i;j++){
			Long one,two;
			one=forStringToLong(s2[j].trim());//长度超出。。。
			two=forStringToLong(skeyto2.trim());
			res[j]=one^two;
		}
		
		String result="";//异或结果转string
		for(int x=0;x<=res.length;x++){
			result=result+res[x];
		}
		
		String result2="";//高低4位对换
		for(int y=0;y<=result.length();){
			String f4,u4;
			if(y+8<=result.length()){
				f4=result.substring(y, y+4);
				u4=result.substring(y+4,y+8);
				result2=result2+u4+f4;
				y=y+4;
			}else{
				break;
			}
		}
		
		String result3="";//字节对换
		for(int h=0;h<=s.length();){
			String f,u;
			if(h+16<=s.length()){
				f=result2.substring(h, h+8);
				u=result2.substring(h+8,h+16);
				result3=result3+f+u;
				h=h+8;
			}else{
				break;
			}
		}
		
		String result4=binaryString2hexString(result3);//2 to 16
		String result5=toStringHex(result4);//16 to string
		return result5;
	}
	//解决长度问题
	public static Long forStringToLong(String str){
		Long one;
		one=Long.parseLong(str.trim());
		return one;
	}
	
	
	//string to 2
	private static String StrToBinstr(String str) {
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i]);
        }
        return result;
	}
	//byte to 2
	public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);
    }
	//16 to 2
	public static String hexString2binaryString(String hexString)  
    {  
        if (hexString == null || hexString.length() % 2 != 0)  
            return null;  
        String bString = "", tmp;  
        for (int i = 0; i < hexString.length(); i++)  
        {  
            tmp = "0000"  
                    + Integer.toBinaryString(Integer.parseInt(hexString  
                            .substring(i, i + 1), 16));  
            bString += tmp.substring(tmp.length() - 4);  
        }  
        return bString;  
    }
	//2 to 16
	public static String binaryString2hexString(String bString)  
    {  
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)  
            return null;  
        StringBuffer tmp = new StringBuffer();  
        int iTmp = 0;  
        for (int i = 0; i < bString.length(); i += 4)  
        {  
            iTmp = 0;  
            for (int j = 0; j < 4; j++)  
            {  
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);  
            }  
            tmp.append(Integer.toHexString(iTmp));  
        }  
        return tmp.toString();  
    }
	//16 to String
	public static String toStringHex(String s)
	{
	   if("0x".equals(s.substring(0, 2)))
	   {
	    s =s.substring(2);
	   }
	   byte[] baKeyword = new byte[s.length()/2];
	   for(int i = 0; i < baKeyword.length; i++)
	   {
	      try
	      {
	       baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i*2, i*2+2),16));
	      }
	      catch(Exception e)
	      {
	       e.printStackTrace();
	      }
	   }
	   
	   try
	   {
	    s = new String(baKeyword, "utf-8");
	   }
	   catch (Exception e1)
	   {
	    e1.printStackTrace();
	   }
	   return s;
	}


}
