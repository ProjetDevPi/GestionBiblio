/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.MultiButton;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Document;
import com.mycompany.myapp.services.ServiceBiblio;
import java.util.List;

/**
 *
 * @author Mehdi
 */
public class displayDocuments {
     public static int idu=3;
    public static int idl=0;
    Form hi = new Form("", new BoxLayout(BoxLayout.Y_AXIS));
    Form current;
//h.add(lb);
  public displayDocuments(Resources res) {
 
 
        
        hi.getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Bibliotheque", "Title")
                        
                )
        );
        installSidemenu(res);

        

        ServiceBiblio serviceTask = new ServiceBiblio();
      

     List<Document> list = serviceTask.getListDoc();
          for (Document l:list)
      {
    
    
hi.add(createRankWidget(l,l.getId(),l.getNomdocument(), l.getEtatdocument(),l.getCat().getNomcategorie(),l.getNom_image(),res));
 hi.showBack();
    }
    
  }
  
          public SwipeableContainer createRankWidget(Document l,int id,String name, String auteur,String price,String img,Resources res) {
            MultiButton button = new MultiButton(name);  
            Button reserver = new Button("Emprunter");
              
              
        button.setHeight(100);

        
        button.setTextLine1(name);
        button.setTextLine2(auteur);
        button.setTextLine3("" + price);
        
             
   
         button.addActionListener(e->{
            
               displayonedoc a = new displayonedoc(l,res);
         
   
         });
          
        reserver.addActionListener(e -> {
         
            Dialog.show("Sign In", "your book "+name+"has been ordered", "ok", null);
        });
       
    return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createStarRankSlider()), 
            button);
}
private Slider createStarRankSlider() {
    Slider starRank = new Slider();

     return starRank;
}
   private void initStarRankStyle(Style s, Image star) {
    s.setBackgroundType(Style.BACKGROUND_IMAGE_TILE_BOTH);
    s.setBorder(Border.createEmpty());
    s.setBgImage(star);
    s.setBgTransparency(0);
}
       private void dialog(Document e,Resources res) {
       
        Dialog d = new Dialog(e.getNomdocument());
       // String img=e.getImage_name();
        TextArea popupBody = new TextArea( e.getEtatdocument() + "\n"   , 8, 12);
  
        popupBody.setUIID("Label");
        popupBody.setEditable(false);
        Button b = new Button("test");
        d.setLayout(new BorderLayout());
        
        d.addComponent(BorderLayout.CENTER, popupBody);
   
   
        d.setTransitionInAnimator(CommonTransitions.createEmpty());
        d.setTransitionOutAnimator(CommonTransitions.createFade(300));
        Rectangle rec = new Rectangle();
        rec.setX(700);
        rec.setY(1000);
        d.showPopupDialog(rec);
    }
   

    public void installSidemenu(Resources res) {
 
 
       
      
             hi.getToolbar().addCommandToRightBar("back", null, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
                
             new Menu().show(); 
                
         }
     });      
 
    }

    
}
