package models;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;


abstract public class DefaultModel {
	private HashMap<String, Object> mapaUdajov;
	
	public DefaultModel(){
		this.mapaUdajov = new HashMap<String, Object>();
	}
	
	public void nastavPreColumnHodnotu(String column, Object hodnota){
		this.mapaUdajov.put(column, hodnota);
	}
	
	public Object getHodnotuPreColumn(String column){
		return mapaUdajov.get(column);
	}
	
	public Set<Entry<String, Object>> vratMiUdaje(){
		return mapaUdajov.entrySet();
	}
	
	@Override
	 public String toString(){
		String nazov = getNazovClassy();
		 StringBuilder str = new StringBuilder(nazov + "[");
		 ArrayList<String> columns = getColumns();
		 for (String column: columns){
			 str.append(column + '=');
			 str.append(this.getHodnotuPreColumn(column));
			 str.append(',');
		 }
		 str.replace(str.length()-1, str.length(), "]");
		 return str.toString();
	 }

	protected abstract String getNazovClassy();

	protected abstract ArrayList<String> getColumns();
}
