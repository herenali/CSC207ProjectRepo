package entity;


import org.openapitools.client.model.GcListChannelsResponse;
import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.List;

/**
 * Class for managing group channels.
 */
public interface GroupChannelManager {
    /**
     * Creates a new group channel.
     * @param userIds a list of the ids of the users in the group channel
     * @param name the name of the group channel
     * @return the group channel
     */
    SendBirdGroupChannel createChannel(List userIds, String name);


    /**
     * Deletes a group channel.
     * @param channelUrl the url of the group channel
     * @return the user
     */
    OcDeleteChannelByUrl200Response deleteChannelByUrl(String channelUrl);

    /**
     * Lists all group channels.
     * @param userId the id of the user
     * @return the list of group channels
     */
    GcListChannelsResponse listChannels(String userId);
}

