package sds.webimporter.domain.commands;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import sds.messaging.contracts.AbstractContract;

public class ParseWebPage extends AbstractContract {
    
    private UUID id;
    private String bucket;
    private UUID blobId;
    private UUID userId;


    public ParseWebPage() {
        namespace = "Sds.WebImporter.Domain.Commands";
        contractName = ParseWebPage.class.getSimpleName();
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

    /**
     * @return the bucket
     */
    @JsonProperty("Bucket")
    public String getBucket() {
        return bucket;
    }

    /**
     * @param bucket the bucket to set
     */
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /**
     * @return the id of blob
     */
    @JsonProperty("BlobId")
    public UUID getBlobId() {
        return blobId;
    }

    public void setBlobId(UUID blobId) {
        this.blobId = blobId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, blobId=%s, bucket=%s, userId=%s, namespace=%s, contractName=%s, correlationId=%s]",
                ParseWebPage.class.getSimpleName(), id, blobId, bucket, userId, namespace, contractName, getCorrelationId());
    }


}
