package treinamentorestassured;

public class Pet {
    public int id;
    public Category category;
    public String name;
    public String[] photoUrls;
    public Tag[] tags;
    public String status;

    public int getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String[] getPhotoUrls() {
        return photoUrls;
    }

    public Tag[] getTags() {
        return tags;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoUrls(String[] photoUrls) {
        this.photoUrls = photoUrls;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
