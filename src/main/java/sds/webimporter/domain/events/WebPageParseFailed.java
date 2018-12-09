package sds.webimporter.domain.events;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import sds.messaging.contracts.AbstractContract;

public class WebPageParseFailed extends AbstractContract {

    private UUID id;
    private String timeStamp;
    private UUID userId;
    private String Message;
    
    public WebPageParseFailed() {
        namespace = "Sds.WebImporter.Domain.Events";
        contractName = WebPageParseFailed.class.getSimpleName();
    }

    public void setMessage(String Message) {
        this.Message = Message;
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

    @JsonProperty("Message")
    public String getMessage() {
        return Message;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("%s [id=%s, timeStamp=%s, userId=%s, namespace=%s, contractName=%s, correlationId=%s]",
                WebPageParseFailed.class.getSimpleName(), id, timeStamp, userId, namespace, contractName, getCorrelationId());
    }
    
    

}
