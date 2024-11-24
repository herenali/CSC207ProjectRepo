package entity;

import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.List;

/**
 * Class for managing group channels.
 */
public interface GroupChannelManager {
    /**
     * Creates a new single channel.
     * @param userId a string of the id of the users in the channel
     * @param name the name of the channel
     * @return the channel
     */
    SendBirdGroupChannel createChannel(List userId, String name);

    /**
     * Deletes a group channel.
     * @param channelUrl the url of the group channel
     * @return the user
     */
    OcDeleteChannelByUrl200Response deleteChannelByUrl(String channelUrl);
}
