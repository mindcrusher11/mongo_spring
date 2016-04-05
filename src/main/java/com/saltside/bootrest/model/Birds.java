package com.saltside.bootrest.model;

import org.springframework.data.annotation.Id;

import static com.saltside.bootrest.util.PreCondition.notEmpty;
import static com.saltside.bootrest.util.PreCondition.notNull;

import java.util.Set;

public final class Birds {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
    

    @Id
    private String id;

    private String name;

    private String family;
    
    private Set<String> continents;

	private String added;
    
    private boolean visible;

    public String getAdded() {
		return added;
	}

	public boolean getVisible() {
		return visible;
	}


	public Birds() {}

    private Birds(Builder builder) {
    	this.id = builder.id;
        this.name = builder.name;
        this.family = builder.family;
        this.continents = builder.continents;
        this.visible = builder.visible;
        this.added = builder.added;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamily() {
        return family;
    }

    public Set<String> getContinents() {
		return continents;
	}
    
    public void update(String name, String id) {
        checkValues(name, id);

        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "Birds[id=%s, name=%s, family=%s]",
                this.id,
                this.name,
                this.family
        );
    }

    /**
     * We don't have to use the builder pattern here because the constructed class has only two String fields.
     * However, I use the builder pattern in this example because it makes the code a bit easier to read.
     */
    public static class Builder {

    	private String id;
    	
        private String name;

        private String family;
        
        private Set<String> continents;

		private String added;
        
        private boolean visible;

        private Builder() {}

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder continents(Set<String> continents)
        {
        	this.continents = continents;
        	return this;
        }
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder family(String family) {
            this.family = family;
            return this;
        }
        
        public Builder added(String added) {
            this.added = added;
            return this;
        }
        
        public Builder visible(boolean visible) {
            this.visible = visible;
            return this;
        }
        
        public Birds build() {
            Birds build = new Birds(this);

            build.checkValues(build.getName(), build.getId());

            return build;
        }
    }

    private void checkValues(String name, String id) {
        notNull(name, "Name cannot be null");
        notEmpty(name, "Name cannot be empty");
        notNull(id, "Id cannot be null");
        notEmpty(id, "Id cannot be empty");
        
/*        isTrue(name.length() <= MAX_LENGTH_TITLE,
                "Title cannot be longer than %d characters",
                MAX_LENGTH_TITLE
        );

        if (family != null) {
            isTrue(family.length() <= MAX_LENGTH_DESCRIPTION,
                    "Description cannot be longer than %d characters",
                    MAX_LENGTH_DESCRIPTION
            );
        }*/
    }
}
