package com.devdooly.skeleton.listener.zombie.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class ScopedValueService {

    // ScopedValue declaration
    public static final ScopedValue<String> CONTEXT_ID = ScopedValue.newInstance();

    public String processInScope() {
        // Retrieve ScopedValue. If not bound, it throws NoSuchElementException
        if (CONTEXT_ID.isBound()) {
            String contextId = CONTEXT_ID.get();
            log.info("[ScopedValueService] Processing with contextId: {}", contextId);
            return "Processed with " + contextId;
        } else {
            log.warn("[ScopedValueService] No contextId bound!");
            return "No context found";
        }
    }

    public void deepProcess() {
        if (CONTEXT_ID.isBound()) {
            log.info("[ScopedValueService] Deep processing with contextId: {}", CONTEXT_ID.get());
        }
    }
}
