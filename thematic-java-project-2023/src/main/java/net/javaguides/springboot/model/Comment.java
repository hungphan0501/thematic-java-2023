package net.javaguides.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_user")
    private int idUser;

    @Column(name = "id_product")
    private int idProduct;


    @Column(name = "content")
    private String content;

    @Column(name = "date")
    private String date;


    @Column(name = "id_reply")
    private int idReply;

    public Comment() {
    }

    public Comment(int idUser, int idProduct, String content, String date, int idReply) {
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.content = content;
        this.date = date;
        this.idReply = idReply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdReply() {
        return idReply;
    }

    public void setIdReply(int idReply) {
        this.idReply = idReply;
    }
}
