import java.util.*;

class Garfield{
	int oplossing;
	List<int[]> veld;
	boolean[] beschikbaar;
	int minuten;
	
	Garfield(List<int[]> veldC, int minutenC){
		minuten=minutenC;
		veld=veldC;
		oplossing=0;
		beschikbaar = new boolean[veld.size()-1];
		Arrays.fill(beschikbaar, Boolean.TRUE);
	}
	
	int afstand(int pos1, int pos2) {
		return Math.abs(veld.get(pos1)[0] - veld.get(pos2)[0]) + Math.abs(veld.get(pos1)[1] - veld.get(pos2)[1]);
	}
	
	int minimumAfstand(int positie, int teller) { //minimum totale afstand berekenen
		Map<Integer, Integer> vrij = new HashMap<Integer, Integer>();
		
		int dichtste = positie;
		
		for(int i=0; i<veld.size()-1; i++) { //lijst maken met alle nog beschikbare plekken
			if(beschikbaar[i]) {
				vrij.put(i+1, afstand(positie, i+1));
			}
		}
		
		int afstand = teller;
		for(; teller>0; teller--) { //teller tot er geen vissen meer zijn
			//System.out.println(vrij.size());
			int minWaarde = Integer.MAX_VALUE;
			int minSleutel = 0;
			for(int sleutel : vrij.keySet()) { //zoek die met kleinste afstand en verwijder daarna
				int waarde = vrij.get(sleutel);
				if(waarde<minWaarde) {
					minWaarde = waarde;
					minSleutel = sleutel;
				}
			}
			int verplaats = minSleutel;
			afstand += minWaarde;
			vrij.remove(minSleutel);
			
			if(afstand(0, verplaats) < afstand(0, dichtste) ) { 
				dichtste = verplaats;
			}
			
			for(int i : vrij.keySet()) {
				vrij.replace(i, Math.min(vrij.get(i), afstand(verplaats, i)));
			}
			
		}
		
		return afstand + afstand(0, dichtste); 
	}
	
	void oplossen(int positie, int overgeblevenTijd, int etenTeller) { //recursief alles overlopen
		if(oplossing == veld.size()-1) {
			return;
		}
		
		if(minimumAfstand(positie, oplossing-etenTeller+1)>overgeblevenTijd) { //testen of het mogelijk is om op tijd terug te zijn
			return;
		}
		
		oplossing = Math.max(oplossing, etenTeller);
		
		for(int i=0; i<veld.size()-1; i++) {
			if(beschikbaar[i]) {
				beschikbaar[i]=false;
				oplossen(i+1, overgeblevenTijd - afstand(positie, i+1) - 1, etenTeller+1);
				beschikbaar[i]=true;
			}
		}
	}
	
	int GetOplossing() {
		return oplossing;
	}
}

public class Main {
	public static void main(String[] args) {
		List<int[]> veld = new ArrayList<int[]>();
		Scanner sc = new Scanner(System.in);
		int aantalTestgevallen = Integer.parseInt(sc.nextLine());
		for(int i=0; i<aantalTestgevallen; i++) {
			veld.clear();
			String[] temp = sc.nextLine().split(" ");
			int breedte = Integer.parseInt(temp[0]);
			int hoogte = Integer.parseInt(temp[1]);
			int minuten = Integer.parseInt(temp[2]);
			for(int j=0; j<hoogte; j++) {
				String lijn = sc.nextLine();
				for(int w=0; w<breedte; w++) {
					if(lijn.charAt(w)=='G') {
						int[] tijd = new int[2];
						tijd[0]=j;
						tijd[1]=w;
						veld.add(0, tijd);
					}
					if(lijn.charAt(w)=='E') {
						int[] tijd = new int[2];
						tijd[0]=j;
						tijd[1]=w;
						veld.add(tijd);
					}
				}
			}
			
			Garfield garf = new Garfield(veld, minuten);
			garf.oplossen(0, minuten, 0);
			System.out.println(i+1 + " " + garf.GetOplossing());
			
		}
		sc.close();
	}

}
