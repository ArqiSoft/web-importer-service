package sds.webimporter.domain.events;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import sds.messaging.contracts.AbstractContract;

public class WebPageParsed extends AbstractContract {    
    
    private long totalRecords;
    private UUID fileId;
    private Iterable<String> fields;
    private UUID id;
    private String timeStamp;
    private UUID userId;
    
    
    public WebPageParsed() {
        namespace = "Sds.WebImporter.Domain.Events";
        contractName = WebPageParsed.class.getSimpleName();
    }

    /**
     * @return the id
     */
    @JsonProperty("Id")
    public UUID getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * @return the timeStamp
     */
    @JsonProperty("TimeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param timeStamp the timeStamp to set
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * @return the userId
     */
    @JsonProperty("UserId")
    public UUID getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @JsonProperty("TotalRecords")
    public long getTotalRecords() {
        return totalRecords;
    }

    @JsonProperty("FileId")
    public UUID getFileId() {
        return fileId;
    }

    @JsonProperty("Fields")
    public Iterable<String> getFields() {
        return fields;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public void setFields(Iterable<String> fields) {
        this.fields = fields;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, timeStamp=%s, userId=%s, namespace=%s, contractName=%s]",
                WebPageParsed.class.getSimpleName(), id, timeStamp, userId, namespace, contractName);
    }

}
