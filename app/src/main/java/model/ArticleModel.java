package model;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Unique;
import io.objectbox.annotation.*;
@Entity
public class ArticleModel
{

    @Id
    public long objectBoxID;

    @SerializedName("id")
    @Expose
    @Unique
    public int articleId;
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
    private int maxRead;
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

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
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

    public int getMaxRead() {
        return maxRead;
    }

    public void setMaxRead(int maxRead) {
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

    @Override
    public String toString() {
        return "ArticleModel{" +
                "id=" + articleId +
                ", categoryId='" + categoryId + '\'' +
                ", artical='" + artical + '\'' +
                ", maxRead='" + maxRead + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public ArticleModel(AllArticleModel allArticleModel) {
        this.articleId = allArticleModel.getId();
        this.categoryId = allArticleModel.getCategoryId();
        this.artical = allArticleModel.getArtical();
        this.articleOtherLan = allArticleModel.getArticleOtherLan();
        this.maxRead = allArticleModel.getMaxRead();
        this.status = allArticleModel.getStatus();
        this.createdAt = allArticleModel.getCreatedAt();
        this.updatedAt = allArticleModel.getUpdatedAt();
        this.sortingIndex = allArticleModel.getSortingIndex();
    }

    public ArticleModel(ArticleModel ArticleModel) {
        this.articleId = ArticleModel.getArticleId();
        this.categoryId = ArticleModel.getCategoryId();
        this.artical = ArticleModel.getArtical();
        this.articleOtherLan = ArticleModel.getArticleOtherLan();
        this.maxRead = ArticleModel.getMaxRead();
        this.status = ArticleModel.getStatus();
        this.createdAt = ArticleModel.getCreatedAt();
        this.updatedAt = ArticleModel.getUpdatedAt();
        this.sortingIndex = ArticleModel.getSortingIndex();
    }

    public ArticleModel() {
    }
}