package models.frisbee;

import java.util.ArrayList;

import konstanty.Konstanty;
import models.DefaultModel;

public class KategoriaTurnajuFrisbeeModel extends DefaultModel {

	@Override
	protected String getNazovClassy() {
		return "KategoriaTurnajuFrisbeeModel";
	}

	@Override
	protected ArrayList<String> getColumns() {
		return Konstanty.stlpceTabulkyFrisbee_KategorieTurnaju;
	}
}
