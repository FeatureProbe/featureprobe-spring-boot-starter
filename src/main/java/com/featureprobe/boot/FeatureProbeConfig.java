package com.featureprobe.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "spring.featureprobe")
public class FeatureProbeConfig {

    @NotBlank
    private String sdkKey;

    @NotBlank
    private String synchronizerUrl;

    @NotBlank
    private String eventUrl;

    @Min(0)
    private Long startWait;

    private Long refreshInterval;

    public String getSdkKey() {
        return sdkKey;
    }

    public void setSdkKey(String sdkKey) {
        this.sdkKey = sdkKey;
    }

    public String getSynchronizerUrl() {
        return synchronizerUrl;
    }

    public void setSynchronizerUrl(String synchronizerUrl) {
        this.synchronizerUrl = synchronizerUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public void setEventUrl(String eventUrl) {
        this.eventUrl = eventUrl;
    }

    public Long getStartWait() {
        return startWait;
    }

    public void setStartWait(Long startWait) {
        this.startWait = startWait;
    }

    public Long getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Long refreshInterval) {
        this.refreshInterval = refreshInterval;
    }
}
