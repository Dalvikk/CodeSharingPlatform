package platform.entries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class CodeSnippet {

    @Id
    @NotNull
    private final String snippetUUID = UUID.randomUUID().toString();

    @NotNull
    @Column(length = 65536)
    private String code = "";

    @NotNull
    private String header = "Untitled";
    @NotNull
    private LocalDateTime createDate = LocalDateTime.now();

    private int views = 0;

    @Nullable
    private LocalDateTime deleteDate = null;
    @Nullable
    Integer viewsLimit = null;

    public CodeSnippet() {
    }

    public CodeSnippet(@NotNull String code) {
        this.code = code;
    }

    public CodeSnippet(@NotNull String code, @NotNull String header) {
        this.code = code;
        this.header = header;
    }

    public boolean checkIsRestricted() {
        return deleteDate != null && viewsLimit != null;
    }

    public boolean checkIsAvailable() {
        if (deleteDate != null && deleteDate.isBefore(LocalDateTime.now())) {
            return false;
        }
        return viewsLimit == null || views < viewsLimit;
    }

    public void view() {
        views++;
    }

    public @NotNull String getSnippetUUID() {
        return snippetUUID;
    }

    public @NotNull String getCode() {
        return code;
    }

    public void setCode(@NotNull String code) {
        this.code = code;
    }

    public @NotNull LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(@NotNull LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public long getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public @Nullable LocalDateTime getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(@Nullable LocalDateTime deleteDate) {
        this.deleteDate = deleteDate;
    }

    public @Nullable Integer getViewsLimit() {
        return viewsLimit;
    }

    public void setViewsLimit(@Nullable Integer viewsLimit) {
        this.viewsLimit = viewsLimit;
    }

    public @NotNull String getHeader() {
        return header;
    }

    public void setHeader(@NotNull String header) {
        this.header = header;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeSnippet that = (CodeSnippet) o;
        return views == that.views && snippetUUID.equals(that.snippetUUID) && code.equals(that.code) && header.equals(that.header) && createDate.equals(that.createDate) && Objects.equals(deleteDate, that.deleteDate) && Objects.equals(viewsLimit, that.viewsLimit);
    }

    @Override
    public String toString() {
        return "CodeSnippet{" +
                "snippetUUID='" + snippetUUID + '\'' +
                ", code='" + code + '\'' +
                ", header='" + header + '\'' +
                ", createDate=" + createDate +
                ", views=" + views +
                ", deleteDate=" + deleteDate +
                ", viewsLimit=" + viewsLimit +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(snippetUUID, code, header, createDate, views, deleteDate, viewsLimit);
    }
}