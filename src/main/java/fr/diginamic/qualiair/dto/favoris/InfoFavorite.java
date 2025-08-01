package fr.diginamic.qualiair.dto.favoris;

public class InfoFavorite {
    private String codeInsee;
    private Long userId;

    public InfoFavorite() {
    }

    /**
     * Getter
     *
     * @return codeInsee
     */
    public String getCodeInsee() {
        return codeInsee;
    }

    /**
     * Setter
     *
     * @param codeInsee sets value
     */
    public void setCodeInsee(String codeInsee) {
        this.codeInsee = codeInsee;
    }

    /**
     * Getter
     *
     * @return userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Setter
     *
     * @param userId sets value
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
