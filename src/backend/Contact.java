package backend;

public class Contact {
	private String Name;
	private String Email;
	
	public Contact(String name, String email){
		this.Name = name;
		this.Email = email;
	}
	public String getName(){
		return this.Name;
	}
	public String getEmail(){
		return this.Email;
	}
	public void setName(String name){
		this.Name = name;
	}
	public void setEmail(String email){
		this.Email = email;
	}
}
