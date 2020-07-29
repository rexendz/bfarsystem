/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Entities.MyToast;
import MainWindow.MainWindow;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.UIManager;

/**
 *
 * @author guess
 */
public class Main {

    private final String serviceAccountDir = "azizelmer-f6f1f-firebase-adminsdk-9q5go-190237b3b6.json";

    public Main() {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        UIManager.put("nimbusBase", Color.decode("#666666"));
        UIManager.put("nimbusBlueGrey", Color.WHITE);
        UIManager.put("control", Color.WHITE);
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            InputStream serviceAccount;
            ClassLoader classLoader = getClass().getClassLoader();
            
      
            try {
                serviceAccount = classLoader.getResourceAsStream(serviceAccountDir);

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://azizelmer-f6f1f.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                System.out.println("There seems to be an error...");
                MyToast.errorMessage("Error Connecting to Database");
            }
            new MainWindow().setVisible(true);
        });
    }

    public static void main(String args[]) {
        new Main();
    }
}
