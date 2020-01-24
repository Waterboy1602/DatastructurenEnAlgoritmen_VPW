import java.util.Scanner;
import java.util.*;

public class Main {
	static class Metingen {
		String linkerSchaal, rechterSchaal, weegResul;
		
		Metingen(String linkerSchaalC, String rechterSchaalC, String weegResulC){
			this.linkerSchaal=linkerSchaalC;
			this.rechterSchaal=rechterSchaalC;
			this.weegResul=weegResulC;
		}
		
		String getAlleLetters() {
			return this.linkerSchaal + this.rechterSchaal;
		}
		String getLinkerSchaal() {
			return this.linkerSchaal;
		}
		
		String getRechterSchaal() {
			return this.rechterSchaal;
		}
		
		String getWeegResul() {
			return this.weegResul;
		}
		
		boolean vergelijkLinks(Metingen meting) {
			return this.linkerSchaal.equals(meting.getLinkerSchaal());
		}
		
		boolean vergelijkRechts(Metingen meting) {
			return this.rechterSchaal.equals(meting.getRechterSchaal());
		}
		
		boolean vergelijkResul(Metingen meting) {
			return this.weegResul.equals(meting.getWeegResul());
		}
	}
	
	static void berekeningen(Metingen[] data) {
		Set<Character> gebalanceerd = new HashSet<>();
		Set<Character> alleLetters = new HashSet<>();
		
		for(int j=0; j<data.length; j++) {
			boolean evenwicht = (data[j].getWeegResul().equals("evenwicht") ? true : false);
			for(char c : data[j].getAlleLetters().toCharArray()) {
				alleLetters.add(c);
				if(evenwicht) {
					gebalanceerd.add(c);
				}
			}
		}
		
		//twee lijsten van elkaar aftrekken
		Set<Character> overige = new HashSet<>(alleLetters);
		overige.removeAll(gebalanceerd);
		
		if(overige.size()==1) {
			char c = overige.iterator().next();
			for(int i=0; i<data.length; i++) {
				//Als de munt is links en de schaal gaat naar boven ==> zwaarder
				//Of de munt is rechts en de schaal gaat naar beneden ==> zwaarder
				if((data[i].getLinkerSchaal().indexOf(c) >= 0 && data[i].getWeegResul().contentEquals("omhoog")) || (data[i].getRechterSchaal().indexOf(c) >= 0 && data[i].getWeegResul().contentEquals("omlaag"))) {
					System.out.println("Het valse geldstuk " + c + " is zwaarder.");
					return;
				//Als de munt is links en de schaal gaat naar beneden ==> lichter
				//Of de munt is rechts en de schaal gaat naar boven ==> lichter
				} else if((data[i].getLinkerSchaal().indexOf(c) >= 0 && data[i].getWeegResul().contentEquals("omlaag")) || (data[i].getRechterSchaal().indexOf(c) >= 0 && data[i].getWeegResul().contentEquals("omhoog"))) {
					System.out.println("Het valse geldstuk " + c + " is lichter.");
					return;
				}
			}
		} else {
			if(overige.size() > 1) {
				for(Metingen meting1 : data) {
					for(Metingen meting2 : data) {
						//De verschillende gevallen na elkaar uitgeschreven
						if((meting1.vergelijkLinks(meting2) && !meting1.vergelijkResul(meting2)) || (!meting1.vergelijkLinks(meting2) && !meting1.vergelijkRechts(meting2) && meting1.vergelijkResul(meting2))) {
							System.out.println("Inconsistente gegevens.");
							return;
						}
						if((meting1.getLinkerSchaal().equals(meting2.getRechterSchaal()) ^ meting1.getRechterSchaal().equals(meting2.getLinkerSchaal())) && !meting1.vergelijkResul(meting2)) {
							if(meting1.getLinkerSchaal().length() > 1 || meting1.getRechterSchaal().length() > 1 || meting2.getLinkerSchaal().length() > 1 || meting2.getRechterSchaal().length() > 1) {
								System.out.println("Te weinig gegevens.");
								return;
							}
							System.out.println("Het valse geldstuk " + (meting1.getLinkerSchaal().equals(meting2.getRechterSchaal()) ? meting1.getLinkerSchaal() : meting1.getRechterSchaal()) + " is " + (!meting1.vergelijkResul(meting2) ? "lichter" : "zwaarder") + ".");
								return;
						} 
						if((meting1.vergelijkLinks(meting2) ^ meting1.vergelijkRechts(meting2)) && meting1.vergelijkResul(meting2)) {
							System.out.println("Het valse geldstuk " + (meting1.getLinkerSchaal().equals(meting2.getRechterSchaal()) ? meting1.getRechterSchaal() : meting1.getLinkerSchaal()) + " is " + (meting1.vergelijkResul(meting2) ? "zwaarder" : " lichter") + ".");
							return;
						}
					}
				}
			}
			
			//Als er geen munten meer zijn ==> inconsistent
			else if(overige.size() < 1) {
				System.out.println("Inconsistente gegevens.");
				return;
			}
			
		}
	}

	public static void main(String[] args) {
		int aantalPakketten=0;
		int aantalWegingen=0;
		String lijn;
		Scanner sc = new Scanner(System.in);
		
		lijn = sc.nextLine();
		aantalPakketten = Integer.parseInt(lijn);
		for(int i=0; i<aantalPakketten; i++) { // voor aantal muntpaketten			
			lijn = sc.nextLine();
			aantalWegingen = Integer.parseInt(lijn);
			//Onmogelijk te bepalen als er maar 1 weging is
			if(aantalWegingen==1) {
				lijn=sc.nextLine();
				System.out.println("Te weinig gegevens.");
			} else {
				Metingen[] data = new Metingen[aantalWegingen];
				for(int j=0; j<aantalWegingen; j++) {
					lijn=sc.nextLine();	
					//opsplitsen van string/weging
					String[] temp=lijn.split(" ");
					data[j] = new Metingen(temp[0], temp[1], temp[2]);
				}
				berekeningen(data);
			}
		}
		sc.close();
	}
}
