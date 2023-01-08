package org.example.likes;

public class Like {
    private String idLiker;
    private String idLiked;

    public Like(String idLiker, String idLiked) {
        this.idLiker = idLiker;
        this.idLiked = idLiked;
    }

    public String getIdLiker() {
        return idLiker;
    }

    public String getIdLiked() {
        return idLiked;
    }
}
