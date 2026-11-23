package com.adobe.aem.guides.wknd.core.models.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.adobe.aem.guides.wknd.core.models.DdRedeemD;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class DdRedeemDImplTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(DdRedeemDImpl.class);
        context.load().json("/com/adobe/aem/guides/wknd/core/models/impl/DdRedeemDImplTest.json", "/content");
    }

    private DdRedeemD adapt(String name) {
        context.currentResource("/content/" + name);
        DdRedeemD model = context.request().adaptTo(DdRedeemD.class);
        assertNotNull(model, "model should adapt from the request");
        return model;
    }

    @Test
    void returnsAuthoredVariant() {
        assertEquals("default", adapt("ddredeemd").getVariant());
    }

    @Test
    void fallsBackToDefaultVariantForUnsupportedValues() {
        assertEquals(DdRedeemDImpl.DEFAULT_VARIANT, adapt("ddredeemd-invalid-variant").getVariant());
    }

    @Test
    void exportsResourceType() {
        assertEquals("wknd/components/ddredeemd", adapt("ddredeemd").getExportedType());
    }
}
