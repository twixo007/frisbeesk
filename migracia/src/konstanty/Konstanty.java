package konstanty;
import java.util.ArrayList;


public class Konstanty extends CustomKonstanty {
	public final static int maximalnyPocetConnectionov = 100;
	
	public final static String openKategoria = "Open";
	
	
	public final static String vzorTable_turnaje = "turnaje";
	
	public final static String vzorTableTurnaje_id = "id";
	public final static String vzorTableTurnaje_nazov = "turnaj";
	public final static String vzorTableTurnaje_kategoriaId = "kategoria";
	public final static String vzorTableTurnaje_datumOd = "datum_od";
	public final static String vzorTableTurnaje_datumDo = "datum_do";
	public final static String vzorTableTurnaje_vysledok = "vysledok";
	public final static String vzorTableTurnaje_pocetTimov = "pocet_timov";
	public final static String vzorTableTurnaje_timOut = "tim_Out";
	public final static String vzorTableTurnaje_spirit = "spirit";
	public final static String vzorTableTurnaje_report = "report";
	public final static String vzorTableTurnaje_stat = "stat";
	public final static String vzorTableTurnaje_mesto = "mesto";
	public final static String vzorTableTurnaje_datumZapisu = "datum_zapisu";
	
	public final static ArrayList<String> stlpceTabulkyVzor_Turnaje = DajStlpceTurnajaVzor();
	

