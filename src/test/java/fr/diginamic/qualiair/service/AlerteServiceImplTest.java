package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.notification.AlerteInfo;
import fr.diginamic.qualiair.dto.notification.DemandeNotification;
import fr.diginamic.qualiair.entity.MesureAir;

import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.enumeration.TypeAlerte;
import fr.diginamic.qualiair.exception.*;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.service.mail.MailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlerteServiceImplTest {
    
    @Mock private UtilisateurService utilisateurService;
    @Mock private MesureAirService mesureAirService;
    @Mock private MailService mailService;
    @Mock private MesureAirMapper mapper;
    
    @InjectMocks private AlerteServiceImpl service;
    
    @Test
    void testGetAlertesByPolluant() {
        MesureAir mesure = new MesureAir();
        Page<MesureAir> page = new PageImpl<>(List.of(mesure));
        when(mesureAirService.findWithDetailsByTypeAndIndiceLessThan(any(), anyInt(), any()))
                .thenReturn(page);
        when(mapper.toAlerteDto(any())).thenReturn(new AlerteInfo());
        
        Page<AlerteInfo> result = service.getAlertesByPolluant(AirPolluant.O3, 0, 10, 5);
        
        assertEquals(1, result.getTotalElements());
        verify(mesureAirService).findWithDetailsByTypeAndIndiceLessThan(any(), anyInt(), any());
    }
    
    @Test
    void testBuildMessage() {
        String result = service.buildMessage(TypeAlerte.COMMUNAL, "123", "Attention !");
        assertTrue(result.contains("COMMUNAL"));
        assertTrue(result.contains("Attention"));
    }
    
    @Test
    void testSendAlert_National() throws Exception {
        DemandeNotification notif = new DemandeNotification();
        notif.setMessage("Test msg");
        notif.setType(TypeAlerte.NATIONAL);
        notif.setCode("FR");
        
        when(utilisateurService.getAllEmails()).thenReturn(List.of("a@b.com", "b@c.com"));
        
        service.sendAlert(notif);
        
        verify(mailService).sendEmails(anyList(), contains("Alerte"), contains("Test msg"));
    }
    
    @Test
    void testSendAlert_Commune() throws Exception {
        DemandeNotification notif = new DemandeNotification();
        notif.setMessage("Commune msg");
        notif.setType(TypeAlerte.COMMUNAL);
        notif.setCode("75001");
        
        when(utilisateurService.getEmailsByCommune("75001")).thenReturn(List.of("x@y.com"));
        
        service.sendAlert(notif);
        
        verify(mailService).sendEmails(anyList(), anyString(), anyString());
    }
}