/*
 *  Copyright (c) 2014-2019 Kumuluz and/or its affiliates
 *  and other contributors as indicated by the @author tags and
 *  the contributor list.
 *
 *  Licensed under the MIT License (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  https://opensource.org/licenses/MIT
 *
 *  The software is provided "AS IS", WITHOUT WARRANTY OF ANY KIND, express or
 *  implied, including but not limited to the warranties of merchantability,
 *  fitness for a particular purpose and noninfringement. in no event shall the
 *  authors or copyright holders be liable for any claim, damages or other
 *  liability, whether in an action of contract, tort or otherwise, arising from,
 *  out of or in connection with the software or the use or other dealings in the
 *  software. See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.kumuluz.ee.feature.flags.unleash.utils;

import com.kumuluz.ee.common.config.EeConfig;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.kumuluz.ee.feature.flags.common.utils.AnnotatedClass;
import com.kumuluz.ee.feature.flags.common.utils.ConnectionUtilInitializer;
import no.finn.unleash.DefaultUnleash;
import no.finn.unleash.Unleash;
import no.finn.unleash.util.UnleashConfig;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import java.lang.reflect.Field;
import java.util.logging.Logger;

/**
 * A class which initializes the connection to the Unleash server
 *
 * @author Blaž Mrak
 * @since 1.0.0
 */
public class UnleashFeatureFlagsInitializer implements ConnectionUtilInitializer {

    private static Logger logger = Logger.getLogger(UnleashFeatureFlagsInitializer.class.getName());
    private static Unleash unleash;

    @Override
    public void after(@Observes AfterDeploymentValidation adv, BeanManager bm) {
        UnleashConfig config = null;
        if (classList.size() <= 1) {
            for (AnnotatedClass bean : classList) {
                for (Field field : bean.getBean().getBeanClass().getDeclaredFields()) {
                    if (field.getType() == UnleashConfig.class) {
                        try {
                            field.setAccessible(true);
                            config = (UnleashConfig) field.get(bm.getReference(bean.getBean(),
                                    bean.getBean().getBeanClass(), bm.createCreationalContext(bean.getBean())));
                            logger.info("Found UnleashConfig in " + bean.getBean().getBeanClass().getName());
                        } catch (IllegalAccessException e) {
                            logger.severe("An exception occurred while processing annotations: " + e.getMessage());
                        }
                    }
                }
                if (config == null) {
                    logger.severe("UnleashConfig not found in class " + bean.getBean().getBeanClass().getName());
                    return;
                }
            }
        } else {
            logger.severe("Found multiple classes annotated with @FFConfig.");
            return;
        }

        if (config == null) {
            String appName = EeConfig.getInstance().getName();
            String unleashApi =
                    ConfigurationUtil.getInstance().get("kumuluzee.feature-flags.unleash.unleash-api").orElse(null);
            if (unleashApi != null) {
                config = new UnleashConfig.Builder()
                        .unleashAPI(unleashApi)
                        .appName(appName)
                        .build();
            } else {
                logger.severe("No Unleash connection defined");
                return;
            }
        }

        unleash = new DefaultUnleash(config);

        logger.info("Initialized Unleash connection");
    }

    public static Unleash getUnleash() {
        return unleash;
    }
}
