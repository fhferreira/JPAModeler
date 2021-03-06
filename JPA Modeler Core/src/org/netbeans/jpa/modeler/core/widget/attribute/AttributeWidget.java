/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.core.widget.attribute;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.lang.model.SourceVersion;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import org.netbeans.jpa.modeler.core.widget.FlowPinWidget;
import org.netbeans.jpa.modeler.core.widget.PersistenceClassWidget;
import org.netbeans.jpa.modeler.properties.PropertiesHandler;
import static org.netbeans.jpa.modeler.properties.PropertiesHandler.getFieldTypeProperty;
import org.netbeans.jpa.modeler.rules.attribute.AttributeValidator;
import org.netbeans.jpa.modeler.rules.entity.SQLKeywords;
import org.netbeans.jpa.modeler.spec.EmbeddedId;
import org.netbeans.jpa.modeler.spec.extend.Attribute;
import org.netbeans.jpa.modeler.spec.extend.CollectionTypeHandler;
import org.netbeans.jpa.modeler.spec.jaxb.JaxbVariableTypeHandler;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.modeler.resource.toolbar.ImageUtil;
import org.netbeans.modeler.specification.model.document.core.IBaseElement;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.IPNodeWidget;
import org.netbeans.modeler.widget.pin.info.PinWidgetInfo;
import org.netbeans.modeler.widget.properties.generic.ElementCustomPropertySupport;
import org.netbeans.modeler.widget.properties.handler.PropertyChangeListener;
import org.netbeans.modules.j2ee.persistence.dd.JavaPersistenceQLKeywords;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

/**
 *
 * @author Gaurav Gupta
 * @param <E>
 */
public abstract class AttributeWidget<E extends Attribute> extends FlowPinWidget<E, JPAModelerScene> {

    public AttributeWidget(JPAModelerScene scene, IPNodeWidget nodeWidget, PinWidgetInfo pinWidgetInfo) {
        super(scene, nodeWidget, pinWidgetInfo);
        this.addPropertyChangeListener("name", (PropertyChangeListener<String>) (String value) -> {
            if (value == null || value.trim().isEmpty()) {
                JOptionPane.showMessageDialog(WindowManager.getDefault().getMainWindow(), NbBundle.getMessage(AttributeValidator.class, AttributeValidator.EMPTY_ATTRIBUTE_NAME));
                setName(AttributeWidget.this.getLabel());//rollback
            } else {
                setName(value);
                setLabel(value);
            }
        });
//        this.addPropertyChangeListener("description", (PropertyChangeListener<String>) (String value) -> {
//                setToolTipText(value);
//        });

        this.addPropertyChangeListener("table_name", (PropertyChangeListener<String>) (String tableName) -> {
            if (tableName != null && !tableName.trim().isEmpty()) {
                if (SQLKeywords.isSQL99ReservedKeyword(tableName)) {
                    errorHandler.throwError(AttributeValidator.ATTRIBUTE_TABLE_NAME_WITH_RESERVED_SQL_KEYWORD);
                } else {
                    errorHandler.clearError(AttributeValidator.ATTRIBUTE_TABLE_NAME_WITH_RESERVED_SQL_KEYWORD);
                }
            } else {
                errorHandler.clearError(AttributeValidator.ATTRIBUTE_TABLE_NAME_WITH_RESERVED_SQL_KEYWORD);
            }
        });
        this.addPropertyChangeListener("column_name", (PropertyChangeListener<String>) (String columnName) -> {
            if (columnName != null && !columnName.trim().isEmpty()) {
                if (SQLKeywords.isSQL99ReservedKeyword(columnName)) {
                    errorHandler.throwError(AttributeValidator.ATTRIBUTE_COLUMN_NAME_WITH_RESERVED_SQL_KEYWORD);
                } else {
                    errorHandler.clearError(AttributeValidator.ATTRIBUTE_COLUMN_NAME_WITH_RESERVED_SQL_KEYWORD);
                }
            } else {
                errorHandler.clearError(AttributeValidator.ATTRIBUTE_COLUMN_NAME_WITH_RESERVED_SQL_KEYWORD);
            }
        });

        this.addPropertyChangeListener("collectionType", (PropertyChangeListener<String>) (String collectionType) -> {
            Attribute attribute = getBaseElementSpec();
            boolean valid = false;
            try {
                if (collectionType != null && !collectionType.trim().isEmpty()) {
                    if (java.util.Collection.class.isAssignableFrom(Class.forName(collectionType.trim()))) {
                        valid = true;
                    }
                }
            } catch (ClassNotFoundException ex) {
                //skip allow = false;
            }
            if (!valid) {
                collectionType = java.util.Collection.class.getName();
            }

            ((CollectionTypeHandler) attribute).setCollectionType(collectionType.trim());
            setAttributeTooltip();

        });

    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        if (!(this.getBaseElementSpec() instanceof EmbeddedId)) {//to hide property
            set.put("BASIC_PROP", getFieldTypeProperty(this));
        } else {
            try {//add "custom manual editable class type property" instead of "Field Type Panel" for EmbeddedId
                set.put("BASIC_PROP", new ElementCustomPropertySupport(set.getModelerFile(), this.getClassWidget().getBaseElementSpec(), String.class,
                       "compositePrimaryKeyClass", "compositePrimaryKeyClass", "Field Type", "", null));
            } catch (NoSuchMethodException | NoSuchFieldException ex) {
                this.getModelerScene().getModelerFile().handleException(ex);;
            }
        }
        PropertiesHandler.getJaxbVarTypeProperty(set, this, (JaxbVariableTypeHandler) this.getBaseElementSpec());
    }
   
