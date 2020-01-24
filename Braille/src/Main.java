import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		int aantalTekensOpLijn=0;
		String lijn;
		char[][] matrix;
		Map<String,Character> brailleAlfabet = new HashMap<String,Character>();
		Scanner sc = new Scanner(System.in);
		lijn = sc.nextLine();
		matrix = new char[3][52];
		for(int k=0; k<3; k++) {
			for(int w=0; w<52; w++) {
				matrix[k][w]=lijn.charAt(w);
			}
			lijn=sc.nextLine();
		}
		int letter = 65;
		for(int j=0; j<52; j+=2) {
			String tijd = null;
			tijd+=matrix[0][j];
			tijd+=matrix[0][j+1];
			tijd+=matrix[1][j];
			tijd+=matrix[1][j+1];
			tijd+=matrix[2][j];
			tijd+=matrix[2][j+1];
			brailleAlfabet.put(tijd,(char) letter);
			letter++;
		}
		
		int aantalLijnenTekst = Integer.parseInt(lijn);
		
		
		
		for(int x=1; x<=aantalLijnenTekst; x++) { //Starten bij een: handig for later bij schrijven cijfer van lijnregel
			System.out.print(x + " ");
			
			lijn = sc.nextLine();
			aantalTekensOpLijn = lijn.length();
			matrix = new char[3][aantalTekensOpLijn];
			
			for(int k=0; k<3; k++) {
				if(k!=0) {
					lijn = sc.nextLine();
				}
				for(int j=0; j<aantalTekensOpLijn; j++) {
					matrix[k][j]=lijn.charAt(j);
				}
			}
			for(int j=0; j<aantalTekensOpLijn; j+=2) {
				String tijd = null;
				tijd+=matrix[0][j];
				tijd+=matrix[0][j+1];
				tijd+=matrix[1][j];
				tijd+=matrix[1][j+1];
				tijd+=matrix[2][j];
				tijd+=matrix[2][j+1];
				System.out.print(String.valueOf(brailleAlfabet.get(tijd)));
			}
			System.out.println();
		}
		sc.close();
	}
}


