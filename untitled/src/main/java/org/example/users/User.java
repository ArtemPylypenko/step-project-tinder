package org.example.users;

public class User {
    private String id;
    private String name;
    private String login;
    private String password;
    private String imgURL;

    public User(String id, String login, String password, String name, String imgURL) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.imgURL = imgURL;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getImgURL() {
        return imgURL;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", imgURL='" + imgURL + '\'' +
                '}';
    }
}
