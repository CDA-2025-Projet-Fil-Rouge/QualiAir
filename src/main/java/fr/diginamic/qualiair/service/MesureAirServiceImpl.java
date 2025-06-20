package fr.diginamic.qualiair.service;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.entity.MesureAir;
import fr.diginamic.qualiair.enumeration.AirPolluant;
import fr.diginamic.qualiair.mapper.MesureAirMapper;
import fr.diginamic.qualiair.repository.MesureAirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Service permettant la gestion des {@link MesureAir}.
 */
@Service
public class MesureAirServiceImpl implements MesureAirService {
    @Autowired
    private MesureAirRepository repository;
    @Autowired
    private MesureAirMapper mapper;

    @Override
    public MesureAir save(MesureAir mesure) {
        return repository.save(mesure);
    }

    @Override
    public boolean existsByDateReleve(LocalDate date) {

        return repository.existsMesureAirByDateReleve(date.atStartOfDay());
    }

    @Override
    public HistoriqueAirQuality getAllByPolluantAndCodeInseeBetweenDates(AirPolluant polluant, String codeInsee, LocalDate dateStart, LocalDate dateEnd) {
        List<MesureAir> mesures = repository.getAllByPolluantAndCoordonnee_Commune_CodeInseeBetweenDates(polluant, codeInsee, dateStart, dateEnd);

        return mapper.toDto(polluant, mesures);
    }

    @Override
    public Page<MesureAir> findWithDetailsByTypeAndIndiceLessThan(AirPolluant polluant, int maxIndice, Pageable pageable) {
        return repository.findWithDetailsByTypeAndIndiceLessThan(polluant, maxIndice, pageable);
    }

}
