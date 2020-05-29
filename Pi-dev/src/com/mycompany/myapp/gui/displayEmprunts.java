/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui;

import com.codename1.components.ImageViewer;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.entities.Emprunt;
import com.mycompany.myapp.services.ServiceBiblio;
import java.io.IOException;
import java.util.List;


/**
 *
 * @author Mehdi
 */
public class displayEmprunts {
        Form hi = new Form("", new BoxLayout(BoxLayout.Y_AXIS));
            EncodedImage enc;
    Image imgs;
    ImageViewer imgv;
    
    
      public displayEmprunts(Resources res) {
 
 
        
        hi.getToolbar().setTitleComponent(
                FlowLayout.encloseCenterMiddle(
                        new Label("Bibliotheques", "Title")
                        
                )
        );
        installSidemenu(res);
        TextField search=new TextField();
        
        ServiceBiblio serviceTask = new ServiceBiblio();
      

     List<Emprunt> list = serviceTask.getListEmp();
          for (Emprunt l:list)
      {
    
    
hi.add(createRankWidget(l,l.getId(),l.getNomdocument(), l.getDocumentid(),l.getNomImage(),res));
 hi.showBack();
    }
    
  }
      public void installSidemenu(Resources res) {
 
 
       
        hi.getToolbar().addCommandToSideMenu("", null, e -> {
             hi.getToolbar().addCommandToLeftBar("back", null, new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent evt) {
                displayDocuments a = new displayDocuments(res);
                a.hi.showBack();
         }
     });
        });
       
        
        
      }
  
      private Slider createStarRankSlider() {
    Slider starRank = new Slider();

     return starRank;
}
      
    public SwipeableContainer createRankWidget(Emprunt l,int id,String name, int auteur,String img,Resources res) {
            MultiButton button = new MultiButton(name);  
            Button reserver = new Button("Emprunter");
              
              
        button.setHeight(100);

        
        button.setTextLine1(name);
       // button.setTextLine2("");
button.addActionListener(e -> {
         
          dialog(l, res);
        });
                
        reserver.addActionListener(e -> {
         
            Dialog.show("Sign In", "your book "+name+"has been ordered", "ok", null);
        });
       
    return new SwipeableContainer(FlowLayout.encloseCenterMiddle(createStarRankSlider()), 
            button);
                }
     private void dialog(Emprunt a,Resources res) {
        
    Dialog d = new Dialog(a.getNomdocument());
            try {
               
                enc = EncodedImage.create("/load.png");
            } catch (IOException ex) {
         
            }     
            String img=a.getNomImage();
            String url="http://localhost/kidoPI-DEV/web/images/"+img;
          imgs = URLImage.createToStorage(enc, url, url, URLImage.RESIZE_SCALE_TO_FILL);
         System.out.println(img);
         imgv = new ImageViewer(imgs);
   
        d.setLayout(new BorderLayout());
        d.addComponent(BorderLayout.NORTH, imgv);
    
 
      
        d.setTransitionInAnimator(CommonTransitions.createEmpty());
        d.setTransitionOutAnimator(CommonTransitions.createFade(300));
        Rectangle rec = new Rectangle();
        rec.setX(700);
        rec.setY(500);
        d.showPopupDialog(rec);
    }
    

}
