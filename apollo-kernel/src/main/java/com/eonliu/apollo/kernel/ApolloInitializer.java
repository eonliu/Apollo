package com.eonliu.apollo.kernel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.eonliu.apollo.Apollo;
import com.eonliu.apollo.Environments;

import java.util.Collections;
import java.util.List;

/**
 * @author Eon Liu
 */
public class ApolloInitializer implements Initializer<Integer> {

    @NonNull
    @Override
    public Integer create(@NonNull Context context) {
        Apollo.setContext(context);
        Environments.getInstance().init(context);
        return 0;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
