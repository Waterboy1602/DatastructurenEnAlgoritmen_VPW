//alle mogelijke combinaties eerst generen voor recursie uitvoeren
import java.util.*;

class Dwergen {
	static boolean gevonden;
	static int aantalDiamanten;
	static int maxAantalDiamanten;
	static List<int[]> diamanten;
	static int rekening;
	static int aantalDwergen;
	static int tijdelijkeSom;
	static int oplossing;
	static int start;
	static List<List<Integer>> permutaties;
	static List<Integer> tijdPerm;
	
	public void inlezen() {
		String lijn;
		Scanner sc = new Scanner(System.in);
		lijn = sc.nextLine();
		int aantalTestgevallen = Integer.parseInt(lijn);
        //start inlezen
		for(int i=0; i<aantalTestgevallen; i++) {
	        gevonden=false;
			aantalDiamanten=0;
			maxAantalDiamanten=20;
			diamanten = new ArrayList<int[]>();
			permutaties = new ArrayList<List<Integer>>();
			lijn = sc.nextLine();
			rekening = Integer.parseInt(lijn);
			lijn = sc.nextLine();
			aantalDwergen = Integer.parseInt(lijn);
			for(int j=0; j<aantalDwergen; j++) {
				lijn=sc.nextLine();
				aantalDiamanten=Integer.parseInt(lijn);
				if(aantalDiamanten<maxAantalDiamanten) {
					maxAantalDiamanten=aantalDiamanten;
				}
				int[] tijd = new int[aantalDiamanten];
				lijn=sc.nextLine();
				String[] temp=lijn.split(" ");
				for(int k=0; k<aantalDiamanten; k++) {
					tijd[k] = Integer.parseInt(temp[k]);
				}
				diamanten.add(tijd);
			}
			
			if(aantalDwergen<rekening) {
				for(int multipliciteit=1; multipliciteit<=maxAantalDiamanten && !gevonden; multipliciteit++) {
					if(multipliciteit==1) {
						for(int k=0; k<diamanten.size(); k++) {
							tijdPerm = new ArrayList<Integer>();
							for(int w=0; w<diamanten.get(k).length; w++) {
								tijdPerm.add(diamanten.get(k)[w]);
							}
							permutaties.add(tijdPerm);
						}
					} else {
						for(int j=0; j<diamanten.size(); j++) {
							tijdPerm = new ArrayList<Integer>();
							permutaties(multipliciteit, j, 0, 0, 0);
							permutaties.add(tijdPerm);
						}
					}
				
					if(!gevonden) {
						tijdelijkeSom=0;
						recursie(0, 0, tijdelijkeSom);
						permutaties.clear();
					}
					if(gevonden) {
						oplossing=multipliciteit;				
					}
					permutaties.clear();
				}
			}
			
			if(!gevonden) {
				System.out.println(i+1 + " ONMOGELIJK");
			} else {
				System.out.println(i+1 + " " + oplossing);
			}
		}
		sc.close();
	}
	
	void permutaties(int multipliciteit, int dwerg, int positie, int som, int tijdMult) {
		if(multipliciteit == tijdMult) {
			if(!tijdPerm.contains(som)) {
				tijdPerm.add(som);
			}
			tijdMult--;
			return;
		}
		
		for(int i=positie; i<diamanten.get(dwerg).length; i++) {
			int tijdPos = positie;
			som+=diamanten.get(dwerg)[i];
			tijdMult++;
			permutaties(multipliciteit, dwerg, i+1, som, tijdMult);
			positie = tijdPos;
			som-=diamanten.get(dwerg)[i];
			tijdMult--;
		}
		return;
	}

	public void recursie(int start, int teller, int tijdelijkeSom) {
		if(start==permutaties.size()) {
			return;
		}
		
		while(teller<permutaties.get(start).size() && !gevonden) {
			int vorigeTijdSom=tijdelijkeSom;
			int vorigeTeller=teller;
			
			tijdelijkeSom+=permutaties.get(start).get(teller);
			if(tijdelijkeSom==rekening && start == aantalDwergen-1) {
				gevonden=true;
			} else {
				start++;
				recursie(start, 0, tijdelijkeSom);
				start--;
				teller=vorigeTeller;
				tijdelijkeSom=vorigeTijdSom;
			}
			teller++;
		}
		return;
	}
}

public class Main {
	public static void main(String[] args) {
			Dwergen dwerg = new Dwergen();
			dwerg.inlezen();
	}
}
