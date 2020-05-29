/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.l10n.ParseException;
import com.mycompany.myapp.entities.Document;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.Base64;
import com.mycompany.myapp.entities.Categorie;
import com.mycompany.myapp.entities.Emprunt;
import com.mycompany.myapp.entities.login;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Mehdi
 */
public class ServiceBiblio {
     public static login l = new login();  
     ArrayList<Emprunt> listemprunt = new ArrayList<>();
    ArrayList<Document> listdocument = new ArrayList<>();
   ArrayList<Document> listdocumentone = new ArrayList<>(); 
    public ArrayList<Document> DocParseJson(String json) throws ParseException, IOException {

        ArrayList<Document> listLivre1 = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> experiences = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) experiences.get("root");

            for (Map<String, Object> obj : list) {

                Document e = new Document();
               // float id = Float.parseFloat(obj.get("idExperience").toString());
              
                float id = Float.parseFloat(obj.get("id").toString());
                float empunte = Float.parseFloat(obj.get("emprunte").toString());
         String nomcategorie =obj.get("nomcategorie").toString();
         String etatdocument =obj.get("etatdocument").toString();
         String image=obj.get("nomImage").toString();
                e.setId((int) id);
                //e.set((int) id);
                e.setEtatdocument(etatdocument);
                e.setNomdocument(obj.get("nomdocument").toString());
                e.setEmprunte((int) empunte);
                e.setCat(new Categorie(nomcategorie));
                e.setNom_image(image);
                
        
                 
                listLivre1.add(e);

            }

        } catch (IOException ex) {
        }
         //System.out.println(listLivre1);
        return listLivre1;
    }
     
     public ArrayList<Document> getListDoc() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/kidoPI-DEV/web/app_dev.php/mobile/showdocmobile");

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBiblio sl = new ServiceBiblio();
                try {
                    listdocument = sl.DocParseJson(new String(con.getResponseData()));
                } catch (ParseException ex) {
                   
                } catch (IOException ex) {
                  
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listdocument;
    
    }
         public void Emprunter(int id) {
        MultipartRequest con = new MultipartRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/kidoPI-DEV/web/app_dev.php/mobile/empruntermob?" + "iddoc=" + id+ "&idUser=" + ServiceUser.currentUser.getId_user();// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((ee) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        Emprunt e = new Emprunt();
             System.out.println( e.getDateemprunt());
              String accountSID = "AC0165240ad9463761d0aa976bdc2c8c2c";
            String authToken = "b8164aea8da9a0ae25824ed6d1c7a6cd";
            String fromPhone = "+12192913355";
            String destinationPhone = "+21692048760";

            Response<Map> SMS = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                    queryParam("To", destinationPhone).
                    queryParam("From", fromPhone).
                    queryParam("Body", "Votre document 'apprendre à compter'"+ "a été emprunté avec succés le 13/05/2020 , la date de retour est le 20/05/2020").
                    header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                    getAsJsonMap();
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
       public ArrayList<Emprunt> getListEmp() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/kidoPI-DEV/web/app_dev.php/mobile/affemprunt?"+"idUser="+ ServiceUser.currentUser.getId_user());

        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceBiblio sl = new ServiceBiblio();
                try {
                    listemprunt = sl.EmpParseJson(new String(con.getResponseData()));
                } catch (ParseException ex) {
                   
                } catch (IOException ex) {
                  
                }

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listemprunt;
    
    }
       public ArrayList<Emprunt> EmpParseJson(String json) throws ParseException, IOException {

        ArrayList<Emprunt> listEmprunt1 = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();

            Map<String, Object> experiences = j.parseJSON(new CharArrayReader(json.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) experiences.get("root");

            for (Map<String, Object> obj : list) {

                Emprunt e = new Emprunt();
               // float id = Float.parseFloat(obj.get("idExperience").toString());
              
                float id = Float.parseFloat(obj.get("id").toString());
        
                float docid=Float.parseFloat(obj.get("idd").toString());
       
                String image=obj.get("nomImage").toString();
                String docname=obj.get("nomdocument").toString();
                e.setId((int) id);
               e.setDocumentid((int) docid);
               e.setNomImage(image);
          e.setNomdocument(docname);
        
                 
                listEmprunt1.add(e);
                System.out.println(e);

            }

        } catch (IOException ex) {
        }
         //System.out.println(listLivre1);
        return listEmprunt1;
    }
}
