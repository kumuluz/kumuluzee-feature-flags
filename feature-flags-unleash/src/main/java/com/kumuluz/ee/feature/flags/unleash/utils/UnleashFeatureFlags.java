package com.kumuluz.ee.feature.flags.unleash.utils;

import com.kumuluz.ee.feature.flags.common.utils.FeatureFlags;
import no.finn.unleash.Unleash;
import no.finn.unleash.UnleashContext;
import no.finn.unleash.Variant;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UnleashFeatureFlags implements FeatureFlags {

    private Unleash unleash = UnleashFeatureFlagsInitializer.getUnleash();

    public boolean isEnabled(String s) {
        return unleash.isEnabled(s);
    }

    public boolean isEnabled(String s, boolean b) {
        return unleash.isEnabled(s, b);
    }

    public boolean isEnabled(String toggleName, UnleashContext context) {
        return unleash.isEnabled(toggleName, context);
    }

    public boolean isEnabled(String toggleName, UnleashContext context, boolean defaultSetting) {
        return unleash.isEnabled(toggleName, context, defaultSetting);
    }

    public Variant getVariant(String s, UnleashContext unleashContext) {
        return unleash.getVariant(s, unleashContext);
    }

    public Variant getVariant(String s, UnleashContext unleashContext, Variant variant) {
        return unleash.getVariant(s, unleashContext, variant);
    }

    public Variant getVariant(String s) {
        return unleash.getVariant(s);
    }

    public Variant getVariant(String s, Variant variant) {
        return unleash.getVariant(s, variant);
    }

    public List<String> getFeatureToggleNames() {
        return unleash.getFeatureToggleNames();
    }
}
