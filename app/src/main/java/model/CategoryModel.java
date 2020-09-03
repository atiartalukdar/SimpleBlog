package model;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;

@Entity
public class CategoryModel
{
    @Id
    public long objectBoxID;

    @SerializedName("id")
    @Expose
    @Unique
    public int categoryId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("cat_other_lan")
    @Expose
    private String catOtherLan;
    @SerializedName("cat_color_code")
    @Expose
    private String catColorCode;
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


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCatOtherLan() {
        return catOtherLan;
    }

    public void setCatOtherLan(String catOtherLan) {
        this.catOtherLan = catOtherLan;
    }

    public String getCatColorCode() {
        return catColorCode;
    }

    public void setCatColorCode(String catColorCode) {
        this.catColorCode = catColorCode;
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


    @Override
    public String toString() {
        return "CategoryModel{" +
                "id=" + categoryId +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}