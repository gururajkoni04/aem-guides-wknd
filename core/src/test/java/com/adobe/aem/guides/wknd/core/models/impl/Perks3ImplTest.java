package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.Perks3;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class Perks3ImplTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(Perks3Impl.class);
        context.load().json("/com/adobe/aem/guides/wknd/core/models/impl/Perks3ImplTest.json", "/content");
    }

    private Perks3 adapt(String name) {
        context.currentResource("/content/" + name);
        Perks3 model = context.request().adaptTo(Perks3.class);
        assertNotNull(model, "model should adapt from the request");
        return model;
    }

    @Test
    void returnsAuthoredVariant() {
        assertEquals("default", adapt("perks3").getVariant());
    }

    @Test
    void fallsBackToDefaultVariantForUnsupportedValues() {
        assertEquals(Perks3Impl.DEFAULT_VARIANT, adapt("perks3-invalid-variant").getVariant());
    }

    @Test
    void exportsResourceType() {
        assertEquals("wknd/components/perks3", adapt("perks3").getExportedType());
    }
}
