/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.escuelaing.arep.clasedocker.Persistence;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Home
 */
public class ServicePersistenceImpl implements ServicePersistence {

    private DB db;
    private DBCollection coleccion;
    

    public ServicePersistenceImpl() {
        MongoClient mongo = crearConexion();
        db = mongo.getDB("data");
        System.out.println(db.getMongo());
        
        coleccion = db.getCollection("strings");
        System.out.println("Conexion realizada");

    }

    private static MongoClient crearConexion() {
        MongoClient mongo = null;
        mongo = new MongoClient("db", 27017);

        return mongo;
    }

    @Override
    public void insertar(String string) {
        BasicDBObject document = new BasicDBObject();
        document.put("string", string);
        document.put("date", LocalDateTime.now());
        coleccion.insert(document);
    }

    @Override
    public Set<String> getLimitData(int numeroDatos) {
        BasicDBObject document = new BasicDBObject();
        document.put("date", -1);
        DBCursor cur = coleccion.find().sort(document).limit(numeroDatos);
        Set<String> data = new HashSet<String>();
        while (cur.hasNext()) {
            //System.out.println(cur.next()+"    <---------");
            data.add(cur.next().toString());
        }
        return data;
    }

    /*public static void main(String... args) {
        ServicePersistenceImpl mydb = new ServicePersistenceImpl();
        //mydb.insertar("hola33333");
        Set<String> data = mydb.getLimitData(2);
        System.out.println(data.size());
        for(String s:data){
            System.out.println(s+"----------------->");
        }
    }*/

}
