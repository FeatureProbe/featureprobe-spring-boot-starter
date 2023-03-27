package com.featureprobe.boot;

import com.featureprobe.sdk.server.FPConfig;
import com.featureprobe.sdk.server.FeatureProbe;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({FeatureProbeConfig.class})
public class FeatureProbeConfiguration {

    @Bean
    public FeatureProbe featureProbe(FeatureProbeConfig properties) throws InvalidPropertiesFormatException {
        FPConfig config;
        long refreshInterval = Objects.isNull(properties.getRefreshInterval()) ? 5L : properties.getRefreshInterval();
        long startWait = Objects.isNull(properties.getStartWait()) ? 10L : properties.getStartWait();
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

        String remoteUri = properties.getRealtimeUri();
        if (StringUtils.isNotBlank(remoteUri)) {
            try {
                config = FPConfig.builder()
                        .useMemoryRepository()
                        .eventUrl(eventUrl)
                        .synchronizerUrl(synchronizerUrl)
                        .realtimeUri(new URI(remoteUri))
                        .streamingMode(Duration.ofSeconds(refreshInterval))
                        .startWait(startWait, TimeUnit.SECONDS)
                        .build();
            } catch (URISyntaxException e) {
                throw new InvalidPropertiesFormatException("RemoteUri Error, " + e.getMessage());
            }
        } else {
          config  = FPConfig.builder()
                    .useMemoryRepository()
                    .eventUrl(eventUrl)
                    .synchronizerUrl(synchronizerUrl)
                    .pollingMode(Duration.ofSeconds(refreshInterval))
                    .startWait(startWait, TimeUnit.SECONDS)
                    .build();
        }

        return new FeatureProbe(properties.getSdkKey(), config);
    }

}
