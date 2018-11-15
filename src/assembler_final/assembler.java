package assembler_final;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
public class assembler {
	public static  String ascii_maker(String value,int string_length) throws UnsupportedEncodingException{
		/*int number = 0 ; 
		String ascii=""; 
		char character;
			for (int i=0; i<string_length-1;i++) {
				character = value.charAt(i);
				number = (int) character ;
				ascii += String.format("%x", number);
			}
			System.out.println(ascii);*/
		String ascii=""; 
	    byte[] bytes = value.getBytes("US-ASCII");
	    if(string_length==3)
	    	ascii = Integer.toString(bytes[0])+Integer.toString(bytes[1])+Integer.toString(bytes[2]);
	    else if(string_length==2)
		     ascii = Integer.toString(bytes[0])+Integer.toString(bytes[1]);
	    else if(string_length==1)
		     ascii = Integer.toString(bytes[0]);
	    
		    
		return ascii;
	}
	/*public static String sappender4(String value){
		String zero = "0";
		int iteration = 4-value.length();
		if(iteration>=1) {
		for(int i = 0 ; i<iteration-1;i++)
			zero=zero+"0";
		zero = zero+value;
		}
		else {
			zero = value;
		}
		return zero;
	}*/
	public static String sappender4(String value,int iteration){
		String zero = "";
		if (iteration == 3) {
			zero="0";
			zero =zero+value;
		}
		else if(iteration ==2) {
			zero="00";
			zero = zero+value;
		}
		else if(iteration ==1) {
			zero="000";
			zero = zero+value;
		}
		else {
			zero = value;
		}
		return zero;
	}
	   
	public static String sappender6(String value){
		String zero = "0";
		int iteration = 6-value.length();
		if(iteration>=1) {
		for(int i = 0 ; i<iteration-1;i++)
			zero=zero+"0";
		zero = zero+value;
		}
		else {
			zero = value;
		}
		return zero;
	}        
	public static String linetype(int count , String line) {
		String type = "";
		int flag = 0;
    	StringTokenizer token = new StringTokenizer(line);
    	String[] arr_token= line.split(" ");
            if (token.countTokens()>2) {
    		if(arr_token[1].equals("BYTE") ) {
    		 type = "byte";
    		 
    		}
    		else if(arr_token[1].equals("WORD")) {
    			 type = "word";
    			}
    		else if (arr_token[1].equals("RESW") ) {
    			 type = "RESW";
    			
    		}
    		else if (arr_token[1].equals("RESB")) {
    			 type = "RESB";
    		}
                else if (arr_token[1].equals("START")&&count==3 ) {
    			 type = "start"; 
    			
    		}
    		else {
    			 type = "label";
    		}
	}
       else if (token.countTokens()==2){
                 if (arr_token[1].equals("START")&&count!=3 ) {
    			 type = "notStart";
    			
    		}
                 else if (arr_token[0].equals("END")) {
    			 type = "end";
                 }
    		
                else {
                    type = "instruction";
                }
            
        }
       else if (token.countTokens()<2) {
    		type = "invalid";
    	}
    	return type; 
   }
	
