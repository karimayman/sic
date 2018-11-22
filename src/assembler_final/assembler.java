package assembler_final;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

;


public class assembler {
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}
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
	public static String linetype(int count , String line,Map<String,String>OP_1,Map<String,String>OP_2,List<String> temp_lit) {
		String type = "";
		int flag = 0;
    	StringTokenizer token = new StringTokenizer(line);
    	String[] arr_token= line.split(" ");
            if (token.countTokens()>2) {
            	if(arr_token[2].charAt(0)=='=') {
            		if(!temp_lit.contains(arr_token[2]))
            		temp_lit.add(arr_token[2]);
            	}
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
                else if(arr_token[1].equals("EQU")) {
                	type = "equ";
                }
                else if(arr_token[1].charAt(0)== '+') {
                	type ="format4";
                	
                }
                else if(OP_2.containsKey(arr_token[0])) {
    		 		type ="format2";
    		 	}
                else if(OP_2.containsKey(arr_token[1])) {
	      			type ="format2_label";
	      		}
    		else {
    			 type = "label";
    		}
	}
       else if (token.countTokens()==2){
		    	   if(arr_token[1].charAt(0)=='=') {
		    		   if(!temp_lit.contains(arr_token[1]))
		    		   temp_lit.add(arr_token[1]);
		       		}	
                 if (arr_token[1].equals("START")&&count!=3 ) {
    			 type = "notStart";
    			
    		}
                 else if (arr_token[0].equals("END")) {
    			 type = "end";
                 }
    			 else if(arr_token[0].charAt(0)== '+') {
                 	type ="format4_2";
                 	
                 }
                 else if(OP_2.containsKey(arr_token[0])) {
         			type ="format2";
         		}
                 else if(OP_1.containsKey(arr_token[1])) {
         			type ="format1_label";
         		}

                else {
                    type = "instruction";
                }
            
        }
       else if (token.countTokens()==1) {
    	   
    		if(OP_1.containsKey(arr_token[0])) {
    			type ="format1";
    		}
    		else if(arr_token[0].equals("RSUB")) {
    			type="intruction";
    		}
    		else if(arr_token[0].equals("LTORG")) {
    			type="ltorg";
    		}
    	}
    	return type; 
   }
	
	public static void pass1(String myfile,Map<String,String>st,Map<String,String>OP_1,Map<String,String>OP_2,List<String> temp_lit,Map<String,String>ltorg) throws IOException {
		File file = new File(myfile);
		String line = "";
		String line_type = "";
		int pccounter = 0X0,intialcounter = 0X0;
		PrintWriter symboltablewriter = new PrintWriter("symboltable.txt", "UTF-8");
                PrintWriter pass1counter = new PrintWriter("pass1counter.txt", "UTF-8");
                PrintWriter littable = new PrintWriter("littable.txt", "UTF-8");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
		    while ((line = reader.readLine()) != null) { 
		    	StringTokenizer token = new StringTokenizer(line);
		    	line_type = linetype(token.countTokens(),line,OP_1,OP_2,temp_lit);
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
		    	else if (line_type.equals("format4")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 pccounter +=0X4;
		    		 
		    	}
		    	else if (line_type.equals("format4_2")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 pccounter +=0X4;
		    		 
		    	}
		    	else if (line_type.equals("format1")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 pccounter +=0X1;
		    		 
		    	}
		    	else if (line_type.equals("format2")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 pccounter +=0X2;
		    		 
		    	}
		    	else if (line_type.equals("format2_label")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 pccounter +=0X2;
		    		 
		    	}
		    	else if (line_type.equals("format1_label")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 pccounter +=0X1;
		    	}
		    	else if(line_type.equals("ltorg")||line_type.equals("end")) {
		    		if(!temp_lit.isEmpty()) {
		    			
		    			for (int i=0;i<temp_lit.size();i++) {
		    				String temp_holder = temp_lit.get(i);
		    				if(temp_holder.charAt(1)=='X') {
			   		    		StringBuilder remover = new StringBuilder(temp_holder) ;
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(temp_holder.length()-4);
				    			temp_holder=(remover.toString());
				    			double size=(double) temp_holder.length()/2;
				    			size = Math.ceil(size);
				    			int inted_size= (int) size;	
				    			String string_size= Integer.toString(inted_size);
				    			pccounter=pccounter+(inted_size);
				    			littable.println(temp_lit.get(i)+" "+temp_holder+" "+string_size+" "+String.format("%x", pccounter));
				    			ltorg.put(temp_lit.get(i), String.format("%x", pccounter));
	                        
		    				}
		    				else if(temp_holder.charAt(1)=='C') {
		    		    		StringBuilder remover = new StringBuilder(temp_holder) ;
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(0);
				    			remover.deleteCharAt(temp_holder.length()-4);
				    			temp_holder=(remover.toString());
				    			String value = ascii_maker(temp_holder,temp_holder.length());
				    			littable.println(temp_lit.get(i)+" "+value+" "+temp_holder.length()+" "+String.format("%x", pccounter));
				    			ltorg.put(temp_lit.get(i), String.format("%x", pccounter));
				    			int inted_size=temp_holder.length();	
				    			pccounter=pccounter+(inted_size);

		    				}
		    				else {
		    					StringBuilder remover = new StringBuilder(temp_holder) ;
				    			temp_holder = remover.deleteCharAt(0).toString();
				    		
		    					littable.println(temp_lit.get(i)+" "+String.format("%x",temp_holder)+" "+temp_holder.length()+" "+String.format("%x", pccounter));
		    					ltorg.put(temp_lit.get(i), String.format("%x", pccounter));
		    					System.out.println(temp_lit.get(i));
		    					String temp1= String.format("%x", temp_lit.get(i));
		    					int temp2 = Integer.parseInt(temp1,16);
		    					pccounter=pccounter+temp2;
		    				}
		    			}
		    			temp_lit.clear();
		    			}
		    		}

		    	else if (line_type.equals("equ")) {
		    		 System.out.println("aha");
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 if(line_arr[2].charAt(0)=='*') {
		    			
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 st.put(line_arr[0],String.format("%x", pccounter) );
		    		 }
		    		/* else if(line_arr[2].charAt(0)=='=') {
			    		 st.put(line_arr[0],String.format("%x", pccounter));
			    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
			    		 st.put(line_arr[0],String.format("%x", pccounter) );
			    		 }*/
		    		 else if(line_arr[2].indexOf('-')>=0||line_arr[2].indexOf('+')>=0){
		    				if(line_arr[2].indexOf('-')>0) {
		    					int place =line_arr[2].indexOf('-');
		    					String subs1 = line_arr[2].substring(0, place);
		    					System.out.println(subs1);
		    					String subs2 = line_arr[2].substring(place+1,line_arr[2].length() );
		    					System.out.println(subs2);
		    					if(!isNumeric(subs2)) {
		    					int op1 = Integer.parseInt( st.get(subs1),16);
		    					int op2 = Integer.parseInt( st.get(subs2),16);
		    					int result = op1-op2;
		    					String result_s= String.format("%x", result);
		    					symboltablewriter.println(line_arr[0]+ "	"+result_s);
		    					st.put(line_arr[0], result_s);
		    						}
		    					else {
		    						int op1 = Integer.parseInt( st.get(subs1),16);
		    						int op2 = Integer.parseInt( subs2);
		    						int result = op1-op2;
		    						String result_s= String.format("%x", result);
			    					symboltablewriter.println(line_arr[0]+ "	"+result_s);
			    					st.put(line_arr[0], result_s);
		    					}
		    					}
		    				else if(line_arr[2].indexOf('+')>0) {
		    					int place =line_arr[2].indexOf('+');
		    					String subs1 = line_arr[2].substring(0, place);
		    					String subs2 = line_arr[2].substring(place+1,line_arr[2].length() );
		    					if(!isNumeric(subs2)) {
		    					int op1 = Integer.parseInt( st.get(subs1),16);
		    					int op2 = Integer.parseInt( st.get(subs2),16);
		    					int result = op1+op2;
		    					String result_s= String.format("%x", result);
		    					symboltablewriter.println(line_arr[0]+ "	"+result_s);
		    					st.put(line_arr[0], result_s);
		    						}
		    					
		    				else {
	    						int op1 = Integer.parseInt( st.get(subs1),16);
	    						int op2 = Integer.parseInt(subs2);
	    						int result = op1+op2;
	    						String result_s= String.format("%x", result);
		    					symboltablewriter.println(line_arr[0]+ "	"+result_s);
		    					st.put(line_arr[0], result_s);
		    					}
		    				}
		    				else if(st.containsKey(line_arr[2])) {
		    					String temp_holding = st.get(line_arr[2]);
		    					symboltablewriter.println(line_arr[0]+ "	"+temp_holding);
		    					st.put(line_arr[0], temp_holding);
		    				}
		    				 
		    			 
		    		 else {
		    			 st.put(line_arr[0],String.format("%x", Integer.parseInt(line_arr[2])));
		    		 	 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x",line_arr[2]));
		    		 	st.put(line_arr[0], String.format("%x", line_arr[2]));
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
		    }
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		symboltablewriter.close();
                pass1counter.close();
                littable.close();
	}
        	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
               System.out.println("enter the name of your file");
               Scanner filename = new Scanner(System.in);
               String myfile = filename.nextLine();
               myfile = myfile.trim();
               myfile= myfile + ".txt";
               List<String> temp_lit = new ArrayList<String>();
               Map<String,String > LTORG = new HashMap<>();//hashmap OPcode to simle
               Map<String,String > OP = new HashMap<>();//hashmap OPcode to simle
               Map<String,String > ST = new HashMap<>();//symble table
               Map<String,String > OP_1 = new HashMap<>();//hashmap OPcode to simle
               Map<String,String > OP_2 = new HashMap<>();//hashmap OPcode to simle
               
               OP.put("ADD", "18");
               OP.put("ADDF", "58");
               OP.put("AND", "40");
               OP.put("COPM", "28");
               OP.put("COPMF", "88");
               OP.put("DIV", "24");
               OP.put("DIVF", "64");
               OP.put("J", "3C");
               OP.put("JEQ", "30");
               OP.put("JGT", "34");
               OP.put("JLT", "38");
               OP.put("JSUB", "48");
               OP.put("LDA", "00");
               OP.put("LDB", "68");
               OP.put("LDCH", "50");
               OP.put("LDF", "70");
               OP.put("LDL", "08");
               OP.put("LDS", "6C");
               OP.put("LDT", "74");
               OP.put("LDX", "04");
               OP.put("LPS", "D0");
               OP.put("MUL", "20");
               OP.put("MULF", "60");
               OP.put("OR", "44");
               OP.put("RD", "D8");
               OP.put("RSUB", "4C");
               OP.put("SSK", "EC");
               OP.put("STA", "0C");
               OP.put("STB", "78");
               OP.put("STCH", "54");
               OP.put("STF", "80");
               OP.put("STI", "D4");
               OP.put("STL", "14");
               OP.put("STS", "7C");
               OP.put("STSW", "E8");
               OP.put("STT", "84");
               OP.put("STX", "10");
               OP.put("SUB", "1C");
               OP.put("SUBF", "5C");
               OP.put("TD", "E0");
               OP.put("TIX", "2C");
               OP.put("WD", "DC");
               //
               //
               //
               OP_1.put("FIX", "C4");
               OP_1.put("FLOAT", "C0");
               OP_1.put("HIO", "F4");
               OP_1.put("NORM", "C8");
               OP_1.put("SIO", "F0");
               OP_1.put("TIO", "F8");
               //
               //
               //
               OP_2.put("ADDR", "90");
               OP_2.put("CLEAR", "B4");
               OP_2.put("COMPR", "A0");
               OP_2.put("DIVR", "9C");
               OP_2.put("MULR", "98");
               OP_2.put("RMO", "AC");
               OP_2.put("SHIFTL", "A4");
               OP_2.put("SHIFTR", "A8");
               OP_2.put("SUBR", "94");
               OP_2.put("SVC", "B0");
               OP_2.put("TIXR", "B8");	
               Map<String,String > OPM = new HashMap<>();//hashmap OPMcode to simle
               
               OPM.put("ADD", "18");
               OPM.put("ADDF", "58");
               OPM.put("AND", "40");
               OPM.put("COPMM", "28");
               OPM.put("COPMMF", "88");
               OPM.put("DIV", "24");
               OPM.put("DIVF", "64");
               OPM.put("J", "3C");
               OPM.put("JEQ", "30");
               OPM.put("JGT", "34");
               OPM.put("JLT", "38");
               OPM.put("JSUB", "48");
               OPM.put("LDA", "00");
               OPM.put("LDB", "68");
               OPM.put("LDCH", "50");
               OPM.put("LDF", "70");
               OPM.put("LDL", "08");
               OPM.put("LDS", "6C");
               OPM.put("LDT", "74");
               OPM.put("LDX", "04");
               OPM.put("LPS", "D0");
               OPM.put("MUL", "20");
               OPM.put("MULF", "60");
               OPM.put("OR", "44");
               OPM.put("RD", "D8");
               OPM.put("RSUB", "4C");
               OPM.put("SSK", "EC");
               OPM.put("STA", "0C");
               OPM.put("STB", "78");
               OPM.put("STCH", "54");
               OPM.put("STF", "80");
               OPM.put("STI", "D4");
               OPM.put("STL", "14");
               OPM.put("STS", "7C");
               OPM.put("STSW", "E8");
               OPM.put("STT", "84");
               OPM.put("STX", "10");
               OPM.put("SUB", "1C");
               OPM.put("SUBF", "5C");
               OPM.put("TD", "E0");
               OPM.put("TIX", "2C");
               OPM.put("WD", "DC");
               //
               //
               //
               OPM.put("FIX", "C4");
               OPM.put("FLOAT", "C0");
               OPM.put("HIO", "F4");
               OPM.put("NORM", "C8");
               OPM.put("SIO", "F0");
               OPM.put("TIO", "F8");
               //
               //
               //
               OPM.put("ADDR", "90");
               OPM.put("CLEAR", "B4");
               OPM.put("COMPR", "A0");
               OPM.put("DIVR", "9C");
               OPM.put("MULR", "98");
               OPM.put("RMO", "AC");
               OPM.put("SHIFTL", "A4");
               OPM.put("SHIFTR", "A8");
               OPM.put("SUBR", "94");
               OPM.put("SVC", "B0");
               OPM.put("TIXR", "B8");
               pass1(myfile,ST,OP_1,OP_2,temp_lit,LTORG);
             //  pass2(myfile,OP,ST,OP_1,OP_2,temp_lit,OPM);
               System.out.println("fin");

	}

}