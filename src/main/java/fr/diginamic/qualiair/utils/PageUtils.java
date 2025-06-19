package fr.diginamic.qualiair.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


public class PageUtils {

    private PageUtils() {
    }

    public static <T> Page<T> convertToPage(List<T> fullList, int page, int size) {

        Pageable pageRequest = PageRequest.of(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + size, fullList.size());

        List<T> pageContent = fullList.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, fullList.size());
    }
}
