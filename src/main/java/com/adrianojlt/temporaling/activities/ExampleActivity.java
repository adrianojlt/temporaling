package com.adrianojlt.temporaling.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ExampleActivity {

    @ActivityMethod
    public void hello();
}
