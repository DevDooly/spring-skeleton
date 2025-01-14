package com.devdooly.skeleton.listener.zombie.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ZombieProperties {
    public final long messagePollTimeout;   // 메시지 poll 타임아웃
    public final long commitInterval;       // 최대 커밋 인터벌
    public final int commitBatchSize;       // 메시지 커밋 배치 크기
    public final int maxPollRecords;        // 최대 컨슘 레코드 수

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
