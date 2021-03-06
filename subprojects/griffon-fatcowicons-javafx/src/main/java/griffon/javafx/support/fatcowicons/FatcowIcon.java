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
package griffon.javafx.support.fatcowicons;

import griffon.plugins.fatcowicons.Fatcow;
import javafx.scene.image.Image;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

/**
 * @author Andres Almiray
 */
public class FatcowIcon extends Image {
    private final Fatcow fatcow;
    private final int size;

    public FatcowIcon(@Nonnull Fatcow fatcow) {
        this(fatcow, 16);
    }

    public FatcowIcon(@Nonnull Fatcow fatcow, int size) {
        super(toURL(fatcow, size), true);
        this.fatcow = fatcow;
        this.size = size;
    }

    public FatcowIcon(@Nonnull String description) {
        this(Fatcow.findByDescription(description));
    }

    @Nonnull
    private static String toURL(@Nonnull Fatcow fatcow, int size) {
        requireNonNull(fatcow, "Argument 'fatcow' must not be null.");
        String resource = fatcow.asResource(size);
        return Thread.currentThread().getContextClassLoader().getResource(resource).toExternalForm();
    }

    @Nonnull
    public Fatcow getFatcow() {
        return fatcow;
    }

    public int getSize() {
        return size;
    }
}