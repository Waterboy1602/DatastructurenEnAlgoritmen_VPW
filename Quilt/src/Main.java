//beter opnieuw beginnen met een 2D array


import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException{
		Stack<String[]> stapel = new Stack<String[]>();
		Map<Character,Character> draaiMap = new HashMap<Character,Character>();
		draaiMap.put('/','\\');
		draaiMap.put('\\','/');
		draaiMap.put('+','+');
		draaiMap.put('-','|');
		draaiMap.put('|','-');
		
		String lijn;
		Scanner sc = new Scanner(System.in);
		lijn = sc.nextLine();
		int aantalOpgaven = Integer.parseInt(lijn);
		
		//start loop aantal opgaven
		for(int i=0; i<aantalOpgaven; i++) {
			lijn=sc.nextLine();
			
			int aantalBasisPatronen = Integer.parseInt(lijn);
			String[][] patronen = new String[aantalBasisPatronen][]; //eerste index == welk patroon
			for(int j=0; j<aantalBasisPatronen; j++) { 
				patronen[j] = new String[2];						//elke basispatroon bestaat uit twee regels
				patronen[j][0] = sc.nextLine(); 					//volledige regel tekens in een positie
				patronen[j][1] = sc.nextLine();
			}
			lijn=sc.nextLine();
			
			//start loop aantal bevelen
			int aantalBevelen=Integer.parseInt(lijn);
			for(int j=0; j<aantalBevelen;j++) {
				String bevel =sc.nextLine();
				
				if(bevel.equals("naai")) {
					String[] eerstePatr = stapel.pop();
					String[] tweedePatr = stapel.pop();
					int lengte = eerstePatr.length;
					String[] nieuwPatr = new String[lengte];
					for(int w=0; w<nieuwPatr.length; w++) {					
						nieuwPatr[w]="";									//is nodig anders kunnen we straks niet +=
					}
					for(int w=0; w<nieuwPatr.length; w++) {
						nieuwPatr[w] += eerstePatr[w] + tweedePatr[w];
					}
					stapel.push(nieuwPatr);
					
					
				} else if(bevel.equals("draai")) {
					String[] bovenstePatr = stapel.pop();
					String[] nieuwPatr = new String[bovenstePatr[0].length()]; //transponeren: aantal kolommen worden aantal rijen (en omgekeerd)
					for(int x=0; x<bovenstePatr[0].length(); x++) {
						String nieuw="";
						for(int w=0; w<bovenstePatr.length;w++) {
							nieuw+= draaiMap.get(bovenstePatr[bovenstePatr.length-1-w].charAt(x)); //vervang teken met vooraf ingesteld equivalent
						}
						nieuwPatr[x]=nieuw;
					}
					stapel.push(nieuwPatr);
					
					
				} else if(bevel.equals("teken")) {
					String [] bovenstePatr = stapel.peek();
					for(String s : bovenstePatr) { //voor elke string s in bovenstePatr --> uitprinten
						System.out.println(s);
					}
					System.out.println();
					
					
				} else if(bevel.equals("stop")) {
					//doe niets
					
					
				} else {
					int welkeOpStapel = Integer.parseInt(bevel);
					stapel.push(patronen[welkeOpStapel-1]); //min een want is beginnen tellen bij nul
					
				}
			}
			
		}
	sc.close();
	}
}
