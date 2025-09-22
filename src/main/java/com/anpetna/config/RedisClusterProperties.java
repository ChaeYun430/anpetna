package com.anpetna.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisClusterProperties {

    /** 기본 타임아웃 5초 */
    private Duration timeout = Duration.ofMillis(5000);

    /** 기본 클러스터 노드 */
    private Cluster cluster = new Cluster();

    public static class Cluster {
        // 기본 노드 목록
        private List<String> nodes = Arrays.asList(
                "172.28.141.159:7000",
                "172.28.141.159:7001",
                "172.28.141.159:7002",
                "172.28.141.159:7003",
                "172.28.141.159:7004",
                "172.28.141.159:7005"
        );

        public List<String> getNodes() { return nodes; }
        public void setNodes(List<String> nodes) { this.nodes = nodes; }
    }

    public Duration getTimeout() { return timeout; }
    public void setTimeout(Duration timeout) { this.timeout = timeout; }

    public Cluster getCluster() { return cluster; }
    public void setCluster(Cluster cluster) { this.cluster = cluster; }
}
