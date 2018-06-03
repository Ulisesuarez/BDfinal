package org.mvpigs.MedicalDB.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="full_text_search")
public class File implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3161717268264982100L;
	// PrimaryKey
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long file_id;
    
	@Column(name = "name")
    private String name;
	
	@Column(name = "description")
	private String description;
	
	
	@Column(name = "headline")
	private String headline;
	
	@Column(name="type")
    private String type;
   
	@Column(name="color")
    private String color;
    
	@Column(name="deleted")
    private String deleted;
    
	@Column(name="deleted_at")
    private String deleted_at;
    
	@Column(name="created_at")
    private String created_at;
    
	@Column(name="updated_at")
    private String updated_at;
    
    
	@Column(name="account_owner_id")
    private long account_owner_id;
    
	@Column(name="username")
    private String username;
    
	@Column(name="email")
    private String email;
	
	
	@Column(name = "total")
	private int total;
    
    protected File() {}

    public File( long file_id, String name, String description, String headline, String type, String color, String deleted, String deleted_at, String created_at, String updated_at, String tsv, String tsv_es, long account_owner_id, String username, String email, int total) {
        this.file_id=file_id;
    	this.name = name;
        this.description = description;
        this.headline=headline;
        this.type = type;
        this.color = color;
        this.deleted = deleted;
        this.deleted_at = deleted_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.account_owner_id = account_owner_id;
        this.username = username;
        this.email = email;
        this.total=-1;
        }

    public File(long file_id2, String name2, String description2, long account_owner_id2, String headline2) {
    	  this.file_id=file_id2;
          this.name = name2;
          this.headline=headline2;
          this.description = description2;
          this.account_owner_id = account_owner_id2;
          this.total=1;
          }
	

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public long getFile_id() {
		return file_id;
	}

	public void setFile_id(long file_id) {
		this.file_id = file_id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(String deleted_at) {
		this.deleted_at = deleted_at;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	
	public long getAccount_owner_id() {
		return account_owner_id;
	}

	public void setAccount_owner_id(long account_owner_id) {
		this.account_owner_id = account_owner_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
    public String toString() {
        return String.format(
                "File: file_id=%d, name='%s', description='%s', headline='%s",
                file_id, name, description, headline);
    }

	public int getTotal() {
		
		return this.total;
	}



   
}
