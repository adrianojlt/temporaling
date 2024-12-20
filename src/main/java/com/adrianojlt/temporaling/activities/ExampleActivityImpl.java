package com.adrianojlt.temporaling.activities;

import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ActivityImpl(workers = "hello-worker")
public class ExampleActivityImpl implements ExampleActivity {

    @Override
    public void hello() {
        System.out.println("Hello, World!");
    }
}
