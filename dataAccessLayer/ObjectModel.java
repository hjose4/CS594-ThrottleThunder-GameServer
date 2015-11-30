package dataAccessLayer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Bastien
 */

public abstract class ObjectModel {
	private HashMap<String, String> data;

	public ObjectModel(){}

	protected ObjectModel(HashMap<String, String> input){
		data=input;
	}

	public String get(String field){
		if(!data.containsKey(field)){
			System.out.println("the key "+field+" does not exists for the object");
			return null;
		}
		return data.get(field);
	}

	public HashMap<String, String> getData()
	{
		return data;
	}

	public void set(String field, Object value){
		data.put(field, value.toString());
	}
	
	public void setAndUpdate(String field, Object value){
		data.put(field, value.toString());
		if(!field.equals("id")) this.save(field);
	}

	public boolean containsField(String field)
	{
		return data.containsKey(field);
	}

	public void pull(){
		ObjectModel d;
		try {
			d = DatabaseDriver.getInstance().findById(this.getClass(), this.get("id"));
			data=d.getData();
		} catch (SQLException ex) {
			Logger.getLogger(ObjectModel.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	//sauvegarde l'état d'un objet dans la bdd
	public boolean save(String field_to_save) {
		//Updating an existing object
		if(field_to_save.equals("all") && data.containsKey("id")) {
			/*Not recommended: - why not just do one update statement instead of doing N times update.
			for(String field : data.keySet()) {				
				if  (!(field.equals("id"))) {
					DatabaseDriver.update(this.getClass(), Integer.valueOf(get("id")), field, get(field));
				}
			}*/
			return DatabaseDriver.getInstance().update(this.getClass(), Integer.valueOf(get("id")), data);
		} else if (!field_to_save.equals("id")) {
			if(!data.containsKey("id") || !DatabaseDriver.getInstance().alreadyExists(this.getClass(), get("id"))) {
				int id = DatabaseDriver.getInstance().insert(this);
				if(id > 0)
					set("id",id);
				return id > 0;
			} else 
				return DatabaseDriver.getInstance().update(this.getClass(),Integer.valueOf(get("id")), field_to_save, get(field_to_save));
		}
		return false;
	}

	public void print() {
		System.out.println(this.getClass().getSimpleName()+data);
	}

}
