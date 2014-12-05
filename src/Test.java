import java.math.BigInteger;


public class Test {

	/**
	 * һ�׼����㷨��String Encrypt(String splain,String skey)���㷨�������£�
	 * 1 ��splain�ĵ�1�ֽں͵�2�ֽڶԻ���3��4�Ի����Դ����ƣ�
	 * 2 ������Ľ��,���ֽڵĸ�4λ�͵�4λ�Ի�
	 * 3 ������Ľ������skey�ĳ��Ƚ��зֿ飬��skey���и��ֽ����
	 * 4 ������Ľ������ʮ�����Ʊ��������
	 * ��splain="abcdefghijklmnopqrstuvwxyz";skey="hello";����Ľ��Ϊ"4e732a5a093ee31acaf9aed38aba689e427b2b580f32eb1bc8ff"��
	 * ��д�����ܳ���String Decrypt(String scipher,String skey)��
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
		String sto2=hexString2binaryString(s);//ʮ������ת������
		
		//��skey���зֿ�
		String s2[]=new String[1024];//�ֿ������
		int i=0;//�ֿ���
		int cut=sto2.length();//�����ж�
		while(cut>=slength){
			int start,end;
			start=slength*i;
			end=slength*(i+1);
			s2[i]=sto2.substring(start, end);
			i++;
			cut=cut-slength;
		}
		
//		//��� BigInteger
		Long res[]=new Long[1024];//��������
		for(int j=0;j<=i;j++){
			Long one,two;
			one=forStringToLong(s2[j].trim());//���ȳ���������
			two=forStringToLong(skeyto2.trim());
			res[j]=one^two;
		}
		
		String result="";//�����תstring
		for(int x=0;x<=res.length;x++){
			result=result+res[x];
		}
		
		String result2="";//�ߵ�4λ�Ի�
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
		
		String result3="";//�ֽڶԻ�
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
	//�����������
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
