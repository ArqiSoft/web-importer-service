package sds.webimporter.domain.events;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sds.storage.Guid;
import sds.messaging.contracts.AbstractContract;

public class PdfGenerated extends AbstractContract {    
    
    private UUID id;
    private Guid blobId;
    private UUID pageId;
    private String title;
    private long lenght;
    private String md5;
    private String bucket;
    private String timeStamp;
    private UUID userId;
    
    
    public PdfGenerated() {
        namespace = "Sds.WebImporter.Domain.Events";
        contractName = PdfGenerated.class.getSimpleName();
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
    
    /**
     * @return the blobId
     */
    @JsonProperty("BlobId")
    public UUID getBlobId() {
        return blobId.getUUID();
    }

    /**
     * @return the pageId
     */
    @JsonProperty("PageId")
    public UUID getPageId() {
        return pageId;
    }

    /**
     * @return the title
     */
    @JsonProperty("Title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("Lenght")
    public long getLenght() {
        return lenght;
    }

    @JsonProperty("Md5")
    public String getMd5() {
        return md5;
    }

    @JsonProperty("Bucket")
    public String getBucket() {
        return bucket;
    }

    public void setBlobId(Guid blobId) {
        this.blobId = blobId;
    }

    public void setPageId(UUID pageId) {
        this.pageId = pageId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, timeStamp=%s, userId=%s, namespace=%s, contractName=%s]",
                PdfGenerated.class.getSimpleName(), id, timeStamp, userId, namespace, contractName);
    }

}
