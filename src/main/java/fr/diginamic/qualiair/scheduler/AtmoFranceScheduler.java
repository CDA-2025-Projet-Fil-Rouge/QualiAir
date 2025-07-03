package fr.diginamic.qualiair.scheduler;

@Deprecated
public interface AtmoFranceScheduler {
    /**
     * Cette méthode appelle le service api atmo france pour récuperer et stocker en base l'ensemble des relevés qualité de l'air de la veille.
     * La reussite ou l'echec de la méthode est loggé dans un fichier .log
     * La tache est configurée pour s'executer toutes les nuits à 02h00.
     */
//    @Scheduled(cron = "${atmo.schedule.cron.meteo}")
    void fetchAirQualityDataAndPersist() throws Exception;
}
