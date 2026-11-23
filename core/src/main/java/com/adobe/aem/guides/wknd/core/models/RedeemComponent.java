package com.adobe.aem.guides.wknd.core.models;


import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;

/**
 * Redeem Component component model.
 * Generated from Figma node 5377:898.
 */
public interface RedeemComponent extends ComponentExporter {

    /** Variant key; always one of the supported values, never null. */
    @NotNull
    String getVariant();
}
