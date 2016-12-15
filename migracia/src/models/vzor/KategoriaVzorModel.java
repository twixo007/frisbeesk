package models.vzor;

import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;


public class KategoriaVzorModel extends DefaultModel{
	
	@Override
    public boolean equals(Object arg) {
		if (!arg.getClass().isAssignableFrom(KategoriaVzorModel.class)){
			return false;
		}
		KategoriaVzorModel obj = (KategoriaVzorModel) arg;
        if(((String) this.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov)).equals((String) obj.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov))){
        	return true;
        }
        return false;
    }
	
	@Override
	 public int hashCode() {
	 	int result = ((String) this.getHodnotuPreColumn(Konstanty.vzorTableKategorie_nazov)).hashCode();
	 	return result;
	 }

	@Override
	protected String getNazovClassy() {
		return "KategoriaVzorModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyVzor_Kategorie;
	}
}
