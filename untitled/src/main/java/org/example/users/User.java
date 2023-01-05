package org.example.users;

public class User {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private String imgURL;

    public User(Integer id, String name, String login, String password, String imgURL) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.imgURL = imgURL;
    }

    public User(String name, String login, String password, String imgURL) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.imgURL = imgURL;
    }

    public Integer getId() {
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
