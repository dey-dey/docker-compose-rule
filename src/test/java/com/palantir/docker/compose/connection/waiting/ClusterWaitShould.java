/*
 * Copyright 2016 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.palantir.docker.compose.connection.waiting;

import static com.palantir.docker.compose.connection.waiting.SuccessOrFailure.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.palantir.docker.compose.connection.Cluster;
import com.palantir.docker.compose.connection.ContainerCache;
import com.palantir.docker.compose.connection.ImmutableCluster;
import org.joda.time.Duration;
import org.junit.Test;

public class ClusterWaitShould {

    private static final Duration DURATION = Duration.standardSeconds(1);
    private static final String IP = "192.168.100.100";

    private final ContainerCache containerCache = mock(ContainerCache.class);
    private final ClusterHealthCheck clusterHealthCheck = mock(ClusterHealthCheck.class);

    private final Cluster cluster = ImmutableCluster.builder()
            .containers(containerCache)
            .ip(IP)
            .build();


    @Test public void
    return_when_a_cluster_is_ready() {
        when(clusterHealthCheck.isClusterHealthy(cluster)).thenReturn(success());
        ClusterWait wait = new ClusterWait(clusterHealthCheck, DURATION);
        wait.waitUntilReady(cluster);
    }
}
