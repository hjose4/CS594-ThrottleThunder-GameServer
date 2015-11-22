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
    protected HashMap<String, String> data;
    
    public ObjectModel(){}
    
    protected ObjectModel(HashMap<String, String> input){
        data=input;
    }
    
    public String get(String field){
        if(!data.containsKey(field)){
            System.out.println("Le champ "+field+" n'existe pas dans l'objet");
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
        if(!field.equals("id")) this.save(field);
    }
    
    public boolean containsField(String field)
    {
        return data.containsKey(field);
    }
    
    public void pull(){
        ObjectModel d;
        try {
            d = DatabaseDriver.findById(this.getClass(), this.get("id"));
            data=d.getData();
        } catch (SQLException ex) {
            Logger.getLogger(ObjectModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //sauvegarde l'état d'un objet dans la bdd
    public void save(String field_to_save)
    {
        if(field_to_save.equals("all"))
            for(String field : data.keySet())
            {
                if  (!(field.equals("id")))
                {
                    DatabaseDriver.update(this.getClass(), Integer.valueOf(get("id")), field, get(field));
                }
            }
        else if (!field_to_save.equals("id"))
        {
        	if(DatabaseDriver.alreadyExists(this.getClass(), get("id"))) DatabaseDriver.insert(this);
        	else DatabaseDriver.update(this.getClass(),Integer.valueOf(get("id")), field_to_save, get(field_to_save));
        }
    }
    
    public void print()
    {
        System.out.println(this.getClass().getSimpleName()+data);
    }
    
}
