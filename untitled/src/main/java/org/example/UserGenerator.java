package org.example;

public class UserGenerator {
    private static final String[] names = {"Bob", "Jim", "Liza", "Mark",
            "Sasha", "Kate", "Mary", "John"};
    private static final String[] images = {"http://surl.li/eidpn", "http://surl.li/eidpp",
            "http://surl.li/eidps", "http://surl.li/eidpx", "http://surl.li/eidqa"};

    public static String getName() {
        int n = (int) Math.floor(Math.random() * names.length);
        return names[n];
    }

    public static String getImgUrl() {
        int n = (int) Math.floor(Math.random() * images.length);
        return images[n];
    }
}
