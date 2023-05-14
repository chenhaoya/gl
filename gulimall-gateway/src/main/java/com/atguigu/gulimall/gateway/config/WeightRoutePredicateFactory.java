package com.atguigu.gulimall.gateway.config;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.cloud.gateway.event.WeightDefinedEvent;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
import org.springframework.cloud.gateway.support.WeightConfig;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.server.ServerWebExchange;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_PREDICATE_ROUTE_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.WEIGHT_ATTR;

/**
 * @author ChenHao
 * @date 2023/5/12 22:05
 */
public class WeightRoutePredicateFactory
        extends AbstractRoutePredicateFactory<WeightConfig>
        implements ApplicationEventPublisherAware {

    /**
     * 权重配置组密钥。
     */
    public static final String GROUP_KEY = WeightConfig.CONFIG_PREFIX + ".group";

    /**
     * 权重配置权重键。
     */
    public static final String WEIGHT_KEY = WeightConfig.CONFIG_PREFIX + ".weight";

    private static final Log log = LogFactory.getLog(org.springframework.cloud.gateway.handler.predicate.WeightRoutePredicateFactory.class);

    private ApplicationEventPublisher publisher;

    public WeightRoutePredicateFactory() {
        super(WeightConfig.class);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(GROUP_KEY, WEIGHT_KEY);
    }

    @Override
    public String shortcutFieldPrefix() {
        return WeightConfig.CONFIG_PREFIX;
    }

    @Override
    public void beforeApply(WeightConfig config) {
        if (publisher != null) {
            publisher.publishEvent(new WeightDefinedEvent(this, config));
        }
    }

    @Override
    public Predicate<ServerWebExchange> apply(WeightConfig config) {
        return new GatewayPredicate() {
            @Override
            public boolean test(ServerWebExchange exchange) {
                Map<String, String> weights = exchange.getAttributeOrDefault(WEIGHT_ATTR,
                        Collections.emptyMap());

                String routeId = exchange.getAttribute(GATEWAY_PREDICATE_ROUTE_ATTR);

                //所有计算和与随机数的比较都发生在 WeightCalculatorWebFilter 中
                String group = config.getGroup();
                if (weights.containsKey(group)) {

                    String chosenRoute = weights.get(group);
                    if (log.isTraceEnabled()) {
                        log.trace("in group weight: " + group + ", current route: "
                                + routeId + ", chosen route: " + chosenRoute);
                    }

                    return routeId.equals(chosenRoute);
                }
                else if (log.isTraceEnabled()) {
                    log.trace("no weights found for group: " + group + ", current route: "
                            + routeId);
                }

                return false;
            }

            @Override
            public String toString() {
                return String.format("Weight: %s %s", config.getGroup(),
                        config.getWeight());
            }
        };
    }

}
