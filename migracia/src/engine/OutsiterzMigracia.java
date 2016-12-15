package engine;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;
import java.util.HashSet;

import connection.Database;
import connection.SpracovanieMapper;
import konstanty.Konstanty;
import models.DefaultModel;
import models.frisbee.HracFrisbeeModel;
import models.frisbee.HracTimuFrisbeeModel;
import models.frisbee.KategoriaFrisbeeModel;
import models.frisbee.KategoriaTurnajuFrisbeeModel;
import models.frisbee.TimFrisbeeModel;
import models.frisbee.TurnajFrisbeeModel;
import models.vzor.HracVzorModel;
import models.vzor.KategoriaVzorModel;
import models.vzor.TurnajVzorModel;
import models.vzor.ZostavaVzorModel;


public class OutsiterzMigracia {
	private ArrayList<TurnajVzorModel> vsetkyTurnaje;
	private ArrayList<HracVzorModel> vsetciHraci;
	private ArrayList<KategoriaVzorModel> vsetkyKategorie;
	private ArrayList<ZostavaVzorModel> vsetkyZostavy;
	
	private Collection<DefaultModel> hraciFrisbee;
	private Collection<DefaultModel> turnajeFrisbee;
	private Collection<DefaultModel> kategorieFrisbee;
	private Collection<DefaultModel> kategorieTurnajovFrisbee;
	private Collection<DefaultModel> timiFrisbee;
	private Collection<DefaultModel> hraciTimovFrisbee;
	
	private SpracovanieMapper maper;
	
	private OutsiterzMigracia(){
		this.maper = new SpracovanieMapper();
	}

	public static void main(String[] args) throws Exception{
		new OutsiterzMigracia().NamapujUdajeDoNovejDb();
	}

	private void NamapujUdajeDoNovejDb() throws Exception {
		PreProcess();
		StiahniExistujuceUdaje();
		PripravHracov();
		VlozHracov();
		VytiahniVlozeneData___Frisbee___Hracov();
		PripravKategorie();
		VlozKategorie();
		PripravNoveTurnaje();
		VlozTurnaje();
		VytiahniVlozeneData___Frisbee___Turnaje_Kategorie();
		PripravNoveKategorieTurnajov();
		VlozKategorieTurnaju();
		VytiahniVlozeneData___Frisbee___KategorieTurnajov();
		PripravTimi();
		VlozTimi();
		VytiahniVlozeneData___Frisbee___Timi();
		PripravHracovTimov();
		VlozHracovTimov();
	}

	private void PreProcess() throws SQLException, ClassNotFoundException {
		maper.execute(String.format("ALTER TABLE %s MODIFY COLUMN %s date null", Konstanty.vzorTable_turnaje, Konstanty.vzorTableTurnaje_datumDo));
		maper.executeUpdate(String.format("UPDATE %s SET %s=NULL WHERE %s='0000-00-00';", Konstanty.vzorTable_turnaje, Konstanty.vzorTableTurnaje_datumDo, Konstanty.vzorTableTurnaje_datumDo));
	}

