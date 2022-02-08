package com.example.apollo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.eonliu.apollo.Apollo;
import com.eonliu.apollo.kernel.ApolloInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eon Liu
 */
public class SampleInitializer implements Initializer<Integer> {
    @NonNull
    @Override
    public Integer create(@NonNull Context context) {
        Apollo.setLogEnable(BuildConfig.DEBUG);
        return 0;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        List<Class<? extends Initializer<?>>> dependencies = new ArrayList<>();
        // 这里需要依赖ApolloInitializer，使其先进行初始化，然后再初始化create中的内容。
        dependencies.add(ApolloInitializer.class);
        return dependencies;
    }
}
