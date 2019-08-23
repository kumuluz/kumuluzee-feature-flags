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
package com.kumuluz.ee.feature.flags.common.utils;

import com.kumuluz.ee.feature.flags.common.annotations.FFConnection;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
import java.util.ArrayList;
import java.util.List;

/**
 * An interface that finds all classes annotated with @FFConnection
 *
 * @author Blaž Mrak
 * @since 1.0.0
 */
public interface ConnectionUtilInitializer extends Extension {

    List<AnnotatedClass> classList = new ArrayList<>();

    default <X> void processStreamListeners(@Observes ProcessBean<X> pat) {
        if (pat.getBean().getBeanClass().getAnnotation(FFConnection.class) != null) {
            classList.add(new AnnotatedClass(pat.getBean()));
        }
    }

    void after(@Observes AfterDeploymentValidation adv, BeanManager bm);
}
