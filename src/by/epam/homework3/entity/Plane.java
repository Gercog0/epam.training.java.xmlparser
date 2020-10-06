package by.epam.homework3.entity;

public abstract class Plane {
    private String model;
    private String id;
    private String description;
    private String origin;
    private Chars chars;
    private Parameters parameters;

    public String getModel() {
        return model;
    }

    public void setModel(String name) {
        this.model = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Chars getChars() {
        return chars;
    }

    public void setChars(Chars chars) {
        this.chars = chars;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Plane plane = (Plane) o;

        if (model != null ? !model.equals(plane.model) : plane.model != null) {
            return false;
        }
        if (id != null ? !id.equals(plane.id) : plane.id != null) {
            return false;
        }
        if (description != null ? !description.equals(plane.description) : plane.description != null) {
            return false;
        }
        if (origin != null ? !origin.equals(plane.origin) : plane.origin != null) {
            return false;
        }
        if (chars != null ? !chars.equals(plane.chars) : plane.chars != null) {
            return false;
        }
        return parameters != null ? parameters.equals(plane.parameters) : plane.parameters == null;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (origin != null ? origin.hashCode() : 0);
        result = 31 * result + (chars != null ? chars.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("model='").append(model).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", origin='").append(origin).append('\'');
        sb.append(", chars=").append(chars);
        sb.append(", parameters=").append(parameters);
        sb.append('}');
        return sb.toString();
    }
}
