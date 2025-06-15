package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesureAir;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface MesureAirRepository extends JpaRepository<MesureAir, Long> {


    boolean existsMesureAirByDateReleve(LocalDateTime dateReleve);
}
