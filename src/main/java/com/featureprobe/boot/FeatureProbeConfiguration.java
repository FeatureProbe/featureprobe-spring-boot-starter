package com.featureprobe.boot;

import com.featureprobe.sdk.server.FPConfig;
import com.featureprobe.sdk.server.FeatureProbe;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties({FeatureProbeConfig.class})
public class FeatureProbeConfiguration {

    @Bean
    public FeatureProbe featureProbe(FeatureProbeConfig properties) throws InvalidPropertiesFormatException {
        long refreshInterval = Objects.isNull(properties.getRefreshInterval()) ? 5L : properties.getRefreshInterval();
        URL eventUrl;
        try {
            eventUrl = new URL(properties.getEventUrl());
        } catch (MalformedURLException e) {
            throw new InvalidPropertiesFormatException("Event url Error, " + e.getMessage());
        }
        URL synchronizerUrl;
        try {
            synchronizerUrl = new URL(properties.getSynchronizerUrl());
        } catch (MalformedURLException e) {
            throw new InvalidPropertiesFormatException("Synchronizer url Error, " + e.getMessage());
        }
        FPConfig config = FPConfig.builder()
                .useMemoryRepository()
                .eventUrl(eventUrl)
                .synchronizerUrl(synchronizerUrl)
                .pollingMode(Duration.ofSeconds(refreshInterval))
                .build();
        return new FeatureProbe(properties.getSdkKey(), config);
    }

}
