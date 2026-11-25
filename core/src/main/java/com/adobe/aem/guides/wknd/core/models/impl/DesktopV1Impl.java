package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;

import com.adobe.aem.guides.wknd.core.models.DesktopV1;
import com.adobe.aem.guides.wknd.core.models.DesktopV1Item;
import com.adobe.cq.wcm.core.components.commons.link.Link;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.adobe.cq.wcm.core.components.models.Teaser;
import com.adobe.cq.wcm.core.components.models.datalayer.ComponentData;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {DesktopV1.class},
        resourceType = {DesktopV1Impl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class DesktopV1Impl implements DesktopV1 {
    protected static final String RESOURCE_TYPE = "wknd/components/desktopv1";

    @Self
    @Via(type = ResourceSuperType.class)
    private Teaser delegate;

    // Composite multifield node written by the dialog as ./earnTiles
    @ChildResource(name = "earnTiles")
    @Via("resource")
    private List<Resource> earnTileResources;

    private List<DesktopV1Item> earnTiles = Collections.emptyList();

    @PostConstruct
    private void init() {
        if (earnTileResources == null || earnTileResources.isEmpty()) {
            return;
        }
        final List<DesktopV1Item> items = new ArrayList<>();
        for (Resource earnTileResource : earnTileResources) {
            if (earnTileResource == null) {
                continue;
            }
            final DesktopV1Item item = earnTileResource.adaptTo(DesktopV1Item.class);
            // A tile without an earn rate has nothing to display
            if (item != null && item.getTitle() != null) {
                items.add(item);
            }
        }
        earnTiles = Collections.unmodifiableList(items);
    }

    @Override
    public List<DesktopV1Item> getEarnTiles() {
        return earnTiles;
    }

    @Override
    public boolean isEmpty() {
        // The heading is required by the design; Core Teaser may resolve it from a linked page
        return StringUtils.isBlank(getTitle());
    }

    @Override
    public boolean isActionsEnabled() {
        return delegate != null && delegate.isActionsEnabled();
    }

    @Override
    public List<ListItem> getActions() {
        if (delegate == null) {
            return Collections.emptyList();
        }
        final List<ListItem> actions = delegate.getActions();
        return actions == null ? Collections.emptyList() : actions;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Link getLink() {
        return delegate == null ? null : delegate.getLink();
    }

    @Override
    public Resource getImageResource() {
        return delegate == null ? null : delegate.getImageResource();
    }

    @Override
    public boolean isImageLinkHidden() {
        return delegate != null && delegate.isImageLinkHidden();
    }

    @Override
    public String getPretitle() {
        return delegate == null ? null : delegate.getPretitle();
    }

    @Override
    public String getTitle() {
        return delegate == null ? null : delegate.getTitle();
    }

    @Override
    public boolean isTitleLinkHidden() {
        return delegate != null && delegate.isTitleLinkHidden();
    }

    @Override
    public String getDescription() {
        return delegate == null ? null : delegate.getDescription();
    }

    @Override
    public String getTitleType() {
        return delegate == null ? null : delegate.getTitleType();
    }

    @Override
    public String getId() {
        return delegate == null ? null : delegate.getId();
    }

    @Override
    public ComponentData getData() {
        return delegate == null ? null : delegate.getData();
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
