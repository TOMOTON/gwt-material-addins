/*
 * #%L
 * GwtMaterial
 * %%
 * Copyright (C) 2015 - 2016 GwtMaterialDesign
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package gwt.material.design.addins.client;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.base.MaterialAddinsTest;
import gwt.material.design.addins.client.base.constants.AddinsCssName;
import gwt.material.design.addins.client.combobox.MaterialComboBox;
import gwt.material.design.addins.client.combobox.events.RemoveItemEvent;
import gwt.material.design.addins.client.dto.User;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.html.Label;
import gwt.material.design.client.ui.html.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Test case for combobox component
 *
 * @author kevzlou7979
 */
public class MaterialComboBoxTest extends MaterialAddinsTest {

    public void init() {
        MaterialComboBox<User> comboBox = new MaterialComboBox<>();
        checkWidget(comboBox);
        checkEvents(comboBox);
        checkProperties(comboBox);
    }

    protected <T extends MaterialComboBox<User>> void checkProperties(T comboBox) {
        comboBox.setReadOnly(true);
        assertTrue(comboBox.isReadOnly());
        comboBox.setReadOnly(false);
        assertFalse(comboBox.isReadOnly());
        comboBox.setMultiple(true);
        assertTrue(comboBox.isMultiple());
        comboBox.setMultiple(false);
        assertFalse(comboBox.isMultiple());
        comboBox.setToggleReadOnly(true);
        assertTrue(comboBox.isToggleReadOnly());
        comboBox.setToggleReadOnly(false);
        assertFalse(comboBox.isToggleReadOnly());
        List<User> users = new ArrayList<>();
        users.add(new User());
        comboBox.setAcceptableValues(users);
        comboBox.setHideSearch(true);
        assertTrue(comboBox.isHideSearch());
        comboBox.setLimit(10);
        assertEquals(comboBox.getLimit(), 10);
        comboBox.setLabel("label");
        assertEquals(comboBox.getLabel().getText(), "label");
        comboBox.setPlaceholder("placeholder");
        assertEquals(comboBox.getPlaceholder(), "placeholder");
    }

    protected <T extends MaterialComboBox> void checkEvents(T comboBox) {
        comboBox.setEnabled(true);
        // Open Handler
        checkOpenHandler(comboBox);
        // Close Handler
        checkCloseHandler(comboBox);
        // Remove Item Handler
        final boolean[] isRemoveItemEvent = {false};
        comboBox.addRemoveItemHandler(event -> {
            isRemoveItemEvent[0] = true;
        });
        comboBox.fireEvent(new GwtEvent<RemoveItemEvent.RemoveItemHandler<?>>() {
            @Override
            public Type<RemoveItemEvent.RemoveItemHandler<?>> getAssociatedType() {
                return RemoveItemEvent.getType();
            }

            @Override
            protected void dispatch(RemoveItemEvent.RemoveItemHandler<?> eventHandler) {
                eventHandler.onRemoveItem(null);
            }
        });
        assertTrue(isRemoveItemEvent[0]);
        // Selection Handler
        final boolean[] isSelectionEvent = {false};
        comboBox.addSelectionHandler(event -> {
            isSelectionEvent[0] = true;
        });
        comboBox.fireEvent(new GwtEvent<SelectionHandler<?>>() {
            @Override
            public Type<SelectionHandler<?>> getAssociatedType() {
                return SelectionEvent.getType();
            }

            @Override
            protected void dispatch(SelectionHandler<?> eventHandler) {
                eventHandler.onSelection(null);
            }
        });
        assertTrue(isSelectionEvent[0]);
        // Value Change Handler
        final boolean[] isValueChangeEvent = {false};
        comboBox.addValueChangeHandler(event -> {
            isValueChangeEvent[0] = true;
        });
        comboBox.fireEvent(new GwtEvent<ValueChangeHandler<?>>() {
            @Override
            public Type<ValueChangeHandler<?>> getAssociatedType() {
                return ValueChangeEvent.getType();
            }

            @Override
            protected void dispatch(ValueChangeHandler<?> eventHandler) {
                eventHandler.onValueChange(null);
            }
        });
        assertTrue(isValueChangeEvent[0]);
    }

    @Override
    protected <T extends MaterialWidget & HasEnabled> void checkEnabled(T widget) {
        MaterialComboBox<User> comboBox = new MaterialComboBox<>();
        super.checkEnabled(comboBox, comboBox.getListbox());
    }

    @Override
    protected <T extends MaterialWidget> void checkChildren(T widget) {
        checkAddItemOption();
    }

    protected void checkAddItemOption() {
        MaterialComboBox<String> comboBox = new MaterialComboBox<>();
        RootPanel.get().add(comboBox);
        // Check Initial children
        assertEquals(comboBox.getValues().size(), 0);
        assertEquals(comboBox.getChildren().size(), 3);
        // Check simple String object
        for (int i = 1; i <= 5; i++) {
            comboBox.addItem("item" + i);
        }
        assertEquals(comboBox.getValues().size(), 5);
        // Check ListBox
        assertNotNull(comboBox.getWidget(0));
        assertTrue(comboBox.getWidget(0) instanceof MaterialWidget);
        assertEquals(comboBox.getListbox(), comboBox.getWidget(0));
        MaterialWidget listBox = comboBox.getListbox();
        assertEquals(listBox.getWidgetCount(), 5);
        for (Widget w : listBox) {
            assertNotNull(w);
            assertTrue(w instanceof Option);
        }
        // Check Label
        assertNotNull(comboBox.getWidget(1));
        assertTrue(comboBox.getWidget(1) instanceof Label);
        Label lblTitle = (Label) comboBox.getWidget(1);
        assertTrue(lblTitle.getElement().hasClassName(AddinsCssName.SELECT2LABEL));
        // Check Error Label
        assertNotNull(comboBox.getWidget(2));
        assertTrue(comboBox.getWidget(2) instanceof MaterialLabel);
    }

    @Override
    protected <T extends MaterialWidget> void checkInteractionEvents(T widget, boolean enabled) {
        checkEvents(new MaterialComboBox());
    }
}
