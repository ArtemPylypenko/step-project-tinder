package org.example.likes;

public class Like {
    private Integer idLiker;
    private Integer idLiked;

    public Like(Integer idLiker, Integer idLiked) {
        this.idLiker = idLiker;
        this.idLiked = idLiked;
    }

    public Integer getIdLiker() {
        return idLiker;
    }

    public Integer getIdLiked() {
        return idLiked;
    }
}
