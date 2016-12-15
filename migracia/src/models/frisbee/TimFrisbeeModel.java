package models.frisbee;

import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;

public class TimFrisbeeModel extends DefaultModel {
	
	@Override
	public boolean equals(Object arg) {
		if (!arg.getClass().isAssignableFrom(TimFrisbeeModel.class)){
			return false;
		}
		TimFrisbeeModel obj = (TimFrisbeeModel) arg;
        if(((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableTim_nazov)).equals((String) obj.getHodnotuPreColumn(Konstanty.frisbeeTableTim_nazov))){
        	return true;
        }
        return false;
    }
	
	 @Override
	 public int hashCode() {
	 	int result = ((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableTim_nazov)).hashCode();
	 	return result;
	 }
	
	
	@Override
	protected String getNazovClassy() {
		return "TimFrisbeeModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyFrisbee_Tim;
	}

}