	private static ArrayList<String> DajStlpceTurnajaVzor() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(vzorTableTurnaje_id);
		vysledok.add(vzorTableTurnaje_nazov);
		vysledok.add(vzorTableTurnaje_kategoriaId);
		vysledok.add(vzorTableTurnaje_datumOd);
		vysledok.add(vzorTableTurnaje_datumDo);
		vysledok.add(vzorTableTurnaje_vysledok);
		vysledok.add(vzorTableTurnaje_pocetTimov);
		vysledok.add(vzorTableTurnaje_timOut);
		vysledok.add(vzorTableTurnaje_spirit);
		vysledok.add(vzorTableTurnaje_report);
		vysledok.add(vzorTableTurnaje_stat);
		vysledok.add(vzorTableTurnaje_mesto);
		vysledok.add(vzorTableTurnaje_datumZapisu);
		return vysledok;
	}
	
	public final static String vzorTable_hraci = "hraci";
	
	public final static String vzorTableHraci_id = "id";
	public final static String vzorTableHraci_krstne = "meno";
	public final static String vzorTableHraci_priezvisko = "priezvisko";
	public final static String vzorTableHraci_prezyvka = "prezyvka";
	public final static String vzorTableHraci_profil = "profil";
	public final static String vzorTableHraci_externe = "externe";
	public final static String vzorTableHraci_domaciTim = "domaci_tim";
	public final static String vzorTableHraci_poznamka = "poznamka";
	public final static String vzorTableHraci_foto = "foto";
	
	public final static ArrayList<String> stlpceTabulkyVzor_Hraci = DajStlpceHracaVzor();


	private static ArrayList<String> DajStlpceHracaVzor() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(vzorTableHraci_id);
		vysledok.add(vzorTableHraci_krstne);
		vysledok.add(vzorTableHraci_priezvisko);
		vysledok.add(vzorTableHraci_prezyvka);
		vysledok.add(vzorTableHraci_profil);
		vysledok.add(vzorTableHraci_externe);
		vysledok.add(vzorTableHraci_domaciTim);
		vysledok.add(vzorTableHraci_poznamka);
		vysledok.add(vzorTableHraci_foto);
		return vysledok;
	}
	
	public final static String vzorTable_kategorie = "kategorie";
	
	public final static String vzorTableKategorie_id = "id";
	public final static String vzorTableKategorie_nazov = "kategoria";
	
	public final static ArrayList<String> stlpceTabulkyVzor_Kategorie = DajStlpceKategorieVzor();


	private static ArrayList<String> DajStlpceKategorieVzor() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(vzorTableKategorie_id);
		vysledok.add(vzorTableKategorie_nazov);
		return vysledok;
	}
	
	public final static String vzorTable_zostavy = "zostavy";
	
	public final static String vzorTableZostavy_id = "id";
	public final static String vzorTableZostavy_turnajId = "turnaj";
	public final static String vzorTableZostavy_hracId = "hrac";
	
	public final static ArrayList<String> stlpceTabulkyVzor_Zostavy = DajStlpceZostavyVzor();


	private static ArrayList<String> DajStlpceZostavyVzor() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(vzorTableZostavy_id);
		vysledok.add(vzorTableZostavy_turnajId);
		vysledok.add(vzorTableZostavy_hracId);
		return vysledok;
	}
	
	
	//FRISBEE
	
	public final static String frisbeeTable_turnaje = "frisbee_turnaj";
	
	public final static String frisbeeTableTurnaje_id = "id";
	public final static String frisbeeTableTurnaje_nazov = "nazov";
	public final static String frisbeeTableTurnaje_datumOd = "datum_od";
	public final static String frisbeeTableTurnaje_datumDo = "datum_do";
	public final static String frisbeeTableTurnaje_mesto = "mesto";
	public final static String frisbeeTableTurnaje_stat = "stat";
	public final static String frisbeeTableTurnaje_datumZapisu = "datum_zapisu";
	public final static String frisbeeTableTurnaje_report = "report";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_Turnaje = DajStlpceTurnajaFrisbee();
	public final static String[] stlpceTabulkyFrisbee_TurnajeString = {frisbeeTableTurnaje_nazov, frisbeeTableTurnaje_datumOd,
		frisbeeTableTurnaje_datumDo, frisbeeTableTurnaje_mesto, frisbeeTableTurnaje_stat, frisbeeTableTurnaje_datumZapisu,
		frisbeeTableTurnaje_report};
	public final static String[] stlpceTabulkyFrisbee_TurnajeNonString = {frisbeeTableTurnaje_id};

	private static ArrayList<String> DajStlpceTurnajaFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableTurnaje_id);
		vysledok.add(frisbeeTableTurnaje_nazov);
		vysledok.add(frisbeeTableTurnaje_datumOd);
		vysledok.add(frisbeeTableTurnaje_datumDo);
		vysledok.add(frisbeeTableTurnaje_mesto);
		vysledok.add(frisbeeTableTurnaje_stat);
		vysledok.add(frisbeeTableTurnaje_datumZapisu);
		vysledok.add(frisbeeTableTurnaje_report);
		return vysledok;
	}
	
	public final static String frisbeeTable_kategorie = "frisbee_kategoria";
	
	public final static String frisbeeTableKategorie_id = "id";
	public final static String frisbeeTableKategorie_nazov = "nazov";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_Kategorie = DajStlpceKategorieFrisbee();
	public final static String[] stlpceTabulkyFrisbee_KategorieString = {frisbeeTableKategorie_nazov};
	public final static String[] stlpceTabulkyFrisbee_KategorieNonString = {frisbeeTableKategorie_id};
	
	private static ArrayList<String> DajStlpceKategorieFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableKategorie_id);
		vysledok.add(frisbeeTableKategorie_nazov);
		return vysledok;
	}
	
	public final static String frisbeeTable_kategoriaTurnaju = "frisbee_kategoriaturnaju";
	
	public final static String frisbeeTableKategoriaTurnaju_id = "id";
	public final static String frisbeeTableKategoriaTurnaju_kategoriaId = "kategoria_id";
	public final static String frisbeeTableKategoriaTurnaju_turnajId = "turnaj_id";
	public final static String frisbeeTableKategoriaTurnaju_pocetTimov = "pocet_timov";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_KategorieTurnaju = DajStlpceKategorieTurnajuFrisbee();
	public final static String[] stlpceTabulkyFrisbee_kategoriaTurnajuString = {};
	public final static String[] stlpceTabulkyFrisbee_kategoriaTurnajuNonString = {frisbeeTableKategoriaTurnaju_id, frisbeeTableKategoriaTurnaju_kategoriaId,
		frisbeeTableKategoriaTurnaju_turnajId, frisbeeTableKategoriaTurnaju_pocetTimov};
			
	private static ArrayList<String> DajStlpceKategorieTurnajuFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableKategoriaTurnaju_id);
		vysledok.add(frisbeeTableKategoriaTurnaju_kategoriaId);
		vysledok.add(frisbeeTableKategoriaTurnaju_turnajId);
		vysledok.add(frisbeeTableKategoriaTurnaju_pocetTimov);
		return vysledok;
	}
	
	public final static String frisbeeTable_hrac = "frisbee_hrac";
	
	public final static String frisbeeTableHrac_id = "id";
	public final static String frisbeeTableHrac_oldId = "old_id";
	public final static String frisbeeTableHrac_krstneMeno = "krstne_meno";
	public final static String frisbeeTableHrac_priezvisko = "priezvisko";
	public final static String frisbeeTableHrac_prezivka = "prezivka";
	public final static String frisbeeTableHrac_foto = "foto";
	public final static String frisbeeTableHrac_poznamka = "poznamka";
	public final static String frisbeeTableHrac_klubId = "klub_id";
	public final static String frisbeeTableHrac_uzivatelId = "uzivatel_id";
	public final static String frisbeeTableHrac_pohlavie = "pohlavie";
	public final static String frisbeeTableHrac_datumNarodenia = "datum_narodenia";
	public final static String frisbeeTableHrac_miestoBydliska = "miesto_bydliska";
	public final static String frisbeeTableHrac_telefonneCislo = "telefonne_cislo";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_Hrac = DajStlpceHracFrisbee();
	public final static String[] stlpceTabulkyFrisbee_hracString = {frisbeeTableHrac_krstneMeno,
		frisbeeTableHrac_priezvisko, frisbeeTableHrac_prezivka, frisbeeTableHrac_foto,
		frisbeeTableHrac_poznamka, frisbeeTableHrac_pohlavie, frisbeeTableHrac_datumNarodenia,
		frisbeeTableHrac_miestoBydliska, frisbeeTableHrac_telefonneCislo};
	public final static String[] stlpceTabulkyFrisbee_hracNonString = {frisbeeTableHrac_id,
		frisbeeTableHrac_oldId, frisbeeTableHrac_klubId, frisbeeTableHrac_uzivatelId};
			
	private static ArrayList<String> DajStlpceHracFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableHrac_id);
		vysledok.add(frisbeeTableHrac_oldId);
		vysledok.add(frisbeeTableHrac_krstneMeno);
		vysledok.add(frisbeeTableHrac_priezvisko);
		vysledok.add(frisbeeTableHrac_prezivka);
		vysledok.add(frisbeeTableHrac_foto);
		vysledok.add(frisbeeTableHrac_poznamka);
		vysledok.add(frisbeeTableHrac_klubId);
		vysledok.add(frisbeeTableHrac_uzivatelId);
		vysledok.add(frisbeeTableHrac_pohlavie);
		vysledok.add(frisbeeTableHrac_datumNarodenia);
		vysledok.add(frisbeeTableHrac_miestoBydliska);
		vysledok.add(frisbeeTableHrac_telefonneCislo);
		return vysledok;
	}
	
	
	public final static String frisbeeTable_tim = "frisbee_tim";
	
	public final static String frisbeeTableTim_id = "id";
	public final static String frisbeeTableTim_nazov = "nazov";
	public final static String frisbeeTableTim_klubId = "klub_id";
	public final static String frisbeeTableTim_umiestnenie = "umiestnenie";
	public final static String frisbeeTableTim_kategoriaTrunajuId = "kategoria_turnaju_id";
	public final static String frisbeeTableTim_spirit = "spirit";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_Tim = DajStlpceTimFrisbee();
	public final static String[] stlpceTabulkyFrisbee_TimString = {frisbeeTableTim_nazov};
	public final static String[] stlpceTabulkyFrisbee_TimNonString = {frisbeeTableTim_id,
		frisbeeTableTim_klubId, frisbeeTableTim_umiestnenie, frisbeeTableTim_kategoriaTrunajuId,
		frisbeeTableTim_spirit};
			
	private static ArrayList<String> DajStlpceTimFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableTim_id);
		vysledok.add(frisbeeTableTim_nazov);
		vysledok.add(frisbeeTableTim_klubId);
		vysledok.add(frisbeeTableTim_umiestnenie);
		vysledok.add(frisbeeTableTim_kategoriaTrunajuId);
		vysledok.add(frisbeeTableTim_spirit);
		return vysledok;
	}
	
public final static String frisbeeTable_hracTimu = "frisbee_hractimu";
	
	public final static String frisbeeTableHracTim_id = "id";
	public final static String frisbeeTableHracTim_hracId = "hrac_id";
	public final static String frisbeeTableHracTim_timId = "tim_id";
	
	public final static ArrayList<String> stlpceTabulkyFrisbee_HracTim = DajStlpceHracTimFrisbee();
	public final static String[] stlpceTabulkyFrisbee_HracTimString = {frisbeeTableHracTim_id,
		frisbeeTableHracTim_hracId, frisbeeTableHracTim_timId};
	public final static String[] stlpceTabulkyFrisbee_HracTimNonString = {};
			
	private static ArrayList<String> DajStlpceHracTimFrisbee() {
		ArrayList<String> vysledok = new ArrayList<String>();
		vysledok.add(frisbeeTableHracTim_id);
		vysledok.add(frisbeeTableHracTim_hracId);
		vysledok.add(frisbeeTableHracTim_timId);
		return vysledok;
	}
}
