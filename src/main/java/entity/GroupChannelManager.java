package entity;

import org.openapitools.client.model.OcDeleteChannelByUrl200Response;
import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.List;

public interface GroupChannelManager {
    public SendBirdGroupChannel createChannel(List userIds, String name);

    public OcDeleteChannelByUrl200Response deleteChannelByUrl(String channelUrl);
}
