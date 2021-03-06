package com.github.zzt93.syncer.producer.input.mysql.connect;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.DeleteRowsEventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.EventHeaderV4Deserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.NullEventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.TableMapEventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.UpdateRowsEventDataDeserializer;
import com.github.shyiko.mysql.binlog.event.deserialization.WriteRowsEventDataDeserializer;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author zzt
 */
public class SyncDeserializer {

  public static EventDeserializer defaultDeserialzer() {
    Map<Long, TableMapEventData> tableMapEventByTableId = new HashMap<>();
    Map<EventType, EventDataDeserializer> eventDataDeserializers = new IdentityHashMap<>();
    eventDataDeserializers.put(EventType.WRITE_ROWS,
        new WriteRowsEventDataDeserializer(tableMapEventByTableId));
    eventDataDeserializers.put(EventType.UPDATE_ROWS,
        new UpdateRowsEventDataDeserializer(tableMapEventByTableId));
    eventDataDeserializers.put(EventType.DELETE_ROWS,
        new DeleteRowsEventDataDeserializer(tableMapEventByTableId));
    eventDataDeserializers.put(EventType.EXT_WRITE_ROWS,
        new WriteRowsEventDataDeserializer(tableMapEventByTableId).
            setMayContainExtraInformation(true));
    eventDataDeserializers.put(EventType.EXT_UPDATE_ROWS,
        new UpdateRowsEventDataDeserializer(tableMapEventByTableId).
            setMayContainExtraInformation(true));
    eventDataDeserializers.put(EventType.EXT_DELETE_ROWS,
        new DeleteRowsEventDataDeserializer(tableMapEventByTableId).
            setMayContainExtraInformation(true));
    eventDataDeserializers.put(EventType.TABLE_MAP, new TableMapEventDataDeserializer());
    return new EventDeserializer(new EventHeaderV4Deserializer(), new NullEventDataDeserializer(),
        eventDataDeserializers, tableMapEventByTableId);
  }
}
