/**
 * @author ANDRIANASOAVINA Anthonio Acquilas
 * Ce contrôleur gère les interactions avec la vue de changement de status de la carte.
 * Il est responsable de la mise à jour du status de la carte et de la fermeture de la fenêtre parente.
 */

package controleurs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import modeles.*;
import vues.*;

public class ControleurCarteChangerStatus extends JPanel implements ActionListener {
    // Constante pour l'action de changement
    public static final String ACTION_CHANGER = "CHANGER";
    
    // Références aux modèles, vues et composants
    Carte _modeleCarte;
    VueCarteChangerStatus _vueCarteChangerStatus;
    VueCarte _vueCarte;
    JButton changerStatusButton;
    JFrame _parentFrame;

    public ControleurCarteChangerStatus(Carte modeleCarte, VueCarteChangerStatus vueCarteChangerStatus, VueCarte vueCarte, JFrame parentFrame) {
        this._modeleCarte = modeleCarte;
        this._vueCarteChangerStatus = vueCarteChangerStatus;
        this._vueCarte = vueCarte;
        this._parentFrame = parentFrame;

        // Création du bouton de changement et assignation de l'action
        this.changerStatusButton = new JButton("Changer");
        this.changerStatusButton.setActionCommand(ACTION_CHANGER);
        this.changerStatusButton.addActionListener(this);
        
        // Configuration du layout
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        // Ajout du bouton au panneau
        this.add(changerStatusButton);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_CHANGER)) {
            // Action : Changement du status de la carte
            this._modeleCarte.ChangerStatusCarte(this._vueCarteChangerStatus.getStatusFieldText());
            
            // Mise à jour de la vue de la carte
            this._vueCarte.majVueCarte();
            
            // Fermeture de la fenêtre parente
            this._parentFrame.dispose();
        }
    }
}
