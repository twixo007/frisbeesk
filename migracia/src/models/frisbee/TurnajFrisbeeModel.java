package models.frisbee;

import java.sql.Date;
import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;

public class TurnajFrisbeeModel extends DefaultModel{
	
	@Override
    public boolean equals(Object arg) {
		if (!arg.getClass().isAssignableFrom(TurnajFrisbeeModel.class)){
			return false;
		}
		TurnajFrisbeeModel obj = (TurnajFrisbeeModel) arg;
        if(((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_nazov)).equals((String) obj.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_nazov))){
        	if ((Date.valueOf((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_datumOd))).equals(Date.valueOf((String) obj.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_datumOd)))){
        		//System.out.println(String.format("Porovnava TurnajFrisbeeModel1=%s a TurnajFrisbeeModel2=%s", this, obj));
        		return true;
        	}
        }
        return false;
    }
	
	 @Override
	 public int hashCode() {
	 	int result = ((String) this.getHodnotuPreColumn(Konstanty.frisbeeTableTurnaje_nazov)).hashCode();
	 	return result;
	 }
	 
	 @Override
	 public String toString(){
		 StringBuilder str = new StringBuilder("TurnajFrisbeeModel[");
		 for (String column: Konstanty.stlpceTabulkyFrisbee_Turnaje){
			 str.append(column + '=');
			 str.append(super.getHodnotuPreColumn(column));
			 str.append(',');
		 }
		 str.replace(str.length()-1, str.length(), "]");
		 return str.toString();
	 }

	@Override
	protected String getNazovClassy() {
		return "TurnajFrisbeeModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyFrisbee_Turnaje;
	}
}
