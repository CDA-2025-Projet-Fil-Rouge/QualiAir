package fr.diginamic.qualiair.annotation;

public @interface DoNotUseDirectly {
    Class<?> useInstead();
}
