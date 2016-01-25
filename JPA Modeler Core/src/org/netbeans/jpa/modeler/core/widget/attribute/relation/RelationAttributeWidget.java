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
package org.netbeans.jpa.modeler.core.widget.attribute.relation;

import java.awt.Image;
import org.netbeans.jpa.modeler.core.widget.EntityWidget;
import org.netbeans.jpa.modeler.core.widget.PersistenceClassWidget;
import org.netbeans.jpa.modeler.core.widget.attribute.AttributeWidget;
import org.netbeans.jpa.modeler.core.widget.flow.relation.RelationFlowWidget;
import org.netbeans.jpa.modeler.core.widget.relation.flow.direction.Bidirectional;
import org.netbeans.jpa.modeler.core.widget.relation.flow.direction.Unidirectional;
import org.netbeans.jpa.modeler.properties.PropertiesHandler;
import org.netbeans.jpa.modeler.properties.cascade.CascadeTypePanel;
import org.netbeans.jpa.modeler.spec.CascadeType;
import org.netbeans.jpa.modeler.spec.Entity;
import org.netbeans.jpa.modeler.spec.extend.CollectionTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.FetchTypeHandler;
import org.netbeans.jpa.modeler.spec.extend.JoinColumnHandler;
import org.netbeans.jpa.modeler.spec.extend.RelationAttribute;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.modeler.properties.embedded.EmbeddedDataListener;
import org.netbeans.modeler.properties.embedded.EmbeddedPropertySupport;
import org.netbeans.modeler.properties.embedded.GenericEmbedded;
import org.netbeans.modeler.specification.model.document.property.ElementPropertySet;
import org.netbeans.modeler.widget.node.IPNodeWidget;
import org.netbeans.modeler.widget.pin.info.PinWidgetInfo;

/**
 *
 * @author Gaurav Gupta
 */
public abstract class RelationAttributeWidget<E extends RelationAttribute> extends AttributeWidget<E> {


    public RelationAttributeWidget(JPAModelerScene scene, IPNodeWidget nodeWidget, PinWidgetInfo pinWidgetInfo) {
        super(scene, nodeWidget, pinWidgetInfo);
       

    }

        @Override
    protected void setAttributeTooltip(){
        if (getBaseElementSpec() instanceof CollectionTypeHandler) {
                CollectionTypeHandler collectionTypeHandler = (CollectionTypeHandler)getBaseElementSpec();
                StringBuilder writer = new StringBuilder();
                writer.append(collectionTypeHandler.getCollectionType().substring(collectionTypeHandler.getCollectionType().lastIndexOf('.')+1));
//                writer.append('<').append(this.getBaseElementSpec().get()).append('>');//TODO
            this.setToolTipText(writer.toString());    
        } else {
            this.setToolTipText(this.getBaseElementSpec().getTargetEntity());//TODO
        }
    }
    
