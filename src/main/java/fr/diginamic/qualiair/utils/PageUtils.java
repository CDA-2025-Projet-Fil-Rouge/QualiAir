package fr.diginamic.qualiair.utils;

import fr.diginamic.qualiair.annotation.DoNotInstanciate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Classe utilitaire pour la conversion de {@link List} en {@link Page}.
 */
@DoNotInstanciate
public class PageUtils {

    private PageUtils() {
    }

    /**
     * Convertit une liste de type T en une sous-liste paginée.
     *
     * @param fullList la liste sources
     * @param page     la page recherchée
     * @param size     la taille de la sous-liste
     * @param <T>      entité listée
     * @return liste paginée à la page rechercée
     */
    public static <T> Page<T> convertToPage(List<T> fullList, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + size, fullList.size());

        List<T> pageContent = fullList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, fullList.size());
    }
}
