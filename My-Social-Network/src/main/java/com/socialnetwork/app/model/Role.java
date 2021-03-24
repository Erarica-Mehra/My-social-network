package com.socialnetwork.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "roles")
//@NoArgsConstructor
//@EqualsAndHashCode
public class Role implements Serializable {

    public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final Role ADMIN = new Role(1L, "ROLE_ADMIN", "admin");
    public static final Role USER = new Role(2L, "ROLE_USER", "user");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Person> users;

    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Person> getUsers() {
		return users;
	}

	public void setUsers(Set<Person> users) {
		this.users = users;
	}

}