    @Override
    public void createPropertySet(ElementPropertySet set) {
        super.createPropertySet(set);
        set.put("BASIC_PROP", getCascadeProperty());
        // Issue Fix #6153 Start
        set.put("BASIC_PROP", PropertiesHandler.getFetchTypeProperty(this.getModelerScene(), (FetchTypeHandler) this.getBaseElementSpec()));
        // Issue Fix #6153 End
        RelationAttribute relationAttributeSpec = (RelationAttribute) this.getBaseElementSpec();
        
        // find source and target entity
        Entity sourceEntity = ((EntityWidget)getModelerScene().getBaseElement(relationAttributeSpec.getConnectedEntity().getId())).getBaseElementSpec();
        Entity targetEntity;
        
        RelationFlowWidget flowWidget = this.getRelationFlowWidget();
        if(flowWidget instanceof Bidirectional){
           RelationAttribute targetRelationAttributeSpec = ((Bidirectional)flowWidget).getTargetRelationAttributeWidget().getBaseElementSpec();
            targetEntity = ((EntityWidget)getModelerScene().getBaseElement(targetRelationAttributeSpec.getConnectedEntity().getId())).getBaseElementSpec();
        } else {
            targetEntity = ((Unidirectional)flowWidget).getTargetEntityWidget().getBaseElementSpec();
        }
        
        
        
        
        
        
        if (relationAttributeSpec.isOwner()) {

            if (this.getBaseElementSpec() instanceof JoinColumnHandler) {
                JoinColumnHandler joinColumnHandlerSpec = (JoinColumnHandler) this.getBaseElementSpec();
                set.put("JOIN_COLUMN_PROP", PropertiesHandler.getJoinColumnsProperty("JoinColumns", "Join Columns", "", this.getModelerScene(), joinColumnHandlerSpec.getJoinColumn(),targetEntity));

            }
            
            set.createPropertySet( this , relationAttributeSpec.getJoinTable());

            set.put("JOIN_TABLE_PROP", PropertiesHandler.getJoinColumnsProperty("JoinTable_JoinColumns", "Join Columns", "", this.getModelerScene(), relationAttributeSpec.getJoinTable().getJoinColumn()));
            set.put("JOIN_TABLE_PROP", PropertiesHandler.getJoinColumnsProperty("JoinTable_InverseJoinColumns", "Inverse Join Columns", "", this.getModelerScene(), relationAttributeSpec.getJoinTable().getInverseJoinColumn()));

//            set.put("JOIN_TABLE_PROP", getJoinTableColumnProperty());
//            set.put("JOIN_TABLE_PROP", getJoinTableInverseColumnProperty());
        }

    }

//    private PropertySupport getJoinTableInverseColumnProperty() {
//        final RelationAttribute relationAttributeSpec = (RelationAttribute) this.getBaseElementSpec();
//        final NAttributeEntity attributeEntity = new NAttributeEntity("JoinTable_InverseJoinColumns", "Inverse Join Columns", "");
//        attributeEntity.setCountDisplay(new String[]{"No JoinColumns exist", "One JoinColumn exist", "JoinColumns exist"});
//
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("OBJECT", false, true, Object.class));
//        columns.add(new Column("Column Name", false, String.class));
//        columns.add(new Column("Referenced Column Name", false, String.class));
//        attributeEntity.setColumns(columns);
//        attributeEntity.setCustomDialog(new JoinColumnPanel());
//
//        attributeEntity.setTableDataListener(new NEntityDataListener() {
//            List<Object[]> data;
//            int count;
//
//            @Override
//            public void initCount() {
//                count = relationAttributeSpec.getJoinTable().getInverseJoinColumn().size();
//            }
//
//            @Override
//            public int getCount() {
//                return count;
//            }
//
//            @Override
//            public void initData() {
//                List<JoinColumn> joinColumns = relationAttributeSpec.getJoinTable().getInverseJoinColumn();
//                List<Object[]> data_local = new LinkedList<Object[]>();
//                Iterator<JoinColumn> itr = joinColumns.iterator();
//                while (itr.hasNext()) {
//                    JoinColumn joinColumn = itr.next();
//                    Object[] row = new Object[attributeEntity.getColumns().size()];
//                    row[0] = joinColumn;
//                    row[1] = joinColumn.getName();
//                    row[2] = joinColumn.getReferencedColumnName();
//                    data_local.add(row);
//                }
//                this.data = data_local;
//            }
//
//            @Override
//            public List<Object[]> getData() {
//                return data;
//            }
//
//            @Override
//            public void setData(List data) {
//                relationAttributeSpec.getJoinTable().getInverseJoinColumn().clear();
//                for (Object[] row : (List<Object[]>) data) {
//                    relationAttributeSpec.getJoinTable().getInverseJoinColumn().add((JoinColumn) row[0]);
//                }
//                this.data = data;
//            }
//
//        });
//
//        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
//    }
//    private PropertySupport getJoinTableColumnProperty() {
//        final RelationAttribute relationAttributeSpec = (RelationAttribute) this.getBaseElementSpec();
//        final NAttributeEntity attributeEntity = new NAttributeEntity("JoinTable_JoinColumns", "Join Columns", "");
//        attributeEntity.setCountDisplay(new String[]{"No JoinColumns exist", "One JoinColumn exist", "JoinColumns exist"});
//
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("OBJECT", false, true, Object.class));
//        columns.add(new Column("Column Name", false, String.class));
//        columns.add(new Column("Referenced Column Name", false, String.class));
//        attributeEntity.setColumns(columns);
//        attributeEntity.setCustomDialog(new JoinColumnPanel());
//
//        attributeEntity.setTableDataListener(new NEntityDataListener() {
//            List<Object[]> data;
//            int count;
//
//            @Override
//            public void initCount() {
//                count = relationAttributeSpec.getJoinTable().getJoinColumn().size();
//            }
//
//            @Override
//            public int getCount() {
//                return count;
//            }
//
//            @Override
//            public void initData() {
//                List<JoinColumn> joinColumns = relationAttributeSpec.getJoinTable().getJoinColumn();
//                List<Object[]> data_local = new LinkedList<Object[]>();
//                Iterator<JoinColumn> itr = joinColumns.iterator();
//                while (itr.hasNext()) {
//                    JoinColumn joinColumn = itr.next();
//                    Object[] row = new Object[attributeEntity.getColumns().size()];
//                    row[0] = joinColumn;
//                    row[1] = joinColumn.getName();
//                    row[2] = joinColumn.getReferencedColumnName();
//                    data_local.add(row);
//                }
//                this.data = data_local;
//            }
//
//            @Override
//            public List<Object[]> getData() {
//                return data;
//            }
//
//            @Override
//            public void setData(List data) {
//                relationAttributeSpec.getJoinTable().getJoinColumn().clear();
//                for (Object[] row : (List<Object[]>) data) {
//                    relationAttributeSpec.getJoinTable().getJoinColumn().add((JoinColumn) row[0]);
//                }
//                this.data = data;
//            }
//
//        });
//
//        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
//    }
//    private PropertySupport getJoinColumnsProperty() {
//        final JoinColumnHandler joinColumnHandlerSpec = (JoinColumnHandler) this.getBaseElementSpec();
//        final NAttributeEntity attributeEntity = new NAttributeEntity("JoinColumns", "Join Columns", "");
//        attributeEntity.setCountDisplay(new String[]{"No JoinColumns exist", "One JoinColumn exist", "JoinColumns exist"});
//
//        List<Column> columns = new ArrayList<Column>();
//        columns.add(new Column("OBJECT", false, true, Object.class));
//        columns.add(new Column("Column Name", false, String.class));
//        columns.add(new Column("Referenced Column Name", false, String.class));
//        attributeEntity.setColumns(columns);
//        attributeEntity.setCustomDialog(new JoinColumnPanel());
//
//        attributeEntity.setTableDataListener(new NEntityDataListener/*<TProperty>*/() {
//                    List<Object[]> data;
//                    int count;
//
//                    @Override
//                    public void initCount() {
//                        count = joinColumnHandlerSpec.getJoinColumn().size();
//                    }
//
//                    @Override
//                    public int getCount() {
//                        return count;
//                    }
//
//                    @Override
//                    public void initData() {
//                        List<JoinColumn> joinColumns = joinColumnHandlerSpec.getJoinColumn();
//                        List<Object[]> data_local = new LinkedList<Object[]>();
//                        Iterator<JoinColumn> itr = joinColumns.iterator();
//                        while (itr.hasNext()) {
//                            JoinColumn joinColumn = itr.next();
//                            Object[] row = new Object[attributeEntity.getColumns().size()];
//                            row[0] = joinColumn;
//                            row[1] = joinColumn.getName();
//                            row[2] = joinColumn.getReferencedColumnName();
//                            data_local.add(row);
//                        }
//                        this.data = data_local;
//                    }
//
//                    @Override
//                    public List<Object[]> getData() {
//                        return data;
//                    }
//
//                    @Override
//                    public void setData(List data) {
//                        joinColumnHandlerSpec.getJoinColumn().clear();
//                        for (Object[] row : (List<Object[]>) data) {
//                            joinColumnHandlerSpec.addJoinColumn((JoinColumn) row[0]);
//                        }
//                        this.data = data;
//                    }
//
//                });
//
//        return new NEntityPropertySupport(this.getModelerScene().getModelerFile(), attributeEntity);
//    }
    private EmbeddedPropertySupport getCascadeProperty() {

        GenericEmbedded entity = new GenericEmbedded("cascadeType", "Cascade Type", "");
        entity.setEntityEditor(new CascadeTypePanel(this.getModelerScene().getModelerFile()));

        entity.setDataListener(new EmbeddedDataListener<CascadeType>() {
            private RelationAttribute relationAttribute;

            @Override
            public void init() {
                relationAttribute = (RelationAttribute) RelationAttributeWidget.this.getBaseElementSpec();
            }

            @Override
            public CascadeType getData() {
                return relationAttribute.getCascade();
            }

            @Override
            public void setData(CascadeType cascadeType) {
                relationAttribute.setCascade(cascadeType);
            }

            @Override
            public String getDisplay() {
                StringBuilder display = new StringBuilder();
                CascadeType cascadeType = relationAttribute.getCascade();
                if (cascadeType == null) {
                    display.append("None");
                } else {
                    if (cascadeType.getCascadeAll() != null) {
                        display.append("All");
                    } else {
                        if (cascadeType.getCascadeDetach() != null) {
                            display.append("Detach,");
                        }
                        if (cascadeType.getCascadeMerge() != null) {
                            display.append("Merge,");
                        }
                        if (cascadeType.getCascadePersist() != null) {
                            display.append("Persist,");
                        }
                        if (cascadeType.getCascadeRefresh() != null) {
                            display.append("Refresh,");
                        }
                        if (cascadeType.getCascadeRemove() != null) {
                            display.append("Remove,");
                        }
                        if (display.length() != 0) {
                            display.setLength(display.length() - 1);
                        }
                    }
                }

                return display.toString();
            }

        });
        return new EmbeddedPropertySupport(this.getModelerScene().getModelerFile(), entity);
    }


    public void setConnectedSibling(EntityWidget classWidget) {
        RelationAttribute relationAttribute = this.getBaseElementSpec();
        relationAttribute.setConnectedEntity(classWidget.getBaseElementSpec());
    }

    public void setConnectedSibling(EntityWidget classWidget, RelationAttributeWidget<RelationAttribute> attributeWidget) {
        RelationAttribute relationAttribute = this.getBaseElementSpec();
        relationAttribute.setConnectedEntity(classWidget.getBaseElementSpec());
        relationAttribute.setConnectedAttribute(attributeWidget.getBaseElementSpec());

    }

    public abstract RelationFlowWidget getRelationFlowWidget();

    public abstract String getIconPath();

    public abstract Image getIcon();
}
