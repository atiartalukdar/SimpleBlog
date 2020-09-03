package model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllArticleModel
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("artical")
    @Expose
    private String artical;
    @SerializedName("article_other_lan")
    @Expose
    private String articleOtherLan;
    @SerializedName("max_read")
    @Expose
    private String maxRead;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("sorting_index")
    @Expose
    private String sortingIndex;
    @SerializedName("category")
    @Expose
    private CategoryModel category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getArtical() {
        return artical;
    }

    public void setArtical(String artical) {
        this.artical = artical;
    }

    public String getArticleOtherLan() {
        return articleOtherLan;
    }

    public void setArticleOtherLan(String articleOtherLan) {
        this.articleOtherLan = articleOtherLan;
    }

    public String getMaxRead() {
        return maxRead;
    }

    public void setMaxRead(String maxRead) {
        this.maxRead = maxRead;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSortingIndex() {
        return sortingIndex;
    }

    public void setSortingIndex(String sortingIndex) {
        this.sortingIndex = sortingIndex;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ArticleModel{" +
                "id=" + id +
                ", categoryId='" + categoryId + '\'' +
                ", artical='" + artical + '\'' +
                ", maxRead='" + maxRead + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}