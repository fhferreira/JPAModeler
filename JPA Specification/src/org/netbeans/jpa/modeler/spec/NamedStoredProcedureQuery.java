//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.08.22 at 04:26:24 PM IST 
//
package org.netbeans.jpa.modeler.spec;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.apache.commons.lang.StringUtils;
import org.netbeans.jpa.source.JavaSourceParserUtil;

/**
 *
 *
 * @Target({TYPE}) @Retention(RUNTIME) public @interface
 * NamedStoredProcedureQuery { String name(); String procedureName();
 * StoredProcedureParameter[] parameters() default {}; Class[] resultClasses()
 * default {}; String[] resultSetMappings() default{}; QueryHint[] hints()
 * default {}; }
 *
 *
 *
 * <p>
 * Java class for named-stored-procedure-query complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="named-stored-procedure-query">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="parameter" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}stored-procedure-parameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="result-class" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="result-set-mapping" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hint" type="{http://xmlns.jcp.org/xml/ns/persistence/orm}query-hint" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="procedure-name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "named-stored-procedure-query", propOrder = {
    "description",
    "parameter",
    "resultClass",
    "resultSetMapping",
    "hint"
})
public class NamedStoredProcedureQuery {

    protected String description;
    @XmlElement(name = "p")
    protected List<StoredProcedureParameter> parameter;
    @XmlElement(name = "rc")//(name = "result-class")
    protected List<String> resultClass;
    @XmlElement(name = "rsm")//(name = "result-set-mapping")
    protected List<String> resultSetMapping;
    protected List<QueryHint> hint;
    @XmlAttribute(name = "n", required = true)//(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "pn", required = true)//(name = "procedure-name", required = true)
    protected String procedureName;

    private static NamedStoredProcedureQuery loadStoredProcedureQuery(Element element, AnnotationMirror annotationMirror) {
        NamedStoredProcedureQuery namedStoredProcedureQuery = null;
        if (annotationMirror != null) {
            namedStoredProcedureQuery = new NamedStoredProcedureQuery();
            namedStoredProcedureQuery.name = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "name");
            namedStoredProcedureQuery.procedureName = (String) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "procedureName");
            List hintsAnnot = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "hints");
            if (hintsAnnot != null) {
                for (Object hintObj : hintsAnnot) {
                    namedStoredProcedureQuery.getHint().add(QueryHint.load(element, (AnnotationMirror) hintObj));
                }
            }
            List resultClassesAnnot = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "resultClasses");
            if (resultClassesAnnot != null) {
                for (Object resultClassObj : resultClassesAnnot) {
                    namedStoredProcedureQuery.getResultClass().add(resultClassObj.toString());
                }
            }
            List resultSetMappingsAnnot = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "resultSetMappings");
            if (resultSetMappingsAnnot != null) {
                for (Object resultSetMappingObj : resultSetMappingsAnnot) {
                    namedStoredProcedureQuery.getResultSetMapping().add(resultSetMappingObj.toString().replaceAll("^\"|\"$", ""));//TODO , removing /n from end of the line
                }
            }
            List parametersAnnot = (List) JavaSourceParserUtil.findAnnotationValue(annotationMirror, "parameters");
            if (parametersAnnot != null) {
                for (Object parameterObj : parametersAnnot) {
                    namedStoredProcedureQuery.getParameter().add(StoredProcedureParameter.load(element, (AnnotationMirror) parameterObj));
                }
            }
        }
        return namedStoredProcedureQuery;
    }

    public static List<NamedStoredProcedureQuery> load(Element element) {
        List<NamedStoredProcedureQuery> namedStoredProcedureQueries = new ArrayList<NamedStoredProcedureQuery>();

        AnnotationMirror namedStoredProcedureQueriesMirror = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.NamedStoredProcedureQueries");
        if (namedStoredProcedureQueriesMirror != null) {
            List namedStoredProcedureQueryMirrorList = (List) JavaSourceParserUtil.findAnnotationValue(namedStoredProcedureQueriesMirror, "value");
            if (namedStoredProcedureQueryMirrorList != null) {
                for (Object namedStoredProcedureQueryObj : namedStoredProcedureQueryMirrorList) {
                    namedStoredProcedureQueries.add(NamedStoredProcedureQuery.loadStoredProcedureQuery(element, (AnnotationMirror) namedStoredProcedureQueryObj));
                }
            }
        } else {
            namedStoredProcedureQueriesMirror = JavaSourceParserUtil.findAnnotation(element, "javax.persistence.NamedStoredProcedureQuery");
            if (namedStoredProcedureQueriesMirror != null) {
                namedStoredProcedureQueries.add(NamedStoredProcedureQuery.loadStoredProcedureQuery(element, namedStoredProcedureQueriesMirror));
            }
        }
        return namedStoredProcedureQueries;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the parameter property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the parameter property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StoredProcedureParameter }
     *
     *
     */
    public List<StoredProcedureParameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<StoredProcedureParameter>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the resultClass property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the resultClass property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultClass().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getResultClass() {
        if (resultClass == null) {
            resultClass = new ArrayList<String>();
        }
        return this.resultClass;
    }

    /**
     * Gets the value of the resultSetMapping property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the resultSetMapping property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResultSetMapping().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list {@link String }
     *
     *
     */
    public List<String> getResultSetMapping() {
        if (resultSetMapping == null) {
            resultSetMapping = new ArrayList<String>();
        }
        return this.resultSetMapping;
    }

    /**
     * Gets the value of the hint property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the hint property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHint().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryHint }
     *
     *
     */
    public List<QueryHint> getHint() {
        if (hint == null) {
            hint = new ArrayList<QueryHint>();
        }
        return this.hint;
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
     * Gets the value of the procedureName property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getProcedureName() {
        return procedureName;
    }

    /**
     * Sets the value of the procedureName property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setProcedureName(String value) {
        this.procedureName = value;
    }

}
