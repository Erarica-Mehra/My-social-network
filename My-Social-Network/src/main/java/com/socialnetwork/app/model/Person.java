package com.socialnetwork.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "persons")
public class Person implements UserDetails, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "full_name")
	@Formula(value = "concat(first_name, ' ', last_name)")
	private String fullName;

//	@Column(unique = true, length = 50) // TODO: 25.01.2017 Think how provide unique group [shortName + email]
	@Column(name = "short_name")
	private String shortName;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "phone")
	private String phone;

	@Temporal(TemporalType.DATE)
	@Column(name = "birth_date")
	private Date birthDate;

	@Convert(converter = GenderConverter.class)
	@Column(name = "gender")
	private Gender gender = Gender.UNDEFINED;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created = new Date();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	@JsonIgnore
	private Set<Person> friends = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
	@JsonIgnore
	private Set<Person> friendOf = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "person_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public boolean hasFriend(Person friend) {
		return friends.contains(friend);
	}

	public void addFriend(Person friend) {
		friends.add(friend);
		friend.friendOf.add(this);
	}

	public void removeFriend(Person friend) {
		friends.remove(friend);
		friend.friendOf.remove(this);
	}

	public boolean isFriendOf(Person person) {
		return friendOf.contains(person);
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		setFullName();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		setFullName();
	}

	private void setFullName() {
		this.fullName = String.format("%s %s", this.firstName, this.lastName);
	}

	public Person(Long id, String firstName, String lastName, String shortName, String email, String password,
			String phone, Date birthDate, Gender gender, Set<Role> roles) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.shortName = shortName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.birthDate = birthDate;
		this.gender = gender;
		this.roles = roles;
		setFullName();
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Set<Person> getFriends() {
		return friends;
	}

	public void setFriends(Set<Person> friends) {
		this.friends = friends;
	}

	public Set<Person> getFriendOf() {
		return friendOf;
	}

	public void setFriendOf(Set<Person> friendOf) {
		this.friendOf = friendOf;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person(Long id, String firstName, String lastName, String fullName, String shortName, String email,
			String password, String phone, Date birthDate, Gender gender, Date created, Set<Person> friends,
			Set<Person> friendOf, Set<Role> roles) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.shortName = shortName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.birthDate = birthDate;
		this.gender = gender;
		this.created = created;
		this.friends = friends;
		this.friendOf = friendOf;
		this.roles = roles;
	}

}
