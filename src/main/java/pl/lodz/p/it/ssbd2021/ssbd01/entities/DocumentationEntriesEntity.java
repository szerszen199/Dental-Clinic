package pl.lodz.p.it.ssbd2021.ssbd01.entities;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "documentation_entries", schema = "public", catalog = "ssbd01")
public class DocumentationEntriesEntity {
    private long id;
    private String wasDone;
    private String toBeDone;
    private Long version;
    private long creationDateTime;
    private Long modificationDateTime;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "was_done", nullable = true, length = -1)
    public String getWasDone() {
        return wasDone;
    }

    public void setWasDone(String wasDone) {
        this.wasDone = wasDone;
    }

    @Basic
    @Column(name = "to_be_done", nullable = true, length = -1)
    public String getToBeDone() {
        return toBeDone;
    }

    public void setToBeDone(String toBeDone) {
        this.toBeDone = toBeDone;
    }

    @Basic
    @Column(name = "version", nullable = true)
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Basic
    @Column(name = "creation_date_time", nullable = false)
    public long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    @Basic
    @Column(name = "modification_date_time", nullable = true)
    public Long getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(Long modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DocumentationEntriesEntity that = (DocumentationEntriesEntity) o;

        return new EqualsBuilder().append(id, that.id).append(creationDateTime, that.creationDateTime).append(wasDone, that.wasDone).append(toBeDone, that.toBeDone).append(version, that.version).append(modificationDateTime, that.modificationDateTime).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(wasDone).append(toBeDone).append(version).append(creationDateTime).append(modificationDateTime).toHashCode();
    }
}
