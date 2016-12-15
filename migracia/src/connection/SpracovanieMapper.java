package connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import konstanty.Konstanty;
import models.DefaultModel;

public class SpracovanieMapper extends Database{
	
	public SpracovanieMapper(){
		setStringConnection(Konstanty.adresa, Konstanty.databaza_Frisbee, Konstanty.user, Konstanty.password);
	}
	
	public <T extends DefaultModel, F extends T> ArrayList<T> getHodnoty(String databaza, String table, ArrayList<String> columns, Class<T> collectionType, Class<F> modelType) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		ArrayList<T> vysledok = new ArrayList<T>();
		try{
			super.CreateConnection();
			
			String sql = String.format("SELECT * FROM %s.%s;", databaza, table);
			
			super.getQuery(sql);
			
			while (rs.next()){
				F udaje = modelType.newInstance();
				for (String column : columns){
					udaje.nastavPreColumnHodnotu(column, rs.getString(column));
				}
				vysledok.add(udaje);
			}
		}finally{
			super.DisconectConnection();
		}
		
		
		return vysledok;
	}

	public void vlozUdaje(String databaza, String table, String[] stringStlpce, String[] nonStringStlpce, Collection<DefaultModel> objekty) throws ClassNotFoundException, SQLException {
		try{
			super.CreateConnection();
			super.StratTransaction();
			
			for (DefaultModel model: objekty){
				String sql = String.format("INSERT INTO %s.%s (", databaza, table);
				StringBuilder sqlBuild = new StringBuilder(sql);
				
				for (String stlpec : stringStlpce){
					sqlBuild.append(stlpec);
					sqlBuild.append(',');
				}
				
				for (String stlpec : nonStringStlpce){
					sqlBuild.append(stlpec);
					sqlBuild.append(',');
				}
				
				sqlBuild.replace(sqlBuild.length()-1, sqlBuild.length(), ") VALUES (");
				
				for (String stlpec : stringStlpce){
					Object hodnota = model.getHodnotuPreColumn(stlpec);
					if (hodnota == null){
						sqlBuild.append(hodnota);
						sqlBuild.append(',');
					}else{
						sqlBuild.append("'" + hodnota + "'");
						sqlBuild.append(',');
					}
				}
				
				for (String stlpec : nonStringStlpce){
					sqlBuild.append(model.getHodnotuPreColumn(stlpec));
					sqlBuild.append(',');
				}
				
				sqlBuild.replace(sqlBuild.length()-1, sqlBuild.length(), ");");
				super.InsertUpdateDeleteQuery(sqlBuild.toString());
			}
			
		}finally{
			super.EndTransaction();
			super.DisconectConnection();
		}
	}
	
}
