/*
 * Copyright 2014-2015 the original author or authors.
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
package org.codehaus.griffon.runtime.swing.fatcowicons

import griffon.builder.swing.factory.FatcowIconFactory
import griffon.core.GriffonApplication
import griffon.core.test.GriffonUnitRule
import griffon.plugins.fatcowicons.Fatcow
import griffon.swing.support.fatcowicons.FatcowIcon
import griffon.util.BuilderCustomizer
import griffon.util.CompositeBuilder
import org.junit.Rule
import spock.lang.Specification
import spock.lang.Unroll

import javax.annotation.Nonnull
import javax.inject.Inject

import static griffon.util.AnnotationUtils.sortByDependencies

/**
 * @author Andres Almiray
 */
class FatcowIconsSwingGroovyModuleSpec extends Specification {
    @Rule
    public final GriffonUnitRule griffon = new GriffonUnitRule()

    @Inject
    private GriffonApplication application

    def 'Builder customizer is configured correctly'() {
        when:
        FactoryBuilderSupport builder = createBuilder(application)

        then:
        builder.factories.containsKey('fatcowIcon')
        builder.factories.fatcowIcon.class == FatcowIconFactory
    }

    @Unroll
    def 'Can create a FatcowIcon using the factory'() {
        given:
        FactoryBuilderSupport builder = createBuilder(application)

        when:
        FatcowIcon icon = builder.fatcowIcon(value, icon: fatcow)

        then:
        icon.fatcow == expected

        where:
        value      | fatcow     || expected
        Fatcow.RSS | null       || Fatcow.RSS
        null       | Fatcow.RSS || Fatcow.RSS
        null       | 'RSS'      || Fatcow.RSS
    }

    private static final String BUILDER_CUSTOMIZER = 'BuilderCustomizer'

    @Nonnull
    protected FactoryBuilderSupport createBuilder(@Nonnull GriffonApplication application) {
        Collection<BuilderCustomizer> customizers = resolveBuilderCustomizers(application)
        return new CompositeBuilder(customizers.toArray(new BuilderCustomizer[customizers.size()]))
    }

    @Nonnull
    private Collection<BuilderCustomizer> resolveBuilderCustomizers(@Nonnull GriffonApplication application) {
        Collection<BuilderCustomizer> customizerInstances = application.injector.getInstances(BuilderCustomizer)
        return sortByDependencies(customizerInstances, BUILDER_CUSTOMIZER, 'customizer').values()
    }
}
