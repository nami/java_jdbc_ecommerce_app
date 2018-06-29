package MySQL.model;

public class products {

    private String p_name;
    private String p_description;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    @Override
    public String toString() {
        return "products{" +
                "p_name='" + p_name + '\'' +
                ", p_description='" + p_description + '\'' +
                ", id=" + id +
                '}';
    }

}
