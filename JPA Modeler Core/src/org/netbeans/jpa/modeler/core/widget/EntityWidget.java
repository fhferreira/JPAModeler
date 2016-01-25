/**
 * Copyright [2013] Gaurav Gupta
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
package org.netbeans.jpa.modeler.core.widget;

import java.util.List;
import javax.swing.JMenuItem;
import static org.netbeans.jpa.modeler.core.widget.InheritenceStateType.BRANCH;
import static org.netbeans.jpa.modeler.core.widget.InheritenceStateType.LEAF;
import static org.netbeans.jpa.modeler.core.widget.InheritenceStateType.ROOT;
import static org.netbeans.jpa.modeler.core.widget.InheritenceStateType.SINGLETON;
import org.netbeans.jpa.modeler.core.widget.flow.GeneralizationFlowWidget;
import org.netbeans.jpa.modeler.properties.PropertiesHandler;
import org.netbeans.jpa.modeler.properties.inheritence.InheritencePanel;
import org.netbeans.jpa.modeler.rules.entity.EntityValidator;
import org.netbeans.jpa.modeler.spec.Attributes;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.InheritanceType;
import org.netbeans.jpa.modeler.spec.Table;
import org.netbeans.jpa.modeler.spec.extend.InheritenceHandler;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.jpa.modeler.specification.model.util.JPAModelerUtil;
import org.netbeans.modeler.properties.embedded.EmbeddedDataListener;
import org.netbeans.modeler.properties.embedded.EmbeddedPropertySupport;
import org.netbeans.modeler.properties.embedded.GenericEmbedded;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.info.NodeWidgetInfo;
import org.netbeans.modeler.widget.properties.handler.PropertyVisibilityHandler;

public class EntityWidget extends PrimaryKeyContainerWidget<Entity> {

    private Boolean abstractEntity;
    
    public EntityWidget(JPAModelerScene scene, NodeWidgetInfo nodeWidgetInfo) {
        super(scene, nodeWidgetInfo);
        
        this.addPropertyVisibilityHandler("inheritence", (PropertyVisibilityHandler<String>) () -> {
            GeneralizationFlowWidget outgoingGeneralizationFlowWidget1 = EntityWidget.this.getOutgoingGeneralizationFlowWidget();
            List<GeneralizationFlowWidget> incomingGeneralizationFlowWidgets1 = EntityWidget.this.getIncomingGeneralizationFlowWidgets();
            if (outgoingGeneralizationFlowWidget1 != null && !(outgoingGeneralizationFlowWidget1.getSuperclassWidget() instanceof EntityWidget)) {
                outgoingGeneralizationFlowWidget1 = null;
            }
            if (outgoingGeneralizationFlowWidget1 != null || !incomingGeneralizationFlowWidgets1.isEmpty()) {
                return true;
            }
            return false;
        });
        
       this.addPropertyChangeListener("abstract", (input) -> changeAbstractionIcon((Boolean) input));
        

    }

    @Override
    public void init() {
        Entity entity = this.getBaseElementSpec();
        if (entity.getAttributes().getAllAttribute().isEmpty()) {
            addNewIdAttribute("id");
            sortAttributes();
        }

        if (entity.getClazz() == null || entity.getClazz().isEmpty()) {
            entity.setClazz(this.getModelerScene().getNextClassName("Entity_"));
        }
            
        setName(entity.getClazz());
        setLabel(entity.getClazz());
        changeAbstractionIcon(entity.getAbstract());
        scanPrimaryKeyError();
//        
    }
    
    private void changeAbstractionIcon(Boolean _abstract){
        if (_abstract) {
            this.setImage(JPAModelerUtil.ABSTRACT_ENTITY);
        } else {
            this.setImage(JPAModelerUtil.ENTITY);
        }
    }

    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        Entity entity = this.getBaseElementSpec();
        if (entity.getTable() == null) {
            entity.setTable(new Table());
        }
        
        set.createPropertySet( this , entity.getTable(), getPropertyChangeListeners());

        if (entity instanceof InheritenceHandler) {
            set.put("BASIC_PROP", getInheritenceProperty());
        }
        set.put("BASIC_PROP", PropertiesHandler.getNamedQueryProperty("NamedQueries", "Named Queries", "", this.getModelerScene(), entity.getNamedQuery()));
        set.put("BASIC_PROP", PropertiesHandler.getNamedEntityGraphProperty("NamedEntityGraphs", "Named Entity Graphs", "", this));
        set.put("BASIC_PROP", PropertiesHandler.getNamedNativeQueryProperty("NamedNativeQueries", "Named Native Queries", "", this.getModelerScene(), entity.getNamedNativeQuery()));
        set.put("BASIC_PROP", PropertiesHandler.getNamedStoredProcedureQueryProperty("NamedStoredProcedureQueries", "Named StoredProcedure Queries", "", this.getModelerScene(), entity));
        set.put("BASIC_PROP", PropertiesHandler.getResultSetMappingsProperty("ResultSetMappings", "ResultSet Mappings", "", this.getModelerScene(), entity));
        
    }

    private EmbeddedPropertySupport getInheritenceProperty() {

        GenericEmbedded entity = new GenericEmbedded("inheritence", "Inheritence", "");
        try {
            entity.setEntityEditor(new InheritencePanel(this.getModelerScene().getModelerFile(), EntityWidget.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        entity.setDataListener(new EmbeddedDataListener<InheritenceHandler>() {
            private InheritenceHandler classSpec;
            private String displayName = null;

            @Override
            public void init() {
                classSpec = (InheritenceHandler) EntityWidget.this.getBaseElementSpec();
            }

            @Override
            public InheritenceHandler getData() {
                return classSpec;
            }

            @Override
            public void setData(InheritenceHandler classSpec) {
                EntityWidget.this.setBaseElementSpec((Entity)classSpec);
            }

            @Override
            public String getDisplay() {

                GeneralizationFlowWidget outgoingGeneralizationFlowWidget = EntityWidget.this.getOutgoingGeneralizationFlowWidget();
                List<GeneralizationFlowWidget> incomingGeneralizationFlowWidgets = EntityWidget.this.getIncomingGeneralizationFlowWidgets();

                if (outgoingGeneralizationFlowWidget != null && !(outgoingGeneralizationFlowWidget.getSuperclassWidget() instanceof EntityWidget)) {
                    outgoingGeneralizationFlowWidget = null;
                }
//                String type;
//                if (outgoingGeneralizationFlowWidget == null && incomingGeneralizationFlowWidgets.isEmpty()) {
//                    type = "SINGLETON";
//                } else
                if (outgoingGeneralizationFlowWidget != null && incomingGeneralizationFlowWidgets.isEmpty()) {
//                    type = "LEAF";
                    EntityWidget superEntityWidget = (EntityWidget) EntityWidget.this.getOutgoingGeneralizationFlowWidget().getSuperclassWidget();
                    InheritenceHandler superClassSpec = (InheritenceHandler) superEntityWidget.getBaseElementSpec();
                    if (superClassSpec.getInheritance() != null && superClassSpec.getInheritance().getStrategy() != null) {
                        return superClassSpec.getInheritance().getStrategy().toString();
                    } else {
                        return InheritanceType.SINGLE_TABLE.toString();
                    }
                } else if (outgoingGeneralizationFlowWidget == null && !incomingGeneralizationFlowWidgets.isEmpty()) {
//                    type = "ROOT";
                    if (classSpec.getInheritance() != null && classSpec.getInheritance().getStrategy() != null) {
                        return classSpec.getInheritance().getStrategy().toString();
                    } else {
                        return InheritanceType.SINGLE_TABLE.toString();
                    }
                } else if (outgoingGeneralizationFlowWidget != null && !incomingGeneralizationFlowWidgets.isEmpty()) {
//                    type = "BRANCH";
                    if (classSpec.getInheritance() != null && classSpec.getInheritance().getStrategy() != null) {
                        return classSpec.getInheritance().getStrategy().toString();
                    } else {
                        return InheritanceType.SINGLE_TABLE.toString();
                    }
                } else {
                    return "";
                }
            }

        });
        return new EmbeddedPropertySupport(this.getModelerScene().getModelerFile(), entity);
    }

    
    @Override
    public InheritenceStateType getInheritenceState() {
        GeneralizationFlowWidget outgoingGeneralizationFlowWidget = this.getOutgoingGeneralizationFlowWidget();
        List<GeneralizationFlowWidget> incomingGeneralizationFlowWidgets = this.getIncomingGeneralizationFlowWidgets();
        if (outgoingGeneralizationFlowWidget != null && outgoingGeneralizationFlowWidget.getSuperclassWidget() != null && !(outgoingGeneralizationFlowWidget.getSuperclassWidget() instanceof EntityWidget)) {
            outgoingGeneralizationFlowWidget = null;
        }
        InheritenceStateType type;
        if (outgoingGeneralizationFlowWidget == null && incomingGeneralizationFlowWidgets.isEmpty()) {
            type = SINGLETON;
        } else if (outgoingGeneralizationFlowWidget != null && incomingGeneralizationFlowWidgets.isEmpty()) {
            type = LEAF;
        } else if (outgoingGeneralizationFlowWidget == null && !incomingGeneralizationFlowWidgets.isEmpty()) {
            type = ROOT;
        } else if (outgoingGeneralizationFlowWidget != null && !incomingGeneralizationFlowWidgets.isEmpty()) {
            type = BRANCH;
        } else {
            throw new IllegalStateException("Illegal Inheritence State Exception Entity : "+ this.getName());
        }
        return type;
    }

//    @Override
    public void scanPrimaryKeyError() {
        
        InheritenceStateType inheritenceState = this.getInheritenceState();
        if (SINGLETON == inheritenceState || ROOT == inheritenceState) {
            // Issue Fix #6041 Start
           boolean relationKey = this.getOneToOneRelationAttributeWidgets().stream().anyMatch(w -> w.getBaseElementSpec().isPrimaryKey())?true:
                   this.getManyToOneRelationAttributeWidgets().stream().anyMatch(w -> w.getBaseElementSpec().isPrimaryKey());
                    
            if (this.getAllIdAttributeWidgets().isEmpty() && this.isCompositePKPropertyAllow() == CompositePKProperty.NONE && !relationKey) {
                getErrorHandler().throwError(EntityValidator.NO_PRIMARYKEY_EXIST);
            } else {
                getErrorHandler().clearError(EntityValidator.NO_PRIMARYKEY_EXIST);
            }
            // Issue Fix #6041 End
        } else {
            getErrorHandler().clearError(EntityValidator.NO_PRIMARYKEY_EXIST);
        }
    }
    
    
        @Override
    protected List<JMenuItem> getPopupMenuItemList() {
        List<JMenuItem> menuList = super.getPopupMenuItemList();
//        JMenuItem addEntityGraph;
//        addEntityGraph = new JMenuItem("Add Entity Graph");
//        addEntityGraph.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Component parentComponent = (Component)EntityWidget.this.getModelerScene().getModelerPanelTopComponent();
//                String name = JOptionPane.showInputDialog(parentComponent, "Please enter entity graph name : ", null);
//                openDiagram(name);
//            }
//        });
//        menuList.add(0, addEntityGraph);
        return menuList;
    }
    
    
//     public void openDiagram(String entityGraphId) {
//        String path = this.getModelerScene().getModelerPanelTopComponent().getToolTipText();
//        JPAFileActionListener fileAction = new JPAFileActionListener(this.getModelerScene().getModelerFile().getModelerFileDataObject());
//        fileAction.openModelerFile(entityGraphId,entityGraphId,entityGraphId + " > " + path);
//    }

    /**
     * @return the abstractEntity
     */
     public Boolean isAbstractEntity() {
        return abstractEntity;
    }

    /**
     * @param abstractEntity the abstractEntity to set
     */
    public void setAbstractEntity(Boolean abstractEntity) {
        this.abstractEntity = abstractEntity;
    }


}
