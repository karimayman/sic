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

	public static String binary_maker(String hex)
	{
	    String s = "";
		 String[] staticLookup = new String[]
				    {"0000","0001","0010","0011","0100","0101","0110","0111",
				     "1000","1001","1010","1011","1100","1101","1110","1111"};
		 	int temp_bin;
		 	String another= "";
		 	String st = "";
		 	String edited = "";
		 	edited=hex;
		 	StringBuilder charremover = new StringBuilder(edited) ;
			char Hex;
	    for(int i =0; i<hex.length();i++)
	    {	
	         Hex = edited.charAt(0);
	         s= s+staticLookup[Integer.parseInt(Character.toString(Hex), 16)];
	         charremover.deleteCharAt(0);
			 edited=charremover.toString();
	    }
	    return s;
	}
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
	    if(string_length==3) {
	    	int ascii1 =bytes[0];
	    	int ascii2 = bytes[1];
	    	int ascii3 = bytes[2];
	    	ascii = String.format("%x",ascii1)+String.format("%x",ascii2)+String.format("%x",ascii3);
	    }
	    else if(string_length==2) {
	    	int ascii1 =bytes[0];
	    	int ascii2 =bytes[1];
	    	ascii = String.format("%x",ascii1)+String.format("%x",ascii2);
	    }
	    							
	    else if(string_length==1) {
	    	int ascii1 = bytes[0];
	    	String.format("%x",ascii);
	    }
		    
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
	public static String name_appender(String value,int iteration){
		String zero = "";
		if (iteration == 3) {
			zero="   ";
			zero =zero+value;
		}
		else if(iteration ==2) {
			zero="    ";
			zero = zero+value;
		}
		else if(iteration ==1) {
			zero="     ";
			zero = zero+value;
		}
		else if(iteration ==4) {
			zero="  ";
			zero = zero+value;
		}
		else if(iteration ==5) {
			zero=" ";
			zero = zero+value;
		}
		else {
			zero = value;
		}
		return zero;
	}  
	public static String sappender6(String value,int iteration){
		String zero = "";
		if (iteration == 3) {
			zero="000";
			zero =zero+value;
		}
		else if(iteration ==2) {
			zero="0000";
			zero = zero+value;
		}
		else if(iteration ==1) {
			zero="00000";
			zero = zero+value;
		}
		else if(iteration ==4) {
			zero="00";
			zero = zero+value;
		}
		else if(iteration ==5) {
			zero="0";
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
            	else if (arr_token[1].equals("BASE")) {
       			 type = "basel";
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
    				else if (arr_token[0].equals("BASE")) {
    	       			 type = "base";
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
	
	public static void pass1(String myfile,Map<String,String>st,Map<String,String>OP_1,Map<String,String>OP_2,List<String> temp_lit,Map<String,String>ltorg,List<String> loccounter) throws IOException {
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
                             st.put("progstart",String.format("%x", intialcounter));
                             st.put("progname",line_arr[0]);
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
		    		 loccounter.add(String.format("%x", pccounter));
		    		 
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
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 pccounter +=0X2;
		    		 
		    	}
		    	else if (line_type.equals("format1_label")) {
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 pccounter +=0X1;
		    	}
		    	else if(line_type.equals("ltorg")||line_type.equals("end")) {
		    		if(!temp_lit.isEmpty()) {
		    			pass1counter.println(String.format("%x", pccounter));
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
		    					String temp1= String.format("%x", temp_lit.get(i));
		    					int temp2 = Integer.parseInt(temp1,16);
		    					pccounter=pccounter+temp2;
		    				}
		    			}
		    			temp_lit.clear();
		    			}
		    		}

		    	else if (line_type.equals("equ")) {
		    		 
		    		 pass1counter.println(String.format("%x", pccounter));
		    		 if(line_arr[2].charAt(0)=='*') {
		    			
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 symboltablewriter.println(line_arr[0]+ "	"+String.format("%x", pccounter));
		    		 st.put(line_arr[0],String.format("%x", pccounter));
		    		 pass1counter.println(String.format("%x", pccounter));
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
		    					String subs2 = line_arr[2].substring(place+1,line_arr[2].length() );
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
		    	else if (line_type.equals("base")){
		    		st.put("base", line_arr[1]);
		    	}
		    	else if (line_type.equals("basel")){
		    		st.put("base", line_arr[2]);
		    	}
		    	else if (line_type.equals("instruction")){	
                            pass1counter.println(String.format("%x", pccounter));
                            pccounter +=0X3;
                            loccounter.add(String.format("%x", pccounter));
		    	}
		    	
		    
		    }
		    int prglength = pccounter-Integer.parseInt(st.get("progstart"),16);
			st.put("proglength", String.format("%x",prglength));
			//System.out.println(st.get("prglength"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		symboltablewriter.close();
        pass1counter.close();
        littable.close();
	}
        public static void pass2(String myfile,Map<String,String>op,Map<String,String>st,Map<String,String>OP_1,Map<String,String>OP_2,List<String> temp_lit,Map<String,String > OPM,Map<String,String>ltorg,List<String> loccounter) throws FileNotFoundException{
            PrintWriter objectcode;
			try {
				objectcode = new PrintWriter("objectcode.txt", "UTF-8");
				PrintWriter HTME = new PrintWriter("HTME.txt", "UTF-8");
				HTME.write("H"+name_appender(st.get("progname"),st.get("progname").length())+sappender6(st.get("progstart"),st.get("progstart").length())+sappender6(st.get("proglength"),st.get("proglength").length())+"\n");
            File file = new File(myfile);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            String line_type = "";
            int destination = 0; 
            int htme_counter =0;
            List<String> mrecord = new ArrayList<String>();;
            try {
                while ((line = reader.readLine()) != null) {
                    StringTokenizer token = new StringTokenizer(line);
                    line_type = linetype(token.countTokens(),line,OP_1,OP_2,temp_lit);
		    	String line_arr[] = line.split(" ");
		    	String x = "x";
	    		String c ="c";
                String checker="";
                if(token.countTokens()>2)
                    checker = Character.toString(line_arr[2].charAt(0));
             	if (line_type.equals("label")){
             		if(line_arr[2].charAt(line_arr[2].length()-1 )== 'X'&&line_arr[2].charAt(line_arr[2].length()-2 )== ',' ) {
            			//int indexed = Integer.parseInt(st.get(line_arr[1]),16)+ 32768;
            			//String instruction_helper = String.format("%x", indexed);
            			//objectcode.write(OPM.get(line_arr[0])+sappender4(instruction_helper,instruction_helper.length())+"\n");
             			StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(line_arr[2].length()-1);
		    			remover.deleteCharAt(line_arr[2].length()-2);
		    			line_arr[2]=remover.toString();
             			String temp=OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length());
    					String temp_string = binary_maker(temp);
    					char[] binary_rep = temp_string.toCharArray();
    					if(line_arr[1].charAt(0)=='@') {
    					binary_rep[6] = '1' ;
    					}
    					else if(line_arr[1].charAt(0)=='#') {
        					binary_rep[7] = '1' ;
        					}
    					else {
    						binary_rep[6] = '1' ;
    						binary_rep[7] = '1' ;
    					}
    					binary_rep[8] = '1' ;
    					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
    						binary_rep[10]='1';
    					}
    					else {
    						binary_rep[9]='1';
    					}
    					String object_code = new String(binary_rep);
    					int temp_conversion = Integer.parseInt(object_code,2);
    					object_code= String.format("%x", temp_conversion);
        				objectcode.write(object_code+"\n");
             		}
            		else {
            			if(!isNumeric(line_arr[2]) ) {
	            				if(line_arr[2].charAt(0)=='@') {
	            					StringBuilder remover = new StringBuilder(line_arr[2]) ;
	        		    			remover.deleteCharAt(0);
	        		    			line_arr[2]=remover.toString();
	        		    			if(!isNumeric(line_arr[2]) ) {
	            					String temp=OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length());
	            					//String hex = Integer.toString(temp);
	            					//temp = Integer.parseInt(hex,16);
	            					String temp_string = binary_maker(temp);
	            					char[] binary_rep = temp_string.toCharArray();
	            					binary_rep[6] = '1' ;
	            					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
	            						binary_rep[10]='1';
	            					}
	            					else {
	            						binary_rep[9]='1';
	            					}
	            					String object_code = new String(binary_rep);
	            					int temp_conversion = Integer.parseInt(object_code,2);
	            					object_code= String.format("%x", temp_conversion);
		            				objectcode.write(object_code+"\n");
	        		    			}
	        		    			else if(isNumeric(line_arr[2])) {
	        		    				objectcode.write(OPM.get(line_arr[1])+sappender4(String.format("%x", line_arr[2]),String.format("%x", line_arr[2]).length())+"\n");	
	        		    			}
	            				}
		            			else if( line_arr[2].charAt(0)=='=') {
		            				objectcode.write(OPM.get(line_arr[1])+sappender4(ltorg.get(line_arr[2]),ltorg.get(line_arr[2]).length())+"\n");
		            			}
		            			else if(line_arr[2].charAt(0)=='#') {
	            					StringBuilder remover = new StringBuilder(line_arr[2]) ;
	        		    			remover.deleteCharAt(0);
	        		    			line_arr[2]=remover.toString();
	        		    			if(!isNumeric(line_arr[2])) {
		            					String temp=OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length());
		            					//String hex = Integer.toString(temp);
		            					//temp = Integer.parseInt(hex,16);
		            					String temp_string = binary_maker(temp) ;
		            					char[] binary_rep = temp_string.toCharArray();
		            					binary_rep[7] = '1' ;
		            					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
		            						binary_rep[10]='1';
		            					}
		            					else {
		            						binary_rep[9]='1';
		            					}
		            					String object_code = new String(binary_rep);
		            					int temp_conversion = Integer.parseInt(object_code,2);
		            					object_code= String.format("%x", temp_conversion);
		            					
			            				objectcode.write(object_code+"\n");
	        		    			}
	        		    			else if(isNumeric(line_arr[2])) {
	        		    				objectcode.write(OPM.get(line_arr[1])+sappender4(String.format("%x", line_arr[2]),String.format("%x", line_arr[2]).length())+"\n");
	        		    			}
		            			}
	            				
				    			else {
				    				System.out.println(OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length()));
				    				String temp_ss=OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length());
				    				//int temp=Integer.parseInt(temp_ss,16);//+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length())
		        					//System.out.println(temp);
				    				/*String hex = Integer.toString(temp);
		        					temp = Integer.parseInt(hex,16);*/
		        					//String temp_string = binary_maker(temp) ;
				    				String binary = binary_maker(temp_ss);
				    				char[] binary_rep = binary.toCharArray();	
		        					
		        					binary_rep[6] = '1' ;
		        					binary_rep[7] = '1' ;
		        					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
	            						binary_rep[10]='1';
	            					}
	            					else {
	            						binary_rep[9]='1';
	            					}
		        					String object_code =  new String(binary_rep);;//new String(binary_rep);
		        					int temp_conversion = Integer.parseInt(object_code,2);
		        					object_code= String.format("%x", temp_conversion);
		        					System.out.println(object_code);
		            				objectcode.write(object_code+"\n");

				    			}

	            				
            			}
            		}
             		//objectcode.write(op.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length())+"\n");
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
		objectcode.write(sappender6(line_arr[2],line_arr[2].length())+"\n");
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
			objectcode.write(sappender6(String.format("%x",line_arr[2]),String.format("%x",line_arr[2]).length())+"\n");
                    }
		
	}
	else if (line_type.equals("format4")){
 		/*if(line_arr[2].charAt(line_arr[2].length()-1 )== 'X'&&line_arr[2].charAt(line_arr[2].length()-2 )== ',' ) {
			int indexed = Integer.parseInt(st.get(line_arr[1]),16)+ 32768;
			String instruction_helper = String.format("%x", indexed);
			objectcode.write(OPM.get(line_arr[0])+sappender4(instruction_helper,instruction_helper.length())+"\n");
	}
		else {*/if(line_arr[2].charAt(line_arr[2].length()-1 )== 'X'&&line_arr[2].charAt(line_arr[2].length()-2 )== ',' ) {
			//int indexed = Integer.parseInt(st.get(line_arr[1]),16)+ 32768;
			//String instruction_helper = String.format("%x", indexed);
			//objectcode.write(OPM.get(line_arr[0])+sappender4(instruction_helper,instruction_helper.length())+"\n");
 			StringBuilder remover = new StringBuilder(line_arr[2]) ;
			remover.deleteCharAt(line_arr[2].length()-1);
			remover.deleteCharAt(line_arr[2].length()-2);
			line_arr[2]=remover.toString();
 			String temp=OPM.get(line_arr[1])+sappender4(st.get(line_arr[2]),st.get(line_arr[2]).length());
			String temp_string = binary_maker(temp);
			char[] binary_rep = temp_string.toCharArray();
			if(line_arr[1].charAt(0)=='@') {
			binary_rep[6] = '1' ;
			}
			else if(line_arr[1].charAt(0)=='#') {
				binary_rep[7] = '1' ;
				}
			else {
				binary_rep[6] = '1' ;
				binary_rep[7] = '1' ;
			}
			binary_rep[8] = '1' ;
			String object_code = new String(binary_rep);
			int temp_conversion = Integer.parseInt(object_code,2);
			object_code= String.format("%x", temp_conversion);
			objectcode.write(object_code+"\n");
			int m  = Integer.parseInt(st.get(line_arr[0]),16)+1;
			mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
		}
	    	else if(!isNumeric(line_arr[2]) ) {
    				if(line_arr[2].charAt(0)=='@') {
    					StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			line_arr[2]=remover.toString();
		    			if(!isNumeric(line_arr[2]) ) {
    					String temp=OPM.get(line_arr[1])+sappender6(st.get(line_arr[2]),st.get(line_arr[2]).length());
    					//String hex = Integer.toString(temp);
    					//temp = Integer.parseInt(hex,16);
    					String temp_string = binary_maker(temp);
    					char[] binary_rep = temp_string.toCharArray();
    					binary_rep[6] = '1' ;
    					binary_rep[11] = '1' ;
    					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]))+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]))+3)>-2048) {
    						binary_rep[10]='1';
    					}
    					else {
    						binary_rep[9]='1';
    					}
    					String object_code = new String(binary_rep);
    					int temp_conversion = Integer.parseInt(object_code,2);
    					object_code= String.format("%x", temp_conversion);
        				objectcode.write(object_code+"\n");
        				int m  = Integer.parseInt(st.get(line_arr[0]),16)+1;
        				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
		    			}
    				
		    			else /*if(isNumeric(line_arr[2]))*/ {
		    				objectcode.write(OPM.get(line_arr[1])+sappender6(String.format("%x", line_arr[2]),String.format("%x", line_arr[2]).length())+"\n");	
		    			}
    				}
        			else if( line_arr[2].charAt(0)=='=') {
        				objectcode.write(OPM.get(line_arr[1])+sappender6(ltorg.get(line_arr[2]),ltorg.get(line_arr[2]).length())+"\n");
        				int m  = Integer.parseInt(st.get(line_arr[0]),16)+1;
        				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
        			}
        			else if(line_arr[2].charAt(0)=='#') {
    					StringBuilder remover = new StringBuilder(line_arr[2]) ;
		    			remover.deleteCharAt(0);
		    			line_arr[2]=remover.toString();
		    			if(!isNumeric(line_arr[2])) {
        					String temp=OPM.get(line_arr[1])+sappender6(st.get(line_arr[2]),st.get(line_arr[2]).length());
        					//String hex = Integer.toString(temp);
        					//temp = Integer.parseInt(hex,16);
        					String temp_string = binary_maker(temp) ;
        					char[] binary_rep = temp_string.toCharArray();
        					binary_rep[7] = '1' ;
        					binary_rep[11] = '1' ;
        					String object_code = new String(binary_rep);
        					int temp_conversion = Integer.parseInt(object_code,2);
        					object_code= String.format("%x", temp_conversion);
        					
            				objectcode.write(object_code+"\n");
            				int m  = Integer.parseInt(st.get(line_arr[0]),16)+1;
            				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");

		    			}
		    			else  {
		    				
		    				objectcode.write(OPM.get(line_arr[1])+sappender6(String.format("%x", line_arr[2]),String.format("%x", line_arr[2]).length())+"\n");
		    			}
        			}
		    			else {
		    				StringBuilder remover_helper1 = new StringBuilder(line_arr[1]) ;
			    			remover_helper1.deleteCharAt(0);
			    			line_arr[1]=remover_helper1.toString();
			    			
		    				String temp=OPM.get(line_arr[1])+sappender6(st.get(line_arr[2]),st.get(line_arr[2]).length());
        					//String hex = Integer.toString(temp);
        					//temp = Integer.parseInt(hex,16);
        					String temp_string = binary_maker(temp) ;
        					char[] binary_rep = temp_string.toCharArray();
        					binary_rep[6] = '1' ;
        					binary_rep[7] = '1' ;
        					
        					String object_code = new String(binary_rep);
        					int temp_conversion = Integer.parseInt(object_code,2);
        					object_code= String.format("%x", temp_conversion);
        					
            				objectcode.write(object_code+"\n");
            				int m  = Integer.parseInt(st.get(line_arr[0]),16)+1;
            				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
            				System.out.println("yeah=========================");
		    			}
        			}
    				
			}
		// dee taba3 el else el m3 el x flag , uncomment lema tzbt el x flag hena}
		
	 
	else if (line_type.equals("format4_2")){
 		/*if(line_arr[2].charAt(line_arr[2].length()-1 )== 'X'&&line_arr[2].charAt(line_arr[2].length()-2 )== ',' ) {
			int indexed = Integer.parseInt(st.get(line_arr[1]),16)+ 32768;
			String instruction_helper = String.format("%x", indexed);
			objectcode.write(OPM.get(line_arr[0])+sappender4(instruction_helper,instruction_helper.length())+"\n");
	}
		else {*/
			if(!isNumeric(line_arr[1]) ) {
    				if(line_arr[1].charAt(0)=='@') {
    					StringBuilder remover = new StringBuilder(line_arr[1]);
    					StringBuilder opmremover = new StringBuilder(line_arr[0]) ;
		    			remover.deleteCharAt(0);
		    			opmremover.deleteCharAt(0);
		    			line_arr[0]=opmremover.toString();
		    			line_arr[1]=remover.toString();
		    			if(!isNumeric(line_arr[1]) ) {
    					String temp=OPM.get(line_arr[0])+sappender6(st.get(line_arr[1]),st.get(line_arr[1]).length());
    					//String hex = Integer.toString(temp);
    					//System.out.println(hex);
    					//temp = Integer.parseInt(hex);
    					String temp_string = binary_maker(temp);
    					char[] binary_rep = temp_string.toCharArray();
    					binary_rep[6] = '1' ;
    					binary_rep[11] = '1' ;
    					String object_code = new String(binary_rep);
    					int temp_conversion = Integer.parseInt(object_code,2);
    					object_code= String.format("%x", temp_conversion);
        				objectcode.write(object_code+"\n");
        				//loccounter.get(i);
        				//i++;
        				int m  = Integer.parseInt(loccounter.get(htme_counter),16)+1;
        				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");

		    			}
		    			else if(isNumeric(line_arr[1])) {
		    				objectcode.write(OPM.get(line_arr[0])+sappender6(String.format("%x", line_arr[1]),String.format("%x", line_arr[1]).length())+"\n");	
		    			
		    			}
    				}
        			else if( line_arr[1].charAt(0)=='=') {
        				objectcode.write(OPM.get(line_arr[0])+sappender6(ltorg.get(line_arr[1]),ltorg.get(line_arr[1]).length())+"\n");
        				int m  = Integer.parseInt(loccounter.get(htme_counter),16)+1;
        			
        				mrecord.add("M"+String.format("%x", m)+"05");
        			}
        			else if(line_arr[1].charAt(0)=='#') {
    					StringBuilder remover = new StringBuilder(line_arr[1]) ;
		    			remover.deleteCharAt(0);
		    			line_arr[1]=remover.toString();
		    			if(!isNumeric(line_arr[1])) {
        					String temp=OPM.get(line_arr[0])+sappender6(st.get(line_arr[1]),st.get(line_arr[1]).length());
        					//String hex = Integer.toString(temp);
        					//temp = Integer.parseInt(hex,16);
        					String temp_string = binary_maker(temp) ;
        					char[] binary_rep = temp_string.toCharArray();
        					binary_rep[7] = '1' ;
        					binary_rep[11] = '1' ;
        					String object_code = new String(binary_rep);
        					int temp_conversion = Integer.parseInt(object_code,2);
        					object_code= String.format("%x", temp_conversion);
        					
            				objectcode.write(object_code+"\n");
            				int m  = Integer.parseInt(loccounter.get(htme_counter),16)+1;
            				
            				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
		    			}
		    			else if(isNumeric(line_arr[1])) {
		    				objectcode.write(OPM.get(line_arr[0])+sappender6(String.format("%x", line_arr[1]),String.format("%x", line_arr[1]).length())+"\n");
		    			}
		    			else {
		    				String temp=OPM.get(line_arr[0])+sappender6(st.get(line_arr[1]),st.get(line_arr[1]).length());
        					//String hex = Integer.toString(temp);
        					//temp = Integer.parseInt(hex,16);
        					String temp_string = binary_maker(temp) ;
        					char[] binary_rep = temp_string.toCharArray();
        					binary_rep[6] = '1' ;
        					binary_rep[7] = '1' ;
        					String object_code = new String(binary_rep);
        					int temp_conversion = Integer.parseInt(object_code,2);
        					object_code= String.format("%x", temp_conversion);
        					
            				objectcode.write(object_code+"\n");
            				int m  = Integer.parseInt(loccounter.get(htme_counter),16)+1;
            				
            				mrecord.add("M"+sappender6(String.format("%x", m),String.format("%x", m).length())+"05");
		    			}
        			}
    				
			}
		// dee taba3 el else el m3 el x flag , uncomment lema tzbt el x flag hena}
			htme_counter++;
	 }
	else if (line_type.equals("format2")){
		//objectcode.write("\n");
		   if(token.countTokens()>2) {
			   objectcode.write(OPM.get(line_arr[0])+OPM.get(line_arr[1])+OPM.get(line_arr[2])+"\n");
		   }
		   else {
			   objectcode.write(OPM.get(line_arr[0])+OPM.get(line_arr[1])+"0"+"\n");
		   }
		
	}
	else if (line_type.equals("format1")){
		objectcode.write(OPM.get(line_arr[0])+"\n");
		   	
	}
	else if(line_type.equals("format2_label")) {
		   //if(token.countTokens()>2) {
			   objectcode.write(OPM.get(line_arr[1])+OPM.get(line_arr[2])+"0"+"\n");
		 /*  }
		   else {
			   objectcode.write(OPM.get(line_arr[1])+"0"+"0"+"\n");
		   }*/
	}
	else if(line_type.equals("format1_label")) {
		 
			   objectcode.write(OPM.get(line_arr[1])+"\n");
		   
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
			//if(!isNumeric(line_arr[1]))
			//objectcode.write(OPM.get(line_arr[0])+sappender4(st.get(line_arr[1]),st.get(line_arr[1]).length())+"\n");
		if(!isNumeric(line_arr[1]) ) {
			if(line_arr[1].charAt(0)=='@') {
				StringBuilder remover = new StringBuilder(line_arr[1]) ;
    			remover.deleteCharAt(0);
    			line_arr[1]=remover.toString();
    			if(!isNumeric(line_arr[1]) ) {
				String temp=OPM.get(line_arr[0])+sappender4(st.get(line_arr[1]),st.get(line_arr[1]).length());
				//String hex = Integer.toString(temp);
				//temp = Integer.parseInt(hex,16);
				String temp_string = binary_maker(temp);
				char[] binary_rep = temp_string.toCharArray();
				binary_rep[6] = '1' ;
				if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
					binary_rep[10]='1';
				}
				else {
					binary_rep[9]='1';
					
				}
				loccounter.get(htme_counter);
				
				String object_code = new String(binary_rep);
				int new_address =Integer.parseInt(st.get(line_arr[2]),16) - Integer.parseInt(st.get(st.get("base")),16);
				if(binary_rep[9]=='1') {
				object_code = object_code.charAt(0)+object_code.charAt(1)+object_code.charAt(2)+String.format("%x",new_address);
				}
				int temp_conversion = Integer.parseInt(object_code,2);
				object_code= String.format("%x", temp_conversion);
				objectcode.write(object_code+"\n");
    			}
    			else if(isNumeric(line_arr[1])) {
    				objectcode.write(OPM.get(line_arr[0])+sappender4(String.format("%x", line_arr[1]),String.format("%x", line_arr[1]).length())+"\n");	
    			}
			}
			else if( line_arr[1].charAt(0)=='=') {
				objectcode.write(OPM.get(line_arr[0])+sappender4(ltorg.get(line_arr[1]),ltorg.get(line_arr[1]).length())+"\n");
			}
			else if(line_arr[1].charAt(0)=='#') {
				StringBuilder remover = new StringBuilder(line_arr[1]) ;
    			remover.deleteCharAt(0);
    			line_arr[1]=remover.toString();
    			if(!isNumeric(line_arr[1])) {
					String temp=OPM.get(line_arr[0])+sappender4(st.get(line_arr[1]),st.get(line_arr[1]).length());
					//String hex = Integer.toString(temp);
					//temp = Integer.parseInt(hex,16);
					String temp_string = binary_maker(temp) ;
					char[] binary_rep = temp_string.toCharArray();
					binary_rep[7] = '1' ;
					if (Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)<2048 &&Integer.parseInt(st.get(line_arr[2]),16) - (Integer.parseInt(st.get(line_arr[0]),16)+3)>-2048) {
						binary_rep[10]='1';
					}
					else {
						binary_rep[9]='1';
						
					}
					loccounter.get(htme_counter);
					String object_code = new String(binary_rep);
					int new_address =Integer.parseInt(st.get(line_arr[2]),16) - Integer.parseInt(st.get(st.get("base")),16);
					if(binary_rep[9]=='1') {
					object_code = object_code.charAt(0)+object_code.charAt(1)+object_code.charAt(2)+String.format("%x",new_address);
					}
					int temp_conversion = Integer.parseInt(object_code,2);
					object_code= String.format("%x", temp_conversion);
					
    				objectcode.write(object_code+"\n");
    			}
    			else if(isNumeric(line_arr[1])) {
    				objectcode.write(OPM.get(line_arr[0])+sappender6(String.format("%x", line_arr[1]),String.format("%x", line_arr[1]).length())+"\n");
    			}
			}
			
	}

		//}
		htme_counter++;
	}


                }
                for(int z = 0; z<=mrecord.size()-1;z++ ) {
                	HTME.write(mrecord.get(z)+"\n");
                
                }
                HTME.write("E"+sappender6(st.get("progstart"),st.get("progstart").length())+"\n");
            } catch (IOException ex) {
                Logger.getLogger(assembler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            objectcode.close();
            
            HTME.close();
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
               List<String> temp_lit = new ArrayList<String>();
               List<String> loccounter = new ArrayList<String>();
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
               OP.put("SUBF", "5C");
               OP.put("SUB", "1C");
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
               OPM.put("A", "0");
               OPM.put("X", "1");
               OPM.put("L", "2");
               OPM.put("B", "3");
               OPM.put("S", "4");
               OPM.put("T", "5");
               OPM.put("F", "6");
               pass1(myfile,ST,OP_1,OP_2,temp_lit,LTORG,loccounter);
               pass2(myfile,OP,ST,OP_1,OP_2,temp_lit,OPM,LTORG,loccounter);
               System.out.println("fin");

	}

}
//to_do kml el address lema yb2a base 
//to_do kml el base fe format 3 men 8yr label 
