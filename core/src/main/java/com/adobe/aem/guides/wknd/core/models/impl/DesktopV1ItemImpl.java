package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.core.models.DesktopV1Item;

@Model(
        adaptables = {Resource.class},
        adapters = {DesktopV1Item.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DesktopV1ItemImpl implements DesktopV1Item {

    @ValueMapValue(name = "title")
    private String title;

    @ValueMapValue(name = "description")
    private String description;

    @Override
    public String getTitle() {
        return StringUtils.trimToNull(title);
    }

    @Override
    public String getDescription() {
        return StringUtils.trimToNull(description);
    }
}
