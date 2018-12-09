package sds.webimporter.domain.events;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sds.storage.Guid;
import sds.messaging.contracts.AbstractContract;

public class WebPageProcessed extends AbstractContract {

    private UUID id;
    private Guid blobId;
    private String bucket;
    private String timeStamp;
    private UUID userId;
    
    public WebPageProcessed() {
        namespace = "Sds.WebImporter.Domain";
        contractName = WebPageProcessed.class.getSimpleName();
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

    @JsonProperty("BlobId")
    public UUID getBlobId() {
        return blobId.getUUID();
    }

    @JsonProperty("Bucket")
    public String getBucket() {
        return bucket;
    }

    public void setBlobId(Guid blobId) {
        this.blobId = blobId;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    

    
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, timeStamp=%s, userId=%s, namespace=%s, contractName=%s, correlationId=%s]",
                WebPageProcessed.class.getSimpleName(), id, timeStamp, userId, namespace, contractName, getCorrelationId());
    }

}