	private void PripravHracovTimov() {
		hraciTimovFrisbee = new HashSet<DefaultModel>();
		for (ZostavaVzorModel hracTurnaju : vsetkyZostavy){
			try{
				int idTurnajuVzor = Integer.parseInt((String) hracTurnaju.getHodnotuPreColumn(Konstanty.vzorTableZostavy_turnajId));
				int idHracVzor = Integer.parseInt((String) hracTurnaju.getHodnotuPreColumn(Konstanty.vzorTableZostavy_hracId));
				HracVzorModel hracVzor = getVzorHracaPodlaId(idHracVzor);
				TurnajVzorModel turnajVzor = getVzorTurnajPodlaId(idTurnajuVzor);
				TurnajFrisbeeModel turnajFrisbee = getFrisbeeTrunajZoVzorTurnaj(turnajVzor);
				HracFrisbeeModel hracFrisbee = getFrisbeeHracZoVzorHrac(hracVzor);
				
				Object obj = turnajVzor.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_kategoriaId);
				
				KategoriaFrisbeeModel kategoriaFrisbee;
				String nazovKategorie;
				if (obj == null){
					kategoriaFrisbee = getFrisbeeOpenKategoria(Konstanty.openKategoria);
					nazovKategorie = Konstanty.openKategoria;
				}else{
					KategoriaVzorModel kategoriaVzor = this.NajdiKategoriuVzorPodlaId(Integer.parseInt((String) obj));
					kategoriaFrisbee = this.NajdiKategoriuVzorPodlaVzorKategorie(kategoriaVzor);
					nazovKategorie = (String) kategoriaVzor.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov);
				}
				
				
				KategoriaTurnajuFrisbeeModel kategoriaTurnajuFrisbee = getFrisbeeKategoriaTurnaju(kategoriaFrisbee, turnajFrisbee);
				
				
				TimFrisbeeModel hladanyTim = new TimFrisbeeModel();
				hladanyTim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_kategoriaTrunajuId, kategoriaTurnajuFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableKategoriaTurnaju_id));
				hladanyTim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_spirit, turnajFrisbee.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_spirit));
				hladanyTim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_nazov, (String) turnajFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_nazov)
						+ "_" + nazovKategorie + "_tim");
				for (DefaultModel team : timiFrisbee){
					TimFrisbeeModel timFrisbee = (TimFrisbeeModel) team;
					if (hladanyTim.equals(timFrisbee)){
						HracTimuFrisbeeModel hracTimu = new HracTimuFrisbeeModel();
						hracTimu.nastavPreColumnHodnotu(Konstanty.frisbeeTableHracTim_hracId, hracFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableHrac_id));
						hracTimu.nastavPreColumnHodnotu(Konstanty.frisbeeTableHracTim_timId, timFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableTim_id));
						hraciTimovFrisbee.add(hracTimu);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void VlozHracovTimov() throws ClassNotFoundException, SQLException {
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_hracTimu, Konstanty.stlpceTabulkyFrisbee_HracTimString,  Konstanty.stlpceTabulkyFrisbee_HracTimNonString, hraciTimovFrisbee);
	}

	private void PripravTimi() {
		timiFrisbee = new HashSet<DefaultModel>();
		for (ZostavaVzorModel hracTurnaju : vsetkyZostavy){
			try{
				int idTurnajuVzor = Integer.parseInt((String) hracTurnaju.getHodnotuPreColumn(Konstanty.vzorTableZostavy_turnajId));
				TurnajVzorModel turnajVzor = getVzorTurnajPodlaId(idTurnajuVzor);
				TurnajFrisbeeModel turnajFrisbee = getFrisbeeTrunajZoVzorTurnaj(turnajVzor);
				
				Object obj = turnajVzor.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_kategoriaId);
				
				KategoriaFrisbeeModel kategoriaFrisbee;
				String nazovKategorie;
				if (obj == null){
					kategoriaFrisbee = getFrisbeeOpenKategoria(Konstanty.openKategoria);
					nazovKategorie = Konstanty.openKategoria;
				}else{
					KategoriaVzorModel kategoriaVzor = this.NajdiKategoriuVzorPodlaId(Integer.parseInt((String) obj));
					kategoriaFrisbee = this.NajdiKategoriuVzorPodlaVzorKategorie(kategoriaVzor);
					nazovKategorie = (String) kategoriaVzor.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov);
				}
				
				
				KategoriaTurnajuFrisbeeModel kategoriaTurnajuFrisbee = getFrisbeeKategoriaTurnaju(kategoriaFrisbee, turnajFrisbee);
				
				
				TimFrisbeeModel tim = new TimFrisbeeModel();
				tim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_kategoriaTrunajuId, kategoriaTurnajuFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableKategoriaTurnaju_id));
				tim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_spirit, turnajVzor.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_spirit));
				tim.nastavPreColumnHodnotu(Konstanty.frisbeeTableTim_nazov, (String) turnajFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_nazov)
						+ "_" + nazovKategorie + "_tim");
				timiFrisbee.add(tim);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void VlozTimi() throws ClassNotFoundException, SQLException{
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_tim, Konstanty.stlpceTabulkyFrisbee_TimString,  Konstanty.stlpceTabulkyFrisbee_TimNonString, timiFrisbee);
	}
	
	private void VytiahniVlozeneData___Frisbee___Timi() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		timiFrisbee = maper.getHodnoty(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_tim, Konstanty.stlpceTabulkyFrisbee_Tim, DefaultModel.class, TimFrisbeeModel.class);
		timiFrisbee = PrerobDoSetu(timiFrisbee);
	}

	

	private KategoriaTurnajuFrisbeeModel getFrisbeeKategoriaTurnaju(
			KategoriaFrisbeeModel kategoriaFrisbee,
			TurnajFrisbeeModel turnajFrisbee) throws Exception {
		for (DefaultModel kategoriaTurnaju: kategorieTurnajovFrisbee){
			if (kategoriaTurnaju.getHodnotuPreColumn(Konstanty.frisbeeTableKategoriaTurnaju_kategoriaId).equals(kategoriaFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_id))&&
					kategoriaTurnaju.getHodnotuPreColumn(Konstanty.frisbeeTableKategoriaTurnaju_turnajId).equals(turnajFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_id))){
				return (KategoriaTurnajuFrisbeeModel) kategoriaTurnaju;
			}
		}
		throw new Exception("KategoriaTurnaju sa nenasla hladalo sa kategoria =" + kategoriaFrisbee + " turnaj =" + turnajFrisbee);
	}

	private HracFrisbeeModel getFrisbeeHracZoVzorHrac(HracVzorModel hracVzor) throws Exception {
		HracFrisbeeModel hracFrisbee = new HracFrisbeeModel();
		hracFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_prezivka, hracVzor.getHodnotuPreColumn(Konstanty.vzorTableHraci_prezyvka));
		
		for (DefaultModel hrac : hraciFrisbee){
			if (hrac.equals(hracFrisbee)){
				hracFrisbee = (HracFrisbeeModel) hrac;
				//System.out.println(turnajFrisbee);
				return hracFrisbee;
			}
		}
		throw new Exception("Hrac sa nenasiel, hladany = " + hracFrisbee.toString());
	}

	private TurnajVzorModel getVzorTurnajPodlaId(int idTurnajuVzor) throws Exception {
		for (TurnajVzorModel vzorTurnaj :vsetkyTurnaje){
			if (Integer.parseInt((String) vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_id)) == idTurnajuVzor){
				return vzorTurnaj;
			}
		}
		throw new Exception("Nenasiel sa turnaj podla id=" + idTurnajuVzor);
	}

	private HracVzorModel getVzorHracaPodlaId(int idHracVzor) throws Exception {
		for (HracVzorModel vzorHrac :vsetciHraci){
			if (Integer.parseInt((String) vzorHrac.getHodnotuPreColumn(Konstanty.vzorTableHraci_id)) == idHracVzor){
				return vzorHrac;
			}
		}
		throw new Exception("Nenaslo sa vzor hrac podla id=" + idHracVzor);
	}

	private void PripravHracov() {
		this.hraciFrisbee = new HashSet<DefaultModel>();
		for (HracVzorModel vzorKategoria: vsetciHraci){
			HracFrisbeeModel frisbeeHrac = new HracFrisbeeModel();
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_oldId, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_id));
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_krstneMeno, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_krstne));
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_priezvisko, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_priezvisko));
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_prezivka, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_prezyvka));
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_foto, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_foto));
			frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_poznamka, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableHraci_poznamka));
			while (hraciFrisbee.add(frisbeeHrac) == false){
				frisbeeHrac.nastavPreColumnHodnotu(Konstanty.frisbeeTableHrac_prezivka, frisbeeHrac.getHodnotuPreColumn(Konstanty.frisbeeTableHrac_prezivka) + "1");
			}
		}
	}
	
	private void VlozHracov() throws ClassNotFoundException, SQLException {
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_hrac, Konstanty.stlpceTabulkyFrisbee_hracString,  Konstanty.stlpceTabulkyFrisbee_hracNonString, hraciFrisbee);
	}
	
	private void VytiahniVlozeneData___Frisbee___Hracov() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		hraciFrisbee = maper.getHodnoty(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_hrac, Konstanty.stlpceTabulkyFrisbee_Hrac, DefaultModel.class, HracFrisbeeModel.class);
		hraciFrisbee = PrerobDoSetu(hraciFrisbee);
	}

	private void VytiahniVlozeneData___Frisbee___KategorieTurnajov() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		kategorieTurnajovFrisbee = maper.getHodnoty(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_kategoriaTurnaju, Konstanty.stlpceTabulkyFrisbee_KategorieTurnaju, DefaultModel.class, KategoriaTurnajuFrisbeeModel.class);
	}

	private void VytiahniVlozeneData___Frisbee___Turnaje_Kategorie() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		turnajeFrisbee = maper.getHodnoty(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_turnaje, Konstanty.stlpceTabulkyFrisbee_Turnaje, DefaultModel.class, TurnajFrisbeeModel.class);
		kategorieFrisbee = maper.getHodnoty(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_kategorie, Konstanty.stlpceTabulkyFrisbee_Kategorie, DefaultModel.class, KategoriaFrisbeeModel.class);
		turnajeFrisbee = PrerobDoSetu(turnajeFrisbee);
		kategorieFrisbee = PrerobDoSetu(kategorieFrisbee);
	}

	private Collection<DefaultModel> PrerobDoSetu(Collection<DefaultModel> zoznam) {
		HashSet<DefaultModel> vysledok = new HashSet<DefaultModel>();
		for (DefaultModel udaj : zoznam){
			vysledok.add(udaj);
		}
		//System.out.println(zoznam.size());
		//System.out.println(vysledok.size());
		return vysledok;
	}

	private void VlozKategorie() throws ClassNotFoundException, SQLException {
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_kategorie, Konstanty.stlpceTabulkyFrisbee_KategorieString,  Konstanty.stlpceTabulkyFrisbee_KategorieNonString, kategorieFrisbee);
	}
	
	private void VlozTurnaje() throws ClassNotFoundException, SQLException {
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_turnaje, Konstanty.stlpceTabulkyFrisbee_TurnajeString,  Konstanty.stlpceTabulkyFrisbee_TurnajeNonString, turnajeFrisbee);
	}
	
	private void VlozKategorieTurnaju() throws ClassNotFoundException, SQLException {
		maper.vlozUdaje(Konstanty.databaza_Frisbee, Konstanty.frisbeeTable_kategoriaTurnaju, Konstanty.stlpceTabulkyFrisbee_kategoriaTurnajuString,  Konstanty.stlpceTabulkyFrisbee_kategoriaTurnajuNonString, kategorieTurnajovFrisbee);
		
	}

	private void PripravNoveKategorieTurnajov() throws Exception {
		this.kategorieTurnajovFrisbee = new HashSet<DefaultModel>();
		KategoriaFrisbeeModel openFrisbee = getFrisbeeOpenKategoria(Konstanty.openKategoria);
		for (TurnajVzorModel vzorTurnaj: vsetkyTurnaje){
			Object obj = vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_kategoriaId);
			if (obj == null){
				TurnajFrisbeeModel turnajFrisbee = getFrisbeeTrunajZoVzorTurnaj(vzorTurnaj);
				PriradKategoriu(turnajFrisbee, openFrisbee, vzorTurnaj);
			}else{
				int idKategorieVzor = Integer.parseInt((String) obj);
				KategoriaVzorModel kategoriaVzor = NajdiKategoriuVzorPodlaId(idKategorieVzor);
				KategoriaFrisbeeModel kategoriaFrisbee = NajdiKategoriuVzorPodlaVzorKategorie(kategoriaVzor);
				TurnajFrisbeeModel turnajFrisbee = getFrisbeeTrunajZoVzorTurnaj(vzorTurnaj);
				PriradKategoriu(turnajFrisbee, kategoriaFrisbee, vzorTurnaj);
			}
		}
	}

	private KategoriaFrisbeeModel NajdiKategoriuVzorPodlaVzorKategorie(
			KategoriaVzorModel kategoriaVzor) throws Exception {
		for (DefaultModel kategoriaFrisbee: kategorieFrisbee){
			if (kategoriaFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_nazov).equals(kategoriaVzor.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov))){
				return (KategoriaFrisbeeModel) kategoriaFrisbee;
			}
		}
		throw new Exception("Nenaslo sa frisbee kategoria podla vzorKategoria=" + kategoriaVzor);
	}

	private KategoriaVzorModel NajdiKategoriuVzorPodlaId(int idKategorieVzor) throws Exception {
		for (KategoriaVzorModel kategoriaVzor :vsetkyKategorie){
			if (Integer.parseInt((String) kategoriaVzor.getHodnotuPreColumn(Konstanty.vzorTableKategorie_id)) == (idKategorieVzor)){
				return kategoriaVzor;
			}
		}
		throw new Exception("Nenaslo sa vzor kategorie podla id=" + idKategorieVzor);
	}

	private void PriradKategoriu(TurnajFrisbeeModel turnajFrisbee,
			KategoriaFrisbeeModel kategoriaFrisbee, TurnajVzorModel vzorTurnaj) {
		KategoriaTurnajuFrisbeeModel kategoriaTurnajuFrisbee = new KategoriaTurnajuFrisbeeModel();
		kategoriaTurnajuFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableKategoriaTurnaju_kategoriaId, kategoriaFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_id));
		kategoriaTurnajuFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableKategoriaTurnaju_turnajId,  turnajFrisbee.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_id));
		kategoriaTurnajuFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableKategoriaTurnaju_pocetTimov, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_pocetTimov));
		kategorieTurnajovFrisbee.add(kategoriaTurnajuFrisbee);
	}

	private TurnajFrisbeeModel getFrisbeeTrunajZoVzorTurnaj(TurnajVzorModel vzorTurnaj) throws Exception {
		TurnajFrisbeeModel turnajFrisbee = new TurnajFrisbeeModel();
		turnajFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_nazov, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_nazov));
		turnajFrisbee.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_datumOd, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_datumOd));
		for (DefaultModel tur :turnajeFrisbee){
			if (tur.equals(turnajFrisbee)){
				turnajFrisbee = (TurnajFrisbeeModel) tur;
				//System.out.println(turnajFrisbee);
				return turnajFrisbee;
			}
		}
		throw new Exception("Turnaj sa nenasiel, hladany = " + turnajFrisbee.toString());
	}

	private KategoriaFrisbeeModel getFrisbeeOpenKategoria(String nazovKategorie) throws Exception {
		KategoriaFrisbeeModel kategoria = new KategoriaFrisbeeModel();
		kategoria.nastavPreColumnHodnotu(Konstanty.frisbeeTableKategorie_nazov, nazovKategorie);
		for (DefaultModel kat :kategorieFrisbee){
			if (kat.equals(kategoria)){
				kategoria = (KategoriaFrisbeeModel) kat;
				//System.out.println(kategoria);
				return kategoria;
			}
		}
		throw new Exception("Nenasla sa kategoria");
	}

	private void PripravKategorie() {
		this.kategorieFrisbee = new HashSet<DefaultModel>();
		for (KategoriaVzorModel vzorKategoria: vsetkyKategorie){
			KategoriaFrisbeeModel frisbeeKategoria = new KategoriaFrisbeeModel();
			frisbeeKategoria.nastavPreColumnHodnotu(Konstanty.frisbeeTableKategorie_nazov, vzorKategoria.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov));
			kategorieFrisbee.add(frisbeeKategoria);
		}
	}

	private void PripravNoveTurnaje() {
		this.turnajeFrisbee = new HashSet<DefaultModel>();
		for (TurnajVzorModel vzorTurnaj: vsetkyTurnaje){
			TurnajFrisbeeModel frisbeeTurnaj = new TurnajFrisbeeModel();
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_nazov, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_nazov));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_datumOd, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_datumOd));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_datumDo, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_datumDo));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_mesto, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_mesto));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_stat, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_stat));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_datumZapisu, Date.valueOf((String) vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_datumZapisu)));
			frisbeeTurnaj.nastavPreColumnHodnotu(Konstanty.frisbeeTableTurnaje_report, vzorTurnaj.getHodnotuPreColumn(Konstanty.vzorTableTurnaje_report));
			turnajeFrisbee.add(frisbeeTurnaj);
		}
	}

	private void StiahniExistujuceUdaje() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException {
		vsetkyTurnaje = maper.getHodnoty(Konstanty.databaza_Vzor, Konstanty.vzorTable_turnaje, Konstanty.stlpceTabulkyVzor_Turnaje, TurnajVzorModel.class, TurnajVzorModel.class);
		vsetciHraci = maper.getHodnoty(Konstanty.databaza_Vzor, Konstanty.vzorTable_hraci, Konstanty.stlpceTabulkyVzor_Hraci, HracVzorModel.class, HracVzorModel.class);
		vsetkyKategorie = maper.getHodnoty(Konstanty.databaza_Vzor, Konstanty.vzorTable_kategorie, Konstanty.stlpceTabulkyVzor_Kategorie, KategoriaVzorModel.class, KategoriaVzorModel.class);
		vsetkyZostavy = maper.getHodnoty(Konstanty.databaza_Vzor, Konstanty.vzorTable_zostavy, Konstanty.stlpceTabulkyVzor_Zostavy, ZostavaVzorModel.class, ZostavaVzorModel.class);
	}
}