	public static void pass1(String myfile,Map<String,String>st) throws IOException {
		File file = new File(myfile);
		String line = "";
		String line_type = "";
		int pccounter = 0X0,intialcounter = 0X0;
		PrintWriter symboltablewriter = new PrintWriter("symboltable.txt", "UTF-8");
                PrintWriter pass1counter = new PrintWriter("pass1counter.txt", "UTF-8");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
		    while ((line = reader.readLine()) != null) {
		    	StringTokenizer token = new StringTokenizer(line);
		    	line_type = linetype(token.countTokens(),line);
		    	String line_arr[] = line.split(" ");
		    	String x = "x";
	    		String c ="c";
                String checker="";
	    		int decimal = 0;
	    		
	    		if(token.countTokens()>2)
                            checker = Character.toString(line_arr[2].charAt(0));
                        if (line_type.equals("start")){
                             pccounter =Integer.parseInt(line_arr[2],16); 
                             intialcounter =  pccounter   ;                           
                        }
                         
		    	if (line_type.equals("label")){
                                pass1counter.println(String.format("%x", pccounter));
		    		symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                st.put(line_arr[0],String.format("%x", pccounter));
		    		pccounter +=0X3;

		    	}
		    	else if (line_type.equals("word")){
                            //pass1counter.println(String.format("%x", pccounter));
		    		/*if(checker.equals("x")) {
		    			
		    			//decimal = Integer.parseInt(line_arr[2],16);
		    			StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(line_arr[2].length()-3);
		    			//line_arr[2]=sappender(remover.toString());
		    			symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                        st.put(line_arr[0],String.format("%x", pccounter));
                                        pccounter+=0X3;
		    		}

		    		else if(checker.equals("c")) {
		    			StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(line_arr[2].length()-3);
		    			line_arr[2]=remover.toString();
		    			//line_arr[2]=ascii_maker(line_arr[2],line_arr[2].length());
		    			symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                        st.put(line_arr[0],String.format("%x", pccounter));
                                        pccounter+=0X3; 
		    		}*/
                            symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter)); 
		    		st.put(line_arr[0],String.format("%x", pccounter));
		    		int intedline = line_arr[2].length();
		    		while(intedline>0&&intedline!=0) {
		    		pccounter +=3;
		    		intedline-=6;
		    		}
                     }
		    	else if (line_type.equals("byte")){
		    	
                            pass1counter.println(String.format("%x", pccounter));
		    		if(checker.equals("X")) {
		    			StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(line_arr[2].length()-3);
		    			line_arr[2]=(remover.toString());
                                       
		    			symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                        st.put(line_arr[0],String.format("%x", pccounter));
                                    	int intedline = line_arr[2].length();
                    		    		while(intedline>0&&intedline!=0) {
                    		    			
                    		    		pccounter +=1;
                    		    		intedline-=2;
                    		    	}
		    		}

		    		else if(checker.equals("C")) {
		    			StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(0);
		    			remover.deleteCharAt(line_arr[2].length()-3);
		    			line_arr[2]= remover.toString();
		    			//line_arr[2]=ascii_maker(line_arr[2],line_arr[2].length());
		    			symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                        st.put(line_arr[0],String.format("%x", pccounter));
                                        if(line_arr[2].length()<3)
                                        pccounter+=line_arr[2].length();
                                        else
                                        	pccounter+=0X3;

		    		}
		    		else {
                                pass1counter.println(String.format("%x", pccounter));
		    		symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		st.put(line_arr[0],String.format("%x", pccounter));
                                pccounter +=0X1;
                                }
		    		
		    	}
		    	else if (line_type.equals("RESB")){
                                pass1counter.println(String.format("%x", pccounter));
		    		//String pc = Integer.toString(pccounter);
		    		symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                st.put(line_arr[0],String.format("%x", pccounter));
                                pccounter =pccounter + (Integer.parseInt(line_arr[2]));

		    	}
		    	else if (line_type.equals("RESW")){
                                pass1counter.println(String.format("%x", pccounter));
		    		String wordpc = Integer.toString(pccounter);
		    		symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
                                st.put(line_arr[0],String.format("%x", pccounter));
                                pccounter =pccounter + (Integer.parseInt(line_arr[2])*3);
		    	
		    	}
		    	else if (line_type.equals("instruction")){	
                            pass1counter.println(String.format("%x", pccounter));
                            pccounter +=0X3;
		    	
		    	}
		    	
		    }
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		symboltablewriter.close();
                pass1counter.close();
	}
        public static void pass2(String myfile,Map<String,String>op,Map<String,String>st) throws FileNotFoundException{
            PrintWriter objectcode;
			try {
				objectcode = new PrintWriter("objectcode.txt", "UTF-8");
			
            File file = new File(myfile);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            String line_type = "";
            
            try {
                while ((line = reader.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line);
                    line_type = linetype(token.countTokens(),line);
		    	String line_arr[] = line.split(" ");
		    	String x = "x";
	    		String c ="c";
                String checker="";
                if(token.countTokens()>2)
                    checker = Character.toString(line_arr[2].charAt(0));
             	if (line_type.equals("label")){
             		if(line_arr[2].charAt(line_arr[2].length()-1 )== 'X'&&line_arr[2].charAt(line_arr[2].length()-2 )== ',' ) {
            			int indexed = Integer.parseInt(st.get(line_arr[1]),16)+ 32768;
            			String instruction_helper = String.format("%x", indexed);
            			objectcode.write(op.get(line_arr[0])+sappender4(instruction_helper,instruction_helper.length())+"\n");
            	}
            		else {
            			objectcode.write(op.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length())+"\n");

            		}
             		
             		//String opcodevar = op.get(line_arr[1])+sappender4(st.get(line_arr[0]));
             		//objectcode.write(opcodevar); 
             		
                    }
             	
	else if (line_type.equals("word")){
          
		/*if(checker.equals("x")) {
			
			//decimal = Integer.parseInt(line_arr[2],16);
			StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(0);
			remover.deleteCharAt(0);
			remover.deleteCharAt(line_arr[2].length()-3);
			line_arr[2]=sappender6(remover.toString());
			objectcode.write(line_arr[2]);
			
		}

		else if(checker.equals("c")) {
			StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(0);
			remover.deleteCharAt(0);
			remover.deleteCharAt(line_arr[2].length()-3);
			line_arr[2]=remover.toString();
			//line_arr[2]=ascii_maker(line_arr[2],line_arr[2].length());
		
		}*/
		objectcode.write(sappender6(line_arr[2])+"\n");
         }
	else if (line_type.equals("byte")){
        		if(checker.equals("X")) {
			StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(0);
			remover.deleteCharAt(0);
			remover.deleteCharAt(line_arr[2].length()-3);
			line_arr[2]=(remover.toString());
			objectcode.write(line_arr[2]+"\n");
			
        }

		else if(checker.equals("C")) {
			/*StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(0);
			remover.deleteCharAt(0);
			remover.deleteCharAt(line_arr[2].length()-3);
			line_arr[2]= remover.toString();
			String ascii_helper="";
			int cbytelength = line_arr[2].length();
			int firstpart=0,secondpart=3;
			while(cbytelength>0&&cbytelength!=0) {
				//System.out.println(line_arr[2].substring(firstpart, secondpart));

				if(line_arr[2].length()<=3) {
					ascii_helper=ascii_maker(line_arr[2].substring(firstpart, line_arr[2].length()-1),line_arr[2].length());
					objectcode.write(ascii_helper+"\n");
					cbytelength-=3;
					firstpart+=3;
					secondpart+=3;
				}
				else {
					ascii_helper=ascii_maker(line_arr[2].substring(firstpart, secondpart),line_arr[2].length());
					System.out.println(ascii_helper);
					objectcode.write(ascii_helper+"\n");

					firstpart+=3;
					cbytelength-=3;
					if(cbytelength > 3 )
					secondpart+=3; 
					else
					secondpart+=cbytelength-1;
				}
			}*/
			String ascii="";
			StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(0);
			remover.deleteCharAt(0);
			remover.deleteCharAt(line_arr[2].length()-3);
			line_arr[2]=remover.toString();
			byte[] bytes =line_arr[2].getBytes("US-ASCII");
			int iterations = line_arr[2].length();
			for(int i =0; i<iterations; i+=3) {
		    if(iterations-i>=3) {
		    	int ascii1 = (bytes[i]);
		    	int ascii2=(bytes[i+1]);
		    	int ascii3=(bytes[i+2]);
		    	String ascii1_s=String.format("%x", ascii1);
		    	String ascii2_s=String.format("%x", ascii2);
		    	String ascii3_s=String.format("%x", ascii3);
		    	ascii=ascii1_s+ascii2_s+ascii3_s;
		    	   objectcode.write( ascii+"\n");
		    }
		    else if(iterations-i>=2) {
		    	int ascii1 = (bytes[i]);
		    	int ascii2=(bytes[i+1]);
		    	String ascii1_s=String.format("%x", ascii1);
		    	String ascii2_s=String.format("%x", ascii2);
		    	ascii=ascii1_s+ascii2_s;
				   System.out.println(ascii); 
		    	   objectcode.write( ascii+"\n");
		    }
		    else if(iterations-i==1) {
		    	 int ascii1 = (bytes[i]);
		    	 String ascii1_s=String.format("%x", ascii1);
		    	 ascii = ascii1_s;
		    objectcode.write( ascii+"\n");
		    }
				}
		
		}
		else {
			//StringBuilder remover = new StringBuilder(line_arr[2]) ;
			//remover.deleteCharAt(0);
			//remover.deleteCharAt(0);
			//remover.deleteCharAt(line_arr[2].length()-3);
			//line_arr[2]=remover.toString();
			objectcode.write(sappender6(String.format("%x",line_arr[2]))+"\n");
                    }
		
	}
	else if (line_type.equals("RESB")){
		//objectcode.write("\n");
	}
	else if (line_type.equals("RESW")){
		//objectcode.write("\n");       
	
	}
	else if (line_type.equals("instruction")){	
		/*if(line_arr[1].charAt(line_arr[1].length()-1 )== 'X'&&line_arr[1].charAt(line_arr[1].length()-2 )== ',' ) {
			System.out.println( st.get(line_arr[1]));
			int indexed = Integer.parseInt(st.get(line_arr[1]));
			String instruction_helper = String.format("%x", Integer.toString(indexed));
		
			
		objectcode.write(op.get(line_arr[0])+sappender4(instruction_helper)+"\n");
	}
		else {*/
			objectcode.write(op.get(line_arr[0])+sappender4(st.get(line_arr[1]),st.get(line_arr[1]).length())+"\n");
			
		//}
	}


                }
            } catch (IOException ex) {
                Logger.getLogger(assembler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            objectcode.close();
        
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
               System.out.println("enter the name of your file");
               Scanner filename = new Scanner(System.in);
               String myfile = filename.nextLine();
               myfile = myfile.trim();
               myfile= myfile + ".txt";
               Map<String,String > OP = new HashMap<>();//hashmap OPcode to simle
               Map<String,String > ST = new HashMap<>();//symble table
               pass1(myfile,ST);
               OP.put("ADD", "18");
               OP.put("AND", "40");
               OP.put("COPM", "28");
               OP.put("DIV", "24");
               OP.put("J", "3C");
               OP.put("JEQ", "30");
               OP.put("JGT", "34");
               OP.put("JLT", "38");
               OP.put("JSUB", "48");
               OP.put("LDA", "00");
               OP.put("LDCH", "50");
               OP.put("LDL", "08");
               OP.put("LDX", "04");
               OP.put("MUL", "20");
               OP.put("OR", "44");
               OP.put("RD", "D8");
               OP.put("RSUB", "4C");
               OP.put("STA", "0C");
               OP.put("STCH", "54");
               OP.put("STL", "14");
               OP.put("STSW", "E8");
               OP.put("STX", "10");
               OP.put("SUB", "1C");
               OP.put("TD", "E0");
               OP.put("TIX", "2C");
               OP.put("WD", "DC");
               pass2(myfile,OP,ST);
               System.out.println("fin");

	}

}