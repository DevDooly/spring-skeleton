package com.devdooly.skeleton.listener.zombie.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZombieProperties {
    public long messagePollTimeout = 5000L;   // 메시지 poll 타임아웃
    public long commitInterval = 10000L;       // 최대 커밋 인터벌
    public int commitBatchSize = 100;       // 메시지 커밋 배치 크기
    public int maxPollRecords = 500;        // 최대 컨슘 레코드 수

    @Override
    public String toString() {
        return System.lineSeparator()
                + "===========================" + System.lineSeparator()
                + " messagePollTimeout=" + messagePollTimeout + System.lineSeparator()
                + " commitInterval=" + commitInterval + System.lineSeparator()
                + " commitBatchSize=" + commitBatchSize + System.lineSeparator()
                + " maxPollRecords=" + maxPollRecords + System.lineSeparator()
                + "===========================";
    }
}
