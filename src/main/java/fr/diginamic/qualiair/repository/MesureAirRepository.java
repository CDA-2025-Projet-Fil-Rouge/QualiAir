package fr.diginamic.qualiair.repository;

import fr.diginamic.qualiair.entity.MesureAir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MesureAirRepository extends JpaRepository<MesureAir, Long> {


    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MesureAir m WHERE m.dateEnregistrement >= :startOfDay AND m.dateEnregistrement < :startOfNextDay")
    boolean existsByDate(LocalDateTime startOfDay, LocalDateTime startOfNextDay);
}
