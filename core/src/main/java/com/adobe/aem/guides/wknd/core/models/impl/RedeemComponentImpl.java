package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.Set;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.jetbrains.annotations.NotNull;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.aem.guides.wknd.core.models.RedeemComponent;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = {RedeemComponent.class, ComponentExporter.class},
    resourceType = RedeemComponentImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class RedeemComponentImpl implements RedeemComponent {

    static final String RESOURCE_TYPE = "wknd/components/redeemcomponent";
    static final String DEFAULT_VARIANT = "default";
    private static final Set<String> VARIANTS = Set.of("default");

    @ValueMapValue(name = "variant")
    private String variant;

    @Override
    @NotNull
    public String getVariant() {
        return variant != null && VARIANTS.contains(variant) ? variant : DEFAULT_VARIANT;
    }

    @Override
    @NotNull
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
