package com.es.diecines.model;

import jakarta.persistence.*;

@Entity
@Table(name = "peliculas")
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;


    private String director;

    private String time;

    private String trailer;
    @Column(name = "poster_image")
    private String posterImage;

    private String screenshot;

    @Column(length = 1000)
    private String synopsis;

    @Column(nullable = false)
    private Double rating;


    public Pelicula() {
    }

    public Pelicula(String time, String title, String director, String posterImage, String trailer, String screenshot, String synopsis, Double rating) {
        this.time = time;
        this.title = title;
        this.director = director;
        this.posterImage = posterImage;
        this.trailer = trailer;
        this.screenshot = screenshot;
        this.synopsis = synopsis;
        this.rating = rating;
    }

    // Getters y setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}