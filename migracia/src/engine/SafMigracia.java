package engine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.text.SimpleDateFormat;
import java.text.ParseException;

//import connection.Database;

public class SafMigracia {
  
  private static JFileChooser ourFileSelector = new JFileChooser();
  

  public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
    String source_path;
    if (args.length == 0) {
      ourFileSelector.setFileSelectionMode(JFileChooser.FILES_ONLY);
      ourFileSelector.showSaveDialog(null);     
      File fl = ourFileSelector.getSelectedFile();
      source_path = fl.getAbsolutePath();
    } else {
      source_path = args[0];
    }
    
      
    ArrayList<Hrac> hraci = new ArrayList<Hrac>();
          
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(source_path),"utf8"));

      
    String riadok = br.readLine();
    int lineNumber = 1;
   
    while (riadok != null) {    
      String[] splited = riadok.split("\\|");
      ArrayList<String> temp = new ArrayList<String>(Arrays.asList(splited));
      ArrayList<String> list = new ArrayList<String>();
      for (String prvok: temp){
        list.add(prvok.trim());
      }          
      
      try {
        hraci.add(Hrac.fromArrayList(list));
      } catch (RuntimeException e) {
        throw new RuntimeException("At line " + lineNumber + ": " + e.getMessage());
      }

      riadok = br.readLine();
      lineNumber += 1;
    }
      
    MojaDatabase db = new MojaDatabase();
    db.vlozUzivatelov(hraci);
  }
}


class Hrac{
  public String meno, priezvisko, gender, bydlisko, mail, klub, telefon, discipliny, suhlas, user_id;
  public Date dat_narodenia;
  
  
  public Hrac(){
    meno = "";
    priezvisko = "";
    gender = "";
    bydlisko = "";
    mail = "";
    klub = "";
    telefon = "";
    suhlas = "";
    user_id = "";
    discipliny = "";
  }

  public static Hrac fromArrayList(ArrayList<String> list) {

    Hrac hrac = new Hrac();
    try {
      hrac.meno = list.get(0);
      hrac.priezvisko = list.get(1);
      hrac.gender = list.get(2);
      hrac.dat_narodenia = parseDate(list.get(3));
      hrac.bydlisko = list.get(4);
      hrac.mail = list.get(5); //niesu v DB modely, ignorujem
      hrac.discipliny = list.get(6); //niesu v DB modely, ignorujem
      hrac.klub = list.get(7);
      hrac.telefon = list.get(9);
      hrac.suhlas = list.get(10);
      if (list.size() == 11) {
        hrac.user_id = "";
      } else {
        hrac.user_id = list.get(11);
      }
      return hrac;
    } catch(IndexOutOfBoundsException e) {
      throw new IndexOutOfBoundsException("Could not create Hrac from " + list.toString() + ": " + e.getMessage());
    }
  }

  private static Date parseDate(String value) {
      Date parsedDate = null;
      try {
        parsedDate = new SimpleDateFormat("d.M.yyyy").parse(value);
      } catch(ParseException e) {}
      if (parsedDate == null) {
        try {
          parsedDate = new SimpleDateFormat("M/d/yyyy").parse(value);
        } catch(ParseException e) {}
      }
      if (parsedDate == null) {
        throw new RuntimeException("Could not parse date string " + value);
      }
      return parsedDate;
    }
}

class MojaDatabase extends connection.Database{
  
  public void vlozUzivatelov(ArrayList<Hrac> hraci) throws SQLException, ClassNotFoundException {
    super.CreateConnection();
    for (Hrac u: hraci){
      int klub_id = getOrCreateKlubId(u.klub);
      String sql = "";
      if (u.user_id != null && u.user_id != "") {
        sql = String.format("UPDATE frisbee_hrac SET " +
          "krstne_meno = '%s', priezvisko = '%s', pohlavie = '%s', " +
          "datum_narodenia = '%s', miesto_bydliska = '%s', " +
          "klub_id = '%s', telefonne_cislo = '%s' " +
          "WHERE old_id = '%s'",
          u.meno, u.priezvisko, u.gender, new SimpleDateFormat("yyyy-MM-dd").format(u.dat_narodenia), u.bydlisko, klub_id, u.telefon, u.user_id
          );
      } else {
        sql = String.format("INSERT INTO frisbee_hrac " +
          "(krstne_meno, priezvisko, prezivka, pohlavie, datum_narodenia, miesto_bydliska, klub_id, telefonne_cislo, old_id) " +
          "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%d', '%s', NULL)",
          u.meno, u.priezvisko, u.meno + " " + u.priezvisko, u.gender, new SimpleDateFormat("yyyy-MM-dd").format(u.dat_narodenia), u.bydlisko, klub_id, u.telefon);
      }
      System.out.println(sql);
      super.InsertUpdateDeleteQuery(sql);
    }
    super.DisconectConnection();
  }

  private int getOrCreateKlubId(String klub) throws SQLException {
    super.getConditionQueryOneColumn("frisbee_klub", "id", String.format("nazov = '%s'", klub));
    if (rs.next()) {
      return rs.getInt("id");
    } else {
      String sql = String.format("INSERT INTO frisbee_klub (nazov) VALUES ('%s')", klub);      
      System.out.println(sql);
      super.InsertUpdateDeleteQuery(sql);
      if (rs.next()) {
        return rs.getInt(1);
      } else {
        throw new SQLException("Insert query returned no ID");
      }
    }
  }
}