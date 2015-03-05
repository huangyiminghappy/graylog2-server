/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog2.restclient.models;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog2.restclient.models.api.responses.alarmcallbacks.AlarmCallbackSummaryResponse;
import org.graylog2.restclient.models.api.responses.alarmcallbacks.GetSingleAvailableAlarmCallbackResponse;
import org.joda.time.DateTime;

import java.util.Map;

/**
 * @author Dennis Oelkers <dennis@torch.sh>
 */
public class AlarmCallback extends ConfigurableEntity {
    public interface Factory {
        public AlarmCallback fromSummaryResponse(String streamId, AlarmCallbackSummaryResponse response);
    }

    private String id;
    private final UserService userService;
    private String streamId;
    private String type;
    private Map<String, Object> configuration;
    private DateTime createdAt;
    private String creatorUserId;
    private User creatorUser;

    @AssistedInject
    public AlarmCallback(UserService userService,
                         @Assisted String streamId,
                         @Assisted AlarmCallbackSummaryResponse response) {
        this.userService = userService;
        this.streamId = streamId;
        this.id = response.id;
        this.type = response.type;
        this.configuration = response.configuration;
        this.createdAt = DateTime.parse(response.createdAt);
        this.creatorUserId = response.creatorUserId;
        this.creatorUser = userService.load(creatorUserId);
    }

    public String getId() {
        return id;
    }

    public String getStreamId() {
        return streamId;
    }

    public String getType() {
        return type;
    }

    @Override
    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public Map<String, Object> getConfiguration(GetSingleAvailableAlarmCallbackResponse availableAlarmCallbackResponse) {
        return getConfiguration(availableAlarmCallbackResponse.getRequestedConfiguration());
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public User getCreatorUser() {
        return creatorUser;
    }
}
