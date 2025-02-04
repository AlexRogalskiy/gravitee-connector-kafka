/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.connector.kafka.json;

import io.gravitee.connector.kafka.model.ConsumerRecordHeader;
import io.gravitee.connector.kafka.model.RecordHeader;
import io.vertx.core.json.JsonObject;
import io.vertx.kafka.client.consumer.KafkaConsumerRecord;
import io.vertx.kafka.client.producer.KafkaHeader;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class JsonRecordFormatter {

    public static <K, V> String format(KafkaConsumerRecord<K, V> record) {
        JsonObject json = new JsonObject();

        json.put("metadata", new ConsumerRecordHeader<>(record.key(), record.partition(), record.offset(), record.timestamp()));
        if (record.headers() != null) {
            json.put(
                "headers",
                record
                    .headers()
                    .stream()
                    .map(kafkaHeader -> new RecordHeader(kafkaHeader.key(), kafkaHeader.value().toString()))
                    .collect(Collectors.toList())
            );
        }
        json.put("payload", record.value());

        return json.toString();
    }
}
