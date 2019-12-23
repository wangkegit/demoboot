package com.demoboot.entity;



import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @Column
    private String  note;

    public User(String name, String note) {
        this.name = name;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