    public static <T> T getInstance(IPNodeWidget nodeWidget, String name, IBaseElement baseElement, Class documentId) {
        PinWidgetInfo pinWidgetInfo = new PinWidgetInfo(baseElement.getId(), baseElement);
        pinWidgetInfo.setName(name);
        pinWidgetInfo.setDocumentId(documentId.getSimpleName());
        return (T)nodeWidget.createPinWidget(pinWidgetInfo);
    }

    @Override
    protected List<JMenuItem> getPopupMenuItemList() {
        List<JMenuItem> menuList = super.getPopupMenuItemList();

        JMenuItem delete;
        delete = new JMenuItem("Delete");
        delete.setIcon(ImageUtil.getInstance().getIcon("delete.png"));
        delete.addActionListener((ActionEvent e) -> {
            AttributeWidget.this.remove(true);
        });

        menuList.add(0, delete);

        return menuList;
    }

    @Override
    public void init() {
        setAttributeTooltip();
        this.getClassWidget().scanDuplicateAttributes(null, this.name);
        validateName(null, this.getName());
    }

    @Override
    public void destroy() {
        this.getClassWidget().scanDuplicateAttributes(this.name, null);
    }
    private void validateName(String previousName,String name){
        if (JavaPersistenceQLKeywords.isKeyword(name)) {
            errorHandler.throwError(AttributeValidator.ATTRIBUTE_NAME_WITH_JPQL_KEYWORD);
        } else {
            errorHandler.clearError(AttributeValidator.ATTRIBUTE_NAME_WITH_JPQL_KEYWORD);
        }
        if(SourceVersion.isName(name)){
            errorHandler.clearError(AttributeValidator.INVALID_ATTRIBUTE_NAME);
        } else {
            errorHandler.throwError(AttributeValidator.INVALID_ATTRIBUTE_NAME);
        }
        this.getClassWidget().scanDuplicateAttributes(previousName, name);

    }
    @Override
    public void setName(String name) {
        String previousName = this.name;
        if (name != null && !name.trim().isEmpty()) {
            this.name = name.replaceAll("\\s+", "");
            if (this.getModelerScene().getModelerFile().isLoaded()) {
                getBaseElementSpec().setName(this.name);
            }
        }
       validateName(previousName,this.getName());
    }

    @Override
    public void setLabel(String label) {
        if (label != null && !label.trim().isEmpty()) {
            this.setPinName(label.replaceAll("\\s+", ""));
        }
    }

    @Override
    public void createVisualPropertySet(ElementPropertySet elementPropertySet) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected abstract void setAttributeTooltip();



    /**
     * @return the classWidget
     */
    public PersistenceClassWidget getClassWidget() {
        return (PersistenceClassWidget) this.getPNodeWidget();
    }


}
