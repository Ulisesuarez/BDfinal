package org.mvpigs.MedicalDB.domain;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="account")
public class User implements Serializable  {
	 protected User() {}
	 @Override
	public String toString() {
		return "User [account_id=" + account_id + ", username=" + username + ", email=" + email + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 222935827730576830L;

	@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private long account_id;
	    
		@Column(name="username")
	    private String username;
	    
		public User(long account_id, String username, String email) {
			super();
			this.account_id = account_id;
			this.username = username;
			this.email = email;
		}

		public long getAccount_id() {
			return account_id;
		}

		public void setAccount_id(long account_id) {
			this.account_id = account_id;
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

		@Column(name="email")
	    private String email;

	    
}
