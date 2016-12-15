package models.frisbee;

import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;

public class HracFrisbeeModel extends DefaultModel {
	
	@Override
	public boolean equals(Object arg) {
		if (!arg.getClass().isAssignableFrom(HracFrisbeeModel.class)){
			return false;
		}
		HracFrisbeeModel obj = (HracFrisbeeModel) arg;
        if(((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableHrac_prezivka)).equals((String) obj.getHodnotuPreColumn(Konstanty.frisbeeTableHrac_prezivka))){
        	//System.out.println(String.format("Obj1=%s Obj2=%s", this, obj));
        	return true;
        }
        return false;
    }
	
	 @Override
	 public int hashCode() {
	 	int result = ((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableHrac_prezivka)).hashCode();
	 	return result;
	 }

	@Override
	protected String getNazovClassy() {
		return "HracFrisbeeModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyFrisbee_Hrac;
	}
}
