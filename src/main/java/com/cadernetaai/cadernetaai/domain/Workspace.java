package com.cadernetaai.cadernetaai.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "workspaces", schema = "public")
public class Workspace extends BaseEntities {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
}


