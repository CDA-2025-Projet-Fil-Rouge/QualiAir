package fr.diginamic.qualiair.builder;

import fr.diginamic.qualiair.dto.historique.HistoriqueAirQuality;
import fr.diginamic.qualiair.dto.historique.HistoriquePopulation;
import fr.diginamic.qualiair.dto.historique.HistoriquePrevision;
import fr.diginamic.qualiair.enumeration.TypeExport;
import fr.diginamic.qualiair.exception.ExportException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;

public class CsvBuilder {

    private final HttpServletResponse response;

    public CsvBuilder(HttpServletResponse response, TypeExport type) throws ExportException {
        this.response = response;
        this.setHeader(type);
    }

    private void setHeader(TypeExport type) throws ExportException {
        if ((type.equals(TypeExport.HISTORIQUE_AIR))) {
            response.setHeader("Content-Disposition", "attachment; filename=\"historique-air.csv\"");
        }
        if ((type.equals(TypeExport.HISTORIQUE_POP))) {
            response.setHeader("Content-Disposition", "attachment; filename=\"historique-pop.csv\"");
        }
        if ((type.equals(TypeExport.HISTORIQUE_PREV))) {
            response.setHeader("Content-Disposition", "attachment; filename=\"historique-prev.csv\"");
        } else {
            throw new ExportException("type not supported yet");
        }
    }

    public CsvBuilder toLines(HistoriqueAirQuality historique) throws IOException {
        PrintWriter writer = response.getWriter();

        writer.println("codeElement,date,value");

        for (Map.Entry<LocalDateTime, Double> entry : historique.getHistorique().entrySet()) {
            String codeElement = historique.getCodeElement();
            String date = entry.getKey().toString();
            double value = entry.getValue();

            writer.printf("%s,%s,%f%n", codeElement, date, value);
        }
        return this;
    }

    public CsvBuilder toLines(HistoriquePrevision historique) throws IOException {
        PrintWriter writer = response.getWriter();

        writer.println("nature,unite,date,valeur");

        for (Map.Entry<LocalDateTime, Double> entry : historique.getValeurs().entrySet()) {
            String nature = historique.getNature();
            String unite = historique.getUnite();
            String date = entry.getKey().toString();
            double valeur = entry.getValue();

            writer.printf("%s,%s, %s,%f.2", nature, unite, date, valeur);
        }
        return this;
    }

    public CsvBuilder toLines(HistoriquePopulation historique) throws IOException {
        PrintWriter writer = response.getWriter();

        writer.println("nature,unite,date,valeur");

        for (Map.Entry<LocalDateTime, Double> entry : historique.getHistorique().entrySet()) {
            String nomVille = historique.getNom();
            String date = entry.getKey().toString();
            double valeur = entry.getValue();

            writer.printf("%s,%s,%f", nomVille, date, valeur);
        }
        return this;
    }

    public void build() throws IOException {
        response.flushBuffer();
    }
}
