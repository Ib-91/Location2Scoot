package controllers;
import models.*;
import views.*;
import controllers.*;


import javax.swing.*;

public class RetourController {
    
    public static void retour(JFrame current, JFrame previous) {
        current.dispose();
        if (previous != null) previous.setVisible(true);
    }
}