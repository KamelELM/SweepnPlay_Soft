package com.univers.sweepplay.Login.model;

/**
 *  used for parameters of the database
 *  each function return the field instantiated
 */
public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    /**
     *  return the Id
     * @return Id
     */
    public int getId() {
        return id;
    }

    /**
     *  init the Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *  return the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *  init the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *  return the Email
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     *  init the Email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *  return the Email
     * @return Password (without encryption)
     */
    public String getPassword() {
        return password;
    }

    /**
     *  init the Password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}

