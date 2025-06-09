package fr.diginamic.qualiair.entities;

import fr.diginamic.qualiair.entities.composite.UtilisateurMessage;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "message_modification")
public class MessageModification
{
    @EmbeddedId
    private UtilisateurMessage id;
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    private String raison;
    private String commentaire;
    
    @MapsId("utilisateurId")
    @ManyToOne
    @JoinColumn(name = "id_utilisateur")
    private Utilisateur utilisateur;
    
    @MapsId("messageId")
    @ManyToOne
    @JoinColumn(name = "id_message")
    private Message message;
}
