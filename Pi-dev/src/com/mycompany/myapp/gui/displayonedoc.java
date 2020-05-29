/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Document;
import com.mycompany.myapp.entities.Emprunt;
import com.mycompany.myapp.services.ServiceBiblio;
import java.io.IOException;

/**
 *
 * @author Mehdi
 */
public class displayonedoc {
     Form hi = new Form("", new BoxLayout(BoxLayout.Y_AXIS));
    EncodedImage enc;
       Image imgs;
    ImageViewer imgv;
 private Resources theme;

  
    public displayonedoc(Document l,Resources res)
    {
             
 
     String img=l.getNom_image();
    String url="http://localhost/kidoPI-DEV/web/images/"+img;
        System.out.println(img);
        hi.getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label(l.getNomdocument(), "Title")
                        
                )
        );
         //installSidemenu(res);
              hi.getToolbar().addCommandToLeftBar("back", null, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
                displayDocuments a = new displayDocuments(res);
                a.hi.showBack();
         }
     });

        TextArea popupBody = new TextArea("State:"+ l.getEtatdocument() + "\n" +"Category:"+ l.getCat().getNomcategorie()+"" + "\n"  , 8, 12);
        popupBody.setEditable(false);
         try {
            enc = EncodedImage.create("/load.png");
                    } catch (IOException ex) {
     
        }
                  Button emprunter = new Button("emprunter");
          emprunter.addActionListener(e -> {
        
        
              if(l.getEmprunte()==0)
              {
                   Emprunt em= new Emprunt();
                         ServiceBiblio sb= new ServiceBiblio();
              sb.Emprunter(l.getId());
              }
              else 
                        Dialog.show("erreur", "this document is already loaned", "ok", null);
          
         
        });
        imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE_TO_FILL);
    
         imgv = new ImageViewer(imgs);
        Container C1= new Container( new BoxLayout(BoxLayout.Y_AXIS));
        
        C1.add(popupBody);
        Container C2= new Container(new BoxLayout(BoxLayout.Y_AXIS));
        C2.add(imgv);
       hi.add(C2);
      hi.add(C1);
      hi.add(emprunter);
       
       hi.show();   
    }
   
                            
                            

 
    
}
