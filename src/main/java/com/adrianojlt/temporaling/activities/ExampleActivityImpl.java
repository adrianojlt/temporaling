package com.adrianojlt.temporaling.activities;

import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@ActivityImpl(workers = "hello-worker")
public class ExampleActivityImpl implements ExampleActivity {

    @Override
    public void hello() {
        log.info("Hello, World!");
    }
}
