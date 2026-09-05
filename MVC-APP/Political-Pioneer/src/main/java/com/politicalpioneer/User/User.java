package com.politicalpioneer.User;
import java.util.ArrayList;
import java.util.List;

import com.politicalpioneer.Party.Party;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

//user can not be used as a table name
@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "user_ideology")
    private Float user_ideology;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Party> parties = new ArrayList<>();

   


    // public User() {};
    public User(Long user_id, String first_name, String last_name , 
        String user_name, String password, String email, 
        Float user_ideology,String status, String role,
        List<Party> parties
    ) {
        this.userId = user_id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.userName = user_name;
        this.password = password;
        this.email = email;
        this.user_ideology = user_ideology;
        this.status = status;
        this.role = role;
        this.parties = parties;

    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

     public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
 public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Float getUserIdeology() {
        return this.user_ideology;
    }

    public void setUserIdeology(Float userIdeology) {
        this.user_ideology = userIdeology;
    }

    public String getStaus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getRole() {
        return this.role;
    }

    public void setString(String role) {
        this.role = role;
    }

    public List<Party> getParties() {
        return parties;
    }

    public void setParties(List<Party> parties) {
        this.parties = parties;
    }
 
}

