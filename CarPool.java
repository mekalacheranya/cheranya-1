package com.oosd.vstudent.models;

import javax.persistence.*;

@Entity
@Table(name = "carpool")
public class CarPool {

    public enum Status {
        OPEN, CLOSED;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carpool_id")
    private int id;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "student_id")
    private Student host;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "source")
    private String source;

    @Column(name = "destination")
    private String destination;

    @Column(name = "date")
    private String date;

    @Enumerated
    @Column(name = "status",
        columnDefinition = "smallint")
    private Status status;
    //constructors
    public CarPool() { }

    public CarPool(Student host, String timestamp, String source, String destination, String date) {
        this.host = host;
        this.timestamp = timestamp;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getHost() {
        return host;
    }

    public void setHost(Student host) {
        this.host = host;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //to string

    @Override
    public String toString() {
        return "CarPool{" +
                "id=" + id +
                ", host=" + host +
                ", timestamp='" + timestamp + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
