//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2014.01.21 at 01:52:19 PM IST
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.netbeans.jpa.modeler.spec.extend.JoinColumnHandler;
import org.netbeans.jpa.modeler.spec.extend.RelationAttribute;
import org.netbeans.jpa.modeler.spec.jaxb.JaxbVariableType;
import org.netbeans.jpa.source.JavaSourceParserUtil;
import org.netbeans.modeler.core.NBModelerUtil;

/**
 *
 *
 * @Target({METHOD, FIELD}) @Retention(RUNTIME) public @interface OneToMany {
 * Class targetEntity() default void.class; CascadeType[] cascade() default {};
 * FetchType fetch() default LAZY; String mappedBy() default ""; }
 *
 *
 *
 * <p>
 * Java class for one-to-many complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="one-to-many">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element name="order-by" type="{http://java.sun.com/xml/ns/persistence/orm}order-by" minOccurs="0"/>
 *           &lt;element name="order-column" type="{http://java.sun.com/xml/ns/persistence/orm}order-column" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="map-key" type="{http://java.sun.com/xml/ns/persistence/orm}map-key" minOccurs="0"/>
 *           &lt;sequence>
 *             &lt;element name="map-key-class" type="{http://java.sun.com/xml/ns/persistence/orm}map-key-class" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="map-key-temporal" type="{http://java.sun.com/xml/ns/persistence/orm}temporal" minOccurs="0"/>
 *               &lt;element name="map-key-enumerated" type="{http://java.sun.com/xml/ns/persistence/orm}enumerated" minOccurs="0"/>
 *               &lt;element name="map-key-attribute-override" type="{http://java.sun.com/xml/ns/persistence/orm}attribute-override" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;/choice>
 *             &lt;choice>
 *               &lt;element name="map-key-column" type="{http://java.sun.com/xml/ns/persistence/orm}map-key-column" minOccurs="0"/>
 *               &lt;element name="map-key-join-column" type="{http://java.sun.com/xml/ns/persistence/orm}map-key-join-column" maxOccurs="unbounded" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;choice>
 *           &lt;element name="join-table" type="{http://java.sun.com/xml/ns/persistence/orm}join-table" minOccurs="0"/>
 *           &lt;element name="join-column" type="{http://java.sun.com/xml/ns/persistence/orm}join-column" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="cascade" type="{http://java.sun.com/xml/ns/persistence/orm}cascade-type" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="target-entity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="fetch" type="{http://java.sun.com/xml/ns/persistence/orm}fetch-type" />
 *       &lt;attribute name="access" type="{http://java.sun.com/xml/ns/persistence/orm}access-type" />
 *       &lt;attribute name="mapped-by" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="orphan-removal" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "one-to-many", propOrder = {
    "orderBy",
    "orderColumn",
    "mapKey",
    "mapKeyClass",
    "mapKeyTemporal",
    "mapKeyEnumerated",
    "mapKeyAttributeOverride",
    "mapKeyColumn",
    "mapKeyJoinColumn",
    "joinTable",
    "joinColumn",
    "cascade"
})
public class OneToMany extends RelationAttribute implements JoinColumnHandler {

    @XmlElement(name = "order-by")
    protected String orderBy;//RENENG PENDING
    @XmlElement(name = "order-column")
    protected OrderColumn orderColumn;//RENENG PENDING
    @XmlElement(name = "map-key")
    protected MapKey mapKey;//RENENG PENDING
    @XmlElement(name = "map-key-class")
    protected MapKeyClass mapKeyClass;//RENENG PENDING
    @XmlElement(name = "map-key-temporal")
    protected TemporalType mapKeyTemporal;//RENENG PENDING
    @XmlElement(name = "map-key-enumerated")
    protected EnumType mapKeyEnumerated;//RENENG PENDING
    @XmlElement(name = "map-key-attribute-override")
    protected List<AttributeOverride> mapKeyAttributeOverride;//RENENG PENDING
    @XmlElement(name = "map-key-column")
    protected MapKeyColumn mapKeyColumn;//RENENG PENDING
    @XmlElement(name = "map-key-join-column")
    protected List<MapKeyJoinColumn> mapKeyJoinColumn;//RENENG PENDING
    @XmlElement(name = "join-table")
    protected JoinTable joinTable;
    @XmlElement(name = "join-column")
    protected List<JoinColumn> joinColumn;
    protected CascadeType cascade;
    @XmlAttribute(required = true)
    protected String name;
    @XmlAttribute(name = "target-entity")
    protected String targetEntity;
    @XmlAttribute
    protected FetchType fetch;
    @XmlAttribute
    protected AccessType access;
    @XmlAttribute(name = "mapped-by")
    protected String mappedBy;
    @XmlAttribute(name = "orphan-removal")
    protected Boolean orphanRemoval;
    @XmlAttribute(name = "collection-type")
    private String collectionType;//custom added

    public static OneToMany load(Element element, VariableElement variableElement) {
        AnnotationMirror annotationMirror = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.OneToMany");
        OneToMany oneToMany = new OneToMany();
        oneToMany.setId(NBModelerUtil.getAutoGeneratedStringId());
        oneToMany.joinTable = JoinTable.load(element);

        AnnotationMirror joinColumnsAnnotationMirror = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.JoinColumns");
        if (joinColumnsAnnotationMirror != null) {
            List joinColumnsAnnot = (List) JavaSourceParserUtil.findAnnotationValue(joinColumnsAnnotationMirror, "value");
            if (joinColumnsAnnot != null) {
                for (Object joinColumnObj : joinColumnsAnnot) {
                    oneToMany.getJoinColumn().add(JoinColumn.load(element, (AnnotationMirror) joinColumnObj));
                }
            }
        }

        List cascadeList = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "cascade");
        if (cascadeList != null) {
            CascadeType cascadeType = new CascadeType();
            oneToMany.cascade = cascadeType;
            for (Object cascadeObj : cascadeList) {
                if (cascadeObj.toString().equals("javax.persistence.CascadeType.ALL")) {
                    cascadeType.setCascadeAll(new EmptyType());
                } else if (cascadeObj.toString().equals("javax.persistence.CascadeType.PERSIST")) {
                    cascadeType.setCascadePersist(new EmptyType());
                } else if (cascadeObj.toString().equals("javax.persistence.CascadeType.MERGE")) {
                    cascadeType.setCascadeMerge(new EmptyType());
                } else if (cascadeObj.toString().equals("javax.persistence.CascadeType.REMOVE")) {
                    cascadeType.setCascadeMerge(new EmptyType());
                } else if (cascadeObj.toString().equals("javax.persistence.CascadeType.REFRESH")) {
                    cascadeType.setCascadeRefresh(new EmptyType());
                } else if (cascadeObj.toString().equals("javax.persistence.CascadeType.DETACH")) {
                    cascadeType.setCascadeDetach(new EmptyType());
                } else {
                    throw new IllegalStateException("Unknown Cascade Type : " + cascadeObj.toString());
                }
            }
        }

        oneToMany.name = variableElement.getSimpleName().toString();
        oneToMany.setCollectionType(((DeclaredType) variableElement.asType()).asElement().toString());
        DeclaredType declaredType = (DeclaredType) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "targetEntity");
        if (declaredType == null) {
            declaredType = (DeclaredType) ((DeclaredType) variableElement.asType()).getTypeArguments().get(0);
        }
        oneToMany.targetEntity = declaredType.asElement().getSimpleName().toString();
        Object fetchObj = JavaSourceParserUtil.findAnnotationValue(annotationMirror, "fetch");
        if (fetchObj != null) {
            oneToMany.fetch = FetchType.valueOf(fetchObj.toString());
        }
//        oneToMany.optional = (Boolean) JpaControllerUtil.findAnnotationValue(annotationMirror, "optional");
        oneToMany.access = AccessType.load(element);
        oneToMany.mappedBy = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "mappedBy");
        oneToMany.orphanRemoval = (Boolean) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "orphanRemoval");
        JavaSourceParserUtil.addNonEEAnnotation(oneToMany, element);
        return oneToMany;
    }

    /**
     * Gets the value of the orderBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the value of the orderBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * Gets the value of the orderColumn property.
     *
     * @return possible object is {@link OrderColumn }
     *
     */
    public OrderColumn getOrderColumn() {
        return orderColumn;
    }

    /**
     * Sets the value of the orderColumn property.
     *
     * @param value allowed object is {@link OrderColumn }
     *
     */
    public void setOrderColumn(OrderColumn value) {
        this.orderColumn = value;
    }

    /**
     * Gets the value of the mapKey property.
     *
     * @return possible object is {@link MapKey }
     *
     */
    public MapKey getMapKey() {
        return mapKey;
    }

    /**
     * Sets the value of the mapKey property.
     *
     * @param value allowed object is {@link MapKey }
     *
     */
    public void setMapKey(MapKey value) {
        this.mapKey = value;
    }

    /**
     * Gets the value of the mapKeyClass property.
     *
     * @return possible object is {@link MapKeyClass }
     *
     */
    public MapKeyClass getMapKeyClass() {
        return mapKeyClass;
    }

    /**
     * Sets the value of the mapKeyClass property.
     *
     * @param value allowed object is {@link MapKeyClass }
     *
     */
    public void setMapKeyClass(MapKeyClass value) {
        this.mapKeyClass = value;
    }

    /**
     * Gets the value of the mapKeyTemporal property.
     *
     * @return possible object is {@link TemporalType }
     *
     */
    public TemporalType getMapKeyTemporal() {
        return mapKeyTemporal;
    }

    /**
     * Sets the value of the mapKeyTemporal property.
     *
     * @param value allowed object is {@link TemporalType }
     *
     */
    public void setMapKeyTemporal(TemporalType value) {
        this.mapKeyTemporal = value;
    }

    /**
     * Gets the value of the mapKeyEnumerated property.
     *
     * @return possible object is {@link EnumType }
     *
     */
    public EnumType getMapKeyEnumerated() {
        return mapKeyEnumerated;
    }

    /**
     * Sets the value of the mapKeyEnumerated property.
     *
     * @param value allowed object is {@link EnumType }
     *
     */
    public void setMapKeyEnumerated(EnumType value) {
        this.mapKeyEnumerated = value;
    }

    /**
     * Gets the value of the mapKeyAttributeOverride property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the mapKeyAttributeOverride property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapKeyAttributeOverride().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttributeOverride }
     *
     *
     */
    public List<AttributeOverride> getMapKeyAttributeOverride() {
        if (mapKeyAttributeOverride == null) {
            mapKeyAttributeOverride = new ArrayList<AttributeOverride>();
        }
        return this.mapKeyAttributeOverride;
    }

    /**
     * Gets the value of the mapKeyColumn property.
     *
     * @return possible object is {@link MapKeyColumn }
     *
     */
    public MapKeyColumn getMapKeyColumn() {
        return mapKeyColumn;
    }

    /**
     * Sets the value of the mapKeyColumn property.
     *
     * @param value allowed object is {@link MapKeyColumn }
     *
     */
    public void setMapKeyColumn(MapKeyColumn value) {
        this.mapKeyColumn = value;
    }

    /**
     * Gets the value of the mapKeyJoinColumn property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the mapKeyJoinColumn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMapKeyJoinColumn().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MapKeyJoinColumn }
     *
     *
     */
    public List<MapKeyJoinColumn> getMapKeyJoinColumn() {
        if (mapKeyJoinColumn == null) {
            mapKeyJoinColumn = new ArrayList<MapKeyJoinColumn>();
        }
        return this.mapKeyJoinColumn;
    }

    /**
     * Gets the value of the joinTable property.
     *
     * @return possible object is {@link JoinTable }
     *
     */
    @Override
    public JoinTable getJoinTable() {
        if (joinTable == null) {
            joinTable = new JoinTable();
        }
        return joinTable;
    }

    /**
     * Sets the value of the joinTable property.
     *
     * @param value allowed object is {@link JoinTable }
     *
     */
    public void setJoinTable(JoinTable value) {
        this.joinTable = value;
    }

    /**
     * Gets the value of the joinColumn property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the joinColumn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJoinColumn().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JoinColumn }
     *
     *
     */
    public List<JoinColumn> getJoinColumn() {
        if (joinColumn == null) {
            joinColumn = new ArrayList<JoinColumn>();
        }
        return this.joinColumn;
    }

    @Override
    public void addJoinColumn(JoinColumn joinColumn_In) {
        if (joinColumn == null) {
            joinColumn = new ArrayList<JoinColumn>();
        }
        joinColumn.add(joinColumn_In);
    }

    @Override
    public void removeJoinColumn(JoinColumn joinColumn_In) {
        if (joinColumn == null) {
            joinColumn = new ArrayList<JoinColumn>();
        }
        joinColumn.remove(joinColumn_In);
    }

    /**
     * Gets the value of the cascade property.
     *
     * @return possible object is {@link CascadeType }
     *
     */
    public CascadeType getCascade() {
        return cascade;
    }

    /**
     * Sets the value of the cascade property.
     *
     * @param value allowed object is {@link CascadeType }
     *
     */
    public void setCascade(CascadeType value) {
        this.cascade = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the targetEntity property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTargetEntity() {
        return targetEntity;
    }

    /**
     * Sets the value of the targetEntity property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTargetEntity(String value) {
        this.targetEntity = value;
    }

    /**
     * Gets the value of the fetch property.
     *
     * @return possible object is {@link FetchType }
     *
     */
    public FetchType getFetch() {
        return fetch;
    }

    /**
     * Sets the value of the fetch property.
     *
     * @param value allowed object is {@link FetchType }
     *
     */
    public void setFetch(FetchType value) {
        this.fetch = value;
    }

    /**
     * Gets the value of the access property.
     *
     * @return possible object is {@link AccessType }
     *
     */
    public AccessType getAccess() {
        return access;
    }

    /**
     * Sets the value of the access property.
     *
     * @param value allowed object is {@link AccessType }
     *
     */
    public void setAccess(AccessType value) {
        this.access = value;
    }

    /**
     * Gets the value of the mappedBy property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMappedBy() {
        return mappedBy;
    }

    /**
     * Sets the value of the mappedBy property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMappedBy(String value) {
        this.mappedBy = value;
    }

    /**
     * Gets the value of the orphanRemoval property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public Boolean isOrphanRemoval() {
        return orphanRemoval;
    }

    /**
     * Sets the value of the orphanRemoval property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setOrphanRemoval(Boolean value) {
        this.orphanRemoval = value;
    }

    /**
     * @return the collectionType
     */
    public String getCollectionType() {
        if (collectionType == null) {
            collectionType = "java.util.Collection";
        }
        return collectionType;
    }

    /**
     * @param collectionType the collectionType to set
     */
    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }

    @Override
    public List<JaxbVariableType> getJaxbVariableList() {
        if(mappedBy!=null && !mappedBy.trim().isEmpty()){
            return null;
        } else {
            return super.getJaxbVariableList();
        }
    }
}
