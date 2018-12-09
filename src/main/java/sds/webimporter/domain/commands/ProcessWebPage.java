package sds.webimporter.domain.commands;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import sds.messaging.contracts.AbstractContract;

public class ProcessWebPage extends AbstractContract {
    
    private UUID id;
    private String bucket;
    private String url;
    private UUID userId;


    public ProcessWebPage() {
        namespace = "Sds.WebImporter.Domain.Commands";
        contractName = ProcessWebPage.class.getSimpleName();
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

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the url
     */
    @JsonProperty("Url")
    public String getUrl() {
        return url;
    }

    
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, bucket=%s, userId=%s, namespace=%s, contractName=%s, correlationId=%s]",
                ProcessWebPage.class.getSimpleName(), id, bucket, userId, namespace, contractName, getCorrelationId());
    }


}
