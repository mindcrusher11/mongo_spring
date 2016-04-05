package com.saltside.bootrest.bird;

import org.hibernate.validator.constraints.NotEmpty;

import com.saltside.bootrest.model.Birds;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TimeZone;

import javax.validation.constraints.Size;


public final class BirdsDTO {

	@NotEmpty
    private String id;

	@NotEmpty
    @Size(max = Birds.MAX_LENGTH_DESCRIPTION)
    private String name;

    @NotEmpty
    @Size(max = Birds.MAX_LENGTH_TITLE)
    private String family;
    
    private Set<String> continents;
    
    private String added;
    
    private boolean visible;
    
    public BirdsDTO() {
    	visible = false;
    	added = setDate();

    }

    @Override
    public String toString() {
        return String.format(
                "BirdsDTO[id=%s, name=%s, family=%s]",
                this.id,
                this.name,
                this.family
        );
    }

    public String setDate()
    {
      String DATEFORMAT = "yyyy-MM-dd";
      final SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
      sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
      String utcTime = sdf.format(new Date());

      return utcTime;
    }

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getFamily() {
		return family;
	}



	public void setFamily(String family) {
		this.family = family;
	}



	public Set<String> getContinents() {
		return continents;
	}



	public void setContinents(Set<String> continents) {
		this.continents = continents;
	}



	public String getAdded() {
		return added;
	}



	public void setAdded(String added) {
		this.added = added;
	}



	public boolean isVisible() {
		return visible;
	}



	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
