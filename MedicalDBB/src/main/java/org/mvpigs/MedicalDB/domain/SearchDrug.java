package org.mvpigs.MedicalDB.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="drug_search")
public class SearchDrug implements Serializable  {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8650212370860652344L;

	protected SearchDrug() {}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long search_id;
	    
		@Column(name="user_id")
	    private long userid;
		
		@Column(name="search_object")
	    private String searchJson;
		
		 		
		public SearchDrug(long search_id, long userid, String searchJson) {
			super();
			this.search_id = search_id;
			this.userid = userid;
			this.searchJson = searchJson;
		}

		public long getSearch_id() {
			return search_id;
		}

		public void setSearch_id(long search_id) {
			this.search_id = search_id;
		}

		public long getUserid() {
			return userid;
		}

		public void setUserid(long userid) {
			this.userid = userid;
		}

		public String getSearchJson() {
			return searchJson;
		}

		public void setSearchJson(String searchJson) {
			this.searchJson = searchJson;
		}

		
}
		
		
		