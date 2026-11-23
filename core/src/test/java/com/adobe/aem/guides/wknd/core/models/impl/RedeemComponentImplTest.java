package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.RedeemComponent;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class RedeemComponentImplTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(RedeemComponentImpl.class);
        context.load().json("/com/adobe/aem/guides/wknd/core/models/impl/RedeemComponentImplTest.json", "/content");
    }

    private RedeemComponent adapt(String name) {
        context.currentResource("/content/" + name);
        RedeemComponent model = context.request().adaptTo(RedeemComponent.class);
        assertNotNull(model, "model should adapt from the request");
        return model;
    }

    @Test
    void returnsAuthoredVariant() {
        assertEquals("default", adapt("redeemcomponent").getVariant());
    }

    @Test
    void fallsBackToDefaultVariantForUnsupportedValues() {
        assertEquals(RedeemComponentImpl.DEFAULT_VARIANT, adapt("redeemcomponent-invalid-variant").getVariant());
    }

    @Test
    void exportsResourceType() {
        assertEquals("wknd/components/redeemcomponent", adapt("redeemcomponent").getExportedType());
    }
}
