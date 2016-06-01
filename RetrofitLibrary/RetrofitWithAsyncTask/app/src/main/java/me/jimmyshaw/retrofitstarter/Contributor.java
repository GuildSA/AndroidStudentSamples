package me.jimmyshaw.retrofitstarter;

// This is the model class to be use with our HTTP request.
public class Contributor {

    String login;
    String html_url;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")";
    }
}
