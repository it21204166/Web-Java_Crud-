package net.javaevent.servicepro.model;

public class User {
	private int id;
	private String name;
	private String businesstype;
	private String email;
	private String phoneno;
	private String about;
	

	public User(String name, String businesstype, String email, String phoneno, String about) {
		super();
		this.name = name;
		this.businesstype = businesstype;
		this.email = email;
		this.phoneno = phoneno;
		this.about = about;
	}
	public User(int id, String name, String businesstype, String email, String phoneno, String about) {
		super();
		this.id = id;
		this.name = name;
		this.businesstype = businesstype;
		this.email = email;
		this.phoneno = phoneno;
		this.about = about;
	}
	
//	Getters and setters
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
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
