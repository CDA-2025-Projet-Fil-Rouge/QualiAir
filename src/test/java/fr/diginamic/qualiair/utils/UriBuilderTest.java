package fr.diginamic.qualiair.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UriBuilderTest {

    @Test
    void testUriBuildeR() {
        UriBuilder ub = new UriBuilder("https://www.oewiewo.com/");
        String uri = ub.pathVariable("tomate").requestParam("couleur", "rouge").build();
        System.out.println(uri);
        assertEquals("https://www.oewiewo.com/tomate?couleur=rouge", uri);
    }
}
