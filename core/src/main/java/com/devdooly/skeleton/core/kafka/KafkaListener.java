package com.devdooly.skeleton.core.kafka;

import com.devdooly.skeleton.core.loop.ThreadLoopImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class KafkaListener<K, V> extends ThreadLoopImpl implements ConsumerRebalanceListener {
    private final KafkaConsumer<K, V> kafkaConsumer;
    private final RecordConsumer<K, V> recordConsumer;
    private final List<String> topics;
    private final ConcurrentHashMap<TopicPartition, OffsetAndMetadata> offsetMap;
    private final AtomicLong counter;
    private final long messagePollTimeout;
    private final long commitInterval;
    private final int commitBatchSize;
    private volatile long lastCommit;

    public KafkaListener(KafkaConsumer<K, V> kafkaConsumer,
                         RecordConsumer<K, V> recordConsumer,
                         List<String> topics,
                         long messagePollTimeout,
                         long commitInterval,
                         int commitBatchSize) {
        this.kafkaConsumer = kafkaConsumer;
        this.recordConsumer = recordConsumer;
        this.topics = topics;
        this.offsetMap = new ConcurrentHashMap<>();
        this.counter = new AtomicLong(1);
        this.messagePollTimeout = messagePollTimeout;
        this.commitInterval = commitInterval;
        this.commitBatchSize = commitBatchSize;
        this.lastCommit = System.currentTimeMillis();
    }

    @Override
    public void preProcess() {
        recordConsumer.prepare();
        kafkaConsumer.subscribe(topics, this);
    }

    @Override
    public void postProcess() {
        doCommit();
        kafkaConsumer.close();
        recordConsumer.flushAll();
        log.info("cleanUp finish.");
    }

    @Override
    public void process() {
        try {
            ConsumerRecords<K, V> records = kafkaConsumer.poll(Duration.ofMillis(messagePollTimeout));
            if (records.count() > 0) {
                log.debug("kafka consumed records: {}", records.count());
                for (ConsumerRecord<K, V> record : records) {
                    recordConsumer.consume(record);
                    commit(record);
                }
            } else {
                checkCommit();
            }
        } catch (WakeupException e) {
            log.info("finish KafkaListener loop.");
            stop();
        } catch (CommitFailedException e) {
            log.warn("commitFailedException at kafkaListener loop.", e);
        } catch (Exception e) {
            log.error("exception at kafkaListener loop.", e);
        } finally {
            recordConsumer.flushPending();
        }
    }

    private void commit(ConsumerRecord<K, V> record) {
        final String topic = record.topic();
        final int partition = record.partition();
        final long offset = record.offset();
        log.trace("commit: topic={}, partition={}, offset={}", topic, partition, offset);
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        OffsetAndMetadata offsetAndMetadata = new OffsetAndMetadata(offset + 1, "empty-meta");
        offsetMap.put(topicPartition, offsetAndMetadata);

        if (Math.abs(counter.getAndIncrement()) % commitBatchSize == 0) {
            doCommit();
        } else {
            checkCommit();
        }
    }

    private void checkCommit() {
        final long current = System.currentTimeMillis();
        final long last = lastCommit;
        if (current > last + commitInterval) {
            doCommit();
            lastCommit = current;
        }
    }

    private void doCommit() {
        if (!offsetMap.isEmpty()) {
            offsetMap.forEach((k, v) ->
                    log.info("doCommit: topic={}, partition={}, offset={}, leaderEpoch={}, metadata={}",
                            k.topic(), k.partition(), v.offset(), v.leaderEpoch(), v.metadata()));
            try {
                kafkaConsumer.commitSync(offsetMap);
                offsetMap.clear();
            } catch (Exception e) {
                log.error("failed doCommit.", e);
            }
        } else {
            log.debug("empty doCommit");
        }
    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> collection) {
        log.info("onPartitionsRevoked! {}", collection);
        doCommit();
        counter.set(1);
    }

    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> collection) {
        log.info("onPartitionsAssigned! {}", collection);
    }
}

