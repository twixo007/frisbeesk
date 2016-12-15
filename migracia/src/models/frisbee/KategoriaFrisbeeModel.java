package models.frisbee;

import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;

public class KategoriaFrisbeeModel extends DefaultModel {
	
	@Override
    public boolean equals(Object arg) {
		if (!arg.getClass().isAssignableFrom(KategoriaFrisbeeModel.class)){
			return false;
		}
		KategoriaFrisbeeModel obj = (KategoriaFrisbeeModel) arg;
        if(((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_nazov)).equals((String) obj.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_nazov))){
        	return true;
        }
        return false;
    }
	
	 @Override
	 public int hashCode() {
	 	int result = ((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableKategorie_nazov)).hashCode();
	 	return result;
	 }

	@Override
	protected String getNazovClassy() {
		return "KategoriaFrisbeeModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyFrisbee_Kategorie;
	}
}
