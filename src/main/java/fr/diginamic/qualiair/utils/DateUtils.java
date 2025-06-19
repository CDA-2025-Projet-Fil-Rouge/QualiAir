package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

/**
 * Classe utilitaire pour les opérations courantes de manipulation de dates et heures.
 */
@DoNotInstanciate
public class DateUtils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private DateUtils() {
    }

    /**
     * Convertit une chaîne de caractères au format {@code yyyy-MM-dd} en {@link LocalDate}.
     *
     * @param dateStr la chaîne représentant une date
     * @return un objet {@link LocalDate} correspondant
     * @throws DateTimeParseException si le format de la chaîne est invalide
     */
    public static LocalDate toLocalDate(String dateStr) {
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Convertit un timestamp UNIX (en secondes) en {@link LocalDateTime}.
     *
     * @param unix le timestamp UNIX à convertir
     * @return un objet {@link LocalDateTime} correspondant
     */
    public static LocalDateTime toLocalDateTime(long unix) {
        return Instant.ofEpochSecond(unix).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Convertit une chaîne contenant un timestamp UNIX (en secondes) en {@link LocalDateTime}.
     *
     * @param unix la chaîne représentant un timestamp
     * @return un objet {@link LocalDateTime} correspondant
     * @throws NumberFormatException si la chaîne ne représente pas un nombre valide
     */
    public static LocalDateTime toLocalDateTime(String unix) {
        return Instant.ofEpochSecond(Long.parseLong(unix)).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Formate une {@link LocalDate} au format {@code yyyy-MM-dd}.
     *
     * @param date la date à formater
     * @return la chaîne de caractères formatée
     */
    public static String toStringSimplePattern(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Formate une {@link LocalDateTime} en ne conservant que la date (format {@code yyyy-MM-dd}).
     *
     * @param date la date-heure à formater
     * @return la chaîne formatée représentant la date
     */
    public static String toStringSimplePattern(LocalDateTime date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Formate une {@link LocalDateTime} au format complet {@code yyyy-MM-dd HH:mm}.
     *
     * @param dateTime la date-heure à formater
     * @return la chaîne formatée représentant la date et l'heure
     */
    public static String toStringCompletePattern(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Vérifie si une chaîne correspond au format de date {@code yyyy-MM-dd}.
     *
     * @param dateStr la chaîne à valider
     * @return {@code true} si la chaîne est valide, {@code false} sinon
     */
    public static boolean isValidDateFormat(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Retourne la date et l’heure actuelle, tronquée à l’heure (minutes et secondes mises à zéro).
     *
     * @return un objet {@link LocalDateTime} avec l'heure actuelle tronquée
     */
    public static LocalDateTime getTimeStamp() {
        return LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);
    }
}
