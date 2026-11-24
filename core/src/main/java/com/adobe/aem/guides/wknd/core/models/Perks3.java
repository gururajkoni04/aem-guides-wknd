package com.adobe.aem.guides.wknd.core.models;


import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;

/**
 * Perks3 component model.
 * Generated from Figma node 5377:869.
 */
public interface Perks3 extends ComponentExporter {

    /** Variant key; always one of the supported values, never null. */
    @NotNull
    String getVariant();
}
